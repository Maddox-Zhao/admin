package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 查询报残单商品明细视图对象(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class DamagedDetailView extends BaseObject {

    private static final long serialVersionUID = 7314802619960345426L;
    //商品编码、商品名称、类目、属性、品牌、供应商、仓位、单位、数量、单位成本、合计成本、备注
    /* @property: 类目*/
    private String            catCode;
    /* @property: 属性*/
    private String            attrs;
    /* @property: 供应商名称*/
    private String            supplierName;
    /* @property: 品牌名称*/
    private String            brandName;
    /* @property: 报残日期*/
    private String            gmtDamaged;
    /* @property: 仓库名称*/
    private String            depName;
    /* @property: 制单人*/
    private String            creater;
    /* @property: 经手人*/
    private String            agent;
    /* @property: */
    private long              id;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: */
    private long              damagedId;
    /*报残单号*/
    private String            damagedCode;
    /* @property: 产品名称*/
    private String            goodsName;
    /* @property: 产品编码*/
    private String            goodsCode;
    /* @property: */
    private long              supplierId;
    /* @property: */
    private String            unit;
    /* @property: */
    private int               amount;
    /* @property: */
    private double            unitCost;
    /* @property: */
    private double            costCount;
    /* @property: */
    private String            remark;
    /* @property: */
    private Date              gmtCreate;
    /* @property: */
    private Date              gmtModify;
    /* @property: */
    private long              locId;
    /* @property: 库位名称*/
    private String            locName;
    /* @property: */
    private String            reason;
    /* @property: 批次 */
    private String            batchNum;

    private Long              depfirstId;

    /* Default constructor - creates a new instance with no values set. */
    public DamagedDetailView() {
    }

    /**
     * @return goodsCode
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * @param goodsCode 要设置的 goodsCode
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
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
     * @return agent
     */
    public String getAgent() {
        return agent;
    }

    /**
     * @param agent 要设置的 agent
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    /**
     * @return creater
     */
    public String getCreater() {
        return creater;
    }

    /**
     * @param creater 要设置的 creater
     */
    public void setCreater(String creater) {
        this.creater = creater;
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
     * @return gmtDamaged
     */
    public String getGmtDamaged() {
        return gmtDamaged;
    }

    /**
     * @param gmtDamaged 要设置的 gmtDamaged
     */
    public void setGmtDamaged(String gmtDamaged) {
        this.gmtDamaged = gmtDamaged;
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
     * @return brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brand_name 要设置的 brand_name
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    /* @model: */
    public void setDamagedId(long obj) {
        this.damagedId = obj;
    }

    /* @model: */
    public long getDamagedId() {
        return this.damagedId;
    }

    /**
     * @return damagedCode
     */
    public String getDamagedCode() {
        return damagedCode;
    }

    /**
     * @param damagedCode 要设置的 damagedCode
     */
    public void setDamagedCode(String damagedCode) {
        this.damagedCode = damagedCode;
    }

    /* @model: */
    public void setGoodsName(String obj) {
        this.goodsName = obj;
    }

    /* @model: */
    public String getGoodsName() {
        return this.goodsName;
    }

    /* @model: */
    public void setSupplierId(long obj) {
        this.supplierId = obj;
    }

    /* @model: */
    public long getSupplierId() {
        return this.supplierId;
    }

    /* @model: */
    public void setUnit(String obj) {
        this.unit = obj;
    }

    /* @model: */
    public String getUnit() {
        return this.unit;
    }

    /* @model: */
    public void setAmount(int obj) {
        this.amount = obj;
    }

    /* @model: */
    public int getAmount() {
        return this.amount;
    }

    /* @model: */
    public void setUnitCost(double obj) {
        this.unitCost = obj;
    }

    /* @model: */
    public double getUnitCost() {
        return this.unitCost;
    }

    /* @model: */
    public void setCostCount(double obj) {
        this.costCount = obj;
    }

    /* @model: */
    public double getCostCount() {
        return this.costCount;
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

    /* @model: */
    public void setLocId(long obj) {
        this.locId = obj;
    }

    /* @model: */
    public long getLocId() {
        return this.locId;
    }

    /* @model: */
    public void setReason(String obj) {
        this.reason = obj;
    }

    /* @model: */
    public String getReason() {
        return this.reason;
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

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DamagedDetailView)) {
            return false;
        }
        final DamagedDetailView damageddetail = (DamagedDetailView) o;
        return this.hashCode() == damageddetail.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return this.hashCode();
    }

    public Long getDepfirstId() {
        return depfirstId;
    }

    public void setDepfirstId(Long depfirstId) {
        this.depfirstId = depfirstId;
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("goodsId", this.goodsId)
            .append("goodsInstanceId", this.goodsInstanceId).append("damagedId", this.damagedId)
            .append("goodsName", this.goodsName).append("supplierId", this.supplierId).append(
                "unit", this.unit).append("amount", this.amount).append("unitCost", this.unitCost)
            .append("costCount", this.costCount).append("remark", this.remark).append("gmtCreate",
                this.gmtCreate).append("gmtModify", this.gmtModify).append("locId", this.locId)
            .append("reason", this.reason).append("batchNum", this.batchNum);
        return sb.toString();
    }
}
