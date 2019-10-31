package com.huaixuan.network.biz.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.OutDetailGbDao;
import com.huaixuan.network.biz.domain.storage.OutDetailGb;
import com.huaixuan.network.biz.service.storage.OutDetailGbManager;

@Service("outDetailGbManager")
public class OutDetailGbManagerImpl implements OutDetailGbManager {

	@Autowired
    private OutDetailGbDao outDetailGbDao;

	@Override
	public void addOutDetailGb(OutDetailGb outDetailGb) {
		outDetailGbDao.addOutDetailGb(outDetailGb);
	}

}
