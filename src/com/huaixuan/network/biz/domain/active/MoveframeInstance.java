package com.huaixuan.network.biz.domain.active;

import java.sql.Date;

public class MoveframeInstance
{
	private Long id;		//
	private Long moveframeId;		//活动框编号
	private Long goodsId;		//商品款式编号
	private Long instanceId;		//商品款式尺寸编号
	private Long instanceNum;		//可售库存
	private Long soldNum;		//已售库存
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
	public Long getInstanceId()
	{
		return instanceId;
	}
	public void setInstanceId(Long instanceId)
	{
		this.instanceId = instanceId;
	}
	public Long getInstanceNum()
	{
		return instanceNum;
	}
	public void setInstanceNum(Long instanceNum)
	{
		this.instanceNum = instanceNum;
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

}
