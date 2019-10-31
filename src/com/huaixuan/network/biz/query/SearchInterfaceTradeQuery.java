/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-8
 */
package com.huaixuan.network.biz.query;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author shengyong
 * @version $Id: SearchInterfaceTradeQuery.java,v 0.1 2011-3-8 ÏÂÎç01:12:57 shengyong Exp $
 */
public class SearchInterfaceTradeQuery extends BaseObject implements Serializable {

    private static final long serialVersionUID = -6480197941013653872L;
    private String userName;
    private String tradeId;
    private String interfaceCode;
    private String gmtCreateStart;
    private String gmtCreateEnd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getGmtCreateStart() {
        return gmtCreateStart;
    }

    public void setGmtCreateStart(String gmtCreateStart) {
        this.gmtCreateStart = gmtCreateStart;
    }

    public String getGmtCreateEnd() {
        return gmtCreateEnd;
    }

    public void setGmtCreateEnd(String gmtCreateEnd) {
        this.gmtCreateEnd = gmtCreateEnd;
    }

}
