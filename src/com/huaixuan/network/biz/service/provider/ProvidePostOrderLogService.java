/**
 * 
 */
package com.huaixuan.network.biz.service.provider;

import com.huaixuan.network.biz.domain.provider.ProvidePostOrderLog;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @author TT
 * 
 */
public interface ProvidePostOrderLogService {
	/**
	 * @Description: providerLog
	 * @date 2019-1-14
	 */
	public QueryPage getProviderLogConditionPage(ProvidePostOrderLog providerLog, int currPage, int pageSize);
}
