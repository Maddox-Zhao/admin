package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 用于入库单成本汇总统计显示
 * @author fanyj
 *
 */
public class GatherInDepository extends BaseObject {

    private static final long serialVersionUID = 1397476841110592641L;

    //入库单ID
    private Long              inDepositoryId;
    //数量
    private Long              amount;
    //单价
    private Double            unitPrice;
    //实例名称
    private String            instanceName;
    //入库单号
    private String            billNum;
    //金额
    private Double          sumMoney;
    //入库日期
    private Date              gmtInDep;
    //产品编码
    private String            goodsInstanceCode;
    //产品属性
    private String            attrs;

    //  财务确认
    private String            financeStatus;

    private Long             count;

    private Double          totalSum;

    //一级仓库ID
    private Long               depFirstId;

    //类型
    private String             storType;

    public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getStorType() {
		return storType;
	}

	public void setStorType(String storType) {
		this.storType = storType;
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
     * @return the inDepositoryId
     */
    public Long getInDepositoryId() {
        return inDepositoryId;
    }

    /**
     * @param inDepositoryId the inDepositoryId to set
     */
    public void setInDepositoryId(Long inDepositoryId) {
        this.inDepositoryId = inDepositoryId;
    }

    /**
     * @return the amount
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Long amount) {
        this.amount = amount;
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

    public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	/**
     * @return the gmtInDep
     */
    public Date getGmtInDep() {
        return gmtInDep;
    }

    /**
     * @param gmtInDep the gmtInDep to set
     */
    public void setGmtInDep(Date gmtInDep) {
        this.gmtInDep = gmtInDep;
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Double totalSum) {
		this.totalSum = totalSum;
	}

}
