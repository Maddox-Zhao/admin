package com.huaixuan.network.biz.dao.order;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.domain.product.Product;



public interface OrderDao {

	public int getOrderListCount(Map<String,String> searchMap);
	/**
	 * 根据查询条件统计总金额
	 * @param searchMap
	 * @return
	 */
	public String getOrderListTotalPrice(Map<String,String> searchMap);
	
	
	/**
	 * 是否可以查询所有订单
	 * @param userName
	 * @return
	 */
	public boolean canSearchAllOrder(String userName);
	
	
	public List<Orderdetails> getorderList(Map<String,String> searchMap);
	
	
	public Orderdetails getorderlistByid(String idorder);
	
	public List<Product> getProductListByidorder(Map<String,String> searchMap);
	
	public Integer insertIntoCustomerOrder(Map<String, String> map);
	
	
	public void updateCustomerOrder(Orderdetails orderdetails);
	/**
	 * @param searchMap
	 * @return
	 * 用于订单导出的count查询
	 */
	public int getOrderListCountExport(Map<String, String> searchMap);
	/**
	 * @param searchMap
	 * @return
	 * 用于订单导出的查询
	 */
	public List<Orderdetails> getorderListExport(Map<String, String> searchMap);
	
}
