package com.huaixuan.network.biz.service.account;

import com.huaixuan.network.biz.domain.counter.query.PayOnlineSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 银行订单后台服务接口
 *
 * @author guoyj
 * @version $Id: BankPayOnlineAdminManager.java,v 0.1 2010-6-7 上午09:28:47 guoyj
 *          Exp $
 */
public interface BankPayOnlineAdminManager {
	/**
	 * 根据搜索条件查找满足条件的支付流水
	 *
	 * @return
	 */
	public QueryPage searchBankPayOnlineListByCondition(
			PayOnlineSearchQuery payOnlineSearchQuery, int currentPage,
			int pageSize);

}