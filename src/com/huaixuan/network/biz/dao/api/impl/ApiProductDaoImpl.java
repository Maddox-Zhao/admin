package com.huaixuan.network.biz.dao.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.api.ApiProductDao;
import com.huaixuan.network.biz.domain.autosyn.StockData;
import com.huaixuan.network.biz.domain.product.Product;



/**
 * @author Mr_Yang   2015-12-4 上午11:14:08
 **/

@Repository("apiProductDao")
public class ApiProductDaoImpl implements ApiProductDao
{
	 @Autowired
	 private SqlMapClientTemplate sqlMapClient;
	 
	@Override
	public List<StockData> getAvailableProductsByPages(Map<String, String> searchMap)
	{
		List<StockData> stockData = new ArrayList<StockData>();
		if(searchMap == null) return stockData;
		if(searchMap.get("idLocation") == null) return stockData;
		if(searchMap.get("startRow") == null) return stockData;
		if(searchMap.get("endRow") == null) return stockData;
		return sqlMapClient.queryForList("searchStockBySitesForApi",searchMap);
	}

	
	@Override
	public int getAvailableProductsByPagesCount(Map<String, String> searchMap)
	{
		if(searchMap == null) return 0;
		if(searchMap.get("idLocation") == null) return 0;
		Object o = sqlMapClient.queryForObject("searchStockBySitesForApiCount",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}


	@Override
	public Map<String,Product> getEuPriceBySkus(List<String> skusList)
	{
		Map<String,Product> map = new HashMap<String, Product>();//一个SKU可能会有多个sku后面的欧洲价覆盖前面欧洲价
		if(skusList==null || skusList.size() == 0) return map;
		List<Product> list = sqlMapClient.queryForList("searchEuPriceBySkus",skusList);
		for(Product p : list)
		{
			map.put(p.getSku(), p);
		}
		return map;
	}
	
}
 
