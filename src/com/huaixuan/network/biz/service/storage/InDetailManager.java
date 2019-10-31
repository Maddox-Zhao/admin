package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 入库单详情(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface InDetailManager {
	/**
	 * 增加入库单详情信息
	 *
	 * @param inDetail
	 * @return
	 */
	public long addInDetail(InDetail inDetail);

	/**
	 * 编辑入库单详情信息
	 *
	 * @param inDetail
	 */
	public void editInDetail(InDetail inDetail);

	/**
	 * 删除入库单详情信息
	 *
	 * @param inDetailId
	 */
	public void removeInDetail(Long inDetailId);

	/**
	 * 根据ID查询入库单详情信息
	 *
	 * @param inDetailId
	 * @return
	 */
	public InDetail getInDetail(Long inDetailId);

	/**
	 * 查询所有入库单详情信息
	 *
	 * @return
	 */
	public List<InDetail> getInDetails();

	/**
	 * 根据入库单ID取得对应产品详情信息
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @parm relationNum String
	 * @return List
	 * @author chenyan 2009/07/22
	 */
	List<InDetailGoods> getInDetailGoodsLists(Long id, String type, String relationNum);

	/**
	 * 根据商品可分配的库位
	 *
	 * @param id
	 *            Long
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
	 */
	List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId);

	/**
	 * 查询可分配的库位（不需商品对应）
	 *
	 * @param id
	 *            Long
	 * @param depFirstId
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
	 */
	List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId);

	/**
	 * 进行入库操作的数据库事务
	 *
	 * @param inDetailInfo
	 *            InDetail
	 * @param mapForUpdate
	 *            Map
	 * @param inDetailBaseInfo
	 *            InDetailBaseInfo
	 * @author chenyan 2009/07/23 modified by chenyan 2010/03/17
	 */
	void updateInDetailRelationIn(InDetail inDetailInfo, Map<String, String[]> mapForUpdate,
			InDetailBaseInfo inDetailBaseInfo);

	/**
	 * 根据入库单主表ID取得未完成分配的产品数
	 *
	 * @param inDepId
	 *            Long
	 * @param status
	 *            String
	 * @return List
	 * @author chenyan 2009/07/23
	 */
	List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status);

	/**
	 * 取得盘点入库时候的基本信息
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2009/07/24
	 */
	InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId);

	/**
	 * 取得采购或者退换货入库时候的基本信息
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2009/07/24
	 */
	InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type);

	/**
	 * 根据销售商品可分配的库位
	 *
	 * @param id
	 *            Long
	 * @return List
	 * @author chenyan 2009/07/25
	 */
	List<GoodsForLocation> getSalesLocationForGoods(Long id);

	/**
	 * 根据入库详单ID取得分配好的产品列表
	 *
	 * @param inDetailId
	 *            Long
	 * @return List
	 * @author chenyan 2009/08/12
	 */
	List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId);

	/**
	 * 根据入库详单ID取得已建立库位关联的对应商品实例数量
	 *
	 * @param inDetailId
	 *            Long
	 * @return int
	 * @author chenyan 2009/08/13
	 */
	int getGoodsLocationCountByInDetailId(Long inDetailId);

	/**
	 * 根据条件得到汇总入库单列表数量
	 *
	 * @param parMap
	 * @return
	 */
	public int gatherInDepositoryListsCount(Map<String, String> parMap);

	 /**
	 * 根据条件得到汇总入库单列表
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage gatherInDepositoryLists(GatherQuery gatherQuery, int currPage, int pageSize, boolean isPage);

	/**
	 * 根据条件得到汇总入库单财务列表数量
	 *
	 * @param parMap
	 * @return
	 */
	public GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery);

	 /**
	 * 根据条件得到汇总入库单财务列表
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize);

	/**
	 * 根据条件得到暂估入库单财务列表数量
	 *
	 * @param parMap
	 * @return
	 */
	public GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery);

	 /**
	 * 根据条件得到暂估入库单财务列表
	 *
	 * @param parMap
	 * @return
	 */
	 public QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currPage, int pageSize);

	/**
	 * 根据入库详单ID取得一级仓库和库存类型（采购入库类型）
	 *
	 * @param inDetailId
	 *            Long
	 * @return InDetailBaseInfo
	 * @author chenyan 2010/03/17
	 */
	InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId);

	/**
	 * 查询可分配的库位（次品库）
	 *
	 * @param id
	 * @param depFirstId
	 * @param loc_id
	 * @return List
	 * @author zhangwy
	 */
	List<GoodsForLocation> getLocationForDefect(Long id, Long locId);
}
