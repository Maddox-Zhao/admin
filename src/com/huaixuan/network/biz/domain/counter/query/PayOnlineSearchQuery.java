/**
 *
 */
package com.huaixuan.network.biz.domain.counter.query;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * @author chenhang 2011-3-31
 */
public class PayOnlineSearchQuery extends BaseObject {
	/**
	 * 支付目的 1.支付 2.充值 3.保证金充值
	 */
	private String payDest;
	/**
	 * 银行类别(使用银行的字母缩写表示，如ICBC)
	 */
	private String bankType;
	/**
	 * 支付时间
	 */
	private String payDateStart;

	private String payDateEnd;
	/**
	 * 支付类型 ENET网银支付,TMO银行汇款
	 */
	private String paymentType;
	/**
	 * 银行订单 每个银行有独特要求，根据银行要求特别生成
	 */
	private String bankBillNo;
	/**
	 * 支付成功标志 I:初使状态 Y:支付成功 F:支付失败
	 */
	private String isSucceed;
	/**
	 * 订单检查标志 Y:检查成功 N:未检查或检查失败
	 */
	/**
	 * 对账标记
	 */
	private String flagCompare;

	public String getPayDest() {
		return payDest;
	}

	public void setPayDest(String payDest) {
		this.payDest = payDest;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getPayDateStart() {
		return payDateStart;
	}

	public void setPayDateStart(String payDateStart) {
		this.payDateStart = payDateStart;
	}

	public String getPayDateEnd() {
		return payDateEnd;
	}

	public void setPayDateEnd(String payDateEnd) {
		this.payDateEnd = payDateEnd;
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

	public String getIsSucceed() {
		return isSucceed;
	}

	public void setIsSucceed(String isSucceed) {
		this.isSucceed = isSucceed;
	}

	public String getFlagCompare() {
		return flagCompare;
	}

	public void setFlagCompare(String flagCompare) {
		this.flagCompare = flagCompare;
	}

}
