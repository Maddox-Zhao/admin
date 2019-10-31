package com.huaixuan.network.biz.domain.taobao;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class TaobaoInterfaceSync  extends BaseObject implements Serializable {

	private long id;

	private Date gmtCreate;

	private Date gmtModify;

	private String interfaceType;

	private Long userId;

	private Long goodsId;

	private Long goodsInstanceId;

	private String status;

	private String itemId;

	private String reason;

	private String title;//商品名称

	private String goodsSn;//商品编码

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

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

    public String getItemId(){
    	return itemId;
    }

    public void setItemId(String itemId){
    	this.itemId = itemId;
    }

    public String getReason(){
    	return reason;
    }

    public void setReason(String reason){
    	this.reason = reason;
    }

}
