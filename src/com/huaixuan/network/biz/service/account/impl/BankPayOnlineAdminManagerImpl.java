package com.huaixuan.network.biz.service.account.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.account.BankPayOnlineDao;
import com.huaixuan.network.biz.domain.base.payment.BaseBankPayOnline;
import com.huaixuan.network.biz.domain.counter.query.PayOnlineSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.account.BankPayOnlineAdminManager;

/**
 * 银行订单后台服务接口
 * 
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManagerImpl.java,v 0.1 2010-6-5 上午10:45:11
 *          guoyj Exp $
 */
@Service("bankPayOnlineAdminManager")
public class BankPayOnlineAdminManagerImpl implements BankPayOnlineAdminManager {

    @Autowired
    BankPayOnlineDao bankPayOnlineDao;

	/**
	 * 鏍规嵁鎼滅储鏉′欢鏌ユ壘婊¤冻鏉′欢鐨勬敮浠樻祦姘??
	 *
	 * @param searchMap
	 * @param page
	 * @return
	 * @see com.hundsun.bible.facade.account.BankPayOnlineAdminManager#searchBankPayOnlineListByCondition(java.util.Map,
	 *      com.hundsun.bible.Page)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QueryPage searchBankPayOnlineListByCondition(
			PayOnlineSearchQuery payOnlineSearchQuery, int currentPage,
			int pageSize) {
		QueryPage queryPage = new QueryPage(payOnlineSearchQuery);
		Map pramas = queryPage.getParameters();
		Integer count = bankPayOnlineDao
				.searchBankPayOnlineListByConditionCount(pramas);

        if (count != null && count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currentPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<BaseBankPayOnline> accList = bankPayOnlineDao
					.searchBankPayOnlineListByCondition(pramas);
			if (accList != null && accList.size() > 0) {
				queryPage.setItems(accList);
			}
		}
		return queryPage;
	}

}
