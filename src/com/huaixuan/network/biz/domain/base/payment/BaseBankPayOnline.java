package com.huaixuan.network.biz.domain.base.payment;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ���ж���
 *
 * @author tao.wangt
 * @version $Id: BaseBankPayOnline.java,v 0.1 2009-8-7 ����10:09:57 tao.wangt Exp
 *          $
 */
public class BaseBankPayOnline extends BaseObject {
    /**
     * ֧����ˮ��
     */
    private Long   id;
    /**
     * �˻���
     */
    private String accountNo;
    /**
     * ֧��Ŀ�� 1.֧�� 2.��ֵ 3.��֤���ֵ
     */
    private int    payDest;
    /**
     * �ڲ�������
     */
    private String innerBillNo;
    /**
     * ��������ʱ�� ����:java.lang.String ��ʽ:yyyymmdd
     */
    private String innerDate;
    /**
     * ��������ʱ��
     */
    private Date   gmtCreate;
    /**
     * �������(ʹ�����е���ĸ��д��ʾ����ICBC)
     */
    private String bankType;
    /**
     * ֧��ʱ��
     */
    private Date   payDate;
    /**
     * ֧������ ENET����֧��,TMO���л��
     */
    private String paymentType;
    /**
     * ���ж��� ÿ�������ж���Ҫ�󣬸�������Ҫ���ر�����
     */
    private String bankBillNo;
    /**
     * ֧����� �Է�Ϊ��λ
     */
    private long   payAmount;
    /**
     * ʵ����� �Է�Ϊ��λ
     */
    private long   realAmount;
    /**
     * ֧���ɹ���־ I:��ʹ״̬ Y:֧���ɹ� F:֧��ʧ��
     */
    private String isSucceed;
    /**
     * ��������־ Y:���ɹ� N:δ������ʧ��
     */
    private String isCheck;
    /**
     * ��������� program auto:ϵͳ�Զ�ȷ��
     */
    private String checkUser;
    /**
     * �����������
     */
    private Date   checkDate;
    /**
     * ���н�������(���з���)
     */
    private String bankTransDate;
    /**
     * ������Ȩ��
     */
    private String bankAuthNo;
    /**
     * �����ڲ�������
     */
    private String bankSerialNo;
    /**
     * ���д������
     */
    private String bankErrorCode;
    /**
     * ����(��ѯ�ӿڲ�ѯ�����Ǽ�)
     */
    private int    orderSeq;
    /**
     * ���˱��
     */
    private String flagCompare;
    /**
     * ��������޸�ʱ��
     */
    private Date   gmtModified;
    /**
     * ���͵����е�֧������ʱ��
     */
    private Date   gmtToBank;
    /**
     * �ⲿ������
     */
    private String outAccountNo;
    /**
     * ����֪ͨ��ɱ�־ Y:�����֪ͨ N:δ���֪ͨ
     */
    private String isNotified;
    /**
     * �û���
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
