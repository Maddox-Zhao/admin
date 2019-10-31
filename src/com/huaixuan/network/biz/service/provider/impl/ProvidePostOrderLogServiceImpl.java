/**
 * 
 */
package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProviderGoodsUpdateDao;
import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.provider.ProvidePostOrderLogService;

/**
 * @author TT
 * 
 */
@Service("ProvidePostOrderLogService")
public class ProvidePostOrderLogServiceImpl implements ProvidePostOrderLogService {
	@Autowired
	private ProviderGoodsUpdateDao providerDao;
	
	@Override
	public QueryPage getProviderLogConditionPage(ProvidePostOrderLog providerLog,
			int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(providerLog);
		Map pramas = queryPage.getParameters();
		
		int count = providerDao.getProviderLogPageCount(pramas);
		if(pageSize>0){
			if (count > 0) {
				queryPage.setCurrentPage(currPage);
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);
	
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				//查询供应商log信息
				List<ProvidePostOrderLog> goodsList = providerDao.getProviderLogConditionWithPage(pramas);
				
				if (goodsList != null && goodsList.size() > 0) {
					queryPage.setItems(goodsList);
				}
			}
		}
		return queryPage;
	}

}
