package com.huaixuan.network.biz.service.reserved;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.reserved.ReservedOrderProduct;
import com.huaixuan.network.common.util.Result;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;



/**
 * @author Mr_Yang   2016-9-6 下午03:32:56
 **/

public interface ReservedOrderService
{
	/**
	 * 预开单
	 * @param requestMap
	 * @return
	 */
	public String reservedShoppingCar2SettleAccount(Map<String, String> requestMap,AdminAgent adminAgent);
	
	/**
	 * 查询
	 * @param searchMap
	 * @return
	 */
	public MiniUiGrid searchReservedOrder(Map<String,String> searchMap);
	
	/**
	 * 查询订单总金额
	 * @param searchMap
	 * @return
	 */
	public String searchReservedListPrice(Map<String, String> searchMap);
	
	
	/**
	 * 查询预开单产品详情
	 * @param searchMap
	 * @return
	 */
	public List<ReservedOrderProduct> searchReservedOrderProduct(Map<String,String> searchMap);
	
	
	/**
	 * 设置订单产品的真实idProduct
	 * @param requestMap 请求数据 包括 订单id 和扫描的idProduct
	 * @return
	 */
	public Result setRealyIdProduct(Map<String,String> requestMap);
	
	
	/**
	 * 修改预开单真实idProduct
	 * @param requestMap
	 * @return
	 */
	public Result updateRealyIdProduct(Map<String, String> requestMap);
	
	
	/**
	 * 生成真实订单
	 * @param requestMap
	 * @return
	 */
	public Result preOrdertoRealyOrder(Map<String,String> requestMap,AdminAgent adminAgent);
 
}
 
