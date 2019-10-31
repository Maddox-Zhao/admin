package com.huaixuan.network.biz.domain.goods;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class PromationGive extends  BaseObject {

	private Long id;
	
	private String modeCode;
	
	private String name;
	
	private String isFreeze;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date gmtCreate;
	
	private Date gmtModify;
	
	private String title;
	
    private String imgMiddle;
    
    private Long goodsInstanceId;
    
    private String goodsInstanceTitle;
    
    private Integer goodsInstanceNum; // 赠送数量
    
    private double goodsWeight; //赠品重量

    private String memo; //提示类型
    
    private Long goodsId;

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Integer getGoodsInstanceNum() {
		return goodsInstanceNum;
	}

	public void setGoodsInstanceNum(Integer goodsInstanceNum) {
		this.goodsInstanceNum = goodsInstanceNum;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public String getGoodsInstanceTitle() {
		return goodsInstanceTitle;
	}

	public void setGoodsInstanceTitle(String goodsInstanceTitle) {
		this.goodsInstanceTitle = goodsInstanceTitle;
	}

	public String getImgMiddle() {
		return imgMiddle;
	}

	public void setImgMiddle(String imgMiddle) {
		this.imgMiddle = imgMiddle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
