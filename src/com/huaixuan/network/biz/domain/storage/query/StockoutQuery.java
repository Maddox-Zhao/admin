package com.huaixuan.network.biz.domain.storage.query;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

public class StockoutQuery extends BaseObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String goodsSn;
	private String optTimeStart;
	private String optTimeEnd;
	private String notifyStatus;
	private String catCode;


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getOptTimeStart() {
		return optTimeStart;
	}
	public void setOptTimeStart(String optTimeStart) {
		this.optTimeStart = optTimeStart;
	}
	public String getOptTimeEnd() {
		return optTimeEnd;
	}
	public void setOptTimeEnd(String optTimeEnd) {
		this.optTimeEnd = optTimeEnd;
	}
	public String getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public String getCatCode() {
		return catCode;
	}
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

}
