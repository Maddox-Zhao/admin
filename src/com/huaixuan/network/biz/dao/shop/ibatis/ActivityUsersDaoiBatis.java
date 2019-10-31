package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ActivityUsersDao;
import com.huaixuan.network.biz.domain.shop.ActivityUsers;

/**
 * 
 * @version 3.2.0
 */
@Service("activityUsersDao")
public class ActivityUsersDaoiBatis implements ActivityUsersDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	/* @model: ���һ��ActivityUsers��¼ */
	@Override
	public void addActivityUsers(ActivityUsers activityUsers) throws Exception {
		this.sqlMapClient.insert("addActivityUsers", activityUsers);
	}

	@Override
	public void editActivityUsers(ActivityUsers activityUsers) throws Exception {
		this.sqlMapClient.update("editActivityUsers", activityUsers);
	}

	@Override
	public void removeActivityUsers(Long activityUsersId) throws Exception {
		this.sqlMapClient.delete("removeActivityUsers", activityUsersId);
	}

	@Override
	public ActivityUsers getActivityUsers(Long activityUsersId)
			throws Exception {
		return (ActivityUsers) this.sqlMapClient.queryForObject(
				"getActivityUsers", activityUsersId);
	}

	@Override
	public List<ActivityUsers> getActivityUserss() throws Exception {
		return this.sqlMapClient.queryForList("getActivityUserss", null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.bible.dao.ios.ActivityUsersDao#countActivityUserssByParMap
	 * (java.util.Map)
	 */
	@Override
	public int countActivityUserssByParMap(Map<String, String> parMap) {
		return (Integer) this.sqlMapClient.queryForObject(
				"countActivityUserssByParMap", parMap);
	}

}
