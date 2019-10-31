package com.huaixuan.network.biz.enums;

public enum EnumDepLocationIsCheck {

	Y("Y", "盘点中"),
    N("N","不在盘点");

    private String code;
    private String name;

    EnumDepLocationIsCheck(String code, String name) {
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
