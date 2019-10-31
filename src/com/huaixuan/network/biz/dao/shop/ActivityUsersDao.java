package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.ActivityUsers;

/**
 * �����Զ����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface ActivityUsersDao {
	/* @interface model: ���һ��ActivityUsers��¼ */
	void addActivityUsers(ActivityUsers activityUsers) throws Exception;

	/* @interface model: ����һ��ActivityUsers��¼ */
	void editActivityUsers(ActivityUsers activityUsers) throws Exception;

	/* @interface model: ɾ��һ��ActivityUsers��¼ */
	void removeActivityUsers(Long activityUsersId) throws Exception;

	/* @interface model: ��ѯһ��ActivityUsers���,����ActivityUsers���� */
	ActivityUsers getActivityUsers(Long activityUsersId) throws Exception;

	/*
	 * @interface model:
	 * ��ѯ����ActivityUsers���,����ActivityUsers����ļ���
	 */
	List<ActivityUsers> getActivityUserss() throws Exception;

	/* 根据条件查询记录� */
	int countActivityUserssByParMap(Map<String, String> parMap);
}
