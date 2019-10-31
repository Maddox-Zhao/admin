package com.huaixuan.network.biz.domain.platformstock;

public class PlatFormOrderDetails {


	private String idorder;//订单ID
	private String tmall;//是否是天猫订单（魅力会平台）
		
	private String palcedTime;//平台下单时间
	private String insertTime;//本地创建时间
	private String name;//收件人姓名
	private String realName;//报关人姓名
	private String identityNumber;//报关人身份证号码
	
	private String mobile;//收件人电话号码
	private String country;//国家
	private String province;//省份
	private String city;//城市
	
	private String district;//区
	private String streetAddress;//街道地址
	private String zipCode;//邮政编码
	private String totalPrice;//订单总价
	private String payprice;//实付金额
	private String discountPrice;//优惠总价
	private String freight;//运费
	private String skuId;//平台SKU
	private String merchantSkuId;//我们的SKU
	
	private String quantity;//商品数量
	private String productname;//商品名称
	private String size;//商品尺寸
	private String price;//商品售价
	private String supplyprice;//供货价
	
	
	private String currency;//币种
	private String emsCode;    //快递单号
	private String emsCompany;//快递公司
	
	private int idPlartform; //平台编码
	private int idStatus;//状态1-已售订单 2-取消订单
	private String createDate; //接受订单时间
	private String status;//订单状态
	private String ptype;//平台
	
	private String payPrice;
	private String payTime; //付款时间
	
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getIdorder() {
		return idorder;
	}
	public void setIdorder(String idorder) {
		this.idorder = idorder;
	}
	public String getTmall() {
		return tmall;
	}
	public void setTmall(String tmall) {
		this.tmall = tmall;
	}
	public String getPalcedTime() {
		return palcedTime;
	}
	public void setPalcedTime(String palcedTime) {
		this.palcedTime = palcedTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getMerchantSkuId() {
		return merchantSkuId;
	}
	public void setMerchantSkuId(String merchantSkuId) {
		this.merchantSkuId = merchantSkuId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSupplyprice() {
		return supplyprice;
	}
	public void setSupplyprice(String supplyprice) {
		this.supplyprice = supplyprice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getPayprice() {
		return payprice;
	}
	public void setPayprice(String payprice) {
		this.payprice = payprice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getPayPrice() {
		return payPrice;
	}
}
