package com.huaixuan.network.biz.dao.hy;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.hy.HistoryView;


public interface HistoryViewDao {
	
	/**
	 * @return
	 */
	public Integer getHistoryViewByConditionWithPageCount(Map parMap);
	
	/**
	 * @return
	 */
	public List<HistoryView> getHistoryViewByConditionWithPage(Map parMap);
	
	
}
