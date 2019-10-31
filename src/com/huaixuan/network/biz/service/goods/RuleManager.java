package com.huaixuan.network.biz.service.goods;

 import java.util.List;

import com.huaixuan.network.biz.domain.goods.Rule;


 public interface RuleManager {
	 	/* @interface model: Rule */ 
	 	public void addRule(Rule rule); 
	  
	 	/* @interface model: Rule */ 
	 	public void editRule(Rule rule); 
	  
	 	/* @interface model: Rule */ 
	 	public void removeRule(Long ruleId); 
	  
	 	/* @interface model: Rule,Rule */ 
	 	public Rule getRule(Long ruleId); 
	  
	 	/* @interface model: Rule,Rule */ 
	 	public List<Rule> getRules(); 
	 	
	 	public Rule getGiveRuleByPromationId(Long promationId);
 }
