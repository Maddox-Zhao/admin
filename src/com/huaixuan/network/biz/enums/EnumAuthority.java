package com.huaixuan.network.biz.enums;

public enum EnumAuthority {

    STOCK_MODIFY(10, "A_STOCK_MODIFY_USER", "采购修改权限");

    private EnumAuthority(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    private String name;
    private String desc;
    private int    code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
