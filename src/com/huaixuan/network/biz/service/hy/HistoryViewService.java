package com.huaixuan.network.biz.service.hy;

import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.query.QueryPage;

public interface HistoryViewService {

	/**
	 * @param 
	 * @return
	 */
	public QueryPage getHistoryViewByConditionWithPage(HistoryView historyView, int currPage, int pageSize);
	
	
	
}
