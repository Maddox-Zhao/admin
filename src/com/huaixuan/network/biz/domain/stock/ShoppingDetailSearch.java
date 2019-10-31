package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @version 3.2.0
 */
public class ShoppingDetailSearch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5058723490361404002L;

    protected Log             log              = LogFactory.getLog(this.getClass());

    private long              shoppingId;
    /* @property: ???????*/
    private String            shoppingNum;
    /* @property: ??????ID*/
    private long              supplierId;
    /* @property: ?????????*/
    private String            supplierName;
    /* @property: ??????*/
    private Date              shoppingTime;
    /* @property: 商品ID*/
    private long              goodsId;
    /* @property: ?????????*/
    private long              goodsInstanceId;
    /* @property: 产品实例编码*/
    private String            goodsInstanceCode;
    /* @property: ?????????*/
    private String            instanceName;
    /* @property: ??????*/
    private String            catCode;
    /* @property: ???????*/
    private String            attrs;
    /* @property: ??λ*/
    private String            units;
    /* @property: ??*/
    private long              amount;
    /* @property: 验收数量*/
    private long              receiveNum;
    /* @property: ????*/
    private double            unitPrice;
    /* @property: ??????*/
    private double            dueFee;
    /* @property: ??????*/
    private double            factFee;

    private Integer           existNum;                                              //库存数量

    private String            shoppingTime_str;

    /* 库存类型 */
    private String            storType;

    private Long              depFirstId;

    /**
     * @return goodsId
     */
    public long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId 要设置的 goodsId
     */
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return shoppingId
     */
    public long getShoppingId() {
        return shoppingId;
    }

    /**
     * @param shoppingId 要设置的 shoppingId
     */
    public void setShoppingId(long shoppingId) {
        this.shoppingId = shoppingId;
    }

    /**
     * @return shoppingNum
     */
    public String getShoppingNum() {
        return shoppingNum;
    }

    /**
     * @param shoppingNum 要设置的 shoppingNum
     */
    public void setShoppingNum(String shoppingNum) {
        this.shoppingNum = shoppingNum;
    }

    /**
     * @return receiveNum
     */
    public long getReceiveNum() {
        return receiveNum;
    }

    /**
     * @param receiveNum 要设置的 receiveNum
     */
    public void setReceiveNum(long receiveNum) {
        this.receiveNum = receiveNum;
    }

    /**
     * @return supplierId
     */
    public long getSupplierId() {
        return supplierId;
    }

    /**
     * @param supplierId ?????? supplierId
     */
    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * @return amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount ?????? amount
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
     * @param attrs ?????? attrs
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
     * @param catCode ?????? catCode
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
     * @param dueFee ?????? dueFee
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
     * @param factFee ?????? factFee
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
     * @param goodsInstanceId ?????? goodsInstanceId
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
     * @param instanceName ?????? instanceName
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * @return shoppingTime
     */
    public Date getShoppingTime() {
        return shoppingTime;
    }

    /**
     * @param shoppingTime ?????? shoppingTime
     */
    public void setShoppingTime(Date shoppingTime) {
        this.shoppingTime = shoppingTime;
    }

    /**
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName ?????? supplierName
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice ?????? unitPrice
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return units
     */
    public String getUnits() {
        return units;
    }

    /**
     * @param units ?????? units
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingDetailSearch)) {
            return false;
        }
        final ShoppingDetailSearch shoppingdetail = (ShoppingDetailSearch) o;
        return this.hashCode() == shoppingdetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append(
            "shoppingNum", this.shoppingNum).append("supplierName", this.supplierName).append(
            "shoppingTime", this.shoppingTime).append("goodsInstanceId", this.goodsInstanceId)
            .append("instanceName", this.instanceName).append("catCode", this.catCode).append(
                "attrs", this.attrs).append("units", this.units).append("amount", this.amount)
            .append("unitPrice", this.unitPrice).append("dueFee", this.dueFee).append("factFee",
                this.factFee);
        return sb.toString();
    }

    public String getShoppingTime_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        if (this.getShoppingTime() != null) {
            return df.format(this.getShoppingTime());
        } else {
            return null;
        }
    }

    public void setShoppingTime_str(String shoppingTime_str) {
        this.shoppingTime_str = shoppingTime_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        Date ts = null;
        if (StringUtils.isNotBlank(shoppingTime_str)) {
            try {
                ts = df.parse(shoppingTime_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.shoppingTime = ts;
    }

    public Integer getExistNum() {
        if (null == existNum) {
            return 0;
        }
        return existNum;
    }

    public void setExistNum(Integer existNum) {
        this.existNum = existNum;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }
}
