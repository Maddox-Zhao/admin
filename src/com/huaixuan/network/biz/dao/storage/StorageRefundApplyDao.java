package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.query.QueryPage;

public interface StorageRefundApplyDao {

	@SuppressWarnings("unchecked")
	QueryPage getStorageRefundApplyListByCondition(Map parMap, int currentPage, int pageSize);

	void addStorageRefundApply(StorageRefundApply storageRefundApply);

	int getStorageRefundApplyCountByRelationNum(String relationNum);

	List<StorageRefundApply> getStorageRefundApplyDetail(String relationNum);

	StorageRefundApply getStorageRefundApplyById(Long id);

	void updateStorageRefundApply(StorageRefundApply storageRefundApply);

	/**
	 * 根据库存ID查找是否存在没有处理完成的单�
	 * @author 方青
	 * @param storageId
	 * @return
	 */
	public int getStorageRefundApplyCountByStorageId(String[] storageIds);
}
