package com.huaixuan.network.biz.service.goods.impl;

 import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.RuleDefDao;
import com.huaixuan.network.biz.domain.goods.RuleDef;
import com.huaixuan.network.biz.service.goods.RuleDefManager;


@Service("ruleDefManager")
 public class RuleDefManagerImpl implements RuleDefManager {
	protected Log    log   = LogFactory.getLog(this.getClass());

	@Autowired
 	private RuleDefDao ruleDefDao;

	@Override
 	public void addRuleDef(RuleDef ruleDefDao) {
 		log.info("RuleDefManagerImpl.addRuleDef method");
 		try {
 			 this.ruleDefDao.addRuleDef(ruleDefDao);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public void editRuleDef(RuleDef ruleDef) {
 		log.info("RuleDefManagerImpl.editRuleDef method");
 		try {
 			this.ruleDefDao.editRuleDef(ruleDef);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public void removeRuleDef(Long ruleDefId) {
 		log.info("RuleDefManagerImpl.removeRuleDef method");
 		try {
 			this.ruleDefDao.removeRuleDef(ruleDefId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 	}

	@Override
 	public RuleDef getRuleDef(Long ruleDefId) {
 		log.info("RuleDefManagerImpl.getRuleDef method");
 		try {
 			return this.ruleDefDao.getRuleDef(ruleDefId);
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}

	@Override
 	public List<RuleDef> getRuleDefs() {
 		log.info("RuleDefManagerImpl.getRuleDefs method");
 		try {
 			return this.ruleDefDao.getRuleDefs();
 		} catch (Exception e) {
 			log.error(e.getMessage());
 		}
 		return null;
 	}
 }
