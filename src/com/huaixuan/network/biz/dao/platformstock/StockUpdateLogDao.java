package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;

public interface StockUpdateLogDao {
	//查询当前总数
	public int searchStockCount(Map<String,String> searchMap);
	//查全部、
	public List<StockUpdateLog> selectAllStock(Map<String,String> searchMap);
	//查询所有的站点
	public List<StockUpdateLog> selectAllLocation();
}
