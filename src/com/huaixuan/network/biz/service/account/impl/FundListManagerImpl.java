package com.huaixuan.network.biz.service.account.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.account.AccountManager;
import com.huaixuan.network.biz.service.account.FundListManager;

public class FundListManagerImpl implements FundListManager {
    @Autowired
    AccountManager accountManager;

    /**
     * 根据条件查看账户金额变动流水
     * @param searchMap
     * @param page
     * @return
     * @see com.hundsun.bible.facade.account.FundListManager#getAccountLogsByCondition(java.util.Map, com.hundsun.bible.Page)
     */
    public QueryPage getAccountLogsByUserId(Map<String, String> searchMap, int currentPage,
                                            int pageSize) {
        long userId = Long.parseLong(searchMap.get("userId"));
        Account buyerAccount = accountManager.getBuyerTransAccount(userId);
        if (buyerAccount != null) {
            searchMap.put("accountNo", buyerAccount.getAccountNo());
            return accountManager.getAccountLogsByUserId(searchMap, currentPage, pageSize);
        }
        return null;
    }

    /**
     * 商户账户资金变动表查�
     * @param searchMap
     * @param page
     * @return
     * @see com.hundsun.bible.facade.account.FundListManager#getMerchantAccountLogsByUserId(java.util.Map, com.hundsun.bible.Page)
     */
    //    public PageUtil<Map> getMerchantAccountLogsByUserId(Map<String, String> searchMap, Page page){
    ////        long userId = Long.parseLong(searchMap.get("userId"));
    ////        Account merchantAccount = null;
    ////        if(searchMap.get("accountSubType").equals("01")){//收款帐户
    ////            merchantAccount = accountManager.getSellerReceiveAccount(userId);
    ////        }else if(searchMap.get("accountSubType").equals("02")){//付款帐户
    ////            merchantAccount = accountManager.getSellerPayAccount(userId);
    ////        }
    ////        if(merchantAccount != null){
    ////            searchMap.put("accountNo", merchantAccount.getAccountNo());
    ////            return accountManager.getAccountLogsByUserId(searchMap, page);
    ////        }
    //        return null;
    //    }

}
