package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-5-11 上午11:25:19
 * 珍品平台自动更新库存
 **/

public interface ZhenPinPlatformStockUpdate
{
	/**
	 * 更新sku对到本地
	 */
	public void updateSku2Location();
	
	
	/**
	 * 同步珍品可售 和下架产品库存
	 */
	public void updateAllStock();
	
	
	
	/**
	 * 更新珍品库存
	 * @param zhenpinSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateZhenpinStock(String zhenpinSku,String ourSku,int num,String type);
	
	
	/**
	 * 自动同步订单库存数到本地
	 * @param startTime
	 * @param endTime
	 */
	public  int atuoSyncOrder(String startTime,String endTime,String type);
}
 
