package com.huaixuan.network.biz.service.platformstock;

import com.huaixuan.network.biz.domain.platformstock.StockNumber;

public interface KaolaPlatHaiTaoformStockUpdate {

	/**
	 * 更新SKU到本地
	 */
	public void updateSku2Location();
	
	/**
	 * 更新考拉海淘库存
	 */
	public boolean updateKaoLahtStocku(String kaolahtSku,String ourSku,int num,String type);
	
	/**
	 * 同步订单
	 */
	public  int atuoSyncOrder(String type);
	
	
	/**
	 * 获取考拉平台库存
	 */
	public StockNumber getKaolahtStock(String kaolahtSku,String kaolahtKey,String type);
	
	/**
	 * 更新待上架产品sku到本地
	 */
	public int updateWaitFoOnSaleSku2Location();
	
	/**
	 * 同步所有库存
	 */
	public void updateAllStock();
}
