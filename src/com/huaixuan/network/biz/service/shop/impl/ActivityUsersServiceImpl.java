package com.huaixuan.network.biz.service.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ActivityUsersDao;
import com.huaixuan.network.biz.domain.shop.ActivityUsers;
import com.huaixuan.network.biz.service.shop.ActivityUsersService;

/**
 * �����Զ����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
@Service("activityUsersService")
public class ActivityUsersServiceImpl implements ActivityUsersService {
	Log log = LogFactory.getLog(this.getClass());

	/* @model: ע��ActivityUsers */
	@Autowired
	public ActivityUsersDao activityUsersDao;

	@Override
	public void addActivityUsers(ActivityUsers activityUsersDao) {
		log.info("ActivityUsersManagerImpl.addActivityUsers method");
		try {
			this.activityUsersDao.addActivityUsers(activityUsersDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editActivityUsers(ActivityUsers activityUsers) {
		log.info("ActivityUsersManagerImpl.editActivityUsers method");
		try {
			this.activityUsersDao.editActivityUsers(activityUsers);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeActivityUsers(Long activityUsersId) {
		log.info("ActivityUsersManagerImpl.removeActivityUsers method");
		try {
			this.activityUsersDao.removeActivityUsers(activityUsersId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public ActivityUsers getActivityUsers(Long activityUsersId) {
		log.info("ActivityUsersManagerImpl.getActivityUsers method");
		try {
			return this.activityUsersDao.getActivityUsers(activityUsersId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<ActivityUsers> getActivityUserss() {
		log.info("ActivityUsersManagerImpl.getActivityUserss method");
		try {
			return this.activityUsersDao.getActivityUserss();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.bible.facade.ios.ActivityUsersManager#countActivityUserssByParMap
	 * (java.util.Map)
	 */
	@Override
	public int countActivityUserssByParMap(Map<String, String> parMap) {
		log.info("ActivityUsersManagerImpl.countActivityUserssByParMap method");
		try {
			return this.activityUsersDao.countActivityUserssByParMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Boolean isAvailableByUserId(Long userId) {
		log.info("ActivityUsersManagerImpl.isAvailableByUserId method");
		try {
			Map<String, String> para = new HashMap<String, String>();
			para.put("usersId", userId.toString());
			return this.activityUsersDao.countActivityUserssByParMap(para) <= 0;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}
}
