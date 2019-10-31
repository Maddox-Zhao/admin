package com.huaixuan.network.biz.dao.statistics;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.StoreDay;
import com.huaixuan.network.biz.domain.report.FCSet;
import com.huaixuan.network.biz.domain.statistics.CatalogOrderAnalysis;
import com.huaixuan.network.biz.domain.statistics.GoodsAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysis;
import com.huaixuan.network.biz.domain.statistics.SaleAnalysisTwo;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.Page;

public interface AnalysisDao {

	public List<FCSet> getSingleOrderAnalysis(Map parMap);

	/**
	 * 一级目录销售金额统计
	 * 
	 * @param parMap
	 * @return @
	 */
	public Map getCatalogAnalysis(Map parMap);

	/**
	 * 类目订单统计
	 * 
	 * @Title: getCatalogOrderAnalysis
	 * @Description: 类目订单统计
	 * @param @param parMap
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public List<CatalogOrderAnalysis> getCatalogOrderAnalysis(Map parMap);

	/**
	 * 类目订单统计
	 * 
	 * @Title: getCatalogOrderAnalysis
	 * @Description: 类目订单统计
	 * @param @param parMap
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public int getCatalogOrderAnalysisCount(Map parMap);

	int getSaleAnalysisCount(Map parMap);

	List<SaleAnalysisTwo> getSaleAnalysis(Map parMap);

	// SaleAnalysis getSaleAnalysis(Map parMap);

	/**
	 * 热销商品
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getHotSalsGoods(Map parMap, Page page) ;

	int getHotSalsGoodsCount(Map parMap);

	Map getHotSalesGoodsSum(Map parMap);

	Map getSaleAnalysisTradeOutPriceSum(Map parMap);

	Map getSaleAnalysisDetailSum(Map parMap);

	/**
	 * 滞销商品
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getSlowSalsGoods(Map parMap, Page page) ;

	int getSlowSalsGoodsCount(Map parMap);

	Map getSlowSalesGoodsSum(Map parMap);

	/**
	 * 类目销售汇总
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	// List<GoodsAnalysis> getCatSalesGoods(Map parMap, Page page) ;

	int getCatSalesGoodsCount(Map parMap);

	Map getCatSalesGoodsSum(Map parMap);

	/**
	 * 退货商品查询
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	QueryPage getAnalysisRefundGoods(Map<String, Object> parMap, Page page);

	int getAnalysisRefundGoodsCount(Map parMap);

	int getSaleAnalysisDetailCount(Map parMap);

	/**
	 * 销售明细查询
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getSaleAnalysisDetail(Map parMap);

	/**
	 * 在库产品统计数量
	 * 
	 * @param parMap
	 * @return
	 */
	int getGoodsInStorageCount(Map parMap);

	/**
	 * 在库产品统计列表
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getGoodsInStorage(Map parMap);

	/**
	 * 销售明细获取退货金额
	 * 
	 * @param parMap
	 * @return
	 */
	Map getRefundAnalysisDetailSum(Map parMap);

	/**
	 * 销售明细获取退货数量
	 * 
	 * @param parMap
	 * @return
	 */
	int getRefundAnalysisDetailCount(Map parMap);

	/**
	 * 销售明细获取退货记录
	 * 
	 * @param parMap
	 * @param page
	 * @return
	 */
	List<SaleAnalysis> getRefundAnalysisDetail(Map parMap);

	/**
	 * 销售统计获取退货数量
	 * 
	 * @param parMap
	 * @return
	 */
	int getRefundAnalysisCount(Map parMap);

	/**
	 * 销售统计获取退货记录
	 * 
	 * @param parMap
	 * @return
	 */
	List<SaleAnalysisTwo> getRefundAnalysis(Map parMap);

	/**
	 * 销售统计获取退货金额
	 * 
	 * @param parMap
	 * @return
	 */
	Map getRefundAnalysisTradeOutPriceSum(Map parMap);

	public List<GoodsAnalysis> getHotSalsGoods(Map parMap);

	public List<GoodsAnalysis> getSlowSalsGoods(Map parMap);

	public List<GoodsAnalysis> getCatSalesGoods(Map parMap);
	
    /**
     * 查询销售统计（商品分库）销售的数量
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getSaleAnalysisDepositoryCount(Map parMap) throws Exception;
    
    /**
     * 查询销售统计（商品分库）退货的数量
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    int getRefundAnalysisDepositoryCount(Map parMap);
    
    /**
     * 查询销售统计（商品分库）销售的价格总数
     * @param parMap
     * @return int
     * @author chenyan 2011/03/03
     */
    Map getSaleAnalysisDepositoryTradeOutPriceSum(Map parMap);
    
    /**
     * 查询销售统计（商品分库）销售的数据列表
     * @param parMap Map
     * @return List
     * @author chenyan 2011/03/03
     */
    List<SaleAnalysisTwo> getSaleAnalysisDepository(Map parMap) throws Exception;
    
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
    
    public StoreDay getStoreDay(Map parMap) throws Exception;
}
