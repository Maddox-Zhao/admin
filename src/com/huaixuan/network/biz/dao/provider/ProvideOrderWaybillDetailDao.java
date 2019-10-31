/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;

/**
 * @author TT
 * 
 */
public interface ProvideOrderWaybillDetailDao {

	/**
	 * @param detailmap
	 */
	void insertWayBillDetail(Map<String, String> detailmap);

	/**
	 * @param onemap
	 * @return
	 */
	List<ProvideOrderWaybillDetail> selectWayBill(Map<String, String> onemap);

	/**
	 * @param detailmap
	 */
	void updateOrderWaybillDetail(Map<String, String> detailmap);
	
	/**
	 * @Description: 统计订单
	 * @date 2019-1-16
	 */
	public Integer searchMiniuiProviderOrderCount(Map<String, String> detailmap);
	/**
	 * @Description: 查询所有订单
	 * @date 2019-1-16
	 */
	public List<ProvideOrderWaybillDetail> selectMiniuiAllProviderOrder(Map<String, String> detailmap);
	
	
}
