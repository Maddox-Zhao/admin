/**
 * 
 */
package com.huaixuan.network.biz.dao.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;

/**
 * @author TT
 * 
 */
@Repository("ProvideGoodsImgeDao")
public class ProvideGoodsImgeDaoImpl implements ProvideGoodsImgeDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao#insertGoodsImge(java.util.List)
	 */
	@Override
	public void insertGoodsImge(List<ProvideGoodsImge> pgiList) {
		sqlMapClient.insert("insertGoodsImge",pgiList);
		
	}
	@Override
	public List<ProvideGoodsImge> getProvideImgDao(Map parMap) {
		//遍历map中的list
		return sqlMapClient.queryForList("getProvideImgDao", parMap);
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao#getProvideOneGoodsImage(java.lang.String)
	 */
	@Override
	public List<ProvideGoodsImge> getProvideOneGoodsImage(String prodid) {
		return sqlMapClient.queryForList("selectOneGoodsImage", prodid);
	}
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.dao.provider.ProvideGoodsImgeDao#updatGoodsImage(com.huaixuan.network.biz.domain.provider.ProvideGoodsImge)
	 */
	@Override
	public void updatGoodsImage(ProvideGoodsImge pgi) {
		sqlMapClient.update("updatGoodsImage", pgi);
		
	}

}
