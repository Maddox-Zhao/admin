package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class RefundOrder extends BaseObject implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 2768959458759758384L;

	/* @property: */
	private long id;

	/* @property: */
	private String refundId;

	/* @property: */
	private long customerId;

	/* @property: */
	private String customerName;

	/* @property: */
	private long goodsId;

	private String goodsTitle;

	/* @property: */
	private long goodsInstanceId;

	private String goodsInstanceName;

	/* @property: */
	private long refAmount;

	/* @property: */
	private double price;

	/* @property: */
	private double dueFee;

	/* @property: */
	private double factFee;

	/* @property: */
	private String reason;

	/* @property: */
	private String create;

	/* @property: */
	private Date gmtModify;

	/* @property: */
	private Date gmtCreate;

	/* @property: */
	private String remark;

	/* @property: */
	private long orderId;

	private String goodsAttr;

	private String buyAttr;

	private String goodsSn;

	private long refAmountLeft;

	private long refundOrderId;

	private double goodsPrice;

	/* @property: */
	private double marketPrice;

	private Double agentSellPrice; // 代销用户淘宝销售价

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getAgentSellPrice() {
		return agentSellPrice;
	}

	public void setAgentSellPrice(Double agentSellPrice) {
		this.agentSellPrice = agentSellPrice;
	}

	public String getBuyAttr() {
		return buyAttr;
	}

	public void setBuyAttr(String buyAttr) {
		this.buyAttr = buyAttr;
	}

	public String getGoodsAttr() {
		return goodsAttr;
	}

	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}

	/* Default constructor - creates a new instance with no values set. */
	public RefundOrder() {
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
	public void setRefundId(String obj) {
		this.refundId = obj;
	}

	/* @model: */
	public String getRefundId() {
		return this.refundId;
	}

	/* @model: */
	public void setCustomerId(long obj) {
		this.customerId = obj;
	}

	/* @model: */
	public long getCustomerId() {
		return this.customerId;
	}

	/* @model: */
	public void setCustomerName(String obj) {
		this.customerName = obj;
	}

	/* @model: */
	public String getCustomerName() {
		return this.customerName;
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
	public void setRefAmount(long obj) {
		this.refAmount = obj;
	}

	/* @model: */
	public long getRefAmount() {
		return this.refAmount;
	}

	/* @model: */
	public void setPrice(double obj) {
		this.price = obj;
	}

	/* @model: */
	public double getPrice() {
		return this.price;
	}

	/* @model: */
	public void setDueFee(double obj) {
		this.dueFee = obj;
	}

	/* @model: */
	public double getDueFee() {
		return this.dueFee;
	}

	/* @model: */
	public void setFactFee(double obj) {
		this.factFee = obj;
	}

	/* @model: */
	public double getFactFee() {
		return this.factFee;
	}

	/* @model: */
	public void setReason(String obj) {
		this.reason = obj;
	}

	/* @model: */
	public String getReason() {
		return this.reason;
	}

	/* @model: */
	public void setCreate(String obj) {
		this.create = obj;
	}

	/* @model: */
	public String getCreate() {
		return this.create;
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
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model: */
	public Date getGmtCreate() {
		return this.gmtCreate;
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
	public void setOrderId(long obj) {
		this.orderId = obj;
	}

	/* @model: */
	public long getOrderId() {
		return this.orderId;
	}

	public String getGoodsInstanceName() {
		return goodsInstanceName;
	}

	public void setGoodsInstanceName(String goodsInstanceName) {
		this.goodsInstanceName = goodsInstanceName;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public long getRefAmountLeft() {
		return refAmountLeft;
	}

	public void setRefAmountLeft(long refAmountLeft) {
		this.refAmountLeft = refAmountLeft;
	}

	public long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

}
