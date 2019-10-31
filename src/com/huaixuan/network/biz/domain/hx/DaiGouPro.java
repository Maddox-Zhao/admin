package com.huaixuan.network.biz.domain.hx;

import java.math.BigDecimal;
import java.util.Date;

public class DaiGouPro
{
	private Long id;		//
	private Long daigouId;		//代购id  外键
	private String note;		//备注
	private BigDecimal amount;		//小计金额
	private BigDecimal paidAmount;		//已付金额
	private BigDecimal price;		//单价
	private BigDecimal highPrice;		//最高承受单价
	private Long num;		//数量
	private BigDecimal commission;		//佣金
	private Long brandId;		//品牌id
	private Long seriesId;		//品名id
	private String type;		//型号
	private String material;		//材质
	private String color;		//颜色
	private String daigouCode;		//代购编号
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
	public Long getDaigouId()
	{
		return daigouId;
	}
	public void setDaigouId(Long daigouId)
	{
		this.daigouId = daigouId;
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
	public BigDecimal getPrice()
	{
		return price;
	}
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}
	public BigDecimal getHighPrice()
	{
		return highPrice;
	}
	public void setHighPrice(BigDecimal highPrice)
	{
		this.highPrice = highPrice;
	}
	public Long getNum()
	{
		return num;
	}
	public void setNum(Long num)
	{
		this.num = num;
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
	public String getDaigouCode()
	{
		return daigouCode;
	}
	public void setDaigouCode(String daigouCode)
	{
		this.daigouCode = daigouCode;
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
