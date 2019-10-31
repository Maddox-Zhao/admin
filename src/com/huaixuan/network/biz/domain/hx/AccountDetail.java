package com.huaixuan.network.biz.domain.hx;

import java.util.Date;

public class AccountDetail
{
	private Long id;		//业务标识号
	private Long accountId;		//现金表ID
	private Long euroAccount;		//欧元帐户
	private Long hkAccount;		//港元帐户
	private Long rmbAccount;		//人民币帐户
	private Long dollarAccount;		//美元帐户
	private Long type;		//0-代表收入,1-支出
	private Long hrefId; //业务归类属性需要连接的ID
	private String operationType;		//业务归类属性;比如：寄卖××××，收购××××，收据××××，代购××××
	private Long operationId;		//操作者ID
	private String note;		//备注
	private Date gmtCreate;		//创建日期
	private Date gmtModify;		//修改时间
	private Integer accountType; //账户类型 1.欧元 2.港元 3.人民币 4.美元
	private Integer tradePrice; //做帐金额
	private Date dateStart;
	private Date dateEnd;

	
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
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getAccountId()
	{
		return accountId;
	}
	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}
	public Long getEuroAccount()
	{
		return euroAccount;
	}
	public void setEuroAccount(Long euroAccount)
	{
		this.euroAccount = euroAccount;
	}
	public Long getHkAccount()
	{
		return hkAccount;
	}
	public void setHkAccount(Long hkAccount)
	{
		this.hkAccount = hkAccount;
	}
	public Long getRmbAccount()
	{
		return rmbAccount;
	}
	public void setRmbAccount(Long rmbAccount)
	{
		this.rmbAccount = rmbAccount;
	}
	public Long getDollarAccount()
	{
		return dollarAccount;
	}
	public void setDollarAccount(Long dollarAccount)
	{
		this.dollarAccount = dollarAccount;
	}
	public Long getType()
	{
		return type;
	}
	public void setType(Long type)
	{
		this.type = type;
	}
	public String getOperationType()
	{
		return operationType;
	}
	public void setOperationType(String operationType)
	{
		this.operationType = operationType;
	}
	public Long getOperationId()
	{
		return operationId;
	}
	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
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
	public Integer getAccountType()
	{
		return accountType;
	}
	public void setAccountType(Integer accountType)
	{
		this.accountType = accountType;
	}
	public Integer getTradePrice()
	{
		return tradePrice;
	}
	public void setTradePrice(Integer tradePrice)
	{
		this.tradePrice = tradePrice;
	}
	public Long getHrefId()
	{
		return hrefId;
	}
	public void setHrefId(Long hrefId)
	{
		this.hrefId = hrefId;
	}
	

}
