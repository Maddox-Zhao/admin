package com.huaixuan.network.biz.domain.product;



/**
 * @author Mr_Yang   2016-3-7 下午02:10:41
 * 锁库信息和产品关联
 **/

public class ProductSuoKuProduct
{
	private String idProduct;
	private String sku;
	private Integer brandId;
	private String brandName;
	private Integer seriesId;
	private String seriesName;
	private String type;
	private String material;
	private String color;
	private String size;
	private Double ssPrice; //尚上价
	private Double smPrice;//尚美价
	private String location;//位置
	private Long idLocation;
	private String status;//状态
	private String idStatus;//状态ID
	private String createDate;//锁库创建日期
	private String endDate;//结束日期
	private String inStockDate;//入库时间
	private String customerManager;//客户经理
	private String createUserName;//创建人
	private String createUserId;//创建者ID
	private String idLastOperator;//最后操作人
	private String customerName; //客户

	
	
	
	public String getIdProduct()
	{
		return idProduct;
	}
	public void setIdProduct(String idProduct)
	{
		this.idProduct = idProduct;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public Integer getBrandId()
	{
		return brandId;
	}
	public void setBrandId(Integer brandId)
	{
		this.brandId = brandId;
	}
	public String getBrandName()
	{
		return brandName;
	}
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	public Integer getSeriesId()
	{
		return seriesId;
	}
	public void setSeriesId(Integer seriesId)
	{
		this.seriesId = seriesId;
	}
	public String getSeriesName()
	{
		return seriesName;
	}
	public void setSeriesName(String seriesName)
	{
		this.seriesName = seriesName;
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
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public Double getSsPrice()
	{
		return ssPrice;
	}
	public void setSsPrice(Double ssPrice)
	{
		this.ssPrice = ssPrice;
	}
	public Double getSmPrice()
	{
		return smPrice;
	}
	public void setSmPrice(Double smPrice)
	{
		this.smPrice = smPrice;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getIdStatus()
	{
		return idStatus;
	}
	public void setIdStatus(String idStatus)
	{
		this.idStatus = idStatus;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public String getInStockDate()
	{
		return inStockDate;
	}
	public void setInStockDate(String inStockDate)
	{
		this.inStockDate = inStockDate;
	}
	public String getCustomerManager()
	{
		return customerManager;
	}
	public void setCustomerManager(String customerManager)
	{
		this.customerManager = customerManager;
	}
	public String getCreateUserName()
	{
		return createUserName;
	}
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}
	public String getIdLastOperator()
	{
		return idLastOperator;
	}
	public void setIdLastOperator(String idLastOperator)
	{
		this.idLastOperator = idLastOperator;
	}
	public String getCreateUserId()
	{
		return createUserId;
	}
	public void setCreateUserId(String createUserId)
	{
		this.createUserId = createUserId;
	}
	public String getCustomerName()
	{
		return customerName;
	}
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	public Long getIdLocation()
	{
		return idLocation;
	}
	public void setIdLocation(Long idLocation)
	{
		this.idLocation = idLocation;
	}
	
	
 
}
 
