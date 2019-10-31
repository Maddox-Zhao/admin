package com.huaixuan.network.biz.domain.platformstock;

public class WeimobEntity {

	
	
	private String sku_code;
	private int  inventory;
	
	
	public String getSku_code() {
		return sku_code;
	}

	public void setSku_code(String sku_code) {
		this.sku_code = sku_code;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public WeimobEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeimobEntity(String sku_code, int inventory) {
		super();
		this.sku_code = sku_code;
		this.inventory = inventory;
	}
	
	
	
}
