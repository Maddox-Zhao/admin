package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.UserInfoDao;
import com.huaixuan.network.biz.domain.user.UserInfo;

@Repository("userInfoDao")
public class UserInfoDaoiBatis implements UserInfoDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;
	
    /* @model: */
    public void addUserInfo(UserInfo userInfo) throws Exception {
        this.sqlMapClient.insert("addUserInfo", userInfo);
    }

    /* @model: */
    public void editUserInfo(UserInfo userInfo) throws Exception {
        this.sqlMapClient.update("editUserInfo", userInfo);
    }

    /* @model: */
    public void removeUserInfo(Long userInfoId) throws Exception {
        this.sqlMapClient.delete("removeUserInfo", userInfoId);
    }

    /* @model: */
    public UserInfo getUserInfo(Long userInfoId) throws Exception {
        return (UserInfo) this.sqlMapClient.queryForObject("getUserInfo", userInfoId);
    }

    public UserInfo getUserInfoByUserId(Long userId) throws Exception {
        return (UserInfo) this.sqlMapClient.queryForObject("getUserInfoByUserId",
            userId);
    }

    /* @model: */
    public List<UserInfo> getUserInfos() throws Exception {
        return this.sqlMapClient.queryForList("getUserInfos", null);
    }

    public void updateTicketLeft(Map<String, Object> parMap) {
        this.sqlMapClient.update("updateTicketLeft", parMap);

    }

}
