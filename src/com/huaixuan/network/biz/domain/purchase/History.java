package com.huaixuan.network.biz.domain.purchase;

import java.util.Date;

/**
 *2012-7-5 下午01:24:03
 *Mr_Yang
 *产品的历史记录
 *
 */
public class History
{
	private Long idHistory; //唯一编号
	
	private Long idOperation; //本次记录的操作。比如：入库，销售
	
	private Long idProduct; //产品ID
	
	private Date date;//本次记录的操作时间
	
	private Long idOperator; //操作者工号
	
	private Long idCurStation; //该产品当前所在位置
	
	private Long idStatus; //该产品现在状态
	
	private Long idSupply; //供应商编号

	public Long getIdHistory()
	{
		return idHistory;
	}

	public void setIdHistory(Long idHistory)
	{
		this.idHistory = idHistory;
	}

	public Long getIdOperation()
	{
		return idOperation;
	}

	public void setIdOperation(Long idOperation)
	{
		this.idOperation = idOperation;
	}

	public Long getIdProduct()
	{
		return idProduct;
	}

	public void setIdProduct(Long idProduct)
	{
		this.idProduct = idProduct;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Long getIdOperator()
	{
		return idOperator;
	}

	public void setIdOperator(Long idOperator)
	{
		this.idOperator = idOperator;
	}

	public Long getIdCurStation()
	{
		return idCurStation;
	}

	public void setIdCurStation(Long idCurStation)
	{
		this.idCurStation = idCurStation;
	}

	public Long getIdStatus()
	{
		return idStatus;
	}

	public void setIdStatus(Long idStatus)
	{
		this.idStatus = idStatus;
	}

	public Long getIdSupply()
	{
		return idSupply;
	}

	public void setIdSupply(Long idSupply)
	{
		this.idSupply = idSupply;
	}
		
	
}
