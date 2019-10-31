package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
* @author hongxh create on 2011-6-27
*/
public enum EnumCabinetType {

    INDEX_CABINET(1L,"首页橱窗"),
    RECOMMENTD_CABINET(2L,"新品上架"),
    NEWPRODUCT_CABINET(3L,"新品推荐");

    private Long code;
    private String name;
    EnumCabinetType(Long code,String name){
        this.code=code;
        this.name=name;
    }
    public Long getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<Long, String> toMap() {
        Map<Long, String> enumDataMap = new HashMap<Long, String>();
        enumDataMap.put(INDEX_CABINET.getKey(), INDEX_CABINET.getValue());
        enumDataMap.put(RECOMMENTD_CABINET.getKey(), RECOMMENTD_CABINET.getValue());
        enumDataMap.put(NEWPRODUCT_CABINET.getKey(), NEWPRODUCT_CABINET.getValue());

        return enumDataMap;
    }
}
