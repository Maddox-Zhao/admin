package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class DaiGou
{
	private Long id;		//
	private String daigouCode;		//�������
	private String status;		//״̬ no_pay ��ʾδ����part_paid��ʾ�Ѹ����֣�paid��ʾ�Ѹ�ȫ��
	private String note;		//��ע
	private Long operatorId;		//������id
	private Long num;		//������
	private BigDecimal amount;		//�ܼƽ��
	private BigDecimal paidAmount;		//��֧������
	private Long payment;		//֧����ʽ
	private Long customerId;		//�ͻ�id
	private Long idcards;		//���֤����
	private String idcardsImage;		//���֤ͼƬ
	private String daigouImage;		//�չ�ƾ֤ͼƬɨ��
	private Date gmtCreate;		//����ʱ��
	private Date gmtModify;		//�޸�ʱ��
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
	public String getDaigouCode()
	{
		return daigouCode;
	}
	public void setDaigouCode(String daigouCode)
	{
		this.daigouCode = daigouCode;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public Long getOperatorId()
	{
		return operatorId;
	}
	public void setOperatorId(Long operatorId)
	{
		this.operatorId = operatorId;
	}
	public Long getNum()
	{
		return num;
	}
	public void setNum(Long num)
	{
		this.num = num;
	}
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
	public BigDecimal getPaidAmount()
	{
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount)
	{
		this.paidAmount = paidAmount;
	}
	public Long getPayment()
	{
		return payment;
	}
	public void setPayment(Long payment)
	{
		this.payment = payment;
	}
	public Long getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}
	public Long getIdcards()
	{
		return idcards;
	}
	public void setIdcards(Long idcards)
	{
		this.idcards = idcards;
	}
	public String getIdcardsImage()
	{
		return idcardsImage;
	}
	public void setIdcardsImage(String idcardsImage)
	{
		this.idcardsImage = idcardsImage;
	}
	public String getDaigouImage()
	{
		return daigouImage;
	}
	public void setDaigouImage(String daigouImage)
	{
		this.daigouImage = daigouImage;
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
