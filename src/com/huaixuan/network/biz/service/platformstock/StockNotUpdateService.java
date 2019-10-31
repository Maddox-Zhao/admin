package com.huaixuan.network.biz.service.platformstock;

import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockNotUpdate;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2016-11-10 下午02:24:03
 * 不需要更新到各个平台的sku
 **/

public interface StockNotUpdateService
{
	public void insertStockNotUpdate(StockNotUpdate stockNotUpdate);
	
	public void deleteStockNotUpdate(Long id);
	
	public MiniUiGrid searchStockNotUpdateByMap(Map<String,String> searchMap);
	
	

	/**
	 * 
	 * @param sku 
	 * @param type sh-国内 hk-海外
	 * @param field 某个平台的sku字段 eg:shangpin_sku
	 */
	public void updateStockUpdateSku2Null(String sku,String type,String field);
	
	
	/**
	 * 处理不需要更新到平台的sku数据
	 */
	public void dealNotUpdate2PlatformSku();
}
 
