package com.huaixuan.network.biz.domain.reserved;

import java.util.Date;



/**
 * @author Mr_Yang   2016-9-1 下午03:26:54
 * 预开单订单
 **/

public class ReservedOrder
{
	private Long id;		//
	private Long idCustomer;		//客户ID
	private Long idOrder; //关联的订单Id
	private Double subTotal;		//总金额
	private Long idCurrency;		//销售币种
	private Long idPayment;		//付款方式
	private Long idChannel;		//销售渠道
	private Double amountCard;		//非现金付款
	private Double amountCash;		//现金付款
	private Long idSite;		//
	private Long status;		//订单状态-1-已取消 0-未处理 1-已处理 2-已处理,部分缺货 3已处理,部分取消
	private String createUserId;
	private String createUserName;
	private String createDate;		//
	private String lastUpdateDate;		//
	
	private String currencyName;
	private String paymentName;
	private String sellChannelName;
	private String customerName;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getIdCustomer()
	{
		return idCustomer;
	}
	public void setIdCustomer(Long idCustomer)
	{
		this.idCustomer = idCustomer;
	}
	public Double getSubTotal()
	{
		return subTotal;
	}
	public void setSubTotal(Double subTotal)
	{
		this.subTotal = subTotal;
	}
	public Long getIdCurrency()
	{
		return idCurrency;
	}
	public void setIdCurrency(Long idCurrency)
	{
		this.idCurrency = idCurrency;
	}
	public Long getIdPayment()
	{
		return idPayment;
	}
	public void setIdPayment(Long idPayment)
	{
		this.idPayment = idPayment;
	}
	public Long getIdChannel()
	{
		return idChannel;
	}
	public void setIdChannel(Long idChannel)
	{
		this.idChannel = idChannel;
	}
	public Double getAmountCard()
	{
		return amountCard;
	}
	public void setAmountCard(Double amountCard)
	{
		this.amountCard = amountCard;
	}
	public Double getAmountCash()
	{
		return amountCash;
	}
	public void setAmountCash(Double amountCash)
	{
		this.amountCash = amountCash;
	}
	public Long getIdSite()
	{
		return idSite;
	}
	public void setIdSite(Long idSite)
	{
		this.idSite = idSite;
	}
	public Long getStatus()
	{
		return status;
	}
	public void setStatus(Long status)
	{
		this.status = status;
	}
	public String getCreateUserId()
	{
		return createUserId;
	}
	public void setCreateUserId(String createUserId)
	{
		this.createUserId = createUserId;
	}
	public String getCreateUserName()
	{
		return createUserName;
	}
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	public String getLastUpdateDate()
	{
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getCurrencyName()
	{
		return currencyName;
	}
	public void setCurrencyName(String currencyName)
	{
		this.currencyName = currencyName;
	}
	public String getPaymentName()
	{
		return paymentName;
	}
	public void setPaymentName(String paymentName)
	{
		this.paymentName = paymentName;
	}
	public String getSellChannelName()
	{
		return sellChannelName;
	}
	public void setSellChannelName(String sellChannelName)
	{
		this.sellChannelName = sellChannelName;
	}
	public String getCustomerName()
	{
		return customerName;
	}
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	public Long getIdOrder()
	{
		return idOrder;
	}
	public void setIdOrder(Long idOrder)
	{
		this.idOrder = idOrder;
	}
	
	
	
	
}
 
