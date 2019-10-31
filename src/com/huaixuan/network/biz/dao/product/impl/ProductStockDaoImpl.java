package com.huaixuan.network.biz.dao.product.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.product.ProductStockDao;



/**
 * @author Mr_Yang   2015-12-15 上午11:22:43
 **/

@Repository
public class ProductStockDaoImpl implements ProductStockDao
{
	@Autowired
	private SqlMapClientTemplate sqlClientTemplate;
	
	@Override
	public boolean updateEmallGoodsStock(Map<String, String> map)
	{
		//map包含hk_goods_number 也要更新hk_goods_number的库存
		if(null == map) return false;
		if(map.get("goodsId") == null) return false;
		int cnt = sqlClientTemplate.update("updateEmallGoodsStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

	@Override
	public boolean updateHxAvaliableStock(Map<String, String> map)
	{
		
		if(null == map) return false;
		if(map.get("goodsId") == null || map.get("goodsInstanceId") == null || map.get("siteId") == null) return false;
		int cnt = sqlClientTemplate.update("updateHxAvaliableStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

	@Override
	public boolean updateIossGoodInstanceStock(Map<String, String> map)
	{
		if(null == map) return false;
		if(map.get("goodsId") == null) return false;
		int cnt = sqlClientTemplate.update("updateIossGoodInstanceStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

	
	/**
	 * 添加emall_goods库存
	 */
	@Override
	public boolean addEmallGoodsStock(Map<String, String> map)
	{
		
		//map包含hk_goods_number 也要更新hk_goods_number的库存
		if(null == map) return false;
		if(map.get("goodsId") == null) return false;
		int cnt = sqlClientTemplate.update("addEmallGoodsStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

	/**
	 * map goodsId goodsInstanceId  siteId必填
	 * map goods_sale_number 存在 goods_sale_number-1 (退货)
	 */
	@Override
	public boolean addHxAvaliableStock(Map<String, String> map)
	{
		if(null == map) return false;
		if(map.get("goodsId") == null || map.get("goodsInstanceId") == null || map.get("siteId") == null) return false;
		int cnt = sqlClientTemplate.update("addHxAvaliableStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

	/**
	 * map  goodsId 必填
	 * map sell_num存在  sell_num-1   （退货）
	 * map hk_goods_number 存在hk_goods_number+1
	 */
	@Override
	public boolean addIossGoodInstanceStock(Map<String, String> map)
	{
		if(null == map) return false;
		if(map.get("goodsId") == null) return false;
		int cnt = sqlClientTemplate.update("addIossGoodInstanceStock",map);
		if(cnt > 0 ) return true;
		return false;
	}

}
 
