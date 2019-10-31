package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.ExpressDistDao;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.express.query.ExpressDistQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @version 3.2.0
 */
@Repository("expressDistDao")
public class ExpressDistDaoiBatis implements ExpressDistDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

    public void addExpressDist(ExpressDist expressDist) throws Exception {
        this.sqlMapClient.insert("addExpressDist", expressDist);
    }

    /**
     * @param expressDist ExpressDist
     * @author songfy 2009/09/07
     */
    public void editExpressDist(ExpressDist expressDist) throws Exception {
        this.sqlMapClient.update("editExpressDist", expressDist);
    }

    public void removeExpressDist(Long expressDistId) throws Exception {
        this.sqlMapClient.delete("removeExpressDist", expressDistId);
    }

    public ExpressDist getExpressDist(Long expressDistId) throws Exception {
        return (ExpressDist) this.sqlMapClient.queryForObject("getExpressDist",
            expressDistId);
    }

    public List<ExpressDist> getExpressDists() throws Exception {
        return this.sqlMapClient.queryForList("getExpressDists", null);
    }

    public ExpressDist getExpressDistById(Long expressDistId) {
        if (expressDistId == null) {
            return null;
        }
        return (ExpressDist) this.sqlMapClient.queryForObject("getExpressDistById",
            expressDistId);
    }

    @SuppressWarnings("unchecked")
    public int removeExpressDistByRegion(Long expressId, String regionCodeStart,
                                         String regionCodeEnd) {
        if (expressId == null || StringUtil.isBlank(regionCodeStart)
            || StringUtil.isBlank(regionCodeEnd)) {
            return 0;
        }
        Map map = new HashMap();
        map.put("expressId", expressId);
        map.put("regionCodeStart", regionCodeStart);
        map.put("regionCodeEnd", regionCodeEnd);
        return this.sqlMapClient.delete("removeExpressDistByRegion", map);
    }

    public int getExpressDistCountByCond(Map map) {
        return (Integer) this.sqlMapClient.queryForObject("getExpressDistCountByCond",
            map);
    }

    @SuppressWarnings("unchecked")
	public QueryPage getExpressDistByCond(Map parMap, int currentPage, final int pageSize) {
    	QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getExpressDistCountByCond", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* ∑÷“≥≤È—Ø */
			queryPage.setItems(sqlMapClient.queryForList("getExpressDistByCond", parMap));
		}
		return queryPage;
    }

    @SuppressWarnings("unchecked")
    public List<ExpressDist> listExpressDistForTrade() {
        return this.sqlMapClient.queryForList("listExpressDistForTrade");
    }

    @SuppressWarnings("unchecked")
    public int getExpressDistByRegion(Long expressId, String regionCodeStart, String regionCodeEnd,
                                      String payment) {
        if (expressId == null || StringUtil.isBlank(regionCodeStart)
            || StringUtil.isBlank(regionCodeEnd)) {
            return 0;
        }
        Map map = new HashMap();
        map.put("expressId", expressId);
        map.put("regionCodeStart", regionCodeStart);
        map.put("regionCodeEnd", regionCodeEnd);
        map.put("payment", payment);
        return (Integer) this.sqlMapClient.queryForObject("getExpressDistByRegion",
            map);
    }

    @SuppressWarnings("unchecked")
    public int getCountByRegionCodeEnd(String regionCodeEnd, String payment) {
        if (StringUtil.isBlank(regionCodeEnd) || StringUtil.isBlank(payment)) {
            return 0;
        }
        Map map = new HashMap();
        map.put("regionCodeEnd", regionCodeEnd);
        map.put("payment", payment);
        return (Integer) this.sqlMapClient.queryForObject("getCountByRegionCodeEnd",
            map);
    }

    @SuppressWarnings("unchecked")
    public List<ExpressDist> listExpressDistByRegionCodeEnd(String regionCodeStart,
                                                            String regionCodeEnd, String payment, Long expressId) {
        if (StringUtil.isBlank(regionCodeStart) || StringUtil.isBlank(regionCodeEnd)
            || StringUtil.isBlank(payment)) {
            return null;
        }
        Map map = new HashMap();
        map.put("regionCodeStart", regionCodeStart);
        map.put("regionCodeEnd", regionCodeEnd);
        map.put("payment", payment);
        map.put("expressId", expressId);
        return this.sqlMapClient.queryForList("listExpressDistByRegionCodeEnd", map);
    }

    @SuppressWarnings("unchecked")
    public int getCountByFourForUpdate(String regionCodeStart, String regionCodeEnd,
                                       Long expressId, Long id, String payment) {
        if (StringUtil.isBlank(regionCodeStart) || StringUtil.isBlank(regionCodeEnd)
            || expressId == null || id == null || StringUtil.isBlank(payment)) {
            return 0;
        }
        Map map = new HashMap();
        map.put("regionCodeStart", regionCodeStart);
        map.put("regionCodeEnd", regionCodeEnd);
        map.put("expressId", expressId);
        map.put("id", id);
        map.put("payment", payment);
        return (Integer) this.sqlMapClient.queryForObject("getCountByFourForUpdate",
            map);
    }

    public int editExpressDistByFourCond(ExpressDist expressDist) {
        return this.sqlMapClient.update("editExpressDistByFourCond", expressDist);
    }

    public ExpressDist getExpressDistByRegionCodeEnd(String regionCodeStart, String regionCodeEnd,
                                                     String payment, Long expressId) {
        if (StringUtil.isBlank(regionCodeStart) || StringUtil.isBlank(regionCodeEnd)
            || StringUtil.isBlank(payment)) {
            return null;
        }
        Map map = new HashMap();
        map.put("regionCodeStart", regionCodeStart);
        map.put("regionCodeEnd", regionCodeEnd);
        map.put("payment", payment);
        map.put("expressId", expressId);
        return (ExpressDist) this.sqlMapClient.queryForObject(
            "getExpressDistByRegionCodeEnd", map);
    }

	public int bathUpdateStatus(List<Long> expressDistIds, String status) {
		Map map = new HashMap();
        map.put("ids", expressDistIds);
        map.put("status", status);
        return this.sqlMapClient.update("bathUpdateStatus", map);
	}
}
