package com.huaixuan.network.biz.domain.express;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

public class ExpressAnalysis extends BaseObject implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long	 regionCode;//省
	private Long 	 outDepSum;//单量
	private Long     expressId;
	private String   gmtOutDep;//归属的时间，例如2010年12月份，2011年11月份
	private Double     actualInventorySum;
	private Double     shippingAmountSum;
	private Double     averge;//平均金额
	public Double getActualInventorySum() {
		return actualInventorySum;
	}
	public Long getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	public void setActualInventorySum(Double actualInventorySum) {
		this.actualInventorySum = actualInventorySum;
	}
	public Double getShippingAmountSum() {
		return shippingAmountSum;
	}
	public void setShippingAmountSum(Double shippingAmountSum) {
		this.shippingAmountSum = shippingAmountSum;
	}
	public String getGmtOutDep() {
		return gmtOutDep;
	}
	public void setGmtOutDep(String gmtOutDep) {
		this.gmtOutDep = gmtOutDep;
	}
	public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	public Long getOutDepSum() {
		return outDepSum;
	}
	public void setOutDepSum(Long outDepSum) {
		this.outDepSum = outDepSum;
	}
	public Double getAverge() {
		return averge;
	}
	public void setAverge(Double averge) {
		this.averge = averge;
	}
}
