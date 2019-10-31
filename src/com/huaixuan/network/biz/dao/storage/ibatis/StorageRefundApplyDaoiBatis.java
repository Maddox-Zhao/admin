package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.storage.StorageRefundApplyDao;
import com.huaixuan.network.biz.domain.storage.StorageRefundApply;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("storageRefundApplyDao")
public class StorageRefundApplyDaoiBatis implements StorageRefundApplyDao {

	@Autowired
    private SqlMapClientTemplate sqlMapClient;

	@SuppressWarnings("unchecked")
    @Override
	public QueryPage getStorageRefundApplyListByCondition(Map parMap, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getStorageRefundApplyCountByCondition", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			
			queryPage.setItems(sqlMapClient.queryForList("getStorageRefundApplyListByCondition", parMap));
		}
		return queryPage;
	}

    @Override
	public void addStorageRefundApply(StorageRefundApply storageRefundApply) {
		this.sqlMapClient.insert("addStorageRefundApply", storageRefundApply);
	}

    @Override
	public int getStorageRefundApplyCountByRelationNum(String relationNum) {
		return (Integer)this.sqlMapClient.queryForObject("getStorageRefundApplyCountByRelationNum", relationNum);
	}

	/**
	 * @param storageId
	 * @return
	 */
	@Override
	public int getStorageRefundApplyCountByStorageId(String[] storageIds){
		    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("storageIds", storageIds);
			return (Integer)this.sqlMapClient.queryForObject("getStorageRefundApplyCountByStorageId",map);
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<StorageRefundApply> getStorageRefundApplyDetail(
			String relationNum) {
		return this.sqlMapClient.queryForList("getStorageRefundApplyDetail", relationNum);
	}

	@Override
	public StorageRefundApply getStorageRefundApplyById(Long id) {
		return (StorageRefundApply)this.sqlMapClient.queryForObject("getStorageRefundApplyById", id);
	}

	@Override
	public void updateStorageRefundApply(StorageRefundApply storageRefundApply) {
		this.sqlMapClient.update("updateStorageRefundApply", storageRefundApply);
	}

}
