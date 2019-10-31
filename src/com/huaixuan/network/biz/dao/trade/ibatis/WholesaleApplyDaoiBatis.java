package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.WholesaleApplyDao;
import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("wholesaleApplyDao")
public class WholesaleApplyDaoiBatis implements WholesaleApplyDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public long addWholesaleApply(WholesaleApply wholesaleApply) throws Exception {
        return (Long) this.sqlMapClient.insert("addWholesaleApply", wholesaleApply);
    }

    @Override
    public void editWholesaleApply(WholesaleApply wholesaleApply) throws Exception {
        this.sqlMapClient.update("editWholesaleApply", wholesaleApply);
    }

    @Override
    public void removeWholesaleApply(Long wholesaleApplyId) throws Exception {
        this.sqlMapClient.delete("removeWholesaleApply", wholesaleApplyId);
    }

    @Override
    public WholesaleApply getWholesaleApply(Long wholesaleApplyId) throws Exception {
        return (WholesaleApply) this.sqlMapClient.queryForObject("getWholesaleApply",
            wholesaleApplyId);
    }

    @Override
    public WholesaleApply getWholesaleApplyByTid(String tid) throws Exception {
        return (WholesaleApply) this.sqlMapClient.queryForObject("getWholesaleApplyByTid", tid);
    }

    @Override
    public List<WholesaleApply> getWholesaleApplys() throws Exception {
        return this.sqlMapClient.queryForList("getWholesaleApplys", null);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage getWholesaleApplysByParMap(Map parMap, int currentPage, int pageSize)
                                                                                          throws Exception {
        QueryPage queryPage = new QueryPage(new WholesaleApply());
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient
            .queryForObject("getCountWholesaleApplysByParMap", parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("getWholesaleApplysByParMap", parMap));
        }
        return queryPage;
    }

    @Override
    public int getCountWholesaleApplysByParMap(Map parMap) throws Exception {
        return (Integer) this.sqlMapClient
            .queryForObject("getCountWholesaleApplysByParMap", parMap);
    }
}
