package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;
import java.util.Date;

public class ShoppingDetailGb implements Serializable {

	private Long id;

	private Long relationId;
	
	private Long shoppingId;
	
	private Long goodsId;
	
	private Long goodsInstanceId;
	
	private String units;
	
	private Long amount;
	
	private Double unitPrice;
	
	private Double dueFee;
	
	private Double factFee;
	
	private Long rejectNum;
	
	private Long missingNum;
	
	private Long receiveNum;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
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

	public Long getShoppingId() {
		return shoppingId;
	}

	public void setShoppingId(Long shoppingId) {
		this.shoppingId = shoppingId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
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

	public Long getRejectNum() {
		return rejectNum;
	}

	public void setRejectNum(Long rejectNum) {
		this.rejectNum = rejectNum;
	}

	public Long getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(Long missingNum) {
		this.missingNum = missingNum;
	}

	public Long getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Long receiveNum) {
		this.receiveNum = receiveNum;
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

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}
	
}
