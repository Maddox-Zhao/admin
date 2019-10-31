package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.ActivityUsers;

public interface ActivityUsersService {
	/**
	 * 增加参加活动用户记录
	 * 
	 * @param activityUsers
	 */
	public void addActivityUsers(ActivityUsers activityUsers);

	/**
	 * 修改参加活动用户记录
	 * 
	 * @param activityUsers
	 */
	public void editActivityUsers(ActivityUsers activityUsers);

	/**
	 * 删除参加活动用户记录
	 * 
	 * @param activityUsersId
	 */
	public void removeActivityUsers(Long activityUsersId);

	/**
	 * 根据主键id获取参加活动用户记录
	 * 
	 * @param activityUsersId
	 * @return ActivityUsers
	 */
	public ActivityUsers getActivityUsers(Long activityUsersId);

	/**
	 * 获取全部的参加活动用户记�
	 * 
	 * @return List<ActivityUsers>
	 */
	public List<ActivityUsers> getActivityUserss();

	/**
	 * 根据搜索条件获取参加活动用户记录数量
	 * 
	 * @param parMap
	 * @return int
	 */
	public int countActivityUserssByParMap(Map<String, String> parMap);

	/**
	 * 根据用户ID判断该用户是否可以购买活动商�
	 * 
	 * @param userId
	 * @return boolean
	 */
	public Boolean isAvailableByUserId(Long userId);
}
