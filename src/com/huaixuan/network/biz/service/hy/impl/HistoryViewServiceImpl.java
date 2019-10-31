package com.huaixuan.network.biz.service.hy.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.hy.HistoryViewDao;
import com.huaixuan.network.biz.domain.hy.HistoryView;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.hy.HistoryViewService;

@Service("historyViewService")
public class HistoryViewServiceImpl implements HistoryViewService {

	@Autowired
	private HistoryViewDao historyViewDao;

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getHistoryViewByConditionWithPage(HistoryView historyView, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(historyView);
		Map pramas = queryPage.getParameters();

		Integer count = historyViewDao.getHistoryViewByConditionWithPageCount(pramas);

		if (count !=null && count.intValue() > 0) {

			queryPage.setCurrentPage(currPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			List<HistoryView> historyViewList = historyViewDao.getHistoryViewByConditionWithPage(pramas);
			if (historyViewList != null && historyViewList.size() > 0) {
				queryPage.setItems(historyViewList);
			}
		}
		return queryPage;
	}

}
