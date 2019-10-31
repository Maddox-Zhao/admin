package com.huaixuan.network.biz.dao.storage.ibatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.InDetailGbDao;
import com.huaixuan.network.biz.domain.storage.InDetailGb;

@Repository("inDetailGbDao")
public class InDetailGbDaoiBatis implements InDetailGbDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
	public long addInDetailGb(InDetailGb inDetailGb) {
		return (Long)sqlMapClient.insert("addInDetailGb", inDetailGb);
	}

}
