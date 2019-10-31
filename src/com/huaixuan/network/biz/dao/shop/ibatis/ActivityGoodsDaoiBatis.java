package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ActivityGoodsDao;
import com.huaixuan.network.biz.domain.shop.ActivityGoods;

@Service("activityGoodsDao")
public class ActivityGoodsDaoiBatis implements ActivityGoodsDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addActivityGoods(ActivityGoods activityGoods) throws Exception {
		this.sqlMapClient.insert("addActivityGoods", activityGoods);
	}

	@Override
	public void editActivityGoods(ActivityGoods activityGoods) throws Exception {
		this.sqlMapClient.update("editActivityGoods", activityGoods);
	}

	@Override
	public void editActivityGoodsByMap(Map parMap) throws Exception {
		this.sqlMapClient.update("editActivityGoodsByMap", parMap);
	}

	@Override
	public void removeActivityGoods(Long activityGoodsId) throws Exception {
		this.sqlMapClient.delete("removeActivityGoods", activityGoodsId);
	}

	@Override
	public ActivityGoods getActivityGoods(Long activityGoodsId)
			throws Exception {
		return (ActivityGoods) this.sqlMapClient.queryForObject(
				"getActivityGoods", activityGoodsId);
	}

	@Override
	public List<ActivityGoods> getActivityGoodssByMap(Map parMap)
			throws Exception {
		return this.sqlMapClient.queryForList("getActivityGoodssByMap", parMap);
	}

	@Override
	public void removeByActivityId(Long activityId) throws Exception {
		this.sqlMapClient.delete("removeByActivityId", activityId);
	}

	@Override
	public List<ActivityGoods> getByActivityId(Long activityId)
			throws Exception {
		return this.sqlMapClient.queryForList("getByActivityId", activityId);
	}

	@Override
	public List<ActivityGoods> getCategoryByActivityId(Long activityId)
			throws Exception {
		return this.sqlMapClient.queryForList("getCategoryByActivityId",
				activityId);
	}

}
