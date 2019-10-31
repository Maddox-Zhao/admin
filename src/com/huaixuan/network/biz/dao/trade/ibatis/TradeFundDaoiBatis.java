package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.huaixuan.network.biz.dao.trade.TradeFundDao;
import com.huaixuan.network.biz.domain.trade.TradeFund;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class TradeFundDaoiBatis implements TradeFundDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public void addTradeFund(TradeFund tradeFund) {
		sqlMapClientTemplate.insert("addTradeFund", tradeFund);
	}

	public void editTradeFund(TradeFund tradeFund) {
		sqlMapClientTemplate.update("editTradeFund", tradeFund);
	}

	public void removeTradeFund(Long tradeFundId) {
		sqlMapClientTemplate.delete("removeTradeFund", tradeFundId);
	}

	public TradeFund getTradeFund(Long tradeFundId) {
		return (TradeFund) sqlMapClientTemplate.queryForObject("getTradeFund", tradeFundId);
	}

	public List<TradeFund> getTradeFunds() {
		return sqlMapClientTemplate.queryForList("getTradeFunds", null);
	}
}
