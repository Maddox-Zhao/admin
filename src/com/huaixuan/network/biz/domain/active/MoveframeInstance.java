package com.huaixuan.network.biz.domain.active;

import java.sql.Date;

public class MoveframeInstance
{
	private Long id;		//
	private Long moveframeId;		//�����
	private Long goodsId;		//��Ʒ��ʽ���
	private Long instanceId;		//��Ʒ��ʽ�ߴ���
	private Long instanceNum;		//���ۿ��
	private Long soldNum;		//���ۿ��
	private Date gmtCreate;		//����ʱ��
	private Date gmtModify;		//�޸�ʱ��
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
