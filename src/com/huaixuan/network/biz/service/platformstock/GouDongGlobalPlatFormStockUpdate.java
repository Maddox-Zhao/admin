package com.huaixuan.network.biz.service.platformstock;
/**
 * 
 * @Description: 京东全球购平台对接
 * @author zxt
 * @date 2018-10-30
 */
public interface GouDongGlobalPlatFormStockUpdate {
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
