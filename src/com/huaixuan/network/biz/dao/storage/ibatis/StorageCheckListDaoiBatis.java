package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.StorageCheckListDao;
import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.domain.storage.query.StorageCheckQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * bibleUtil auto code generation)
 * @version 3.2.0
 */
@Repository("storageCheckListDao")
public class StorageCheckListDaoiBatis implements StorageCheckListDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
    public void addStorageCheckList(StorageCheckList storageCheckList) throws Exception {
        this.sqlMapClient.insert("addStorageCheckList", storageCheckList);
    }

	@Override
    public void editStorageCheckList(StorageCheckList storageCheckList) throws Exception {
        this.sqlMapClient.update("editStorageCheckList", storageCheckList);
    }

	@Override
    public void removeStorageCheckList(Long storageCheckListId) throws Exception {
        this.sqlMapClient.delete("removeStorageCheckList", storageCheckListId);
    }

	@Override
    public StorageCheckList getStorageCheckList(Long storageCheckListId) throws Exception {
        return (StorageCheckList) this.sqlMapClient.queryForObject(
            "getStorageCheckList", storageCheckListId);
    }

	@Override
	@SuppressWarnings("unchecked")
    public List<StorageCheckList> getStorageCheckLists() throws Exception {
        return this.sqlMapClient.queryForList("getStorageCheckLists", null);
    }

	@Override
	@SuppressWarnings("unchecked")
    public int getStorageCheckListsByCountParameterMap(Map parameterMap) {
        return (Integer) this.sqlMapClient.queryForObject(
            "getStorageCheckListsByCountParameterMap", parameterMap);
    }

	@Override
	@SuppressWarnings("unchecked")
    public QueryPage getStorageCheckListsByParameterMap(StorageCheckQuery storageCheckQuery, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(storageCheckQuery);
		Map parameterMap = queryPage.getParameters();

		if(isPage){
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			
			int count = (Integer) sqlMapClient.queryForObject("getStorageCheckListsByCountParameterMap", parameterMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parameterMap.put("startRow", queryPage.getPageFristItem());
				parameterMap.put("endRow", queryPage.getPageLastItem());
				/* ∑÷“≥≤È—Ø  */
				queryPage.setItems(sqlMapClient.queryForList("getStorageCheckListsByParameterMap", parameterMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getStorageCheckListsByParameterMap", parameterMap));
		}
		return queryPage;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<StorageCheckList> getAllStorageCheckListsByParameterMap(Map parameterMap) {
		return this.sqlMapClient.queryForList("getStorageCheckListsByParameterMap", parameterMap);
	}

	@Override
	public List<StorageCheckList> getStorageCheckListsByParameterMap(Map parameterMap) {
		return sqlMapClient.queryForList("getStorageCheckListsByParameterMap", parameterMap);
	}

}
