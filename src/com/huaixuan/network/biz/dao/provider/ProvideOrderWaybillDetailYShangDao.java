/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetail;
import com.huaixuan.network.biz.domain.provider.ProvideOrderWaybillDetailYShang;

/**
 * @author TT
 * 
 */
public interface ProvideOrderWaybillDetailYShangDao {

	/**
	 * @param detailmap
	 */
	void insertWayBillDetailYShang(Map<String, String> detailmap);

	/**
	 * @param onemap
	 * @return
	 */
//	List<ProvideOrderWaybillDetailYShang> selectWayBillYShang(Map<String, String> onemap);

	/**
	 * @param detailmap
	 */
	void updateOrderWaybillDetailYShang(Map<String, String> detailmap);
	
	/**
	 * @Description: 统计订单
	 * @date 2019-1-16
	 */
	public Integer selectMiniuiOrderListDetailCount(Map<String, String> detailmap);
	/**
	 * @Description: 查询所有订单
	 * @date 2019-1-16
	 */
	public List<ProvideOrderWaybillDetailYShang> selectMiniuiOrderListDetail(Map<String, String> detailmap);
	
	
}
