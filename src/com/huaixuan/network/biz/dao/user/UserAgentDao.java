package com.huaixuan.network.biz.dao.user;

import java.util.Map;

import com.huaixuan.network.biz.domain.user.AgentInfo;

public interface UserAgentDao {
	AgentInfo getUserAgentById(Long id);

	public void insertApply(AgentInfo agentInfo);

	public AgentInfo getAgentInfoById(long id);

	public void modifyApply(AgentInfo agentInfo);

	public void isallowApply(AgentInfo agentInfo);

	public int updateAgentTickets(AgentInfo agentInfo);

	/**
	 * �����û�IDȡ�ô�����Ϣ
	 * @param userId Long
	 * @return AgentInfo
	 * @author chenyan 2009/10/16
	 */
	AgentInfo getAgentInfoByUserId(Long userId);

	/**
	 * �������ֲ���
	 * @param map Map
	 * @author chenyan 2009/10/16
	 */
	void editAgentInfoCash(Map map);

	/**
	 * ���·�����ַ
	 * @param map
	 * @return
	 */
	public int updateAgentDistributAddr(Map map);
}
