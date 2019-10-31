package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class StorageRefundApply extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;

    private Date gmtCreate;

    private Date gmtModify;

    private String relationNum;

    private long storageId;

    private long refundNum;

    private double refPrice;

    private double factFee;

    private String reason;

    private String memo;

    private String status;

    private String applyUserName;

    private String disposeUserName;

    private Long   locId;

    private Long   depFirstId;

    private String depFirstName;

    private String depLocationName;

    private String depositoryName;

    private String batchNum;

    private String storType;

    private Long   storageNum;

    private double dueFee;

    private String code;

    private String catCode;

    private String attrs;

    private String goodsUnit;

    private String instanceName;

    private String supplierName;

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

	public String getStorType() {
		return storType;
	}

	public void setStorType(String storType) {
		this.storType = storType;
	}

	public Long getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(Long storageNum) {
		this.storageNum = storageNum;
	}

	public double getDueFee() {
		return dueFee;
	}

	public void setDueFee(double dueFee) {
		this.dueFee = dueFee;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDepLocationName() {
		return depLocationName;
	}

	public void setDepLocationName(String depLocationName) {
		this.depLocationName = depLocationName;
	}

	public String getDepositoryName() {
		return depositoryName;
	}

	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getDepFirstName() {
		return depFirstName;
	}

	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getDisposeUserName() {
		return disposeUserName;
	}

	public void setDisposeUserName(String disposeUserName) {
		this.disposeUserName = disposeUserName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getRelationNum() {
		return relationNum;
	}

	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

	public long getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(long refundNum) {
		this.refundNum = refundNum;
	}

	public double getRefPrice() {
		return refPrice;
	}

	public void setRefPrice(double refPrice) {
		this.refPrice = refPrice;
	}

	public double getFactFee() {
		return factFee;
	}

	public void setFactFee(double factFee) {
		this.factFee = factFee;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}


}
