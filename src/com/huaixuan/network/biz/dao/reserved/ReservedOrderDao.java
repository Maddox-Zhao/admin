package com.huaixuan.network.biz.dao.reserved;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.reserved.ReservedOrder;
import com.huaixuan.network.biz.domain.reserved.ReservedOrderProduct;



/**
 * @author Mr_Yang   2016-9-6 下午03:48:03
 **/

public interface ReservedOrderDao
{
	/**
	 * 添加预开单订单	
	 * @param reservedOrder
	 * @return
	 */
	public Long insertReservedOrder(ReservedOrder reservedOrder);
	
	
	/**
	 * 添加预开单产品
	 * @param reservedOrderProduct
	 */
	public void insertReservedOrderProduct(ReservedOrderProduct reservedOrderProduct);
	
	
	/**
	 * 查询预开单订单
	 * @param searchMap
	 * @return
	 */
	public List<ReservedOrder> searchReservedList(Map<String,String> searchMap);
	
	public int searchReservedListCnt(Map<String,String> searchMap);
	
	/**
	 * 查询订单总额
	 * @param searchMap
	 * @return
	 */
	public String searchReservedListPrice(Map<String,String> searchMap);
	
	
	/**
	 * 查询预开单产品详情
	 * @param searchMap
	 * @return
	 */
	public List<ReservedOrderProduct> searchReservedOrderProduct(Map<String,String> searchMap);
	
	/**
	 * 更新订单产品详情
	 * @param reservedOrderProduct
	 */
	public void updateReservedProudctByNotNull(ReservedOrderProduct reservedOrderProduct);
	
	/**
	 * 更新预开单信息
	 * @param reservedOrder
	 */
	public void updateReservedOrderByNotNull(ReservedOrder reservedOrder);
	
}
 
