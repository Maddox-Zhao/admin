package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class AcquisitionPro
{
	private Long id;		//
	private Long acqId;		//�չ�id  ���
	private String note;		//��ע
	private BigDecimal amount;		//���
	private BigDecimal paidAmount;		//�Ѹ����
	private BigDecimal commission;		//Ӷ��
	private Long brandId;		//Ʒ��id
	private Long seriesId;		//Ʒ��id
	private String type;		//�ͺ�
	private String material;		//����
	private String color;		//��ɫ
	private String uuid;		//
	private String auuid;		//
	private String level;		//�ȼ�
	private String acqCode;		//�չ����
	private String pic;		//��ƷͼƬ
	private String status;		//״̬  wait_sell��ʾδ�� sold��ʾ���۵���
	private Date gmtCreate;		//����ʱ��
	private Date gmtModify;		//�޸�ʱ��
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getAcqId()
	{
		return acqId;
	}
	public void setAcqId(Long acqId)
	{
		this.acqId = acqId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
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
	public BigDecimal getCommission()
	{
		return commission;
	}
	public void setCommission(BigDecimal commission)
	{
		this.commission = commission;
	}
	public Long getBrandId()
	{
		return brandId;
	}
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	public Long getSeriesId()
	{
		return seriesId;
	}
	public void setSeriesId(Long seriesId)
	{
		this.seriesId = seriesId;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getMaterial()
	{
		return material;
	}
	public void setMaterial(String material)
	{
		this.material = material;
	}
	public String getColor()
	{
		return color;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	public String getUuid()
	{
		return uuid;
	}
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	public String getAuuid()
	{
		return auuid;
	}
	public void setAuuid(String auuid)
	{
		this.auuid = auuid;
	}
	public String getLevel()
	{
		return level;
	}
	public void setLevel(String level)
	{
		this.level = level;
	}
	public String getAcqCode()
	{
		return acqCode;
	}
	public void setAcqCode(String acqCode)
	{
		this.acqCode = acqCode;
	}
	public String getPic()
	{
		return pic;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
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

}
