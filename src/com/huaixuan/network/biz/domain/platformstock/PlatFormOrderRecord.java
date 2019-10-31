package com.huaixuan.network.biz.domain.platformstock;



/**
 * @author Mr_Yang   2016-5-26 下午02:35:11
 **/

public class PlatFormOrderRecord
{
	private int id;
	private String orderId; //订单号
	private int idPlartform; //平台编码
	private int idStatus;//状态1-已售订单 2-取消订单
	private String createDate; //接受订单时间
	private String orderTime;//下单时间
	private String status;//订单状态      魅力惠订单中代表获取面单次数
	private String billHref; //魅力惠打印面单的连接
	private String backPackStatus; //魅力惠回传包的状态
	private String type;  //国内或海外
	private String emsCode;    //快递单号
	private String emsCompany;//快递公司
	
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
	public int getIdPlartform() {
		return idPlartform;
	}
	public void setIdPlartform(int idPlartform) {
		this.idPlartform = idPlartform;
	}
	public int getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmsCode() {
		return emsCode;
	}
	public void setEmsCode(String emsCode) {
		this.emsCode = emsCode;
	}
	public String getEmsCompany() {
		return emsCompany;
	}
	public void setEmsCompany(String emsCompany) {
		this.emsCompany = emsCompany;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getBillHref() {
		return billHref;
	}
	public void setBillHref(String billHref) {
		this.billHref = billHref;
	}
	public String getBackPackStatus() {
		return backPackStatus;
	}
	public void setBackPackStatus(String backPackStatus) {
		this.backPackStatus = backPackStatus;
	}
	
	
	
}
 
