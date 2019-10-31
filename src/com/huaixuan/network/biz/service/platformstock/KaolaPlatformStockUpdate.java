package com.huaixuan.network.biz.service.platformstock;

import com.huaixuan.network.biz.domain.platformstock.StockNumber;



/**
 * @author Mr_Yang   2016-5-10 下午05:16:13
 * 考拉平台自动更新库存
 **/

public interface KaolaPlatformStockUpdate
{
	/**
	 * 更新sku对到本地
	 */
	public void updateSku2Location();
	
	
	/**
	 * 同步所有库存
	 */
	public void updateAllStock();
	
	/**
	 * 更新待上架产品sku到本地
	 */
	public int updateWaitFoOnSaleSku2Location();
	
	
	 
	
	
	/**
	 * 更新考拉库存
	 * @param kaolaSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateKaoLaStocku(String kaolaSku,String ourSku,int num,String type);
	
	
	/**
	 * 获取考拉平台库存
	 * @param kaolaSku
	 * @param kaolaKey
	 * @param type
	 * @return
	 */
	public StockNumber getKaolaStock(String kaolaSku,String kaolaKey,String type);
	
	
	/**
	 * 同步订单
	 * @param startTime
	 * @param endTime
	 * @param type
	 */
	public  int atuoSyncOrder(String type);
}
 
