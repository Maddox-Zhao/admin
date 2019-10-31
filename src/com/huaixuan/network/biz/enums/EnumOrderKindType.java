package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;


public enum EnumOrderKindType {
	Order_Kind_Type_n("d","代销"),
	Order_Kind_Type_w("w","批发兼代销"),
	Order_Kind_Type_p("p","批发");
    private String code;
    private String name;
    EnumOrderKindType(String code,String name){
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
        enumDataMap.put(Order_Kind_Type_n.getKey(), Order_Kind_Type_n.getValue());
        //enumDataMap.put(Order_Kind_Type_w.getKey(), Order_Kind_Type_w.getValue());
        enumDataMap.put(Order_Kind_Type_p.getKey(), Order_Kind_Type_p.getValue());
        enumDataMap.put(Order_Kind_Type_w.getKey(), Order_Kind_Type_w.getValue());
        return enumDataMap;
    }
}

