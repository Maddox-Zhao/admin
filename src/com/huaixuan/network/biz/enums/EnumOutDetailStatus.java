package com.huaixuan.network.biz.enums;

public enum EnumOutDetailStatus {

	OUT_NEW("out_new", "新建"),
    OUT_FINISHED("out_finished","完成");

    private String code;
    private String name;

    EnumOutDetailStatus(String code, String name) {
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
