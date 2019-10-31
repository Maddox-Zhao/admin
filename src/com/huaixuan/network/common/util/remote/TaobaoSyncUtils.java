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
     * @param num(������� �����ǵ��ڻ����0)
     * @param numId(�Ա���Ʒ��num_iid ���磺6000167509)
     * @param properties(��Ʒ������:����:1627207:3232484)
     * @param appKey(�Ա���appkey)
     * @param appSecret(�Ա���appSecret)
     * @param nick�Ա��ǳ�(ɳ�价������Ҫ)
     * @param isSandBox(false:��ʽ���� true:ɳ�价��)
     * @return Map<String,Object> ��װ������Ϣ
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
                    parMap.put("msg", "�û��ṩ����Ϣ����ȷ" + errorMsg);
                } else {
                    parMap.put("msg", "δ֪����");
                }
            } else {
                parMap.put("state", "true");
            }
        } catch (TaobaoApiException e) {
            parMap.put("state", "false");
            parMap.put("msg", "�����Ա������쳣...��");
            logger.error("�����Ա������쳣..." + e.getMessage());
        }
        return parMap;
    }

    /**
     *
     * @param num(������� �����ǵ��ڻ����0)
     * @param numId(�Ա���Ʒ��num_iid ���磺6000167509)
     * @param appKey(�Ա���appkey)
     * @param appSecret(�Ա���appSecret)
     * @param nick�Ա��ǳ�(ɳ�价������Ҫ)
     * @param isSandBox(false:��ʽ���� true:ɳ�价��)
     * @return Map<String,Object> ��װ������Ϣ
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
                    parMap.put("msg", "�û��ṩ����Ϣ����ȷ" + errorMsg);
                } else {
                    parMap.put("msg", "δ֪����");
                }
            } else {
                parMap.put("state", "true");
            }
        } catch (TaobaoApiException e) {
            parMap.put("state", "false");
            parMap.put("msg", "�����Ա������쳣...��");
            logger.error("�����Ա������쳣..." + e.getMessage());
        }
        return parMap;
    }
}
