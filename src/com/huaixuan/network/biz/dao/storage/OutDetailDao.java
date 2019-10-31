package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface OutDetailDao {
	/* @interface model: IossOutDetail */
	Long addOutDetail(OutDetail outDetail) throws Exception;

	/* @interface model: IossOutDetail */
	void editOutDetail(OutDetail outDetail) throws Exception;

	/* @interface model: IossOutDetail */
	void removeOutDetail(Long outDetailId) throws Exception;

	/* @interface model: IossOutDetail,IossOutDetail */
	OutDetail getOutDetail(Long outDetailId) throws Exception;

	/* @interface model: IossOutDetail,IossOutDetail */
	List<OutDetail> getOutDetails() throws Exception;

	void addOutDetails(List<OutDetail> outDetails);

	/**
	 * 根据出库单ID取得对应产品详情信息
	 *
	 * @param id
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/28
	 */
	List<OutDetailGoods> getOutDetailGoodsLists(Long id);

	/**
	 * 根据出库单详情ID取得销售换货基本信息
	 *
	 * @param outDetailId
	 *            Long
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailSalesChangeBaseInfo(Long outDetailId);

	/**
	 * 根据出库单详情ID取得基本信息（销售换货除外）
	 *
	 * @param outDetailId
	 *            Long
	 * @param type
	 *            String
	 * @return OutDetailBaseInfo
	 * @author chenyan 2009/07/29
	 */
	OutDetailBaseInfo getOutDetailShoppingBaseInfo(Long outDetailId, String type);

	/**
	 * 根据ID更新出库详单状态
	 *
	 * @param outDetailId
	 *            Long
	 * @param status
	 *            String
	 * @author chenyan 2009/07/29
	 */
	void updateOutDetailStatusById(Long outDetailId, String status);

	/**
	 * 根据ID更新出库详单库存类型
	 *
	 * @param outDetailId
	 *            Long
	 * @param storType
	 *            String
	 * @author shengyong 2010/03/26
	 */
	public void updateOutDetailStorTypeById(Long outDetailId, String storType);

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

	/**
	 * 插入剩余库存
	 *
	 * @param outDetailGoodsInfo
	 * @throws Exception
	 */
	void editOutOutDetailGoods(OutDetailGoods outDetailGoodsInfo)
			throws Exception;

	GatherOutDepository gatherFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

	QueryPage gatherFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery,
			int currentPage, int pageSize) throws Exception;

	/**
	 * 财务管理暂估出库单管理统计数量
	 *
	 * @author zhangwy
	 * @param parMap
	 * @return
	 */
	GatherOutDepository estimateFinanceOutDepositoryCount(
			FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

	/**
	 * 财务管理暂估出库单管理统计列表
	 *
	 * @author zhangwy
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage estimateFinanceOutDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery,
			int currentPage, int pageSize) throws Exception;

	/**
	 * 根据主表Id获取全部的详细列表
	 *
	 * @param outDepositoryId
	 * @return
	 * @throws Exception
	 */
	List<OutDetail> getOutDetailListByOutDepositoryId(Long outDepositoryId)
			throws Exception;
}
