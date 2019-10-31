/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailYShangDao;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetailYShang;

/**
 * @author TT
 * 
 */
@Repository("ProvideOrderWaybillDetailYShangDao")
public class ProvideOrderWaybillDetailYShangDaoImpl implements ProvideOrderWaybillDetailYShangDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/*  
	 * 
	 */
	@Override
	public void insertWayBillDetailYShang(Map<String, String> detailmap) {
		sqlMapClient.insert("insertWayBillDetaiYShang",detailmap);	

	}

	/*  
	 * 
	 */
	@Override
	public void updateOrderWaybillDetailYShang(Map<String, String> detailmap) {
		sqlMapClient.update("updateOrderWaybillDetailYShang",detailmap);

	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailYShangDao#selectOrderListDetail(java.util.Map)
	 */
	@Override
	public List<ProvideOrderWaybillDetailYShang> selectMiniuiOrderListDetail(Map<String, String> detailmap) {
		 
		return sqlMapClient.queryForList("selectWayBillDetailYShang",detailmap);
	}

	/*  
	 *
	 */
	@Override
	public Integer selectMiniuiOrderListDetailCount(Map<String, String> detailmap) {
		 
		return (Integer)sqlMapClient.queryForObject("selectMiniuiOrderListDetailCount",detailmap);
	}

}
