package com.huaixuan.network.biz.service.autosyn;

import java.util.List;

/**
 * 同步本地庫存到走秀
 * @author ss9
 *
 */
public interface AutoSyncZouXiuManager 
{
	
	/**
	 * 更新走秀sku到本地
	 * @param type  sh-上海账号  hk-香港账号 默认hk
	 * @param isAll isAll=true 更新所有  默认只更新1个小时内sku到本地
	 */
	public List<String> updateZouXiuSku(String type,String isAll);
	
	
	
	/**
	 * 更新库存
	 * @param sku  走秀sku
	 * @param num 当前库存 
	 * @param type  sh-更新上海 hk-更新香港 默认更新香港
	 * @return
	 */
	public Result updateZouXiuStock(String sku, int realyNum,int serverNum, String type,String huoHao);
	
	
	/**
	 * 
	 * @param updateType sh-更新上海 hk-更新香港 默认更新香港
	 * @return
	 */
	public List<String> getZouXiuStock(String updateType);
}
