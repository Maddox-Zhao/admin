package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class DaiGou
{
	private Long id;		//
	private String daigouCode;		//代购编号
	private String status;		//状态 no_pay 表示未付；part_paid表示已付部分；paid表示已付全款
	private String note;		//备注
	private Long operatorId;		//操作者id
	private Long num;		//总数量
	private BigDecimal amount;		//总计金额
	private BigDecimal paidAmount;		//已支付定金
	private Long payment;		//支付方式
	private Long customerId;		//客户id
	private Long idcards;		//身份证号码
	private String idcardsImage;		//身份证图片
	private String daigouImage;		//收购凭证图片扫描
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
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
