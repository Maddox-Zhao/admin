package com.huaixuan.network.biz.domain.hx;

import java.util.Date;

public class AccountDetail
{
	private Long id;		//ҵ���ʶ��
	private Long accountId;		//�ֽ��ID
	private Long euroAccount;		//ŷԪ�ʻ�
	private Long hkAccount;		//��Ԫ�ʻ�
	private Long rmbAccount;		//������ʻ�
	private Long dollarAccount;		//��Ԫ�ʻ�
	private Long type;		//0-��������,1-֧��
	private Long hrefId; //ҵ�����������Ҫ���ӵ�ID
	private String operationType;		//ҵ���������;���磺���������������չ������������վݡ���������������������
	private Long operationId;		//������ID
	private String note;		//��ע
	private Date gmtCreate;		//��������
	private Date gmtModify;		//�޸�ʱ��
	private Integer accountType; //�˻����� 1.ŷԪ 2.��Ԫ 3.����� 4.��Ԫ
	private Integer tradePrice; //���ʽ��
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
