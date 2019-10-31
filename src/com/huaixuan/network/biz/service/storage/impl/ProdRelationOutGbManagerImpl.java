package com.huaixuan.network.biz.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.ProdRelationOutGbDao;
import com.huaixuan.network.biz.domain.storage.ProdRelationOutGb;
import com.huaixuan.network.biz.service.storage.ProdRelationOutGbManager;

@Service("prodRelationOutGbManager")
public class ProdRelationOutGbManagerImpl implements ProdRelationOutGbManager {

	@Autowired
	private ProdRelationOutGbDao prodRelationOutGbDao;

	@Override
	public void addProdRelationOutGb(ProdRelationOutGb prodRelationOutGb) {
		prodRelationOutGbDao.addProdRelationOutGb(prodRelationOutGb);
	}

}
