package com.huaixuan.network.biz.service.user;

import java.util.Map;

import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.user.UserSales;
import com.huaixuan.network.biz.query.QueryPage;

public interface UserSalesManager {

    void addUserSales(UserSales userSales);

    UserSales getUserSalesInfo(Map parMap);

    double getUserRefundInfo(Map parMap);

    int getUserSalesCountByParameterMap(Map parMap);

    QueryPage getUserSalesListsByParameterMap(CrmRankQuery crmCustomerRankQuery, int currentPage, int pageSize);

    int getSalesManCountByParameterMap(Map parMap);

    QueryPage getSalesManListsByParameterMap(CrmRankQuery crmSalesmanRankQuery, int currentPage, int pageSize);

    int getUserTradeDetailCount(Map parMap);

    QueryPage getUserTradeDetail(Map parMap, int currentPage, int pageSize);

    void deleteUserSalesByParameterMap(Map parMap);

    int getSearchDayByMap(Map parMap);
}
