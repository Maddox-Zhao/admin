package com.huaixuan.network.common.util.remote;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * 登录淘宝网获取授权�
 *
 * @author czx
 */
public class LoginUtil {

    public final static String AUTHORIZE_URL = "http://open.taobao.com/isv/authorize.php?appkey=";
    public final static String SANDBOX_URL   = "http://container.api.tbsandbox.com/container?authcode=";

    protected final static Log logger        = LogFactory.getLog(LoginUtil.class);
    public static String       session;

    public static String getSession() {
        return session;
    }

    public static void setSession(String session) {
        LoginUtil.session = session;
    }

    /**
     *  add by jinxx 2010/10/21
     * @param nick 淘宝的昵�
     * @param appkey 申请通过
     * @return sessionKey 获取
     */
    public static String getTestSession(String nick, String appkey) {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
        HtmlPage loginPage = null;
        try {
            loginPage = webClient.getPage(AUTHORIZE_URL + appkey);
        } catch (FailingHttpStatusCodeException e1) {
            logger.error(e1.getMessage());
        } catch (MalformedURLException e1) {
            logger.error(e1.getMessage());
        } catch (IOException e1) {
            logger.error(e1.getMessage());
        }
        HtmlForm loginForm = loginPage.getForms().get(1);
        HtmlSelect select = loginForm.getSelectByName("nick");
        HtmlOption option = select.getOptionByValue(nick);
        select.setSelectedAttribute(option, true);
        HtmlSubmitInput authorizeButton = loginForm.getInputByValue("获取授权�");

        HtmlPage authorizePage = null;

        String session = null;

        try {
            Page rsp = authorizeButton.click();
            authorizePage = (HtmlPage) rsp;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return session;
        }

        HtmlElement element = authorizePage.getDocumentElement();
        HtmlElement autoInput = element.getElementById("autoInput");
        String authorizeCode = autoInput.getAttribute("value");

        if (StringUtils.isNotBlank(authorizeCode)) {
            try {
                InputStream in = new URL(SANDBOX_URL + authorizeCode).openStream();
                session = StringUtils.substringBetween(IOUtils.toString(in, "utf-8"),
                    "top_session=", "&");
                ;
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return session;
    }
}
