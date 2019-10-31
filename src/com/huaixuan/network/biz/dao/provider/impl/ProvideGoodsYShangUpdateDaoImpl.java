/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProviderYShangPage;

/**
 * @author TT
 * 
 */
@Repository("ProvideGoodsYShangUpdateDao")
public class ProvideGoodsYShangUpdateDaoImpl implements ProvideGoodsYShangUpdateDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * 
	 */
	@Override
	public List<ProvideGoodsYShang> selectYShangEntityByMap(Map<String, String> map) {
		 
		return sqlMapClient.queryForList("selectYShangEntity",map);
	}

	/* (non-Javadoc)
	 * 
	 */
	@Override
	public void insertProvideGoodsYShang(List<ProvideGoodsYShang> pgysList) {
		sqlMapClient.insert("insertYShangProduct",pgysList);
	}

	/* (non-Javadoc)
	 * 
	 */
	@Override
	public Integer updateGoodsYShangMap(Map<String, String> maps) {

		Integer i=sqlMapClient.update("updateGoodsYShangMap",maps);
		if(i==null)return 0;
//		System.out.println(i+"-------------------------i");
		return i;
	}

	/* (non-Javadoc)
	 * 
	 */
	@Override
	public List<ProvideGoodsYShang> getProviderListByPage(Map parMap) {
		return sqlMapClient.queryForList("getProviderListByPage", parMap);
	}

	

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao#updateGoodsYShangByList(java.util.Map)
	 */
	@Override
	public void updateGoodsYShangByList(Map updateMap) {
		sqlMapClient.update("updateGoodsYShangByList",updateMap);
		
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao#selectYShangPage()
	 */
	@Override
	public ProviderYShangPage selectYShangPage() {
		ProviderYShangPage er = (ProviderYShangPage) this.sqlMapClient.queryForObject("selectPage");
		return er;
		
	}

	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangUpdateDao#updateYShangPage(java.util.Map)
	 */
	@Override
	public void updateYShangPage(Map<String, Integer> map) {
		sqlMapClient.update("updatePage",map);
		
	}

}
