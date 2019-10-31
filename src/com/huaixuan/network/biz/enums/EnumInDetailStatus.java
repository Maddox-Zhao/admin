package com.huaixuan.network.biz.enums;

public enum EnumInDetailStatus {

	IN_NEW("in_new", "新建"),
    IN_FINISHED("in_finished","完成");

    private String code;
    private String name;

    EnumInDetailStatus(String code, String name) {
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
