/**
 * 
 */
package com.huaixuan.network.biz.service.platformstock;

/**
 * @author TT
 * 
 */
public interface YinTaiPlatFormStockUpdate {
   
	/*
	 * 银泰平台sku更新到本地
	 * 
    */
	public void updateSku2Location();
	
	
	/**
	 * 同步所有库存
	 */
	 public void updateAllStock();


	/**
	 * 当平台库存发生变化时，执行
	 * @param yinTaiSku
	 * @param ourSku
	 * @param quantity
	 * @param type
	 * @return
	 */
	 public boolean updateYinTaiStock(String oursku,String weimobsku,int quantity,String type);
	 
	 
	 
	 
     /*
	 * 同步订单
	 */
	
	public int atuoSyncOrder();
	
	
}
