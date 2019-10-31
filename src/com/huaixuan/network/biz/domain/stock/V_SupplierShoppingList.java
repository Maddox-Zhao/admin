package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangxiang
 */
public class V_SupplierShoppingList implements Serializable
{

	/**
	 * 供货商查询用到的装载查询结果显示�
	 */
	private static final long	serialVersionUID	= -181761769516177784L;

	private Long				id;										// 采购单号

	private String				supplierName;								// 供货商姓�

	private String             status;                                   // 采购单状�

	private String				shoppingNum;								// 采购单号

	private Date				shoppingTime;								// 采购时间

	private Date				arriveTime;								// 到货时间

	private double				dueFee;									// 应付金额

	private double				factFee;									// 实付金额

	private String              goodsSn;

	private String              instanceName;

	private String              attrDesc;

	private long                amount;

	private double              unitPrice;

	private long                missingNum;                               //缺货数量

	private long                rejectNum;								//拒收数量

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getAttrDesc() {
		return attrDesc;
	}

	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public V_SupplierShoppingList()
	{
		super();
	}

	public V_SupplierShoppingList(Long id, String supplierName,
			String shoppingNum, Date shoppingTime, Date arriveTime,
			double dueFee, double factFee)
	{
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.shoppingNum = shoppingNum;
		this.shoppingTime = shoppingTime;
		this.arriveTime = arriveTime;
		this.dueFee = dueFee;
		this.factFee = factFee;
	}


	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status 要设置的 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return arriveTime
	 */
	public Date getArriveTime()
	{
		return arriveTime;
	}

	/**
	 * @param arriveTime
	 *            要设置的 arriveTime
	 */
	public void setArriveTime(Date arriveTime)
	{
		this.arriveTime = arriveTime;
	}

	/**
	 * @return dueFee
	 */
	public double getDueFee()
	{
		return dueFee;
	}

	/**
	 * @param dueFee
	 *            要设置的 dueFee
	 */
	public void setDueFee(double dueFee)
	{
		this.dueFee = dueFee;
	}

	/**
	 * @return factFee
	 */
	public double getFactFee()
	{
		return factFee;
	}

	/**
	 * @param factFee
	 *            要设置的 factFee
	 */
	public void setFactFee(double factFee)
	{
		this.factFee = factFee;
	}

	/**
	 * @return id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            要设置的 id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return shoppingNum
	 */
	public String getShoppingNum()
	{
		return shoppingNum;
	}

	/**
	 * @param shoppingNum
	 *            要设置的 shoppingNum
	 */
	public void setShoppingNum(String shoppingNum)
	{
		this.shoppingNum = shoppingNum;
	}

	/**
	 * @return shoppingTime
	 */
	public Date getShoppingTime()
	{
		return shoppingTime;
	}

	/**
	 * @param shoppingTime
	 *            要设置的 shoppingTime
	 */
	public void setShoppingTime(Date shoppingTime)
	{
		this.shoppingTime = shoppingTime;
	}

	/**
	 * @return supplierName
	 */
	public String getSupplierName()
	{
		return supplierName;
	}

	/**
	 * @param supplierName
	 *            要设置的 supplierName
	 */
	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}

	public long getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(long missingNum) {
		this.missingNum = missingNum;
	}

	public long getRejectNum() {
		return rejectNum;
	}

	public void setRejectNum(long rejectNum) {
		this.rejectNum = rejectNum;
	}

}
