package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsRule;

public interface GoodsRuleManager {

	public void addGoodsRule(GoodsRule goodsRule)throws Exception;
	
	public List<GoodsRule> getGoodsRuleByGoodsId(Long goodsId);
	
	public GoodsRule getGoodsRuleByMap(Map parMap);
	
	/**
	 * 根据返点规则ID统计记录数
	 * @param returnId
	 * @return
	 */
	public int countGoodsRuleByReturnId(Long returnId);
	
	void editGoodsRule(GoodsRule goodsRule);
}
