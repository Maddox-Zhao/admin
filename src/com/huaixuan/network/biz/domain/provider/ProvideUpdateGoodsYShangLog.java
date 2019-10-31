package com.huaixuan.network.biz.domain.provider;

public class ProvideUpdateGoodsYShangLog {

	private Long id;
	private String skuId;// 云尚商品唯一标识
	private String ourSku;// 我们的sku
	private int stock;// 库存数量
	private String productName;// 商品名

	private Double oldCost;// 成本价
	private Double newCost;// 新成本价
	private Double oldOurPrice;// 我们的销售价
	private Double newOurPrice;// 新销售价
	
	private String costChangeTime;// cost价格变动时间
	private String priceChangeTime;// price价格变动时间
	private String insertTime;// 数据生成时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOurSku() {
		return ourSku;
	}
	public void setOurSku(String ourSku) {
		this.ourSku = ourSku;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}


	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getOldCost() {
		return oldCost;
	}
	public void setOldCost(Double oldCost) {
		this.oldCost = oldCost;
	}
	public Double getNewCost() {
		return newCost;
	}
	public void setNewCost(Double newCost) {
		this.newCost = newCost;
	}


	public Double getNewOurPrice() {
		return newOurPrice;
	}
	public void setNewOurPrice(Double newOurPrice) {
		this.newOurPrice = newOurPrice;
	}
	public String getCostChangeTime() {
		return costChangeTime;
	}
	public void setCostChangeTime(String costChangeTime) {
		this.costChangeTime = costChangeTime;
	}
	public String getPriceChangeTime() {
		return priceChangeTime;
	}
	public void setPriceChangeTime(String priceChangeTime) {
		this.priceChangeTime = priceChangeTime;
	}
	
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public Double getOldOurPrice() {
		return oldOurPrice;
	}
	public void setOldOurPrice(Double oldOurPrice) {
		this.oldOurPrice = oldOurPrice;
	}


}
