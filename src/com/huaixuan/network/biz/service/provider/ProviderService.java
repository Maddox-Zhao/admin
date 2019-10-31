package com.huaixuan.network.biz.service.provider;

import java.util.List;

import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.query.QueryPage;



/**
 *2019-1-10 
 *供应商
 */
public interface ProviderService {
	/**
	 * @Description: 供应商分页查询
	 * @date 2019-1-11
	 */
	public QueryPage getProviderListByConditionPage(ProvideGoodsXiYou provide, int currPage, int pageSize);

	/**
	 * @param provide
	 * @param list 
	 * @return
	 * 用于导出商品的查询
	 */
	public List<ProvideGoodsXiYou> getOneProviderGoodsList(ProvideGoodsXiYou provide, List<Long> list);
	
	
	
	
	
	//===========================================以下是云尚的商品=============================================
	/**
	 * @Description: 供应商分页查询
	 * @date 2019-1-11
	 */
	public QueryPage getProviderListByConditionPageYShang(ProvideGoodsYShang provide, int currPage, int pageSize);
	
	/**
	 * @param provide
	 * @param list 
	 * @return
	 * 用于导出商品的查询
	 */
	public List<ProvideGoodsYShang> getOneProviderGoodsListYShang(ProvideGoodsYShang provide, List<String> list);
	
	
}
