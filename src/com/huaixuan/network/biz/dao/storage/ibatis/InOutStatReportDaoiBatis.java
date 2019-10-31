package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.InOutStatReportDao;
import com.huaixuan.network.biz.domain.storage.InOutStatReport;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Service("inOutStatReportDao")
public class InOutStatReportDaoiBatis implements InOutStatReportDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public void addInOutStatReport(InOutStatReport inOutStatReport) throws Exception {
        this.sqlMapClient.insert("addInOutStatReport", inOutStatReport);
    }

    public void editInOutStatReport(InOutStatReport inOutStatReport) throws Exception {
        this.sqlMapClient.update("editInOutStatReport", inOutStatReport);
    }

    public void removeInOutStatReport(Long inOutStatReportId) throws Exception {
        this.sqlMapClient.delete("removeInOutStatReport", inOutStatReportId);
    }

    public InOutStatReport getInOutStatReport(Long inOutStatReportId) throws Exception {
        return (InOutStatReport) this.sqlMapClient.queryForObject("getInOutStatReport",
            inOutStatReportId);
    }

    public List<InOutStatReport> getInOutStatReports() throws Exception {
        return this.sqlMapClient.queryForList("getInOutStatReports", null);
    }

    public int getInOutStatReportCountByMap(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getInOutStatReportCountByMap", parMap);
    }

    @SuppressWarnings("unchecked")
	public QueryPage getInOutStatReportListByMap(Map parMap, int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("getInOutStatReportCountByMap", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            /* ∑÷“≥≤È—Ø  */
            queryPage.setItems(sqlMapClient.queryForList("getInOutStatReportListByMap", parMap));
        }
        return queryPage;
    }

    @Override
    public List<InOutStatReport> getInOutStatReportListByMap(Map parMap) {
        return this.sqlMapClient.queryForList("getInOutStatReportListByMap", parMap);
    }
}
