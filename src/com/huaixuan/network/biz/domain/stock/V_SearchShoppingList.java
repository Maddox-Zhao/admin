package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;

/**
 * @author zhangxiang
 */
public class V_SearchShoppingList implements Serializable
{

	/**
	 * 供货商查询vo
	 */
	private static final long	serialVersionUID	= -2871502493592422808L;

	private String				supplier_id;									// 供应商id

	private String				supplier_name;									// 供应商名�

	private String				startDateStr;									// 采购弧�时间

	private String				endDateStr;									// 采购结束时间

	private String				status;										//采购单状�

	public V_SearchShoppingList()
	{
		super();
	}

	public V_SearchShoppingList(String supplier_id, String startDateStr,
			String endDateStr)
	{
		super();
		this.supplier_id = supplier_id;
		this.startDateStr = startDateStr;
		this.endDateStr = endDateStr;
	}

	/**
	 * @return the supplier_name
	 */
	public String getSupplier_name() {
		return supplier_name;
	}

	/**
	 * @param supplier_name the supplier_name to set
	 */
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	/**
	 * @return endDateStr
	 */
	public String getEndDateStr()
	{
		return endDateStr;
	}

	/**
	 * @param endDateStr
	 *            要设置的 endDateStr
	 */
	public void setEndDateStr(String endDateStr)
	{
		this.endDateStr = endDateStr;
	}

	/**
	 * @return startDateStr
	 */
	public String getStartDateStr()
	{
		return startDateStr;
	}

	/**
	 * @param startDateStr
	 *            要设置的 startDateStr
	 */
	public void setStartDateStr(String startDateStr)
	{
		this.startDateStr = startDateStr;
	}

	/**
	 * @return supplier_id
	 */
	public String getSupplier_id()
	{
		return supplier_id;
	}

	/**
	 * @param supplier_id
	 *            要设置的 supplier_id
	 */
	public void setSupplier_id(String supplier_id)
	{
		this.supplier_id = supplier_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
