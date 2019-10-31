package com.huaixuan.network.biz.enums;

public enum EnumAdminUserStatus {
    FREEZING(0, "用户被冻结"), USING(1, "可使用的");

    private EnumAdminUserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int    value;
    private String desc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 
     */
    public static boolean isEnabled(int status) {
        return USING.getValue() == status;
    }
}
