package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.trade.TradeFundDao;
import com.huaixuan.network.biz.domain.trade.TradeFund;
import com.huaixuan.network.biz.service.trade.TradeFundManager;

/**
 * 代码自动生成(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public class TradeFundManagerImpl implements TradeFundManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private TradeFundDao tradeFundDao;

	/* @model: */
	public void addTradeFund(TradeFund tradeFundDao) {
		log.info("TradeFundManagerImpl.addTradeFund method");
		try {
			this.tradeFundDao.addTradeFund(tradeFundDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void editTradeFund(TradeFund tradeFund) {
		log.info("TradeFundManagerImpl.editTradeFund method");
		try {
			this.tradeFundDao.editTradeFund(tradeFund);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/* @model: */
	public void removeTradeFund(Long tradeFundId) {
		log.info("TradeFundManagerImpl.removeTradeFund method");
		try {
			this.tradeFundDao.removeTradeFund(tradeFundId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/* @model: */
	public TradeFund getTradeFund(Long tradeFundId) {
		log.info("TradeFundManagerImpl.getTradeFund method");
		try {
			return this.tradeFundDao.getTradeFund(tradeFundId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/* @model: */
	public List<TradeFund> getTradeFunds() {
		log.info("TradeFundManagerImpl.getTradeFunds method");
		try {
			return this.tradeFundDao.getTradeFunds();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
