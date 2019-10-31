package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 入库单基本信息(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface InDepositoryManager {
	/**
	 * 新增入库单基本信息
	 *
	 * @param inDepository
	 * @return
	 */
	// public long addInDepository(InDepository inDepository);

	/**
	 * 编辑入库单基本信息
	 *
	 * @param inDepository
	 */
	 public void editInDepository(InDepository inDepository);

	/**
	 * 删除入库单基本信息
	 *
	 * @param inDepositoryId
	 */
	// public void removeInDepository(Long inDepositoryId);

	/**
	 * 根据ID得到入库单基本信息
	 *
	 * @param inDepositoryId
	 * @return
	 */
	public InDepository getInDepository(Long inDepositoryId);

	/**
	 * 根据条件查询入库单基本信息对象
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public InDepository getInDepository(Map map) throws Exception;

	/**
	 * 查询全部入库单基本信息列表
	 *
	 * @return
	 */
	// public List<InDepository> getInDepositorys();

	/**
	 * 检索待入库的入库单总数
	 *
	 * @return int
	 * @author chenyan 2009/07/21
	 */
	// int getInDepositoryListsCount(Map<String, String> parMap);

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
	// int updateInDepositoryStatusById(Long id, String status, Date gmtInDep);

	/**
	 * 完成入库的事务操作
	 *
	 * @param map
	 *            Map
	 * @return Boolean 操作成功标识
	 * @author chenyan 2009/07/25
	 * @throws Exception
	 */
	Boolean addStorageOpt(Map<String, Object> map) throws Exception;

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
	// int getFinishedInDepByRelNum(String relationNum);

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
	 * 获取所有入库详单信息
	 *
	 * @return list
	 * @author zhangwy 2009/09/10
	 */
	List<InDepository> getInDepositorysWithDetail(Map parmap);

	/**
	 * 获取所有入库详单总数
	 *
	 * @param parmap
	 * @return int
	 * @author zhangwy 2009/09/16
	 */
	// int getInDepositorysWithDetailCount(Map parmap);

	/**
	 * 获取所有入库类型
	 *
	 * @return list
	 * @author zhangwy 2009/09/18
	 */
	// List<String> getAlltypes();

	/**
	 * 入库单Id查询供应商名称
	 *
	 * @param inDepositoryIds
	 * @return
	 */
	Map getSupplierNameById(List<String> inDepositoryIds);
}
