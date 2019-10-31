package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.WebSiteEmail;

public interface WebSiteEmailDao {

	/**
	 * ����
	 *
	 * @param webSiteEmail
	 */
	int insertWebSiteEmail(WebSiteEmail webSiteEmail);

	/**
	 * ɾ��
	 *
	 * @param webSiteEmail
	 */
	void removeWebSiteEmail(WebSiteEmail webSiteEmail);

	/**
	 * ��ѯ
	 *
	 * @param webSiteEmail
	 * @return
	 */
	List<WebSiteEmail> selectWebSiteEmail(Map parMap);

	/**
	 * ����status
	 *
	 * @param param
	 */
	void updateWebSiteEmailStatus(WebSiteEmail param);

	WebSiteEmail getWebSiteEmail(WebSiteEmail param);

	/**
	 * ��ѯ
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
