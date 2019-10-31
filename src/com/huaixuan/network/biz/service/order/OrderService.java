package com.huaixuan.network.biz.service.order;

import java.util.Map;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.order.Orderdetails;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface OrderService {
	public  QueryPage getOrderList(Map<String,String> searchMap);
	
	public  MiniUiGrid getOrdersList(Map<String,String> searchMap);
	
	/**
	 * 根据查询条件统计总金额
	 * @param searchMap
	 * @return
	 */
	public String getOrderListTotalPrice(Map<String,String> searchMap);
 
	public  Orderdetails getorderlistByid(String idorder);
	public  MiniUiGrid getProductByidorder(Map<String,String> searchMap);
	
	public void updateCustomerOrder(Orderdetails orderdetails);
	
	/**
	 * 订单完成付款
	 * @param map
	 */
	public void customerOrderPayMent(Map<String,String> map);
	
	
	/**
	 * 通过订单ID取消订单
	 * @param idOrder
	 */
	public void cancelOrderById(Map<String, String> requestMap);
	
	
	/**
	 * 单个产品 取消预留
	 * @param idProduct
	 */
	public void cancelOrderByIdProduct(String idProduct,AdminAgent adminAgent);
	
	
	
	
	/**
	 * 单个产品 销售入库
	 * @param idProduct
	 * @param idPayment 退款方式
	 */
	public void saleInStockByIdProduct(String idProduct,String idPayment,AdminAgent adminAgent);
	
	
}
