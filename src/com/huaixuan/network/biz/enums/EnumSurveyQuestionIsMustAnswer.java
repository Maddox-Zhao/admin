package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷分类
 * @author chenhang 2010/10/26
 *
 */
public enum EnumSurveyQuestionIsMustAnswer {
    NOT("0","可答"),
    IS("1","必答");

    private String code;

    private String name;

    EnumSurveyQuestionIsMustAnswer(String code,String name){
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
        enumDataMap.put(NOT.getKey(), NOT.getValue());
        enumDataMap.put(IS.getKey(),IS.getValue());

        return enumDataMap;
    }
}
