package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockNumber;



/**
 * @author Mr_Yang   2016-5-11 下午12:06:27
 * 寺库平台同步库存
 **/

public interface SiKuNewPlatFormStockUpdate
{
	/**
	 * 更新sku对到本地
	 */
	public void updateSku2Location();
	
	
	/**
	 * 同步所有可售 待售产品库存
	 */
	public void updateAllStock();
	
	
	/**
	 * 获取寺库库存
	 * @param sikuSku
	 * @param type
	 * @return
	 */
	public StockNumber getSikuStock(String sikuSku,String type);
	
	
	/**
	 * 更新寺库库存
	 * @param sikuSku
	 * @param ourSku
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean updateSikuStock(String sikuSku,String ourSku,int num,String type);
	
	
	/**
	 * 设置寺库 和本地sku对应关系 从文件设置
	 * @param keyMap key 为寺库sku value为我们sku
	 * @param type
	 */
	public void updateSku2LocationByFile(Map<String,String> keyMap,String type);
	
	
	
	/**
	 * 自动同步订单库存数到本地 默认同步当前的未发货订单
	 * @param startTime
	 * @param endTime
	 */
	public  int atuoSyncOrder(String type);
	
}
 
