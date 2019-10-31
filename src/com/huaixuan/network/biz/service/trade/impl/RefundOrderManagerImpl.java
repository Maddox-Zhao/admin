package com.huaixuan.network.biz.service.trade.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.RefundOrderDao;
import com.huaixuan.network.biz.domain.trade.RefundOrder;
import com.huaixuan.network.biz.service.trade.RefundOrderManager;

/**
 * @version 3.2.0
 */
@Service("refundOrderManager")
public class RefundOrderManagerImpl implements RefundOrderManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private RefundOrderDao refundOrderDao;
/* @model: */
	public void addRefundOrder(RefundOrder refundOrderDao) {
		log.info("RefundOrderManagerImpl.addRefundOrder method");
		try {
			this.refundOrderDao.addRefundOrder(refundOrderDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editRefundOrder(RefundOrder refundOrder) {
		log.info("RefundOrderManagerImpl.editRefundOrder method");
		try {
			this.refundOrderDao.editRefundOrder(refundOrder);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeRefundOrder(Long refundOrderId) {
		log.info("RefundOrderManagerImpl.removeRefundOrder method");
		try {
			this.refundOrderDao.removeRefundOrder(refundOrderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public RefundOrder getRefundOrder(Long refundOrderId) {
		log.info("RefundOrderManagerImpl.getRefundOrder method");
		try {
			return this.refundOrderDao.getRefundOrder(refundOrderId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
/* @model: */
	public List<RefundOrder> getRefundOrders() {
		log.info("RefundOrderManagerImpl.getRefundOrders method");
		try {
			return this.refundOrderDao.getRefundOrders();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public void removeRefundOrderByRefund(String refundId) {
		log.info("RefundOrderManagerImpl.removeRefundOrderByRefund method");
		try {
			this.refundOrderDao.removeRefundOrderByRefund(refundId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public List<RefundOrder> getRefundOrderByRefundId(String refundId) {
		return refundOrderDao.getRefundOrderByRefundId(refundId);
	}
}
