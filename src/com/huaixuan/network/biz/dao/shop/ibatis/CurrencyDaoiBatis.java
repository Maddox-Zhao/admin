package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.CurrencyDao;
import com.huaixuan.network.biz.domain.shop.Currency;

@Service("currencyDao")
public class CurrencyDaoiBatis implements CurrencyDao{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public List<Currency> getCurrenctByname() {
		return sqlMapClient.queryForList("getCurrenct");
	}

}
