package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsRule;


public interface GoodsRuleDao {

	void addGoodsRule(GoodsRule goodsRule)throws Exception;
	
	List<GoodsRule> getGoodsRuleByGoodsId(Long goodsId);
	
	GoodsRule getGoodsRuleByMap(Map parMap);
	
	/**
	 * 根据返点规则ID统计记录数
	 * @param returnId
	 * @return
	 */
	int countGoodsRuleByReturnId(Long returnId) throws Exception;
	
	void editGoodsRule(GoodsRule goodsRule);
}
