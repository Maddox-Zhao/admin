package com.huaixuan.network.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.user.UserInfo;



public interface UserInfoDao {
 	void addUserInfo(UserInfo userInfo) throws Exception;

 	/* @interface model: UserInfo */
 	void editUserInfo(UserInfo userInfo) throws Exception;

 	/* @interface model: UserInfo */
 	void removeUserInfo(Long userInfoId) throws Exception;

 	/* @interface model: UserInfo,UserInfo */
 	UserInfo getUserInfo(Long userInfoId) throws Exception;

 	/* @interface model: UserInfo,UserInfo */
 	List <UserInfo> getUserInfos() throws Exception;

	UserInfo getUserInfoByUserId(Long userId) throws Exception;

	/**更新emal_agent_info表的可用和剩余返点数
	 * @param parMap
	 */
	void updateTicketLeft(Map<String,Object> parMap);
}
