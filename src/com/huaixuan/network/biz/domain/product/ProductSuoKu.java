package com.huaixuan.network.biz.domain.product;




/**
 * @author Mr_Yang   2016-3-4 上午11:56:54
 * 锁库
 **/

public class ProductSuoKu
{
	private Integer id;
	private Long customerId;
	private String createDate; //创建时间
	private String endDate; //锁库结束时间
	private String createUser; //创建者ID
	private  String custonerName; //客户
	private String createUserName;
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Long getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public String getCreateUser()
	{
		return createUser;
	}
	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}
	public String getCustonerName()
	{
		return custonerName;
	}
	public void setCustonerName(String custonerName)
	{
		this.custonerName = custonerName;
	}
	public String getCreateUserName()
	{
		return createUserName;
	}
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}
	
	
	
}
 
