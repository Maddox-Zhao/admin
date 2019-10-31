package com.huaixuan.network.biz.domain.hx;

import java.util.Date;

/**
 * 现金表
 */
public class Account
{
	private Long id;		//
	private String accountName;		//帐户名称
	private String accountAddress;		//帐户地址
	private String accountType;		//帐户名称分类
	private String accountContact;		//帐户联系人
	private Long euBalance;		//欧元账户
	private Long hkBalance;		//港元账户
	private Long rmbBalance;		//人名币账户
	private Long dollarBalance;		//美元账户
	private String accountDepcode;		//账户所属部门
	private Date gmtCreate;		//
	private Date gmtModify;		//
	private Date dateStart;
	private Date dateEnd;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getAccountName()
	{
		return accountName;
	}
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}
	public String getAccountAddress()
	{
		return accountAddress;
	}
	public void setAccountAddress(String accountAddress)
	{
		this.accountAddress = accountAddress;
	}
	public String getAccountType()
	{
		return accountType;
	}
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}
	public String getAccountContact()
	{
		return accountContact;
	}
	public void setAccountContact(String accountContact)
	{
		this.accountContact = accountContact;
	}
	public Long getEuBalance()
	{
		return euBalance;
	}
	public void setEuBalance(Long euBalance)
	{
		this.euBalance = euBalance;
	}
	public Long getHkBalance()
	{
		return hkBalance;
	}
	public void setHkBalance(Long hkBalance)
	{
		this.hkBalance = hkBalance;
	}
	public Long getRmbBalance()
	{
		return rmbBalance;
	}
	public void setRmbBalance(Long rmbBalance)
	{
		this.rmbBalance = rmbBalance;
	}
	public Long getDollarBalance()
	{
		return dollarBalance;
	}
	public void setDollarBalance(Long dollarBalance)
	{
		this.dollarBalance = dollarBalance;
	}
	public String getAccountDepcode()
	{
		return accountDepcode;
	}
	public void setAccountDepcode(String accountDepcode)
	{
		this.accountDepcode = accountDepcode;
	}
	public Date getGmtCreate()
	{
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate)
	{
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify()
	{
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify)
	{
		this.gmtModify = gmtModify;
	}
	public Date getDateStart()
	{
		return dateStart;
	}
	public void setDateStart(Date dateStart)
	{
		this.dateStart = dateStart;
	}
	public Date getDateEnd()
	{
		return dateEnd;
	}
	public void setDateEnd(Date dateEnd)
	{
		this.dateEnd = dateEnd;
	}
	
	
	
}
