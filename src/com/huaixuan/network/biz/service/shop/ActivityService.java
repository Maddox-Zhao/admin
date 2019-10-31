package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.huaixuan.network.biz.domain.shop.Activity;
import com.huaixuan.network.biz.query.QueryPage;

public interface ActivityService {
	public long addActivity(Activity activity,List<MultipartFile> files);

	public void editActivity(Activity activity, List<MultipartFile> files);

	public void editActivity(Activity activity);

	public void removeActivity(Long activityId);

	public Activity getActivity(Long activityId);

	public List<Activity> getActivitys();

	public QueryPage getActivitysPage(int currentPage, int
	pageSize);

	public List<Activity> allActivityDisplay();

	public List<Activity> allActivityDisplay2();
	
	String getActivityPosition(long id);
	
	boolean validatePublish(String activityPosition);
	
	int validateGoodsCount(long id);
}
