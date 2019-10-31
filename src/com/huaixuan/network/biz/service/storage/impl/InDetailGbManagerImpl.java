package com.huaixuan.network.biz.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.InDetailGbDao;
import com.huaixuan.network.biz.domain.storage.InDetailGb;
import com.huaixuan.network.biz.service.storage.InDetailGbManager;


@Service("inDetailGbManager")
public class InDetailGbManagerImpl implements InDetailGbManager {

	@Autowired
    private InDetailGbDao inDetailGbDao;

	@Override
	public long addInDetailGb(InDetailGb inDetailGb) {
		return inDetailGbDao.addInDetailGb(inDetailGb);
	}

}
