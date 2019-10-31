package com.huaixuan.network.biz.service.autosyn;

import java.util.List;

import com.huaixuan.network.biz.domain.autosyn.AutoSynDate;



/**
 * @author Mr_Yang   2015-10-12 下午02:55:52
 * 旺店通 库存对接
 **/

public interface AutoSyncWangDianTongManager
{
	/**
	 * 更新旺店通sku到本地
	 */
	public void updateWDTSku2Loacation();
	
	
	
	/**
	 * 获取旺店通sku的库存 然后更新库存不匹配的
	 * @return
	 */
	public AutoSynDate getLocationStockBySku(String sku);
	
	
	
	/**
	 * 更新该旺店通库存
	 * @param sku
	 * @return
	 */
	public void updateWangDianTongStock();
}	
 
