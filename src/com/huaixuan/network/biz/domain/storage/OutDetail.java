package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class OutDetail extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -6376156185225119015L;
    /* @property: */
    private long              id;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              outDepositoryId;
    /* @property: */
    private long              outNum;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    private String            status;
    private double            unitPrice;
    private double            dueFee;
    private double            factFee;
    //  剩余库存
    private long              leftNum;

    //  剩余一级仓库库存
    private long              leftDepNum;
    // 关联单号
    private String            relationNum;

    private String            storType;                                //类型

    private long              outVirtualNum;                           //暂估出库数量

    private Long            depFirstId;

    private String            depFirstName;

    public long getOutVirtualNum() {
        return outVirtualNum;
    }

    public void setOutVirtualNum(long outVirtualNum) {
        this.outVirtualNum = outVirtualNum;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the relationNum
     */
    public String getRelationNum() {
        return relationNum;
    }

    /**
     * @param relationNum the relationNum to set
     */
    public void setRelationNum(String relationNum) {
        this.relationNum = relationNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /* Default constructor - creates a new instance with no values set. */
    public OutDetail() {
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
    public void setGoodsInstanceId(long obj) {
        this.goodsInstanceId = obj;
    }

    /* @model: */
    public long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    /* @model: */
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model: */
    public long getGoodsId() {
        return this.goodsId;
    }

    /* @model: */
    public void setOutDepositoryId(long obj) {
        this.outDepositoryId = obj;
    }

    /* @model: */
    public long getOutDepositoryId() {
        return this.outDepositoryId;
    }

    /* @model: */
    public void setOutNum(long obj) {
        this.outNum = obj;
    }

    /* @model: */
    public long getOutNum() {
        return this.outNum;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutDetail)) {
            return false;
        }
        final OutDetail outdetail = (OutDetail) o;
        return this.hashCode() == outdetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("goodsInstanceId", this.goodsInstanceId)
            .append("goodsId", this.goodsId).append("outDepositoryId", this.outDepositoryId)
            .append("outNum", this.outNum).append("status", this.status);
        ;
        return sb.toString();
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getDueFee() {
        return dueFee;
    }

    public void setDueFee(double dueFee) {
        this.dueFee = dueFee;
    }

    public double getFactFee() {
        return factFee;
    }

    public void setFactFee(double factFee) {
        this.factFee = factFee;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public long getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(long leftNum) {
        this.leftNum = leftNum;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public long getLeftDepNum() {
        return leftDepNum;
    }

    public void setLeftDepNum(long leftDepNum) {
        this.leftDepNum = leftDepNum;
    }

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

}
