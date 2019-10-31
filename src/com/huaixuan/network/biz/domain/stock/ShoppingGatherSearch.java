package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 采购订单汇�查询结果对象(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class ShoppingGatherSearch implements Serializable {

    private static final long serialVersionUID = -5058723490361404002L;

    /* @property: 产品ID*/
    private long              goodsInstanceId;
    /* @property: 产品编码*/
    private String            goodsInstanceCode;
    /* @property: 产品名称*/
    private String            instanceName;
    /* @property: 类目*/
    private String            catCode;
    /* @property: 属�*/
    private String            attrs;
    /* @property: 单位*/
    private String            units;
    /* @property: 数量*/
    private long              amount;
    /* @property: 应付金额*/
    private double            dueFee;
    /* @property: 应收金额*/
    private double            factFee;

    /* 库存类型 */
    private String            storType;

    /**
     * @return amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount 要设置的 amount
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return attrs
     */
    public String getAttrs() {
        return attrs;
    }

    /**
     * @param attrs 要设置的 attrs
     */
    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * @return catCode
     */
    public String getCatCode() {
        return catCode;
    }

    /**
     * @param catCode 要设置的 catCode
     */
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    /**
     * @return dueFee
     */
    public double getDueFee() {
        return dueFee;
    }

    /**
     * @param dueFee 要设置的 dueFee
     */
    public void setDueFee(double dueFee) {
        this.dueFee = dueFee;
    }

    /**
     * @return factFee
     */
    public double getFactFee() {
        return factFee;
    }

    /**
     * @param factFee 要设置的 factFee
     */
    public void setFactFee(double factFee) {
        this.factFee = factFee;
    }

    /**
     * @return goodsInstanceId
     */
    public long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    /**
     * @param goodsInstanceId 要设置的 goodsInstanceId
     */
    public void setGoodsInstanceId(long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    /**
     * @return goodsInstanceCode
     */
    public String getGoodsInstanceCode() {
        return goodsInstanceCode;
    }

    /**
     * @param goodsInstanceCode 要设置的 goodsInstanceCode
     */
    public void setGoodsInstanceCode(String goodsInstanceCode) {
        this.goodsInstanceCode = goodsInstanceCode;
    }

    /**
     * @return instanceName
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * @param instanceName 要设置的 instanceName
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * @return units
     */
    public String getUnits() {
        return units;
    }

    /**
     * @param units 要设置的 units
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingGatherSearch)) {
            return false;
        }
        final ShoppingGatherSearch shoppingdetail = (ShoppingGatherSearch) o;
        return this.hashCode() == shoppingdetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append(
            "goodsInstanceId", this.goodsInstanceId).append("instanceName", this.instanceName)
            .append("catCode", this.catCode).append("attrs", this.attrs)
            .append("units", this.units).append("amount", this.amount)
            .append("dueFee", this.dueFee).append("factFee", this.factFee);
        return sb.toString();
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

}
