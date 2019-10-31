package com.huaixuan.network.biz.service.trade;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.agent.AgentTrade;
import com.huaixuan.network.biz.query.QueryPage;

public interface TradeAgentManager {

    // public List<AgentTrade> getAgentByParameterMap(Map parameterMap, Page page);

    public int getAgentCountByParameterMap(Map<String, String> parMap);

    /**
     * 保存到agentTrade，并且记录ticket_record和更新agent_info中user的点券数量
     * 
     * @param userId
     * @param tid
     * @return
     */
    public void updateAgentTradeWithAll(String tid, String status, String saveType)
                                                                                   throws Exception;

    /**
     * 统计用户代销的金额
     * 
     * @param cal
     *            当前日期
     * @return
     */
    List<AgentTrade> countAgentAmount(Calendar cal);

    void updateAgentIsReceive(Calendar cal, Long userId);

    // /**
    // * 后台查询界面
    // *
    // * @param parameterMap
    // * @param page
    // * @return
    // */
    // public List<AgentTrade> searchAgentByParameterMap(Map parameterMap, Page page);

    QueryPage searchAgentByParameterMap(AgentTrade trade, int currPage, int pageSize);

    public Double searchAgentCountPriceByParameterMap(AgentTrade trade);

    public int getCountAgentTradeByParMap(Map parameterMap);

    //	public List<AgentTrade> getAgentTradeByParMap(Map parameterMap, Page page);

    public List<AgentTrade> getGroupAgentTrade();

    public int searchAgentCountByParameterMap(Map<String, String> parMap);

    public List<Admin> getAdminInfoById(Long newAgentManagerId);

    public void updateAgentTradeById(Map<String, String> param);

    public void resetAgentManager(Map<String, String> paramap);

    public List<Admin> getAdminInfo();
}
