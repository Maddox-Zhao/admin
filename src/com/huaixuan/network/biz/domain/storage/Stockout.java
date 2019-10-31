package com.huaixuan.network.biz.domain.storage;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class Stockout extends BaseObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date gmtCreate;
	private Date gmtModify;
	private Long userId;
	private String userEmail;
	private Long goodsId;
	private Long goodsInstanceId;
	private String goodsInstanceName;
	private String notifyStatus;
	private String goodsSn;
	private String goodsDesc;
	private String title;
	private String account;
	private String catCode;
	public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	public String getGoodsInstanceName() {
		return goodsInstanceName;
	}
	public void setGoodsInstanceName(String goodsInstanceName) {
		this.goodsInstanceName = goodsInstanceName;
	}
	public String getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
    public String getGoodsSn() {
        return goodsSn;
    }
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }
    public String getGoodsDesc() {
        return goodsDesc;
    }
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
    public String getCatCode() {
        return catCode;
    }
    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

}
