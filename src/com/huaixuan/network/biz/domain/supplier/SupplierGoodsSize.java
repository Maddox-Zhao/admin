package com.huaixuan.network.biz.domain.supplier;

import java.util.Date;



/**
 * @author Mr_Yang   2015-9-10 ����11:02:03
 **/

public class SupplierGoodsSize 
{
	private Long id;		//
	private Long goodsId;		//������goodsid
	private String size;		//��С
	private Integer num;		//����
	private Date gmtCreate;		//
	private Date gmtModify;		//
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getGoodsId()
	{
		return goodsId;
	}
	public void setGoodsId(Long goodsId)
	{
		this.goodsId = goodsId;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
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
	public Integer getNum()
	{
		return num;
	}
	public void setNum(Integer num)
	{
		this.num = num;
	}
	
	
}
 
