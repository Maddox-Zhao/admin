package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockNotUpdate;



/**
 * @author Mr_Yang   2016-11-10 下午02:09:37
 **/

public interface StockNotUpdateDao
{
	public void insertStockNotUpdate(StockNotUpdate stockNotUpdate);
	
	public void deleteStockNotUpdate(Long id);
	
	public List<StockNotUpdate> searchStockNotUpdateByMap(Map<String,String> map);
	
	public int searchStockNotUpdateCntByMap(Map<String,String> map);
	
	
	/**
	 * 更新某个sku的平台sku为null  不更新库存到平台
	 * 必须要field sku type字段
	 * @param map
	 */
	public void updateStockUpdateSku2Null(Map<String,String> map);
}
 
