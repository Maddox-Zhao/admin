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
	 * ���ݽ��׺ŵõ��˽����е��ײ�
	 * 
	 * @param tid
	 * @return
	 */
	public List<PackageTrade> getPackagesByTid(String tid);
}
