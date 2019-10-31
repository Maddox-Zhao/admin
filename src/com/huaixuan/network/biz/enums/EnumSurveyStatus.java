package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷分类
 * @author chenhang 2010/10/27
 *
 */
public enum EnumSurveyStatus {
	
    PUBLISH("1","发布"),
    UNPUBLISH("0","未发布");
        
    private String code;

    private String name;

    EnumSurveyStatus(String code,String name){
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
        enumDataMap.put(PUBLISH.getKey(), PUBLISH.getValue());
        enumDataMap.put(UNPUBLISH.getKey(),UNPUBLISH.getValue());

        return enumDataMap;
    }
}
