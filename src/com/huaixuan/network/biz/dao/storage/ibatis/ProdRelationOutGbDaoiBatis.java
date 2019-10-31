package com.huaixuan.network.biz.dao.storage.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationOutGbDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationOutGb;

@Service("prodRelationOutGbDao")
public class ProdRelationOutGbDaoiBatis implements ProdRelationOutGbDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addProdRelationOutGb(ProdRelationOutGb prodRelationOutGb) {
		sqlMapClient.insert("addProdRelationOutGb",prodRelationOutGb);
	}

}
