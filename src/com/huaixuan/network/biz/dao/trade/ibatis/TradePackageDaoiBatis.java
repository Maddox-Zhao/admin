package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.TradePackageDao;
import com.huaixuan.network.biz.domain.trade.TradePackage;

@Service("tradePackageDao")
public class TradePackageDaoiBatis implements TradePackageDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	public void addTradePackage(TradePackage tradePackage) {
		sqlMapClientTemplate.insert("addTradePackage", tradePackage);
	}

	public void editTradePackage(TradePackage tradePackage) {
		sqlMapClientTemplate.update("editTradePackage", tradePackage);
	}

	public void removeTradePackage(Long tradePackageId) {
		sqlMapClientTemplate.delete("removeTradePackage", tradePackageId);
	}

	public TradePackage getTradePackage(Long tradePackageId) {
		return (TradePackage) sqlMapClientTemplate.queryForObject("getTradePackage", tradePackageId);
	}

	public List<TradePackage> getTradePackages() {
		return sqlMapClientTemplate.queryForList("getTradePackages", null);
	}

	public List<TradePackage> getTradePackagesByMergeTid(String mergeTid) {
		return sqlMapClientTemplate.queryForList("getTradePackagesByMergeTid", mergeTid);
	}

	@SuppressWarnings("unchecked")
	public List<String> getTidByMergeTid(String mergeTid) {
		List<TradePackage> tradePackageList = sqlMapClientTemplate.queryForList("getTradePackagesByMergeTid", mergeTid);
		List<String> tidList = new ArrayList<String>();
		for (TradePackage obj : tradePackageList) {
			tidList.add(obj.getTid());
		}
		return tidList;
	}
}
