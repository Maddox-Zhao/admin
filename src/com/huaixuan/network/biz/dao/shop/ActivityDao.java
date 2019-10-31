package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Activity;

public interface ActivityDao {
	long addActivity(Activity activity) throws Exception;

	void editActivity(Activity activity) throws Exception;

	void removeActivity(Long activityId) throws Exception;

	Activity getActivity(Long activityId) throws Exception;

	List<Activity> getActivitys() throws Exception;

	public Integer getActivitysCount() throws Exception;

	public List<Activity> getActivitysPage(Map<String, String> prama)
			throws Exception;

	List<Activity> allActivityDisplay(Map pramas) throws Exception;

	List<Activity> allActivityDisplay2(Map pramas) throws Exception;
	
	int validatePublish(String activityPosition);
	
	String getActivityPosition(long id);
	
	int validateGoodsCount(long id);

}
