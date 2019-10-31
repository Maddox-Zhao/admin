package com.huaixuan.network.biz.domain.taobao;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.huaixuan.network.common.util.remote.TaobaoFenXiaoUtils;


/**
 * 订单�
 */
public class Order implements Serializable{

	private static final long serialVersionUID = -1645865632872118189L;

	/**
	 * 商品字符串编�
	 */
	private String iid;

	/**
	 * 商品数字编号
	 */
	private Long numIid;

	/**
	 * 商品�小属性单�
	 */
	private String skuId;

	/**
	 * SKU的��如：机身颜�黑色;手机套餐:官方标配
	 */
	private String skuPropertiesName;

	/**
	 * 套餐的��如：M8原装电池:便携支架:M8专用座充:莫凡保护�
	 */
	private String itemMealName;

	/**
	 * 购买数量
	 */
	private Integer num;

	/**
	 * 商品标题
	 */
	private String title;

	/**
	 * 商品价格
	 */
	private String price;

	/**
	 * 商品图片路径
	 */
	private String picPath;

	/**
	 * 淘宝交易号，即父订单在淘宝业务订单中的bizOrderId
	 */
	private String tid;

	/**
	 * �款状�
	 */
	private String refundStatus;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 交易类型
	 */
	private String type;

	/**
	 * 创建时间
	 */
	private String created;

	private String outerSkuId;

	private String outerIid;

	/**
	 * 应付金额
	 */
	private String totalFee;

	/**
	 * 实付金额
	 */
	private String payment;

	/**
	 * 系统优惠金额
	 */
	private String discountFee;

	/**
	 * 卖家优惠金额
	 */
	private String adjustFee;

	/**
	 * 订单状�
	 */
	private String status;

	/**
	 * 订单快照地址
	 */
	private String snapshotUrl;

	/**
	 * 超时到期时间
	 */
	private String timeoutActionTime;

	/**
	 * 订单快照
	 */
	private String snapshot;

	/**
	 * 商品备注 专用
	 */
	private String itemMemo;

	private String modified;

	private Boolean buyerRate;
	private Boolean sellerRate;

	private Long refundId;



	/**
	 * 卖家类型
	 */
	private String sellerType;

	public String getItemMemo() {
		return itemMemo;
	}

	public void setItemMemo(String itemMemo) {
		this.itemMemo = itemMemo;
	}

	public String getSkuPropertiesName() {
		return skuPropertiesName;
	}

	public void setSkuPropertiesName(String skuPropertiesName) {
		this.skuPropertiesName = skuPropertiesName;
	}

	public String getItemMealName() {
		return itemMealName;
	}

	public void setItemMealName(String itemMealName) {
		this.itemMealName = itemMealName;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getOuterSkuId() {
		return outerSkuId;
	}

	public void setOuterSkuId(String outerSkuId) {
		this.outerSkuId = outerSkuId;
	}

	public String getOuterIid() {
		return outerIid;
	}

	public void setOuterIid(String outerIid) {
		this.outerIid = outerIid;
	}

	/**
	 * @return the iid
	 */
	public String getIid() {
		return iid;
	}

	/**
	 * @param iid the iid to set
	 */
	public void setIid(String iid) {
		this.iid = iid;
	}

	@XmlElement(name="num_iid")
	public Long getNumIid() {
		return this.numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the picPath
	 */
	@XmlElement(name="pic_path")
	public String getPicPath() {
		return picPath;
	}

	/**
	 * @param picPath the picPath to set
	 */
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * @deprecated replaced by getOid
	 */
	public String getTid() {
		return tid;
	}

	/**
	 * @deprecated replaced by setOid
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getOid() {
		return tid;
	}

	public void setOid(String oid) {
		this.tid = oid;
	}

	/**
	 * @return the refundStatus
	 */
	@XmlElement(name="refund_status")
	public String getRefundStatus() {
		return refundStatus;
	}

	/**
	 * @param refundStatus the refundStatus to set
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * @return the sellerNick
	 */
	public String getSellerNick() {
		return sellerNick;
	}

	/**
	 * @param sellerNick the sellerNick to set
	 */
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	/**
	 * @return the buyerNick
	 */
	public String getBuyerNick() {
		return buyerNick;
	}

	/**
	 * @param buyerNick the buyerNick to set
	 */
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	@XmlElement(name="total_fee")
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@XmlElement(name="discount_fee")
	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	@XmlElement(name="adjust_fee")
	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSnapshotUrl() {
		return snapshotUrl;
	}

	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}

	public String getTimeoutActionTime() {
		return timeoutActionTime;
	}

	public void setTimeoutActionTime(String timeoutActionTime) {
		this.timeoutActionTime = timeoutActionTime;
	}

	public String getModified() {
		return this.modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	@XmlElement(name="buyer_rate")
	public Boolean getBuyerRate() {
		return this.buyerRate;
	}

	public void setBuyerRate(Boolean buyerRate) {
		this.buyerRate = buyerRate;
	}

	@XmlElement(name="seller_rate")
	public Boolean getSellerRate() {
		return this.sellerRate;
	}

	public void setSellerRate(Boolean sellerRate) {
		this.sellerRate = sellerRate;
	}

	public Long getRefundId() {
		return this.refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	@XmlElement(name="seller_type")
	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, TaobaoFenXiaoUtils.getStringStyle());
	}


}
