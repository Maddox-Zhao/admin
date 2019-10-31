/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

import java.math.BigDecimal;

/**
 * @author TT
 * 只用于云尚的传递参数
 */
public class OrderItemDto {

	
	private String cardNo; //身份证号码
	private String outOrderId;//我们的订单号
	private String receiverCity;
	private String receiverArea;
	
	private String receiverMobile;
	private String receiverProvince;
	private String receiverAddress;
	private String receiverName;
	
	private BigDecimal amount;
	private String orderChannelId;
	private String orderChannelName;
	private Integer productNum; //身份证号码



	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	
	

	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverArea() {
		return receiverArea;
	}
	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getOrderChannelId() {
		return orderChannelId;
	}
	public void setOrderChannelId(String orderChannelId) {
		this.orderChannelId = orderChannelId;
	}
	public String getOrderChannelName() {
		return orderChannelName;
	}
	public void setOrderChannelName(String orderChannelName) {
		this.orderChannelName = orderChannelName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	

	
	
}
