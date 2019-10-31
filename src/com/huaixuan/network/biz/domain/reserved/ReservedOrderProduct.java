package com.huaixuan.network.biz.domain.reserved;

import java.util.Date;



/**
 * @author Mr_Yang   2016-9-1 下午03:26:54
 * 预开单产品
 **/

public class ReservedOrderProduct
{
	private Long id;		//
	private Long idReserved;		//预开单ID
	private String sku;
	private String idProduct;		//预开单的产品ID
	private String realyIdProduct;		//实际开单的产品ID
	private Double salePrice;		//售价
	private String status;		//1-已提交 2-已开单 3-已取消
	private Date createDate;		//	
	private Long idBrand;
	private Long idSeries;
	private String brandName;
	private String seriesName;
	private String idSite;
	private String siteName;
	private String type;
	private String material;
	private String color;
	private String size;;
	private Double smPrice;
	private Double ssPrice;
	private Long idStatus;
	private Long realyIdStatus;
	
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getIdReserved()
	{
		return idReserved;
	}
	public void setIdReserved(Long idReserved)
	{
		this.idReserved = idReserved;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getIdProduct()
	{
		return idProduct;
	}
	public void setIdProduct(String idProduct)
	{
		this.idProduct = idProduct;
	}
	public String getRealyIdProduct()
	{
		return realyIdProduct;
	}
	public void setRealyIdProduct(String realyIdProduct)
	{
		this.realyIdProduct = realyIdProduct;
	}
	public Double getSalePrice()
	{
		return salePrice;
	}
	public void setSalePrice(Double salePrice)
	{
		this.salePrice = salePrice;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public Long getIdBrand()
	{
		return idBrand;
	}
	public void setIdBrand(Long idBrand)
	{
		this.idBrand = idBrand;
	}
	public Long getIdSeries()
	{
		return idSeries;
	}
	public void setIdSeries(Long idSeries)
	{
		this.idSeries = idSeries;
	}
 
	public String getBrandName()
	{
		return brandName;
	}
	public void setBrandName(String brandName)
	{
		this.brandName = brandName;
	}
	public String getSeriesName()
	{
		return seriesName;
	}
	public void setSeriesName(String seriesName)
	{
		this.seriesName = seriesName;
	}
	public String getIdSite()
	{
		return idSite;
	}
	public void setIdSite(String idSite)
	{
		this.idSite = idSite;
	}
	public String getSiteName()
	{
		return siteName;
	}
	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
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
	public Double getSmPrice()
	{
		return smPrice;
	}
	public void setSmPrice(Double smPrice)
	{
		this.smPrice = smPrice;
	}
	public Double getSsPrice()
	{
		return ssPrice;
	}
	public void setSsPrice(Double ssPrice)
	{
		this.ssPrice = ssPrice;
	}
	public Long getIdStatus()
	{
		return idStatus;
	}
	public void setIdStatus(Long idStatus)
	{
		this.idStatus = idStatus;
	}
	public Long getRealyIdStatus()
	{
		return realyIdStatus;
	}
	public void setRealyIdStatus(Long realyIdStatus)
	{
		this.realyIdStatus = realyIdStatus;
	}
	
	
	

}
 
