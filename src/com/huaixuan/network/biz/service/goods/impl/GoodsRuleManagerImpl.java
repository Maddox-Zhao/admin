package com.huaixuan.network.biz.service.goods.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.GoodsRuleDao;
import com.huaixuan.network.biz.domain.goods.GoodsRule;
import com.huaixuan.network.biz.service.goods.GoodsRuleManager;

@Service("goodsRuleManager")
public class GoodsRuleManagerImpl implements GoodsRuleManager {

	@Autowired
	private GoodsRuleDao goodsRuleDao;

	public void addGoodsRule(GoodsRule goodsRule) throws Exception {
		goodsRuleDao.addGoodsRule(goodsRule);
	}

	public List<GoodsRule> getGoodsRuleByGoodsId(Long goodsId) {
		List<GoodsRule> list = this.goodsRuleDao.getGoodsRuleByGoodsId(goodsId);
//		if (list != null && list.size() > 0) {
//			for (GoodsRule tmp : list) {
//				ReturnPoint returnPoint = returnPointManager.getReturnPoint(tmp.getReturnId());
//				tmp.setRuleName(returnPoint.getName());
//			}
//		}
		return list;
	}

	public GoodsRule getGoodsRuleByMap(Map parMap) {
		return this.goodsRuleDao.getGoodsRuleByMap(parMap);
	}

	public int countGoodsRuleByReturnId(Long returnId) {
		try {
			return goodsRuleDao.countGoodsRuleByReturnId(returnId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void editGoodsRule(GoodsRule goodsRule) {
		this.goodsRuleDao.editGoodsRule(goodsRule);
	}

}
