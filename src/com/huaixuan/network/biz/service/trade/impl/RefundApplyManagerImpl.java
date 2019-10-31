package com.huaixuan.network.biz.service.trade.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.dao.trade.RefundApplyDao;
import com.huaixuan.network.biz.dao.trade.RefundApplyOrderDao;
import com.huaixuan.network.biz.domain.base.Constants;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.RefundApply;
import com.huaixuan.network.biz.domain.trade.RefundApplyOrder;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.RefundApplyQuery;
import com.huaixuan.network.biz.service.goods.UploadUtil;
import com.huaixuan.network.biz.service.trade.RefundApplyManager;
import com.huaixuan.network.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * 
 * @version 3.2.0
 */
@Service("refundApplyManager")
public class RefundApplyManagerImpl implements RefundApplyManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private RefundApplyDao refundApplyDao;
	@Autowired
	private UploadUtil uploadUtil;
	@Autowired
	private RefundApplyOrderDao refundApplyOrderDao;
	@Autowired
	private OrderDao orderDao;
/* @model: */
	public void addRefundApply(RefundApply refundApplyDao) {
		log.info("RefundApplyManagerImpl.addRefundApply method");
		try {
			this.refundApplyDao.addRefundApply(refundApplyDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void editRefundApply(RefundApply refundApply) {
		log.info("RefundApplyManagerImpl.editRefundApply method");
		try {
			this.refundApplyDao.editRefundApply(refundApply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
/* @model: */
	public void removeRefundApply(Long refundApplyId) {
		log.info("RefundApplyManagerImpl.removeRefundApply method");
		try {
			this.refundApplyDao.removeRefundApply(refundApplyId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * refundApplyId»ñµÃRefundApply
	 */
	public RefundApply getRefundApply(Long refundApplyId) {
		try {
			RefundApply ra = this.refundApplyDao.getRefundApply(refundApplyId);
			if (ra == null) {
				return null;
			}
			List<RefundApplyOrder> raos = refundApplyOrderDao.getRefundApplyOrdersByApplyId(ra.getId());
			for (RefundApplyOrder rao : raos) {
				Long orderId = rao.getOrderId();
				Order order = orderDao.getOrder(orderId);
				if (order != null) {
					rao.setGoodsAttr(order.getGoodsAttr());
					rao.setBuyAttr(order.getBuyAttr());
					rao.setGoodsTitle(order.getGoodsTitle());
					rao.setGoodsInstanceName(order.getGoodsInstanceName());
					rao.setGoodsNumber(order.getGoodsNumber());
				}
			}
			ra.setRefundApplyOrder(raos);
			return ra;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;

	}
/* @model: */
	public QueryPage getRefundApplys(RefundApplyQuery query, int currPage, int pageSize) {
		log.info("RefundApplyManagerImpl.getRefundApplys method");
		try {
			return this.refundApplyDao.getRefundApplys(query, currPage, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public boolean addRefundApply(RefundApply refundApply, File[] pics, String[] imagesFileName) {
		try {
			if (pics != null) {
				String goodsPicPath = "refundApplyOrder" + Constants.FILE_SEP
						+ DateUtil.getDateTime("yyyyMM", new Date());
				StringBuffer uploadFileNames = new StringBuffer();
				for (int i = 0; i < pics.length; i++) {
					String preName = String.valueOf(i);
					String fileName = uploadUtil.upload(pics[i], preName + imagesFileName[i], goodsPicPath);
					Thread.sleep(30);
					uploadFileNames.append(goodsPicPath + Constants.FILE_SEP + fileName + "|");
				}
				String uploadNames = uploadFileNames.toString();
				if (StringUtil.isNotBlank(uploadNames)) {
					refundApply.setApplyPics(uploadNames.substring(0, uploadNames.length() - 1));
				}
			}
			Long id = refundApplyDao.addRefundApply(refundApply);
			List<RefundApplyOrder> applyOrders = refundApply.getRefundApplyOrder();
			for (RefundApplyOrder ao : applyOrders) {
				ao.setApplyId(id);
				refundApplyOrderDao.addRefundApplyOrder(ao);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return true;
	}

	public int getRefundApplysCountParameterMap(RefundApplyQuery query) {
		try {
			return this.refundApplyDao.getRefundApplysCountParameterMap(query);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return 0;
	}

	public void updateRefundApply(RefundApply refundApply) {
		try {
			this.refundApplyDao.updateRefundApply(refundApply);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
