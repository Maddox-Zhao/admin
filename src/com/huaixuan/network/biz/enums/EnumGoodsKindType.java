package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;


public enum EnumGoodsKindType {
	Goods_Kind_Type_y("y", "代销"),
	Goods_Kind_Type_w("w","批发"),
	Goods_Kind_Type_z("z","可批可销");

    private String code;
    private String name;

    EnumGoodsKindType(String code, String name) {
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
        enumDataMap.put(Goods_Kind_Type_y.getKey(), Goods_Kind_Type_y.getValue());
        enumDataMap.put(Goods_Kind_Type_w.getKey(), Goods_Kind_Type_w.getValue());
        enumDataMap.put(Goods_Kind_Type_z.getKey(), Goods_Kind_Type_z.getValue());
        return enumDataMap;
    }
}
