/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideOrderWaybillDetail {

	private int id;
	private String orderId;
	private String buyname;
	private String phone;
	private String address;
	
	private String amount;
	private String paidamount;
	private String shipname;
	private String waybillcode;
	private String insertTime;
	
	private String updateTime;
	private String sellPlatform;
	private String pushPlatform;
	private String orderstatus;
	
	private String cancelstatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBuyname() {
		return buyname;
	}
	public void setBuyname(String buyname) {
		this.buyname = buyname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaidamount() {
		return paidamount;
	}
	public void setPaidamount(String paidamount) {
		this.paidamount = paidamount;
	}
	public String getShipname() {
		return shipname;
	}
	public void setShipname(String shipname) {
		this.shipname = shipname;
	}
	public String getWaybillcode() {
		return waybillcode;
	}
	public void setWaybillcode(String waybillcode) {
		this.waybillcode = waybillcode;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getSellPlatform() {
		return sellPlatform;
	}
	public void setSellPlatform(String sellPlatform) {
		this.sellPlatform = sellPlatform;
	}
	public String getPushPlatform() {
		return pushPlatform;
	}
	public void setPushPlatform(String pushPlatform) {
		this.pushPlatform = pushPlatform;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getCancelstatus() {
		return cancelstatus;
	}
	public void setCancelstatus(String cancelstatus) {
		this.cancelstatus = cancelstatus;
	}

}
