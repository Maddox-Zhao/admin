/**
 *
 */
package com.huaixuan.network.biz.domain.crm;

import java.util.Date;
import java.util.List;

import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Trade;

/**
 * @author chenhang 2011-5-12
 */
public class DoUploadTrade implements Comparable<DoUploadTrade> {

	private Long id;
	private String paipaiTradeId;
	private String goodsSn;
	private String goodsName;
	private Integer goodsNumber;
	private Double goodsPrice;

	private Double marketPrice;
	private Long goodsInstanceId;
	private Long goodsId;
	private String buyAttr;

	private String tid;
	private String type;
	private Integer tradeType;
	private Long depFirstId;
	private String province;
	private String city;
	private String district;
	private String zipcode;
	private String receiver;
	private String mobile;
	private String tel;
	private String address;
	private String payment;
	private String interfaceExpressCode;
	private String buyerNote;
	private String invoice;
	private Long expressId;

	private List<Order> orderList;

	public List<Order> getOrderList() {
		return orderList;
	}

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public String getBuyAttr() {
		return buyAttr;
	}

	public void setBuyAttr(String buyAttr) {
		this.buyAttr = buyAttr;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Long getId() {
		return id;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaipaiTradeId() {
		return paipaiTradeId;
	}

	public void setPaipaiTradeId(String paipaiTradeId) {
		this.paipaiTradeId = paipaiTradeId;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getInterfaceExpressCode() {
		return interfaceExpressCode;
	}

	public void setInterfaceExpressCode(String interfaceExpressCode) {
		this.interfaceExpressCode = interfaceExpressCode;
	}

	public String getBuyerNote() {
		return buyerNote;
	}

	public void setBuyerNote(String buyerNote) {
		this.buyerNote = buyerNote;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(DoUploadTrade o) {
		return this.paipaiTradeId.compareTo(o.getPaipaiTradeId());
	}

	public Trade getTradeObject() {
		Trade trade = new Trade();
		trade.setAddress(this.address);
		trade.setPayment(this.payment);
		trade.setTradeType(this.tradeType);
		trade.setDepFirstId(this.depFirstId);
		trade.setProvince(this.province);
		trade.setCity(this.city);
		trade.setDistrict(this.district);
		trade.setZipcode(this.zipcode);
		trade.setReceiver(this.receiver);
		trade.setMobile(this.mobile);
		trade.setTel(this.tel);
		trade.setInterfaceExpressCode(this.interfaceExpressCode);
		trade.setBuyerNote(this.buyerNote);
		trade.setInvoice(invoice);
		trade.setType(String.valueOf(1));
		trade.setPaipaiTradeId(this.paipaiTradeId);
		trade.setTid(this.tid);
		trade.setIsWholesale("n");
		trade.setExpressId(this.expressId);
		return trade;
	}

	public Order getOrderObject() {
		Order order = new Order();
		order.setGoodsSn(this.goodsSn);
		order.setGoodsNumber(this.goodsNumber);
		order.setGoodsPrice(this.goodsPrice);
		order.setGoodsTitle(this.goodsName);
		order.setMarketPrice(this.marketPrice);
		order.setGoodsId(this.goodsId);
		order.setGoodsInstanceId(this.goodsInstanceId);
		order.setTid(this.tid);
		order.setBuyAttr(this.buyAttr);
		return order;
	}

}
