package com.huaixuan.network.biz.domain.storage;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsForLocation extends BaseObject {

	private static final long serialVersionUID = 1754910375923982923L;

	private Long inDetailId;
	private Long locationId;
	private String locName;
	private Long supplierId;
	private String supplierName;
	private String batchNum;
	private String oriAmount;
	private String oriAmountForDis;
	private String oriLocIdForDis;
	private String oriDepIdForDis;
	private String depName;
	private Long depId;
	private Double price;
	private String storType;


	public Long getDepId() {
		return depId;
	}
	public void setDepId(Long depId) {
		this.depId = depId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getOriAmountForDis() {
		return oriAmountForDis;
	}
	public void setOriAmountForDis(String oriAmountForDis) {
		this.oriAmountForDis = oriAmountForDis;
	}
	public String getOriLocIdForDis() {
		return oriLocIdForDis;
	}
	public void setOriLocIdForDis(String oriLocIdForDis) {
		this.oriLocIdForDis = oriLocIdForDis;
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getOriAmount() {
		return oriAmount;
	}
	public void setOriAmount(String oriAmount) {
		this.oriAmount = oriAmount;
	}
	public Long getInDetailId() {
		return inDetailId;
	}
	public void setInDetailId(Long inDetailId) {
		this.inDetailId = inDetailId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
    public String getStorType() {
        return storType;
    }
    public void setStorType(String storType) {
        this.storType = storType;
    }
    public String getOriDepIdForDis() {
        return oriDepIdForDis;
    }
    public void setOriDepIdForDis(String oriDepIdForDis) {
        this.oriDepIdForDis = oriDepIdForDis;
    }
}
