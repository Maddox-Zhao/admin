package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-5-13 下午03:39:00
 **/

public class StockUpdateHistory
{
	private Long idHistory;
	private Long idOperation;
	private String idProduct;
	private String date;
	private String idOperator;
	private String idLocation;
	private Integer idStatus;
	private String idSupply;
	private Integer idCustomer;
	private String idLastLocation;
	private String sku;
	private String customerName;
	private String customerName2; //若果是 预订状态 客户端开的单历史表没有客户信息 通过它来保存
	
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
	public String getIdProduct()
	{
		return idProduct;
	}
	public void setIdProduct(String idProduct)
	{
		this.idProduct = idProduct;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getIdOperator()
	{
		return idOperator;
	}
	public void setIdOperator(String idOperator)
	{
		this.idOperator = idOperator;
	}
	public String getIdLocation()
	{
		return idLocation;
	}
	public void setIdLocation(String idLocation)
	{
		this.idLocation = idLocation;
	}
	public Integer getIdStatus()
	{
		return idStatus;
	}
	public void setIdStatus(Integer idStatus)
	{
		this.idStatus = idStatus;
	}
	public String getIdSupply()
	{
		return idSupply;
	}
	public void setIdSupply(String idSupply)
	{
		this.idSupply = idSupply;
	}
	public Integer getIdCustomer()
	{
		return idCustomer;
	}
	public void setIdCustomer(Integer idCustomer)
	{
		this.idCustomer = idCustomer;
	}
	public String getIdLastLocation()
	{
		return idLastLocation;
	}
	public void setIdLastLocation(String idLastLocation)
	{
		this.idLastLocation = idLastLocation;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getCustomerName()
	{
		return customerName;
	}
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	public String getCustomerName2()
	{
		return customerName2;
	}
	public void setCustomerName2(String customerName2)
	{
		this.customerName2 = customerName2;
	}
	
	
	
}
 
