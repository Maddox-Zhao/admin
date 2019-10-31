package com.huaixuan.network.biz.service.statistics;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.Page;

/**
 * 统计报表
 * @author yuancong
 * @version $Id: AnalysisManager.java,v 0.1 2009-3-30 下午05:18:16 yuancong Exp $
 */
public interface AnalysisManager {
    /**
     * 查询订单统计报表
     * @return
     */
    public String getSingleOrderAnalysis(Map parMap);

    /**
     * 查询一级目录商品销售统计报表
     * @param parMap
     * @return
     */
    public String getCatalogAnalysis(Map parMap);

    /**
     * 查询订单统计报表
     * @return
     */
    public String getMultiOrderAnalysis(Map parMap);
    
    
    public List<String> getStoreAnalysis(Map parMap);
    
    public List<String> getStoreAnalysisday(Map parMap);

    public int getSaleAnalysisCount(Map parMap);

    public Map getSaleAnalysisTradeOutPriceSum(Map parMap);

    /**
    * @Title: getCatalogOrderAnalysis
    * @Description: 类目订单统计
    * @param @param parMap
    * @param @return
    * @return Map
    * @throws
     */
    public QueryPage getCatalogOrderAnalysis(Map parMap, int currPage, int pageSize);

    public Map getSaleAnalysisDetailSum(Map parMap);

    public List<SaleAnalysisTwo> getSaleAnalysis(Map parMap);

    public int getSaleAnalysisDetailCount(Map parMap);

    /**
     * 查询销售明细
     * @param parMap
     * @param page
     * @return
     */
    public List<SaleAnalysis> getSaleAnalysisDetail(Map parMap);

    /**
     * 热销商品列表
     * @param parMap
     * @param page
     * @return
     */
    public QueryPage getHotSalesGoods(Map parMap, int currPage, int pageSize);

    public int getHotSalesGoodsCount(Map parMap);

    public Map getHotSalesGoodsSum(Map parMap);

    /**
     * 获得滞销商品列表
     * @param parMap
     * @param page
     * @return
     */
    public QueryPage getSlowSalesGoods(Map parMap, int currPage, int pageSize);

    public int getSlowSalesGoodsCount(Map parMap);

    public Map getSlowSalesGoodsSum(Map parMap);

    /**
     * 类目销售汇总列表
     * @param parMap
     * @param page
     * @return
     */
    //    public List<GoodsAnalysis> getCatSalesGoods(Map parMap, Page page);

    public int getCatSalesGoodsCount(Map parMap);

    public Map getCatSalesGoodsSum(Map parMap);

    /**
     * 退货商品查询
     * @param parMap
     * @param page
     * @return
     */
	public QueryPage getAnalysisRefundGoods(Map parMap, Page page);

    public int getAnalysisRefundGoodsCount(Map parMap);

    /**
     * 在库产品统计数量
     * @param parMap
     * @return
     */
    public int getGoodsInStorageCount(Map parMap);

    /**
     * 在库产品统计列表
     * @param parMap
     * @param page
     * @return
     */
    //    public List<SaleAnalysis> getGoodsInStorage(Map parMap, Page page);

    /**
     * 销售明细获取退货金额
     * @param parMap
     * @return
     */
    public Map getRefundAnalysisDetailSum(Map parMap);

    /**
     * 销售明细获取退货数量
     * @param parMap
     * @return
     */
    public int getRefundAnalysisDetailCount(Map parMap);

    /**
     * 销售明细获取退货记录
     * @param parMap
     * @param page
     * @return
     */
    public List<SaleAnalysis> getRefundAnalysisDetail(Map parMap);

    /**
     * 销售统计获取退货数量
     * @param parMap
     * @return
     */
    public int getRefundAnalysisCount(Map parMap);

    /**
     * 销售统计获取退货金额
     * @param parMap
     * @return
     */
    public Map getRefundAnalysisTradeOutPriceSum(Map parMap);

    /**
     * 销售统计获取退货记录
     * @param parMap
     * @return
     */
    public List<SaleAnalysisTwo> getRefundAnalysis(Map parMap);

    public QueryPage getCatSalesGoods(Map parMap, int currPage, int pageSize);

    public QueryPage getGoodsInStorage(Map parMap, int currPage, int pageSize);

    public QueryPage getInOutStatReportListByMap(Map parMap, int currPage, int pageSize);
    
    /**
     * 查询销售统计（商品分库）销售的数量
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getSaleAnalysisDepositoryCount(Map parMap);
    
    /**
     * 查询销售统计（商品分库）退货的数量
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getRefundAnalysisDepositoryCount(Map parMap);
    
    /**
     * 查询销售统计（商品分库）销售价格列表
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * 查询销售统计（商品分库）销售的数据列表
     * @param parMap
     * @return
     */
    List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap);
    
    /**
     * 查询销售统计（商品分库）退货价格列表
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getRefundAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * 查询销售统计（商品分库）退货的数据列表
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    List<SaleAnalysisTwo> getRefundAnalysisDepository(Map parMap);
}