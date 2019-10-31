package com.huaixuan.network.biz.domain.trade;
/**
 *
 * @author lincf
 *
 * Sep 25, 2009
 */
   public class TradeCode {
	private String regionCodeEndTemp;

	private String payEnd;

	private String payBybank;

	private String payByPeriod;

	private String payByYcPeriod;

	public String getPayBybank() {
		return payBybank;
	}

	public void setPayBybank(String payBybank) {
		this.payBybank = payBybank;
	}

	public String getRegionCodeEndTemp() {
		return regionCodeEndTemp;
	}

	public void setRegionCodeEndTemp(String regionCodeEndTemp) {
		this.regionCodeEndTemp = regionCodeEndTemp;
	}

	public String getPayEnd() {
		return payEnd;
	}

	public void setPayEnd(String payEnd) {
		this.payEnd = payEnd;
	}

	public String getPayByPeriod() {
		return payByPeriod;
	}

	public void setPayByPeriod(String payByPeriod) {
		this.payByPeriod = payByPeriod;
	}

	public String getPayByYcPeriod() {
		return payByYcPeriod;
	}

	public void setPayByYcPeriod(String payByYcPeriod) {
		this.payByYcPeriod = payByYcPeriod;
	}
}