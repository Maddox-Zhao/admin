package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class GoodsWholsale extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date gmtCreate;

	private Date gmtModify;

	private Long goodsId;

	private int wholesaleLevel;

	private Long startNum;

	private Long endNum;

	private double wholesalePrice;

	private String isCheck;

	private String goodsUnit;

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public int getWholesaleLevel() {
		return wholesaleLevel;
	}

	public void setWholesaleLevel(int wholesaleLevel) {
		this.wholesaleLevel = wholesaleLevel;
	}

	public Long getStartNum() {
		return startNum;
	}

	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	public Long getEndNum() {
		return endNum;
	}

	public void setEndNum(Long endNum) {
		this.endNum = endNum;
	}

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
}
