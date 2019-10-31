package com.huaixuan.network.biz.dao.express.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.express.CastweightDao;
import com.huaixuan.network.biz.domain.express.Castweight;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Repository("castweightDao")
public class CastweightDaoiBatis implements CastweightDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addCastweight(Castweight castweight) throws Exception {
		this.sqlMapClient.insert("addCastweight", castweight);
	}

	@Override
	public void editCastweight(Castweight castweight) throws Exception {
		this.sqlMapClient.update("editCastweight", castweight);
	}

	@Override
	public void removeCastweight(Long castweightId) throws Exception {
		this.sqlMapClient.delete("removeCastweight", castweightId);
	}

	@Override
	public Castweight getCastweight(Long castweightId) throws Exception {
		return (Castweight) this.sqlMapClient.queryForObject("getCastweight",
				castweightId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Castweight getCastweightByGoodsIdAndExpessId(Long goodsId,
			Long expressId) throws Exception {
		Map parMap = new HashMap();
		parMap.put("goodsId", goodsId);
		parMap.put("expressId", expressId);
		parMap.put("status", "y");
		return (Castweight) this.sqlMapClient.queryForObject(
				"getCastweightByGoodsIdAndExpessId", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Castweight> getCastweights() throws Exception {
		return this.sqlMapClient.queryForList("getCastweights", null);
	}

	@Override
	public int getCastWeightCount(Map<String, String> parMap) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getCastWeightCount",
				parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getCastWeightList(Map parMap,
			int currentPage, int pageSize, boolean isPage) throws Exception {
		QueryPage queryPage = new QueryPage(parMap);
		queryPage.setCurrentPage(currentPage);
		queryPage.setPageSize(pageSize);

		if(isPage){
			/* ·ÖÒ³²éÑ¯ */
			int count = (Integer) sqlMapClient.queryForObject("getCastWeightCount",parMap);
			queryPage.setTotalItem(count);

			if (count > 0) {
				parMap.put("startRow", queryPage.getPageFristItem());
				parMap.put("endRow", queryPage.getPageLastItem());
				queryPage.setItems(sqlMapClient.queryForList("getCastWeightList",parMap));
			}
		}else{
			queryPage.setItems(sqlMapClient.queryForList("getCastWeightList",parMap));
		}
		return queryPage;
	}

	@Override
	public void updateCastweight(Castweight castweight) throws Exception {
		this.sqlMapClient.update("updateCastweight", castweight);
	}

	@Override
	public Castweight getCheckCastWeight(Map<String, String> pm)
			throws Exception {
		return (Castweight) this.sqlMapClient.queryForObject(
				"getCheckCastWeight", pm);
	}
}
