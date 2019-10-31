/**
 * @Title: StockDetailSearchQuery.java
 * @Package com.huaixuan.network.biz.domain.stock.query
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ÏÂÎç02:30:20
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.stock.query;

/**
 * @ClassName: StockDetailSearchQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ÏÂÎç02:30:20
 *
 */
public class StockDetailSearchQuery {

	private String instanceCode;

	private String instanceName;

	private String supplierId;

	private String supplierName;

	private String storType;

	private String startTime;

	private String endTime;

	private String status;

	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getStorType() {
		return storType;
	}

	public void setStorType(String storType) {
		this.storType = storType;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
