package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.ExpressDao;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.query.FreightStatisticsQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 *(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
@Repository("expressDao")
public class ExpressDaoiBatis implements ExpressDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addExpress(Express express) {
		this.sqlMapClient.insert("addExpress", express);
	}

	@Override
	public void editExpress(Express express) {
		this.sqlMapClient.update("editExpress", express);
	}

	@Override
	public void removeExpress(Long expressId) {
		this.sqlMapClient.delete("removeExpress", expressId);
	}

	@Override
	public Express getExpress(Long expressId) {
		return (Express) this.sqlMapClient.queryForObject("getExpress",
				expressId);
	}

	@Override
	public List<Express> getExpresss() {
		return this.sqlMapClient.queryForList("getExpresss", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Express> getExpressByName(String expressName) {
		// null
		if (StringUtil.isBlank(expressName)) {
			return null;
		}
		return this.sqlMapClient.queryForList("getExpressByName", expressName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Express> getExpressByCode(String expressCode) {
		// null
		if (StringUtil.isBlank(expressCode)) {
			return null;
		}
		return this.sqlMapClient.queryForList("getExpressByCode", expressCode);
	}

	public int getExpressCountByCond(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"getExpressCountByCond", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage listExpressByCond(Map parMap, int currentPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject(
				"getExpressCountByCond", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			/* 分页查询 */
			queryPage.setItems(sqlMapClient.queryForList("listExpressByCond",
					parMap));
		}
		return queryPage;
	}

	@Override
	public String getInterfaceExpressCodeByExpressId(Long id) {
		Map map = new HashMap();
		map.put("id", id);
		Express e = (Express) this.sqlMapClient.queryForObject(
				"getInterfaceExpressCodeByExpressId", map);
		return e.getInterfaceExpressCode();
	}

	@Override
	public Express getExpressIdByInterfaceExpressCode(
			String interfaceExpressCode) {
		Map map = new HashMap();
		map.put("interfaceExpressCode", interfaceExpressCode);
		return (Express) this.sqlMapClient.queryForObject(
				"getExpressIdByInterfaceExpressCode", map);
	}

	@Override
	public Express getExpressIdByExpressCode(String expressCode) {
		Map map = new HashMap();
		map.put("expressCode", expressCode);
		return (Express) this.sqlMapClient.queryForObject(
				"getExpressIdByExpressCode", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateExpressStatusById(String status, Long id) {
		if (StringUtil.isBlank(status) || id == null) {
			return 0;
		}
		Map map = new HashMap();
		map.put("status", status);
		map.put("id", id);
		return this.sqlMapClient.update("updateExpressStatusById", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Express> listExpressByStatus(String status) {
		return this.sqlMapClient.queryForList("listExpressByStatus", status);
	}

	@Override
	public int getFreightCountByParameterMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"getFreightCountByParameterMap", parMap);
	}

	@SuppressWarnings("unchecked")
	public QueryPage getFreightListsByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery, int currentPage,
			int pageSize) {
		QueryPage queryPage = new QueryPage(freightStatisticsQuery);
		Map pramas = queryPage.getParameters();
		pramas.put("depfirstIds", freightStatisticsQuery.getDepfirstIds());

		int count = (Integer) sqlMapClient.queryForObject(
				"getFreightCountByParameterMap", pramas);

		if (count > 0) {

			/* 当前页 */
			queryPage.setCurrentPage(currentPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());

			/* 分页查询 */
			queryPage.setItems(sqlMapClient.queryForList(
					"getFreightListsByParameterMap", pramas));
		}
		return queryPage;
	}

	@Override
	public double getFreightAmountByParameterMap(
			FreightStatisticsQuery freightStatisticsQuery) {
		return (Double) this.sqlMapClient.queryForObject(
				"getFreightAmountByParameterMap", freightStatisticsQuery);
	}

	@Override
	public List<Express> getGoodsExpressRelation(Map parMap) {
		return this.sqlMapClient
				.queryForList("getGoodsExpressRelation", parMap);
	}

	@Override
	public List<Express> getNotGoodsExpressRelation(Map parMap) {
		return this.sqlMapClient.queryForList("getNotGoodsExpressRelation",
				parMap);
	}
}
