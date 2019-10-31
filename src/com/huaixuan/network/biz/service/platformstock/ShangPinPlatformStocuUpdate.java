package com.huaixuan.network.biz.service.platformstock;

import com.huaixuan.network.biz.domain.platformstock.StockNumber;



/**
 * @author Mr_Yang   2016-5-11 下午01:49:15
 * 尚品自动同步库存
 **/

public interface ShangPinPlatformStocuUpdate
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
	 * 通过尚品sku获取库存
	 * @param shangpinSku
	 * @return
	 */
	public StockNumber getShangpinStockByShangPinSku(String shangpinSku,String type);
	
	/**
	 * 通过我们的sku获取尚品库存
	 * @param ourSku
	 * @return
	 */
	public StockNumber getShangpinStockByOurSku(String ourSku,String type);
	
	/**
	 * 通过尚品sku更新库存
	 * @param shangpinSku
	 * @param num
	 * @return
	 */
	public boolean updateShangpinStocku(String shangpinSku,String ourSku,int num,String type);
	
	/**
	 * 通过我们的sku更新库存
	 * @param shangpinSku
	 * @param num
	 * @type type
	 * @return
	 */
	public boolean updateShangpinStockByOurSku(String shangpinSku,int num,String type);
	
	
	/**
	 * 自动同步订单库存数到本地
	 * @param startTime
	 * @param endTime
	 */
	public  int atuoSyncOrder(String startTime,String endTime,String type);
	
}
 
