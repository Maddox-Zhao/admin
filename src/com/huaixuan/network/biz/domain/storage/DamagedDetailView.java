package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ��ѯ���е���Ʒ��ϸ��ͼ����(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class DamagedDetailView extends BaseObject {

    private static final long serialVersionUID = 7314802619960345426L;
    //��Ʒ���롢��Ʒ���ơ���Ŀ�����ԡ�Ʒ�ơ���Ӧ�̡���λ����λ����������λ�ɱ����ϼƳɱ�����ע
    /* @property: ��Ŀ*/
    private String            catCode;
    /* @property: ����*/
    private String            attrs;
    /* @property: ��Ӧ������*/
    private String            supplierName;
    /* @property: Ʒ������*/
    private String            brandName;
    /* @property: ��������*/
    private String            gmtDamaged;
    /* @property: �ֿ�����*/
    private String            depName;
    /* @property: �Ƶ���*/
    private String            creater;
    /* @property: ������*/
    private String            agent;
    /* @property: */
    private long              id;
    /* @property: */
    private long              goodsId;
    /* @property: */
    private long              goodsInstanceId;
    /* @property: */
    private long              damagedId;
    /*���е���*/
    private String            damagedCode;
    /* @property: ��Ʒ����*/
    private String            goodsName;
    /* @property: ��Ʒ����*/
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
    /* @property: ��λ����*/
    private String            locName;
    /* @property: */
    private String            reason;
    /* @property: ���� */
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
     * @param goodsCode Ҫ���õ� goodsCode
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
     * @param locName Ҫ���õ� locName
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
     * @param agent Ҫ���õ� agent
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
     * @param creater Ҫ���õ� creater
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
     * @param depName Ҫ���õ� depName
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
     * @param gmtDamaged Ҫ���õ� gmtDamaged
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
     * @param attrs Ҫ���õ� attrs
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
     * @param brand_name Ҫ���õ� brand_name
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
     * @param catCode Ҫ���õ� catCode
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
     * @param supplierName Ҫ���õ� supplierName
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
     * @param damagedCode Ҫ���õ� damagedCode
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
     * @param batchNum Ҫ���õ� batchNum
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
