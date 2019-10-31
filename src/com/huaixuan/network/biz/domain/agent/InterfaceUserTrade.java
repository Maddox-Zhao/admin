/*
 * Hundsun Inc.
 * Copyright (c) 2006-2009 All Rights Reserved.
 *
 * Author     :shengyong
 * Version    :1.0
 * Create Date:2011-3-7
 */
package com.huaixuan.network.biz.domain.agent;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author shengyong
 * @version $Id: InterfaceUserTrade.java,v 0.1 2011-3-7 ����04:54:46 shengyong Exp $
 */
public class InterfaceUserTrade implements Serializable {

    private static final long serialVersionUID = 3010431851199982677L;
    /* @property:���ID */
    private Long   id;
    /* @property:����ʱ�� */
    private Date   gmtCreate;
    /* @property:�޸�ʱ�� */
    private Date   gmtModify;
    /* @property:�û�ID */
    private Long   userId;
    /* @property:�̳Ƕ���TID */
    private String tradeId;
    /* @property:���ĵĶ���ID */
    private String paipaiTradeId;
    /* @property:���ĵĶ������ʱ�� */
    private Date   gmtPaipaiCreate;
    /* @property:���ĵĶ���֧��ʱ�� */
    private Date   gmtPaipaiPaied;
    /* @property:����ͬ���쳣���⣺�����޿��֮��� */
    private String reason;

    private String userName;

    /* Default constructor - creates a new instance with no values set. */
    public InterfaceUserTrade() {
    }

    /* @model:�������ID */
    public void setId(Long obj) {
        this.id = obj;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /* @model:��ȡ���ID */
    public Long getId() {
        return this.id;
    }

    /* @model:���ô���ʱ�� */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:��ȡ����ʱ�� */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:�����޸�ʱ�� */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:��ȡ�޸�ʱ�� */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /* @model:�����û�ID */
    public void setUserId(Long obj) {
        this.userId = obj;
    }

    /* @model:��ȡ�û�ID */
    public Long getUserId() {
        return this.userId;
    }

    /* @model:�����̳Ƕ���TID */
    public void setTradeId(String obj) {
        this.tradeId = obj;
    }

    /* @model:��ȡ�̳Ƕ���TID */
    public String getTradeId() {
        return this.tradeId;
    }

    /* @model:�������ĵĶ���ID */
    public void setPaipaiTradeId(String obj) {
        this.paipaiTradeId = obj;
    }

    /* @model:��ȡ���ĵĶ���ID */
    public String getPaipaiTradeId() {
        return this.paipaiTradeId;
    }

    /* @model:�������ĵĶ������ʱ�� */
    public void setGmtPaipaiCreate(Date obj) {
        this.gmtPaipaiCreate = obj;
    }

    /* @model:��ȡ���ĵĶ������ʱ�� */
    public Date getGmtPaipaiCreate() {
        return this.gmtPaipaiCreate;
    }

    /* @model:�������ĵĶ���֧��ʱ�� */
    public void setGmtPaipaiPaied(Date obj) {
        this.gmtPaipaiPaied = obj;
    }

    /* @model:��ȡ���ĵĶ���֧��ʱ�� */
    public Date getGmtPaipaiPaied() {
        return this.gmtPaipaiPaied;
    }

    /* @model:���ö���ͬ���쳣���⣺�����޿��֮��� */
    public void setReason(String obj) {
        this.reason = obj;
    }

    /* @model:��ȡ����ͬ���쳣���⣺�����޿��֮��� */
    public String getReason() {
        return this.reason;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterfaceUserTrade)) {
            return false;
        }
        final InterfaceUserTrade interfaceusertrade = (InterfaceUserTrade) o;
        return this.hashCode() == interfaceusertrade.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
            .append("id", this.id).append("gmtCreate", this.gmtCreate)
            .append("gmtModify", this.gmtModify).append("userId", this.userId)
            .append("tradeId", this.tradeId).append("paipaiTradeId", this.paipaiTradeId)
            .append("gmtPaipaiCreate", this.gmtPaipaiCreate)
            .append("gmtPaipaiPaied", this.gmtPaipaiPaied).append("reason", this.reason);
        return sb.toString();
    }
}
