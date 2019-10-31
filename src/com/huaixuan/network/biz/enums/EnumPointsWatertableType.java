package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分流水表中type的类型
 * 
 * @author sunfj
 * @version $Id: EnumPointsWatertableType.java,v 0.1 2009-6-17 下午03:04:33 wb_fujun.sunfj Exp $
 */
public enum EnumPointsWatertableType {
    ORDER("order", "下单"), PAY("pay", "支付"), CANCEL_ORDER("cancelOrder", "取消订单"), TRANSACTION(
                                                                                                               "transaction",
                                                                                                               "交易成功");

    private String code;

    private String name;

    EnumPointsWatertableType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(ORDER.getKey(), ORDER.getValue());
        enumDataMap.put(PAY.getKey(), PAY.getValue());
        enumDataMap.put(CANCEL_ORDER.getKey(), CANCEL_ORDER.getValue());
        enumDataMap.put(TRANSACTION.getKey(), TRANSACTION.getValue());
        
        return enumDataMap;
    }

}
