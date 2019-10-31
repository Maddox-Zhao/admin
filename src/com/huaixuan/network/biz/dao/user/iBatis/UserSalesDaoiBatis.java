package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserSalesDao;
import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.user.UserSales;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("userSalesDao")
public class UserSalesDaoiBatis implements UserSalesDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addUserSales(UserSales userSales) {
        this.sqlMapClient.insert("addUserSales", userSales);
    }

    @Override
    public double getUserRefundInfo(Map parMap) {
        return (Double) this.sqlMapClient.queryForObject("getUserRefundInfo", parMap);
    }

    @Override
    public UserSales getUserSalesInfo(Map parMap) {
        return (UserSales) this.sqlMapClient.queryForObject("getUserSalesInfo", parMap);
    }

    @Override
    public int getUserSalesCountByParameterMap(Map parMap) {
        return (Integer) this.sqlMapClient
            .queryForObject("getUserSalesCountByParameterMap", parMap);
    }

    @Override
    public QueryPage getUserSalesListsByParameterMap(CrmRankQuery crmCustomerRankQuery,
                                                     int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(crmCustomerRankQuery);
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        Map parMap = queryPage.getParameters();
        int count = (Integer) sqlMapClient
            .queryForObject("getUserSalesCountByParameterMap", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {

            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage
                .setItems(sqlMapClient.queryForList("getUserSalesListsByParameterMap", parMap));
        }
        return queryPage;
    }

    @Override
    public int getSalesManCountByParameterMap(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getSalesManCountByParameterMap", parMap);
    }

    @Override
    public QueryPage getSalesManListsByParameterMap(CrmRankQuery crmSalesmanRankQuery,
                                                    int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(crmSalesmanRankQuery);
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);
        Map parMap = queryPage.getParameters();
        int count = (Integer) sqlMapClient.queryForObject("getSalesManCountByParameterMap", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getSalesManListsByParameterMap", parMap));
        }
        return queryPage;
    }

    @Override
    public QueryPage getUserTradeDetail(Map parMap, int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("getUserTradeDetailCount", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getUserTradeDetail", parMap));
        }
        return queryPage;
    }

    @Override
    public int getUserTradeDetailCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getUserTradeDetailCount", parMap);
    }

    @Override
    public void deleteUserSalesByParameterMap(Map parMap) {
        this.sqlMapClient.delete("deleteUserSalesByParameterMap", parMap);
    }

    @Override
    public int getSearchDayByMap(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getSearchDayByMap", parMap);
    }
}
