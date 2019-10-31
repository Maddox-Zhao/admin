package com.huaixuan.network.biz.domain.taobao;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class TaobaoInterfaceUserGoods extends BaseObject implements Serializable {

	private long id;

	private Date gmtCreate;

	private Date gmtModify;

	private long userId;

	private long goodsId;

	private String type;

	private String status;

	private String title;

	private double goodsPrice;

	private int   goodsNumber;

	private String goodsStatus;

	private String imgSmall;

	private String goodsSn;

	private long taobaoItemId;

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getImgSmall() {
		return imgSmall;
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTaobaoItemId() {
		return taobaoItemId;
	}

	public void setTaobaoItemId(long taobaoItemId) {
		this.taobaoItemId = taobaoItemId;
	}
}
