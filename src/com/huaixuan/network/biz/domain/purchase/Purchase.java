package com.huaixuan.network.biz.domain.purchase;



/**
 * @author Mr_Yang   2015-12-31 下午02:18:46
 **/

public class Purchase  {
	private Long idpurchase;		//
	private String date;		//
	private int secondhand;		//
	private Long idsupply;		//
	private Long idcustomer;		//
	private Double subtotal;		
	private Double insubtotal;		//
	private int idcurrency;		//
	private String operator;		//
	public Long getIdpurchase()
	{
		return idpurchase;
	}
	public void setIdpurchase(Long idpurchase)
	{
		this.idpurchase = idpurchase;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public int getSecondhand()
	{
		return secondhand;
	}
	public void setSecondhand(int secondhand)
	{
		this.secondhand = secondhand;
	}
	public Long getIdsupply()
	{
		return idsupply;
	}
	public void setIdsupply(Long idsupply)
	{
		this.idsupply = idsupply;
	}
	public Long getIdcustomer()
	{
		return idcustomer;
	}
	public void setIdcustomer(Long idcustomer)
	{
		this.idcustomer = idcustomer;
	}
	public Double getSubtotal()
	{
		return subtotal;
	}
	public void setSubtotal(Double subtotal)
	{
		this.subtotal = subtotal;
	}
	public Double getInsubtotal()
	{
		return insubtotal;
	}
	public void setInsubtotal(Double insubtotal)
	{
		this.insubtotal = insubtotal;
	}
	public int getIdcurrency()
	{
		return idcurrency;
	}
	public void setIdcurrency(int idcurrency)
	{
		this.idcurrency = idcurrency;
	}
	public String getOperator()
	{
		return operator;
	}
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
 
	
	 
	

}
