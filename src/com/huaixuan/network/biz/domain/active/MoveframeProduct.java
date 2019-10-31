package com.huaixuan.network.biz.domain.active;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr_Yang
 * @version 创建时间：2012-3-14 上午11:57:30
 * 活动框对应的产品信息，比如折后价，库存
 */

public class MoveframeProduct
{
	private Long id;		//
	private Long moveframeId;		//活动框编号
	private Long productId;		//产品编号
	private Long goodsId;		//商品款式编号
	private Long instanceId;		//商品款式尺寸编号
	private String note;		//备注
	private Double discountPrice;		//产品折后价格
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
	private Integer area; //活动产品所属的区域
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getMoveframeId()
	{
		return moveframeId;
	}
	public void setMoveframeId(Long moveframeId)
	{
		this.moveframeId = moveframeId;
	}
	public Long getProductId()
	{
		return productId;
	}
	public void setProductId(Long productId)
	{
		this.productId = productId;
	}
	public Long getGoodsId()
	{
		return goodsId;
	}
	public void setGoodsId(Long goodsId)
	{
		this.goodsId = goodsId;
	}
	public Long getInstanceId()
	{
		return instanceId;
	}
	public void setInstanceId(Long instanceId)
	{
		this.instanceId = instanceId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public Double getDiscountPrice()
	{
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice)
	{
		this.discountPrice = discountPrice;
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
	
	public Integer getArea()
	{
		return area;
	}
	public void setArea(Integer area)
	{
		this.area = area;
	}
	
	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
		result = prime * result
				+ ((moveframeId == null) ? 0 : moveframeId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoveframeProduct other = (MoveframeProduct) obj;
		if (goodsId == null)
		{
			if (other.goodsId != null)
				return false;
		}
		else if (!goodsId.equals(other.goodsId))
			return false;
		if (moveframeId == null)
		{
			if (other.moveframeId != null)
				return false;
		}
		else if (!moveframeId.equals(other.moveframeId))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return this.getNote() + " " + this.productId + " " + this.discountPrice + " " + this.area;
	}
}
