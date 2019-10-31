package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-11-29 上午11:18:37
 * 狗东库存对接
 **/

public interface GouDongPlatFormStocuUpdate
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
	 * 更新JD库存
	 * @param jdSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateGouDongStock(String jdSku,String ourSku,int num,String type);
	
	
	
	
	/**
	 * 同步订单
	 * @param type
	 */
	public  int atuoSyncOrder();
}
 
