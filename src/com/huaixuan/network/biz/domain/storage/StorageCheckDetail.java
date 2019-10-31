package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class StorageCheckDetail extends BaseObject {
    private static final long serialVersionUID = 2427858103167721665L;
    /* @property: */
    private long              id;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: */
    private long              storId;

    private long              locId;

    private String            locName;
    /* @property: */
    private long              checkId;
    /* @property: */
    private long              storNumber;
    /* @property: */
    private String            storType;

    private String            batchNum;
    /* @property: */
    private Long              checkNum;
    /* @property: */
    private long              profitNum;
    /* @property: */
    private long              supplierId;

    private String            supplierName;

    private String            goodsInstanceName;

    private String            attribute;
    /* @property: */
    private String            remark;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;

    private String            code;

    //  ≤÷ø‚¿‡–Õ
    private String            depType;

    /* Default constructor - creates a new instance with no values set. */
    public StorageCheckDetail() {
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
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model: */
    public long getGoodsId() {
        return this.goodsId;
    }

    /* @model: */
    public void setGoodsInstanceId(long obj) {
        this.goodsInstanceId = obj;
    }

    /* @model: */
    public long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    public long getStorId() {
        return storId;
    }

    public void setStorId(long storId) {
        this.storId = storId;
    }

    /* @model: */
    public void setCheckId(long obj) {
        this.checkId = obj;
    }

    /* @model: */
    public long getCheckId() {
        return this.checkId;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    public long getProfitNum() {
        return profitNum;
    }

    public void setProfitNum(long profitNum) {
        this.profitNum = profitNum;
    }

    public long getStorNumber() {
        return storNumber;
    }

    public void setStorNumber(long storNumber) {
        this.storNumber = storNumber;
    }

    /* @model: */
    public void setStorType(String obj) {
        this.storType = obj;
    }

    /* @model: */
    public String getStorType() {
        return this.storType;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    /* @model: */
    public void setRemark(String obj) {
        this.remark = obj;
    }

    /* @model: */
    public String getRemark() {
        return this.remark;
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

    public long getLocId() {
        return locId;
    }

    public void setLocId(long locId) {
        this.locId = locId;
    }

    public String getGoodsInstanceName() {
        return goodsInstanceName;
    }

    public void setGoodsInstanceName(String goodsInstanceName) {
        this.goodsInstanceName = goodsInstanceName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
    }

}
