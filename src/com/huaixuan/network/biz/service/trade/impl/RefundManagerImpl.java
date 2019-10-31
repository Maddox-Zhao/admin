package com.huaixuan.network.biz.service.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.domain.account.Account;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumRefundType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AccountManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.TradeAgentManager;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;
import com.hundsun.itrans.biz.manager.AccountTransManager;
import com.hundsun.itrans.biz.model.AccountTransReq;
import com.hundsun.itrans.biz.model.AccountTransResult;
import com.hundsun.itrans.common.util.Money;

@Service("refundManager")
public class RefundManagerImpl implements RefundManager {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private RefundDao refundDao;

	// public GoodsDao goodsDao;
	//
	// public OrderDao orderDao;
	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private TradeAgentManager tradeAgentManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	private AccountTransManager accountTransManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Value("${inAccountNo}")
	private String inAccountNo;

	//
	/* @model: */
	// public void addRefund(Refund refundDao) {
	// log.info("RefundManagerImpl.addRefund method");
	// try {
	// this.refundDao.addRefund(refundDao);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void editRefund(Refund refund) {
	// log.info("RefundManagerImpl.editRefund method");
	// try {
	// this.refundDao.editRefund(refund);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//
	/* @model: */
	// public void removeRefund(Long refundId) {
	// log.info("RefundManagerImpl.removeRefund method");
	// try {
	// this.refundDao.removeRefund(refundId);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }

	/* @model: */
	public Refund getRefund(Long refundId) {
		return this.refundDao.getRefund(refundId);
	}

	// /* @model:*/
	// public List<Refund> getRefunds() {
	// log.info("RefundManagerImpl.getRefunds method");
	// try {
	// return this.refundDao.getRefunds();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }
	//
	// public void updateRefundStatusAndNote(Refund refund) {
	// this.refundDao.updateRefundStatusAndNote(refund);
	// }
	//
	// public List<Order> getGoodsListByRefundId(String refundId) {
	// Map<String, Object> pramas = new HashMap<String, Object>();
	// pramas.put("refundId", refundId);
	// return this.orderDao.getOrdersListByRefundId(pramas);
	// }

	@SuppressWarnings("unchecked")
	@Transactional
	public String updateRefundStatusByRefund(final Refund refund, final String agreeTag) {
		return (String) transactionTemplate.execute(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				Object savePoint = status.createSavepoint();
				try {
					Boolean isTradeClose = false;
					Boolean isTradeSuccess = false;
					if (refund == null) {
						return "refund is null";
					}
					Map<String, Object> pramas = new HashMap<String, Object>();
					pramas.put("tid", refund.getTid());
					Trade t = tradeDao.getTradeByTid(refund.getTid());
					if (t == null) {
						return "trade is null";
					}

					// zhangwy 获取一级仓库信息
					DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(t.getDepFirstId());

					if (EnumTradeStatus.TRADE_FINISH.getKey().equals(t.getStatus())) {
						isTradeSuccess = true;
					}
					// 拒绝，
					if ("0".equalsIgnoreCase(agreeTag)) {
						refund.setStatus(EnumRefundStatus.Seller_Refuse_Refund.getKey());
						// deleted by chenyan 2009/08/30 start 状态不修改
						// pramas.put("status",
						// EnumTradeStatus.WAIT_BUYER_CONFIRM_GOODS.getKey());
						// this.tradeDao.updateTradeByRefund(pramas);
						// deleted by chenyan 2009/08/30 end
					}
					// 同意退款，不进行金额退款，更改订单状态为"财务退款"
					if ("1".equalsIgnoreCase(agreeTag)) {
						refund.setStatus(EnumRefundStatus.Financer_Refund.getKey());// 修改订单状态为"财务退款"
						// 更新库存和销售量
						if (!isTradeSuccess) {
							if (null != t) {
								String tid = t.getTid();
								Map<String, Object> paramMap = new HashMap<String, Object>();
								paramMap.put("tid", tid);
								List<Order> orderList = orderManager.getOrdersByParameterMap(paramMap);
								for (Order order : orderList) {
									if (depositoryFirst != null && (!depositoryFirst.getType().equals("w"))) {
										goodsInstanceManager.updateAmountForTwo(order.getGoodsInstanceId(),
												order.getGoodsId(), order.getGoodsNumber(), t.getDepFirstId(), true);
										// 取消订单时销售数量减少
									}
									goodsManager.updateSaleNumberById(order.getGoodsId(), 0 - order.getGoodsNumber());
								}
							}
							pramas.put("status", EnumTradeStatus.TRADE_CLOSE.getKey());
							if (!isTradeSuccess) {
								tradeDao.updateTradeByRefund(pramas);
							}
						}
						isTradeClose = true;
					}
					// 同意
					if ("2".equalsIgnoreCase(agreeTag)) {
						refund.setStatus(EnumRefundStatus.Wait_Buyer_Return_Goods.getKey());
					}

					// 卖家确认收到,申请货成功，关闭交易
					if ("3".equalsIgnoreCase(agreeTag)) {
						refund.setStatus(EnumRefundStatus.Goods_Confirm_Success.getKey());

						if (refund.getType().equals(EnumRefundType.REFUND_ALL_GOODS.getKey())) {
							pramas.put("status", EnumTradeStatus.TRADE_CLOSE.getKey());
							if (!isTradeSuccess) {
								tradeDao.updateTradeByRefund(pramas);
							}
							isTradeClose = true;
						}
					}
					refundDao.updateRefundStatusAndNote(refund);
					// 后台退款列表中，点确认退款后，直接退钱给买家
					if ("3".equalsIgnoreCase(agreeTag)) {
						if (!returnGoodsMoney(refund)) {
							status.rollbackToSavepoint(savePoint);
							return "returnGoodsMoney fail";
						}
					}

					if (isTradeClose || isTradeSuccess) {

						tradeAgentManager.updateAgentTradeWithAll(refund.getTid(),
								EnumTradeStatus.TRADE_CLOSE.getKey(), null);
					}
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("updateRefundStatusByRefund fail:" + e.getMessage());
					return e.getMessage();
				}
				return null;
			}
		});
	}

	/**
	 * 退货退款
	 * 
	 * @throws Exception
	 */
	public boolean returnGoodsMoney(Refund refund) throws Exception {
		AccountTransReq req = new AccountTransReq();
		Account transAccount = accountManager.getBuyerTransAccount(refund.getBuyId());
		if (transAccount != null) {
			req.setInAccountNo(transAccount.getAccountNo());
		}
		//退货退款金额为0的时候
		if(refund.getRefundAmount().equals(0.0)){
            return true;
        }
		req.setOutAccountNo(inAccountNo);
		req.setInnerBizNo(refund.getRefundId());
		req.setAmount(new Money(refund.getRefundAmount()));
		req.setSubTransCode(EnumSubTransCode.TXCODE_TRANSFER_RETURN_GOODS);
		AccountTransResult result = accountTransManager.execute(req);
		if (!result.getCode().equals(AccountTransResult.TXN_RESULT_SUCCESS.getCode())) {
			log.error("method returnGoodsMoney AccountTransResult fail:" + result.getCode() + "[" + result.getMessage()
					+ "]");
			return false;
		}
		return true;
	}

	public List<Refund> getRefundByParameterMap(Map<String, Object> parameterMap) {
		return refundDao.getRefundByParameterMap(parameterMap);
	}

	public QueryPage getRefundByParameterMap(Map<String, Object> parameterMap, int currPage, int pageSize) {
		return refundDao.getRefundByParameterMap(parameterMap, currPage, pageSize);
	}

	// public int getRefundCountByParameterMap(Map<String, String> parMap) {
	// try {
	// return this.refundDao.getRefundCountByParameterMap(parMap);
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// return 0;
	// }

	public Refund getRefundByOrder(String tid) {
		log.info("RefundManagerImpl.getRefundByOrder method");
		return this.refundDao.getRefundByOrder(tid);
	}

	public Refund getRefundByRefundId(String refundId) {
		log.info("RefundManagerImpl.getRefundByOrder method");
		return refundDao.getRefundByRefundId(refundId);
	}

	// public List<Refund> getRefundByStatus(Map parameterMap, Page page) {
	// return this.refundDao.getRefundByStatus(parameterMap, page);
	// }
	//
	// public int getRefundCountByStatus(Map parMap) {
	// return this.refundDao.getRefundCountByStatus(parMap);
	// }
	//
	// public List<Refund> getRefundByTradeStatus(String stauts, String isRefund, Long userId) {
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// paramMap.put("status", stauts);
	// paramMap.put("buyId", userId);
	// paramMap.put("isRefund", isRefund);
	// try {
	// return this.refundDao.getRefundByTradeStatus(paramMap);
	// } catch (SQLException e) {
	// log.error(e.getMessage());
	// }
	// return null;
	// }

	public void updateMessageForRefundByTradeId(String refundId, String message) {
		refundDao.updateMessageForRefundByTradeId(refundId, message);
	}

}
