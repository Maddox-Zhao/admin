package com.huaixuan.network.biz.service.account;

import java.util.Map;

import com.huaixuan.network.biz.query.QueryPage;

public interface FundListManager {
    /**
     * ���������鿴�˻����䶯��ˮ
     * @param searchMap
     * @param page
     * @return
     */
    public QueryPage getAccountLogsByUserId(Map<String, String> searchMap, int currentPage,
                                            int pageSize);

//    /**
//     * �̻��˻��ʽ�䶯���ѯ
//     * @param searchMap
//     * @param page
//     * @return
//     */
//    public QueryPage getMerchantAccountLogsByUserId(Map<String, String> searchMap, int currentPage,
//                                                    int pageSize);
}