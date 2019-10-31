/**
 * 
 */
package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;



/**
 * @author TT
 * 
 */
public interface ProviderGoodsUpdateDao {
	/**
	 * @param pgList
	 * @return 
	 */
	public void insertXiYouProducts(List<ProvideGoodsXiYou> pgList);

	/**
	 * @param provideGoodsXiYou
	 * @return
	 * 查询
	 */
	public List<ProvideGoodsXiYou> selectAllProid(ProvideGoodsXiYou provideGoodsXiYou);

	/**
	 * @param pg
	 * @return
	 * 查询返回实体
	 */
	public ProvideGoodsXiYou selectOneProidProduct(ProvideGoodsXiYou pg);

	/**
	 * @param pxy
	 */
	public void updateGoodsXiYou(ProvideGoodsXiYou pxy);

	/**
	 * @param maps
	 * @return 
	 */
	public Integer updateGoodsXiYouMap(Map<String, String> maps);
	
	/**
	 * @Description: 查询供应商总条数
	 * @date 2019-1-10
	 */
	public Integer getProviderWithPageCount(Map parMap);
	/**
	 * @Description: 供应商详情
	 * @date 2019-1-10
	 */
	public List<ProvideGoodsXiYou> getProviderListByConditionWithPage(Map parMap);
	
	/**
	 * @Description: providerLog
	 * @date 2019-1-14
	 */
	public Integer getProviderLogPageCount(Map parMap);
	/**
	 * @Description: 供应商log详情
	 * @date 2019-1-14
	 */
	public List<ProvidePostOrderLog> getProviderLogConditionWithPage(Map parMap);

	/**
	 * @return
	 * 查询出stock>0的所有商品
	 * 
	 */
	public List<ProvideGoodsXiYou> selectProidHaveStock();
	
//=================================以下是云尚的===========================
	/**
	 * @Description: 查询供应商总条数
	 * @date 2019-1-10
	 */
	public Integer getProviderWithPageCountYShang(Map parMap);
	
	/**
	 * @Description: 供应商详情
	 * @date 2019-1-10
	 */
	public List<ProvideGoodsYShang> getProviderListByConditionWithPageYShang(Map parMap);
	
}
