package com.huaixuan.network.biz.domain.active;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveframeGoods
{
	private Long id;		//
	private Long moveframeId;		//活动框编号
	private Long goodsId;		//商品款式编号
	private Double discountPrice;		//产品折后价格
	private Long goodsNum;		//可售库存
	private Long soldNum;		//已售库存
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
	private Integer hasStock; //0为从有到无,1为从无到有，2.为增加，3.为减少
	private Map<Long,List<Long>> goodsIdMoveFrameIds = new HashMap<Long, List<Long>>(); //储存该goodsId对应的MoveFrameId
	private Integer type; //1.增加的 2.减少的
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
	public Long getGoodsNum()
	{
		return goodsNum;
	}
	public void setGoodsNum(Long goodsNum)
	{
		this.goodsNum = goodsNum;
	}
	public Long getSoldNum()
	{
		return soldNum;
	}
	public void setSoldNum(Long soldNum)
	{
		this.soldNum = soldNum;
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

	public Integer getHasStock()
	{
		return hasStock;
	}
	public void setHasStock(Integer hasStock)
	{
		this.hasStock = hasStock;
	}
	public Map<Long, List<Long>> getGoodsIdMoveFrameIds()
	{
		return goodsIdMoveFrameIds;
	}
	public void setGoodsIdMoveFrameIds(Map<Long, List<Long>> goodsIdMoveFrameIds)
	{
		this.goodsIdMoveFrameIds = goodsIdMoveFrameIds;
	}
	
	
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
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
		MoveframeGoods other = (MoveframeGoods) obj;
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
}
