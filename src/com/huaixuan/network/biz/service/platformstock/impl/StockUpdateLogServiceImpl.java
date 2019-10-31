package com.huaixuan.network.biz.service.platformstock.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.platformstock.StockUpdateLogDao;
import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.platformstock.StockUpdateLogService;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;
@Service("stockUpdateLogService")
public class StockUpdateLogServiceImpl implements StockUpdateLogService {
	@Autowired
    private  StockUpdateLogDao stockUpdateLogDao;

	@Override
	public MiniUiGrid searchAllStock(Map<String, String> searchMap) {
		
		QueryPage queryPage = new QueryPage(new Object());
		int count = stockUpdateLogDao.searchStockCount(searchMap);
		MiniUiGrid grid = new MiniUiGrid();
		grid.setTotal(count);
		if(count>0){
			String pageIndex = searchMap.get("pageIndex");
			String pageSize = searchMap.get("pageSize");
			queryPage.setCurrentPageForMiniUi(pageIndex);
			queryPage.setPageSizeString(pageSize);
			queryPage.setTotalItemForMiniUi(count); //之前的当前页大于最大页返回最后页  现在不做处理.
			searchMap.put("startRow", queryPage.getPageFristItem()+"");
			searchMap.put("endRow", queryPage.getPageSize()+"");
			List<StockUpdateLog> list = stockUpdateLogDao.selectAllStock(searchMap);
			if(list!=null && list.size()>0){
				grid.setData(list);
			}
		}
	
		return grid;
	}

	@Override
	public List<StockUpdateLog> getAllLocation() {
		// TODO Auto-generated method stub
		return stockUpdateLogDao.selectAllLocation();
	}
}
