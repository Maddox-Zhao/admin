/**
 * 
 */
package com.huaixuan.network.biz.service.provider.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.provider.ProvideUpdateGoodsYShangLogDao;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsYShangLog;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.provider.ProvideGoodsYShangLogService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

/**
 * @author TT
 * 
 */
@Service("ProvideGoodsYShangLogService")
public class ProvideGoodsYShangLogServiceImpl implements ProvideGoodsYShangLogService {

	@Autowired
	private ProvideUpdateGoodsYShangLogDao provideUpdateGoodsYShangLogDao;
	/* (non-Javadoc)
	 * @see com.huaixuan.network.biz.service.provider.ProvideGoodsYShangLogService#providerYShanglogInfolist(java.util.Map)
	 */
	@Override
	public MiniUiGrid providerYShanglogInfolist(Map<String, String> searchMap) {
		QueryPage queryPage = new QueryPage(new Object());
		int count = provideUpdateGoodsYShangLogDao.getYShangSaleInfoLogCount(searchMap);
		MiniUiGrid gird = new MiniUiGrid();
		gird.setTotal(count);
		if(count >0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count);
			if(!"yes".equalsIgnoreCase(searchMap.get("noStartRowAndEndRow"))){
				searchMap.put("startRow", queryPage.getPageFristItem()+"");
				searchMap.put("endRow", queryPage.getPageSize()+"");
			}
			List<ProvideUpdateGoodsYShangLog> list = provideUpdateGoodsYShangLogDao.getYShangSaleInfoLogList(searchMap);
			if(list != null && list.size() >0){
				gird.setData(list);
			}
		}
		return gird;
	}

}
