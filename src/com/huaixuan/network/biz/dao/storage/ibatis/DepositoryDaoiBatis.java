package com.huaixuan.network.biz.dao.storage.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.storage.DepositoryDao;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 
 * @version 3.2.0
 */
@Service("depositoryDao")
public class DepositoryDaoiBatis implements DepositoryDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
/* @model: */
	public Long addDepository(Depository depository) {
		return (Long) this.sqlMapClient.insert("addDepository", depository);
	}
/* @model: */
	public void editDepository(Depository depository) {
		this.sqlMapClient.update("editDepository", depository);
	}
/* @model: */
	public void removeDepository(Long depositoryId) {
		this.sqlMapClient.delete("removeDepository", depositoryId);
	}
/* @model: */
	public Depository getDepository(Long depositoryId) {
		return (Depository) this.sqlMapClient.queryForObject("getDepository", depositoryId);
	}

	/*
	 * @see com.hundsun.bible.dao.ios.DepositoryDao#getDepositorys()
	 */
	public List<Depository> getDepositorys() {

		return this.sqlMapClient.queryForList("getDepositorys");
	}

	public List<Depository> getDepositorysByDepFirstId(Map<String, Object> paramMap) {
		return this.sqlMapClient.queryForList("getDepositorys", paramMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getDepositorysByParMap(DepositoryQuery depository, int currentPage, int pageSize, boolean isPage) {
		QueryPage queryPage = new QueryPage(depository);
		Map parMap = queryPage.getParameters();
		if(depository.getDepfirstIds()!= null && depository.getDepfirstIds().size() > 0){
			parMap.put("depfirstIds", depository.getDepfirstIds());
		}
		if(depository.getTypes()!=null && depository.getTypes().length > 0){
			parMap.put("types", depository.getTypes());
		}
		
		if (isPage) {
			int count = (Integer) sqlMapClient.queryForObject("getCountByParMap", parMap);
			
            /* ��ǰҳ */
            queryPage.setCurrentPage(currentPage);
            /* ÿҳ���� */
            queryPage.setPageSize(pageSize);
            queryPage.setTotalItem(count);


			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				/* ��ҳ��ѯ */
				queryPage.setItems(sqlMapClient.queryForList("getDepositorysByParMap", parMap));
			}
		} else {
			queryPage.setItems(sqlMapClient.queryForList("getDepositorysByParMap", parMap));
		}
		return queryPage;
	}

	/*
	 * @see com.hundsun.bible.dao.ios.DepositoryDao#getCountByParMap(java.util.Map)
	 */
	public int getCountByParMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject("getCountByParMap", parMap);
	}

	public List<Depository> getDeplistByFirstDepId(Long depfirstId) {
		return this.sqlMapClient.queryForList("getDeplistByFirstDepId", depfirstId);
	}

	public List<Depository> getRightDeplistByFirstDepId(Long depfirstId) {
		return this.sqlMapClient.queryForList("getRightDeplistByFirstDepId", depfirstId);
	}
}
