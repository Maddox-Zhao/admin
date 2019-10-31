package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-11-29 上午11:18:37
 **/

public interface FenQiLePlatFormStocuUpdate
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
	 * 更新分期乐库存
	 * @param fqlSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateFenQiLeStock(String fqlSku,String ourSku,int num,String type);
	
	
	
	
	/**
	 * 同步订单
	 * @param type
	 */
	public  int atuoSyncOrder();
	
	
	
}
 
