package com.huaixuan.network.biz.service.platformstock;

public interface PinDuoDuoPlatFormStockUpdate {
	
	/**
	 * 更新SKU到本地
	 */
	public void updateSku2Location();
	
	/**
	 * 更新拼多多库存
	 */
	 public  boolean updatePinDuoduoStock(String pddSku,String ourSku,int quantity,String type);
	 
	/**
	 * 同步所有库存
	 */
	 public void updateAllStock();
	 
	/**
	 * 同步订单
	 */
	public int atuoSyncOrder();
}