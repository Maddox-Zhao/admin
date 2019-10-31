package com.huaixuan.network.biz.service.remote.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import net.sf.json.JSONObject;

import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumPaipaiApi;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.PaipaiInterfaceManager;
import com.huaixuan.network.common.util.ApiParameter;
import com.huaixuan.network.common.util.Md5Util;
import com.huaixuan.network.common.util.ReservedName;
import com.huaixuan.network.common.util.XmlUtil;

/**
 * @author shengyong
 * @version $Id: PaipaiInterfaceManagerImpl.java,v 0.1 2011-3-8 下午03:55:32 shengyong Exp $
 */
@Service("paipaiInterfaceManager")
public class PaipaiInterfaceManagerImpl implements PaipaiInterfaceManager {

    protected final Log log             = LogFactory.getLog(this.getClass());

    protected String    IP_PORT         = "http://api.paipai.com";

    protected String    charset         = "GB18030";

    private HttpClient  httpClient      = new DefaultHttpClient();

    byte[]              responseContent = null;

    public byte[] getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(byte[] responseContent) {
        this.responseContent = responseContent;
    }

    @Autowired
    InterfaceApplyManager interfaceApplyManager;

    public JSONObject paipaiClientByJson(ApiParameter parameter, EnumPaipaiApi api,
                                         boolean needSign, Map<String, String> noSignPram,
                                         Long userId, String format)
                                                                    throws UnsupportedEncodingException,
                                                                    IOException {
        String modelName = api.getFloder();
        String actionName = api.name();

        boolean flag = invoke(IP_PORT + "/" + modelName + "/" + actionName + ".xhtml", parameter,
            needSign, noSignPram, userId, format);
        if (flag) {
            String content = new String(getResponseContent(), "GB2312");  
            JSONObject json = JSONObject.fromObject(content);
            if (json == null) {
                log.error("JSONObject解析json后返回null");
                return null;
            }
            Integer errorCode = (Integer) json.get("errorCode");
            Object errorMessage = json.get("errorMessage");
            if (errorCode != 0) {
                log.error("返回失败errorCode[" + errorCode + "] errorMessage[" + errorMessage + "]");
                return null;
            } else {
                return json;
            }
        } else {
            log.error("调用失败:" + IP_PORT + "/" + modelName + "/" + actionName + ".xhtml");
            return null;
        }
    }

    public Document paipaiClientByXml(ApiParameter parameter, EnumPaipaiApi api, boolean needSign,
                                      Map<String, String> noSignPram, Long userId, String format)
                                                                                                 throws UnsupportedEncodingException,
                                                                                                 IOException {
        String modelName = api.getFloder();
        String actionName = api.name();

        boolean flag = invoke(IP_PORT + "/" + modelName + "/" + actionName + ".xhtml", parameter,
            needSign, noSignPram, userId, format);
        if (flag) {
            Document doc = XmlUtil.createDocument(this.getResponseContent());
            if (doc == null) {
                log.error("解析XML后返回null");
                return null;
            }
            //          String errorCode = XmlUtil.getElementValueByTagName(doc, actionName, "errorCode");
            //          String errorMessage = XmlUtil.getElementValueByTagName(doc, actionName, "errorMessage");
            //          if(Integer.parseInt(errorCode) != 0){
            //              log.error("返回失败errorCode["+errorCode+"] errorMessage["+errorMessage+"]");
            //              return null;
            //          }
            return doc;
        } else {
            log.error("调用失败:" + IP_PORT + "/" + modelName + "/" + actionName + ".xhtml");
            return null;
        }
    }

    public boolean paipaiClientByTimeTask(ApiParameter parameter, EnumPaipaiApi api, Long userId,
                                          String format) throws UnsupportedEncodingException,
                                                        IOException {
        String modelName = api.getFloder();
        String actionName = api.name();

        boolean flag = invoke(IP_PORT + "/" + modelName + "/" + actionName + ".xhtml", parameter,
            true, null, userId, format);
        if (flag) {
            String content = new String(getResponseContent());
            JSONObject json = JSONObject.fromObject(content);
            if (json == null) {
                log.error("JSONObject解析json后返回null");
                return false;
            }
            Integer errorCode = (Integer) json.get("errorCode");
            Object errorMessage = json.get("errorMessage");
            if (errorCode != 0) {
                log.error("返回失败errorCode[" + errorCode + "] errorMessage[" + errorMessage + "]");
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean invoke(String apiUrl, ApiParameter parameter, boolean needSign,
                          Map<String, String> noSignPram, Long userId, String format)
                                                                                     throws UnsupportedEncodingException,
                                                                                     IOException {
        TreeMap signParams = new TreeMap();

        if (needSign) {
            if (userId == null) {
                log.error("关联拍拍的用户不存在!");
                return false;
            }

            InterfaceApply interfaceApply = this.interfaceApplyManager.getInterfaceApplyByUserId(
                userId, EnumInterfaceType.PAIPAI.getKey());
            if (interfaceApply == null) {
                log.error("关联拍拍的用户不存在!");
                return false;
            }

            Map<String, String> apiParams = new HashMap();
            apiParams.put("uin", interfaceApply.getParamOne());
            apiParams.put("token", interfaceApply.getParamTwo());
            apiParams.put("spid", interfaceApply.getParamThree());
            apiParams.put("pureData", "1");
            apiParams.put("format", format);

            int pos = apiUrl.indexOf(63);
            if (pos > 0) {
                apiUrl = apiUrl.substring(0, pos);
            }
            apiUrl = apiUrl + "?" + ReservedName.CHARSET + "=" + charset;
            signParams.put(ReservedName.CHARSET.toString(), new String[] { charset });
            HttpPost post = new HttpPost(apiUrl);
            post.addHeader("User-Agent",
                "PaiPai API Invoker/Java " + System.getProperty("java.version"));
            post.getParams().setParameter("http.protocol.expect-continue", Boolean.FALSE);
            Charset charsetObj = Charset.forName(charset);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT, null, charsetObj);
            String name;
            for (Iterator localIterator1 = parameter.getNames().iterator(); localIterator1
                .hasNext();) {
                name = (String) localIterator1.next();
                ArrayList signValues = new ArrayList();
                for (Iterator localIterator2 = parameter.getParam(name).iterator(); localIterator2
                    .hasNext();) {
                    Object value = localIterator2.next();
                    if (value instanceof String) {
                        entity.addPart(name, new StringBody((String) value, charsetObj));
                        signValues.add((String) value);
                    } else if (value instanceof File) {
                        entity.addPart(name, new FileBody((File) value));
                    } else {
                        log.error("发现不支持的参数类型。 name:" + name + " value.class:"
                                  + value.getClass().getName() + "。");
                        this.responseContent = null;
                        return false;
                    }
                }
                if (signValues.size() > 0) {
                    signParams.put(name, (String[]) signValues.toArray(new String[0]));
                }
            }

            for (Iterator localIterator1 = apiParams.keySet().iterator(); localIterator1.hasNext();) {
                name = (String) localIterator1.next();
                String value = (String) apiParams.get(name);
                entity.addPart(name, new StringBody(value, charsetObj));
                signParams.put(name, new String[] { value });
            }

            String sign = makeSign(getCmd(post.getURI().getPath()), signParams,
                interfaceApply.getParamFour(), charset, false);
            entity.addPart(ReservedName.SIGN.toString(), new StringBody(sign, charsetObj));
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                InputStream stream = responseEntity.getContent();
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = stream.read(buffer)) != -1) {
                        byteStream.write(buffer, 0, len);
                    }
                    this.responseContent = byteStream.toByteArray();
                } catch (RuntimeException e) {
                    post.abort();
                    log.error(e.getMessage());
                    return false;
                } finally {
                    stream.close();
                }
            } else {
                this.responseContent = null;
            }
        } else {
            int pos = apiUrl.indexOf(63);
            if (pos > 0) {
                apiUrl = apiUrl.substring(0, pos);
            }
            apiUrl = apiUrl + "?" + ReservedName.CHARSET + "=" + charset + "&"
                     + ReservedName.PUREDATA + "=" + "1" + "&" + ReservedName.FORMAT + "=" + format;
            Iterator noSign = noSignPram.entrySet().iterator();
            while (noSign.hasNext()) {
                Map.Entry<String, String> noSignMap = (Map.Entry<String, String>) noSign.next();
                apiUrl = apiUrl + "&" + noSignMap.getKey() + "=" + noSignMap.getValue();
            }
            HttpPost post = new HttpPost(apiUrl);
            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                InputStream stream = responseEntity.getContent();
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = stream.read(buffer)) != -1) {
                        byteStream.write(buffer, 0, len);
                    }
                    this.responseContent = byteStream.toByteArray();
                } catch (RuntimeException e) {
                    post.abort();
                    log.error(e.getMessage());
                    return false;
                } finally {
                    stream.close();
                }
            } else {
                this.responseContent = null;
            }
        }
        return true;
    }

    public String makeSign(String cmdId, TreeMap<String, String[]> signParams, String secretKey,
                           String charset, boolean debug) throws UnsupportedEncodingException,
                                                         IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byteStream.write(cmdId.getBytes(charset));
        for (String key : signParams.keySet()) {
            byteStream.write(key.getBytes(charset));
            for (String value : (String[]) signParams.get(key)) {
                byteStream.write(value.getBytes(charset));
            }
        }
        byteStream.write(secretKey.getBytes(charset));
        byte[] array = byteStream.toByteArray();
        return Md5Util.makeMd5Sum(array);
    }

    public String getCmd(String requestURI) {
        byte[] uri = requestURI.getBytes();
        int posStart = 0;
        while (uri[posStart] == 47) {
            ++posStart;
        }
        int posEnd = posStart;
        int index = posStart;
        while (index < uri.length) {
            if (uri[index] == 47) {
                uri[index] = 46;
            } else if (uri[index] == 46) {
                posEnd = index;
            }
            ++index;
        }
        return new String(uri, posStart, posEnd - posStart);
    }
}
