package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.PackageDao;
import com.huaixuan.network.biz.domain.trade.PackageTrade;
import com.huaixuan.network.biz.service.trade.PackageManager;

@Service("packageManager")
public class PackageManagerImpl implements PackageManager {
	@Autowired
	public PackageDao packageDao;

	//
	// public void setPackageDao(PackageDao packageDao) {
	// this.packageDao = packageDao;
	// }
	//
	// public PackageDao getPackageDao() {
	// return this.packageDao;
	// }
	//
	/* @model: */
	// public void addPackage(PackageTrade packageDao) {
	// log.info("PackageManagerImpl.addPackage method");
	// try {
	// this.packageDao.addPackage(packageDao);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void editPackage(PackageTrade packageInstanse) {
	// log.info("PackageManagerImpl.editPackage method");
	// try {
	// this.packageDao.editPackage(packageInstanse);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void removePackage(Long packageId) {
	// log.info("PackageManagerImpl.removePackage method");
	// try {
	// this.packageDao.removePackage(packageId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public PackageTrade getPackage(Long packageId) {
	// log.info("PackageManagerImpl.getPackage method");
	// try {
	// return this.packageDao.getPackage(packageId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	/* @model: */
	// public List<PackageTrade> getPackages() {
	// log.info("PackageManagerImpl.getPackages method");
	// try {
	// return this.packageDao.getPackages();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	/**
	 * 根据交易号得到此交易中的套餐
	 * 
	 * @param tid
	 * @return
	 * @see com.hundsun.bible.facade.trade.PackageManager#getPackagesByTid(java.lang.String)
	 */
	public List<PackageTrade> getPackagesByTid(String tid) {
		return packageDao.getPackagesByTid(tid);
	}
}
