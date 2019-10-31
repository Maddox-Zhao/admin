package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-5-19 下午04:39:06
 **/

public interface YhdPlatformStockUpdate
{
	/**
	 * 更新库存到1号店
	 */
	public boolean updateYhdStocku(String platformSku,String ourSku,int num,String type);
	
	
	/**
	 * 更新sku对到本地
	 */
	public void updateSku2Location();
	
	
	/**
	 * 更新所有库存
	 */
	public void updateAllStock();
	
}
 
