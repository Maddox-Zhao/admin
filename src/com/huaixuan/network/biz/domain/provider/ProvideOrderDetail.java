/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideOrderDetail {

	private int id;
	private String originalTradeId; //在订单详情中获取到订单编号
	private String tradeId; //我们自己生成的订单编号
	private String userName; //用户名称
	private String phone;  //电话
	private String province; //省
	
	private String city;    //市
	private String region; //区	
	private String location; //详细地址
	private String totalPrice; //订单总金额
	private String payPrice; //实付金额
	private String sellerMemo; //卖家备注
	
	private String insertTime;//插入时间
	private String createTime;//订单创建时间
	private String payTime;  //支付时间
	private String prodId; //银泰唯一标识
	private String itemId;// 我们的sku
	
	
	private String title; //商品名称
	private String sellPrice;//商品售价,实际上是cost，需要传给银泰的
	private String ourSellPrice;//商品售价，我们售卖的实际价格
	private String tax;    //税费
	private int qty;     //数量
	private String freight; //运费金额
	
	private String sellPlatform; //售卖的平台
	private String provider; //供货商
	
	
	private String cardNo; // 身份证号码（云尚）
	private String shopCoupon; //云尚的优惠金额（云尚）
	private String platformCoupon; //我们的优惠金额（云尚）
	private String orderChannelId; //订单渠道商用户 ID（云尚分配）
	private String orderChannelName; //渠道商用户名称（云尚分配）
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getOriginalTradeId() {
		return originalTradeId;
	}
	public void setOriginalTradeId(String originalTradeId) {
		this.originalTradeId = originalTradeId;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	
	public String getSellerMemo() {
		return sellerMemo;
	}
	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}
	public String getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getSellPlatform() {
		return sellPlatform;
	}
	public void setSellPlatform(String sellPlatform) {
		this.sellPlatform = sellPlatform;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getOurSellPrice() {
		return ourSellPrice;
	}
	public void setOurSellPrice(String ourSellPrice) {
		this.ourSellPrice = ourSellPrice;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getShopCoupon() {
		return shopCoupon;
	}
	public void setShopCoupon(String shopCoupon) {
		this.shopCoupon = shopCoupon;
	}
	
	public String getPlatformCoupon() {
		return platformCoupon;
	}
	public void setPlatformCoupon(String platformCoupon) {
		this.platformCoupon = platformCoupon;
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
	
	
	
}
