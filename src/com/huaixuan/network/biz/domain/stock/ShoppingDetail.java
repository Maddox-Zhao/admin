package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* @ClassName: ShoppingDetail
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-23 上午10:36:46
 */
public class ShoppingDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/* @property: */
    private long   id;
    /* @property: */
    private long   shoppingId;
    /* @property: */
    private long   goodsId;
    /* @property: */
    private long   goodsInstanceId;
    /* @property: */
    private String units;
    /* @property: */
    private long   amount;
    /* @property: */
    private double unitPrice;
    /* @property: */
    private double dueFee;
    /* @property: */
    private double factFee;
    /* @property: */
    private long   rejectNum;
    /* @property: */
    private long   missingNum;
    /* @property: */
    private long   receiveNum;
    /* @property: */
    private Date   gmtCreate;
    /* @property: */
    private Date   gmtModify;

    /* Default constructor - creates a new instance with no values set. */
    public ShoppingDetail() {
    }

    public void setId(long obj) {
        this.id = obj;
    }

    public long getId() {
        return this.id;
    }

    public void setShoppingId(long obj) {
        this.shoppingId = obj;
    }

    public long getShoppingId() {
        return this.shoppingId;
    }

    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    public long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsInstanceId(long obj) {
        this.goodsInstanceId = obj;
    }

    public long getGoodsInstanceId() {
        return this.goodsInstanceId;
    }

    public void setUnits(String obj) {
        this.units = obj;
    }

    public String getUnits() {
        return this.units;
    }

    public void setAmount(long obj) {
        this.amount = obj;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setUnitPrice(double obj) {
        this.unitPrice = obj;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setDueFee(double obj) {
        this.dueFee = obj;
    }

    public double getDueFee() {
        return this.dueFee;
    }

    public void setFactFee(double obj) {
        this.factFee = obj;
    }

    public double getFactFee() {
        return this.factFee;
    }

    public void setRejectNum(long obj) {
        this.rejectNum = obj;
    }

    public long getRejectNum() {
        return this.rejectNum;
    }

    public void setMissingNum(long obj) {
        this.missingNum = obj;
    }

    public long getMissingNum() {
        return this.missingNum;
    }

    public void setReceiveNum(long obj) {
        this.receiveNum = obj;
    }

    public long getReceiveNum() {
        return this.receiveNum;
    }

    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    public Date getGmtModify() {
        return this.gmtModify;
    }

    public double getReturnPer() {
        long r = this.rejectNum;
        long m = this.missingNum;
        long total = this.amount;
        if (total == 0) {
            return 0.00;
        }
        double per = ((r + m) * 100) / total;
        return per;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingDetail)) {
            return false;
        }
        final ShoppingDetail shoppingdetail = (ShoppingDetail) o;
        return this.hashCode() == shoppingdetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());

        return result;
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("shoppingId", this.shoppingId).append("goodsId", this.goodsId).append(
            "goodsInstanceId", this.goodsInstanceId).append("units", this.units).append("amount",
            this.amount).append("unitPrice", this.unitPrice).append("dueFee", this.dueFee).append(
            "factFee", this.factFee).append("rejectNum", this.rejectNum).append("missingNum",
            this.missingNum).append("receiveNum", this.receiveNum).append("gmtCreate",
            this.gmtCreate).append("gmtModify", this.gmtModify);
        return sb.toString();
    }

}
