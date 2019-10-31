package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huaixuan.network.biz.dao.trade.RefundApplyOrderDao;
import com.huaixuan.network.biz.domain.trade.RefundApplyOrder;
import com.huaixuan.network.biz.service.trade.RefundApplyOrderManager;

/**
 * 
 * @version 3.2.0
 */
public class RefundApplyOrderManagerImpl implements RefundApplyOrderManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private RefundApplyOrderDao refundApplyOrderDao;
/* @model: */
	public void addRefundApplyOrder(RefundApplyOrder refundApplyOrderDao) {
		log.info("RefundApplyOrderManagerImpl.addRefundApplyOrder method");
		try {
			this.refundApplyOrderDao.addRefundApplyOrder(refundApplyOrderDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editRefundApplyOrder(RefundApplyOrder refundApplyOrder) {
		log.info("RefundApplyOrderManagerImpl.editRefundApplyOrder method");
		try {
			this.refundApplyOrderDao.editRefundApplyOrder(refundApplyOrder);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeRefundApplyOrder(Long refundApplyOrderId) {
		log.info("RefundApplyOrderManagerImpl.removeRefundApplyOrder method");
		try {
			this.refundApplyOrderDao.removeRefundApplyOrder(refundApplyOrderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public RefundApplyOrder getRefundApplyOrder(Long refundApplyOrderId) {
		log.info("RefundApplyOrderManagerImpl.getRefundApplyOrder method");
		try {
			return this.refundApplyOrderDao.getRefundApplyOrder(refundApplyOrderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<RefundApplyOrder> getRefundApplyOrders() {
		log.info("RefundApplyOrderManagerImpl.getRefundApplyOrders method");
		try {
			return this.refundApplyOrderDao.getRefundApplyOrders();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
