package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserAgentDao;
import com.huaixuan.network.biz.domain.user.AgentInfo;

@Repository("userAgentDao")
public class UserAgentDaoiBatis implements UserAgentDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
	protected Log  logger = LogFactory.getLog(this.getClass());
	
    public AgentInfo getUserAgentById(Long id) {
        try {
            return (AgentInfo) this.sqlMapClient.queryForObject("getUserAgentById", id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void insertApply(AgentInfo agentInfo) {
        this.sqlMapClient.insert("insertApply", agentInfo);
    }

    public AgentInfo getAgentInfoById(long id) {
        AgentInfo agent = (AgentInfo) this.sqlMapClient.queryForObject("getAgentInfoById", id);
        return agent;
    }

    public void modifyApply(AgentInfo agentInfo) {
        this.sqlMapClient.update("modifyApply", agentInfo);
    }

    public void isallowApply(AgentInfo agentInfo) {
        this.sqlMapClient.update("isallowApply", agentInfo);

    }

    public int updateAgentTickets(AgentInfo agentInfo) {
        return this.sqlMapClient.update("updateAgentTickets", agentInfo);
    }

    public AgentInfo getAgentInfoByUserId(Long userId) {
        return (AgentInfo) this.sqlMapClient.queryForObject("getAgentInfoByUserId",
            userId);
    }

    @SuppressWarnings("unchecked")
    public void editAgentInfoCash(Map map) {
        this.sqlMapClient.update("editAgentInfoCash", map);
    }

    @SuppressWarnings("unchecked")
    public int updateAgentDistributAddr(Map map) {
		return this.sqlMapClient.update("updateAgentDistributAddr", map);
	}

}
