package com.huaixuan.network.biz.dao.hx.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.hx.DxrecordDao;
import com.huaixuan.network.biz.domain.hx.Dxrecord;

@Repository("DxrecordDao")
public class DxrecordDaoiBatis implements DxrecordDao{
	@Autowired
	private SqlMapClientTemplate sqlMap;
	@Override
	public Long addDxrecord(Dxrecord dx) {
		return (Long)this.sqlMap.insert("addDxrecord", dx);
	}
	@Override
	public List<Dxrecord> queryDxrecordList(Map<String, String> searchMap) {
		return sqlMap.queryForList("dxrecordList", searchMap);
	}
	@Override
	public List<Dxrecord> statisticsDxrecordList(Map<String, String> searchMap) {
		return sqlMap.queryForList("statisticsDxrecordList", searchMap);
	}
	@Override
	public int getDxrecordLsitCount(Map<String, String> searchMap) {
		Object obj = sqlMap.queryForObject("getDxrecordLsitCount", searchMap);
		if(obj == null) 
		return 0;
		return (Integer)obj;
	}
	@Override
	public int getstatisticsDxrecordListCount(Map<String, String> searchMap) {
		Object obj = sqlMap.queryForObject("getstatisticsDxrecordListCount",searchMap);
		if(obj == null) 
		return 0;
		return (Integer)obj;
	}

}
