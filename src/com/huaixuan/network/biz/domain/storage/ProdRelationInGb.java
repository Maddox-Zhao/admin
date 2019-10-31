package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class ProdRelationInGb extends BaseObject {
	
	private Long id;
	
	private Long relationId;
	
	private Long inDepId;
	
	private Long goodsInstanceId;
	
	private Long amount;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
	private Long goodsId;
	
	private Long inDetailId;
	
	private Long locId;
	
	private Long supplierId;

	private String batchNum;
	
	private Double selfCost;
	
	private Long depFirstId;
	
	private String storType;
	
	private String isWholesale;
	
	private String tid;
	
	private Long billId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getInDepId() {
		return inDepId;
	}

	public void setInDepId(Long inDepId) {
		this.inDepId = inDepId;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getInDetailId() {
		return inDetailId;
	}

	public void setInDetailId(Long inDetailId) {
		this.inDetailId = inDetailId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Double getSelfCost() {
		return selfCost;
	}

	public void setSelfCost(Double selfCost) {
		this.selfCost = selfCost;
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

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

}
