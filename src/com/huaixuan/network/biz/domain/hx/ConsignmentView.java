package com.huaixuan.network.biz.domain.hx;

import java.util.Date;



public class ConsignmentView
{
	/*
	 * 2012-02-10 15:11
	 * by Mr_Yang
	 */
	private int id;
	private String conCode;
	private String status;
	private String note;
	private String operatorId; //
	private double amount; //总计金额
	private double paidAmount; //以还金额
	private int customerId;
	private String idcardsImage; //客户身份证图片
	private String 	conImage; //寄卖凭证图片
	private Date gmtCreate; //开单时间，数据库为Date类型
	private Date gmtModify; //修改时间
	private String dateStart;//查询的开始时间
	private String dateEnd;//查询结束时间
	private String fileMsg;//上传图片的信息
	private String idcards; //客户身份证


	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getConCode()
	{
		return conCode;
	}
	public void setConCode(String conCode)
	{
		this.conCode = conCode;
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
	public String getOperatorId()
	{
		return operatorId;
	}
	public void setOperatorId(String operatorId)
	{
		this.operatorId = operatorId;
	}
	public double getAmount()
	{
		return amount;
	}
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	public double getPaidAmount()
	{
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount)
	{
		this.paidAmount = paidAmount;
	}
	public int getCustomerId()
	{
		return customerId;
	}
	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}

	public String getIdcardsImage()
	{
		return idcardsImage;
	}
	public void setIdcardsImage(String idcardsImage)
	{
		this.idcardsImage = idcardsImage;
	}
	public String getConImage()
	{
		return conImage;
	}
	public void setConImage(String conImage)
	{
		this.conImage = conImage;
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
	public String getDateStart()
	{
		return dateStart;
	}
	public void setDateStart(String dateStart)
	{
		this.dateStart = dateStart;
	}
	public String getDateEnd()
	{
		return dateEnd;
	}
	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}
	public String getFileMsg()
	{
		return fileMsg;
	}
	public void setFileMsg(String fileMsg)
	{
		this.fileMsg = fileMsg;
	}
	public String getIdcards()
	{
		return idcards;
	}
	public void setIdcards(String idcards)
	{
		this.idcards = idcards;
	}
	
	
	
	
	
}
