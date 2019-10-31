package com.huaixuan.network.biz.domain.base;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.BaseObject;


public class BaseFreezeBalanceLog extends BaseObject implements Serializable {
    private static final long serialVersionUID = 734335845935747090L;
    private long id;
    private String accountNo;
    private String actionType;
    private long freezeAmount;
    private long balance;
    private long freezeBalance;
    private String actionReason;
    private String operator;
    private Date gmtCreate;
    private String freezeMemo;
    private long relatedTransLog;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getActionType() {
        return actionType;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    public long getFreezeAmount() {
        return freezeAmount;
    }
    public void setFreezeAmount(long freezeAmount) {
        this.freezeAmount = freezeAmount;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    public long getFreezeBalance() {
        return freezeBalance;
    }
    public void setFreezeBalance(long freezeBalance) {
        this.freezeBalance = freezeBalance;
    }
    public String getActionReason() {
        return actionReason;
    }
    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public Date getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public String getFreezeMemo() {
        return freezeMemo;
    }
    public void setFreezeMemo(String freezeMemo) {
        this.freezeMemo = freezeMemo;
    }
    public long getRelatedTransLog() {
        return relatedTransLog;
    }
    public void setRelatedTransLog(long relatedTransLog) {
        this.relatedTransLog = relatedTransLog;
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(699016221, -2133735507).appendSuper(super.hashCode()).append(
            this.id).append(this.balance).append(this.gmtCreate).append(this.freezeAmount).append(
            this.freezeMemo).append(this.freezeBalance).append(this.accountNo).append(
            this.actionType).append(this.relatedTransLog).append(this.actionReason).append(
            this.operator).toHashCode();
    }

}
