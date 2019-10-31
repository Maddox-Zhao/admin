package com.huaixuan.network.biz.domain.hy;

 import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

 /**
  * 
  * 20111027  songfy
  * @version 3.2.0
  */
 public class ProductViewAll extends BaseObject implements Serializable {
 		 
	private static final long serialVersionUID = -457181355269204402L;
	/* @property: */
	 private Long idLifeCycle;
	 /* @property: */
	 private Long idProduct;
	 /* @property: */
	 private Double cost;
	 
	 private String costCurrency;
	 
	 private Long idBrand;
	 
	 private String brand;
	
	 private Long idSeries;
	 
	 private String series;
	 
	 private String type;
     
	 private String color;
	 
	 private String material;
	 
	 private String size;
	 
	 private Long hasValidCard;
	 
	 private Long idPurchase;
	 
	 private Double hXPrice;
	 
	 private Double hKHXPrice;
	 
	 private Long curSiteId;
	 
	 private Long lastSiteId;
	 
	 private String site;
	 
	 private String city;
	 
	 private String status;
	 
	 private Long idStatus;
	 
	 private Double price;
	 
	 private Double euPrice;
	 
	 private String  priceCurrency;
	 
	 private String  inDate;
	 
	 private Long  idsupply;
     
	 private Long  idchannel;
	 
	 private String  sellchannel;
	 
	 private Long  idlastoperator;
	 
	 private Date  selldate;
	 
	 private Date  date;
	 private String datestr;
	 
	 private String  dateEnd;
	 
	 private String  dateStart;
	 
	 private Double hxPSum;
	 
	 private Double hkhxPSum;
	 
	 private Double priceSum;
	 
	 private String  priceCurrencySum;
	 
	 private Double costAll;
	 
	 private String  storeDateStart;
	 
	 private String  storeDateEnd;
	 
	 private Integer idProductHistory;
	 
	 private Integer hkGoodsNumber;
	 
	 private String imgLarge;
	 
	 private String imgMiddle;
	 
	 private Integer idOrder;
	 
	 private String customerName;
	 
	 /* Default constructor - creates a new instance with no values set. */
	 public ProductViewAll(){}
	 /* @model: */

	public Long getIdLifeCycle() {
		return idLifeCycle;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getEuPrice() {
		return euPrice;
	}

	public void setEuPrice(Double euPrice) {
		this.euPrice = euPrice;
	}

	public Integer getHkGoodsNumber() {
		return hkGoodsNumber;
	}

	public void setHkGoodsNumber(Integer hkGoodsNumber) {
		this.hkGoodsNumber = hkGoodsNumber;
	}

	public String getImgLarge() {
		return imgLarge;
	}

	public void setImgLarge(String imgLarge) {
		this.imgLarge = imgLarge;
	}

	public String getImgMiddle() {
		return imgMiddle;
	}

	public void setImgMiddle(String imgMiddle) {
		this.imgMiddle = imgMiddle;
	}

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}

	public Integer getIdProductHistory() {
		return idProductHistory;
	}

	public void setIdProductHistory(Integer idProductHistory) {
		this.idProductHistory = idProductHistory;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setIdLifeCycle(Long idLifeCycle) {
		this.idLifeCycle = idLifeCycle;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getCostCurrency() {
		return costCurrency;
	}

	public void setCostCurrency(String costCurrency) {
		this.costCurrency = costCurrency;
	}

	public Long getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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


	public Long getHasValidCard() {
		return hasValidCard;
	}

	public void setHasValidCard(Long hasValidCard) {
		this.hasValidCard = hasValidCard;
	}

	public Long getIdPurchase() {
		return idPurchase;
	}

	public void setIdPurchase(Long idPurchase) {
		this.idPurchase = idPurchase;
	}

	public Long getCurSiteId() {
		return curSiteId;
	}

	public void setCurSiteId(Long curSiteId) {
		this.curSiteId = curSiteId;
	}

	public Long getLastSiteId() {
		return lastSiteId;
	}

	public void setLastSiteId(Long lastSiteId) {
		this.lastSiteId = lastSiteId;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
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

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public Long getIdsupply() {
		return idsupply;
	}

	public void setIdsupply(Long idsupply) {
		this.idsupply = idsupply;
	}

	public Long getIdchannel() {
		return idchannel;
	}

	public void setIdchannel(Long idchannel) {
		this.idchannel = idchannel;
	}

	public String getSellchannel() {
		return sellchannel;
	}

	public void setSellchannel(String sellchannel) {
		this.sellchannel = sellchannel;
	}

	public Long getIdlastoperator() {
		return idlastoperator;
	}

	public void setIdlastoperator(Long idlastoperator) {
		this.idlastoperator = idlastoperator;
	}

	public Date getSelldate() {
		return selldate;
	}

	public void setSelldate(Date selldate) {
		this.selldate = selldate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getHXPrice() {
		return hXPrice;
	}

	public void setHXPrice(Double price) {
		hXPrice = price;
	}

	public Double getHKHXPrice() {
		return hKHXPrice;
	}

	public void setHKHXPrice(Double price) {
		hKHXPrice = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result
				+ ((costCurrency == null) ? 0 : costCurrency.hashCode());
		result = prime * result
				+ ((curSiteId == null) ? 0 : curSiteId.hashCode());
		result = prime * result
				+ ((hKHXPrice == null) ? 0 : hKHXPrice.hashCode());
		result = prime * result + ((hXPrice == null) ? 0 : hXPrice.hashCode());
		result = prime * result
				+ ((hasValidCard == null) ? 0 : hasValidCard.hashCode());
		result = prime * result + ((idBrand == null) ? 0 : idBrand.hashCode());
		result = prime * result
				+ ((idLifeCycle == null) ? 0 : idLifeCycle.hashCode());
		result = prime * result
				+ ((idProduct == null) ? 0 : idProduct.hashCode());
		result = prime * result
				+ ((idPurchase == null) ? 0 : idPurchase.hashCode());
		result = prime * result
				+ ((idSeries == null) ? 0 : idSeries.hashCode());
		result = prime * result
				+ ((idStatus == null) ? 0 : idStatus.hashCode());
		result = prime * result
				+ ((idchannel == null) ? 0 : idchannel.hashCode());
		result = prime * result
				+ ((idlastoperator == null) ? 0 : idlastoperator.hashCode());
		result = prime * result
				+ ((idsupply == null) ? 0 : idsupply.hashCode());
		result = prime * result + ((inDate == null) ? 0 : inDate.hashCode());
		result = prime * result
				+ ((lastSiteId == null) ? 0 : lastSiteId.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((priceCurrency == null) ? 0 : priceCurrency.hashCode());
		result = prime * result
				+ ((sellchannel == null) ? 0 : sellchannel.hashCode());
		result = prime * result
				+ ((selldate == null) ? 0 : selldate.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ProductViewAll other = (ProductViewAll) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (costCurrency == null) {
			if (other.costCurrency != null)
				return false;
		} else if (!costCurrency.equals(other.costCurrency))
			return false;
		if (curSiteId == null) {
			if (other.curSiteId != null)
				return false;
		} else if (!curSiteId.equals(other.curSiteId))
			return false;
		if (hKHXPrice == null) {
			if (other.hKHXPrice != null)
				return false;
		} else if (!hKHXPrice.equals(other.hKHXPrice))
			return false;
		if (hXPrice == null) {
			if (other.hXPrice != null)
				return false;
		} else if (!hXPrice.equals(other.hXPrice))
			return false;
		if (hasValidCard == null) {
			if (other.hasValidCard != null)
				return false;
		} else if (!hasValidCard.equals(other.hasValidCard))
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
		if (idStatus == null) {
			if (other.idStatus != null)
				return false;
		} else if (!idStatus.equals(other.idStatus))
			return false;
		if (idchannel == null) {
			if (other.idchannel != null)
				return false;
		} else if (!idchannel.equals(other.idchannel))
			return false;
		if (idlastoperator == null) {
			if (other.idlastoperator != null)
				return false;
		} else if (!idlastoperator.equals(other.idlastoperator))
			return false;
		if (idsupply == null) {
			if (other.idsupply != null)
				return false;
		} else if (!idsupply.equals(other.idsupply))
			return false;
		if (inDate == null) {
			if (other.inDate != null)
				return false;
		} else if (!inDate.equals(other.inDate))
			return false;
		if (lastSiteId == null) {
			if (other.lastSiteId != null)
				return false;
		} else if (!lastSiteId.equals(other.lastSiteId))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (priceCurrency == null) {
			if (other.priceCurrency != null)
				return false;
		} else if (!priceCurrency.equals(other.priceCurrency))
			return false;
		if (sellchannel == null) {
			if (other.sellchannel != null)
				return false;
		} else if (!sellchannel.equals(other.sellchannel))
			return false;
		if (selldate == null) {
			if (other.selldate != null)
				return false;
		} else if (!selldate.equals(other.selldate))
			return false;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public Double getHxPSum() {
		return hxPSum;
	}

	public void setHxPSum(Double hxPSum) {
		this.hxPSum = hxPSum;
	}

	public Double getHkhxPSum() {
		return hkhxPSum;
	}

	public void setHkhxPSum(Double hkhxPSum) {
		this.hkhxPSum = hkhxPSum;
	}

	public String getPriceCurrencySum() {
		return priceCurrencySum;
	}

	public void setPriceCurrencySum(String priceCurrencySum) {
		this.priceCurrencySum = priceCurrencySum;
	}

	public Double getPriceSum() {
		return priceSum;
	}

	public void setPriceSum(Double priceSum) {
		this.priceSum = priceSum;
	}

	public Double getCostAll() {
		return costAll;
	}

	public void setCostAll(Double costAll) {
		this.costAll = costAll;
	}

	public String getStoreDateStart() {
		return storeDateStart;
	}

	public void setStoreDateStart(String storeDateStart) {
		this.storeDateStart = storeDateStart;
	}

	public String getStoreDateEnd() {
		return storeDateEnd;
	}

	public void setStoreDateEnd(String storeDateEnd) {
		this.storeDateEnd = storeDateEnd;
	}
	

 }
