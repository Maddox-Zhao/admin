/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao;
import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;

/**
 * @author TT
 * 
 */
@Repository("ProvideOrderDetailDao")
public class ProvideOrderDetailDaoImpl implements ProvideOrderDetailDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao#insertOrderDetailList(java.util.List)
	 * 获取供应商的订单插入单独存放供应商商品的表
	 */
	
	@Override
	public void insertOrderDetailList(List<ProvideOrderDetail> orderIdProList) {
		sqlMapClient.insert("insertOrderDetailList",orderIdProList);	
		
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao#selectOrderDetailCount(java.util.Map)
	 */
	@Override
	public int selectOrderDetailCount(Map<String, String> searchmap) {
		Object o =  sqlMapClient.queryForObject("selectOrderDetailCount",searchmap);
		if(o == null) return 0;
		return (Integer)o;
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderDetailDao#selectOrderDetailByMap(java.util.Map)
	 */
	@Override
	public List<ProvideOrderDetail> selectOrderDetailByMap(
			Map<String, String> searchmap) {
		
		return sqlMapClient.queryForList("selectOrderDetailByMap",searchmap);
	}

	@Override
	public List<ProvideOrderDetail> selectProviderOrderId(String orderId) {
		return sqlMapClient.queryForList("selectProviderOrderId",orderId);
	}

	@Override
	public Integer updateProviderOrderDetailDao(Map<String, String> searchmap) {
		//int update = sqlMapClient.update("updateProviderOrderDetailDao", searchmap);
		return sqlMapClient.update("updateProviderOrderDetailDao", searchmap);
	}

}
