package com.huaixuan.network.biz.dao.hy.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hy.HistoryViewDao;
import com.huaixuan.network.biz.domain.hy.HistoryView;

/**
 *
 * @version 3.2.0
 */
@Repository("historyViewDao")
public class HistoryViewDaoiBatis implements HistoryViewDao {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
	@Override
	public Integer getHistoryViewByConditionWithPageCount(Map parMap) {
		return (Integer)sqlMapClient.queryForObject("getHistoryViewByConditionWithPageCount", parMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryView> getHistoryViewByConditionWithPage(Map parMap) {
		return sqlMapClient.queryForList("getHistoryViewByConditionWithPage", parMap);
	}


}
