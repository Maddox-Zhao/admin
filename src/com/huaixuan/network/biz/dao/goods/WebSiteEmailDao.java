package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.WebSiteEmail;

public interface WebSiteEmailDao {

	/**
	 * 新增
	 *
	 * @param webSiteEmail
	 */
	int insertWebSiteEmail(WebSiteEmail webSiteEmail);

	/**
	 * 删除
	 *
	 * @param webSiteEmail
	 */
	void removeWebSiteEmail(WebSiteEmail webSiteEmail);

	/**
	 * 查询
	 *
	 * @param webSiteEmail
	 * @return
	 */
	List<WebSiteEmail> selectWebSiteEmail(Map parMap);

	/**
	 * 更新status
	 *
	 * @param param
	 */
	void updateWebSiteEmailStatus(WebSiteEmail param);

	WebSiteEmail getWebSiteEmail(WebSiteEmail param);

	/**
	 * 查询
	 *
	 * @param webSiteEmail
	 * @return
	 */
	// List<WebSiteEmail> selectWebSiteRelation(WebSiteEmail webSiteEmail ,Page
	// page);

	public void updateWebSite(WebSiteEmail webSiteEmail);

	int countWebSiteEmail(Map parMap);

	int countWebSiteEmailRelation(WebSiteEmail webSiteEmail);
}
