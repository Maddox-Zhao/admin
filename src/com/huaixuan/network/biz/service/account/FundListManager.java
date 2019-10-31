package com.huaixuan.network.biz.service.account;

import java.util.Map;

import com.huaixuan.network.biz.query.QueryPage;

public interface FundListManager {
    /**
     * 根据条件查看账户金额变动流水
     * @param searchMap
     * @param page
     * @return
     */
    public QueryPage getAccountLogsByUserId(Map<String, String> searchMap, int currentPage,
                                            int pageSize);

//    /**
//     * 商户账户资金变动表查询
//     * @param searchMap
//     * @param page
//     * @return
//     */
//    public QueryPage getMerchantAccountLogsByUserId(Map<String, String> searchMap, int currentPage,
//                                                    int pageSize);
}