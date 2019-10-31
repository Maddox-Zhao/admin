package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.StorageCheckDetailDao;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Repository("storageCheckDetailDao")
public class StorageCheckDetailDaoiBatis implements StorageCheckDetailDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@Override
    public void addStorageCheckDetail(StorageCheckDetail storageCheckDetail) throws Exception {
        this.sqlMapClient.insert("addStorageCheckDetail", storageCheckDetail);
    }

    @Override
    public void editStorageCheckDetail(StorageCheckDetail storageCheckDetail) throws Exception {
        this.sqlMapClient.update("editStorageCheckDetail", storageCheckDetail);
    }

    @Override
    public void removeStorageCheckDetail(Long storageCheckDetailId) throws Exception {
        this.sqlMapClient.delete("removeStorageCheckDetail", storageCheckDetailId);
    }

    @Override
    public StorageCheckDetail getStorageCheckDetail(Long storageCheckDetailId) throws Exception {
        return (StorageCheckDetail) this.sqlMapClient.queryForObject(
            "getStorageCheckDetail", storageCheckDetailId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StorageCheckDetail> getStorageCheckDetails() throws Exception {
        return this.sqlMapClient.queryForList("getStorageCheckDetails", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryPage getStorageCheckDetailsByParameterMap(Map parameterMap, int currentPage, int pageSize, boolean isPage) {
    	QueryPage queryPage = new QueryPage(parameterMap);

		if(isPage){
			int count = (Integer) sqlMapClient.queryForObject("getStorageCheckDetailsCountByParameterMap", parameterMap);
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parameterMap.put("startRow", queryPage.getPageFristItem());
				parameterMap.put("endRow", queryPage.getPageLastItem());
				/* ∑÷“≥≤È—Ø  */
				queryPage.setItems(sqlMapClient.queryForList("getStorageCheckDetailsByParameterMap", parameterMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getStorageCheckDetailsByParameterMap", parameterMap));
		}
		return queryPage;
    }

    @Override
    @SuppressWarnings("unchecked")
	public List<StorageCheckDetail> getAllStorageCheckDetailsByParameterMap(
			Map parameterMap) {
    	return this.sqlMapClient.queryForList("getStorageCheckDetailsByParameterMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
    @Override
    public int getStorageCheckDetailsCountByParameterMap(Map parameterMap) {
        Integer i = (Integer) this.sqlMapClient.queryForObject(
            "getStorageCheckDetailsCountByParameterMap", parameterMap);
        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public int sumStorageCheckDetailByParameterMap(Map parameterMap) {
        Integer i = (Integer) this.sqlMapClient.queryForObject(
            "sumStorageCheckDetailByParameterMap", parameterMap);
        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StorageCheckDetail> getCheckDetailCountByMap(Map parameterMap) {
        return this.sqlMapClient.queryForList("getCheckDetailCountByMap", parameterMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<StockAge> getStockAgeAnalysisDataListsByParameterMap(Map parMap)
    {
    	 return this.sqlMapClient.queryForList("getStockAgeAnalysisDataListsByParameterMap", parMap);
    }

	@Override
	public List<StorageCheckDetail> getStorageCheckDetailsByParameterMap(
			Map parameterMap) {
		return this.sqlMapClient.queryForList("getStorageCheckDetailsByParameterMap", parameterMap);
	}

}
