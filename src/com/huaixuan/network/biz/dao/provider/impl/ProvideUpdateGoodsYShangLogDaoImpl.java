/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsYShangLog;

/**
 * @author TT
 * 
 */
@Repository("ProvideUpdateGoodsYShangLogDao")
public class ProvideUpdateGoodsYShangLogDaoImpl implements ProvideUpdateGoodsYShangLogDao {

	
	
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao#insertProvideYShangLog(com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsYShangLog)
	 */
	@Override
	public void insertProvideYShangLog(ProvideUpdateGoodsYShangLog pugxyl) {
		 sqlMapClient.insert("insertYShangProductlog",pugxyl);

	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao#insertYShangLog(java.util.List)
	 */
	@Override
	public void insertYShangLog(List<ProvideUpdateGoodsYShangLog> pgList) {
		sqlMapClient.insert("insertYShangLogList",pgList);

	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao#getYShangSaleInfoLogCount(java.util.Map)
	 */
	@Override
	public int getYShangSaleInfoLogCount(Map<String, String> searchMap) {
		Object obj = sqlMapClient.queryForObject("getYShangSaleInfoLogCount",searchMap);
		if(obj == null) return 0;
		return (Integer)obj;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao#getYShangSaleInfoLogList(java.util.Map)
	 */
	@Override
	public List<ProvideUpdateGoodsYShangLog> getYShangSaleInfoLogList(Map<String, String> searchMap) {
		List<ProvideUpdateGoodsYShangLog> queryForList =null;
		try {
			queryForList = sqlMapClient.queryForList("getYShangSaleInfoLogList",searchMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForList ;
	}

}
