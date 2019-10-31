package com.huaixuan.network.biz.dao.trade.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.trade.TradeAgentDao;
import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.agent.AgentTrade;

@Repository("tradeAgentDao")
public class TradeAgentDaoiBatis implements TradeAgentDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    // public List<AgentTrade> getAgentByParameterMap(Map parameterMap, Page page) {
    // if (page == null) {
    // return sqlMapClientTemplate.queryForList("getAgentByParameterMap", parameterMap);
    // }
    //
    // return this.findQueryPage("getAgentByParameterMap", parameterMap, page);
    //
    // }

    public int getCountAgentTradeByParMap(Map parameterMap) {
        return (Integer) sqlMapClientTemplate.queryForObject("getCountAgentTradeByParMap",
            parameterMap);
    }

    // public List<AgentTrade> getAgentTradeByParMap(Map parameterMap, Page page) {
    // if (page == null) {
    // return sqlMapClientTemplate.queryForList("getAgentTradeByParMap", parameterMap);
    // }
    // return this.findQueryPage("getAgentTradeByParMap", parameterMap, page);
    // }

    public List<AgentTrade> getGroupAgentTrade() {
        return sqlMapClientTemplate.queryForList("getGroupAgentTrade", null);
    }

    public int getAgentCountByParameterMap(Map<String, String> parMap) {
        return (Integer) sqlMapClientTemplate.queryForObject("getAgentCountByParameterMap", parMap);
    }

    public void addAgentTrade(AgentTrade agentTrade) {
        sqlMapClientTemplate.insert("addAgentTrade", agentTrade);
    }

    public void updateAgentTrade(AgentTrade agentTrade) {
        sqlMapClientTemplate.update("editAgentTrade", agentTrade);
    }

    public AgentTrade getAgentTradeByTidOrderId(String tid, long orderId) {
        Map parm = new HashMap();
        parm.put("tid", tid);
        parm.put("orderId", orderId);
        return (AgentTrade) sqlMapClientTemplate.queryForObject("getAgentTradeByTidOrderId", parm);
    }

    @SuppressWarnings("unchecked")
    public List<AgentTrade> countAgentAmount(Map<String, Object> parMap) {
        return sqlMapClientTemplate.queryForList("countUserAmount", parMap);
    }

    public void updateAgentIsReceive(Map<String, Object> parmap) {
        sqlMapClientTemplate.update("updateIsReceive", parmap);

    }

    public List<AgentTrade> searchAgentByParameterMap(Map parameterMap) {

        return sqlMapClientTemplate.queryForList("searchAgentByParameterMap", parameterMap);
    }

    public int searchAgentCountByParameterMap(Map<String, String> parMap) {
        return (Integer) sqlMapClientTemplate.queryForObject("searchAgentCountByParameterMap",
            parMap);
    }

    public Double searchAgentCountPriceByParameterMap(Map<String, String> parMap) {
        return (Double) sqlMapClientTemplate.queryForObject("searchAgentCountPriceByParameterMap",
            parMap);
    }

    public List<Admin> getAdminInfoById(Long newAgentManagerId) {
        return sqlMapClientTemplate.queryForList("getAdminInfoById", newAgentManagerId);
    }

    public void updateAgentTradeById(Map<String, String> param) {
        sqlMapClientTemplate.update("updateAgentTradeById", param);
    }

    public void resetAgentManager(Map<String, String> paramap) {
        sqlMapClientTemplate.update("resetAgentManager", paramap);
    }

    public List<Admin> getAdminInfo() {
        return sqlMapClientTemplate.queryForList("getAdminInfo");
    }
}
