package com.huaixuan.network.biz.service.autosyn;

import java.util.List;
import java.util.Map;


 
/**
 *@author YangJie
 *@date 2015-8-6 下午05:11:34 尚品对接
 */
public interface AutoSyncShangPingManager
{
	/**
	 * 同步尚品库存
	 */
	public List<String>  syncShangPingStockForSh();
	
	
	
	/**
	 * 同步香港库存
	 * @return 同步成功List
	 */
	public List<String>  syncShangPingStockForHk();
	
	
	/**
	 * 更新库存
	 * @param sku  尚品sku
	 * @param num 当前库存 
	 * @param type  sh-更新上海 hk-更新香港
	 * @param huoHao 型号材质颜色+大小
	 * @param huoHao 更新之前尚品库存
	 */
	public Result updateShangpStock(String sku, Integer num,String type,String huoHao,Integer beforNum,String ourSku);
	
	
	/**
	 * 通过尚品货号和size获取供货商sku
	 * @param name
	 * @param size
	 * @return
	 */
	public String getSupplierSkuByNameAndSize(String name, String size);
	
	/**
	 * 更新尚品在本地数据sku
	 * @param type  sh-上海账号  hk-香港账号
	 */
	public List<String> updateShangpSku(String type,String isAll);
	
	

	/**
	 * 只更新有库存变化的数据
	 * @param type sh-上海  hk-香港
	 * @return
	 */
	public List<String>  updateStockNew(String type);
	
	
	/**
	 * 只更新有库存变化的数据
	 * @param type sh-上海  hk-香港
	 * @return
	 */
	public void updateChangedStock(Map<String,Integer> needUpdateMap,String type);
	
	
	
	/**
	 * 获取某段时间内有过库存改变的数据  
	 * @param type
	 * @return map  key-sku  value-当前数量
	 */
	public Map<String,Integer> getChangedStock(String type);
	
	
	

	/**
	 * 更新单个库存
	 * @param type sh-上海  hk-香港
	 * @param platformSku 平台sku
	 * @param ourSku 我们的sku
	 * @param nowNum 现在库存
	 */
	public boolean updateStockByOurSkuAndPlatFormSku(String type,String platformSku,String ourSku,int nowNum);

}
