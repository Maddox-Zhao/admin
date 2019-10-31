package com.huaixuan.network.biz.domain.purchase;

import java.util.Date;



/**
 * @author Mr_Yang   2015-12-30 上午11:15:03
 **/

public class Purchaselifecycle
{
	private Long idPurchaseLifeCycle;		//
	private Long idPurchase;		//
	private Long idMoneyStatus;		//
	private Long idStatus;		// 状态
	private String origDoc;		//
	private Long idPayAccount;		//
	private String exchangeRate;		//
	private Double taxesReate; //税率
	private Long idProxyAgency;		//
	private String flightNum;		//
	private String sentDate;		//
	private Integer isBilling;//是否开票
	private Long idLastOperator;		//
	private String transportationFee;		//
	private String extraFee;		//
	private String supplyDebt;		//
	private String remark;		//
	public Long getIdPurchaseLifeCycle()
	{
		return idPurchaseLifeCycle;
	}
	public void setIdPurchaseLifeCycle(Long idPurchaseLifeCycle)
	{
		this.idPurchaseLifeCycle = idPurchaseLifeCycle;
	}
	public Long getIdPurchase()
	{
		return idPurchase;
	}
	public void setIdPurchase(Long idPurchase)
	{
		this.idPurchase = idPurchase;
	}
	public Long getIdMoneyStatus()
	{
		return idMoneyStatus;
	}
	public void setIdMoneyStatus(Long idMoneyStatus)
	{
		this.idMoneyStatus = idMoneyStatus;
	}
	public Long getIdStatus()
	{
		return idStatus;
	}
	public void setIdStatus(Long idStatus)
	{
		this.idStatus = idStatus;
	}
	public String getOrigDoc()
	{
		return origDoc;
	}
	public void setOrigDoc(String origDoc)
	{
		this.origDoc = origDoc;
	}
	public Long getIdPayAccount()
	{
		return idPayAccount;
	}
	public void setIdPayAccount(Long idPayAccount)
	{
		this.idPayAccount = idPayAccount;
	}
	public String getExchangeRate()
	{
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate)
	{
		this.exchangeRate = exchangeRate;
	}
	public Long getIdProxyAgency()
	{
		return idProxyAgency;
	}
	public void setIdProxyAgency(Long idProxyAgency)
	{
		this.idProxyAgency = idProxyAgency;
	}
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getSentDate()
	{
		return sentDate;
	}
	public void setSentDate(String sentDate)
	{
		this.sentDate = sentDate;
	}
	public Long getIdLastOperator()
	{
		return idLastOperator;
	}
	public void setIdLastOperator(Long idLastOperator)
	{
		this.idLastOperator = idLastOperator;
	}
	public String getTransportationFee()
	{
		return transportationFee;
	}
	public void setTransportationFee(String transportationFee)
	{
		this.transportationFee = transportationFee;
	}
	public String getExtraFee()
	{
		return extraFee;
	}
	public void setExtraFee(String extraFee)
	{
		this.extraFee = extraFee;
	}
	public String getSupplyDebt()
	{
		return supplyDebt;
	}
	public void setSupplyDebt(String supplyDebt)
	{
		this.supplyDebt = supplyDebt;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public Integer getIsBilling()
	{
		return isBilling;
	}
	public void setIsBilling(Integer isBilling)
	{
		this.isBilling = isBilling;
	}
	public Double getTaxesReate()
	{
		return taxesReate;
	}
	public void setTaxesReate(Double taxesReate)
	{
		this.taxesReate = taxesReate;
	}
    
	
	
	
	
	
}
 
