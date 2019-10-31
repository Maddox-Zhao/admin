package com.huaixuan.network.biz.domain.shop;

 import java.io.Serializable;

import com.huaixuan.network.biz.domain.BaseObject;


 public class BrandCategory extends BaseObject implements Serializable {
	 /**
     * 
     */
    private static final long serialVersionUID = 2419661874358049439L;

    private long id;
	 
	 private long brandId;
	 
	 private String catCode;
	 
	 private String status;
	 
	 private String brandName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	 
	 
 }
