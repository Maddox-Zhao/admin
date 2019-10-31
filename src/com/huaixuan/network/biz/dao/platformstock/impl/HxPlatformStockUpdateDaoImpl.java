package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.HxPlatformStockUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.StockUpdate;

@Repository("HxPlatformStockUpdateDao")
public class HxPlatformStockUpdateDaoImpl implements HxPlatformStockUpdateDao {

	 @Autowired
	 
	 private SqlMapClientTemplate sqlMapClient;
	@Override
	public int HxStockUpdatecnt(Map<String, String> searchmap) {
		
		Object o = sqlMapClient.queryForObject("HxStockUpdateCount", searchmap);
		if(o==null)return 0;
		return (Integer)o;
	}

	@Override
	public List<StockUpdate> HxSelectStockUpdate(Map<String, String> searchmap) {
		return sqlMapClient.queryForList("SelectStockUpdateList", searchmap);
	}

}
