package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * StorageManager
 *
 * @version 3.2.0
 */
public interface StorageManager {
	// /**
	// *
	// * @param storage
	// */
	// public void addStorage(Storage storage);
	//
	// /**
	// *
	// * @param storage
	// */
	// public void editStorage(Storage storage);
	//
	// /**
	// *
	// * @param storageId
	// */
	// public void removeStorage(Long storageId);

	 /**
	 *
	 * @param storageId
	 * @return
	 */
	 public Storage getStorage(Long storageId);

	// /**
	// *
	// * @return
	// */
	// public List<Storage> getStorages();

	 /**
	 * ����ѯ
	 *
	 * @param condition
	 * @param page
	 * @param isGroup
	 * �Ƿ����ͳ��
	 * @return
	 */
	 public QueryPage getStoragesByCondition(StorageQuery storageQuery, int currentPage, int pageSize, boolean isGroup, boolean isPage);

	 /**
	 *
	 * @param condition
	 * @return
	 */
	 public List<Storage> getStorageByIds(Map<String, Object> condition);

	 /**
	 *
	 * @param condition
	 * @return
	 */
	 public int getStoragesCountWithMove(MoveStorageQuery moveStorageQuery);

	 /**
	 *
	 * @param condition
	 * @param page
	 * @author zhangwy
	 * @return
	 */
	 public QueryPage getStoragesWithMove(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize);

	 /**
	 *
	 * @param condition
	 * @param page
	 * @return
	 */
	 public QueryPage getStoragesWithMoveTwo(MoveStorageQuery moveStorageQuery, int currPage, int pageSize);

	 /**
	 *
	 * @param condition
	 * @param page
	 * @return
	 */
	 public QueryPage getStoragesWithAll(Storage storage, int currPage, int pageSize);

	 /**
	 *
	 * @param condition
	 * @param page
	 * @return
	 */
	 public QueryPage getStoragesWithMoveAllTwo(Storage storage, int currPage, int pageSize);

	/**
	 * ��ò�Ʒ�����
	 *
	 * @param goodsInstanceId
	 *            ��ƷID
	 * @param showAllStorageNum
	 *            �Ƿ���ʾ���Ϊ0����Ϣ
	 * @return
	 */
	public List<Storage> getStorageWithTrade(Long goodsInstanceId, boolean showAllStorageNum);

	// /**
	// * ���¿��ÿ������
	// *
	// * @param storage
	// * Storage
	// * @return int
	// * @author chenyan 2009/07/25
	// */
	// int editStorageExistNum(Storage storage);
	//
	// /**
	// * ��ÿ���Ʒ����
	// *
	// * @param parameterMap
	// * @return
	// */
	// public int sumStorageByParameterMap(Map parameterMap);

	/**
	 * ��������MAP�޸Ŀ��
	 *
	 * @param parameterMap
	 * @return @
	 */
	int updateStorageStoNumByMap(Map parameterMap);

	/**
	 * ��������MAP��ѯһ������¼
	 *
	 * @param parameterMap
	 * @return @
	 */
	public Storage getStorageByMap(Map parameterMap);

	/**
	 * ��������MAP��ѯ��������¼����
	 *
	 * @param parameterMap
	 * @return @
	 */
	public List<Storage> getStorageListsByMap(Map parameterMap);


	 /**
	 * �����ϣ���Ӧ��λ��InstanceId
	 *
	 * @param locId
	 * @return
	 */
	 public List<Long> getGoodsInstanceIdsByLocId(Long locId);

	/**
	 * ���ݳ����굥IDȡ�÷���õĿ���б�
	 *
	 * @param outDetailId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/12
	 */
	List<OutDepositoryStorage> listOutDetailForDisByDetailId(Long outDetailId);

    /**
    * ��Ŀ���ɱ�����
    *
    * @param depId
    * @return
    */
    public List<Storage> sumStorageCostByDepid(Long depId);
	//
	// /**
	// * ���ڷ���ʱ�����ݳ�ʼ����
	// *
	// * @return List
	// * @author chenyan 2009/08/30
	// */
	// List<Storage> getDataForStorageOnce();
	//
	// /**
	// * ���ݲ�Ʒʵ��IDȡ�ÿ�������
	// *
	// * @param goodsInstanceId
	// * Long
	// * @return Storage
	// * @author chenyan 2009/08/30
	// */
	// Storage getStorageByGoodsInstanceId(Long goodsInstanceId);

	 /**
	 * �����ѯ
	 *
	 * @param condition
	 * @param page
	 * @return List
	 * @author zhangwy 2009/09/10
	 */
	 public QueryPage searchZeroStock(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage);

	 /**
	 * ����������ѯ����ļ�¼����
	 *
	 * @param parMap
	 * @return
	 */
	 public List<Storage> getZeroStorageByParameterMap(Map<String, String> parMap);
	//
	 /**
	 * ����˻���ѯ����
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	 public List<Storage> getRefundStoragesByMap(Map<String, String> parMap);
	//
	// /**
	// * ����˻���ѯ���ϸ���
	// *
	// * @param parMap
	// * @return
	// */
	// public int getRefundStoragesCountByMap(Map<String, String> parMap);
	//
	// /**
	// * �����ѯ
	// *
	// * @param condition
	// * @param page
	// * @return
	// */
	// public List<Storage> getStockAgeByCondition(Map<String, String> condition, Page page);

	 /**
	 * �������������ͳ��
	 *
	 * @param condition
	 * @return
	 */
	 public Storage getStorageAmountByCondition(StorageQuery storageQuery);

	// /**
	// * ��ȡ�ɱ���
	// *
	// * @param Long
	// * id
	// * @return
	// */
	// public Double getStoragePrice(Long id);

	/**
	 * ����goodsInstanceIdȡʣ���棨ʵ�ʡ����ã�
	 *
	 * @param goodsInstanceId
	 * @param depFirstId
	 * @param type
	 *            (exist:���ã�storage:ʵ��)
	 * @return
	 */
	Long sumStorageByGoodsInstanceId(long goodsInstanceId, Long depFirstId, String type);

	 Storage sumStorageResultForCheck(Map parameterMap);

	/**
	 * ����жϴ���
	 *
	 * @param parameterMap
	 * @author zhangwy
	 * @return
	 */
	public int getStorageNumBySend(Map parameterMap);

	 /**
	 * ����˻�����ɹ�
	 *
	 * @author zhangwy
	 * @return
	 */
	 public Long refundApplyStorages(List<StorageRefundApply> storageRefundApplyList, String disposeUserName);

	 public List<Storage> getStoragesByCondition(Map parMap);

	 public long getSumGoodsNumberByGoodsId(Long goodsId);
	 
	 /**
	  * ���ǰ���п��ÿ�λ�Ŀ������
	  * @param parMap
	  * @return
	  */
	 public Long getLeftStorageNumWithLoc(Map parMap);
}
