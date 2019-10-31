package com.huaixuan.network.biz.domain.purchase;



/**
 * @author Mr_Yang   2016-4-27 上午10:56:31
 * 采购订单 
 **/

public class PurchaseOrder
{
	private Long idPurchase;
	private Long idPurchaseLifeCycle;
	private Long idMoneyStatus; //货款状态
	private String  moneyStatus;
	private Long idStatus;//订单状态
	private String status; //
	private String origDoc;//源文件
	private Long idPayAccount;//付款账户
	private String payMentAccount;
	private Double exchangeRate;//汇率
	private Long idProxyAgency; //货代公司
	private String proxyAgency;
	private String flightNum;//航班号
	private String sentDate;
	private String idLastOperator;//最后操作人
	private String lastOperatorName;
	private String idOperator;
	private String operatorName;
	private Double transportationFee;
	private Double extraFee;
	private Double supplyDebt;
	private String remark;
	private Long idSupply;
	private String supplyName;
	private Double subTotal; //
	private Double inSubTotal; //
	private Long idCurrency;
	private String date;
	private int isBilling;//是否开票
	private Double taxesReate;//税率
	private String finishDate;//完成时间
	public Long getIdPurchase()
	{
		return idPurchase;
	}
	public void setIdPurchase(Long idPurchase)
	{
		this.idPurchase = idPurchase;
	}


	public Long getIdPurchaseLifeCycle()
	{
		return idPurchaseLifeCycle;
	}
	public void setIdPurchaseLifeCycle(Long idPurchaseLifeCycle)
	{
		this.idPurchaseLifeCycle = idPurchaseLifeCycle;
	}
	public Long getIdMoneyStatus()
	{
		return idMoneyStatus;
	}
	public void setIdMoneyStatus(Long idMoneyStatus)
	{
		this.idMoneyStatus = idMoneyStatus;
	}
	public String getMoneyStatus()
	{
		return moneyStatus;
	}
	public void setMoneyStatus(String moneyStatus)
	{
		this.moneyStatus = moneyStatus;
	}
	public Long getIdStatus()
	{
		return idStatus;
	}
	public void setIdStatus(Long idStatus)
	{
		this.idStatus = idStatus;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
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
	 
	public String getPayMentAccount()
	{
		return payMentAccount;
	}
	public void setPayMentAccount(String payMentAccount)
	{
		this.payMentAccount = payMentAccount;
	}
	public Double getExchangeRate()
	{
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate)
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
	public String getProxyAgency()
	{
		return proxyAgency;
	}
	public void setProxyAgency(String proxyAgency)
	{
		this.proxyAgency = proxyAgency;
	}
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getIdLastOperator()
	{
		return idLastOperator;
	}
	public void setIdLastOperator(String idLastOperator)
	{
		this.idLastOperator = idLastOperator;
	}
	public String getLastOperatorName()
	{
		return lastOperatorName;
	}
	public void setLastOperatorName(String lastOperatorName)
	{
		this.lastOperatorName = lastOperatorName;
	}
	public String getIdOperator()
	{
		return idOperator;
	}
	public void setIdOperator(String idOperator)
	{
		this.idOperator = idOperator;
	}
	public String getOperatorName()
	{
		return operatorName;
	}
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
	public Double getTransportationFee()
	{
		return transportationFee;
	}
	public void setTransportationFee(Double transportationFee)
	{
		this.transportationFee = transportationFee;
	}
	public Double getExtraFee()
	{
		return extraFee;
	}
	public void setExtraFee(Double extraFee)
	{
		this.extraFee = extraFee;
	}
	public Double getSupplyDebt()
	{
		return supplyDebt;
	}
	public void setSupplyDebt(Double supplyDebt)
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
	public Long getIdSupply()
	{
		return idSupply;
	}
	public void setIdSupply(Long idSupply)
	{
		this.idSupply = idSupply;
	}
	public String getSupplyName()
	{
		return supplyName;
	}
	public void setSupplyName(String supplyName)
	{
		this.supplyName = supplyName;
	}
	public Double getSubTotal()
	{
		return subTotal;
	}
	public void setSubTotal(Double subTotal)
	{
		this.subTotal = subTotal;
	}
	public Double getInSubTotal()
	{
		return inSubTotal;
	}
	public void setInSubTotal(Double inSubTotal)
	{
		this.inSubTotal = inSubTotal;
	}
	public Long getIdCurrency()
	{
		return idCurrency;
	}
	public void setIdCurrency(Long idCurrency)
	{
		this.idCurrency = idCurrency;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getFinishDate()
	{
		return finishDate;
	}
	public void setFinishDate(String finishDate)
	{
		this.finishDate = finishDate;
	}
	public int getIsBilling()
	{
		return isBilling;
	}
	public void setIsBilling(int isBilling)
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
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	
	
	
	 
}
 
