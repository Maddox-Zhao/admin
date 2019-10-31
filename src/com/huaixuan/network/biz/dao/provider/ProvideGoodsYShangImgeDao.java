/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShangImge;

/**
 * @author TT
 * 
 */
public interface ProvideGoodsYShangImgeDao {

	/**
	 * @param pgiList
	 */
	void insertGoodsYShangImge(List<ProvideGoodsYShangImge> pgiList);
	/**
	 * @Description: 下载图片
	 * @date 2019-1-21
	 */
	public List<ProvideGoodsYShangImge> getProvideYShangImgDao(Map parMap);
	/**
	 * @param prodid
	 * @return
	 */
	List<ProvideGoodsYShangImge> getProvideOneGoodsYShangImage(String prodid);
	/**
	 * @param pgi
	 */
	void updatGoodsYShangImage(ProvideGoodsYShangImge pg);
}
