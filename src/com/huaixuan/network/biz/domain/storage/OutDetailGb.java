package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class OutDetailGb extends BaseObject {
	
	private Long id;
	
	private Long relationId;
	
	private Long goodsInstanceId;
	
	private Long goodsId;
	
	private Long outDepositoryId;
	
	private long outNum;
	
	private Double unitPrice;
	
	private Double dueFee;
	
	private Double factFee;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
	private String status;
	
	private Long leftNum;
	
	private String relationNum;
	
	private Long depFirstId;
	
	private String storType;
	
	private Long outVirtualNum;
	
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

	public Long getOutDepositoryId() {
		return outDepositoryId;
	}

	public void setOutDepositoryId(Long outDepositoryId) {
		this.outDepositoryId = outDepositoryId;
	}

	public long getOutNum() {
		return outNum;
	}

	public void setOutNum(long outNum) {
		this.outNum = outNum;
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

	public Long getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(Long leftNum) {
		this.leftNum = leftNum;
	}

	public String getRelationNum() {
		return relationNum;
	}

	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
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

	public Long getOutVirtualNum() {
		return outVirtualNum;
	}

	public void setOutVirtualNum(Long outVirtualNum) {
		this.outVirtualNum = outVirtualNum;
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
