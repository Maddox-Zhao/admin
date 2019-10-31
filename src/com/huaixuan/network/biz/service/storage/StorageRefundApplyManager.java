package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.StorageRefundApplyQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface StorageRefundApplyManager{

	/**
	 * 根据条件获取库存退货申请单
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	public QueryPage getStorageRefundApplyListByCondition(StorageRefundApplyQuery storageRefundApplyQuery, int currentPage, int pageSize);

	/**
	 * 添加到库存退货单申请
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	public String addStorageRefundApply(Map<String, Object> parMap);

	/**
	 * 根据申请单关联号找申请单
	 * @author zhangwy
	 * @param relationNum
	 * @return
	 */
	public int getStorageRefundApplyCountByRelationNum(String relationNum);


	/**
	 * 根据库存ID查找是否存在没有处理完成的单子
	 * @author
	 * @param storageId
	 * @return
	 */
	public int getStorageRefundApplyCountByStorageId(String storageId);

	/**
	 * 根据申请单关联号找申请列表
	 * @author zhangwy
	 * @param relationNum
	 * @return
	 */
	public List<StorageRefundApply> getStorageRefundApplyDetail(String relationNum);

	/**
	 * 根据ID取记录
	 * @param id
	 * @return
	 */
	public StorageRefundApply getStorageRefundApplyById(Long id);

	/**
	 * 更新记录
	 * @param storageRefundApply
	 */
	public void updateStorageRefundApply(StorageRefundApply storageRefundApply);

	/**
	 * 重新提交保存
	 * @param parMap
	 * @return
	 */
	public String modifyRefundApply(Map parMap);
}
