package com.huaixuan.network.biz.domain.goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsInstanceSupplier extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 217878271943279580L;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Long              id;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.supplier_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Long              supplierId;

    private String            supplierName;

    private String            supplierCode;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.goods_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Long              goodsId;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.goods_instance_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Long              goodsInstanceId;

    private String            goodsInstanceName;

    private String            code;

    private String            catCode;

    private String            goodsUnit;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.consult_price
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private BigDecimal        consultPrice;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.gmt_create
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Date              gmtCreate;

    /**
     * This field was generated by Abator for iBATIS. This field corresponds to
     * the database column ioss_goods_instance_supplier.gmt_modify
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    private Date              gmtModify;

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.id
     *
     * @return the value of ioss_goods_instance_supplier.id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.id
     *
     * @param id
     *            the value for ioss_goods_instance_supplier.id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.supplier_id
     *
     * @return the value of ioss_goods_instance_supplier.supplier_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.supplier_id
     *
     * @param supplierId
     *            the value for ioss_goods_instance_supplier.supplier_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.goods_id
     *
     * @return the value of ioss_goods_instance_supplier.goods_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.goods_id
     *
     * @param goodsId
     *            the value for ioss_goods_instance_supplier.goods_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column
     * ioss_goods_instance_supplier.goods_instance_id
     *
     * @return the value of ioss_goods_instance_supplier.goods_instance_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column
     * ioss_goods_instance_supplier.goods_instance_id
     *
     * @param goodsInstanceId
     *            the value for ioss_goods_instance_supplier.goods_instance_id
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.consult_price
     *
     * @return the value of ioss_goods_instance_supplier.consult_price
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public BigDecimal getConsultPrice() {
        return consultPrice;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.consult_price
     *
     * @param consultPrice
     *            the value for ioss_goods_instance_supplier.consult_price
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setConsultPrice(BigDecimal consultPrice) {
        this.consultPrice = consultPrice;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.gmt_create
     *
     * @return the value of ioss_goods_instance_supplier.gmt_create
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.gmt_create
     *
     * @param gmtCreate
     *            the value for ioss_goods_instance_supplier.gmt_create
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by Abator for iBATIS. This method returns the
     * value of the database column ioss_goods_instance_supplier.gmt_modify
     *
     * @return the value of ioss_goods_instance_supplier.gmt_modify
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * This method was generated by Abator for iBATIS. This method sets the
     * value of the database column ioss_goods_instance_supplier.gmt_modify
     *
     * @param gmtModify
     *            the value for ioss_goods_instance_supplier.gmt_modify
     *
     * @abatorgenerated Thu Jul 16 10:18:05 CST 2009
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGoodsInstanceName() {
        return goodsInstanceName;
    }

    public void setGoodsInstanceName(String goodsInstanceName) {
        this.goodsInstanceName = goodsInstanceName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

}