package com.huaixuan.network.biz.service.goods;

import java.util.Map;

import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSales;
import com.huaixuan.network.biz.query.QueryPage;

public interface GoodsInstanceSalesManager {

    public void addGoodsInstanceSales(GoodsInstanceSales goodsInstanceSales);

    double getSalesAmountInfo(Map parMap);

    double getRefundAmountInfo(Map parMap);

    public int getProductByParameterMap(Map parMap);

    QueryPage getProductListsByParameterMap(CrmRankQuery crmProductRankQuery, int currentPage, int pageSize);

    public void deleteProductByParameterMap(Map parMap);

    public int getSearchDayByMap(Map parMap);
}
