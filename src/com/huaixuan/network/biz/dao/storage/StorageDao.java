package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * StorageDao
 *
 * @version 3.2.0
 */
public interface StorageDao {
	Long addStorage(Storage storage);

	void editStorage(Storage storage);

	void removeStorage(Long storageId);

	Storage getStorage(Long storageId);

	List<Storage> getStorages();

	List<Storage> getStorageByParameterMap(Map parameterMap);

	List<Storage> getStorageAndCheckDetailByParameterMap(Map parameterMap);

	int sumStorageByParameterMap(Map parameterMap);

	QueryPage getStoragesByCondition(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage);

	QueryPage getStorageListByMap(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage);

	List<Storage> getStorageByIds(Map condition);

	/**
	 * 查询产品库存数
	 *
	 * @param goodsInstanceId
	 *            产品ID
	 * @param showAllStorageNum
	 *            是否显示库存为0的产品
	 * @return
	 */
	List<Storage> getStorageWithTrade(Long goodsInstanceId, boolean showAllStorageNum);

	/**
	 *
	 * @param condition
	 * @param page
	 * @author zhangwy
	 * @return
	 */
	QueryPage getStoragesWithMove(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize);

	QueryPage getStoragesWithAll(Storage storage, int currentPage, int pageSize);

	int getStoragesCountWithMove(Map condition);

	/**
	 *
	 * @param storage
	 *            Storage
	 * @return int
	 * @author  2009/07/25
	 */
	int editStorageExistNum(Storage storage);

	/**
	 * @param mapSearch
	 *            Map
	 * @return List
	 * @author  2009/07/29 modified by chenyan 2010/06/13
	 */
	List<OutDepositoryStorage> getOutStorageList(Map mapSearch);

	/**
	 * @param storageNum
	 *            Long
	 * @param id
	 *            Long
	 * @return int
	 * @author  2009/07/30
	 */
	int updateStorageStorageNumById(Long storageNum, Long id);

	/**
	 * @param parameterMap
	 * @return @
	 */
	int updateStorageStoNumByMap(Map parameterMap);

	/**
	 * @param parameterMap
	 * @return @
	 */
	Storage getStorageByMap(Map parameterMap);

	/**
	 * @param parameterMap
	 * @return @
	 */
	List<Storage> getStorageListsByMap(Map parameterMap);

	/**
	 * @param locId
	 * @return
	 */
	List<Long> getGoodsInstanceIdsByLocId(Long locId);

	/**
	 * @param outDetailId
	 *            Long
	 * @return List
	 * @author  2009/08/12
	 */
	List<OutDepositoryStorage> listOutDetailForDisByDetailId(Long outDetailId);

	/**
	 * @param depId
	 * @return
	 */
	List<Storage> sumStorageCostByDepid(Long depId);

	/**
	 * @return List
	 * @author  2009/08/30
	 */
	List<Storage> getDataForStorageOnce();

	/**
	 * @param goodsInstanceId
	 *            Long
	 * @return Storage
	 * @author  2009/08/30
	 */
	Storage getStorageByGoodsInstanceId(Long goodsInstanceId);

	/**
	 * @param condition
	 * @param page
	 * @return List
	 * @author  2009/09/10
	 */
	QueryPage searchZeroStock(Map condition, int currentPage, int pageSize, boolean isPage);

	/**
	 * @param parMap
	 * @return
	 */
	List<Storage> getZeroStorageByParameterMap(Map<String, String> parMap);

	/**
	 * @param parMap
	 * @return
	 */
	QueryPage getRefundStoragesByMap(Map parMap, int currentPage, int pageSize);;

	/**
	 * @param parMap
	 * @return
	 */
	int getRefundStoragesCountByMap(Map<String, String> parMap);;

	/**
	 * @param goodsInstanceId
	 * @return
	 */
	Long sumStorageByGoodsInstanceId(long goodsInstanceId, Long depFirstId);

	/**
	 * @param goodsInstanceId
	 * @return
	 */
	Long sumExistNumByGoodsInstanceId(long goodsInstanceId, Long depFirstId);

	/**
	 * @param condition
	 * @param page
	 * @return
	 */
	QueryPage getStockAgeByCondition(Map condition, int currentPage, int pageSize, boolean isPage);

	Double getStoragePriceById(long id);

	/**
	 * @param condition
	 * @return
	 */
	Storage getStorageAmountByCondition(Map condition);

	public List<Storage> getGoodsInstanceList();

	public long getStorageAgeCount();

	Storage sumStorageResultForCheck(Map parameterMap);

	/**
	 * @param parameterMap
	 * @author zhanwy
	 * @return
	 */
	int getStorageNumBySend(Map parameterMap);

	List<Storage> getRefundStoragesByMap(Map parMap);

	List<Storage> getStoragesByCondition(Map parMap);
	
	 /**
	  * 配货前所有可用库位的库存数量
	  * @param parMap
	  * @return
	  */
	Long getLeftStorageNumWithLoc(Map parMap);
}
