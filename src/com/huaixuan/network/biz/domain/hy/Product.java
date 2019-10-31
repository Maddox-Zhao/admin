package com.huaixuan.network.biz.domain.hy;

 import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * 
  * 20111027  songfy
  * @version 3.2.0
  */
 public class Product extends BaseObject implements Serializable {
 	
	private static final long serialVersionUID = 5344983412246993305L;
	/* @property: */
	 private Long idBrand;
	 /* @property: */
	 private Long idSeries;
	 /* @property: */
	 private String brandname;
	 
	 private String seriesname;
	 
	 private String size;
	 
	 private Long targetCustomers;
	 
	 private String color;
	
	 private String material;
	 
	 private Long idLocation;
	 
	 private Long idLifeCycle;
	
	 private Long idPurchase;
	 
	 private String type;
	 
     private Long idProduct;
     
     private String picture; 
     //后台查询开始日期
     private String dateStart;
     //后台查询结束日期
     private String dateEnd;
     
     private Long idStatus;
     
     private Double cost;
     
     private int idCostCurrency;
     
     private Double hxPrice;
     
     private Double	hkhxPrice;
     
     private Double cnPrice;
     
     private Double	euPrice;
     
     private Double	hkPrice;
     
     private Long goodsId;
     
     private Long instanceId;
     
     private Date gmtCreate;
 	
     private Date gmtModify;
     
     private Date gmtExport;
     
     private Double price;
	 
	 private String  priceCurrency;
     
	 /* Default constructor - creates a new instance with no values set. */
	 public Product(){}
	 /* @model: */

	public Long getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getTargetCustomers() {
		return targetCustomers;
	}

	public void setTargetCustomers(Long targetCustomers) {
		this.targetCustomers = targetCustomers;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Long getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	public Long getIdLifeCycle() {
		return idLifeCycle;
	}

	public void setIdLifeCycle(Long idLifeCycle) {
		this.idLifeCycle = idLifeCycle;
	}

	public Long getIdPurchase() {
		return idPurchase;
	}

	public void setIdPurchase(Long idPurchase) {
		this.idPurchase = idPurchase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brandname == null) ? 0 : brandname.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result
				+ ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((idBrand == null) ? 0 : idBrand.hashCode());
		result = prime * result
				+ ((idLifeCycle == null) ? 0 : idLifeCycle.hashCode());
		result = prime * result
				+ ((idLocation == null) ? 0 : idLocation.hashCode());
		result = prime * result
				+ ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result
				+ ((idPurchase == null) ? 0 : idPurchase.hashCode());
		result = prime * result
				+ ((idSeries == null) ? 0 : idSeries.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result
				+ ((seriesname == null) ? 0 : seriesname.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result
				+ ((targetCustomers == null) ? 0 : targetCustomers.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (brandname == null) {
			if (other.brandname != null)
				return false;
		} else if (!brandname.equals(other.brandname))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (idBrand == null) {
			if (other.idBrand != null)
				return false;
		} else if (!idBrand.equals(other.idBrand))
			return false;
		if (idLifeCycle == null) {
			if (other.idLifeCycle != null)
				return false;
		} else if (!idLifeCycle.equals(other.idLifeCycle))
			return false;
		if (idLocation == null) {
			if (other.idLocation != null)
				return false;
		} else if (!idLocation.equals(other.idLocation))
			return false;
		if (idProduct == null) {
			if (other.idProduct != null)
				return false;
		} else if (!idProduct.equals(other.idProduct))
			return false;
		if (idPurchase == null) {
			if (other.idPurchase != null)
				return false;
		} else if (!idPurchase.equals(other.idPurchase))
			return false;
		if (idSeries == null) {
			if (other.idSeries != null)
				return false;
		} else if (!idSeries.equals(other.idSeries))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (seriesname == null) {
			if (other.seriesname != null)
				return false;
		} else if (!seriesname.equals(other.seriesname))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (targetCustomers == null) {
			if (other.targetCustomers != null)
				return false;
		} else if (!targetCustomers.equals(other.targetCustomers))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public int getIdCostCurrency() {
		return idCostCurrency;
	}

	public void setIdCostCurrency(int idCostCurrency) {
		this.idCostCurrency = idCostCurrency;
	}

	public Double getHxPrice() {
		return hxPrice;
	}

	public void setHxPrice(Double hxPrice) {
		this.hxPrice = hxPrice;
	}

	public Double getHkhxPrice() {
		return hkhxPrice;
	}

	public void setHkhxPrice(Double hkhxPrice) {
		this.hkhxPrice = hkhxPrice;
	}

	public Double getCnPrice() {
		return cnPrice;
	}

	public void setCnPrice(Double cnPrice) {
		this.cnPrice = cnPrice;
	}

	public Double getEuPrice() {
		return euPrice;
	}

	public void setEuPrice(Double euPrice) {
		this.euPrice = euPrice;
	}

	public Double getHkPrice() {
		return hkPrice;
	}

	public void setHkPrice(Double hkPrice) {
		this.hkPrice = hkPrice;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Date getGmtExport() {
		return gmtExport;
	}

	public void setGmtExport(Date gmtExport) {
		this.gmtExport = gmtExport;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
	}

 }
