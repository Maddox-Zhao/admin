package com.huaixuan.network.biz.dao.trade;

import java.util.List;

import com.huaixuan.network.biz.domain.trade.PackageTrade;

public interface PackageDao {
	// /* @interface model: Package */
	// Long addPackage(PackageTrade packageInstanse);
	//
	// /* @interface model: Package */
	// void editPackage(PackageTrade packageInstanse) throws Exception;
	//
	// /* @interface model: Package */
	// void removePackage(Long packageId) throws Exception;
	//
	// /* @interface model: Package,Package */
	// PackageTrade getPackage(Long packageId) throws Exception;
	//
	// /* @interface model: Package,Package */
	// List<PackageTrade> getPackages() throws Exception;

	/**
	 * ���ݽ��׺ŵõ��˽����е��ײ�
	 * 
	 * @param tid
	 * @return
	 */
	List<PackageTrade> getPackagesByTid(String tid);
}
