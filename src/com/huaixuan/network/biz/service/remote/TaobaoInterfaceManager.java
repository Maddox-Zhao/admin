package com.huaixuan.network.biz.service.remote;

import java.text.ParseException;
import java.util.Map;

import com.taobao.api.TaobaoApiException;
import com.taobao.api.model.ItemAddRequest;

public interface TaobaoInterfaceManager {
    public Map<String, String> taobaoGoodsAdd(ItemAddRequest req, long userId, String sessionKey,
                                              boolean isSandBox) throws TaobaoApiException,
                                                                ParseException;

    public boolean taobaoItemGet(String numIid, long userId, boolean isSandbox)
                                                                               throws TaobaoApiException;
}