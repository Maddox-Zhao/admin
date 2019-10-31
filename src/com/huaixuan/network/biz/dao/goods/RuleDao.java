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
	 	 * �����Ż�ID����ȡ�Żݹ�������
	 	 * @param promationId
	 	 * @return
	 	 */
	 	List <Rule> getRulesByPromationId(Long promationId);

	 	/**
	 	 * �����Ż�ID��ɾ���Żݵ�
	 	 * @param promationId
	 	 * @return
	 	 */
	 	boolean removeRuleByPromationId(Long promationId);
	 	
	 	Rule getGiveRule(Map parMap);
	 	
	 	List<Rule> getSaleGiveRuleList(Long promationId);
 }
