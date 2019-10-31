package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumTradeWholesaleStatus {
	Trade_WholesaleStatus_n("confirmed","已确认"),
	Trade_WholesaleStatus_w("wait_confirm","待确认");
    private String code;
    private String name;
    EnumTradeWholesaleStatus(String code,String name){
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
        enumDataMap.put(Trade_WholesaleStatus_n.getKey(), Trade_WholesaleStatus_n.getValue());
        enumDataMap.put(Trade_WholesaleStatus_w.getKey(), Trade_WholesaleStatus_w.getValue());
        return enumDataMap;
    }
}
