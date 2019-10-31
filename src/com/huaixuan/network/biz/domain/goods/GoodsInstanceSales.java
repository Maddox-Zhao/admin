package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;


public class GoodsInstanceSales extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private long goodsInstanceId;

	private String goodsInstanceName;

	private String goodsInstanceCode;

	private Date optTime;

	private double salesAmount;

	private double refundAmount;

	private String name;

	private String desc;

	private long rankNum;

	public long getRankNum() {
		return rankNum;
	}

	public void setRankNum(long rankNum) {
		this.rankNum = rankNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public String getGoodsInstanceName() {
		return goodsInstanceName;
	}

	public void setGoodsInstanceName(String goodsInstanceName) {
		this.goodsInstanceName = goodsInstanceName;
	}

	public String getGoodsInstanceCode() {
		return goodsInstanceCode;
	}

	public void setGoodsInstanceCode(String goodsInstanceCode) {
		this.goodsInstanceCode = goodsInstanceCode;
	}

	public Date getOptTime() {
		return optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public double getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

}
