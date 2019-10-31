package com.huaixuan.network.biz.dao.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.agent.AgentTrade;

public interface TradeAgentDao {

    // List<AgentTrade> getAgentByParameterMap(Map parameterMap, Page page);

    int getAgentCountByParameterMap(Map<String, String> parMap);

    void addAgentTrade(AgentTrade agentTrade);

    AgentTrade getAgentTradeByTidOrderId(String tid, long orderId);

    public void updateAgentTrade(AgentTrade agentTrade);

    /**
     * 统计用户代销的金额
     * 
     * @param parMap
     * @return
     */
    List<AgentTrade> countAgentAmount(Map<String, Object> parMap);

    /**
     * 更新is_receive标记
     * 
     * @param parmap
     */
    void updateAgentIsReceive(Map<String, Object> parmap);

    /**
     * 后台搜索界面
     * 
     * @param parameterMap
     * @param page
     * @return
     */
    List<AgentTrade> searchAgentByParameterMap(Map parameterMap);

    int getCountAgentTradeByParMap(Map parameterMap);

    // List<AgentTrade> getAgentTradeByParMap(Map parameterMap, Page page);

    List<AgentTrade> getGroupAgentTrade();

    int searchAgentCountByParameterMap(Map<String, String> parMap);

    List<Admin> getAdminInfoById(Long newAgentManagerId);

    void updateAgentTradeById(Map<String, String> param);

    void resetAgentManager(Map<String, String> paramap);

    List<Admin> getAdminInfo();

    public Double searchAgentCountPriceByParameterMap(Map<String, String> parMap);

}
