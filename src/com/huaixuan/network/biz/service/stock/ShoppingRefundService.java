/**
 * @Title: ShoppingRefundService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:53:21
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundSearch;
import com.huaixuan.network.biz.domain.stock.query.RefundDetailSearchQuery;
import com.huaixuan.network.biz.domain.stock.query.ShoppingRefundSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @ClassName: ShoppingRefundService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:53:21
 *
 */
public interface ShoppingRefundService {
	/**
	 *
	 * @param shoppingRefund
	 * @return
	 */
	public long addShoppingRefund(ShoppingRefund shoppingRefund)
			throws Exception;

	/**
	 *
	 * @param shoppingRefund
	 */
	public void editShoppingRefund(ShoppingRefund shoppingRefund)
			throws Exception;

	/**
	 *
	 * @param shoppingRefundId
	 */
	public void removeShoppingRefund(Long shoppingRefundId) throws Exception;

	/**
	 *
	 * @param shoppingRefundId
	 * @return
	 */
	public ShoppingRefund getShoppingRefund(Long shoppingRefundId)
			throws Exception;

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	public ShoppingRefund getShoppingRefunds(Map<String, String> paramMap)
			throws Exception;

	/**
	 *
	 * @param parMap
	 * @return
	 */
	public int getShoppingCountByParameterMap(Map parMap) throws Exception;

	/**
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	// public List<ShoppingRefund> getShoppingRefundListByParameterMap(
	// Map<String, Object> parameterMap, Page page);
	public QueryPage getShoppingRefundListByParameterMap(
			ShoppingRefundSearchQuery shoppingRefundSearchQuery, int currPage,
			int pageSize) throws Exception;

	/**
	 *
	 * @param shoppingRefundId
	 * @return
	 */
	public List<ShoppingRefundSearch> getShoppingRefundNum(Long shoppingRefundId)
			throws Exception;

	/**
	 *
	 * @param parMap
	 * @return
	 */
	public List<ShoppingRefundDSearch> getShoppingRefundDNum(
			Map<String, String> parMap) throws Exception;

	/**
	 * 根据查询条件，查询采购退货订单详细记录集合
	 */
	// public List<ShoppingRefundDetailSearch>
	// getShoppingRefundDetailSearchRusultByParameterMap(
	// Map parameterMap, Page page);
	public QueryPage getShoppingRefundDetailSearchRusultByParameterMap(
			RefundDetailSearchQuery refundDetailSearchQuery, int currPage, int pageSize) throws Exception;

	/**
	 * 根据查询条件，查询采购退货订单详细记录总条数
	 */
	public int getShoppingRefundCountDetailSearchByParameterMap(
			Map<String, String> parMap) throws Exception;

	/**
	 * 根据查询条件，查询采购退货订单汇总记录集合
	 */
	// public List<ShoppingRefundGatherSearch>
	// getShoppingRefundGatherSearchRusultByParameterMap(
	// Map parameterMap, Page page);
	public QueryPage getShoppingRefundGatherSearchRusultByParameterMap(
			RefundDetailSearchQuery refundDetailSearchQuery, int currPage, int pageSize) throws Exception;

	/**
	 * 根据查询条件，查询采购退货订单汇总记录总条数
	 */
	public int getShoppingRefundCountGatherSearchByParameterMap(
			Map<String, String> parMap) throws Exception;

	/**
	 * 根据关联单号查入库批次号
	 *
	 * @param relationNum
	 * @return
	 */
	public List<ShoppingRefundDetail> getBatchNumByRelationNum(
			String relationNum) throws Exception;
}
