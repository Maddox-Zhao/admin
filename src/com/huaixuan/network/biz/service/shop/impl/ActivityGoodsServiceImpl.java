package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ActivityGoodsDao;
import com.huaixuan.network.biz.domain.shop.ActivityGoods;
import com.huaixuan.network.biz.service.shop.ActivityGoodsService;
import com.hundsun.network.melody.common.util.StringUtil;

@Service("activityGoodsService")
public class ActivityGoodsServiceImpl implements ActivityGoodsService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public ActivityGoodsDao activityGoodsDao;

	@Override
	public void addActivityGoods(ActivityGoods activityGoodsDao) {
		log.info("ActivityGoodsManagerImpl.addActivityGoods method");
		try {
			this.activityGoodsDao.addActivityGoods(activityGoodsDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editActivityGoods(ActivityGoods activityGoods) {
		log.info("ActivityGoodsManagerImpl.editActivityGoods method");
		try {
			this.activityGoodsDao.editActivityGoods(activityGoods);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeActivityGoods(Long activityGoodsId) {
		log.info("ActivityGoodsManagerImpl.removeActivityGoods method");
		try {
			this.activityGoodsDao.removeActivityGoods(activityGoodsId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public ActivityGoods getActivityGoods(Long activityGoodsId) {
		log.info("ActivityGoodsManagerImpl.getActivityGoods method");
		try {
			return this.activityGoodsDao.getActivityGoods(activityGoodsId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<ActivityGoods> getActivityGoodssByMap(Map parMap) {
		log.info("ActivityGoodsManagerImpl.getActivityGoodssByMap method");
		try {
			return this.activityGoodsDao.getActivityGoodssByMap(parMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void addMoreActivityGoods(Map parMap) {
		log.info("ActivityGoodsManagerImpl.addMoreActivityGoods method");
		String[] goodsIds = (String[]) parMap.get("goodsIds");
		try {
			// 修改分类名称
			String oldCategoryName = (String) parMap.get("oldCategoryName");
			if (StringUtil.isNotBlank(oldCategoryName)) {
				this.activityGoodsDao.editActivityGoodsByMap(parMap);
			}

			if (goodsIds != null && goodsIds.length > 0) {
				ActivityGoods activityGoods = null;
				// 添加商品
				for (String idStr : goodsIds) {
					if (StringUtil.isNumeric(idStr)) {
						Long id = Long.parseLong(idStr);
						parMap.put("goodsId", id);
						List<ActivityGoods> actGoodsList = activityGoodsDao
								.getActivityGoodssByMap(parMap);
						// 如果存在就不再添�
						if (actGoodsList == null || actGoodsList.size() <= 0) {
							activityGoods = new ActivityGoods();
							activityGoods.setGoodsId(id);
							activityGoods.setActivityId((Long) parMap
									.get("activityId"));
							activityGoods.setCategory((String) parMap
									.get("category"));
							this.activityGoodsDao
									.addActivityGoods(activityGoods);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeByActivityId(Long activityId) {
		log.info("ActivityGoodsManagerImpl.removeByActivityId method");
		try {
			this.activityGoodsDao.removeByActivityId(activityId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public List<ActivityGoods> getByActivityId(Long activityId) {
		log.info("ActivityGoodsManagerImpl.getByActivityId method");
		try {
			return this.activityGoodsDao.getByActivityId(activityId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;

	}

	@Override
	public List<ActivityGoods> getCategoryByActivityId(Long activityId) {
		log.info("ActivityGoodsManagerImpl.getByActivityId method");
		try {
			return this.activityGoodsDao.getCategoryByActivityId(activityId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;

	}

}
