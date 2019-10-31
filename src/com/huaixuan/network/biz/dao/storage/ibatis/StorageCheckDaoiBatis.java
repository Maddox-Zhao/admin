package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.StorageCheckDao;
import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Repository("storageCheckDao")
public class StorageCheckDaoiBatis implements StorageCheckDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
    public Long addStorageCheck(StorageCheck storageCheck) throws Exception {
        return (Long) this.sqlMapClient.insert("addStorageCheck", storageCheck);
    }

	@Override
    public void editStorageCheck(StorageCheck storageCheck) throws Exception {
        this.sqlMapClient.update("editStorageCheck", storageCheck);
    }

	@Override
    public void removeStorageCheck(Long storageCheckId) throws Exception {
        this.sqlMapClient.delete("removeStorageCheck", storageCheckId);
    }

	@Override
    public StorageCheck getStorageCheck(Long storageCheckId) throws Exception {
        return (StorageCheck) this.sqlMapClient.queryForObject("getStorageCheck",
            storageCheckId);
    }

	@Override
	@SuppressWarnings("unchecked")
    public List<StorageCheck> getStorageChecks() throws Exception {
        return this.sqlMapClient.queryForList("getStorageChecks", null);
    }

	@Override
	@SuppressWarnings("unchecked")
    public int getStorageChecksByCountParameterMap(Map parameterMap) {
        try {
            return (Integer) this.sqlMapClient.queryForObject(
                "getStorageChecksByCountParameterMap", parameterMap);
        } catch (Exception e) {
//            log.error(e.getMessage());
            return 0;
        }
    }

	@Override
	@SuppressWarnings("unchecked")
    public QueryPage getStorageChecksByParameterMap(Map parameterMap, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parameterMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getStorageChecksByCountParameterMap", parameterMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parameterMap.put("startRow", queryPage.getPageFristItem());
			parameterMap.put("endRow", queryPage.getPageLastItem());
			/* ∑÷“≥≤È—Ø  */
			queryPage.setItems(sqlMapClient.queryForList("getStorageChecksByParameterMap", parameterMap));
		}
		return queryPage;
    }

	@Override
    public Long getDepFirstIdByCheckId(Long checkId) {
        return (Long) this.sqlMapClient.queryForObject("getDepFirstIdByCheckId",
            checkId);
    }
}
