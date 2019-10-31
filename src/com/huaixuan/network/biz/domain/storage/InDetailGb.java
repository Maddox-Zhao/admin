package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class InDetailGb extends BaseObject {

	private Long id;
	
	private Long relationId;
	
	private Long goodsInstanceId;
	
	private Long goodsId;
	
	private Long inDepositoryId;
	
	private Long amount;
	
	private Double unitPrice;
	
	private Double dueFee;
	
	private Double factFee;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
	private String status;
	
	private Long storId;
	
	private Long leftNum;
	
	private String storType;
	
	private Long depFirstId;
	
	private Long leftDepNum;
	
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

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getInDepositoryId() {
		return inDepositoryId;
	}

	public void setInDepositoryId(Long inDepositoryId) {
		this.inDepositoryId = inDepositoryId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getDueFee() {
		return dueFee;
	}

	public void setDueFee(Double dueFee) {
		this.dueFee = dueFee;
	}

	public Double getFactFee() {
		return factFee;
	}

	public void setFactFee(Double factFee) {
		this.factFee = factFee;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStorId() {
		return storId;
	}

	public void setStorId(Long storId) {
		this.storId = storId;
	}

	public Long getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(Long leftNum) {
		this.leftNum = leftNum;
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

	public Long getLeftDepNum() {
		return leftDepNum;
	}

	public void setLeftDepNum(Long leftDepNum) {
		this.leftDepNum = leftDepNum;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}
	
}
