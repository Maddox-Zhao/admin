/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvidePostOrderLogDao;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;

/**
 * @author TT
 * 
 */
@Repository("ProvidePostOrderLogDao")
public class ProvidePostOrderLogDaoImpl implements ProvidePostOrderLogDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	 /*(non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvidePostOrderLogDao#insertPushOrderLog(java.util.Map)
	 */
	@Override
	public List<ProvidePostOrderLog> selectPushOrderLog(Map<String, String> maplog) {
		return sqlMapClient.queryForList("selectAllPushLog",maplog);
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvidePostOrderLogDao#insertPushOrderLog(java.util.Map)
	 */
	@Override
	public void insertPushOrderLog(Map<String, String> maplog) {
		 sqlMapClient.insert("insertPushLog",maplog);		
	}

}
