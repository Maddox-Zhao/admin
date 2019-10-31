/**
 * 
 */
package com.huaixuan.network.biz.service.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideOrderDetail;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */
public interface ProvideOrderDetailService {

	/**
	 * @param searchmap
	 * @return
	 */
	MiniUiGrid getProvideOrderDetail(Map<String, String> searchmap);
	
	
	/**
	 * @Description: 订单信息
	 * @date 2019-1-17
	 */
	public  MiniUiGrid searchOneProviderOrderOrderIdService(Map<String, String> searchmap);
	
	/**
	 * @Description: 修改订单详情信息
	 * @date 2019-1-18
	 */
	public  Integer updateProviderOrderDetaillService(Map<String, String> searchmap);
}
