/**
 * 
 */
package com.huaixuan.network.biz.service.provider;

import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */

public interface ProvideOrderWaybillDetailService {
	/**
	 * @Description: 查询所有订单
	 * @date 2019-1-17
	 */
	public MiniUiGrid searchAllProviderOrderId(Map<String, String> searchMap);
	
	
	
	/**
	 * @Description: 订单信息
	 * @date 2019-1-17
	 */
	public  MiniUiGrid selectMiniuiOrderListDetailService(Map<String, String> searchmap);
	

	
	/**
	 * @Description: 修改订单详情信息
	 * @date 2019-1-18
	 */
//	public  Integer updateProviderOrderDetaillService(Map<String, String> searchmap);
	
}
