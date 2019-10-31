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
     * ���浽agentTrade�����Ҽ�¼ticket_record�͸���agent_info��user�ĵ�ȯ����
     * 
     * @param userId
     * @param tid
     * @return
     */
    public void updateAgentTradeWithAll(String tid, String status, String saveType)
                                                                                   throws Exception;

    /**
     * ͳ���û������Ľ��
     * 
     * @param cal
     *            ��ǰ����
     * @return
     */
    List<AgentTrade> countAgentAmount(Calendar cal);

    void updateAgentIsReceive(Calendar cal, Long userId);

    // /**
    // * ��̨��ѯ����
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
