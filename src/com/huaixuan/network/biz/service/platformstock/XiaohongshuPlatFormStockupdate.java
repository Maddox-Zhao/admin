package com.huaixuan.network.biz.service.platformstock;

import com.huaixuan.network.biz.domain.platformstock.StockNumber;

public interface XiaohongshuPlatFormStockupdate {

	/**
	 * 更新小红书SKU到本地
	 */
	public void updateSku2Location();
	
	/**
	 * 同步所有库存
	 */
	 public void updateAllStock();
	 
	/**
	 * 更新小红书库存
	 */
	 public boolean updateCanSaleProduct(String xhsSku,String ourSku,int quantity,String type);
	 
	 /**
	  * 同步订单
	  */
	 public int atuoSyncOrder(String type);
}
