package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.RuleDef;


 public interface RuleDefManager {
	 	/* @interface model: RuleDef */
	 	public void addRuleDef(RuleDef ruleDef);

	 	/* @interface model: RuleDef */
	 	public void editRuleDef(RuleDef ruleDef);

	 	/* @interface model: RuleDef */
	 	public void removeRuleDef(Long ruleDefId);

	 	/* @interface model: RuleDef,RuleDef */
	 	public RuleDef getRuleDef(Long ruleDefId);

	 	/* @interface model: RuleDef,RuleDef */
	 	public List<RuleDef> getRuleDefs();


 }
