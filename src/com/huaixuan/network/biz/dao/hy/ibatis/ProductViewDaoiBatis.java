package com.huaixuan.network.biz.dao.hy.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hy.ProductViewDao;
import com.huaixuan.network.biz.domain.hy.ProductView;

/**
 *
 * @version 3.2.0
 */
@Repository("productViewDao")
public class ProductViewDaoiBatis implements ProductViewDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	@Override
	public Integer getProductViewByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getProductViewByConditionWithPageCount", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductView> getProductViewByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getProductViewByConditionWithPage", parMap);
	}


}
