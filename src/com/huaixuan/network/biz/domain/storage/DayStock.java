package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;

public class DayStock extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = -5058723490361404002L;
	
	private String tid;
	
	private String catCode;

	private String goodsName;
	
	private Long storageNum;
	
	private Long amountTotal;
	
    private String name;
	
	private String locName;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getStorageNum() {
		return storageNum;
	}

	public void setStorageNum(Long storageNum) {
		this.storageNum = storageNum;
	}

	public Long getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Long amountTotal) {
		this.amountTotal = amountTotal;
	}


	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
