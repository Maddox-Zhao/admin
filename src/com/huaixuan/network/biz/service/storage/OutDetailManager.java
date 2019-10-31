package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 出库单详情信息(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface OutDetailManager {
	/**
	 * 新增出库单详情信息
	 *
	 * @param outDetail
	 * @return
	 */
	public Long addOutDetail(OutDetail outDetail);

	/**
	 * 编辑出库单详情信息
	 *
	 * @param outDetail
	 */
	public void editOutDetail(OutDetail outDetail);

	/**
	 * 删除出库单详情信息
	 *
	 * @param outDetailId
	 */
	public void removeOutDetail(Long outDetailId);

	/**
	 * 根据ID得到出库单详情信息
	 *
	 * @param outDetailId
	 * @return
	 */
	public OutDetail getOutDetail(Long outDetailId);

	/**
	 * 查询所有出库单详情信息
	 *
	 * @return
	 */
	public List<OutDetail> getOutDetails();

	/**
	 * 根据出库单ID取得对应产品详情信息
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @parm relationNum String
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	List<OutDetailGoods> getOutDetailGoodsLists(Long id);

	/**
	 * 根据出库单详情ID取得基本信息
	 *
	 * @param outDetailId
	 *            Long
	 * @param type
	 *            String
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailBaseInfo(Long outDetailId, String type);

	/**
	 * 根据商品实例ID和供应商查询库存数据
	 *
	 * @param mapSearch
	 *            map
	 * @return List
	 * @author chenyan 2009/07/29 modified by chenyan 2010/06/13
	 */
	List<OutDepositoryStorage> getOutStorageList(Map mapSearch);

	/**
	 * 更新商品出库对应表数据以及出库详单状态
	 *
	 * @param map
	 *            Map
	 * @param type
	 *            String
	 * @return Boolean
	 * @author chenyan 2009/07/29
	 */
	@SuppressWarnings("unchecked")
	Boolean outDepositoryOpt(Map map, String type);

	// added by chenhang 自动入库
	Boolean outDepositoryOptAuto(Map map, String type);

	/**
	 * 根据出库单主表ID取得未完成分配的产品信息
	 *
	 * @param outDepId
	 *            Long
	 * @param status
	 *            String
	 * @return OutDetailGoods
	 * @author chenyan 2009/07/29 modified by chenyan 2009/09/06
	 */
	List<OutDetailGoods> listOutDetailNotFinish(Long outDepId, String status);

	GatherOutDepository gatherFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery);

	QueryPage gatherFinanceOutDepositoryLists(
			FinanceDepositoryQuery financeDepositoryQuery, int currPage,
			int pageSize);

	/**
	 * 财务管理暂估出库单管理统计数量
	 *
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	GatherOutDepository estimateFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery);

	/**
	 * 财务管理暂估出库单管理统计列表
	 *
	 * @author zhangwy
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage estimateFinanceOutDepositoryLists(
			FinanceDepositoryQuery financeDepositoryQuery, int currPage,
			int pageSize);

	/**
	 * 根据主表Id获取全部的详细列表
	 *
	 * @author zhangwy
	 * @param outDepositoryId
	 * @return
	 */
	List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId);
}
