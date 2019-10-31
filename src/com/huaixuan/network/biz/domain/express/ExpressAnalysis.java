package com.huaixuan.network.biz.domain.express;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

public class ExpressAnalysis extends BaseObject implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long	 regionCode;//ʡ
	private Long 	 outDepSum;//����
	private Long     expressId;
	private String   gmtOutDep;//������ʱ�䣬����2010��12�·ݣ�2011��11�·�
	private Double     actualInventorySum;
	private Double     shippingAmountSum;
	private Double     averge;//ƽ�����
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
