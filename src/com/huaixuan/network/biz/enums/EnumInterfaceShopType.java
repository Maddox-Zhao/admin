/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-5-26
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shengyong
 * @version $Id: EnumInterfaceShopType.java,v 0.1 2011-5-26 下午03:04:24 shengyong Exp $
 */
public enum EnumInterfaceShopType {
    USER("user", "个人店"), SELF("self", "自营店");
    private String code;

    private String name;

    EnumInterfaceShopType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(USER.getKey(), USER.getValue());
        enumDataMap.put(SELF.getKey(), SELF.getValue());
        return enumDataMap;
    }

}
