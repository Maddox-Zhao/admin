package com.huaixuan.network.biz.enums;

/**
 * 积分流水表中outer_type
 * 
 * @author sunfj
 * @version $Id: EnumPointsWatertableOutType.java,v 0.1 2009-6-17 下午03:22:22 wb_fujun.sunfj Exp $
 */
public enum EnumPointsWatertableOuterType {

    TRANSACTION("transaction", "交易"), ACTIVITY("activity", "活动");

    private String code;

    private String name;

    EnumPointsWatertableOuterType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }
}
