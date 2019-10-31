package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.StorageDao;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.MoveStorageQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 *
 * @version 3.2.0
 */
@Service("storageDao")
public class StorageDaoiBatis implements StorageDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Long addStorage(Storage storage) {
		return (Long) sqlMapClientTemplate.insert("addStorage", storage);
	}

	@Override
	public void editStorage(Storage storage) {
		sqlMapClientTemplate.update("editStorage", storage);
	}

	@Override
	public void removeStorage(Long storageId) {
		sqlMapClientTemplate.delete("removeStorage", storageId);
	}

	@Override
	public Storage getStorage(Long storageId) {
		return (Storage) sqlMapClientTemplate.queryForObject("getStorage", storageId);
	}

	/**
	 * getStorages all
	 *
	 * @return @
	 * @see com.hundsun.bible.dao.ios.StorageDao#getStorages()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> getStorages() {
		return sqlMapClientTemplate.queryForList("getStorages", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> getStorageByParameterMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getStorageByParameterMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> getStorageAndCheckDetailByParameterMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getStorageAndCheckDetailByParameterMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int sumStorageByParameterMap(Map parameterMap) {
		Integer sum = (Integer) sqlMapClientTemplate.queryForObject("sumStorageByParameterMap", parameterMap);
		if (sum == null) {
			return 0;
		} else {
			return sum.intValue();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Storage sumStorageResultForCheck(Map parameterMap) {
		return (Storage) sqlMapClientTemplate.queryForObject("sumStorageResultForCheck", parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getStoragesByCondition(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(storageQuery);
		Map pramas = queryPage.getParameters();
		if(storageQuery.getDepfirstIds() != null && storageQuery.getDepfirstIds().size() > 0){
			pramas.put("depfirstIds", storageQuery.getDepfirstIds());
		}

	    if(storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("goodsCodeAsc")) {
	    	pramas.put("orderBy", "order by gi.code asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("goodsCodeDesc")) {
	    	pramas.put("orderBy", "order by gi.code desc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("supplierIdAsc")) {
	    	pramas.put("orderBy", "order by st.supplier_id asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("supplierIdDesc")) {
	    	pramas.put("orderBy", "order by st.supplier_id desc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("storageNumSumAsc")) {
	    	pramas.put("orderBy", "order by sum(st.storage_num) asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("storageNumSumDesc")) {
	    	pramas.put("orderBy", "order by sum(st.storage_num) desc");
	    }


		if(isPage){
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);

			int count = (Integer) sqlMapClientTemplate.queryForObject("getStoragesByConditionCount", pramas);
			queryPage.setTotalItem(count);

			if (count > 0) {
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getStoragesByCondition", pramas));
			}
		}else{
			queryPage.setItems(sqlMapClientTemplate.queryForList("getStoragesByCondition", pramas));
		}
		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getStorageListByMap(StorageQuery storageQuery, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(storageQuery);
		Map pramas = queryPage.getParameters();
		if(storageQuery.getDepfirstIds() != null && storageQuery.getDepfirstIds().size() > 0){
			pramas.put("depfirstIds", storageQuery.getDepfirstIds());
		}

	    if(storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("goodsCodeAsc")) {
	    	pramas.put("orderBy", "order by gi.code asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("goodsCodeDesc")) {
	    	pramas.put("orderBy", "order by gi.code desc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("supplierIdAsc")) {
	    	pramas.put("orderBy", "order by st.supplier_id asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("supplierIdDesc")) {
	    	pramas.put("orderBy", "order by st.supplier_id desc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("storageNumSumAsc")) {
	    	pramas.put("orderBy", "order by sum(st.storage_num) asc");
	    } else if (storageQuery.getOrderBy() != null && storageQuery.getOrderBy().equals("storageNumSumDesc")) {
	    	pramas.put("orderBy", "order by sum(st.storage_num) desc");
	    }

		if(isPage){
			queryPage.setCurrentPage(currentPage);
			queryPage.setPageSize(pageSize);
			int count = (Integer) sqlMapClientTemplate.queryForObject("getStoragesByMapCount", pramas);
			queryPage.setTotalItem(count);

			if (count > 0) {
				pramas.put("startRow", queryPage.getPageFristItem());
				pramas.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getStorageListByMap", pramas));
			}
		}else{
			queryPage.setItems(sqlMapClientTemplate.queryForList("getStorageListByMap", pramas));
		}

		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getStoragesWithMove(MoveStorageQuery moveStorageQuery, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(moveStorageQuery);
		Map map = queryPage.getParameters();
		map.put("storageids", moveStorageQuery.getStorageids());
		map.put("depfirstIds", moveStorageQuery.getDepfirstIds());
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getStoragesCountWithMove", map);
		queryPage.setTotalItem(count);

		if (count > 0) {
			map.put("startRow", queryPage.getPageFristItem());
			map.put("endRow", queryPage.getPageLastItem());
			/* 分页查询 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getStoragesWithMove", map));
		}
		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getStoragesWithAll(Storage storage, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(storage);
		Map paraMap = queryPage.getParameters();
		paraMap.put("storageids", storage.getStorageids());
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getStoragesCountWithAll", paraMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			paraMap.put("startRow", queryPage.getPageFristItem());
			paraMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getStoragesWithAll", paraMap));
		}
		return queryPage;
	}

	@Override
	public int editStorageExistNum(Storage storage) {
		return (Integer) sqlMapClientTemplate.update("editStorageExistNum", storage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutDepositoryStorage> getOutStorageList(Map mapSearch) {
		return (List<OutDepositoryStorage>) sqlMapClientTemplate.queryForList("getOutStorageList", mapSearch);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateStorageStorageNumById(Long storageNum, Long id) {
		Map map = new HashMap();
		map.put("storageNum", storageNum);
		map.put("id", id);
		return sqlMapClientTemplate.update("updateStorageStorageNumById", map);
	}

	/*
	 * 锛堥潪 Javadoc锛??
	 *
	 * @see com.hundsun.bible.dao.ios.StorageDao#updateStorageStoNumByMap(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateStorageStoNumByMap(Map parameterMap) {
		return sqlMapClientTemplate.update("updateStorageStoNumByMap", parameterMap);
	}

	/*
	 * 锛堥潪 Javadoc锛??
	 *
	 * @see com.hundsun.bible.dao.ios.StorageDao#getStorageByMap(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Storage getStorageByMap(Map parameterMap) {
		return (Storage) sqlMapClientTemplate.queryForObject("getStorageByMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> getStorageListsByMap(Map parameterMap) {
		return sqlMapClientTemplate.queryForList("getStorageListsByMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getGoodsInstanceIdsByLocId(Long locId) {
		return sqlMapClientTemplate.queryForList("getGoodsInstanceIdsByLocId", locId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutDepositoryStorage> listOutDetailForDisByDetailId(Long outDetailId) {
		if (outDetailId == null) {
			return null;
		}
		return sqlMapClientTemplate.queryForList("listOutDetailForDisByDetailId", outDetailId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Storage> sumStorageCostByDepid(Long depId) {
		return sqlMapClientTemplate.queryForList("sumStorageCostByDepid", depId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Storage> getDataForStorageOnce() {
		return sqlMapClientTemplate.queryForList("getDataForStorageOnce");
	}

	@Override
	public Storage getStorageByGoodsInstanceId(Long goodsInstanceId) {
		return (Storage) sqlMapClientTemplate.queryForObject("getStorageByGoodsInstanceId", goodsInstanceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage searchZeroStock(Map condition, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(condition);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClientTemplate.queryForObject("searchZeroStockCount", condition);
			queryPage.setTotalItem(count);

			if (count > 0) {
				condition.put("startRow", queryPage.getPageFristItem());
				condition.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				queryPage.setItems(sqlMapClientTemplate.queryForList("searchZeroStock", condition));
			}
		}else{
			queryPage.setItems(sqlMapClientTemplate.queryForList("searchZeroStock", condition));
		}

		return queryPage;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hundsun.bible.dao.ios.StorageDao#getZeroStorageByParameterMap(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Storage> getZeroStorageByParameterMap(Map<String, String> parMap) {
		return sqlMapClientTemplate.queryForList("getZeroStorageByParameterMap", parMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int getStoragesCountWithMove(Map condition) {
		return (Integer) sqlMapClientTemplate.queryForObject("getStoragesCountWithMove", condition);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Storage> getStorageByIds(Map condition) {
		return sqlMapClientTemplate.queryForList("getStorageByIds", condition);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Storage> getStorageWithTrade(Long goodsInstanceId, boolean showAllStorageNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("goodsInstanceId", goodsInstanceId);
		param.put("showAllStorageNum", showAllStorageNum);
		return sqlMapClientTemplate.queryForList("getStorageWithTrade", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getRefundStoragesByMap(Map parMap, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClientTemplate.queryForObject("getRefundStoragesCountByMap", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询 */
			queryPage.setItems(sqlMapClientTemplate.queryForList("getRefundStoragesByMap", parMap));
		}
		return queryPage;
	}

	@Override
	public int getRefundStoragesCountByMap(Map<String, String> parMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("getRefundStoragesCountByMap", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long sumStorageByGoodsInstanceId(long goodsInstanceId, Long depFirstId) {
		try {
			Map parm = new HashMap();
			parm.put("goodsInstanceId", goodsInstanceId);
			parm.put("depFirstId", depFirstId);
			return (Long) sqlMapClientTemplate.queryForObject("sumStorageByGoodsInstanceId", parm);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long sumExistNumByGoodsInstanceId(long goodsInstanceId, Long depFirstId) {
		try {
			Map parm = new HashMap();
			parm.put("goodsInstanceId", goodsInstanceId);
			parm.put("depFirstId", depFirstId);
			return (Long) sqlMapClientTemplate.queryForObject("sumExistNumByGoodsInstanceId", parm);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getStockAgeByCondition(Map condition, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(condition);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			int count = (Integer) sqlMapClientTemplate.queryForObject("getStockAgeByConditionCount", condition);
			queryPage.setTotalItem(count);

			if (count > 0) {
				condition.put("startRow", queryPage.getPageFristItem());
				condition.put("endRow", queryPage.getPageLastItem());
				/* 分页查询 */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getStockAgeByCondition", condition));
			}
		}else{
			queryPage.setItems(sqlMapClientTemplate.queryForList("getStockAgeByCondition", condition));
		}

		return queryPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Storage getStorageAmountByCondition(Map condition) {
		return (Storage) sqlMapClientTemplate.queryForObject("getStorageAmountByCondition", condition);
	}

	@Override
	public Double getStoragePriceById(long id) {
		Double reprice = (Double) sqlMapClientTemplate.queryForObject("getStoragePriceById", id);
		if (reprice == null) {
			reprice = 0.00;
		}
		return reprice;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> getGoodsInstanceList() {
		return sqlMapClientTemplate.queryForList("getStockGoodsInstanceIds");
	}

	@Override
	public long getStorageAgeCount() {
		return (Long) sqlMapClientTemplate.queryForObject("getStorageAgeCount");
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getStorageNumBySend(Map parameterMap) {
		return (Integer) sqlMapClientTemplate.queryForObject("getStorageNumBySend", parameterMap);
	}

	@Override
	public List<Storage> getRefundStoragesByMap(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getRefundStoragesByMap", parMap);
	}

	@Override
	public List<Storage> getStoragesByCondition(Map parMap) {
		return this.sqlMapClientTemplate.queryForList("getStoragesByCondition", parMap);
	}

	@Override
	public Long getLeftStorageNumWithLoc(Map parMap) {
		return (Long)this.sqlMapClientTemplate.queryForObject("getLeftStorageNumWithLoc", parMap);
	}
	
}
