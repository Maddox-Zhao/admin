package com.huaixuan.network.biz.dao.express;

import java.util.Map;

import com.huaixuan.network.biz.query.QueryPage;

public interface ExpressAnalysisDao {
	
	int getExpressAnalysisByExpCount(Map parMap);

	QueryPage getExpressAnalysisByExp(Map parMap, int currentPage, final int pageSize, boolean isPage);

	int getExpressAnalysisByProCount(Map parMap);

	QueryPage getExpressAnalysisByPro(Map parMap, int currentPage, final int pageSize, boolean isPage);
}
