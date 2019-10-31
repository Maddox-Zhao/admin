package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.StockUpdateLogDao;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
@Repository("stockUpdateLogDao")
public class StockUpdateLogDaoImpl implements StockUpdateLogDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public int searchStockCount(Map<String, String> searchMap) {
		
		Object o = sqlMapClient.queryForObject("searchStockCount",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}

	@Override
	public List<StockUpdateLog> selectAllStock(Map<String, String> searchMap) {
		
		return sqlMapClient.queryForList("selectStockAll",searchMap);
	}

	
	//cha zhao suo you de zhan dian
	@Override
	public List<StockUpdateLog> selectAllLocation() {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectAllLocation");
	}

}
