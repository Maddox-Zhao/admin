package com.huaixuan.network.biz.domain.product;



/**
 * @author Mr_Yang   2016-12-13 上午11:34:08
 * 产品更新日志
 **/

public class ProductUpdateLog
{
	private Long id;
	private String idProduct;
	private String beforSku;
	private String nowSku;	
	
	private Double beforActivePrice;
	private Double nowActivePrice;
	private Double beforSmPrice;
	private Double nowSmprice;
	private String beforType;
	private String nowType;
	private String beforMaterial;
	private String nowMaterial;
	private String beforColor;
	private String nowColor;
	private String beforSize;
	private String nowSize;
	private String updateUserId;
	private String updateUserName;
	private String updateTime;
	private String type; //1-活动价改变 2-sku改变
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getIdProduct()
	{
		return idProduct;
	}
	public void setIdProduct(String idProduct)
	{
		this.idProduct = idProduct;
	}
	public String getBeforSku()
	{
		return beforSku;
	}
	public void setBeforSku(String beforSku)
	{
		this.beforSku = beforSku;
	}
	public String getNowSku()
	{
		return nowSku;
	}
	public void setNowSku(String nowSku)
	{
		this.nowSku = nowSku;
	}
	public Double getBeforActivePrice()
	{
		return beforActivePrice;
	}
	public void setBeforActivePrice(Double beforActivePrice)
	{
		this.beforActivePrice = beforActivePrice;
	}
	public Double getNowActivePrice()
	{
		return nowActivePrice;
	}
	public void setNowActivePrice(Double nowActivePrice)
	{
		this.nowActivePrice = nowActivePrice;
	}
	public Double getBeforSmPrice()
	{
		return beforSmPrice;
	}
	public void setBeforSmPrice(Double beforSmPrice)
	{
		this.beforSmPrice = beforSmPrice;
	}
	public Double getNowSmprice()
	{
		return nowSmprice;
	}
	public void setNowSmprice(Double nowSmprice)
	{
		this.nowSmprice = nowSmprice;
	}
	public String getBeforType()
	{
		return beforType;
	}
	public void setBeforType(String beforType)
	{
		this.beforType = beforType;
	}
	public String getNowType()
	{
		return nowType;
	}
	public void setNowType(String nowType)
	{
		this.nowType = nowType;
	}
	public String getBeforMaterial()
	{
		return beforMaterial;
	}
	public void setBeforMaterial(String beforMaterial)
	{
		this.beforMaterial = beforMaterial;
	}
	public String getNowMaterial()
	{
		return nowMaterial;
	}
	public void setNowMaterial(String nowMaterial)
	{
		this.nowMaterial = nowMaterial;
	}
	public String getBeforColor()
	{
		return beforColor;
	}
	public void setBeforColor(String beforColor)
	{
		this.beforColor = beforColor;
	}
	public String getNowColor()
	{
		return nowColor;
	}
	public void setNowColor(String nowColor)
	{
		this.nowColor = nowColor;
	}
	public String getBeforSize()
	{
		return beforSize;
	}
	public void setBeforSize(String beforSize)
	{
		this.beforSize = beforSize;
	}
	public String getNowSize()
	{
		return nowSize;
	}
	public void setNowSize(String nowSize)
	{
		this.nowSize = nowSize;
	}
	public String getUpdateUserId()
	{
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId)
	{
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName()
	{
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName)
	{
		this.updateUserName = updateUserName;
	}
	public String getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	
	 
	
	
}
 
