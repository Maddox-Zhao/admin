package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ActivityDao;
import com.huaixuan.network.biz.domain.shop.Activity;

@Service("activityDao")
public class ActivityDaoiBatis implements ActivityDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public long addActivity(Activity activity) throws Exception {
		return (Long) this.sqlMapClient.insert("addActivity", activity);
	}

	@Override
	public void editActivity(Activity activity) throws Exception {
		this.sqlMapClient.update("editActivity", activity);
	}

	@Override
	public void removeActivity(Long activityId) throws Exception {
		this.sqlMapClient.delete("removeActivity", activityId);
	}

	@Override
	public Activity getActivity(Long activityId) throws Exception {
		return (Activity) this.sqlMapClient.queryForObject("getActivity",
				activityId);
	}

	@Override
	public List<Activity> getActivitys() throws Exception {
		return this.sqlMapClient.queryForList("getActivitys", null);
	}

	@Override
 	public Integer getActivitysCount() throws Exception{
 		return (Integer) this.sqlMapClient.queryForObject("getActivitysCount");
 	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivitysPage(Map<String, String> prama)
			throws Exception {
		return this.sqlMapClient.queryForList("getActivitysPage", prama);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Activity> allActivityDisplay(Map pramas) throws Exception {
		return this.sqlMapClient.queryForList("allActivityDisplay", pramas);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Activity> allActivityDisplay2(Map pramas) throws Exception {
		return this.sqlMapClient.queryForList("allActivityDisplay2", pramas);
	}

	@Override
	public int validatePublish(String activityPosition) {
		return (Integer) this.sqlMapClient.queryForObject("getPublishedCount", activityPosition);
	}

	@Override
	public String getActivityPosition(long id) {
		return (String) this.sqlMapClient.queryForObject("getPublishedPosition",id);
	}

	@Override
	public int validateGoodsCount(long id) {
		return (Integer) this.sqlMapClient.queryForObject("getActivityGoodsCount",id);
	}

}
