package com.huaixuan.network.biz.enums;

/**
 * @author guoyj 账户状态类型
 */
public enum EnumAccountStatus {
    ENABLE_ACCOUNT("T", "正常帐户"), 
    FREEZEN_ACCOUNT("B", "冻结帐户");

    private String code;

    private String name;

    EnumAccountStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
