package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 问卷分类
 * @author chenhang 2010/10/26
 *
 */
public enum EnumSurveyType {
	
    MARKETSURVEY("m","市场问卷调查"),
    USERSATIFIEDSURVEY("u","用户满意度调查");
        
    private String code;

    private String name;

    EnumSurveyType(String code,String name){
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
        enumDataMap.put(MARKETSURVEY.getKey(), MARKETSURVEY.getValue());
        enumDataMap.put(USERSATIFIEDSURVEY.getKey(),USERSATIFIEDSURVEY.getValue());

        return enumDataMap;
    }
}
