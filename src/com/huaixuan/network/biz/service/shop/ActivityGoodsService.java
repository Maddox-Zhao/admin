package com.huaixuan.network.biz.service.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.ActivityGoods;

public interface ActivityGoodsService {
	public void addActivityGoods(ActivityGoods activityGoods);

	public void editActivityGoods(ActivityGoods activityGoods);

	public void removeActivityGoods(Long activityGoodsId);

	public ActivityGoods getActivityGoods(Long activityGoodsId);

	public List<ActivityGoods> getActivityGoodssByMap(Map parMap);

	public void addMoreActivityGoods(Map parMap);

	public void removeByActivityId(Long activityId);

	public List<ActivityGoods> getByActivityId(Long activityId);

	public List<ActivityGoods> getCategoryByActivityId(Long activityId);

}
