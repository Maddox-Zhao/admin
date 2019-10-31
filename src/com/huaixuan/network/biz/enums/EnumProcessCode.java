package com.huaixuan.network.biz.enums;

/**
 * 增删改操作后提示给view页面的code
 * @author zhanglong 账户类型
 */
public enum EnumProcessCode {
    SEARCH_OK("search_ok", "搜索成功"), 
    SEARCH_ERROR("search_error", "搜索失败"),
    PROCESS_OK("process_ok", "操作成功"), 
    PROCESS_ERROR("process_error", "操作失败");

    private String code;

    private String name;

    EnumProcessCode(String code, String name) {
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
