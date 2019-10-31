package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Service("inDepositoryDao")
public class InDepositoryDaoiBatis implements InDepositoryDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClientTemplate;
/* @model: */
	public Long addInDepository(InDepository inDepository) {
		return (Long) this.sqlMapClientTemplate.insert("addInDepository", inDepository);
	}
/* @model: */
	public void editInDepository(InDepository inDepository) {
		this.sqlMapClientTemplate.update("editInDepository", inDepository);
	}
/* @model: */
	public void removeInDepository(Long inDepositoryId) {
		this.sqlMapClientTemplate.delete("removeInDepository", inDepositoryId);
	}
/* @model: */
	public InDepository getInDepository(Long inDepositoryId) {
		return (InDepository) this.sqlMapClientTemplate.queryForObject("getInDepository", inDepositoryId);
	}
/* @model: */
	public List<InDepository> getInDepositorys() {
		return this.sqlMapClientTemplate.queryForList("getInDepositorys", null);
	}

	public QueryPage getInDepositoryLists(InDepositoryQuery query, int currPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(query);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		if (isPage) {
			int count = (Integer) sqlMapClientTemplate.queryForObject("getInDepositoryListsCount", query);
			queryPage.setTotalItem(count);

			if (count > 0) {
				query.setStartRow(queryPage.getPageFristItem());
				query.setEndRow(queryPage.getPageLastItem());
				/* ∑÷“≥≤È—Ø */
				queryPage.setItems(sqlMapClientTemplate.queryForList("getInDepositoryLists", query));
			}
		} else {
			queryPage.setItems(sqlMapClientTemplate.queryForList("getInDepositoryLists", query));
		}
		return queryPage;
	}

	public int getInDepositoryListsCount(Map<String, String> parMap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getInDepositoryListsCount", parMap);
	}

	public List<InDepository> getInDepository(Map map) {
		return this.sqlMapClientTemplate.queryForList("getInDepositoryByParams", map);
	}

	@SuppressWarnings("unchecked")
	public int updateInDepositoryStatusById(Long id, String status, Date gmtInDep) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("status", status);
		map.put("gmtInDep", gmtInDep);
		return this.sqlMapClientTemplate.update("updateInDepositoryStatusById", map);
	}

	public String getBatchNumById(Long id) {
		return (String) this.sqlMapClientTemplate.queryForObject("getBatchNumById", id);
	}

	public int getUnFinishedInDepByRelNum(String relationNum) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getUnFinishedInDepByRelNum", relationNum);
	}

	public int getFinishedInDepByRelNum(String relationNum) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getFinishedInDepByRelNum", relationNum);
	}

	public String getInDepositoryStatusByDetailId(Long inDetailId) {
		if (inDetailId == null) {
			return "";
		}
		return (String) this.sqlMapClientTemplate.queryForObject("getInDepositoryStatusByDetailId", inDetailId);
	}

	@SuppressWarnings("unchecked")
	public List<InDepository> getInDepositorysWithDetail(Map parmap) {
		try {
			return this.sqlMapClientTemplate.queryForList("getInDepositorysWithDetail", parmap);
		} catch (Exception e) {
			// log.error(e.getMessage());
			return null;
		}
	}

	public int getInDepositorysWithDetailCount(Map parmap) {
		return (Integer) this.sqlMapClientTemplate.queryForObject("getInDepositorysWithDetailCount", parmap);
	}

	public List<String> getAlltypes() {
		try {
			return this.sqlMapClientTemplate.queryForList("getAllIntypes", null);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public InDepository getInDepByRelNum(String relationNum) {
		return (InDepository) this.sqlMapClientTemplate.queryForObject("getInDepByRelNum", relationNum);
	}

	public Map getSupplierNameById(List<String> inDepositoryIds) {
		Map map = new HashMap();
		map.put("inDepositoryIds", inDepositoryIds);
		return (Map) this.sqlMapClientTemplate.queryForMap("getSupplierNameById", map, "id", "supplierName");
	}

}
