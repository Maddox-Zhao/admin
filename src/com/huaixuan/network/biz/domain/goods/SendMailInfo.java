package com.huaixuan.network.biz.domain.goods;

import java.util.HashMap;
import java.util.Map;

public class SendMailInfo implements Cloneable
{
	private String type;
	private String material;
	private String color;
	private String brand;
	private String series;
	private String idProduct;
	private String moveframeName;
	private Long moveFrameId;
	private String beforPrice;
	private String nowPrice;
	private  String middleImg;
	private Integer hasStock; //0为从有到无,1为从无到有，2.为增加，3.为减少
	private Double discountPrice; //活动价
	private Double hkPrice; //shangshang价
	private String userRank; //客户等级
	private Integer userType; //類型。 1.內部用戶 2.客戶
	private  Long goodsNum;
	private Long goodsId;
	private Double hxPrice; //尚上价
	private  Map<String,Integer> stock = new HashMap<String, Integer>(); //库存 

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
	public String getBrand()
	{
		return brand;
	}
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	public String getSeries()
	{
		return series;
	}
	public void setSeries(String series)
	{
		this.series = series;
	}
	public String getIdProduct()
	{
		return idProduct;
	}
	public void setIdProduct(String idProduct)
	{
		this.idProduct = idProduct;
	}
	public String getMoveframeName()
	{
		return moveframeName;
	}
	public void setMoveframeName(String moveframeName)
	{
		this.moveframeName = moveframeName;
	}
	public String getBeforPrice()
	{
		return beforPrice;
	}
	public void setBeforPrice(String beforPrice)
	{
		this.beforPrice = beforPrice;
	}
	public String getNowPrice()
	{
		return nowPrice;
	}
	public void setNowPrice(String nowPrice)
	{
		this.nowPrice = nowPrice;
	}
	public String getMiddleImg()
	{
		return middleImg;
	}
	public void setMiddleImg(String middleImg)
	{
		this.middleImg = middleImg;
	}
	public Integer getHasStock()
	{
		return hasStock;
	}
	public void setHasStock(Integer hasStock)
	{
		this.hasStock = hasStock;
	}
	public Long getGoodsId()
	{
		return goodsId;
	}
	public void setGoodsId(Long goodsId)
	{
		this.goodsId = goodsId;
	}
	public Double getDiscountPrice()
	{
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice)
	{
		this.discountPrice = discountPrice;
	}
	public Double getHkPrice()
	{
		return hkPrice;
	}
	public void setHkPrice(Double hkPrice)
	{
		this.hkPrice = hkPrice;
	}

	public String getUserRank()
	{
		return userRank;
	}
	public void setUserRank(String userRank)
	{
		this.userRank = userRank;
	}
	public Integer getUserType()
	{
		return userType;
	}
	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}
	public Map<String, Integer> getStock()
	{
		return stock;
	}
	public void setStock(Map<String, Integer> stock)
	{
		this.stock = stock;
	}
	public Long getGoodsNum()
	{
		return goodsNum;
	}
	public void setGoodsNum(Long goodsNum)
	{
		this.goodsNum = goodsNum;
	}
	public Double getHxPrice()
	{
		return hxPrice;
	}
	public void setHxPrice(Double hxPrice)
	{
		this.hxPrice = hxPrice;
	}
	public Long getMoveFrameId()
	{
		return moveFrameId;
	}
	public void setMoveFrameId(Long moveFrameId)
	{
		this.moveFrameId = moveFrameId;
	}
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	
	
	
}
