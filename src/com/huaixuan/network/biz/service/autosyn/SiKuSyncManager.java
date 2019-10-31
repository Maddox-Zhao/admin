package com.huaixuan.network.biz.service.autosyn;

import java.util.Map;



/**
 * @author Mr_Yang   2016-4-13 下午12:04:33
 * 司库 库存对接
 **/

public interface SiKuSyncManager
{
	/**
	 * 更新单个库存
	 * @param platformSku 平台sku
	 * @param ourSku 我们的sku
	 * @param nowNum 现在库存
	 * @param idProduct  暂时不用
	 */
	public boolean updateStockByOurSkuAndPlatFormSku(String type,String platformSku,String ourSku,int nowNum,String idProduct);
	
	
	/**
	 * 获取寺库库存
	 * @param type
	 * @param idProduct
	 * @return 返回 寺库当前库存  总库存-锁定库存
	 */
	public Map<Integer,Integer> getSiKuStockBySiKuSku(String type,String idProduct);
	
	
	
	/**
	 * 全量更新寺库库存
	 */
	public void updateCanSaleProduct();
	
	
	/**
	 * 更新寺库sku到本地
	 */
	public void updateSiKuSku2Location();
	
	
	/**
	 * 设置sku对应关系
	 * @param type
	 */
	public void setSiku2OurSkuMatch(String type);
}
 
