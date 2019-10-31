package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class ProdRelationIn extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -1074534696709923313L;
    /* @property: */
    private long              id;
    /* @property: */
    private long              inDepId;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: 数量 */
    private long              amount;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              inDetailId;
    /* @property: */
    private long              locId;
    private String            locName;                                  // 库位名称
    private String            depName;                                  // 仓库名称
    private String            instanceCode;                             // 商品编码
    private String            instanceName;                             // 商品名称
    private String            attrs;                                    // 属性
    private String            goodsUnit;                                // 单位
    private double            unitPrice;                                // 单价
    private double            money;                                    // 金额
    private String            supplierName;                             // 供应商名称
    private Long              supplierId;                               // 供应商ID
    private String            batchNum;                                 // 批次
    private Long              depFirstId;                               //一级仓库ID
    private String            storType;                                 //库存类型

    private String            isWholesale;
    private String            tid;
    /*
     * 成本价
     */
    private Double            selfCost;

    public Double getSelfCost() {
        return selfCost;
    }

    public void setSelfCost(Double selfCost) {
        this.selfCost = selfCost;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * @return depName
     */
    public String getDepName() {
        return depName;
    }

    /**
     * @param depName 要设置的 depName
     */
    public void setDepName(String depName) {
        this.depName = depName;
    }

    /**
     * @return locName
     */
    public String getLocName() {
        return locName;
    }

    /**
     * @param locName 要设置的 locName
     */
    public void setLocName(String locName) {
        this.locName = locName;
    }

    /**
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName 要设置的 supplierName
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * @return money
     */
    public double getMoney() {
        return money;
    }

    /**
     * @param money 要设置的 money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * @return unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice 要设置的 unitPrice
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return instanceCode
     */
    public String getInstanceCode() {
        return instanceCode;
    }

    /**
     * @param instanceCode 要设置的 instanceCode
     */
    public void setInstanceCode(String instanceCode) {
        this.instanceCode = instanceCode;
    }

    /**
     * @return goodsUnit
     */
    public String getGoodsUnit() {
        return goodsUnit;
    }

    /**
     * @param goodsUnit 要设置的 goodsUnit
     */
    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
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

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /* Default constructor - creates a new instance with no values set. */
    public ProdRelationIn() {
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
    public void setInDepId(long obj) {
        this.inDepId = obj;
    }

    /* @model: */
    public long getInDepId() {
        return this.inDepId;
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
    public void setAmount(long obj) {
        this.amount = obj;
    }

    /* @model: */
    public long getAmount() {
        return this.amount;
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
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model: */
    public long getGoodsId() {
        return this.goodsId;
    }

    /* @model: */
    public void setInDetailId(long obj) {
        this.inDetailId = obj;
    }

    /* @model: */
    public long getInDetailId() {
        return this.inDetailId;
    }

    /* @model: */
    public void setLocId(long obj) {
        this.locId = obj;
    }

    /* @model: */
    public long getLocId() {
        return this.locId;
    }

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("inDepId", this.inDepId)
            .append("goodsInstanceId", this.goodsInstanceId).append("amount", this.amount).append(
                "gmtCreate", this.gmtCreate).append("gmtModify", this.gmtModify).append("goodsId",
                this.goodsId).append("inDetailId", this.inDetailId).append("locName", this.locName)
            .append("depName", this.depName).append("instanceCode", this.instanceCode).append(
                "goodsUnit", this.goodsUnit).append("unitPrice", this.unitPrice).append("money",
                this.money).append("supplierName", this.supplierName).append("locId", this.locId);
        return sb.toString();
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

}
