package com.huaixuan.network.biz.dao.storage.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationInGbDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationInGb;

@Service("prodRelationInGbDao")
public class ProdRelationInGbDaoiBatis implements ProdRelationInGbDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addProdRelationInGb(ProdRelationInGb prodRelationInGb) {
		sqlMapClient.insert("addProdRelationInGb", prodRelationInGb);
	}

}
