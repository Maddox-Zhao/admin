package com.huaixuan.network.biz.domain.base.payment;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 银行订单
 *
 * @author tao.wangt
 * @version $Id: BaseBankPayOnline.java,v 0.1 2009-8-7 上午10:09:57 tao.wangt Exp
 *          $
 */
public class BaseBankPayOnline extends BaseObject {
    /**
     * 支付流水号
     */
    private Long   id;
    /**
     * 账户号
     */
    private String accountNo;
    /**
     * 支付目的 1.支付 2.充值 3.保证金充值
     */
    private int    payDest;
    /**
     * 内部订单号
     */
    private String innerBillNo;
    /**
     * 订单生成时间 类型:java.lang.String 格式:yyyymmdd
     */
    private String innerDate;
    /**
     * 订单生成时间
     */
    private Date   gmtCreate;
    /**
     * 银行类别(使用银行的字母缩写表示，如ICBC)
     */
    private String bankType;
    /**
     * 支付时间
     */
    private Date   payDate;
    /**
     * 支付类型 ENET网银支付,TMO银行汇款
     */
    private String paymentType;
    /**
     * 银行订单 每个银行有独特要求，根据银行要求特别生成
     */
    private String bankBillNo;
    /**
     * 支付金额 以分为单位
     */
    private long   payAmount;
    /**
     * 实付金额 以分为单位
     */
    private long   realAmount;
    /**
     * 支付成功标志 I:初使状态 Y:支付成功 F:支付失败
     */
    private String isSucceed;
    /**
     * 订单检查标志 Y:检查成功 N:未检查或检查失败
     */
    private String isCheck;
    /**
     * 订单检查人 program auto:系统自动确认
     */
    private String checkUser;
    /**
     * 订单检查日期
     */
    private Date   checkDate;
    /**
     * 银行交易日期(银行返回)
     */
    private String bankTransDate;
    /**
     * 银行授权号
     */
    private String bankAuthNo;
    /**
     * 银行内部订单号
     */
    private String bankSerialNo;
    /**
     * 银行错误代码
     */
    private String bankErrorCode;
    /**
     * 计数(查询接口查询次数登记)
     */
    private int    orderSeq;
    /**
     * 对账标记
     */
    private String flagCompare;
    /**
     * 订单最后修改时间
     */
    private Date   gmtModified;
    /**
     * 发送到银行的支付请求时间
     */
    private Date   gmtToBank;
    /**
     * 外部订单号
     */
    private String outAccountNo;
    /**
     * 订单通知完成标志 Y:已完成通知 N:未完成通知
     */
    private String isNotified;
    /**
     * 用户名
     */
    private String account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getPayDest() {
        return payDest;
    }

    public void setPayDest(int payDest) {
        this.payDest = payDest;
    }

    public String getInnerBillNo() {
        return innerBillNo;
    }

    public void setInnerBillNo(String innerBillNo) {
        this.innerBillNo = innerBillNo;
    }

    public String getInnerDate() {
        return innerDate;
    }

    public void setInnerDate(String innerDate) {
        this.innerDate = innerDate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getBankBillNo() {
        return bankBillNo;
    }

    public void setBankBillNo(String bankBillNo) {
        this.bankBillNo = bankBillNo;
    }

    public long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(long payAmount) {
        this.payAmount = payAmount;
    }

    public long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(long realAmount) {
        this.realAmount = realAmount;
    }

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankTransDate() {
        return bankTransDate;
    }

    public void setBankTransDate(String bankTransDate) {
        this.bankTransDate = bankTransDate;
    }

    public String getBankAuthNo() {
        return bankAuthNo;
    }

    public void setBankAuthNo(String bankAuthNo) {
        this.bankAuthNo = bankAuthNo;
    }

    public String getBankSerialNo() {
        return bankSerialNo;
    }

    public void setBankSerialNo(String bankSerialNo) {
        this.bankSerialNo = bankSerialNo;
    }

    public String getBankErrorCode() {
        return bankErrorCode;
    }

    public void setBankErrorCode(String bankErrorCode) {
        this.bankErrorCode = bankErrorCode;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getFlagCompare() {
        return flagCompare;
    }

    public void setFlagCompare(String flagCompare) {
        this.flagCompare = flagCompare;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtToBank() {
        return gmtToBank;
    }

    public void setGmtToBank(Date gmtToBank) {
        this.gmtToBank = gmtToBank;
    }

    public String getOutAccountNo() {
        return outAccountNo;
    }

    public void setOutAccountNo(String outAccountNo) {
        this.outAccountNo = outAccountNo;
    }

    public String getIsNotified() {
        return isNotified;
    }

    public void setIsNotified(String isNotified) {
        this.isNotified = isNotified;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(833262413, -1238303657).appendSuper(super.hashCode()).append(
            this.gmtModified).append(this.bankTransDate).append(this.checkUser).append(
            this.realAmount).append(this.isCheck).append(this.id).append(this.orderSeq).append(
            this.innerDate).append(this.checkDate).append(this.payDate).append(this.innerBillNo)
            .append(this.flagCompare).append(this.payAmount).append(this.bankAuthNo).append(
                this.isSucceed).append(this.gmtCreate).append(this.bankErrorCode).append(
                this.paymentType).append(this.payDest).append(this.bankBillNo).append(
                this.accountNo).append(this.bankSerialNo).append(this.outAccountNo).append(
                this.gmtToBank).append(this.bankType).toHashCode();
    }

}
