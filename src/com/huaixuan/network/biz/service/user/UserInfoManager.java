package com.huaixuan.network.biz.service.user;

import java.util.List;

import com.huaixuan.network.biz.domain.user.UserInfo;



public interface UserInfoManager {

    public void addUserInfo(UserInfo userInfo);

    /* @interface model: UserInfo */
    public void editUserInfo(UserInfo userInfo);

    /* @interface model: UserInfo */
    public void removeUserInfo(Long userInfoId);

    /* @interface model: UserInfo,UserInfo */
    public UserInfo getUserInfo(Long userInfoId);
    public UserInfo getUserInfoByUserId(Long userId);
    /* @interface model: UserInfo,UserInfo */
    public List<UserInfo> getUserInfos();

    /**更新emal_agent_info表的可用和剩余返点数
     * @param parMap
     */
    void updateTicketLeft(Long userId,Long amount);
}
