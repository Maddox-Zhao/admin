package com.huaixuan.network.biz.domain.shop;



/**
 * @author Mr_Yang   2015-11-23 ����12:15:12
 **/

public class Series
{
	private int id;
	private String name;
	
	private int parentIdSeries;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;  
	}
	public int getParentIdSeries() {
		return parentIdSeries;
	}
	public void setParentIdSeries(int parentIdSeries) {
		this.parentIdSeries = parentIdSeries;
	}
	
}
 
