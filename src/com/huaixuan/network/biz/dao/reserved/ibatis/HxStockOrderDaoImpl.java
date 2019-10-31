package com.huaixuan.network.biz.dao.reserved.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.reserved.HxStockOrderDao;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderDetails;
import com.huaixuan.network.biz.domain.platformstock.PlatFormOrderRecord;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

@Repository("HxStockOrderDao")
public class HxStockOrderDaoImpl implements HxStockOrderDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	//查询PlatFormOrderRecord的总条数
	public int searchStockOrderOne(Map<String,String> searchmap){
		Object o = sqlMapClient.queryForObject("hxStockOrderCount", searchmap);
		if(o==null)return 0;
		return (Integer)o;
	}
	//查询PlatFormOrderRecord的详情
		@Override
		public List<PlatFormOrderRecord> searchStockOrderDc(Map<String, String> searchmap) {
			return  sqlMapClient.queryForList("searchStockOrderProduct",searchmap);
		}
	
	//查询总条数Derdetails
	@Override
	public int searchorDerdetails(Map<String, String> searchmap) {
		Object o = sqlMapClient.queryForObject("hxDerdetailsOrderCount", searchmap);
		if(o==null)return 0;
		return (Integer)o;
	}
	//根据条件查询Derdetails
	@Override
	public List<PlatFormOrderDetails> searchDetailsOrder(
			Map<String, String> searchmap) {
		return  sqlMapClient.queryForList("searchDetailsOrderProduct",searchmap);
	}

	@Override
	public int selectDerdetails(Map<String, String> searchmap) {
		Object o = sqlMapClient.queryForObject("selectDerdetailsOrderCount", searchmap);
		if(o==null)return 0;
		return (Integer)o;
	}

	@Override
	public List<PlatFormOrderDetails> selectDetailsOrder(Map<String, String> searchmap) {
		// TODO Auto-generated method stub		
		return  sqlMapClient.queryForList("selectDetailsOrderProduct",searchmap);
	}
	
	
	

	

}
