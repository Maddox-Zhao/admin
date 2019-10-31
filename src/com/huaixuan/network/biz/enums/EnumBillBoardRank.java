package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumBillBoardRank {

    HOT_SALE("hot_sale","1"),
    HIGH_POPULAR("high_popular","2");

    private String code;
    private String name;
    EnumBillBoardRank(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<String , String> toMap(){
        Map<String , String> enumDataMap=new HashMap<String, String>();
        enumDataMap.put(HOT_SALE.getKey(), HOT_SALE.getValue());
        enumDataMap.put(HIGH_POPULAR.getKey(), HIGH_POPULAR.getValue());
        return enumDataMap;
    }
}
