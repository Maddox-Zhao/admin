package com.huaixuan.network.biz.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * 用户等级划分
 * @author fanyj
 * @version $Id: EnumUserGrade.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumUserGrade  {
    GRADE_1("1","100"), GRADE_2("2","400"), GRADE_3("3","900"), GRADE_4("4","1500"), GRADE_5("5","2500"),
    GRADE_6("6","5000"), GRADE_7("7","10000"), GRADE_8("8","20000"), GRADE_9("9","50000"), GRADE_10("10","100000"),
    GRADE_11("11","200000"), GRADE_12("12","500000"), GRADE_13("13","1000000"), GRADE_14("14","2000000"), GRADE_15("15","5000000"),
    GRADE_16("16","10000000"), GRADE_17("17","20000000"), GRADE_18("18","50000000"), GRADE_19("19","100000000"), GRADE_20("20","999999999");

    private String code;
    private String name;
//    private int index;

    EnumUserGrade(String code, String name) {
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
        Map<String, String> enumDataMap = new TreeMap<String, String>();

        enumDataMap.put(GRADE_1.getKey(), GRADE_1.getValue());
        enumDataMap.put(GRADE_2.getKey(), GRADE_2.getValue());
        enumDataMap.put(GRADE_3.getKey(), GRADE_3.getValue());
        enumDataMap.put(GRADE_4.getKey(), GRADE_4.getValue());
        enumDataMap.put(GRADE_5.getKey(), GRADE_5.getValue());
        enumDataMap.put(GRADE_6.getKey(), GRADE_6.getValue());
        enumDataMap.put(GRADE_7.getKey(), GRADE_7.getValue());
        enumDataMap.put(GRADE_8.getKey(), GRADE_8.getValue());
        enumDataMap.put(GRADE_9.getKey(), GRADE_9.getValue());
        enumDataMap.put(GRADE_10.getKey(), GRADE_10.getValue());
        enumDataMap.put(GRADE_11.getKey(), GRADE_11.getValue());
        enumDataMap.put(GRADE_12.getKey(), GRADE_12.getValue());
        enumDataMap.put(GRADE_13.getKey(), GRADE_13.getValue());
        enumDataMap.put(GRADE_14.getKey(), GRADE_14.getValue());
        enumDataMap.put(GRADE_15.getKey(), GRADE_15.getValue());
        enumDataMap.put(GRADE_16.getKey(), GRADE_16.getValue());
        enumDataMap.put(GRADE_17.getKey(), GRADE_17.getValue());
        enumDataMap.put(GRADE_18.getKey(), GRADE_18.getValue());
        enumDataMap.put(GRADE_19.getKey(), GRADE_19.getValue());
        enumDataMap.put(GRADE_20.getKey(), GRADE_20.getValue());
        
        return enumDataMap;
    }

}
