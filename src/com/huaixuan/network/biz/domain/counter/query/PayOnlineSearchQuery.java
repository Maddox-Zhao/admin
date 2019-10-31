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
	 * ֧��Ŀ�� 1.֧�� 2.��ֵ 3.��֤���ֵ
	 */
	private String payDest;
	/**
	 * �������(ʹ�����е���ĸ��д��ʾ����ICBC)
	 */
	private String bankType;
	/**
	 * ֧��ʱ��
	 */
	private String payDateStart;

	private String payDateEnd;
	/**
	 * ֧������ ENET����֧��,TMO���л��
	 */
	private String paymentType;
	/**
	 * ���ж��� ÿ�������ж���Ҫ�󣬸�������Ҫ���ر�����
	 */
	private String bankBillNo;
	/**
	 * ֧���ɹ���־ I:��ʹ״̬ Y:֧���ɹ� F:֧��ʧ��
	 */
	private String isSucceed;
	/**
	 * ��������־ Y:���ɹ� N:δ������ʧ��
	 */
	/**
	 * ���˱��
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
