package com.huaixuan.network.biz.domain.hx;

import java.util.Date;

/**
 * �ֽ��
 */
public class Account
{
	private Long id;		//
	private String accountName;		//�ʻ�����
	private String accountAddress;		//�ʻ���ַ
	private String accountType;		//�ʻ����Ʒ���
	private String accountContact;		//�ʻ���ϵ��
	private Long euBalance;		//ŷԪ�˻�
	private Long hkBalance;		//��Ԫ�˻�
	private Long rmbBalance;		//�������˻�
	private Long dollarBalance;		//��Ԫ�˻�
	private String accountDepcode;		//�˻���������
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
