/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;

/**
 * @author TT
 * 
 */
@Repository("ProvideOrderWaybillDetailDao")
public class provideOrderWaybillDetailDaoImpl implements ProvideOrderWaybillDetailDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao#insertWayBillDetail(java.util.Map)
	 */
	@Override
	public void insertWayBillDetail(Map<String, String> detailmap) {
		
		sqlMapClient.insert("insertWayBillDetai",detailmap);	
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao#selectWayBill(java.util.Map)
	 */
	@Override
	public List<ProvideOrderWaybillDetail> selectWayBill(Map<String, String> onemap) {
		return sqlMapClient.queryForList("selectWayBill",onemap);
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideOrderWaybillDetailDao#updateOrderWaybillDetail(java.util.Map)
	 */
	@Override
	public void updateOrderWaybillDetail(Map<String, String> detailmap) {
		sqlMapClient.update("updateOrderWaybillDetail",detailmap);
		
	}

	@Override
	public Integer searchMiniuiProviderOrderCount(Map<String, String> detailmap) {
		return (Integer)sqlMapClient.queryForObject("searchMiniuiProviderOrderCount",detailmap);
	}

	@Override
	public List<ProvideOrderWaybillDetail> selectMiniuiAllProviderOrder(
			Map<String, String> detailmap) {
		return  sqlMapClient.queryForList("selectMiniuiAllProviderOrder",detailmap);
	}

}
