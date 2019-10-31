package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 淘宝商品上下架
 *
 * @author jinxx
 */
public enum EnumTaobaoGoodsType {
    ONSALE("onsale","出售中"),INSTOCK("instock","库中");

    private String code;

    private String name;

    EnumTaobaoGoodsType(String code,String name){
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
        enumDataMap.put(ONSALE.getKey(), ONSALE.getValue());
        enumDataMap.put(INSTOCK.getKey(), INSTOCK.getValue());
        return enumDataMap;
    }
}
