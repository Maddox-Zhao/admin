package com.huaixuan.network.biz.service.user;

import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.domain.goods.WebSiteRelation;

public interface WebSiteEmailManager {

	// /**查询发信人的信息
	// * @param param
	// * @return
	// */
	// List<WebSiteEmail> getFromUserWebSite(String fromUser, Page page);
	//
	// /**查询收信人的信息
	// * @param param
	// * @return
	// */
	// List<WebSiteEmail> getToUserWebSite(String toUser,String status, Page page);

	/*
	 * @param param
	 */
	boolean insertWebSiteEmail(WebSiteEmail param);

	// /**保存站内信，和站内信用户关联表的数据
	// * @param param
	// * @return
	// */
	// boolean insertWebSite(WebSiteEmail param);
	//
	// /**保存站内信，和站内信用户关联表的数据，不用For循环
	// * @param param
	// * @return
	// * @author fangqing 2009/12/14
	// */
	// public boolean insertWebSite2(WebSiteEmail param);
	// List<WebSiteEmail> selectWebSiteEmail(WebSiteEmail param, Page page);
	//
	// /**主键id
	// * @param id
	// */
	// boolean removeWebSiteEmail(List<String> ids);
	//
	// /**
	// * @param param
	// */
	// boolean updateWebSiteEmailStatus(Long id, String status);
	//
	// WebSiteEmail getWebSiteEmail(Long id);
	//
	// WebSiteRelation getWebSiteRelation(Long id);
	//
	// boolean updateWebSiteRelation(Long id, String status);
	//
	// WebSiteEmail getWebSiteEmailByRelationId(Long id);
	//
	// public boolean removeWebSiteRelation(List<String> ids);
	//
	//
	// boolean updateWebSite(WebSiteEmail webSiteEmail);
	//
	// int countWebSiteEmail(WebSiteEmail param);
	//
	// int countWebSiteEmailRelation(WebSiteEmail param);
}
