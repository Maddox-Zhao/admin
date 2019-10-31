package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.WebSiteEmailDao;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;

@Repository("webSiteEmailDao")
public class WebSiteEmailDaoiBatis implements WebSiteEmailDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	public int insertWebSiteEmail(WebSiteEmail webSiteEmail) {
		Long obj = (Long) this.sqlMapClient.insert("addWebSiteEmail",
				webSiteEmail);
		if (obj != null) {
			return obj.intValue();
		} else {
			return 0;
		}
	}

	public void removeWebSiteEmail(WebSiteEmail webSiteEmail) {
		this.sqlMapClient.update("removeWebSiteEamil", webSiteEmail);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebSiteEmail> selectWebSiteEmail(Map parMap) {
		return this.sqlMapClient.queryForList("getWebSiteEmail", parMap);
	}

	public void updateWebSite(WebSiteEmail webSiteEmail) {
		this.sqlMapClient.update("updateWebSite", webSiteEmail);
	}

	// @SuppressWarnings("unchecked")
	// public List<WebSiteEmail> selectWebSiteRelation(WebSiteEmail
	// webSiteEmail, Page page) {
	// return this.findQueryPage("getWebSiteEmailRelation", webSiteEmail, page);
	// }

	public void updateWebSiteEmailStatus(WebSiteEmail param) {
		this.sqlMapClient.update("editWebSiteEamilStatus", param);
	}

	public WebSiteEmail getWebSiteEmail(WebSiteEmail param) {
		return (WebSiteEmail) this.sqlMapClient.queryForObject(
				"getWebSiteEmailQuery", param);
	}

	public int countWebSiteEmail(Map parMap) {
		Integer obj = (Integer) this.sqlMapClient.queryForObject(
				"countWebSiteEmail", parMap);
		if (obj == null) {
			return 0;
		} else {
			return obj.intValue();
		}

	}

	public int countWebSiteEmailRelation(WebSiteEmail param) {
		Integer obj = (Integer) this.sqlMapClient.queryForObject(
				"countWebSiteEmailRelation", param);
		if (obj == null) {
			return 0;
		} else {
			return obj.intValue();
		}

	}
}
