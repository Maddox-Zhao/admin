package com.huaixuan.network.biz.service.user;

import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.domain.goods.WebSiteRelation;

public interface WebSiteEmailManager {

	// /**��ѯ�����˵���Ϣ
	// * @param param
	// * @return
	// */
	// List<WebSiteEmail> getFromUserWebSite(String fromUser, Page page);
	//
	// /**��ѯ�����˵���Ϣ
	// * @param param
	// * @return
	// */
	// List<WebSiteEmail> getToUserWebSite(String toUser,String status, Page page);

	/*
	 * @param param
	 */
	boolean insertWebSiteEmail(WebSiteEmail param);

	// /**����վ���ţ���վ�����û������������
	// * @param param
	// * @return
	// */
	// boolean insertWebSite(WebSiteEmail param);
	//
	// /**����վ���ţ���վ�����û�����������ݣ�����Forѭ��
	// * @param param
	// * @return
	// * @author fangqing 2009/12/14
	// */
	// public boolean insertWebSite2(WebSiteEmail param);
	// List<WebSiteEmail> selectWebSiteEmail(WebSiteEmail param, Page page);
	//
	// /**����id
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
