package com.huaixuan.network.biz.dao.api;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.product.Product;

 



/**
 * @author Mr_Yang   2015-12-4 上午10:59:13
 * 查询产品信息
 **/

public interface ApiProductDao
{
	/**
	 * 分页查询所有可售库存
	 * @param searchMap
	 * @return
	 */
	public List<StockData> getAvailableProductsByPages(Map<String,String> searchMap);
	
	public int getAvailableProductsByPagesCount(Map<String,String> searchMap);
	
	
	/**
	 * 根据sku获取欧洲价
	 * @param skus
	 * @return
	 */
	public Map<String,Product> getEuPriceBySkus(List<String> skusList);
}
 
