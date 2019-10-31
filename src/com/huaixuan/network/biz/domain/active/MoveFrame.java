package com.huaixuan.network.biz.domain.active;

import java.util.Date;

/**
 *2012-6-19 上午10:47:12
 *Mr_Yang
 */
public class MoveFrame
{
	private Long id;		//
	private String name;		//活动框名字
	private String status;		//状态 默认‘open’开启；‘closed’已结束；‘wait’未开始
	private Long area;		//活动区域；1-表示大陆，2-表示香港
	private Long headAdminId;		//负责人
	private String note;		//备注
	private String depCodes;		//部门code，多个部门code用 ‘;’号相隔
	private String depNames;		//部门名称，多个部门名称用 ‘;’号相隔
	private String adminIds;		//员工id，多个员工id用 ‘;’号相隔
	private String adminNames;		//
	private String customerIds;		//客户id，多个客户id用 ‘;’号相隔
	private Double customerRate;		//客户折扣率（添加客户时才有折扣率）
	private Long customerType;		//客户类型
	private Double amount;		//产品总金额
	private Double discountAmount;		//产品折后总金额
	private Double soldAmount;		//当前已售金额
	private Double frameAmount;		//活动已售金额
	private Long productNum;		//产品总数
	private Long soldProduct;		//当前已售总数
	private Long frameProduct;		//活动已售总数
	private Long idcurrency;		//币种（预留）
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
	private Date gmtStart;		//活动开始时间
	private Date gmtEnd;		//活动结束时间
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Long getArea()
	{
		return area;
	}
	public void setArea(Long area)
	{
		this.area = area;
	}
	public Long getHeadAdminId()
	{
		return headAdminId;
	}
	public void setHeadAdminId(Long headAdminId)
	{
		this.headAdminId = headAdminId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getDepCodes()
	{
		return depCodes;
	}
	public void setDepCodes(String depCodes)
	{
		this.depCodes = depCodes;
	}
	public String getDepNames()
	{
		return depNames;
	}
	public void setDepNames(String depNames)
	{
		this.depNames = depNames;
	}
	public String getAdminIds()
	{
		return adminIds;
	}
	public void setAdminIds(String adminIds)
	{
		this.adminIds = adminIds;
	}
	public String getAdminNames()
	{
		return adminNames;
	}
	public void setAdminNames(String adminNames)
	{
		this.adminNames = adminNames;
	}
	public String getCustomerIds()
	{
		return customerIds;
	}
	public void setCustomerIds(String customerIds)
	{
		this.customerIds = customerIds;
	}
	public Double getCustomerRate()
	{
		return customerRate;
	}
	public void setCustomerRate(Double customerRate)
	{
		this.customerRate = customerRate;
	}
	public Long getCustomerType()
	{
		return customerType;
	}
	public void setCustomerType(Long customerType)
	{
		this.customerType = customerType;
	}
	public Double getAmount()
	{
		return amount;
	}
	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
	public Double getDiscountAmount()
	{
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount)
	{
		this.discountAmount = discountAmount;
	}
	public Double getSoldAmount()
	{
		return soldAmount;
	}
	public void setSoldAmount(Double soldAmount)
	{
		this.soldAmount = soldAmount;
	}
	public Double getFrameAmount()
	{
		return frameAmount;
	}
	public void setFrameAmount(Double frameAmount)
	{
		this.frameAmount = frameAmount;
	}
	public Long getProductNum()
	{
		return productNum;
	}
	public void setProductNum(Long productNum)
	{
		this.productNum = productNum;
	}
	public Long getSoldProduct()
	{
		return soldProduct;
	}
	public void setSoldProduct(Long soldProduct)
	{
		this.soldProduct = soldProduct;
	}
	public Long getFrameProduct()
	{
		return frameProduct;
	}
	public void setFrameProduct(Long frameProduct)
	{
		this.frameProduct = frameProduct;
	}
	public Long getIdcurrency()
	{
		return idcurrency;
	}
	public void setIdcurrency(Long idcurrency)
	{
		this.idcurrency = idcurrency;
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
	public Date getGmtStart()
	{
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart)
	{
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd()
	{
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd)
	{
		this.gmtEnd = gmtEnd;
	}
	
	

}
