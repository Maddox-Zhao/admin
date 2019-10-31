package com.huaixuan.network.biz.service.storage.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.dao.goods.GoodsDao;
import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.dao.storage.InDepositoryDao;
import com.huaixuan.network.biz.dao.storage.InDetailDao;
import com.huaixuan.network.biz.dao.storage.OutDepositoryDao;
import com.huaixuan.network.biz.dao.storage.OutDetailDao;
import com.huaixuan.network.biz.dao.trade.OrderDao;
import com.huaixuan.network.biz.dao.trade.RefundDao;
import com.huaixuan.network.biz.dao.trade.RefundOrderDao;
import com.huaixuan.network.biz.dao.trade.TradeDao;
import com.huaixuan.network.biz.dao.trade.TradePackageDao;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.PackageTrade;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.RefundOrder;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradePackage;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumRefundType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.InAndOutDepositoryManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.PackageManager;
import com.huaixuan.network.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author liuchao
 * @version $Id: InAndOutDepositoryImpl.java,v 0.1 2009-7-20 
 */
@Service("inAndOutDepositoryManager")
public class InAndOutDepositoryManagerImpl implements InAndOutDepositoryManager {

	@Autowired
	private TradeDao tradeDao;
	@Autowired
	private TradePackageDao tradePackageDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CodeManager codeManager;
	@Autowired
	private OutDepositoryDao outDepositoryDao;
	@Autowired
	private InDepositoryDao inDepositoryDao;
	@Autowired
	private OutDetailDao outDetailDao;
	@Autowired
	private InDetailDao inDetailDao;
	@Autowired
	private GoodsInstanceDao goodsInstanceDao;
	@Autowired
	private RefundDao refundDao;
	@Autowired
	private RefundOrderDao refundOrderDao;
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private PackageManager packageManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	private DepLocationManager depLocationManager;
	@Autowired
	private StorageManager storageManager;

	/**
	 * @param tradeId
	 * @see com.hundsun.bible.facade.ios.InAndOutDepositoryManager#addInDepository(java.lang.Long)
	 */
	public void addInDepository(Long tradeId) {
		if (tradeId == null) {
			throw new NullPointerException("tradeId can't be null.");
		}
		// InDepository inDepository = new InDepository();
		// Trade trade = tradeDao.get(tradeId);
		// inDepository.setBillNum(obj);
		// inDepository.setCreater(obj);
		// inDepository.setGmtCreate(obj);
		// inDepository.setGmtModify(obj);
		// inDepository.setRelationNum(obj);
		// inDepository.setType(obj);

	}
	

	/**
	 * 配货判定
	 */
	@Override
	public boolean judgeFactStorage(String tid) {
		if (StringUtil.isBlank(tid)||StringUtil.isEmpty(tid)) {
			return false;
		}
		Trade trade = tradeDao.getTradeByTid(tid);
		List<Order> list = orderDao.getOrdersByTid(tid);
		//找寻这个一级仓库下的普通仓库的非盘点库位(仓库为普通仓库，不在盘点中；库位不在盘点中;仓库和库位都必须可用)
		List<DepLocation> locs= depLocationManager.getRightDeplocationByDepfirstId(trade.getDepFirstId());
		List<Long> locIds = new ArrayList<Long>();
		for(DepLocation tmp : locs){
			locIds.add(tmp.getId());
		}
		Map parMap = new HashMap();
		for(Order temp : list){
			parMap.put("goodsInstanceId", temp.getGoodsInstanceId());
			parMap.put("goodsId", temp.getGoodsId());
			parMap.put("depfirstId", trade.getDepFirstId());
			parMap.put("locIds", locIds);
			//找库存总量
			Long leftStorage = storageManager.getLeftStorageNumWithLoc(parMap);
			if(leftStorage < temp.getGoodsNumber()){
				return false;
			}
		}
		return true;
	}

	/**
	 * 合并配货判定
	 */
	@Override
	public boolean judgeFactStorageMerge(String[] tids) {
		if (tids == null || tids.length == 0) {
			return false;
		}
		List<Order> orderAll = new ArrayList<Order>();
		for(String tid : tids){
			List<Order> list = orderDao.getOrdersByTid(tid);
			for(Order temp : list){
				orderAll.add(temp);
			}
		}
		
		//去重复合并产品id相同的产品数量
		Collections.sort(orderAll);
		List<Order> newOrderAll = new ArrayList<Order>();
		Order order = new Order();
		int i = 0;
		while(i < orderAll.size()){
			if(i == 0){
				order = orderAll.get(i);
			}else{
				if(orderAll.get(i).getGoodsInstanceId().equals(orderAll.get(i-1).getGoodsInstanceId())){
					order.setGoodsNumber(order.getGoodsNumber() + orderAll.get(i).getGoodsNumber());
					if(i == (orderAll.size()-1)){
						newOrderAll.add(order);
					}
				}else{
					newOrderAll.add(order);
					order = new Order();
					order = orderAll.get(i);
				}
			}
			i++;
		}
		
		//找寻这个一级仓库下的普通仓库的非盘点库位(仓库为普通仓库，不在盘点中；库位不在盘点中;仓库和库位都必须可用)
		Trade trade = tradeDao.getTradeByTid(tids[0]);
		List<DepLocation> locs= depLocationManager.getRightDeplocationByDepfirstId(trade.getDepFirstId());
		List<Long> locIds = new ArrayList<Long>();
		for(DepLocation tmp : locs){
			locIds.add(tmp.getId());
		}
		Map parMap = new HashMap();
		for(Order temp : newOrderAll){
			parMap.put("goodsInstanceId", temp.getGoodsInstanceId());
			parMap.put("goodsId", temp.getGoodsId());
			parMap.put("depfirstId", trade.getDepFirstId());
			parMap.put("locIds", locIds);
			//找库存总量
			Long leftStorage = storageManager.getLeftStorageNumWithLoc(parMap);
			if(leftStorage < temp.getGoodsNumber()){
				return false;
			}
		}
		return true;
	}


	/**
	 * 配货
	 * 
	 * @param tradeId
	 *            @
	 * @see com.hundsun.bible.facade.ios.InAndOutDepositoryManager#addOutDepository(java.lang.Long)
	 */
	@Transactional
	public Long addOutDepository(String[] tIds, AdminAgent agent) {
		if (tIds == null || tIds.length == 0) {
			throw new NullPointerException("tradeId can't be null.");
		}
		// 合并订单改造（出库单关联单号为合并订单关联编号）modify by fanyj 2011-1-6
		String mergeTid = "HB" + tIds[0] + DateUtil.dateline();// 合并TID前加HB字符和时间戳
		// 出库表基础信息
		Trade trade = tradeDao.getTradeByTid(tIds[0]);
		OutDepository outDepository = new OutDepository();
		outDepository.setCreater(agent.getUsername());
		outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, "CK"));
		outDepository.setRelationNum(mergeTid);
		outDepository.setType(EnumOutDepository.OUT_SALES.getKey());
		outDepository.setStatus(EnumOutStatus.OUT_NEW.getKey());
		// 增加一级仓库标识
		if (trade.getDepFirstId() != null) {
			outDepository.setDepFirstId(trade.getDepFirstId());
		}
		// 批发订单
		// if("w".equals(trade.getIsWholesale())){
		// outDepository.setIsWholesale("w");
		// outDepository.setTid(trade.getTid());
		// }
		outDepository.setIsWholesale(trade.getIsWholesale());
		Long outId = outDepositoryDao.addOutDepository(outDepository);
		List<OutDetail> listOut = new ArrayList<OutDetail>();
		for (String tId : tIds) {
			// 增加合并订单关联记录
			TradePackage tradePackage = new TradePackage();
			tradePackage.setMergeTid(mergeTid);
			tradePackage.setTid(tId);
			tradePackageDao.addTradePackage(tradePackage);

			// 出库表详细信息
			List<Order> list = null;
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("tid", tId);
			list = orderDao.getOrdersByParameterMap(map);
			for (Order order : list) {
			    OutDetail outDetail = null;
				outDetail = new OutDetail();
				outDetail.setGoodsId(order.getGoodsId());
				outDetail.setGoodsInstanceId(order.getGoodsInstanceId());
				outDetail.setOutDepositoryId(outId.intValue());
				outDetail.setOutNum(order.getGoodsNumber());
				outDetail.setUnitPrice(order.getGoodsPrice());
				// 增加一级仓库标识
				if (trade.getDepFirstId() != null) {
					outDetail.setDepFirstId(trade.getDepFirstId());
				}
				listOut.add(outDetail);
			}
		}
		outDetailDao.addOutDetails(listOut);
		return outId;
	}

	// public static String getUsername() {
	// // User loginUser = null;
	// // Object obj = SecurityContextHolder.getContext().getAuthentication()
	// // .getPrincipal();
	// // // 注册成功之后将用户信息setDetails();
	// // Object objDatil = SecurityContextHolder.getContext()
	// // .getAuthentication().getDetails();
	// // if (obj instanceof User) {
	// // loginUser = (User) obj;
	// // }
	// //
	// // if (objDatil instanceof User) {
	// // loginUser = (User) objDatil;
	// // }
	// //
	// // if (loginUser != null) {
	// // return loginUser.getAccount();
	// // }
	// // return null;
	// AdminUser user = (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// return user == null ? null : user.getUsername();
	// }

	/**
	 * 换货
	 * 
	 * @param tid
	 * @param orderId
	 * @param goodsInsId
	 * @param goodsNum
	 *            @
	 * @see com.hundsun.bible.facade.ios.InAndOutDepositoryManager#addInAndOutDetail(java.lang.String, java.util.List,
	 *      java.util.List, java.lang.String)
	 */
	@Transactional
	private String changeGoods(String tid, String[] orderIds, String[] goodsInsIds, String[] goodsNum,
			String[] goodsChangePrice, String remark, Double shipFee, AdminAgent agent) {
		// 退款退货表中插入换货信息
		Trade trade = tradeDao.getTradeByTid(tid);

		// 获取订单中的一级仓库信息
		DepositoryFirst DepositoryFirst = depositoryFirstManager.getDepositoryById(trade.getDepFirstId());

		if (trade != null && trade.getStatus().equals(EnumTradeStatus.TRADE_FINISH.getKey())) {
			return "交易完成，不允许换货！";
		}
		Order order = null;
		Integer orderId = null;
		Integer goodInsId = null;
		RefundOrder refundOrder = null;
		GoodsInstance goodsInstance = null;
		List<RefundOrder> refunOrderList = new ArrayList<RefundOrder>();

		for (int i = 0; orderIds.length > i; i++) {
			if (StringUtil.isNotBlank(goodsNum[i]) && Long.valueOf(goodsNum[i]).longValue() > 0) {
				orderId = Integer.valueOf(orderIds[i]);
				order = orderDao.getOrder(orderId.longValue());
				refundOrder = new RefundOrder();
				refundOrder.setCreate(agent.getUsername());
				refundOrder.setCustomerId(trade.getBuyId());
				refundOrder.setCustomerName(trade.getBuyNick());
				refundOrder.setDueFee(Double.valueOf(0));
				refundOrder.setFactFee(Double.valueOf(0));
				refundOrder.setGmtCreate(new Date());
				refundOrder.setGmtModify(new Date());
				refundOrder.setGoodsId(order.getGoodsId());
				refundOrder.setRemark("");
				goodInsId = Integer.valueOf(goodsInsIds[i]);
				refundOrder.setGoodsInstanceId(goodInsId);
				refundOrder.setOrderId(order.getId());
				refundOrder.setPrice(Double.valueOf(goodsChangePrice[i]));
				refundOrder.setReason(remark);
				refundOrder.setRefAmount(Long.valueOf(goodsNum[i]).longValue());
				// refundOrder.setRefundId(String.valueOf(refundId));

				goodsInstance = goodsInstanceDao.getInstance(goodInsId.longValue());

				if (order.getGoodsId() != goodsInstance.getGoodsId()) {
					return order.getGoodsTitle()
							+ ",换货的对象需要是同一个商品！";
				}
				// if (order.getGoodsPrice() != goodsInstance.getSellPrice()) {
				// return order.getGoodsTitle()
				// +
				// "&#44;&#25442;&#36135;&#30340;&#23545;&#35937;&#20215;&#26684;&#38656;&#35201;&#30456;&#21516;&#65281;";
				// }
				if (goodsInstance.getExistNum() == null || goodsInstance.getExistNum().intValue() < 1) {
					return order.getGoodsTitle()
							+ ",换货的产品库存为0！";
				}
				refunOrderList.add(refundOrder);
			}
		}

		Refund refund = new Refund();

		refund.setAmount(Double.valueOf(0));
		refund.setBuyId(trade.getBuyId());
		refund.setBuyNick(trade.getBuyNick());
		refund.setGmtCreate(new Date());
		refund.setGmtModify(new Date());
		refund.setGoodsAmount(trade.getGoodsAmount());
		refund.setIsGoodsRecevied("2");
		refund.setIsGoodsUntread("1");
		if (StringUtil.isBlank(remark)) {
			remark = "";
		}
		refund.setNote(remark);
		refund.setRefundAmount(Double.valueOf(0));
		refund.setRefundId(codeManager.buildCodeByDateAndType(CodeManager.REFUND_CODE, 6, "HH"));
		refund.setSellerId(trade.getSellerId());
		refund.setSellerNick(trade.getSellerNick());
		refund.setDepFirstId(trade.getDepFirstId());
		if (shipFee == null) {
			refund.setShippingAmount(trade.getShippingAmount());
		} else {
			refund.setShippingAmount(shipFee);
		}

		refund.setShopId(trade.getShopId());
		refund.setStatus(EnumRefundStatus.Wait_Seller_Confirm_Goods.getKey());
		refund.setTid(tid);
		refund.setType(EnumRefundType.CHANGE_GOODS.getKey());
		refund.setCreater(agent.getUsername());

		Long refundId = refundDao.addRefund(refund);

		for (RefundOrder tmp : refunOrderList) {
			tmp.setRefundId(refund.getRefundId());
		}

		refundOrderDao.addRefundOrders(refunOrderList);

		// 入库
		InDepository inDepository = new InDepository();
		inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
		inDepository.setCreater(agent.getUsername());
		inDepository.setRelationNum(refund.getRefundId());
		inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
		inDepository.setType(EnumInDepository.IN_SALES_CHANGE.getKey());
		inDepository.setDepFirstId(refund.getDepFirstId());
		Long inId = inDepositoryDao.addInDepository(inDepository);

		InDetail inDetail = null;
		Integer inOrderId = null;
		List<InDetail> inDetailList = new ArrayList<InDetail>();
		for (int j = 0; orderIds.length > j; j++) {
			if (StringUtil.isNotBlank(goodsNum[j]) && Long.valueOf(goodsNum[j]).longValue() > 0) {
				inOrderId = Integer.valueOf(orderIds[j]);
				order = orderDao.getOrder(inOrderId.longValue());
				inDetail = new InDetail();
				inDetail.setAmount(Long.valueOf(goodsNum[j]).longValue());
				inDetail.setGoodsId(order.getGoodsId());
				inDetail.setGoodsInstanceId(order.getGoodsInstanceId());
				inDetail.setUnitPrice(Double.valueOf(goodsChangePrice[j]));
				inDetail.setInDepositoryId(inId);
				inDetail.setDepFirstId(refund.getDepFirstId());
				inDetailList.add(inDetail);
			}
		}
		inDetailDao.addInDetails(inDetailList);

		// 出库
		OutDepository outDepository = new OutDepository();
		outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, "CK"));
		outDepository.setCreater(agent.getUsername());
		outDepository.setRelationNum(refund.getRefundId());
		outDepository.setStatus(EnumOutStatus.OUT_NEW.getKey());
		outDepository.setType(EnumOutDepository.OUT_SALES_CHANGE.getKey());
		outDepository.setDepFirstId(refund.getDepFirstId());
		Long outId = outDepositoryDao.addOutDepository(outDepository);

		OutDetail outDetail = null;
		Integer orderOut = null;
		Integer goodsInstId = null;
		List<OutDetail> outDetailList = new ArrayList<OutDetail>();
		for (int ii = 0; orderIds.length > ii; ii++) {
			if (StringUtil.isNotBlank(goodsNum[ii]) && Long.valueOf(goodsNum[ii]).longValue() > 0) {
				orderOut = Integer.valueOf(orderIds[ii]);
				goodsInstId = Integer.valueOf(goodsInsIds[ii]);
				order = orderDao.getOrder(orderOut.longValue());
				outDetail = new OutDetail();
				outDetail.setGoodsId(order.getGoodsId());
				outDetail.setGoodsInstanceId(goodsInstId);
				outDetail.setOutDepositoryId(outId);
				outDetail.setOutNum(Long.valueOf(goodsNum[ii]).longValue());
				outDetail.setUnitPrice(Double.valueOf(goodsChangePrice[ii]));
				outDetail.setDepFirstId(refund.getDepFirstId());
				outDetailList.add(outDetail);
				if (!DepositoryFirst.getType().equals("w")) {
					// 出库完成，要更新可用库存，减去1
					goodsInstanceManager.updateAmountForTwo(new Long(goodsInstId.longValue()), order.getGoodsId(),
							new Long(0 - Long.valueOf(goodsNum[ii]).longValue()), outDetail.getDepFirstId(), true);
				}
				goodsDao.updateSaleNumberById(order.getGoodsId(), new Long(goodsNum[ii]));
			}
		}
		outDetailDao.addOutDetails(outDetailList);

		return "success";
	}

	/**
	 * 换退货表中的货
	 * 
	 * @param tid
	 * @param orderId
	 * @param goodsInsId
	 * @param goodsNum
	 *            @
	 * @see com.hundsun.bible.facade.ios.InAndOutDepositoryManager#addInAndOutDetail(java.lang.String, java.util.List,
	 *      java.util.List, java.lang.String)
	 */
	@Transactional
	private String changeRefundGoods(String tid, String[] refundorderIds, String[] goodsInsIds, String[] goodsNum,
			String[] goodsChangePrice, String remark, Double shipFee, AdminAgent agent) {
		// 退款退货表中插入换货信息
		Trade trade = tradeDao.getTradeByTid(tid);

		// 获取订单中的一级仓库信息
		DepositoryFirst DepositoryFirst = depositoryFirstManager.getDepositoryById(trade.getDepFirstId());

		if (trade != null && trade.getStatus().equals(EnumTradeStatus.TRADE_FINISH.getKey())) {
			return "交易完成，不允许换货！";
		}
		Integer refundorderId = null;
		Integer goodInsId = null;
		RefundOrder refundOrder = null;
		GoodsInstance goodsInstance = null;
		List<RefundOrder> refunOrderList = new ArrayList<RefundOrder>();
		List<RefundOrder> refunOrderListOld = new ArrayList<RefundOrder>();
		String oldRefundId = "";
		for (int i = 0; refundorderIds.length > i; i++) {
			if (StringUtil.isNotBlank(goodsNum[i]) && Long.valueOf(goodsNum[i]).longValue() > 0) {

				refundorderId = Integer.valueOf(refundorderIds[i]);
				RefundOrder refundOrderOld = refundOrderDao.getRefundOrder(refundorderId.longValue());
				refundOrderOld.setRefAmountLeft(refundOrderOld.getRefAmountLeft()
						- Long.valueOf(goodsNum[i]).longValue());
				oldRefundId = refundOrderOld.getRefundId();
				refundOrder = new RefundOrder();
				refundOrder.setCreate(agent.getUsername());
				refundOrder.setCustomerId(trade.getBuyId());
				refundOrder.setCustomerName(trade.getBuyNick());
				refundOrder.setDueFee(Double.valueOf(0));
				refundOrder.setFactFee(Double.valueOf(0));
				refundOrder.setGmtCreate(new Date());
				refundOrder.setGmtModify(new Date());
				refundOrder.setGoodsId(refundOrderOld.getGoodsId());
				refundOrder.setRemark("");
				goodInsId = Integer.valueOf(goodsInsIds[i]);
				refundOrder.setGoodsInstanceId(goodInsId);
				// refundOrder.setOrderId(0 - refundOrderOld.getId());
				refundOrder.setRefundOrderId(refundOrderOld.getId());
				refundOrder.setPrice(Double.valueOf(goodsChangePrice[i]));
				refundOrder.setReason(remark);
				refundOrder.setRefAmount(Long.valueOf(goodsNum[i]).longValue());
				// refundOrder.setRefundId(String.valueOf(refundId));

				goodsInstance = goodsInstanceDao.getInstance(goodInsId.longValue());

				if (refundOrderOld.getGoodsId() != goodsInstance.getGoodsId()) {
					GoodsInstance g = goodsInstanceManager.getInstance(refundOrderOld.getGoodsInstanceId());
					if (g != null) {
						refundOrderOld.setGoodsTitle(g.getInstanceName());
					}
					return refundOrderOld.getGoodsTitle()
							+ ",换货的对象需要是同一个商品！";
				}
				// if (order.getGoodsPrice() != goodsInstance.getSellPrice()) {
				// return order.getGoodsTitle()
				// +
				// "&#44;&#25442;&#36135;&#30340;&#23545;&#35937;&#20215;&#26684;&#38656;&#35201;&#30456;&#21516;&#65281;";
				// }
				if (goodsInstance.getExistNum() == null || goodsInstance.getExistNum().intValue() < 1) {
					GoodsInstance g = goodsInstanceManager.getInstance(refundOrderOld.getGoodsInstanceId());
					if (g != null) {
						refundOrderOld.setGoodsTitle(g.getInstanceName());
					}
					return refundOrderOld.getGoodsTitle()
							+ ",换货的产品库存为0！";
				}
				refunOrderList.add(refundOrder);
				refunOrderListOld.add(refundOrderOld);
			}
		}

		Refund refund = new Refund();

		refund.setAmount(Double.valueOf(0));
		refund.setBuyId(trade.getBuyId());
		refund.setBuyNick(trade.getBuyNick());
		refund.setGmtCreate(new Date());
		refund.setGmtModify(new Date());
		refund.setGoodsAmount(trade.getGoodsAmount());
		refund.setIsGoodsRecevied("2");
		refund.setIsGoodsUntread("1");
		if (StringUtil.isBlank(remark)) {
			remark = "";
		}
		refund.setNote(remark);
		refund.setRefundAmount(Double.valueOf(0));
		refund.setRefundId(codeManager.buildCodeByDateAndType(CodeManager.REFUND_CODE, 6, "HH"));
		refund.setSellerId(trade.getSellerId());
		refund.setSellerNick(trade.getSellerNick());
		refund.setDepFirstId(trade.getDepFirstId());
		if (shipFee == null) {
			refund.setShippingAmount(trade.getShippingAmount());
		} else {
			refund.setShippingAmount(shipFee);
		}

		refund.setShopId(trade.getShopId());
		refund.setStatus(EnumRefundStatus.Wait_Seller_Confirm_Goods.getKey());
		refund.setTid(tid);
		refund.setType(EnumRefundType.CHANGE_GOODS.getKey());
		refund.setCreater(agent.getUsername());
		refund.setRelRefundId(oldRefundId);

		Long refundId = refundDao.addRefund(refund);

		for (RefundOrder tmp : refunOrderList) {
			tmp.setRefundId(refund.getRefundId());
		}

		refundOrderDao.addRefundOrders(refunOrderList);

		for (RefundOrder old : refunOrderListOld) {
			refundOrderDao.editRefundOrder(old);
		}

		// 入库
		InDepository inDepository = new InDepository();
		inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
		inDepository.setCreater(agent.getUsername());
		inDepository.setRelationNum(refund.getRefundId());
		inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
		inDepository.setType(EnumInDepository.IN_SALES_CHANGE.getKey());
		inDepository.setDepFirstId(refund.getDepFirstId());
		Long inId = inDepositoryDao.addInDepository(inDepository);

		InDetail inDetail = null;
		Integer inOrderId = null;
		List<InDetail> inDetailList = new ArrayList<InDetail>();
		for (int j = 0; refundorderIds.length > j; j++) {
			if (StringUtil.isNotBlank(goodsNum[j]) && Long.valueOf(goodsNum[j]).longValue() > 0) {
				inOrderId = Integer.valueOf(refundorderIds[j]);
				RefundOrder refundOrderOld = refundOrderDao.getRefundOrder(inOrderId.longValue());
				inDetail = new InDetail();
				inDetail.setAmount(Long.valueOf(goodsNum[j]).longValue());
				inDetail.setGoodsId(refundOrderOld.getGoodsId());
				inDetail.setGoodsInstanceId(refundOrderOld.getGoodsInstanceId());
				inDetail.setUnitPrice(Double.valueOf(goodsChangePrice[j]));
				inDetail.setInDepositoryId(inId);
				inDetail.setDepFirstId(refund.getDepFirstId());
				inDetailList.add(inDetail);
			}
		}
		inDetailDao.addInDetails(inDetailList);

		// 出库
		OutDepository outDepository = new OutDepository();
		outDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.CHUKU_CODE, 6, "CK"));
		outDepository.setCreater(agent.getUsername());
		outDepository.setRelationNum(refund.getRefundId());
		outDepository.setStatus(EnumOutStatus.OUT_NEW.getKey());
		outDepository.setType(EnumOutDepository.OUT_SALES_CHANGE.getKey());
		outDepository.setDepFirstId(refund.getDepFirstId());
		Long outId = outDepositoryDao.addOutDepository(outDepository);

		OutDetail outDetail = null;
		Integer orderOut = null;
		Integer goodsInstId = null;
		List<OutDetail> outDetailList = new ArrayList<OutDetail>();
		for (int ii = 0; refundorderIds.length > ii; ii++) {
			if (StringUtil.isNotBlank(goodsNum[ii]) && Long.valueOf(goodsNum[ii]).longValue() > 0) {
				orderOut = Integer.valueOf(refundorderIds[ii]);
				goodsInstId = Integer.valueOf(goodsInsIds[ii]);
				RefundOrder refundOrderOld = refundOrderDao.getRefundOrder(orderOut.longValue());
				outDetail = new OutDetail();
				outDetail.setGoodsId(refundOrderOld.getGoodsId());
				outDetail.setGoodsInstanceId(goodsInstId);
				outDetail.setOutDepositoryId(outId);
				outDetail.setOutNum(Long.valueOf(goodsNum[ii]).longValue());
				outDetail.setUnitPrice(Double.valueOf(goodsChangePrice[ii]));
				outDetail.setDepFirstId(refund.getDepFirstId());
				outDetailList.add(outDetail);

				if (!DepositoryFirst.getType().equals("w")) {
					// 出库完成，要更新可用库存，减去1
					goodsInstanceManager.updateAmountForTwo(new Long(goodsInstId.longValue()),
							refundOrderOld.getGoodsId(), new Long(0 - Long.valueOf(goodsNum[ii]).longValue()),
							outDetail.getDepFirstId(), true);
				}
				goodsDao.updateSaleNumberById(refundOrderOld.getGoodsId(), new Long(goodsNum[ii]));
			}
		}
		outDetailDao.addOutDetails(outDetailList);

		return "success";
	}

	/**
	 * 退货
	 * 
	 * @param tid
	 * @param orderIds
	 * @param goodsNum
	 * @return @
	 */
	@Transactional
	private String refundGoods(String tid, Integer[] orderIds, Integer[] goodsNum, String remark, Double[] duFees,
			Double[] factFees) {

		// // 是否全部退货
		// int c = 0;
		// // 退款退货表中插入换货信息
		// Trade trade = tradeDao.getTradeByTid(tid);
		// if (trade != null && trade.getStatus().equals(EnumTradeStatus.TRADE_FINISH.getKey())) {
		// return "&#x4EA4;&#x6613;&#x5B8C;&#x6210;&#xFF0C;&#x4E0D;&#x5141;&#x8BB8;&#x64CD;&#x4F5C;&#xFF01;";
		// }
		// Refund refund = new Refund();
		// refund.setAmount(Double.valueOf(0));
		// refund.setBuyId(trade.getBuyId());
		// refund.setBuyNick(trade.getBuyNick());
		// refund.setGmtCreate(new Date());
		// refund.setGmtModify(new Date());
		// refund.setGoodsAmount(trade.getGoodsAmount());
		// refund.setIsGoodsRecevied("2");
		// refund.setIsGoodsUntread("1");
		// if (StringUtil.isBlank(remark)) {
		// remark = "";
		// }
		// refund.setNote(remark);
		// refund.setRefundAmount(Double.valueOf(0));
		// refund.setRefundId(codeManager.buildCodeByDateAndType(CodeManager.REFUND_CODE, 6, "TH"));
		// refund.setSellerId(trade.getSellerId());
		// refund.setSellerNick(trade.getSellerNick());
		// refund.setShippingAmount(trade.getShippingAmount());
		// refund.setShopId(trade.getShopId());
		// refund.setStatus(EnumRefundStatus.Wait_Seller_Confirm_Goods.getKey());
		// refund.setTid(tid);
		// refund.setType(EnumRefundType.REFUND_GOODS.getKey());
		// refund.setCreater(getUsername());
		//
		// Long refundId = refundDao.addRefund(refund);
		//
		// Order order = null;
		// Integer orderId = null;
		// List<RefundOrder> refundList = new ArrayList<RefundOrder>();
		// RefundOrder refundOrder = null;
		// double refundPrice = 0;
		// for (int i = 0; orderIds.length > i; i++) {
		// orderId = orderIds[i];
		// order = orderDao.getOrder(orderId.longValue());
		// if (order != null && order.getGoodsNumber() != goodsNum[i].longValue()) {
		// c++;
		// }
		// if (goodsNum[i].intValue() > 0) {
		//
		// refundOrder = new RefundOrder();
		// refundOrder.setCreate(getUsername());
		// refundOrder.setCustomerId(trade.getBuyId());
		// refundOrder.setCustomerName(trade.getBuyNick());
		// refundOrder.setDueFee(duFees[i]);
		// refundOrder.setFactFee(factFees[i]);
		// refundOrder.setGmtCreate(new Date());
		// refundOrder.setGmtModify(new Date());
		// refundOrder.setGoodsId(order.getGoodsId());
		// refundOrder.setGoodsInstanceId(order.getGoodsInstanceId());
		// refundOrder.setOrderId(order.getId());
		// refundOrder.setPrice(order.getGoodsPrice());
		// refundOrder.setReason(remark);
		// refundOrder.setRefAmount(goodsNum[i]);
		// refundOrder.setRefundId(refund.getRefundId());
		// // refundOrder.setRemark("换货");
		// refundList.add(refundOrder);
		//
		// refundPrice = add(factFees[i].doubleValue(), refundPrice);
		//
		// }
		// }
		//
		// refund.setRefundAmount(refundPrice);
		//
		// // 如果本交易，用点券折扣过，那么退款要先退点券
		// if (trade != null && trade.getTicketAmount() > 0 && refundPrice > 0) {
		//
		// // 点券折扣的金额
		// double ticketFee = add(trade.getGoodsAmount(), trade.getShippingAmount());
		//
		// // 是否有退款信息
		// Map param = new HashMap();
		// param.put("tid", trade.getTid());
		// List<Refund> refs = refundDao.getRefundByParameterMap(param);
		// for (Refund r : refs) {
		// if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(r.getStatus())) {
		// ticketFee = ticketFee - r.getRefundAmount();
		// trade.setTicketAmount(trade.getTicketAmount() - r.getTicketAmount());
		// }
		// }
		// ticketFee = sub(ticketFee, trade.getAmount());
		//
		// if (refundPrice >= ticketFee) {
		// refund.setRefundAmount(refundPrice - ticketFee);
		// refund.setTicketAmount(trade.getTicketAmount());
		// } else {
		// double rate = ticketFee / trade.getTicketAmount();
		// int num = (int) (refundPrice / rate);
		// refund.setTicketAmount(num);
		// refund.setRefundAmount(sub(refundPrice, num * rate));
		// }
		//
		// }
		// // 如果全部退货，并且没有套餐商品，那么type改为全部退货，a
		// if (c == 0) {
		// List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
		// if (packages.size() == 0) {
		// refund.setType(EnumRefundType.REFUND_ALL_GOODS.getKey());
		// }
		//
		// }
		//
		// refundDao.editRefund(refund);
		// refundOrderDao.addRefundOrders(refundList);
		//
		// // 入库
		// InDepository inDepository = new InDepository();
		// inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
		// inDepository.setCreater(getUsername());
		// inDepository.setRelationNum(refund.getRefundId());
		// inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
		// inDepository.setType(EnumInDepository.IN_SALES_REFUND.getKey());
		// Long inId = inDepositoryDao.addInDepository(inDepository);
		//
		// InDetail inDetail = null;
		// Integer inOrderId = null;
		// List<InDetail> inDetailList = new ArrayList<InDetail>();
		// for (int j = 0; orderIds.length > j; j++) {
		// if (goodsNum[j] > 0) {
		// inOrderId = orderIds[j];
		// order = orderDao.getOrder(inOrderId.longValue());
		// inDetail = new InDetail();
		// inDetail.setAmount(goodsNum[j]);
		// inDetail.setGoodsId(order.getGoodsId());
		// inDetail.setGoodsInstanceId(order.getGoodsInstanceId());
		// inDetail.setUnitPrice(order.getGoodsPrice());
		// inDetail.setInDepositoryId(inId);
		// inDetailList.add(inDetail);
		// }
		// }
		// inDetailDao.addInDetails(inDetailList);

		return "success";
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public String refundGoods(Map param, AdminAgent agent) {
		String tid = (String) param.get("tid");
		String[] orderIds = (String[]) param.get("orderIds");
		String[] goodsNum = (String[]) param.get("goodsNum");
		String remark = (String) param.get("remark");
		String[] duFees = (String[]) param.get("duFees");
		String[] factFees = (String[]) param.get("factFees");
		String allRefund = (String) param.get("allRefund");

		// 是否全部退货
		int c = 0;
		// 退款退货表中插入换货信息
		Trade trade = tradeDao.getTradeByTid(tid);
		if (trade == null) {
			return "不允许操作！";
		}
		Refund refund = new Refund();
		refund.setAmount(Double.valueOf(0));
		refund.setBuyId(trade.getBuyId());
		refund.setBuyNick(trade.getBuyNick());
		refund.setGmtCreate(new Date());
		refund.setGmtModify(new Date());
		refund.setGoodsAmount(trade.getGoodsAmount());
		refund.setIsGoodsRecevied("2");
		refund.setIsGoodsUntread("1");
		if (StringUtil.isBlank(remark)) {
			remark = "";
		}
		refund.setIsRefund("n");
		refund.setNote(remark);
		refund.setRefundAmount(Double.valueOf(0));
		refund.setRefundId(codeManager.buildCodeByDateAndType(CodeManager.REFUND_CODE, 6, "TH"));
		refund.setSellerId(trade.getSellerId());
		refund.setSellerNick(trade.getSellerNick());
		refund.setShippingAmount(trade.getShippingAmount());
		refund.setShopId(trade.getShopId());
		refund.setStatus(EnumRefundStatus.Wait_Seller_Check_Goods.getKey());
		refund.setTid(tid);
		refund.setType(EnumRefundType.REFUND_GOODS.getKey());
		refund.setCreater(agent.getUsername());
		refund.setDepFirstId(trade.getDepFirstId());

		refundDao.addRefund(refund);

		Order order = null;
		String orderId = null;
		List<RefundOrder> refundList = new ArrayList<RefundOrder>();
		RefundOrder refundOrder = null;
		double refundPrice = 0;
		for (int i = 0; orderIds.length > i; i++) {
			orderId = orderIds[i];
			order = orderDao.getOrder(Long.valueOf(orderId));
			long gnum = Long.valueOf(goodsNum[i]).longValue();
			if (order != null && order.getGoodsNumber() != gnum) {
				c++;
			}
			if (gnum > 0) {

				refundOrder = new RefundOrder();
				refundOrder.setCreate(agent.getUsername());
				refundOrder.setCustomerId(trade.getBuyId());
				refundOrder.setCustomerName(trade.getBuyNick());
				refundOrder.setDueFee(Double.valueOf(duFees[i]));
				refundOrder.setFactFee(Double.valueOf(factFees[i]));
				refundOrder.setGmtCreate(new Date());
				refundOrder.setGmtModify(new Date());
				refundOrder.setGoodsId(order.getGoodsId());
				refundOrder.setGoodsInstanceId(order.getGoodsInstanceId());
				refundOrder.setOrderId(order.getId());
				refundOrder.setPrice(order.getGoodsPrice());
				refundOrder.setReason(remark);
				refundOrder.setRefAmount(gnum);
				refundOrder.setRefundId(refund.getRefundId());
				// refundOrder.setRemark("换货");
				refundList.add(refundOrder);

				refundPrice = add(Double.valueOf(factFees[i]).doubleValue(), refundPrice);

			}
		}

		refund.setRefundAmount(refundPrice);

		// 如果本交易，用点券折扣过，那么退款要先退点券
		if (trade != null && trade.getTicketAmount() > 0 && refundPrice > 0) {

			// 交易实际需要支付的钱
			double ticketFee = add(trade.getGoodsAmount(), trade.getShippingAmount());
			double rate = ticketFee / trade.getTicketAmount();

			// 是否有退款信息
			Map pm = new HashMap();
			pm.put("tid", trade.getTid());
			List<Refund> refs = refundDao.getRefundByParameterMap(pm);
			for (Refund r : refs) {
				if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(r.getStatus())) {
					ticketFee = sub(ticketFee, r.getRefundAmount());
					ticketFee = sub(ticketFee, r.getTicketAmount() * rate);
					trade.setTicketAmount(trade.getTicketAmount() - r.getTicketAmount());
				}
			}
			ticketFee = sub(ticketFee, trade.getAmount());

			if (refundPrice >= ticketFee) {
				refund.setRefundAmount(refundPrice - ticketFee);
				refund.setTicketAmount(trade.getTicketAmount());
			} else {

				int num = (int) (refundPrice / rate);
				refund.setTicketAmount(num);
				refund.setRefundAmount(sub(refundPrice, num * rate));
			}

		}
		// 如果全部退货，并且没有套餐商品，那么type改为全部退货，a
		if (StringUtil.isNotBlank(allRefund) && "true".equals(allRefund)) {
			List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
			if (packages.size() == 0) {
				refund.setType(EnumRefundType.REFUND_ALL_GOODS.getKey());
			}
		}
		if (c == 0) {
			List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
			if (packages.size() == 0) {
				refund.setType(EnumRefundType.REFUND_ALL_GOODS.getKey());
			}

		}

		refundDao.editRefund(refund);
		refundOrderDao.addRefundOrders(refundList);

		return "success";
	}

	/**
	 * 同意退货操作
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String checkRefundGoods(Map param, AdminAgent agent) {
		String id = (String) param.get("id");

		if (id == null || !StringUtil.isNumeric(id)) {
			return "failed";
		}

		Refund refund = refundDao.getRefund(Long.parseLong(id));
		// 入库
		InDepository inDepository = new InDepository();
		inDepository.setBillNum(codeManager.buildCodeByDateAndType(CodeManager.RUKU_CODE, 6, "RK"));
		inDepository.setCreater(agent.getUsername());
		inDepository.setRelationNum(refund.getRefundId());
		inDepository.setStatus(EnumInStatus.IN_NEW.getKey());
		inDepository.setType(EnumInDepository.IN_SALES_REFUND.getKey());
		inDepository.setDepFirstId(refund.getDepFirstId());
		Long inId = inDepositoryDao.addInDepository(inDepository);

		InDetail inDetail = null;
		List<RefundOrder> refunOrderList = refundOrderDao.getRefundOrderByRefundId(refund.getRefundId());
		List<InDetail> inDetailList = new ArrayList<InDetail>();
		for (RefundOrder order : refunOrderList) {
			inDetail = new InDetail();
			inDetail.setAmount(order.getRefAmount());
			inDetail.setGoodsId(order.getGoodsId());
			inDetail.setGoodsInstanceId(order.getGoodsInstanceId());
			inDetail.setUnitPrice(order.getPrice());
			inDetail.setInDepositoryId(inId);
			inDetail.setDepFirstId(refund.getDepFirstId());
			inDetailList.add(inDetail);
		}
		inDetailDao.addInDetails(inDetailList);

		// 修改退货单状态
		refund.setGmtModify(new Date());
		refund.setStatus(EnumRefundStatus.Wait_Seller_Confirm_Goods.getKey());
		refundDao.editRefund(refund);

		return "success";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.bible.facade.ios.InAndOutDepositoryManager#editRefundGoods(java.lang.Long)
	 */
	public void editRefundGoods(Refund refund) {
		refundDao.editRefund(refund);
	}

	@Transactional
	public String goodsOption(String tid, String[] orderIds, String[] goodsInsIds, String[] goodsNum,
			String[] goodsChangePrice, String remark, String[] dufees, String[] factFees, Double shipFee,
			boolean isRefund, AdminAgent agent) {
		String results = "success";
		if (tid == null) {
			throw new NullPointerException("tid can't be null.");
		}

		if (orderIds.length < 1) {
			throw new NullPointerException("orderIds can't be null.");
		}

		if (goodsNum.length < 1) {
			throw new NullPointerException("goodsNums can't be null.");
		}

		Map param = new HashMap();
		param.put("tid", tid);
		List<Refund> refs = refundDao.getRefundByParameterMap(param);
		int c = 0;
		int r = 0;
		for (Refund refund : refs) {
			if (EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
					|| EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())) {
				// if (EnumRefundType.CHANGE_GOODS.getKey().equals(refund.getType())) {
				// c++;
				// }

			} else {
				r++;
			}

		}
		if (r > 0) {
			return "该订单已经有未完成的退货退款操作！";
		}
		if (c > 0) {
			return "该订单已有换货操作，不允许第二次！ ";
		}

		if (goodsInsIds != null) {
			if (isRefund) {
				results = changeRefundGoods(tid, orderIds, goodsInsIds, goodsNum, goodsChangePrice, remark, shipFee,
						agent);
			} else {
				results = changeGoods(tid, orderIds, goodsInsIds, goodsNum, goodsChangePrice, remark, shipFee, agent);
			}
		}
		return results;
	}

	/**
	 * 加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
}
