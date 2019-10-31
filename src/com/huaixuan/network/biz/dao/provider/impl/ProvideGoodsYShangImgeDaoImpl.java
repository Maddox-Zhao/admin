/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideGoodsYShangImgeDao;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;

/**
 * @author TT
 * 
 */
@Repository("ProvideGoodsYShangImgeDao")
public class ProvideGoodsYShangImgeDaoImpl implements ProvideGoodsYShangImgeDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * 
	 */
	@Override
	public void insertGoodsYShangImge(List<ProvideGoodsYShangImge> pgList) {
		sqlMapClient.insert("insertGoodsYShangImge",pgList);

	}

	/* (non-Javadoc)
	 *  
	 */
	@Override
	public List<ProvideGoodsYShangImge> getProvideYShangImgDao(Map parMap) {
		//遍历map中的list
				return sqlMapClient.queryForList("getProvideImgDaoYShang", parMap);
	}

	/* (non-Javadoc)
	 * 
	 */
	@Override
	public List<ProvideGoodsYShangImge> getProvideOneGoodsYShangImage(
			String prodid) {
		return sqlMapClient.queryForList("selectOneGoodsImageYShang", prodid);
	}

	/* (non-Javadoc)
	 * 
	 */
	@Override
	public void updatGoodsYShangImage(ProvideGoodsYShangImge pg) {
		sqlMapClient.update("updatGoodsImageYShang", pg);

	}

}
