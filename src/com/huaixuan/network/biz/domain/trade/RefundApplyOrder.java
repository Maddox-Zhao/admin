package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**  
 * (bibleUtil auto code generation) 
 * @version 3.2.0  
 */
public class RefundApplyOrder extends BaseObject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 384305854814953653L;
    /* @property: */
    private Long              id;
    /* @property:id */
    private Long              applyId;
    /* @property:id */
    private Long              orderId;
    /* @property: */
    private Date              gmtModify;
    /* @property: */
    private Date              gmtCreate;
    /* @property:id */
    private Long              goodsId;
    /* @property:id */
    private Long              goodsInstanceId;
    /* @property: */
    private Long              refAmount;
    /* @property: */
    private String            reason;
    /* @property: */
    private String            type;

    private long              goodsNumber;
    private String            goodsAttr;
    private String            buyAttr;
    private String            goodsTitle;
    private String            goodsInstanceName;

    /* Default constructor - creates a new instance with no values set. */
    /* @model: */
    public void setId(Long obj) {
        this.id = obj;
    }

    /* @model: */
    public Long getId() {
        return this.id;
    }

    /* @model:id */
    public void setApplyId(Long obj) {
        this.applyId = obj;
    }

    /* @model:id */
    public Long getApplyId() {
        return this.applyId;
    }

    /* @model:id */
    public void setOrderId(Long obj) {
        this.orderId = obj;
    }

    /* @model:id */
    public Long getOrderId() {
        return this.orderId;
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
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:id */
    public void setGoodsId(Long obj) {
        this.goodsId = obj;
    }

    /* @model:id */
    public Long getGoodsId() {
        return this.goodsId;
    }

    /* @model:¨°id */
    public void setGoodsInstanceId(Long obj) {
        this.goodsInstanceId = obj;
    }

    /* @model:id */
    public Long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    /* @model: */
    public void setRefAmount(Long obj) {
        this.refAmount = obj;
    }

    /* @model: */
    public Long getRefAmount() {
        return this.refAmount;
    }

    /* @model: */
    public void setReason(String obj) {
        this.reason = obj;
    }

    /* @model: */
    public String getReason() {
        return this.reason;
    }

    /* @model: */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model: */
    public String getType() {
        return this.type;
    }

    public String getBuyAttr() {
        return buyAttr;
    }

    public void setBuyAttr(String buyAttr) {
        this.buyAttr = buyAttr;
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public String getGoodsInstanceName() {
        return goodsInstanceName;
    }

    public void setGoodsInstanceName(String goodsInstanceName) {
        this.goodsInstanceName = goodsInstanceName;
    }

    public long getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(long goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

}
