/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumAccountTranCode {

    ONLINE_PAY("600201","在线支付"),
    ONLINE_RECHARGE("400301","在线充值"),
    REFUND("301101","退货退款");


    private String code;

    private String name;

    EnumAccountTranCode(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(ONLINE_PAY.getKey(), ONLINE_PAY.getValue());
        enumDataMap.put(ONLINE_RECHARGE.getKey(), ONLINE_RECHARGE.getValue());
        enumDataMap.put(REFUND.getKey(), REFUND.getValue());
        return enumDataMap;
    }
}
