package com.huaixuan.network.biz.service.platformstock;



/**
 * @author Mr_Yang   2016-10-21 下午05:11:10
 * 天猫库存对接
 **/

public interface SuNingPlatFormStockUpdate
{
	/**
	 * 更新sku对到本地
	 */
	public void updateSku2Location();
	
	
	/**
	 * 同步所有库存
	 */
	public void updateAllStock();
	
	

	/**
	 * 更新tmall库存
	 * @param tmallNumIid num_iid	Number	必须	3838293428		商品数字ID，必填参数
	 * @param ourSku 我们的sku  必填  通过tmallNumIid+ourSku更新库存
	 * @param num 数量 必填
	 * @param type 类型 必填 国内海外  tmall只有国内 不卖海外
	 * @return
	 */
	public boolean updateSuNingStock(String tmallNumIid,String ourSku,int num,String type);
	
	
	
	
	/**
	 * 同步订单
	 * @param type
	 */
	public  int atuoSyncOrder();

}
 
