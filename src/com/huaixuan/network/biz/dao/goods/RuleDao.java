package com.huaixuan.network.biz.dao.goods;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.Rule;


 public interface RuleDao{
	 	/* @interface model: Rule */
	 	Long addRule(Rule rule) throws Exception;

	 	/* @interface model: Rule */
	 	void editRule(Rule rule) throws Exception;

	 	/* @interface model: Rule */
	 	void removeRule(Long ruleId) throws Exception;

	 	/* @interface model: Rule,Rule */
	 	Rule getRule(Long ruleId) throws Exception;

	 	/* @interface model: Rule,Rule */
	 	List <Rule> getRules() throws Exception;


	 	/**
	 	 * 根据优惠ID，获取优惠规则数据
	 	 * @param promationId
	 	 * @return
	 	 */
	 	List <Rule> getRulesByPromationId(Long promationId);

	 	/**
	 	 * 根据优惠ID，删除优惠的
	 	 * @param promationId
	 	 * @return
	 	 */
	 	boolean removeRuleByPromationId(Long promationId);
	 	
	 	Rule getGiveRule(Map parMap);
	 	
	 	List<Rule> getSaleGiveRuleList(Long promationId);
 }
