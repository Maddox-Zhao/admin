package com.huaixuan.network.biz.service.autosyn;

import java.util.Map;



/**
 * @author Mr_Yang   2016-3-21 下午02:46:41
 **/

public interface KaoLaSyncManager
{
	/**
		 * 同步可售和下架的产品 库存和sku到本地
	 * 相当于全量更新产品库存
	 * 考拉只能更新 可售和已下架的产品库存 其他状态的不能更新库存信息
	 */
	public void updateCanSaleProduct();
	
	
	/**
	 *更新考拉sku到本地 
	 */
	public void updateSku2Location();
	
	
	/**
	 * 同步某段时间内有过库存改变的产品库存
	 * @param needUpdateMapSh
	 * @param type
	 */
	public void updateChangedStock(Map<String,Integer> needUpdateMapSh, String type);
	
	
	/**
	 * 更新单个库存
	 * @param platformSku 平台sku
	 * @param ourSku 我们的sku
	 * @param nowNum 现在库存
	 * @param idProduct 用于查询考拉key 和产品客户
	 */
	public boolean updateStockByOurSkuAndPlatFormSku(String type,String platformSku,String ourSku,int nowNum,String idProduct);
	
	
	/**
	 * 获取考拉库存
	 * @param type
	 * @param idProduct
	 * @param kaolaSku
	 */
	public int getKaoLaStockByKaoLaSku(String type,String idProduct,String kaolaSku);
}
 
