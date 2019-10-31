package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.StockNotUpdateDao;
import com.huaixuan.network.biz.domain.platformstock.StockNotUpdate;



/**
 * @author Mr_Yang   2016-11-10 下午02:18:01
 **/

@Repository("stockNotUpdateDao")
public class StockNotUpdateDaoImpl implements StockNotUpdateDao
{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public void deleteStockNotUpdate(Long id)
	{
		sqlMapClient.delete("deleteStockNotUpdateById",id);

	}

	@Override
	public void insertStockNotUpdate(StockNotUpdate stockNotUpdate)
	{
		sqlMapClient.insert("insertStockNotUpdate",stockNotUpdate);
	}

	@Override
	public List<StockNotUpdate> searchStockNotUpdateByMap(Map<String, String> map)
	{
		return sqlMapClient.queryForList("selectStockNotUpdateByMap",map);
	}

	@Override
	public int searchStockNotUpdateCntByMap(Map<String, String> map)
	{
		Object o =  sqlMapClient.queryForObject("selectStockNotUpdateByMapCount",map);
		if(o == null) return 0;
		return (Integer)o;
	}

	@Override
	public void updateStockUpdateSku2Null(Map<String, String> map)
	{
		sqlMapClient.update("updateStockUpdateSku2Null",map);
	}

}
 
