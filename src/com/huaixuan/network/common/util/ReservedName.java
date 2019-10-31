/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.common.util;

/**
 * @author shengyong
 * @version $Id: ReservedName.java,v 0.1 2011-3-8 обнГ03:53:41 shengyong Exp $
 */
public enum ReservedName {
    CHARSET("charset"), FORMAT("format"), PUREDATA("pureData"), DEBUG("debug"),

    UIN("uin"), SPID("spid"), TOKEN("token"), SKEY("skey"), SIGN("sign");

    private String desc;

    private ReservedName(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return desc;
    }
}
