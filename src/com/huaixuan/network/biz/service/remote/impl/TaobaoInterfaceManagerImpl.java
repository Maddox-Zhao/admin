package com.huaixuan.network.biz.service.remote.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceUserGoodsManager;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.TaobaoXmlRestClient;
import com.taobao.api.model.ItemAddRequest;
import com.taobao.api.model.ItemAddResponse;
import com.taobao.api.model.ItemGetRequest;
import com.taobao.api.model.ItemGetResponse;

@Service("taobaoInterfaceManager")
public class TaobaoInterfaceManagerImpl implements TaobaoInterfaceManager {
    protected final Log             log           = LogFactory.getLog(this.getClass());
    @Autowired
    TaobaoInterfaceApplyManager     taobaoInterfaceApplyManager;
    @Autowired
    TaobaoInterfaceUserGoodsManager taobaoInterfaceUserGoodsManager;
    public final static String      AUTHORIZE_URL = "http://open.taobao.com/isv/authorize.php?appkey=";
    public final static String      SANDBOX_URL   = "http://container.api.tbsandbox.com/container?authcode=";
    public final static String      CESHI_URL     = "http://gw.api.tbsandbox.com/router/rest";

    /**
     *  解析错误的信�
     */
    public Map<String, String> getErrorCode(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("xml解析异常..." + e.getMessage());
        }
        Element element = doc.getRootElement();
        Map<String, String> map = new HashMap<String, String>();
        map.put("state", "false");
        map.put("code", element.element("code").getText());
        map.put("msg", element.element("msg").getText());
        if (element.element("sub_msg") != null)
            map.put("sub_msg", element.element("sub_msg").getText());
        else if (element.element("msg") != null) {
            map.put("sub_msg", element.element("msg").getText());
        }
        return map;
    }

    /**
     *  �查是否是正确�
     */
    public String check(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("xml解析异常..." + e.getMessage());
        }
        return doc.getRootElement().getName();
    }

    /**
     * 淘宝上获取授权的
     * @param nick
     * @param appkey
     * @return
     */
    public String getTestSession(String nick, String appkey) {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);
        HtmlPage loginPage = null;
        try {
            loginPage = webClient.getPage(AUTHORIZE_URL + appkey);
        } catch (FailingHttpStatusCodeException e1) {
            log.error(e1.getMessage());
        } catch (MalformedURLException e1) {
            log.error(e1.getMessage());
        } catch (IOException e1) {
            log.error(e1.getMessage());
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
            log.error(e.getMessage());
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
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return session;
    }

    /**
     *  新增淘宝商品
     */
    public Map<String, String> taobaoGoodsAdd(ItemAddRequest req, long userId, String sessionKey,
                                              boolean isSandbox) throws TaobaoApiException,
                                                                ParseException {
        TaobaoApply taobaoApply = this.taobaoInterfaceApplyManager.getInterfaceApplyByUserId(
            userId, EnumInterfaceType.TAOBAO.getKey());
        TaobaoRestClient client;
        if (isSandbox) {
            client = new TaobaoXmlRestClient("test", "test", isSandbox);
        } else {
            client = new TaobaoXmlRestClient(taobaoApply.getParamOne(), taobaoApply.getParamTwo(),
                isSandbox);
        }
        ItemAddResponse rsp = client.itemAdd(req, sessionKey);
        String xml = rsp.getBody();
        if (StringUtils.equals(check(xml), "error_response")) {
            return getErrorCode(xml);
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("state", "true");
            map.put("numIid", getNumIid(xml));
            return map;
        }
    }

    /**
     *  获取单个淘宝商品
     */
    public boolean taobaoItemGet(String numIid, long userId, boolean isSandbox)
                                                                               throws TaobaoApiException {
        boolean isGet = false;
        TaobaoApply taobaoApply = this.taobaoInterfaceApplyManager.getInterfaceApplyByUserId(
            userId, EnumInterfaceType.TAOBAO.getKey());
        TaobaoRestClient client = new TaobaoXmlRestClient(taobaoApply.getParamOne(),
            taobaoApply.getParamTwo(), isSandbox);
        ItemGetRequest req = new ItemGetRequest();
        req.setFields("title,approve_status");
        req.setNumIid(numIid);
        ItemGetResponse rsp = client.itemGet(req);
        if (rsp.getErrorCode() == null)
            isGet = true;
        return isGet;
    }

    /**
     *  取到商品ID:iid
     */
    public String getNumIid(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("xml解析异常..." + e.getMessage());
        }
        Element element = doc.getRootElement();
        String numIid = element.element("item").element("num_iid").getText();
        return numIid;
    }

}
