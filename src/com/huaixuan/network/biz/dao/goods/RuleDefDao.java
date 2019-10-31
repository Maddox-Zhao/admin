package com.huaixuan.network.biz.dao.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.RuleDef;


 public interface RuleDefDao  {
	 
		/* @interface model: RuleDef */
	 	void addRuleDef(RuleDef ruleDef) throws Exception;

	 	/* @interface model: RuleDef */
	 	void editRuleDef(RuleDef ruleDef) throws Exception;

	 	/* @interface model: RuleDef */
	 	void removeRuleDef(Long ruleDefId) throws Exception;

	 	/* @interface model: RuleDef,RuleDef */
	 	RuleDef getRuleDef(Long ruleDefId) throws Exception;

	 	/* @interface model: RuleDef,RuleDef */
	 	List <RuleDef> getRuleDefs() throws Exception;

	 	/**
	 	 * 根据代码，获取规则定义
	 	 * @param code
	 	 * @return
	 	 * @throws Exception
	 	 */
	 	RuleDef getRuleDefByCode(String code) throws Exception;
 }
