package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsXiYouLogDao;
import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;

@Repository("ProvideUpdateGoodsXiYouLogDao")
public class ProvideUpdateGoodsXiYouLogDaoImpl implements ProvideUpdateGoodsXiYouLogDao{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void insertProvideXiYouLog(ProvideUpdateGoodsXiYouLog pugxyl) {
		
		 sqlMapClient.insert("insertXiYouProductlog",pugxyl);
		
	}

	@Override
	public int getproviderSaleInfolistCount(Map<String, String> searchMap) {
		Object obj = sqlMapClient.queryForObject("getproviderSaleInfolistCount",searchMap);
		if(obj == null) return 0;
		return (Integer)obj;
	}

	@Override
	public List<ProvideUpdateGoodsXiYouLog> getproviderSaleInfoList(
			Map<String, String> searchMap) {
		List<ProvideUpdateGoodsXiYouLog> queryForList =null;
		try {
			queryForList = sqlMapClient.queryForList("getproviderSaleInfoList",searchMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryForList ;
	}

}
