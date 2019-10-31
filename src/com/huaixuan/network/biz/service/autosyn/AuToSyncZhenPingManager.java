package com.huaixuan.network.biz.service.autosyn;



/**
 * @author Mr_Yang   2015-11-17 下午03:04:54
 * 真品网自动同步库存
 **/

public interface AuToSyncZhenPingManager
{
	/**
	 * 自动同步珍品sku到本地
	 */
	 public void autoUpdateSku2Location();
	 
	 /**
	  * 自动同步库存到珍品
	  */
	 public void  autoUpdateZhenPinStock();
}
 
