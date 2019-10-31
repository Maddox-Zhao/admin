package com.huaixuan.network.biz.dao.hy.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hy.ProductViewAllDao;
import com.huaixuan.network.biz.domain.hy.ProductViewAll;

/**
 *
 * @version 3.2.0
 */
@Repository("productViewAllDao")
public class ProductViewAllDaoiBatis implements ProductViewAllDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	@Override
	public Integer getProductViewAllByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getProductViewAllByConditionWithPageCount", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getProductHistoryByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getProductHistoryByConditionWithPageCount", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> getProductViewAllByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getProductViewAllByConditionWithPage", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> getProductHistoryByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getProductHistoryByConditionWithPage", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> getProductViewAllSumByConditionWithPage(
			Map parMap) {
		return sqlMapClient.queryForList("getProductViewAllSumValue", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> productViewCostSumValue(
			Map parMap) {
		return sqlMapClient.queryForList("productViewCostSumValue", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductViewAll> productViewToday() throws Exception{
		return sqlMapClient.queryForList("productViewToday", null);
	}

	@Override
	public Integer addProductHistory(ProductViewAll productViewAll) throws Exception {
		return (Integer) sqlMapClient.insert("addProductHistory", productViewAll);
	}

}
