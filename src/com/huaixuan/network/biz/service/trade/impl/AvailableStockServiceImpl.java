/**
 * @Title: AvailableStockServiceImpl.java
 * @Package com.huaixuan.network.biz.service.impl
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ÏÂÎç07:06:50
 * @version V1.0
 */
package com.huaixuan.network.biz.service.trade.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.goods.AvailableStockDao;
import com.huaixuan.network.biz.domain.goods.AvailableStock;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.service.stock.AvailableStockService;

/**
 * @ClassName: AvailableStockServiceImpl
 * @Description: TODO
 *
 */
@Service("availableStockService")
public class AvailableStockServiceImpl implements AvailableStockService{
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private AvailableStockDao availableStockDao;
	
	@Override
	public AvailableStock getAvailableStockByPramas(Map<String, Object> pramas) {
		log.info("AvailableStockServiceImpl.getAvailableStockByPramas method");
		try {
			return this.availableStockDao.getAvailableStockByPramas(pramas);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	@Override
	public void updateAvaiStoEsNumByPramas(Map<String, Object> pramas) {
		log.info("AvailableStockServiceImpl.updateAvaiStoEsNumByPramas method");
		try {
			this.availableStockDao.updateAvaiStoEsNumByPramas(pramas);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	@Override
	public void updateAvaiStoEsNumZero() {
		log.info("AvailableStockServiceImpl.updateAvaiStoEsNumZero method");
		try {
			this.availableStockDao.updateAvaiStoEsNumZero(null);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	
	
	@Override
	public void addhxAvaiStoByPramas(AvailableStock availableStock) {
		log.info("AvailableStockServiceImpl.addhxAvaiStoByPramas method");
		try {
			this.availableStockDao.addClientAvailableStock(availableStock);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}
	

}
