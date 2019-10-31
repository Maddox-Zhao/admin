package com.huaixuan.network.biz.domain.purchase;

import javax.xml.crypto.Data;

/**
 *2012-7-5 下午01:28:47
 *Mr_Yang
 *单款产品的基本数据
 */
public class BaseData
{
		private Long idProduct; //产品ID
		
		private Integer idBrand; //品牌ID
		
		private Integer idSeries; //品名ID
		
		private String type; //型号
		
		private String material; //材质
		
		private String color; //颜色
		
		private String picture; //图片
		
		private Double hkPrice; //尚上价
		
		private Double euPrice; //欧洲价
		
		private Integer noPurchase; //未完成的采购数量
		
		private Integer bookNumber; //被预订数量
		
		private Long goodsId;		
		
		private Data date; 

		public Integer getIdBrand()
		{
			return idBrand;
		}

		public void setIdBrand(Integer idBrand)
		{
			this.idBrand = idBrand;
		}

		public Integer getIdSeries()
		{
			return idSeries;
		}

		public void setIdSeries(Integer idSeries)
		{
			this.idSeries = idSeries;
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

		public String getPicture()
		{
			return picture;
		}

		public void setPicture(String picture)
		{
			this.picture = picture;
		}

		public Double getHkPrice()
		{
			return hkPrice;
		}

		public void setHkPrice(Double hkPrice)
		{
			this.hkPrice = hkPrice;
		}

		public Double getEuPrice()
		{
			return euPrice;
		}

		public void setEuPrice(Double euPrice)
		{
			this.euPrice = euPrice;
		}

		public Integer getNoPurchase()
		{
			return noPurchase;
		}

		public void setNoPurchase(Integer noPurchase)
		{
			this.noPurchase = noPurchase;
		}

		public Integer getBookNumber()
		{
			return bookNumber;
		}

		public void setBookNumber(Integer bookNumber)
		{
			this.bookNumber = bookNumber;
		}

		public Long getGoodsId()
		{
			return goodsId;
		}

		public void setGoodsId(Long goodsId)
		{
			this.goodsId = goodsId;
		}

		public Data getDate()
		{
			return date;
		}

		public void setDate(Data date)
		{
			this.date = date;
		}

		public Long getIdProduct()
		{
			return idProduct;
		}

		public void setIdProduct(Long idProduct)
		{
			this.idProduct = idProduct;
		}
		
	
}
