package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.ExpressAnalysisDao;
import com.huaixuan.network.biz.domain.express.ExpressAnalysis;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("expressAnalysisDao")
public class ExpressAnalysisDaoiBatis implements ExpressAnalysisDao{
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public int getExpressAnalysisByExpCount(Map parMap){
		return (Integer) this.sqlMapClient.queryForObject("getExpressAnalysisByExpCount",
	            parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getExpressAnalysisByExp(Map parMap, int currentPage, int pageSize, boolean isPage){
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClient.queryForObject("getExpressAnalysisByExpCount", parMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClient.queryForList("getExpressAnalysisByExp", parMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getExpressAnalysisByExp", parMap));
		}
		return queryPage;
	}

	public int getExpressAnalysisByProCount(Map parMap){
		return (Integer) this.sqlMapClient.queryForObject("getExpressAnalysisByProCount",
	            parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getExpressAnalysisByPro(Map parMap, int currentPage, int pageSize, boolean isPage){
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClient.queryForObject("getExpressAnalysisByProCount", parMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* 分页查询  */
				queryPage.setItems(sqlMapClient.queryForList("getExpressAnalysisByPro", parMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getExpressAnalysisByPro", parMap));
		}
		return queryPage;
	}
}
