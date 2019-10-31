package com.huaixuan.network.biz.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationInGbDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationInGb;
import com.huaixuan.network.biz.service.storage.ProdRelationInGbManager;

@Service("prodRelationInGbManager")
public class ProdRelationInGbManagerImpl implements ProdRelationInGbManager {

	@Autowired
	private ProdRelationInGbDao prodRelationInGbDao;

	@Override
	public void addProdRelationInGb(ProdRelationInGb prodRelationInGb) {
		prodRelationInGbDao.addProdRelationInGb(prodRelationInGb);
	}

}
