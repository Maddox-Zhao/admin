package com.huaixuan.network.biz.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.UserAddressDao;
import com.huaixuan.network.biz.dao.user.UserInfoDao;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.service.user.UserInfoManager;

@Service("userInfoManager")
public class UserInfoManagerImpl implements UserInfoManager {

	@Autowired
	private UserInfoDao userInfoDao;
	
	protected Log  log = LogFactory.getLog(this.getClass());
	
    public void addUserInfo(UserInfo userInfo) {
        log.info("UserInfoManagerImpl.addUserInfo method");
        try {
            this.userInfoDao.addUserInfo(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void editUserInfo(UserInfo userInfo) {
        log.info("UserInfoManagerImpl.editUserInfo method");
        try {
            this.userInfoDao.editUserInfo(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* @model: */
    public void removeUserInfo(Long userInfoId) {
        log.info("UserInfoManagerImpl.removeUserInfo method");
        try {
            this.userInfoDao.removeUserInfo(userInfoId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public UserInfo getUserInfoByUserId(Long userId) {
        log.info("UserInfoManagerImpl.getUserInfoByUserId method");
        try {
            return this.userInfoDao.getUserInfoByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public UserInfo getUserInfo(Long userInfoId) {
        log.info("UserInfoManagerImpl.getUserInfo method");
        try {
            return this.userInfoDao.getUserInfo(userInfoId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /* @model: */
    public List<UserInfo> getUserInfos() {
        log.info("UserInfoManagerImpl.getUserInfos method");
        try {
            return this.userInfoDao.getUserInfos();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

	public void updateTicketLeft(Long userId, Long amount) {
		Map<String,Object> parMap=new HashMap<String,Object>();
		parMap.put("userId", userId);
		parMap.put("ticketLeft", amount);
		this.userInfoDao.updateTicketLeft(parMap);
	}

}
