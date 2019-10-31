package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-10-20 上午11:39:33
 **/

public interface HigoPlatFormStockUpdate
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
	 * 更新HIGO库存
	 * @param higoSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateHigOStock(String higoSku,String ourSku,int num,String type);
	
	
	
	
	/**
	 * 同步订单
	 * @param type
	 */
	public  int atuoSyncOrder();
}
 
