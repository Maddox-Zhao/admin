package com.huaixuan.network.common.util.remote;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.TaobaoRestClient;
import com.taobao.api.TaobaoXmlRestClient;
import com.taobao.api.model.ItemUpdateRequest;
import com.taobao.api.model.ItemUpdateResponse;
import com.taobao.api.model.SkuUpdateRequest;
import com.taobao.api.model.SkuUpdateResponse;

/***
 *
 * @author jinxx
 *
 */
public class TaobaoSyncUtils {
    protected static Log logger = LogFactory.getLog(TaobaoSyncUtils.class);

    /**
     *
     * @param num(库存数量 必须是等于或大于0)
     * @param numId(淘宝商品的num_iid 比如：6000167509)
     * @param properties(商品的属性:比如:1627207:3232484)
     * @param appKey(淘宝的appkey)
     * @param appSecret(淘宝的appSecret)
     * @param nick淘宝昵称(沙箱环境下需要)
     * @param isSandBox(false:正式环境 true:沙箱环境)
     * @return Map<String,Object> 封装错误信息
     */
    public static Map<String, Object> taobaoClientByTimeTask(Integer num, String numId,
                                                             String properties, String appKey,
                                                             String appSecret, String nick,
                                                             boolean isSandBox) {
        if (properties == null || properties.trim().equals("")) {
            return taobaoClientByTimeTask(num, numId, appKey, appSecret, nick, isSandBox);
        }
        Map<String, Object> parMap = new HashMap<String, Object>();
        TaobaoRestClient client;
        try {
            client = new TaobaoXmlRestClient(appKey, appSecret, isSandBox);
            SkuUpdateRequest req = new SkuUpdateRequest();
            req.setNumIid(numId);
            req.setProperties(properties);
            req.setQuantity(num.toString());
            SkuUpdateResponse rsp = client.skuUpdate(req, TaobaoUtils.getTestSession(nick, appKey));
            if (rsp.getErrorCode() != null) {
                String errorMsg = rsp.getSubMsg() == null ? rsp.getMsg() : rsp.getSubMsg();
                parMap.put("state", "false");
                if (errorMsg != null) {
                    parMap.put("msg", "用户提供的信息不正确" + errorMsg);
                } else {
                    parMap.put("msg", "未知错误");
                }
            } else {
                parMap.put("state", "true");
            }
        } catch (TaobaoApiException e) {
            parMap.put("state", "false");
            parMap.put("msg", "更新淘宝出现异常...！");
            logger.error("更新淘宝出现异常..." + e.getMessage());
        }
        return parMap;
    }

    /**
     *
     * @param num(库存数量 必须是等于或大于0)
     * @param numId(淘宝商品的num_iid 比如：6000167509)
     * @param appKey(淘宝的appkey)
     * @param appSecret(淘宝的appSecret)
     * @param nick淘宝昵称(沙箱环境下需要)
     * @param isSandBox(false:正式环境 true:沙箱环境)
     * @return Map<String,Object> 封装错误信息
     */
    public static Map<String, Object> taobaoClientByTimeTask(Integer num, String numId,
                                                             String appKey, String appSecret,
                                                             String nick, boolean isSandBox) {
        Map<String, Object> parMap = new HashMap<String, Object>();
        TaobaoRestClient client;
        try {
            client = new TaobaoXmlRestClient(appKey, appSecret, isSandBox);
            ItemUpdateRequest req = new ItemUpdateRequest();
            req.setNumIid(numId);
            req.setNum(num);
            ItemUpdateResponse rsp = client.itemUpdate(req,
                TaobaoUtils.getTestSession(nick, appKey));
            if (rsp.getErrorCode() != null) {
                String errorMsg = rsp.getSubMsg() == null ? rsp.getMsg() : rsp.getSubMsg();
                parMap.put("state", "false");
                if (errorMsg != null) {
                    parMap.put("msg", "用户提供的信息不正确" + errorMsg);
                } else {
                    parMap.put("msg", "未知错误");
                }
            } else {
                parMap.put("state", "true");
            }
        } catch (TaobaoApiException e) {
            parMap.put("state", "false");
            parMap.put("msg", "更新淘宝出现异常...！");
            logger.error("更新淘宝出现异常..." + e.getMessage());
        }
        return parMap;
    }
}
