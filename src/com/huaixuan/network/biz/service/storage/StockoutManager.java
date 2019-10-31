package com.huaixuan.network.biz.service.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.Stockout;
import com.huaixuan.network.biz.domain.storage.query.StockoutQuery;
import com.huaixuan.network.biz.query.QueryPage;

public interface StockoutManager {

    /**
     *
     * @param parMap
     * @param page
     * @return
     */
    QueryPage getStockoutList(StockoutQuery stockoutQuery, int currentPage, int pageSize, boolean isPage);

    /**
     *
     * @param parMap
     * @return
     */
    boolean updateNotifyStatus(Map<String, Object> parMap);

    /**
     *
     * @param id
     * @return
     */
    Stockout getStockout(long id);
    
    
    List<Stockout> getStockoutList(Map parMap);
}
