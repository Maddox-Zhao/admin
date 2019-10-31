package com.huaixuan.network.biz.domain.autosyn;

public class OrderItem {

	private String skuId;
	
	private String merchantSkuId;
	
	private String quantity;
	
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
