package com.huaixuan.network.biz.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * 用户等级划分
 * @author fangqing
 * @version $Id: EnumUserRank.java,v 0.1 2009-12-24 下午05:05:12  Exp $
 */
public enum EnumUserRank {

	GRADE_1("heart-1","一星"),
	GRADE_2("heart-2","二星"),
	GRADE_3("heart-3","三星"),
	GRADE_4("heart-4","四星"),
	GRADE_5("heart-5","五星"),
    GRADE_6("diamond-1","一钻"),
    GRADE_7("diamond-2","二钻"),
    GRADE_8("diamond-3","三钻"),
    GRADE_9("diamond-4","四钻"),
    GRADE_10("diamond-5","五钻"),
    GRADE_11("crown-1","一皇冠"),
    GRADE_12("crown-2","二皇冠"),
    GRADE_13("crown-3","三皇冠"),
    GRADE_14("crown-4","四皇冠"),
    GRADE_15("crown-5","五皇冠"),
    GRADE_16("16","一紫冠"),
    GRADE_17("17","二紫冠"),
    GRADE_18("18","三紫冠"),
    GRADE_19("19","四紫冠"),
    GRADE_20("20","五紫冠");


    private String code;
    private String name;
//    private int index;

    EnumUserRank(String code, String name) {
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
