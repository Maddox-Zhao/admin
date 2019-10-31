package com.huaixuan.network.biz.dao.provider;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.product.Product;
import com.huaixuan.network.biz.domain.provider.ProvideUpdateGoodsXiYouLog;

public interface ProvideUpdateGoodsXiYouLogDao {
	
	public void insertProvideXiYouLog(ProvideUpdateGoodsXiYouLog pugxyl);
	
	public int getproviderSaleInfolistCount(Map<String, String> searchMap);
	
	public List<ProvideUpdateGoodsXiYouLog> getproviderSaleInfoList(Map<String,String> searchMap);

}
