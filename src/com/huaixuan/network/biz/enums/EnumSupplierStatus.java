/**
 * created since 2009-8-12
 */
package com.huaixuan.network.biz.enums;

/**
 * @author shengyong
 * @version $Id: EnumSupplierStatus.java,v 0.1 2009-8-12 下午02:47:16 shengyong Exp $
 */
public enum EnumSupplierStatus {
    VALID("v", "正常"), INVALID("d", "失效");

    private String code;
    private String name;

    EnumSupplierStatus(String code, String name) {
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
