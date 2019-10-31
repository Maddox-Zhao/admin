package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public interface InDetailDao {
    /* @interface model: InDetail */
    long addInDetail(InDetail inDetail) throws Exception;

    /* @interface model: InDetail */
    void editInDetail(InDetail inDetail) throws Exception;

    /* @interface model: InDetail */
    void removeInDetail(Long inDetailId) throws Exception;

    /* @interface model: InDetail,InDetail */
    InDetail getInDetail(Long inDetailId) throws Exception;

    /* @interface model: InDetail,InDetail */
    List<InDetail> getInDetails() throws Exception;

    /**
     * 根据入库单ID取得对应产品详情信息
     * @param id Long
     * @return List
     * @author chenyan 2009/07/22
     */
    List<InDetailGoods> getInDetailGoodsLists(Long id);

    /**
     * 根据商品可分配的库位
     * @param id Long
     * @param depFirstId Long
     * @return List
     * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
     */
    List<GoodsForLocation> getLocationForGoods(Long id, Long depFirstId);

    /**
     * 查询可分配的库位（不需商品对应）
     * @param id Long
     * @param depFirstId Long
     * @return List
     * @author chenyan 2009/07/22 modified by chenyan 2010/03/16
     */
    List<GoodsForLocation> getLocationForGoodsNoMatch(Long id, Long depFirstId);

    /**
     * 根据ID更新入库详细表的状态
     * @param status String
     * @param id Long
     * @param depFirstId Long
     * @param storType String
     * @author chenyan 2009/07/23 modified by chenyan 2010/03/17
     */
    void updateStatusById(String status, Long id, Long depFirstId, String storType);

    /**
     * 根据入库单主表ID取得未完成分配的产品数
     * @param inDepId Long
     * @param status String
     * @return List
     * @author chenyan 2009/07/23
     */
    List<InDetailGoods> listInDetailNotFinish(Long inDepId, String status);

    /**
     * 取得盘点入库时候的基本信息
     * @param inDetailId Long
     * @return InDetailBaseInfo
     * @author chenyan 2009/07/24
     */
    InDetailBaseInfo getCheckInDetailBaseInfo(Long inDetailId);

    /**
     * 取得采购或者退换货入库时候的基本信息
     * @param inDetailId Long
     * @param type String
     * @return InDetailBaseInfo
     * @author chenyan 2009/07/24
     */
    InDetailBaseInfo getShoppingOrSalesInDetailBaseInfo(Long inDetailId, String type);

    /**
     * 根据销售商品可分配的库位
     * @param id Long
     * @return List
     * @author chenyan 2009/07/25
     */
    List<GoodsForLocation> getSalesLocationForGoods(Long id);

    List<GoodsForLocation> getSalesLocationForGoodsChange(Long id);

    void addInDetails(List<InDetail> inDetails);

    /**
     * 根据入库详单ID取得分配好的产品列表
     * @param inDetailId Long
     * @return List
     * @author chenyan 2009/08/12
     */
    List<GoodsForLocation> listInDetailForDisByDetailId(Long inDetailId);

    /**
     * 根据入库详单ID取得已建立库位关联的对应商品实例数量
     * @param inDetailId Long
     * @return int
     * @author chenyan 2009/08/13
     */
    int getGoodsLocationCountByInDetailId(Long inDetailId);

    /**
     * 根据条件得到汇总入库单列表数量
     * @param parMap
     * @return
     */
    int gatherInDepositoryListsCount(Map parMap) throws Exception;

    /**
     * 根据条件得到汇总入库单列表
     * @param parMap
     * @return
     */
    QueryPage gatherInDepositoryLists(Map parMap, int currentPage, int pageSize, boolean isPage) throws Exception;

    void editInDetailGoodsInfo(InDetailGoods inDetailGoodsInfo) throws Exception;

    GatherInDepository gatherFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery) throws Exception;

    QueryPage gatherFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize)
                                                                                                  throws Exception;

    /**
     * 根据条件得到暂估入库单财务列表数量
     * @param parMap
     * @return
     */
    GatherInDepository estimateFinanceInDepositoryCount(FinanceDepositoryQuery financeDepositoryQuery)
                                                                                   throws Exception;

    /**
     * 根据条件得到暂估入库单财务列表
     * @param parMap
     * @return
     */
    QueryPage estimateFinanceInDepositoryLists(FinanceDepositoryQuery financeDepositoryQuery, int currentPage, int pageSize)
                                                                                                    throws Exception;

    /**
     * 根据入库详单ID取得一级仓库和库存类型（采购入库类型）
     * @param inDetailId Long
     * @return InDetailBaseInfo
     * @author chenyan 2010/03/17
     */
    InDetailBaseInfo getInfoByInDetailIdForShoppingList(Long inDetailId);

    /**
     * 查询可分配的库位（次品库）
     * @param id
     * @param depFirstId
     * @param locId
     * @return
     */
    List<GoodsForLocation> getLocationForDefect(Long id, Long locId);

    /**
     * 根据主表获取子表列表
     * @param InDepositoryId
     * @return
     */
    List<InDetail> getInDetailListByInDepositoryId(Long InDepositoryId);
}
