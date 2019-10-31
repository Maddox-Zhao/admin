package com.huaixuan.network.biz.domain.base.account;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.BaseObject;

public class BaseAccountLog extends BaseObject implements Serializable {
    private static final long serialVersionUID = -7549765637358536465L;
    private long id;
	private long transLogId;
	private String transDate;
    private String outDate;
    private Date transDt;
    private String transCode;
    private String subTransCode;
    private long transAmount;
    private long balance;
    private long freezeBalance;
    private long accountType;
    private String transAccount;
    private long otherAccountType;
    private String otherAccount;
    private String transInstitution;
    private String transOutOrderNo;
    private String bankType;
    private String transMemo;
    private String transOperator;
    private String transTerminal;
    
	public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getTransLogId() {
        return transLogId;
    }
    public void setTransLogId(long transLogId) {
        this.transLogId = transLogId;
    }
    public String getTransDate() {
        return transDate;
    }
    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
    public String getOutDate() {
        return outDate;
    }
    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }
    public Date getTransDt() {
        return transDt;
    }
    public void setTransDt(Date transDt) {
        this.transDt = transDt;
    }
    public String getTransCode() {
        return transCode;
    }
    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }
    public String getSubTransCode() {
        return subTransCode;
    }
    public void setSubTransCode(String subTransCode) {
        this.subTransCode = subTransCode;
    }
    public long getTransAmount() {
        return transAmount;
    }
    public void setTransAmount(long transAmount) {
        this.transAmount = transAmount;
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
    public long getAccountType() {
        return accountType;
    }
    public void setAccountType(long accountType) {
        this.accountType = accountType;
    }
    public String getTransAccount() {
        return transAccount;
    }
    public void setTransAccount(String transAccount) {
        this.transAccount = transAccount;
    }
    public long getOtherAccountType() {
        return otherAccountType;
    }
    public void setOtherAccountType(long otherAccountTyp) {
        this.otherAccountType = otherAccountTyp;
    }
    public String getOtherAccount() {
        return otherAccount;
    }
    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }
    public String getTransInstitution() {
        return transInstitution;
    }
    public void setTransInstitution(String transInstitution) {
        this.transInstitution = transInstitution;
    }
    public String getTransOutOrderNo() {
        return transOutOrderNo;
    }
    public void setTransOutOrderNo(String transOutOrderNo) {
        this.transOutOrderNo = transOutOrderNo;
    }
    public String getBankType() {
        return bankType;
    }
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    public String getTransMemo() {
        return transMemo;
    }
    public void setTransMemo(String transMemo) {
        this.transMemo = transMemo;
    }
    public String getTransOperator() {
        return transOperator;
    }
    public void setTransOperator(String transOperator) {
        this.transOperator = transOperator;
    }
    public String getTransTerminal() {
        return transTerminal;
    }
    public void setTransTerminal(String transTerminal) {
        this.transTerminal = transTerminal;
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-401333917, 891812781).appendSuper(super.hashCode()).append(
            this.transAmount).append(this.otherAccountType).append(this.transLogId).append(
            this.transOperator).append(this.transDt).append(this.transInstitution).append(
            this.outDate).append(this.transMemo).append(this.transAccount).append(this.id).append(
            this.transTerminal).append(this.transDate).append(this.balance).append(
            this.transOutOrderNo).append(this.otherAccount).append(this.bankType).append(
            this.freezeBalance).append(this.accountType).append(this.subTransCode).append(
            this.transCode).toHashCode();
    }
}
