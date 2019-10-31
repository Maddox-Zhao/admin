package com.huaixuan.network.biz.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StockoutDao;
import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.domain.storage.query.StockoutQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.StockoutManager;

@Service("stockoutManager")
public class StockoutManagerImpl implements StockoutManager {

	@Autowired
	StockoutDao stockoutDao;

	@Override
	public QueryPage getStockoutList(StockoutQuery stockoutQuery, int currentPage, int pageSize, boolean isPage) {
		return stockoutDao.getStockoutList(stockoutQuery,currentPage,pageSize,isPage);
	}

	@Override
	public boolean updateNotifyStatus(Map<String, Object> parMap) {
		try {
			stockoutDao.updateNotifyStatus(parMap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
    public Stockout getStockout(long id) {
        return this.stockoutDao.getStockout(id);
    }

	@Override
	public List<Stockout> getStockoutList(Map parMap) {
		return stockoutDao.getStockoutList(parMap);
	}

}
