package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DepLocationDao;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.query.QueryPage;

@Service("depLocationDao")
public class DepLocationDaoIbatis implements DepLocationDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#addDepLocation(com.hundsun.bible.domain.model.ios.DepLocation)
	 */
	public Long addDepLocation(DepLocation depLocation) {
		return (Long) this.sqlMapClient.insert("createDepLocation", depLocation);
	}

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#editDepLocation(com.hundsun.bible.domain.model.ios.DepLocation)
	 */
	public void editDepLocation(DepLocation depLocation) {
		this.sqlMapClient.update("editDepLocation", depLocation);
	}

	/*
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#getCountByParMap(java.util.Map)
	 */
	public int getCountByParMap(Map<String, Object> parMap) {
		return (Integer) this.sqlMapClient.queryForObject("getDepLocCountByParMap", parMap);
	}

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#getDepLocationsByParMap(java.util.Map, com.hundsun.bible.Page)
	 */
	@SuppressWarnings("unchecked")
	public QueryPage getDepLocationsByParMap(Map parameterMap, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parameterMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getDepLocCountByParMap", parameterMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parameterMap.put("startRow", queryPage.getPageFristItem());
			parameterMap.put("endRow", queryPage.getPageLastItem());
			/* ∑÷“≥≤È—Ø */
			queryPage.setItems(sqlMapClient.queryForList("getDepLocationsByParMap", parameterMap));
		}
		return queryPage;
	}

	public List<DepLocation> getAll() {
		return this.sqlMapClient.queryForList("loadAllDepLocation");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#getAllDepLocationByMap()
	 */
	public List<DepLocation> getAllDepLocationByMap(Map<String, String> parMap) {
		return this.sqlMapClient.queryForList("getAllDepLocationByMap", parMap);
	}

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#getLocationsByDepositoryId()
	 */
	public List<DepLocation> getLocationsByDepositoryId(Long depositoryId) {
		return this.sqlMapClient.queryForList("getLocationsByDepositoryId", depositoryId);
	}

	public DepLocation getLocationsById(Long id) {
		Map parameterMap = new HashMap();
		parameterMap.put("locationId", id);
		return (DepLocation) this.sqlMapClient.queryForObject("getLocationsByParMap", parameterMap);
	}

	public DepLocation getLocationsByName(String name) {
		Map parameterMap = new HashMap();
		parameterMap.put("locationName", name);
		return (DepLocation) this.sqlMapClient.queryForObject("getLocationsByParMap", parameterMap);
	}

	@SuppressWarnings("unchecked")
	public List<DepLocation> getCheckLocationInfo(Long goodsInstanceId, String isCheck, Long depFirstId) {
		Map map = new HashMap();
		map.put("goodsInstanceId", goodsInstanceId);
		map.put("isCheck", isCheck);
		map.put("depFirstId", depFirstId);
		return this.sqlMapClient.queryForList("getCheckLocationInfo", map);
	}

	@SuppressWarnings("unchecked")
	public int getIsCheckCountById(Long id, String isCheck) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("isCheck", isCheck);
		return (Integer) this.sqlMapClient.queryForObject("getIsCheckCountById", map);
	}

	public void lockDepLocation(DepLocation depLocation) {
		try {
			this.sqlMapClient.update("lockDepLocation", depLocation);
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	public List<DepLocation> listUseabledLocInfo(Long depFirstId) {
		return this.sqlMapClient.queryForList("listUseabledLocInfo", depFirstId);
	}

	/*
	 * 
	 * @see com.hundsun.bible.dao.ios.DepLocationDao#countStorageByDepLocId(java.lang.Long)
	 */
	public int countStorageByDepLocId(Long depLocId) {
		return (Integer) this.sqlMapClient.queryForObject("countStorageByDepLocId", depLocId);
	}

	public List<DepLocation> getRightLocationsByDepositoryId(Long depositoryId) {
		return this.sqlMapClient.queryForList("getRightLocationsByDepositoryId", depositoryId);
	}

	public List<DepLocation> getAllRightLocations() {
		return this.sqlMapClient.queryForList("getAllRightLocations");
	}

	@Override
	public List<DepLocation> getRightDeplocationByDepfirstId(Long depFirstId) {
		return this.sqlMapClient.queryForList("getRightDeplocationByDepfirstId", depFirstId);
	}

}
