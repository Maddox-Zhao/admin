package com.huaixuan.network.biz.service.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.query.QueryPage;

public interface WholesaleApplyManager {
//    long addWholesaleApply(WholesaleApply wholesaleApply);

    void editWholesaleApply(WholesaleApply wholesaleApply);

    void removeWholesaleApply(Long wholesaleApplyId);

    WholesaleApply getWholesaleApply(Long wholesaleApplyId);

    WholesaleApply getWholesaleApplyByTid(String tid);

    List<WholesaleApply> getWholesaleApplys();

    QueryPage getWholesaleApplysByParMap(Map parMap, int currentPage, int pageSize);

    int getCountWholesaleApplysByParMap(Map parMap);
}
