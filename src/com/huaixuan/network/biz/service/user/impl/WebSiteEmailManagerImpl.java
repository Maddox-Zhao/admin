package com.huaixuan.network.biz.service.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.dao.goods.WebSiteEmailDao;
import com.huaixuan.network.biz.dao.goods.WebSiteRelationDao;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.domain.goods.WebSiteRelation;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.service.goods.GoodsAgentManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.biz.service.user.WebSiteEmailManager;

@Service("webSiteEmailManager")
public class WebSiteEmailManagerImpl implements WebSiteEmailManager {
	Log log = LogFactory.getLog(getClass());

	@Autowired
	private WebSiteEmailDao webSiteEmailDao;
	@Autowired
	private WebSiteRelationDao webSiteRelationDao;
	@Autowired
	private UserManager userManager;
	@Autowired
	private GoodsAgentManager goodsAgentManager;

	// public List<WebSiteEmail> selectWebSiteEmail(WebSiteEmail param, Page page) {
	// try {
	// return this.webSiteEmailDao.selectWebSiteEmail(param, page);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	// public List<WebSiteEmail> getFromUserWebSite(String fromUser, Page page) {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setFromUser(fromUser);
	// return this.webSiteEmailDao.selectWebSiteRelation(webSiteEmail, page);
	// }
	//
	// public List<WebSiteEmail> getToUserWebSite(String toUser, String status, Page page) {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setToUser(toUser);
	// webSiteEmail.setStatus(status);
	// return this.webSiteEmailDao.selectWebSiteRelation(webSiteEmail, page);
	// }
	//
	// public void setGoodsAgentManager(GoodsAgentManager goodsAgentManager) {
	// this.goodsAgentManager = goodsAgentManager;
	// }
	//
	// public boolean updateWebSite(WebSiteEmail webSiteEmail) {
	// try {
	// this.webSiteEmailDao.updateWebSite(webSiteEmail);
	// return true;
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// return false;
	// }
	// }

	public boolean insertWebSiteEmail(WebSiteEmail param) {
		try {
			int obj = 0;
			if (null != param) {
				obj = webSiteEmailDao.insertWebSiteEmail(param);
			}
			if (obj != 0) {
				WebSiteRelation relation = new WebSiteRelation();
				relation.setFromUser(param.getFromUser());
				relation.setToUser(param.getToUser());
				relation.setStatus(param.getStatus());
				relation.setWebSiteId(new Long(obj));
				webSiteRelationDao.insertWebSiteRelation(relation);
			}
			return true;
		} catch (Exception e) {
			this.log.error("error : ", e);
			return false;
		}
	}

	// public WebSiteEmail getWebSiteEmailByRelationId(Long id) {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setId(id);
	// List<WebSiteEmail> list = this.webSiteEmailDao.selectWebSiteRelation(webSiteEmail, null);
	// if (list.size() > 0) {
	// return list.get(0);
	// } else {
	// return null;
	// }
	// }
	//
	// public List<WebSiteEmail> getWebSiteEmailList() {
	//
	// return null;
	// }
	//
	// public boolean removeWebSiteEmail(List<String> ids) {
	// try {
	// for (String id : ids) {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setId(Long.parseLong(id));
	// webSiteEmail.setStatus("delete");
	// this.webSiteEmailDao.updateWebSiteEmailStatus(webSiteEmail);
	// // this.webSiteEmailDao.removeWebSiteEmail(webSiteEmail);
	// return true;
	// }
	// } catch (Exception e) {
	// this.log.error("error : ", e);
	// return false;
	// }
	// return false;
	// }
	//
	// public boolean removeWebSiteRelation(List<String> ids) {
	// try {
	// for (String id : ids) {
	// WebSiteRelation relation = new WebSiteRelation();
	// relation.setId(Long.parseLong(id));
	// relation.setStatus("delete");
	// webSiteRelationDao.updateWebSiteRelationStatus(relation);
	// }
	// return true;
	// } catch (Exception e) {
	// this.log.error("error : ", e);
	// return false;
	// }
	//
	// }
	//
	// public boolean updateWebSiteEmailStatus(Long id, String status) {
	// try {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setId(id);
	// webSiteEmail.setStatus(status);
	// this.webSiteEmailDao.updateWebSiteEmailStatus(webSiteEmail);
	// return true;
	// } catch (Exception e) {
	// this.log.error("error :" + e);
	// return false;
	// }
	// }
	//
	// public WebSiteEmail getWebSiteEmail(Long id) {
	// WebSiteEmail webSiteEmail = new WebSiteEmail();
	// webSiteEmail.setId(id);
	// try {
	// return webSiteEmailDao.getWebSiteEmail(webSiteEmail);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return webSiteEmail;
	// }
	//
	// public WebSiteEmailDao getWebSiteEmailDao() {
	// return webSiteEmailDao;
	// }
	//
	// public void setWebSiteEmailDao(WebSiteEmailDao webSiteEmailDao) {
	// this.webSiteEmailDao = webSiteEmailDao;
	// }
	//
	// public WebSiteRelationDao getWebSiteRelationDao() {
	// return webSiteRelationDao;
	// }
	//
	// public void setWebSiteRelationDao(WebSiteRelationDao webSiteRelationDao) {
	// this.webSiteRelationDao = webSiteRelationDao;
	// }
	//
	// public boolean insertWebSite(WebSiteEmail param) {
	// try {
	// Map parMap = new HashMap();
	// List<String> userTypes = new ArrayList<String>();
	// userTypes.add(EnumUserType.AGENT_USER.getKey());
	// userTypes.add(EnumUserType.WHOLESALE_USER.getKey());
	// parMap.put("userTypes", userTypes);
	// List<User> userList = userManager.getUserWithTypes(parMap);// 取得代销和批发用户getUserWithWebSiteList
	// int obj = 0;
	// if (null != param) {
	// obj = webSiteEmailDao.insertWebSiteEmail(param);
	// }
	// for (User user : userList) {// 将所有的代销用户都插入关联
	// if (obj != 0) {
	// WebSiteRelation relation = new WebSiteRelation();
	// relation.setFromUser(param.getFromUser());
	// relation.setToUser(user.getAccount());
	// relation.setStatus(param.getStatus());
	// relation.setWebSiteId(new Long(obj));
	// webSiteRelationDao.insertWebSiteRelation(relation);
	// }
	// }
	// return true;
	// } catch (Exception e) {
	// this.log.error("error :" + e);
	// return false;
	// }
	// }
	//
	// /**
	// * 批量插入站内信
	// *
	// * @param webSiteEmail
	// * @return bool
	// * @author fangqing 2009/12/14
	// */
	// public boolean insertWebSite2(WebSiteEmail webSiteEmail) {
	// try {
	// int obj = 0;
	// if (null != webSiteEmail) {
	// obj = webSiteEmailDao.insertWebSiteEmail(webSiteEmail);
	// }
	// if (obj != 0) {
	// WebSiteRelation relation = new WebSiteRelation();
	// relation.setFromUser(webSiteEmail.getFromUser());
	// relation.setStatus(webSiteEmail.getStatus());
	// relation.setWebSiteId(new Long(obj));
	// List<Long> userIds = goodsAgentManager.getPastUserIdForAddGoodsRelation();
	// for (Long userId : userIds) {
	// User u = userManager.getUser(userId);
	// if (u != null) {
	// relation.setToUser(u.getAccount());
	// webSiteRelationDao.insertWebSiteRelation(relation);
	// }
	// }
	// }
	// return true;
	// } catch (Exception e) {
	// this.log.error("error :" + e);
	// return false;
	// }
	// }
	//
	// public UserManager getUserManager() {
	// return userManager;
	// }
	//
	// public void setUserManager(UserManager userManager) {
	// this.userManager = userManager;
	// }
	//
	// public WebSiteRelation getWebSiteRelation(Long id) {
	// WebSiteRelation relation = new WebSiteRelation();
	// relation.setId(id);
	// return webSiteRelationDao.getWebSiteRelation(relation);
	// }
	//
	// public boolean updateWebSiteRelation(Long id, String status) {
	// try {
	// WebSiteRelation relation = new WebSiteRelation();
	// relation.setId(id);
	// relation.setStatus(status);
	// webSiteRelationDao.updateWebSiteRelationStatus(relation);
	// return true;
	// } catch (Exception e) {
	// return false;
	// }
	//
	// }
	//
	// public int countWebSiteEmail(WebSiteEmail param) {
	// return webSiteEmailDao.countWebSiteEmail(param);
	// }
	//
	// public int countWebSiteEmailRelation(WebSiteEmail param) {
	// return this.webSiteEmailDao.countWebSiteEmailRelation(param);
	// }

}
