package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 库存类型枚举类
 * @author chenyan 2010/03/04
 *
 */
public enum EnumStorType {
    STOR_TYPE_V("v","暂估"),
    STOR_TYPE_F("f","实际");

    private String code;
    private String name;
    EnumStorType(String code,String name){
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
        enumDataMap.put(STOR_TYPE_V.getKey(), STOR_TYPE_V.getValue());
        enumDataMap.put(STOR_TYPE_F.getKey(), STOR_TYPE_F.getValue());
        return enumDataMap;
    }
}
