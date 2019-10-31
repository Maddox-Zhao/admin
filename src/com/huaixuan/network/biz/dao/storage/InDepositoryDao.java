package com.huaixuan.network.biz.dao.storage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface InDepositoryDao {
	/* @interface model: InDepository */
	Long addInDepository(InDepository inDepository);

	/* @interface model: InDepository */
	void editInDepository(InDepository inDepository);

	/* @interface model: InDepository */
	void removeInDepository(Long inDepositoryId);

	/* @interface model: InDepository,InDepository */
	InDepository getInDepository(Long inDepositoryId);

	/* @interface model: InDepository,InDepository */
	List<InDepository> getInDepository(Map map);

	/* @interface model: InDepository,InDepository */
	List<InDepository> getInDepositorys();

	/**
	 * 检索待入库的入库单总数
	 * 
	 * @param parMap
	 *            Map
	 * @return int
	 * @author chenyan 2009/07/21
	 */
	int getInDepositoryListsCount(Map<String, String> parMap);

	/**
	 * 检索待入库的入库单列表信息
	 * 
	 * @param parMap
	 *            Map
	 * @param page
	 *            Page
	 * @return List
	 * @author chenyan 2009/07/21
	 */
	QueryPage getInDepositoryLists(InDepositoryQuery query, int currPage, int pageSize, boolean isPage);

	/**
	 * 根据ID更新状态
	 * 
	 * @param id
	 *            Long
	 * @param status
	 *            String
	 * @param gmtInDep
	 *            Date
	 * @return int
	 * @author chenyan 2009/07/25
	 */
	int updateInDepositoryStatusById(Long id, String status, Date gmtInDep);

	/**
	 * 根据ID取得批次
	 * 
	 * @param id
	 *            Long
	 * @return String
	 * @author chenyan 2009/07/27
	 */
	String getBatchNumById(Long id);

	/**
	 * 根据关联单据号取得未完成的入库单据
	 * 
	 * @param relationNum
	 *            String
	 * @return int
	 * @author chenyan 2009/08/05
	 */
	int getUnFinishedInDepByRelNum(String relationNum);

	/**
	 * 根据关联单据号取得完成的入库单据数量
	 * 
	 * @param relationNum
	 *            String
	 * @return int
	 * @author fanyj 2010/05/25
	 */
	int getFinishedInDepByRelNum(String relationNum);

	/**
	 * 根据入库详单ID检索入库单状态
	 * 
	 * @param inDetailId
	 *            Long
	 * @return String
	 * @author chenyan 2009/08/11
	 */
	String getInDepositoryStatusByDetailId(Long inDetailId);

	/**
	 * 获取所有入库详单
	 * 
	 * @return list
	 * @author zhangwy 2009/09/10
	 */
	List<InDepository> getInDepositorysWithDetail(Map parmap);

	/**
	 * 获取所有入库详单总量
	 * 
	 * @param parmap
	 * @return int
	 * @author zhangwy 2009/9/16
	 */
	int getInDepositorysWithDetailCount(Map parmap);

	/**
	 * 获取所有入库类型
	 * 
	 * @return list
	 * @author zhangwy 2009/09/18
	 */
	List<String> getAlltypes();

	/**
	 * 根据关联单号获取入库单信息
	 * 
	 * @param relationNum
	 * @author zhangwy
	 * @return
	 */
	InDepository getInDepByRelNum(String relationNum);

	/**
	 * 根据入库单Id查询供应商名称
	 * 
	 * @param inDepositoryIds
	 * @return
	 */
	Map getSupplierNameById(List<String> inDepositoryIds);
}
