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
	 * 库存查询
	 *
	 * @param condition
	 * @param page
	 * @param isGroup
	 * 是否分组统计
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
	 * 获得产品库存数
	 *
	 * @param goodsInstanceId
	 *            产品ID
	 * @param showAllStorageNum
	 *            是否显示库存为0的信息
	 * @return
	 */
	public List<Storage> getStorageWithTrade(Long goodsInstanceId, boolean showAllStorageNum);

	// /**
	// * 更新可用库存数量
	// *
	// * @param storage
	// * Storage
	// * @return int
	// * @author chenyan 2009/07/25
	// */
	// int editStorageExistNum(Storage storage);
	//
	// /**
	// * 获得库存产品数量
	// *
	// * @param parameterMap
	// * @return
	// */
	// public int sumStorageByParameterMap(Map parameterMap);

	/**
	 * 根据条件MAP修改库存
	 *
	 * @param parameterMap
	 * @return @
	 */
	int updateStorageStoNumByMap(Map parameterMap);

	/**
	 * 根据条件MAP查询一条库存记录
	 *
	 * @param parameterMap
	 * @return @
	 */
	public Storage getStorageByMap(Map parameterMap);

	/**
	 * 根据条件MAP查询多条库存记录集合
	 *
	 * @param parameterMap
	 * @return @
	 */
	public List<Storage> getStorageListsByMap(Map parameterMap);


	 /**
	 * 库存表上，对应库位的InstanceId
	 *
	 * @param locId
	 * @return
	 */
	 public List<Long> getGoodsInstanceIdsByLocId(Long locId);

	/**
	 * 根据出库详单ID取得分配好的库存列表
	 *
	 * @param outDetailId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/12
	 */
	List<OutDepositoryStorage> listOutDetailForDisByDetailId(Long outDetailId);

    /**
    * 类目库存成本汇总
    *
    * @param depId
    * @return
    */
    public List<Storage> sumStorageCostByDepid(Long depId);
	//
	// /**
	// * 用于发布时候数据初始化用
	// *
	// * @return List
	// * @author chenyan 2009/08/30
	// */
	// List<Storage> getDataForStorageOnce();
	//
	// /**
	// * 根据产品实例ID取得库存表数据
	// *
	// * @param goodsInstanceId
	// * Long
	// * @return Storage
	// * @author chenyan 2009/08/30
	// */
	// Storage getStorageByGoodsInstanceId(Long goodsInstanceId);

	 /**
	 * 零库存查询
	 *
	 * @param condition
	 * @param page
	 * @return List
	 * @author zhangwy 2009/09/10
	 */
	 public QueryPage searchZeroStock(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage);

	 /**
	 * 根据条件查询零库存的记录集合
	 *
	 * @param parMap
	 * @return
	 */
	 public List<Storage> getZeroStorageByParameterMap(Map<String, String> parMap);
	//
	 /**
	 * 库存退货查询集合
	 *
	 * @param parMap
	 * @param page
	 * @return
	 */
	 public List<Storage> getRefundStoragesByMap(Map<String, String> parMap);
	//
	// /**
	// * 库存退货查询集合个数
	// *
	// * @param parMap
	// * @return
	// */
	// public int getRefundStoragesCountByMap(Map<String, String> parMap);
	//
	// /**
	// * 库龄查询
	// *
	// * @param condition
	// * @param page
	// * @return
	// */
	// public List<Storage> getStockAgeByCondition(Map<String, String> condition, Page page);

	 /**
	 * 库存条数和数量统计
	 *
	 * @param condition
	 * @return
	 */
	 public Storage getStorageAmountByCondition(StorageQuery storageQuery);

	// /**
	// * 获取成本价
	// *
	// * @param Long
	// * id
	// * @return
	// */
	// public Double getStoragePrice(Long id);

	/**
	 * 根据goodsInstanceId取剩余库存（实际、可用）
	 *
	 * @param goodsInstanceId
	 * @param depFirstId
	 * @param type
	 *            (exist:可用；storage:实际)
	 * @return
	 */
	Long sumStorageByGoodsInstanceId(long goodsInstanceId, Long depFirstId, String type);

	 Storage sumStorageResultForCheck(Map parameterMap);

	/**
	 * 配货判断处理
	 *
	 * @param parameterMap
	 * @author zhangwy
	 * @return
	 */
	public int getStorageNumBySend(Map parameterMap);

	 /**
	 * 库存退货申请成功
	 *
	 * @author zhangwy
	 * @return
	 */
	 public Long refundApplyStorages(List<StorageRefundApply> storageRefundApplyList, String disposeUserName);

	 public List<Storage> getStoragesByCondition(Map parMap);

	 public long getSumGoodsNumberByGoodsId(Long goodsId);
	 
	 /**
	  * 配货前所有可用库位的库存数量
	  * @param parMap
	  * @return
	  */
	 public Long getLeftStorageNumWithLoc(Map parMap);
}
