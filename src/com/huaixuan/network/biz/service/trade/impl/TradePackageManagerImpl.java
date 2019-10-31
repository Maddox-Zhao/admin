package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.TradePackageDao;
import com.huaixuan.network.biz.domain.trade.TradePackage;
import com.huaixuan.network.biz.service.trade.TradePackageManager;

/**
 * @version 3.2.0
 */
@Service("tradePackageManager")
public class TradePackageManagerImpl implements TradePackageManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private TradePackageDao tradePackageDao;
/* @model: */
	public void addTradePackage(TradePackage tradePackageDao) {
		log.info("TradePackageManagerImpl.addTradePackage method");
		try {
			this.tradePackageDao.addTradePackage(tradePackageDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editTradePackage(TradePackage tradePackage) {
		log.info("TradePackageManagerImpl.editTradePackage method");
		try {
			this.tradePackageDao.editTradePackage(tradePackage);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeTradePackage(Long tradePackageId) {
		log.info("TradePackageManagerImpl.removeTradePackage method");
		try {
			this.tradePackageDao.removeTradePackage(tradePackageId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public TradePackage getTradePackage(Long tradePackageId) {
		log.info("TradePackageManagerImpl.getTradePackage method");
		try {
			return this.tradePackageDao.getTradePackage(tradePackageId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<TradePackage> getTradePackages() {
		log.info("TradePackageManagerImpl.getTradePackages method");
		try {
			return this.tradePackageDao.getTradePackages();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public List<String> getTidByMergeTid(String mergeTid) {
		return this.tradePackageDao.getTidByMergeTid(mergeTid);
	}

}
