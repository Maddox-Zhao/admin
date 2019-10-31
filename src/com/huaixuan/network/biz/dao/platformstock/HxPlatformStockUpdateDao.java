package com.huaixuan.network.biz.dao.platformstock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.platformstock.StockUpdate;

public interface HxPlatformStockUpdateDao {
	//查询当前的库存总数量
		public int HxStockUpdatecnt(Map<String,String> searchmap);
		
		//根据条件查询库存
		
		public List<StockUpdate> HxSelectStockUpdate(Map<String,String> searchmap);
}
