/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvidePostOrderLog {

	private int id;
	
	private String orderId; //订单号
	
	private Long prodId; //银泰商品唯一标识
	
	private String skuId; //云尚商品唯一标识
	
	private String sSprodid; //我们的唯一标识，由三个一，银泰商品唯一标识，随机数，一起生成
	private Integer qty;    //推送的数量
	
	private String ptype;  //哪个平台的卖出的货
	
	private String pushPlatform; //推送到哪个供应商
	
	private String createTime;  //创建时间
	
	private String status;   //订单推送后返回的状态
	
	private String message; //订单推送后返回的信息

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

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getsSprodid() {
		return sSprodid;
	}

	public void setsSprodid(String sSprodid) {
		this.sSprodid = sSprodid;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPushPlatform() {
		return pushPlatform;
	}

	public void setPushPlatform(String pushPlatform) {
		this.pushPlatform = pushPlatform;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	
	
	
	
}
