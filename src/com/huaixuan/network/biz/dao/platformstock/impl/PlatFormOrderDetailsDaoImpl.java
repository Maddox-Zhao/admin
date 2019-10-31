package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderDetailsDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;

@Repository("platFormOrderDetailsDao")
public class PlatFormOrderDetailsDaoImpl implements PlatFormOrderDetailsDao{

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	
	@Override
	public void insertOrder(PlatFormOrderDetails platformorderdetails) {
		 sqlMapClient.insert("platformorderdetails",platformorderdetails);
		
	}


	@Override
	public PlatFormOrderDetails select(PlatFormOrderDetails platformorderdetails) {
		return (PlatFormOrderDetails) sqlMapClient.queryForObject("searchdetails", platformorderdetails);
	}


	@Override
	public List<PlatFormOrderDetails> selectList(PlatFormOrderDetails platformorderdetails) {
		
		return sqlMapClient.queryForList("searchListWeimob",platformorderdetails);
	}

}
