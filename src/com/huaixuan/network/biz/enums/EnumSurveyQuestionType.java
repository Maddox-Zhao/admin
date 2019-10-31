package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷分类
 * @author chenhang 2010/10/26
 *
 */
public enum EnumSurveyQuestionType {
    RADIO("r","单项选择题"),
    EXTENDEDRADIO("exr","扩展单项选择题"),
    SELECTED("s","下拉单项选择题"),
    CHECKBOX("c","多项选择题"),
    EXTENDEDCHECKBOX("exc","扩展多项选择题"),
    QUESITON("q","问答题");

    
    private String code;

    private String name;

    EnumSurveyQuestionType(String code,String name){
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
        enumDataMap.put(RADIO.getKey(), RADIO.getValue());
        enumDataMap.put(EXTENDEDRADIO.getKey(),EXTENDEDRADIO.getValue());
        enumDataMap.put(SELECTED.getKey(),SELECTED.getValue());
        enumDataMap.put(CHECKBOX.getKey(), CHECKBOX.getValue());
        enumDataMap.put(EXTENDEDCHECKBOX.getKey(),EXTENDEDCHECKBOX.getValue());
        enumDataMap.put(QUESITON.getKey(),QUESITON.getValue());

        return enumDataMap;
    }
}
