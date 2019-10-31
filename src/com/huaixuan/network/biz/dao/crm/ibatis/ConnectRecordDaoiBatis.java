package com.huaixuan.network.biz.dao.crm.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.crm.ConnectRecordDao;
import com.huaixuan.network.biz.domain.crm.ConnectRecord;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * Î¬»¤¼ÇÂ¼
 * @author  2010/08/04
 *
 */
@Repository("connectRecordDao")
public class ConnectRecordDaoiBatis implements ConnectRecordDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    @Override
    public Long addConnectRecord(ConnectRecord connectRecord) {
        return (Long) this.sqlMapClient.insert("addConnectRecord", connectRecord);
    }

    @Override
    public ConnectRecord getConnectRecordById(Long id) {
        return (ConnectRecord) this.sqlMapClient.queryForObject("getConnectRecordById", id);
    }

    @Override
    public int listConnectRecordByParameterCount(Map parMap) {
        return (Integer) this.sqlMapClient.queryForObject("listConnectRecordByParameterCount",
            parMap);
    }

    @SuppressWarnings("unchecked")
	@Override
    public QueryPage listConnectRecordByParameter(Map parMap, int currentPage, int pageSize) {
        QueryPage queryPage = new QueryPage(parMap);
        queryPage.setCurrentPage(currentPage);
        queryPage.setPageSize(pageSize);

        int count = (Integer) sqlMapClient.queryForObject("listConnectRecordByParameterCount",
            parMap);
        queryPage.setTotalItem(count);

        if (count > 0) {
            parMap.put("startRow", queryPage.getPageFristItem());
            parMap.put("endRow", queryPage.getPageLastItem());
            queryPage.setItems(sqlMapClient.queryForList("listConnectRecordByParameter", parMap));
        }
        return queryPage;
    }

    @Override
    public int updateConnectRecordById(ConnectRecord connectRecord) {
        return this.sqlMapClient.update("updateConnectRecordById", connectRecord);
    }

    @Override
    public int updateConnectRecordStatusById(ConnectRecord connectRecord) {
        return this.sqlMapClient.update("updateConnectRecordStatusById", connectRecord);
    }

    @Override
    public List<ConnectRecord> ListConnectRecordByUserId(Map parMap) {
        return this.sqlMapClient.queryForList("listConnectRecordByUserId", parMap);
    }
}
