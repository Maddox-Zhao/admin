package com.huaixuan.network.biz.dao.product;

import java.util.Map;



/**
 * @author Mr_Yang   2015-12-15 上午11:19:20
 * 产品状态改变  更新库存信息
 **/

public interface ProductStockDao
{
	/**
	 * 更新emall_goods库存
	 * @param map
	 */
	public boolean updateEmallGoodsStock(Map<String,String> map);
	
	/**
	 * 更新instance库存
	 * @param map
	 */
	public boolean updateIossGoodInstanceStock(Map<String,String> map);
	
	
	/**
	 * 更新站点库存
	 * @param map
	 * @return
	 */
	public boolean updateHxAvaliableStock(Map<String,String> map);
	
	
	
	/**
	 * 添加emall_goods库存
	 * @param map
	 */
	public boolean addEmallGoodsStock(Map<String,String> map);
	
	/**
	 * 添加instance库存
	 * @param map
	 */
	public boolean addIossGoodInstanceStock(Map<String,String> map);
	
	
	/**
	 * 添加站点库存
	 * @param map
	 * @return
	 */
	public boolean addHxAvaliableStock(Map<String,String> map); 
	
	
	
}
 
