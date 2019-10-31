package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口类型
 * 
 * @author zhangwy
 */
public enum EnumInterfaceType {
    PAIPAI("paipai","拍拍"),TAOBAO("taobao","淘宝");

    private String code;

    private String name;

    EnumInterfaceType(String code,String name){
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
        enumDataMap.put(PAIPAI.getKey(), PAIPAI.getValue());
        enumDataMap.put(TAOBAO.getKey(), TAOBAO.getValue());
        return enumDataMap;
    }
}
