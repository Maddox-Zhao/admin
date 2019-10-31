package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.OutDepAnalysisDao;
import com.huaixuan.network.biz.domain.storage.OutDepAnalysis;
import com.huaixuan.network.biz.query.QueryPage;

@Service("outDepAnalysisDao")
public class OutDepAnalysisDaoiBatis implements OutDepAnalysisDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@SuppressWarnings("unchecked")
	public QueryPage getOutDepAnalysis(Map outDepParam, int currentPage, int pageSize, boolean isPage) {
		OutDepAnalysis p = new OutDepAnalysis();
		p.setDateStart((String) outDepParam.get("gmtOutDepStart"));
		p.setDateEnd((String) outDepParam.get("gmtOutDepEnd"));

		QueryPage queryPage = new QueryPage(p);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if (isPage) {
			int count = (Integer) sqlMapClientTemplate.queryForObject("getOutDepAnalysisCount", outDepParam);
			queryPage.setTotalItem(count);

			if (count > 0) {
				outDepParam.put("startRow", queryPage.getPageFristItem());
				outDepParam.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getOutDepAnalysis", outDepParam));
			}
		} else {
			queryPage.setItems(sqlMapClientTemplate.queryForList("getOutDepAnalysis", outDepParam));
		}
		return queryPage;
	}

	public int getOutDepAnalysisCount(Map<String, String> parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getOutDepAnalysisCount", parMap);
	}

	@SuppressWarnings("unchecked")
	public List<OutDepAnalysis> getOutDepAnalysisTradeCount(Map outDepParam, int currentPage,
			int pageSize, boolean isPage) {
		if (isPage) {
			OutDepAnalysis p = new OutDepAnalysis();
			p.setDateStart((String) outDepParam.get("gmtOutDepStart"));
			p.setDateEnd((String) outDepParam.get("gmtOutDepEnd"));

			QueryPage queryPage = new QueryPage(p);
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);

			int count = (Integer) sqlMapClientTemplate.queryForObject("getOutDepAnalysisCount", outDepParam);
			queryPage.setTotalItem(count);

			if (count > 0) {
				outDepParam.put("startRow", queryPage.getPageFristItem());
				outDepParam.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				return sqlMapClientTemplate.queryForList("getOutDepAnalysisTradeCount", outDepParam);
			}
			return new ArrayList<OutDepAnalysis>(0);
		} else {
			return sqlMapClientTemplate.queryForList("getOutDepAnalysisTradeCount", outDepParam);
		}
	}
}
