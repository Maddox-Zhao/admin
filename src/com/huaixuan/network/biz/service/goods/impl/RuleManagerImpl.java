package com.huaixuan.network.biz.service.goods.impl;

 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.RuleDao;
import com.huaixuan.network.biz.domain.goods.Rule;
import com.huaixuan.network.biz.service.goods.RuleManager;

@Service("ruleManager")
 public class RuleManagerImpl implements RuleManager {

	protected Log  log = LogFactory.getLog(this.getClass());
	
	@Autowired
 	RuleDao ruleDao;

 	/* @model: */
 	public void addRule(Rule ruleDao) {
 		log.info("RuleManagerImpl.addRule method");
 		try {
 			 this.ruleDao.addRule(ruleDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void editRule(Rule rule) {
 		log.info("RuleManagerImpl.editRule method");
 		try {
 			this.ruleDao.editRule(rule);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public void removeRule(Long ruleId) {
 		log.info("RuleManagerImpl.removeRule method");
 		try {
 			this.ruleDao.removeRule(ruleId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

 	/* @model: */
 	public Rule getRule(Long ruleId) {
 		log.info("RuleManagerImpl.getRule method");
 		try {
 			return this.ruleDao.getRule(ruleId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

 	/* @model: */
 	public List<Rule> getRules() {
 		log.info("RuleManagerImpl.getRules method");
 		try {
 			return this.ruleDao.getRules();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

	public Rule getGiveRuleByPromationId(Long promationId) {
		log.info("RuleManagerImpl.getRuleByPromationIdAndType method");
		Map map = new HashMap();
		map.put("promationId", promationId);
 		try {
 			return this.ruleDao.getGiveRule(map);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
	}

 }
