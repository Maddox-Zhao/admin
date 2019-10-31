package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsYShangLog;

public interface ProvideUpdateGoodsYShangLogDao {
	
	public void insertProvideYShangLog(ProvideUpdateGoodsYShangLog pugxyl);
	
	public void insertYShangLog(List<ProvideUpdateGoodsYShangLog> pgList);
	
	public int getYShangSaleInfoLogCount(Map<String, String> searchMap);
	
	public List<ProvideUpdateGoodsYShangLog> getYShangSaleInfoLogList(Map<String,String> searchMap);

}
