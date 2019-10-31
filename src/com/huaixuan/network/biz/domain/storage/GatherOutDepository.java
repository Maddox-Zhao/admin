package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ���ⵥ�ɱ�����ͳ����ʾ
 * @author fanyj
 *
 */
public class GatherOutDepository extends BaseObject {

    private static final long serialVersionUID = 1397476841110592641L;

    //���ⵥID
    private Long              outDepositoryId;
    //����
    private Long              outNum;
    //����
    private Double            unitPrice;
    //ʵ������
    private String            instanceName;
    //��ⵥ��
    private String            billNum;
    //���
    private Double            sumMoney;
    //��������
    private Date              gmtOutDep;
    //��Ʒ����
    private String            goodsInstanceCode;
    //��Ʒ����
    private String            attrs;
    //  ����ȷ��
    private String            financeStatus;

    private Long              count;

    private Double            totalSum;

    //һ���ֿ�ID
    private Long               depFirstId;

    public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	/**
     * @return the goodsInstanceCode
     */
    public String getGoodsInstanceCode() {
        return goodsInstanceCode;
    }

    /**
     * @param goodsInstanceCode the goodsInstanceCode to set
     */
    public void setGoodsInstanceCode(String goodsInstanceCode) {
        this.goodsInstanceCode = goodsInstanceCode;
    }

    /**
     * @return the attrs
     */
    public String getAttrs() {
        return attrs;
    }

    /**
     * @param attrs the attrs to set
     */
    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * @return the outDepositoryId
     */
    public Long getOutDepositoryId() {
        return outDepositoryId;
    }

    /**
     * @param outDepositoryId the outDepositoryId to set
     */
    public void setOutDepositoryId(Long outDepositoryId) {
        this.outDepositoryId = outDepositoryId;
    }

    /**
     * @return the unitPrice
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the instanceName
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * @param instanceName the instanceName to set
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * @return the billNum
     */
    public String getBillNum() {
        return billNum;
    }

    /**
     * @param billNum the billNum to set
     */
    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    /**
     * @return the sumMoney
     */
    public Double getSumMoney() {
        return sumMoney;
    }

    /**
     * @param sumMoney the sumMoney to set
     */
    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    /**
     * @return the outNum
     */
    public Long getOutNum() {
        return outNum;
    }

    /**
     * @param outNum the outNum to set
     */
    public void setOutNum(Long outNum) {
        this.outNum = outNum;
    }

    /**
     * @return the gmtOutDep
     */
    public Date getGmtOutDep() {
        return gmtOutDep;
    }

    /**
     * @param gmtOutDep the gmtOutDep to set
     */
    public void setGmtOutDep(Date gmtOutDep) {
        this.gmtOutDep = gmtOutDep;
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

	public Double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Double totalSum) {
		this.totalSum = totalSum;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
