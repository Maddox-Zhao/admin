package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.PackageDao;
import com.huaixuan.network.biz.domain.trade.PackageTrade;

@Service("packageDao")
public class PackageDaoiBatis implements PackageDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	/* @model: */
	// public Long addPackage(PackageTrade packageInstanse) {
	// return (Long) sqlMapClientTemplate.insert("addPackage", packageInstanse);
	// }
	//
	/* @model: */
	// public void editPackage(PackageTrade packageInstanse) throws Exception {
	// sqlMapClientTemplate.update("editPackage", packageInstanse);
	// }
	//
	/* @model: */
	// public void removePackage(Long packageId) throws Exception {
	// sqlMapClientTemplate.delete("removePackage", packageId);
	// }
	//
	/* @model: */
	// public PackageTrade getPackage(Long packageId) throws Exception {
	// return (PackageTrade) sqlMapClientTemplate.queryForObject("getPackage", packageId);
	// }
	//
	/* @model: */
	// public List<PackageTrade> getPackages() throws Exception {
	// return sqlMapClientTemplate.queryForList("getPackages", null);
	// }

	@Override
	@SuppressWarnings("unchecked")
	public List<PackageTrade> getPackagesByTid(String tid) {
		return sqlMapClientTemplate.queryForList("getPackagesByTid", tid);
	}
}
