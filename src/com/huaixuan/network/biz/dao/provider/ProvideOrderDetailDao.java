/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;

/**
 * @author TT
 * 
 */
public interface ProvideOrderDetailDao {

	/**
	 * @param orderIdProList
	 */
	void insertOrderDetailList(List<ProvideOrderDetail> orderIdProList);

	/**
	 * @param searchmap
	 * @return
	 */
	int selectOrderDetailCount(Map<String, String> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<ProvideOrderDetail> selectOrderDetailByMap(
			Map<String, String> searchmap);
	
	/**
	 * @Description: 通过订单id查询订单详情
	 * @date 2019-1-17
	 */
	public List<ProvideOrderDetail> selectProviderOrderId(String orderId) ;
	
	/**
	 * @Description: 修改订单详情信息
	 * @date 2019-1-18
	 */
	public Integer updateProviderOrderDetailDao(Map<String, String> searchmap);

}
