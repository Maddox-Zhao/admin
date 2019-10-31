package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class InDepository extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -5680896570489694179L;
    /* @property: */
    private long              id;
    /* @property: */
    private String            billNum;
    /* @property: */
    private String            type;
    /* @property: */
    private String            relationNum;
    /* @property: */
    private String            creater;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;
    /* @property: */
    private String            status;
    private Date              gmtInDep;
    private String            batchNum;                                //批次
    private String            instanceName;                            //产品名称
    private long              amount;                                  //产品数量
    private String            goodCode;                                //产品编码
    private String            attrs;                                   //产品属性
    private long              leftNum;                                 //当前库存数量
    private String            financeStatus;

    private Long              depFirstId;

    private String            depFirstName;

    private long              leftDepNum;                              //剩余一级仓库库存

    private String            isWholesale;
    private String            tid;

    private String 			  supplierName; 						   //供应商名称

	public long getLeftDepNum() {
        return leftDepNum;
    }

    public void setLeftDepNum(long leftDepNum) {
        this.leftDepNum = leftDepNum;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    /**
     * @return the leftNum
     */
    public long getLeftNum() {
        return leftNum;
    }

    /**
     * @param leftNum the leftNum to set
     */
    public void setLeftNum(long leftNum) {
        this.leftNum = leftNum;
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
     * @return the goodCode
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * @param goodCode the goodCode to set
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
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
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return batchNum
     */
    public String getBatchNum() {
        return batchNum;
    }

    /**
     * @param batchNum 要设置的 batchNum
     */
    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Date getGmtInDep() {
        return gmtInDep;
    }

    public void setGmtInDep(Date gmtInDep) {
        this.gmtInDep = gmtInDep;
    }

    /* Default constructor - creates a new instance with no values set. */
    public InDepository() {
    }

    /* @model: */
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model: */
    public long getId() {
        return this.id;
    }

    /* @model: */
    public void setBillNum(String obj) {
        this.billNum = obj;
    }

    /* @model: */
    public String getBillNum() {
        return this.billNum;
    }

    /* @model: */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model: */
    public String getType() {
        return this.type;
    }

    /* @model: */
    public void setRelationNum(String obj) {
        this.relationNum = obj;
    }

    /* @model: */
    public String getRelationNum() {
        return this.relationNum;
    }

    /* @model: */
    public void setCreater(String obj) {
        this.creater = obj;
    }

    /* @model: */
    public String getCreater() {
        return this.creater;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model: */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model: */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /* @model: */
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model: */
    public String getStatus() {
        return this.status;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InDepository)) {
            return false;
        }
        final InDepository indepository = (InDepository) o;
        return this.hashCode() == indepository.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return (type != null ? type.hashCode() : 0);
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("billNum", this.billNum).append("type", this.type).append(
            "relationNum", this.relationNum).append("creater", this.creater).append("gmtCreate",
            this.gmtCreate).append("gmtModify", this.gmtModify).append("status", this.status)
            .append("gmtInDep", this.gmtInDep).append("financeStatus", this.financeStatus)
            .append("supplierName",this.supplierName);
        return sb.toString();
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getIsWholesale() {
        return isWholesale;
    }

    public void setIsWholesale(String isWholesale) {
        this.isWholesale = isWholesale;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
