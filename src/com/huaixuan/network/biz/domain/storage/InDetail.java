package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class InDetail extends BaseObject {

    private static final long serialVersionUID = 8244556634545146111L;
    /* @property: */
    private long              id;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              inDepositoryId;
    /* @property: */
    private long              amount;
    /* @property: */
    private double            unitPrice;
    /* @property: */
    private double            dueFee;
    /* @property: */
    private double            factFee;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    private String            status;

    private long              storId;

    // £”‡ø‚¥Ê
    private long              leftNum;

    private Long              depFirstId;
    private long              leftDepNum;

    private String            storType;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /* Default constructor - creates a new instance with no values set. */
    public InDetail() {
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
    public void setInDepositoryId(long obj) {
        this.inDepositoryId = obj;
    }

    /* @model: */
    public long getInDepositoryId() {
        return this.inDepositoryId;
    }

    /* @model: */
    public void setAmount(long obj) {
        this.amount = obj;
    }

    /* @model: */
    public long getAmount() {
        return this.amount;
    }

    /* @model: */
    public void setUnitPrice(double obj) {
        this.unitPrice = obj;
    }

    /* @model: */
    public double getUnitPrice() {
        return this.unitPrice;
    }

    /* @model: */
    public void setDueFee(double obj) {
        this.dueFee = obj;
    }

    /* @model: */
    public double getDueFee() {
        return this.dueFee;
    }

    /* @model: */
    public void setFactFee(double obj) {
        this.factFee = obj;
    }

    /* @model: */
    public double getFactFee() {
        return this.factFee;
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

    /*{@inheritDoc}*/
    public long getStorId() {
        return storId;
    }

    public void setStorId(long storId) {
        this.storId = storId;
    }

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

    public long getLeftDepNum() {
        return leftDepNum;
    }

    public void setLeftDepNum(long leftDepNum) {
        this.leftDepNum = leftDepNum;
    }

}
