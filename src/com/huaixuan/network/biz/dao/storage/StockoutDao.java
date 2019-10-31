package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.domain.storage.query.StockoutQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface StockoutDao  {

	long insertStockOut(Stockout stockout);

	QueryPage getStockoutList(StockoutQuery stockoutQuery, int currentPage, int pageSize, boolean isPage);

	void updateNotifyStatus(Map<String,Object> parMap);

	Stockout getStockout(long id);

	List<Stockout> getStockoutList(Map parMap);
}
