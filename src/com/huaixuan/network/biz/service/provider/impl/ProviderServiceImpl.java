package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou;
import com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.provider.ProviderService;
/**
 *2019-1-10 
 */
@Service("providerService")
public class ProviderServiceImpl implements ProviderService{
	@Autowired
	private ProviderGoodsUpdateDao providerDao;
	
	

	//银泰西有的商品
	@Override
	public QueryPage getProviderListByConditionPage(ProvideGoodsXiYou provide,int currPage, int pageSize) {
		
		QueryPage queryPage = new QueryPage(provide);
		Map pramas = queryPage.getParameters();
		//统计供应商总数
		int count = providerDao.getProviderWithPageCount(pramas);
		if(pageSize>0){
			if (count > 0) {
				queryPage.setCurrentPage(currPage);
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);
	
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				//查询供应商信息
				List<ProvideGoodsXiYou> goodsList = providerDao.getProviderListByConditionWithPage(pramas);
				
				if (goodsList != null && goodsList.size() > 0) {
					queryPage.setItems(goodsList);
				}
			}
		}
		return queryPage;
	}

	
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProviderService#getOneProviderGoodsList(com.huaixuan.network.biz.domain.provider.ProvideGoodsXiYou, java.util.List)
	 */
	@Override
	public List<ProvideGoodsXiYou> getOneProviderGoodsList(ProvideGoodsXiYou provide, List<Long> prodList) {
		QueryPage queryPage = new QueryPage(provide);
		Map pramas = queryPage.getParameters();
		if(prodList!=null &&prodList.size()>0){
			pramas.put("hasListProdId", "yes");
		}		
        pramas.put("prodList", prodList);
        List<ProvideGoodsXiYou> goodsList = providerDao.getProviderListByConditionWithPage(pramas);
		return goodsList;
	}

//===========================================以下是云尚的商品=============================================
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProviderService#getProviderListByConditionPageYShang(com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang, int, int)
	 */
	@Override
	public QueryPage getProviderListByConditionPageYShang(
			ProvideGoodsYShang provide, int currPage, int pageSize) {
		 
		QueryPage queryPage = new QueryPage(provide);
		Map pramas = queryPage.getParameters();
		//统计供应商总数
		int count = providerDao.getProviderWithPageCountYShang(pramas);
		if(pageSize>0){
			if (count > 0) {
				queryPage.setCurrentPage(currPage);
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);
	
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				//查询供应商信息
				List<ProvideGoodsYShang> goodsList = providerDao.getProviderListByConditionWithPageYShang(pramas);
				
				if (goodsList != null && goodsList.size() > 0) {
					queryPage.setItems(goodsList);
				}
			}
		}
		return queryPage;
	}


	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProviderService#getOneProviderGoodsListYShang(com.huaixuan.network.biz.domain.provider.ProvideGoodsYShang, java.util.List)
	 */
	@Override
	public List<ProvideGoodsYShang> getOneProviderGoodsListYShang(ProvideGoodsYShang provide, List<String> prodList) {
		QueryPage queryPage = new QueryPage(provide);
		Map pramas = queryPage.getParameters();
		if(prodList!=null &&prodList.size()>0){
			pramas.put("hasListProdId", "yes");
		}		
        pramas.put("prodList", prodList);
        List<ProvideGoodsYShang> goodsList = providerDao.getProviderListByConditionWithPageYShang(pramas);
		return goodsList;
	}

	
	
	

	
	
}
