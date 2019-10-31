package com.huaixuan.network.biz.service.user.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.user.UserAgentDao;
import com.huaixuan.network.biz.dao.user.UserDao;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.service.user.MailEngine;
import com.huaixuan.network.biz.service.user.UserAgentManager;

@Service("userAgentManager")
public class UserAgentManagerImpl implements UserAgentManager {

    protected Log        log = LogFactory.getLog(this.getClass());
    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAgentDao userAgentDao;
    
	@Autowired
    private MailEngine   mailEngine;

    public AgentInfo getUserAgentById(Long id) {
        try {
            return this.userAgentDao.getUserAgentById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void insertApply(AgentInfo agent) {
        this.userAgentDao.insertApply(agent);
    }

    public AgentInfo getAgentInfoById(long id) {
        AgentInfo agent = this.userAgentDao.getAgentInfoById(id);
        return agent;
    }

    public void modifyApply(AgentInfo agent) {
        this.userAgentDao.modifyApply(agent);
    }

    public void isallowApply(AgentInfo agentInfo, String ctx) {
        Map map = new HashMap();
        User user = this.userDao.getUserById(agentInfo.getUserId());
        map.put("user", user.getAccount());
        map.put("ctx", ctx);
        if ("success".equalsIgnoreCase(agentInfo.getStatus())) {
//            try {
//                mailEngine.sendMessage(user.getEmail(), getText("ios.agent.login.title.success"),
//                    "email-template/applySuccess.vm", map);
//            } catch (MessagingException e) {
//                log.error(e.getMessage());
//            }
        } else {
//            try {
//                mailEngine.sendMessage(user.getEmail(), this.getText("ios.agent.login.title.fail"),
//                    "email-template/applyFail.vm", map);
//            } catch (MessagingException e) {
//                log.error(e.getMessage());
//            }
        }
        if ("success".equals(agentInfo.getStatus())) {
            this.userDao.updateUserType(agentInfo.getUserId(), EnumUserType.AGENT_USER.getKey());
        } else {
            this.userDao.updateUserType(agentInfo.getUserId(), EnumUserType.NORMAL_USER.getKey());
        }
        this.userAgentDao.isallowApply(agentInfo);
    }

    public AgentInfo getAgentInfoByUserId(Long userId) {
        log.info("UserAgentManagerImpl.getAgentInfoByUserId method");
        try {
            return this.userAgentDao.getAgentInfoByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void editAgentInfoCash(Map map) {
        log.info("UserAgentManagerImpl.editAgentInfoCash method");
        try {
            this.userAgentDao.editAgentInfoCash(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

	@SuppressWarnings("unchecked")
	public int updateAgentDistributAddr(Map map) {
        log.info("UserAgentManagerImpl.updateAgentDistributAddr method");
        try {
            return this.userAgentDao.updateAgentDistributAddr(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return 0;
	}
}
