package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.StorageRefundApplyQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface StorageRefundApplyManager{

	/**
	 * ����������ȡ����˻����뵥
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	public QueryPage getStorageRefundApplyListByCondition(StorageRefundApplyQuery storageRefundApplyQuery, int currentPage, int pageSize);

	/**
	 * ��ӵ�����˻������믷
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	public String addStorageRefundApply(Map<String, Object> parMap);

	/**
	 * �������뵥�����������뵥
	 * @author zhangwy
	 * @param relationNum
	 * @return
	 */
	public int getStorageRefundApplyCountByRelationNum(String relationNum);


	/**
	 * ���ݿ��ID�����Ƿ����û�д�����ɵĵ���
	 * @author
	 * @param storageId
	 * @return
	 */
	public int getStorageRefundApplyCountByStorageId(String storageId);

	/**
	 * �������뵥�������������б�
	 * @author zhangwy
	 * @param relationNum
	 * @return
	 */
	public List<StorageRefundApply> getStorageRefundApplyDetail(String relationNum);

	/**
	 * ����IDȡ��¼
	 * @param id
	 * @return
	 */
	public StorageRefundApply getStorageRefundApplyById(Long id);

	/**
	 * ���¼�¼
	 * @param storageRefundApply
	 */
	public void updateStorageRefundApply(StorageRefundApply storageRefundApply);

	/**
	 * �����ύ����
	 * @param parMap
	 * @return
	 */
	public String modifyRefundApply(Map parMap);
}
