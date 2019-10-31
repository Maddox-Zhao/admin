package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.NavDao;
import com.huaixuan.network.biz.domain.shop.Nav;

@Service("navDao")
public class NavDaoiBatis implements NavDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addNav(Nav nav) throws Exception {
		this.sqlMapClient.insert("addNav", nav);
	}

	@Override
	public void editNav(Nav nav) throws Exception {
		this.sqlMapClient.update("editNav", nav);
	}

	@Override
	public void removeNav(Long navId) throws Exception {
		this.sqlMapClient.delete("removeNav", navId);
	}

	@Override
	public Nav getNav(Long navId) throws Exception {
		return (Nav) this.sqlMapClient.queryForObject("getNav", navId);
	}

	@Override
	public List<Nav> getNavs() throws Exception {
		return this.sqlMapClient.queryForList("getNavs", null);
	}

	@Override
	public List<Nav> getNavByType(String type) throws Exception {
		return this.sqlMapClient.queryForList("getNavByType", type);
	}

	@Override
	public Integer getNavsCount(long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getNavsCount",
				shopId);
	}

	@Override
	public List<Nav> getNavsPage(Map<String, String> conditions) {
		return this.sqlMapClient.queryForList("getNavsPage", conditions);
	}

	@Override
	public void isshowNav(Nav newNav) {
		this.sqlMapClient.update("isshowNav", newNav);
	}

	@Override
	public void isOpenNav(Nav newNav) {
		this.sqlMapClient.update("isOpenNav", newNav);
	}

	@Override
	public Integer getNavsMax(String navType) {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("type", navType);
		return (Integer) this.sqlMapClient.queryForObject("getNavsMax", prama);
	}

	@Override
	public void getNavsSortBig(int sort, int sortold, String type) {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("type", type);
		prama.put("sort", sort);
		prama.put("sortold", sortold);
		this.sqlMapClient.update("getNavsSortBig", prama);
	}

	@Override
	public void getNavsSortBwteenSmall(int sort, int sortold, String type) {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("type", type);
		prama.put("sort", sort);
		prama.put("sortold", sortold);
		this.sqlMapClient.update("getNavsSortBwteenSmall", prama);
	}

	@Override
	public void getNavsSortsamll(int sortold, String type) {
		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("type", type);
		prama.put("sortold", sortold);
		this.sqlMapClient.update("getNavsSortsamll", prama);
	}
}
