package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.PayRecordDao;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.query.TradeListQuery;

@Service("payRecordDao")
public class PayRecordDaoiBatis implements PayRecordDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public void addPayRecord(PayRecord payRecord) {
        sqlMapClient.insert("addPayRecord", payRecord);
    }

    @Override
    public void editPayRecord(PayRecord payRecord) {
        sqlMapClient.update("editPayRecord", payRecord);
    }

    @Override
    public void removePayRecord(Long payRecordId) {
        sqlMapClient.delete("removePayRecord", payRecordId);
    }

    @Override
    public PayRecord getPayRecord(Long payRecordId) {
        return (PayRecord) sqlMapClient.queryForObject("getPayRecord", payRecordId);
    }

    @Override
    public List<PayRecord> getPayRecords() {
        return sqlMapClient.queryForList("getPayRecords", null);
    }

    @Override
    public void updatePayRecordByNotify(PayRecord payRecord) {
        sqlMapClient.update("updatePayRecordByNotify", payRecord);
    }

    @Override
    public PayRecord getPayRecordByTid(String tid) {
        return (PayRecord) sqlMapClient.queryForObject("getPayRecordByTid", tid);
    }

    @Override
    public List<PayRecord> getPayRecordLists(Map parMap) {
        return sqlMapClient.queryForList("getPayRecordLists", parMap);
    }

    @Override
    public int getPayRecordListsCount(Map parMap) {
        return (Integer) sqlMapClient.queryForObject("getPayRecordListsCount", parMap);
    }

    public PayRecord getPayAmountByPlatform(TradeListQuery tradeListQuery) {
        return (PayRecord) this.sqlMapClient.queryForObject("getPayAmountByPlatform",
        		tradeListQuery);
    }

    public int getCodPayRecordListsCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("getCodPayRecordListsCount",
            parMap);
    }

    public PayRecord getPayAmountByCod(Map parMap) {
        return (PayRecord) this.sqlMapClient.queryForObject("getPayAmountByCod", parMap);
    }

    //	    public QueryPage getPayRecordListsByCod(Map parMap, int currentPage, int pageSize) {
    //	        QueryPage queryPage = new QueryPage(new PayRecord());
    //	        queryPage.setCurrentPage(currentPage);
    //	        queryPage.setPageSize(pageSize);
    //
    //	        int count = (Integer) sqlMapClient.queryForObject("getSaleGiftGoodsInstanceCount", parMap);
    //	        queryPage.setTotalItem(count);
    //
    //	        if (count > 0) {
    //	            parMap.put("startRow", String.valueOf(queryPage.getPageFristItem()));
    //	            parMap.put("endRow", String.valueOf(queryPage.getPageLastItem()));
    //	            queryPage.setItems(sqlMapClient.queryForList("getPayRecordListsByCod", parMap));
    //	        }
    //	        return queryPage;
    //	    }
}
