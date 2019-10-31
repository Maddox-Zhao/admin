package com.huaixuan.network.biz.service.user;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.domain.user.AgentInfo;


public interface UserAgentManager {
	AgentInfo getUserAgentById(Long id);

	public void insertApply(AgentInfo agent);

	public AgentInfo getAgentInfoById(long id);

	public void modifyApply(AgentInfo agent);

	public void isallowApply(AgentInfo agentInfo,String ctx);

	/**
	 * 根据用户ID取得代销信息
	 * @param userId Long
	 * @return AgentInfo
	 * @author chenyan 2009/10/16
	 */
	AgentInfo getAgentInfoByUserId(Long userId);

	/**
	 * 申请提现操作
	 * @param map Map
	 * @author chenyan 2009/10/16
	 */
	void editAgentInfoCash(Map map);

	/**
	 * 更新分销地址
	 * @param map
	 * @return
	 */
	public int updateAgentDistributAddr(Map map);
}
