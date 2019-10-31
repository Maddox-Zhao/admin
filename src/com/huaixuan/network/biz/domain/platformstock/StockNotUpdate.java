package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-11-10 下午01:55:07
 * 不需要更新到平台的sku
 **/

public class StockNotUpdate
{
	
	private Long id;
	private String sku;
	private String platform; //平台
	private String type ;//sh-国内   hk-海外
	private String createUserName;
	private String createUserId;
	private String createTime;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getPlatform()
	{
		return platform;
	}
	public void setPlatform(String platform)
	{
		this.platform = platform;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getCreateUserName()
	{
		return createUserName;
	}
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}
	public String getCreateUserId()
	{
		return createUserId;
	}
	public void setCreateUserId(String createUserId)
	{
		this.createUserId = createUserId;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	 
	
	
	
	
}
 
