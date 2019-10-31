package com.huaixuan.network.biz.dao.storage.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.OutDetailGbDao;
import com.huaixuan.network.biz.domain.storage.OutDetailGb;

@Repository("outDetailGbDao")
public class OutDetailGbDaoiBatis implements OutDetailGbDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addOutDetailGb(OutDetailGb outDetailGb) {
		sqlMapClient.insert("addOutDetailGb", outDetailGb);
	}

}
