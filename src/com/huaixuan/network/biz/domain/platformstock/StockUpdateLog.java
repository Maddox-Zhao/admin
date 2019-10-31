package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-10-27 下午01:56:22
 * 库存同步日志
 **/

public class StockUpdateLog
{
	private String sku;
	private String location;
	private String psku;
	private String name;
	private int stock;
	private String type;
	private String error;
	private String updateTime;
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getPsku()
	{
		return psku;
	}
	public void setPsku(String psku)
	{
		this.psku = psku;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getStock()
	{
		return stock;
	}
	public void setStock(int stock)
	{
		this.stock = stock;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getError()
	{
		return error;
	}
	public void setError(String error)
	{
		this.error = error;
	}
	public String getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
	
	
	
}
 
