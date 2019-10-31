package com.huaixuan.network.biz.domain;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Withdraw {
    private Long   id;
    private Long   userId;
    private String accountNo;
    private String bankType;
    private String bankBranch;
    private String status;
    private long   transAmount;
    private Date   gmtApply;
    private Date   gmtToBank;
    private Date   gmtBankAck;
    private Date   gmtModified;
    private String failMemo;
    private String operator;
    private String memo;
    private String transAmountStr;
    private String amount;
    //begin add by kevin_gao 2010-03-02
    private String bankName;//开户名
    private String bankAccountNo;//开户账号
    //end add by kevin_gao 2010-03-02
    
    //begin add by lilei 2010-04-29
    private String withdrawalDesc;//提现银行网点
    //end add by lilei 2010-04-29
    //    private String pointStr;
    //    private String securityBalance;

    public String getTransAmountStr() {
        return transAmountStr;
    }

    public String getWithdrawalDesc() {
		return withdrawalDesc;
	}

	public void setWithdrawalDesc(String withdrawalDesc) {
		this.withdrawalDesc = withdrawalDesc;
	}

	public void setTransAmountStr(String transAmountStr) {
        this.transAmountStr = transAmountStr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtApply() {
        return gmtApply;
    }

    public void setGmtApply(Date gmtApply) {
        this.gmtApply = gmtApply;
    }

    public Date getGmtToBank() {
        return gmtToBank;
    }

    public void setGmtToBank(Date gmtToBank) {
        this.gmtToBank = gmtToBank;
    }

    public Date getGmtBankAck() {
        return gmtBankAck;
    }

    public void setGmtBankAck(Date gmtBankAck) {
        this.gmtBankAck = gmtBankAck;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getFailMemo() {
        return failMemo;
    }

    public void setFailMemo(String failMemo) {
        this.failMemo = failMemo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(long transAmount) {
        this.transAmount = transAmount;
    }
    //begin add by kevin_gao 2010-03-02
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
    //end add by kevin_gao 2010-03-02
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-956988245, -426363991).appendSuper(super.hashCode()).append(
            this.gmtModified).append(this.transAmount).append(this.memo).append(this.status)
            .append(this.failMemo).append(this.accountNo).append(this.gmtBankAck).append(
                this.operator).append(this.amount).append(this.transAmountStr).append(this.id)
            .append(this.gmtToBank).append(this.bankType).append(this.gmtApply).append(this.userId)
            .append(this.bankBranch).append(this.bankName).append(this.bankAccountNo).toHashCode();
    }

}
