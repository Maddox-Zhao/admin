/**
 * @Title: ShoppingListService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:39:27
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.ShoppingListQuery;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @ClassName: ShoppingListService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:39:27
 *
 */
public interface ShoppingListService {
	/**
	 *
	 * @param shoppingList
	 * @return
	 */
	public long addShoppingList(ShoppingList shoppingList) throws Exception;

	/**
	 * 修改除去时间以外的信息
	 *
	 * @param shoppingList
	 */
	public void editShoppingList(ShoppingList shoppingList, String oldStatus, String actionType);


	 /**
	 * 修改包括时间在内的所有信息
	 * @param shoppingList
	 */
	 public void editShoppingListAllInfo(ShoppingList shoppingList);

	// /**
	// *
	// * @param shoppingListId
	// */
	// public void removeShoppingList(Long shoppingListId);
	//
	/**
	 *
	 * @param shoppingListId
	 * @return
	 */
	public ShoppingList getShoppingList(Long shoppingListId) throws Exception;

	//
	/**
	 *
	 * 根据采购订单编号得到采购订单对象
	 *
	 * @param shoppingNum
	 * @return
	 */
	public ShoppingList getShoppingListByShoppingNum(String shoppingNum)
			throws Exception;

	//
	/**
	 *
	 * @param shoppingNum
	 * @return
	 */
	public int getCountByShoppingNum(String shoppingNum) throws Exception;

	//
	// /**
	// *
	// * @param parameterMap
	// * @param page
	// * @return
	// */
	public QueryPage getShoppingListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	// /**
	// *
	// * @param parMap
	// * @return
	// */
	// public int getShoppingCountByParameterMap(Map<String, String>
	// parMap)throws Exception;
	//
	/**
	 * 根据查询条件，查询采购订单详细记录集合
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getShoppingDetailSearchRusult(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	//
	// /**
	// * 根据查询条件，查询采购订单财务应收款记录总条数
	// * @param parMap
	// * @return
	// */
	// public int getDueSearchCountByParameterMap(Map<String, String> parMap);
	//
	/**
	 * 根据查询条件，查询采购订单财务应收款记录集合
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getDueSearchListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	/**
	 * 根据查询条件，查询采购订单详细记录总条数
	 */
	public int getShoppingCountDetailSearchByParameterMap(
			Map<String, String> parMap) throws Exception;

	/**
	 * 根据查询条件，查询采购订单汇总记录集合
	 */
	public QueryPage getShoppingGatherSearchRusult(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	/**
	 * 根据查询条件，查询采购订单汇总记录总条数
	 */
	// public QueryPage getShoppingCountGatherSearchByParameterMap(
	// StockDetailSearchQuery stockDetailSearchQuery, int currPage,
	// int pageSize) throws Exception;
	//
	/**
	 * 根据查询条件，查出对应的供货商对应的记录
	 *
	 * @param v_SearchShoppingList
	 *            对应的查询vo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public QueryPage getSearchShoppingLists(
			StockDetailSearchQuery refundDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	//
	// /**
	// * 根据查询条件，查出对应的供货商对应的记录总条数
	// * @param v_SearchShoppingList
	// * @return
	// * @throws Exception
	// */
	// public int getCountSupplierShoppingLists(V_SearchShoppingList
	// v_SearchShoppingList)throws Exception;
	//
	 /**
	 * 验收采购单（修改在途库存、生成入库单、修改产品实例表和商品表中的库存）
	 */
	 @Transactional
	 @SuppressWarnings("unchecked")
	 public Boolean checkShoppingList(Map parMap) throws Exception;

	/**
	 * 采购单明细,采购库存统计查询
	 *
	 * @param paramMap
	 * @return
	 */
	public List<ShoppingDetailSearch> getShoppingDetailStorageNum(
			Map<String, String> paramMap) throws Exception;

	//
	//
	// /**
	// * 根据查询条件，查询采购订单财务暂估应收款记录总条数
	// * @param parMap
	// * @return
	// */
	// public int getDueEstimateSearchCountByParameterMap(Map<String, String>
	// parMap)throws Exception;
	//
	/**
	 * 根据查询条件，查询采购订单财务暂估应收款记录集合
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getDueEstimateSearchListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	/**
	 * 供应商供应商品导出
	 *
	 * @param v_SearchShoppingList
	 * @return
	 */
	public List<V_SupplierShoppingList> getSupplierShoppingExportList(
			StockDetailSearchQuery stockDetailSearchQuery) throws Exception;
}
