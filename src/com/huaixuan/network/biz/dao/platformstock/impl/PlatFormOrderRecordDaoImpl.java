package com.huaixuan.network.biz.dao.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.platformstock.PlatFormOrderRecordDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.user.User;
@Repository("PlatFormOrderRecordDao")
public class PlatFormOrderRecordDaoImpl implements PlatFormOrderRecordDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public int searchOrderCount(Map<String, String> searchMap) {
		
		Object o = sqlMapClient.queryForObject("searchOrderCountOne",searchMap);
		if(o == null) return 0;
		return (Integer)o;
	}

	@Override
	public List<PlatFormOrderDetails> selectAllOrderid(Map<String, String> searchMap) {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("selectOrderidAll",searchMap);
	}

	@Override
	public void updateOrderExcel(PlatFormOrderRecord orderExcel) {
		
		sqlMapClient.update("updateOrderExcel",orderExcel);
	}

	@Override
	public PlatFormOrderRecord selectgetems(PlatFormOrderRecord order) {
		return (PlatFormOrderRecord)sqlMapClient.queryForObject("searchRecords",order);
	}

	//用于前段页面显示
	@Override
	public List<PlatFormOrderRecord> selectAllOrderidOne(
			Map<String, String> searchMap) {
		return sqlMapClient.queryForList("selectOrderidAllOne",searchMap);
	}

	
	@Override//count
	public Integer searchOrderCountByParams(Map parMap, PlatFormOrderRecord platFormOrderRecord) {
		return (Integer) sqlMapClient.queryForObject(
				"searchOrderCountOne", parMap);
	}

	@Override//map
	public List<PlatFormOrderRecord> selectAllOrderidByOrder(Map parMap, PlatFormOrderRecord platFormOrderRecord) {
		return sqlMapClient.queryForList("selectOrderidAllOne", parMap);
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.platformstock.PlatFormOrderRecordDao#updateOrderToBill(com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord)
	 */
	@Override
	public void updateOrderToBill(PlatFormOrderRecord pf) {
			sqlMapClient.update("updateOrdertobill",pf);
		}
		
	
	
}
