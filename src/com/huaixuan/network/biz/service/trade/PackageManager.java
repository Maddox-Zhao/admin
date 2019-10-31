package com.huaixuan.network.biz.service.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.PackageTrade;

public interface PackageManager {
	// /* @interface model: Package */
	// public void addPackage(PackageTrade packageInstanse);
	//
	// /* @interface model: Package */
	// public void editPackage(PackageTrade packageInstanse);
	//
	// /* @interface model: Package */
	// public void removePackage(Long packageId);
	//
	// /* @interface model: Package,Package */
	// public PackageTrade getPackage(Long packageId);
	//
	// /* @interface model: Package,Package */
	// public List<PackageTrade> getPackages();

	/**
	 * 根据交易号得到此交易中的套餐
	 * 
	 * @param tid
	 * @return
	 */
	public List<PackageTrade> getPackagesByTid(String tid);
}
