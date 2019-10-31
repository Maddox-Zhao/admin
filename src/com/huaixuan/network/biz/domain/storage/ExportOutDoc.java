package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 用于导出出库报表统计
 * @author chenyan 2009/08/17
 */
public class ExportOutDoc extends BaseObject {

	private static final long serialVersionUID = 3115615150446267701L;
	private Date gmtCreate;
	private Long outDetailId;
	private String buyNick;
	private String code;
	private String instanceName;
	private String goodsUnit;
	private String depName;
	private String locName;
	private Long amount;
	private Double unitPrice;
	private Double countPrice;
	private Double earnPrice;
	private Double expressPrice;
	private String payment;
	private String expressName;
	private String memo;

	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Long getOutDetailId() {
		return outDetailId;
	}
	public void setOutDetailId(Long outDetailId) {
		this.outDetailId = outDetailId;
	}
	public String getBuyNick() {
		return buyNick;
	}
	public void setBuyNick(String buyNick) {
		this.buyNick = buyNick;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
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
	public Double getCountPrice() {
		return countPrice;
	}
	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}
	public Double getEarnPrice() {
		return earnPrice;
	}
	public void setEarnPrice(Double earnPrice) {
		this.earnPrice = earnPrice;
	}
	public Double getExpressPrice() {
		return expressPrice;
	}
	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
