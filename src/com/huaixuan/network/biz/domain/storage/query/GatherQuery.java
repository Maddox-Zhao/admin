package com.huaixuan.network.biz.domain.storage.query;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

public class GatherQuery extends BaseObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String billNum;
	private String instanceName;
	private String goodsInstanceCode;
	private String storType;
	private String inDepTimeStart;
	private String inDepTimeEnd;
	private String outDepTimeStart;
	private String outDepTimeEnd;


	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getGoodsInstanceCode() {
		return goodsInstanceCode;
	}
	public void setGoodsInstanceCode(String goodsInstanceCode) {
		this.goodsInstanceCode = goodsInstanceCode;
	}
	public String getStorType() {
		return storType;
	}
	public void setStorType(String storType) {
		this.storType = storType;
	}
	public String getInDepTimeStart() {
		return inDepTimeStart;
	}
	public void setInDepTimeStart(String inDepTimeStart) {
		this.inDepTimeStart = inDepTimeStart;
	}
	public String getInDepTimeEnd() {
		return inDepTimeEnd;
	}
	public void setInDepTimeEnd(String inDepTimeEnd) {
		this.inDepTimeEnd = inDepTimeEnd;
	}
	public String getOutDepTimeStart() {
		return outDepTimeStart;
	}
	public void setOutDepTimeStart(String outDepTimeStart) {
		this.outDepTimeStart = outDepTimeStart;
	}
	public String getOutDepTimeEnd() {
		return outDepTimeEnd;
	}
	public void setOutDepTimeEnd(String outDepTimeEnd) {
		this.outDepTimeEnd = outDepTimeEnd;
	}

}
