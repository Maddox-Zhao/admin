package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class ProdRelationOutGb extends BaseObject {
	

	private Long id;
	
	private Long relationId;
	
	private Long outDepId;
	
	private Long goodsInstanceId;
	
	private long amount;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
	private Long goodsId;
	
	private Long outDetailId;
	
	private Long storageId;
	
	private Long locId;
	
	private Double selfCost;
	
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

	public Long getOutDepId() {
		return outDepId;
	}

	public void setOutDepId(Long outDepId) {
		this.outDepId = outDepId;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
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

	public Long getOutDetailId() {
		return outDetailId;
	}

	public void setOutDetailId(Long outDetailId) {
		this.outDetailId = outDetailId;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public Double getSelfCost() {
		return selfCost;
	}

	public void setSelfCost(Double selfCost) {
		this.selfCost = selfCost;
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
