package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接入申请状态
 * 
 * @author fanyj
 */
public enum EnumInterfaceApplyStatus {
    NEW("new","待审核"),
    PASS("pass","审核通过"),
    FAIL("fail","审核不通过");

    private String code;

    private String name;

    EnumInterfaceApplyStatus(String code,String name){
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
        enumDataMap.put(NEW.getKey(), NEW.getValue());
        enumDataMap.put(PASS.getKey(), PASS.getValue());
        enumDataMap.put(FAIL.getKey(), FAIL.getValue());
        return enumDataMap;
    }
}
