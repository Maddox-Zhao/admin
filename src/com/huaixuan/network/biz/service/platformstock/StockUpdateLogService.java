package com.huaixuan.network.biz.service.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockUpdateLog;
import com.huaixuan.network.common.util.miniui.MiniUiGrid;

public interface StockUpdateLogService {
	
	 //查全部
	public MiniUiGrid searchAllStock(Map<String,String> searchMap);
	
	public List<StockUpdateLog> getAllLocation();

}
