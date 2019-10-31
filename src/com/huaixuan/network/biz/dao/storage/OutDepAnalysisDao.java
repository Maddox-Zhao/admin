package com.huaixuan.network.biz.dao.storage;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.storage.OutDepAnalysis;
import com.huaixuan.network.biz.query.QueryPage;

public interface OutDepAnalysisDao {
	QueryPage getOutDepAnalysis(Map outDepParam, int currentPage, int pageSize, boolean isPage);

	int getOutDepAnalysisCount(Map<String, String> parMap);

	List<OutDepAnalysis> getOutDepAnalysisTradeCount(Map parMap, int currentPage, int pageSize,
			boolean isPage);
}
