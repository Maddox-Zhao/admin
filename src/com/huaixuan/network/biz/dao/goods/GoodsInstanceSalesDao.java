package com.huaixuan.network.biz.dao.goods;

import java.util.Map;

import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSales;
import com.huaixuan.network.biz.query.QueryPage;


public interface GoodsInstanceSalesDao {

	void addGoodsInstanceSales(GoodsInstanceSales goodsInstanceSales) throws Exception;

	double getSalesAmountInfo(Map parMap) throws Exception;

	double getRefundAmountInfo(Map parMap) throws Exception;

	int getProductByParameterMap(Map parMap) throws Exception;

	QueryPage getProductListsByParameterMap(CrmRankQuery crmProductRankQuery, int currPage, int pageSize) throws Exception;

	void deleteProductByParameterMap(Map parMap) throws Exception;

	int getSearchDayByMap(Map parMap) throws Exception;
}
