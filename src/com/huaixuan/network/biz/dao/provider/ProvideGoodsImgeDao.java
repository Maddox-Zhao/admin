/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsImge;

/**
 * @author TT
 * 
 */
public interface ProvideGoodsImgeDao {

	/**
	 * @param pgiList
	 */
	void insertGoodsImge(List<ProvideGoodsImge> pgiList);
	/**
	 * @Description: 下载图片
	 * @date 2019-1-21
	 */
	public List<ProvideGoodsImge> getProvideImgDao(Map parMap);
	/**
	 * @param prodid
	 * @return
	 */
	List<ProvideGoodsImge> getProvideOneGoodsImage(String prodid);
	/**
	 * @param pgi
	 */
	void updatGoodsImage(ProvideGoodsImge pgi);
}
