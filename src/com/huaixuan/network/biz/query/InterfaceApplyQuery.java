/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-9
 */
package com.huaixuan.network.biz.query;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author shengyong
 * @version $Id: InterfaceApplyQuery.java,v 0.1 2011-3-9 ÏÂÎç03:08:02 shengyong Exp $
 */
public class InterfaceApplyQuery extends BaseObject implements Serializable {

    private static final long serialVersionUID = -6813242151948748156L;

    private String            paramOne;
    private String            account;
    private String            status;
    private String            gmtCreateStart;
    private String            gmtCreateEnd;
    private String            type;
    private String            shopType;

    public String getParamOne() {
        return paramOne;
    }

    public void setParamOne(String paramOne) {
        this.paramOne = paramOne;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

}
