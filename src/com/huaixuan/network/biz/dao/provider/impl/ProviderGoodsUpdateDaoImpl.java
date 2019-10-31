/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;

/**
 * @author TT
 * 
 */
@Repository("ProviderGoodsUpdateDao")
public class ProviderGoodsUpdateDaoImpl implements ProviderGoodsUpdateDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.platformstock.PlatformStockUpdateDao#insertXiYouProducts(java.util.List)
	 */
	@Override
	public void insertXiYouProducts(List<ProvideGoodsXiYou> pgList) {
		
		
	 
	    sqlMapClient.insert("insertXiYouProduct",pgList);
	 
				
		
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#selectAllProid(com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou)
	 */
	@Override
	public List<ProvideGoodsXiYou> selectAllProid(ProvideGoodsXiYou provideGoodsXiYou) {
		return sqlMapClient.queryForList("selectAllProid",provideGoodsXiYou);
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#selectOneProidProduct(com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou)
	 */
	@Override
	public ProvideGoodsXiYou selectOneProidProduct(ProvideGoodsXiYou pg) {
		
		return (ProvideGoodsXiYou) sqlMapClient.queryForObject("selectOneProidProduct", pg);
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#updateGoodsXiYou(java.util.Map)
	 */
	@Override
	public void updateGoodsXiYou(ProvideGoodsXiYou pxy) {
		sqlMapClient.update("updateGoodsXiYou",pxy);
		
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#updateGoodsXiYouMap(java.util.Map)
	 */
	@Override
	public Integer updateGoodsXiYouMap(Map<String, String> maps) {
		Integer i=sqlMapClient.update("updateGoodsXiYouMap",maps);
		if(i==null)return 0;
//		System.out.println(i+"-------------------------i");
		return i;
	}
	
	
	
	@Override
	public Integer getProviderWithPageCount(Map parMap) {//Goods.getGoodsByConditionCounts
		return  (Integer)sqlMapClient.queryForObject("getProviderWithPageCount", parMap);
	}
	@Override
	public List<ProvideGoodsXiYou> getProviderListByConditionWithPage(Map parMap) {//Goods.getGoodsByConditions
		return sqlMapClient.queryForList("getProviderListByConditionWithPage", parMap);
	}
	
	
	@Override
	public Integer getProviderLogPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getProviderLogPageCount", parMap);
	}
	@Override
	public List<ProvidePostOrderLog> getProviderLogConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getProviderLogConditionWithPage", parMap);
	}
	/* 
	 * 查询出stock>0的所有商品
	 */
	@Override
	public List<ProvideGoodsXiYou> selectProidHaveStock() {
        
		return sqlMapClient.queryForList("getProvidStockNoZero");
	}
	
	
	
	//以下是云尚的商品
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#getProviderWithPageCountYShang(java.util.Map)
	 */
	@Override
	public Integer getProviderWithPageCountYShang(Map parMap) {
		return  (Integer)sqlMapClient.queryForObject("getProviderWithPageCountYShang", parMap);
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao#getProviderListByConditionWithPageYShang(java.util.Map)
	 */
	@Override
	public List<ProvideGoodsYShang> getProviderListByConditionWithPageYShang(Map parMap) {
		return sqlMapClient.queryForList("getProviderListByConditionWithPageYShang", parMap);
	}
		
	}


