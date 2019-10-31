package com.huaixuan.network.biz.dao.shop.ibatis;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.PayaccountDao;
import com.huaixuan.network.biz.domain.shop.Payaccount;
import com.huaixuan.network.biz.domain.shop.Proxyagency;

@Service("PayaccountDao")
public class PayaccountDaoiBatis implements PayaccountDao{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	@Override
	public List<Payaccount> getBankbyBankName(){
		return sqlMapClient.queryForList("getBankbyBankName");
	}

	@Override
	public List<Proxyagency> getCompany() {
		return sqlMapClient.queryForList("getCompany");
	}

}
