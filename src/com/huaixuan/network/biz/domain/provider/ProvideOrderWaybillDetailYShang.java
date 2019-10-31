/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideOrderWaybillDetailYShang {

	
	private int id;
	private String outOrderId; //第三方订单号,即我们的订单号
	private String parentOrderId; //云尚主订单号
	private String receiverCity; //城市
	
	private String receiverArea; //区
	private String receiverMobile; //手机号
	private String receiverProvince; //省
	private String receiverAddress; //收货人详细地址
	
	private String receiverName; //收货人
	private String totalPay; //云尚订单总金额
	private String expressName; //物流公司名称
	private String expressNum; //物流单号
	
	private String orderId; //云尚子订单编号
	private String totalPayChild; //云尚子订单总金额
	private String wmsStatus; //订单状态（见后续说明）
	private String warehouseId; //仓库 Id

	private int quantity; //数量
	private String nowPrice; //云尚销售价
	private String productId; //云尚互联商编
	private String productName; //商品名称
	
	private String salePrice; //云尚供货价
	private String insertTime; //云尚供货价
	private String updateTime; //云尚供货价

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
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

	public String getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTotalPayChild() {
		return totalPayChild;
	}

	public void setTotalPayChild(String totalPayChild) {
		this.totalPayChild = totalPayChild;
	}

	public String getWmsStatus() {
		return wmsStatus;
	}

	public void setWmsStatus(String wmsStatus) {
		this.wmsStatus = wmsStatus;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
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
	
	
}
