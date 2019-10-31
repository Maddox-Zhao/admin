package com.huaixuan.network.biz.domain.order;

import java.util.Date;

public class Orderdetails {
	private String idorder; //订单号
	
	private Date date;//订单日期
	
	private Double subTotal;//订单总额
	
	private String remark;//备注
	
	private String currencyname;//币种
	
	private String idCurrency;
	
	private String  paymentname;//汇款方式
	
	private Integer idPayment;//汇款方式ID
	
	private Double amountCash;//现金总额
	
	private Double amountCard;//刷卡总额
	
	private String customername;//客户
	
	private Integer idCustomer;//客户ID
	
	private String sellchannelname;//渠道
	
	private Integer idSellChannel;//渠道ID
	
	private String sitename;//出售地点
	
	private String idSite;//出售地点ID
	
	private String employeename;//最后经手人
	
	private String empName;   //直接开单人(operator2)
	
	private String idEmployee;//最后经手人ID
	
	private Date gmtpay;//付款时间
	
	private String deliverymeno;//发货信息
	
	private Integer status;//订单状态
	
	private String deliverystatus;//发货状态
	
	private Date deliverydate;//发货时间
	
	private String customerManager; //客户经理
	

	public String getIdorder()
	{
		return idorder;
	}

	public void setIdorder(String idorder)
	{
		this.idorder = idorder;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Double getSubTotal()
	{
		return subTotal;
	}

	public void setSubTotal(Double subTotal)
	{
		this.subTotal = subTotal;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getCurrencyname()
	{
		return currencyname;
	}

	public void setCurrencyname(String currencyname)
	{
		this.currencyname = currencyname;
	}

	public String getPaymentname()
	{
		return paymentname;
	}

	public void setPaymentname(String paymentname)
	{
		this.paymentname = paymentname;
	}

	public Integer getIdPayment()
	{
		return idPayment;
	}

	public void setIdPayment(Integer idPayment)
	{
		this.idPayment = idPayment;
	}

	public Double getAmountCash()
	{
		return amountCash;
	}

	public void setAmountCash(Double amountCash)
	{
		this.amountCash = amountCash;
	}

	public Double getAmountCard()
	{
		return amountCard;
	}

	public void setAmountCard(Double amountCard)
	{
		this.amountCard = amountCard;
	}

	public String getCustomername()
	{
		return customername;
	}

	public void setCustomername(String customername)
	{
		this.customername = customername;
	}

	public Integer getIdCustomer()
	{
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer)
	{
		this.idCustomer = idCustomer;
	}

	public String getSellchannelname()
	{
		return sellchannelname;
	}

	public void setSellchannelname(String sellchannelname)
	{
		this.sellchannelname = sellchannelname;
	}

	public Integer getIdSellChannel()
	{
		return idSellChannel;
	}

	public void setIdSellChannel(Integer idSellChannel)
	{
		this.idSellChannel = idSellChannel;
	}

	public String getSitename()
	{
		return sitename;
	}

	public void setSitename(String sitename)
	{
		this.sitename = sitename;
	}

	public String getIdSite()
	{
		return idSite;
	}

	public void setIdSite(String idSite)
	{
		this.idSite = idSite;
	}

	public String getEmployeename()
	{
		return employeename;
	}

	public void setEmployeename(String employeename)
	{
		this.employeename = employeename;
	}

	public String getIdEmployee()
	{
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee)
	{
		this.idEmployee = idEmployee;
	}

	public Date getGmtpay()
	{
		return gmtpay;
	}

	public void setGmtpay(Date gmtpay)
	{
		this.gmtpay = gmtpay;
	}

	public String getDeliverymeno()
	{
		return deliverymeno;
	}

	public void setDeliverymeno(String deliverymeno)
	{
		this.deliverymeno = deliverymeno;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getDeliverystatus()
	{
		return deliverystatus;
	}

	public void setDeliverystatus(String deliverystatus)
	{
		this.deliverystatus = deliverystatus;
	}

	public Date getDeliverydate()
	{
		return deliverydate;
	}

	public void setDeliverydate(Date deliverydate)
	{
		this.deliverydate = deliverydate;
	}

	public String getCustomerManager()
	{
		return customerManager;
	}

	public void setCustomerManager(String customerManager)
	{
		this.customerManager = customerManager;
	}
   
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getIdCurrency()
	{
		return idCurrency;
	}

	public void setIdCurrency(String idCurrency)
	{
		this.idCurrency = idCurrency;
	}
	
	
	
}
