package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class AcquisitionPro
{
	private Long id;		//
	private Long acqId;		//收购id  外键
	private String note;		//备注
	private BigDecimal amount;		//金额
	private BigDecimal paidAmount;		//已付金额
	private BigDecimal commission;		//佣金
	private Long brandId;		//品牌id
	private Long seriesId;		//品名id
	private String type;		//型号
	private String material;		//材质
	private String color;		//颜色
	private String uuid;		//
	private String auuid;		//
	private String level;		//等级
	private String acqCode;		//收购编号
	private String pic;		//产品图片
	private String status;		//状态  wait_sell表示未售 sold表示销售掉了
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
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
