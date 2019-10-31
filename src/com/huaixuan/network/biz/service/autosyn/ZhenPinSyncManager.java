package com.huaixuan.network.biz.service.autosyn;

import java.util.Map;



/**
 * @author Mr_Yang   2016-3-22 下午03:29:03
 * 珍品库存对接
 **/

public interface ZhenPinSyncManager
{
	/**
	 * 更新可售和下架的产品 库存和sku到本地
	 * 相当于全量更新产品库存
	 * 珍品只能更新审核通过的产品
	 */
	public void updateCanSaleProduct();
	
	
	/**
	 * 更新某段时间内有过库存改变的数据
	 * @param needUpdateMap
	 */
	public void  updateChangedStock(Map<String,Integer> needUpdateMap,String type);
	
	
	/**
	 * 更新单个库存
	 * @param type sh-上海  hk-香港
	 * @param platformSku 平台sku
	 * @param ourSku 我们的sku
	 * @param nowNum 现在库存
	 */
	public boolean updateStockByOurSkuAndPlatFormSku(String type,String platformSku,String ourSku,int nowNum);
}
 
