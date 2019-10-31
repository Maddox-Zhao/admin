package com.huaixuan.network.biz.domain.order;



/**
 * @author Mr_Yang   2016-4-6 下午04:18:52
 * 订单答应
 **/

public class OrderPrint
{
	private String name;
	private Integer qty;
	private Double unitPrice; //尚美价
	private Double dealPrice; //成交价
	private Double amountPrice; //总价
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getQty()
	{
		return qty;
	}
	public void setQty(Integer qty)
	{
		this.qty = qty;
	}
	public Double getUnitPrice()
	{
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice)
	{
		this.unitPrice = unitPrice;
	}
	public Double getDealPrice()
	{
		return dealPrice;
	}
	public void setDealPrice(Double dealPrice)
	{
		this.dealPrice = dealPrice;
	}
	public Double getAmountPrice()
	{
		return amountPrice;
	}
	public void setAmountPrice(Double amountPrice)
	{
		this.amountPrice = amountPrice;
	}
	
	
}
 
