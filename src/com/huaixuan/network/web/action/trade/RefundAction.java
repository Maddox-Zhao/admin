package com.huaixuan.network.web.action.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.PackageTrade;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.RefundOrder;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumRefundStatus;
import com.huaixuan.network.biz.enums.EnumRefundType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.RefundQuery;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.InAndOutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.PackageManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.RefundOrderManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
@RequestMapping("/trade")
public class RefundAction extends BaseAction {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private RefundManager refundManager;

	@Autowired
	private TradeManager tradeManager;

	@Autowired
	private AttributeManager attributeManager;

	@Autowired
	private GoodsManager goodsManager;

	@Autowired
	private OrderManager orderManager;

	@Autowired
	private PackageManager packageManager;

	@Autowired
	private RefundOrderManager refundOrderManager;

	@Autowired
	private GoodsInstanceManager goodsInstanceManager;

	@Autowired
	private InAndOutDepositoryManager inAndOutDepositoryManager;

	@Autowired
	private DepositoryFirstManager depositoryFirstManager;

	@Autowired
	private OutDepositoryManager outDepositoryManager;

	//
	// private Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
	//
	// public String returnMessage;

	/**
	 * 退款/退货的list页面
	 * 
	 * @return "success" if no exceptions thrown
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("refundList")
	public String refundSearchlist(@ModelAttribute("query") RefundQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) throws Exception {
		model.addAttribute("enumRefundStatusMap", EnumRefundStatus.toMap());
		model.addAttribute("enumRefundTypeMap", EnumRefundType.toMap());

		// 默认7天的数据
		if (StringUtil.isBlank(query.getGmtCreateStart()) && StringUtil.isBlank(query.getGmtCreateEnd())) {
			query.setGmtCreateStart(DateUtil.getDiffDate(new Date(), -30));
			query.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
		}

		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);

		List<Long> depFirstIds = getDepfirstIdForQuery(agent);
		if (depFirstIds == null || depFirstIds.size() == 0) {
			return "/trade/refundList";
		}

		Map<String, Object> param = BeanUtils.describe(query);
		param.put("depfirstIds", depFirstIds);

		QueryPage page = refundManager.getRefundByParameterMap(param, currPage, pageSize);
		model.addAttribute("page", page);

		return "/trade/refundList";
	}

	@RequestMapping("confirmRefund")
	public String confirmRefund(@RequestParam(value = "refundId", required = false) String refundId,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "detail", required = false) String detailTag,
			@RequestParam(value = "aType", required = false) String aType, Model model) {
		model.addAttribute("enumRefundStatusMap", EnumRefundStatus.toMap());
		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());

		Refund refund = null;
		if (StringUtil.isNotBlank(refundId)) {
			refund = refundManager.getRefundByRefundId(refundId);
			detailTag = "yes";
		} else {
			refund = refundManager.getRefund(id);
		}
		model.addAttribute("refund", refund);

		Trade trade = tradeManager.getTradeByTid(refund.getTid());
		model.addAttribute("trade", trade);

		// 订单中的普通商品
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		model.addAttribute("orderList", orderList);

		// 订单中的套餐及套餐商品信息
		Map<Object, List<Order>> packageMap = new HashMap<Object, List<Order>>();

		List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("tid", trade.getTid());
		for (PackageTrade p : packages) {
			parameterMap.put("packageId", p.getId());
			List<Order> orders = orderManager.getOrdersByParameterMap(parameterMap);
			packageMap.put(p, orders);
		}
		model.addAttribute("packageMap", packageMap);

		List<RefundOrder> listRefundOrder = refundOrderManager.getRefundOrderByRefundId(refund.getRefundId());
		model.addAttribute("listRefundOrder", listRefundOrder);

		for (RefundOrder refundOrder : listRefundOrder) {
			long goodsInstanceId = refundOrder.getGoodsInstanceId();

			GoodsInstance g = goodsInstanceManager.getInstance(goodsInstanceId);
			if (g != null) {
				refundOrder.setGoodsInstanceName(g.getInstanceName());
				refundOrder.setGoodsAttr(attributeManager.getFullAttributeStringByAttrs(g.getAttrs()));
				refundOrder.setGoodsSn(g.getCode());
			}

			Goods gs = goodsManager.getGoods(refundOrder.getGoodsId());
			if (gs != null) {
				refundOrder.setGoodsTitle(gs.getTitle());
			}

			for (Order o : orderList) {
				goodsInstanceId = o.getGoodsInstanceId();
				g = goodsInstanceManager.getInstance(goodsInstanceId);
				if (g != null) {
					o.setGoodsInstanceName(g.getInstanceName());
					o.setGoodsSn(g.getCode());
					o.setProperties(g.getProperties());
				}
				if (o.getId() == refundOrder.getOrderId()) {
					orderList.remove(o);
					o.setIscheck(true);

					orderList.add(o);
					break;
				}
			}

			for (PackageTrade packageinstance : packages) {
				if (packageMap.get(packageinstance).get(0).getId() == refundOrder.getOrderId()) {
					List<Order> orders = packageMap.get(packageinstance);
					for (Order o : orders) {
						GoodsInstance gd = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
						if (gd != null) {
							o.setGoodsSn(gd.getCode());
						}
					}

					packageinstance.setIscheck(true);
					packageMap.put(packageinstance, orders);
					break;
				}
			}
		}

		// 打印功能
		if ("print".equals(aType)) {
			double sumDueFee = 0;
			double sumFactFee = 0;
			int countNum = 0;
			for (RefundOrder refundOrder : listRefundOrder) {
				sumDueFee += refundOrder.getDueFee();// 应收金额总和
				sumFactFee += refundOrder.getFactFee();// 实收金额总和
				countNum += refundOrder.getRefAmount();// 拒收数量总和
			}
			model.addAttribute("sumDueFee", sumDueFee);
			model.addAttribute("sumFactFee", sumFactFee);
			model.addAttribute("countNum", countNum);
			return "/trade/printSalesRefund";
		}

		model.addAttribute("detailTag", detailTag);
		return "/trade/confirmRefund";
	}

	@RequestMapping("confirmChange")
	public String confirmChange(@RequestParam("id") Long id, @RequestParam("agreeTag") String agreeTag, Model model,
			AdminAgent agent) {
		model.addAttribute("agreeTag", agreeTag);

		Refund refundTwo = refundManager.getRefund(id);
		refundTwo.setCreater(agent.getUsername());

		String result = refundManager.updateRefundStatusByRefund(refundTwo, agreeTag);
		if (result == null) {
			model.addAttribute("success", true);
			model.addAttribute("message", "操作成功");
		} else {
			model.addAttribute("success", false);
			model.addAttribute("message", "操作失败");
		}

		return confirmRefund(refundTwo.getRefundId(), null, null, null, model);
	}

	/**
	 * 卖家确认收到退货
	 * 
	 * @return "success" if no exceptions thrown
	 * @throws Exception
	 * 
	 */
	@RequestMapping("congoo")
	public String confirmGoodsRefund(@ModelAttribute("query") RefundQuery query,
			@RequestParam(value = "currPage", required = false, defaultValue = "1") int currPage,
			@RequestParam("refundId") Long refundId, Model model, AdminAgent agent) throws Exception {
		Refund refund = refundManager.getRefund(refundId);

		if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())
				|| EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())
				|| EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())) {
			model.addAttribute("error", refund.getRefundId() + ",此退款不能操作！");
			return refundSearchlist(query, 1, model, agent);
		}

		refund.setCreater(agent.getUsername());

		String tradeResult = refundManager.updateRefundStatusByRefund(refund, "3");
		if (tradeResult == null) {
			model.addAttribute("success", "操作成功");
		} else {
			model.addAttribute("error", "操作失败");
		}

		return refundSearchlist(query, currPage, model, agent);
	}

	@RequestMapping(value = "refundGoods", method = RequestMethod.GET)
	public String refundGoods(@RequestParam("tid") String tid, Model model) {
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		// 订单中的普通商品
		Map<String, Long> orderMap = new HashMap<String, Long>();
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		for (Order temp : orderList) {
			orderMap.put(String.valueOf(temp.getId()), new Long(temp.getGoodsNumber()));
			GoodsInstance g = goodsInstanceManager.getInstance(temp.getGoodsInstanceId());
			if (g != null) {
				temp.setGoodsSn(g.getCode());
			}
		}
		model.addAttribute("orderList", orderList);

		// 订单中的套餐及套餐商品信
		Map<PackageTrade, List<Order>> packageMap = new HashMap<PackageTrade, List<Order>>();

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("tid", trade.getTid());
		List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
		for (PackageTrade packageinstance : packages) {
			parameterMap.put("packageId", packageinstance.getId());
			List<Order> orders = orderManager.getOrdersByParameterMap(parameterMap);
			for (Order o : orders) {
				GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
				if (g != null) {
					o.setGoodsSn(g.getCode());
				}
			}
			packageMap.put(packageinstance, orders);
		}
		model.addAttribute("packageMap", packageMap);

		// 是否有退款信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tid", trade.getTid());
		List<Refund> refs = refundManager.getRefundByParameterMap(param);
		for (Refund r : refs) {
			if (EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(r.getStatus())
					|| EnumRefundStatus.Refund_Close.getKey().equals(r.getStatus())) {
				continue;
			}
			List<RefundOrder> listRefundOrder = refundOrderManager.getRefundOrderByRefundId(r.getRefundId());
			for (RefundOrder ro : listRefundOrder) {
				Long gNum = (Long) orderMap.get(String.valueOf(ro.getOrderId()));
				if (gNum != null) {
					long canrefNum = gNum.longValue() - ro.getRefAmount();
					orderMap.put(String.valueOf(ro.getOrderId()), new Long(canrefNum));
				}
			}
		}

		model.addAttribute("refsList", refs);
		model.addAttribute("orderMap", orderMap);

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("enumRefundStatusMap", EnumRefundStatus.toMap());
		model.addAttribute("enumRefundTypeMap", EnumRefundType.toMap());

		return "/trade/refundGoods";
	}

	@RequestMapping(value = "refundGoods", method = RequestMethod.POST)
	public String submitRefund(ServletRequest req, Model model, AdminAgent agent) {

		String tid = req.getParameter("tid");
		String[] orderId = req.getParameterValues("orderIdText");
		String[] goodsNum = req.getParameterValues("ordersNumberText");
		String remark = req.getParameter("remark");
		String[] duFees = req.getParameterValues("dueFee");
		String[] factFees = req.getParameterValues("factFee");
		String reserveNick = req.getParameter("reserveNick");
		boolean hasChangeRefund = false;
		try {
			// 普通会员商品发货状态45天后不允许退货，2688会员商品90天后不允许退货
			if (StringUtil.isBlank(tid)) {
				model.addAttribute("error", "交易代码不正确，请重新操作！");
				return refundGoods(tid, model);
			}
			Trade trade = tradeManager.getTradeByTid(tid);
			if (trade == null) {
				model.addAttribute("error", "交易代码不正确，请重新操作！");
				return refundGoods(tid, model);
			}

			OutDepository outd = outDepositoryManager.getOutDepositoryByTid(tid);
			// modified by chenyan 2010/05/28 修改了普通会员10天改为45天
			int canRefundDays = 45;
			if (StringUtil.isNotBlank(reserveNick)) {
				String[] nick = reserveNick.split(",");
				for (int c = 0; c < nick.length; c++) {
					if (trade.getBuyNick().equals(nick[c])) {
						// 普通会员商品发货状态45天后不允许退货，2688会员商品90天后不允许退货
						// canRefundDays = 45;
						// 2010-2-2 退货时间改为90天
						canRefundDays = 90;
						break;
					}
				}
			}
			if (DateUtil.countDays(outd.getGmtOutDep(), new Date()) > canRefundDays) {
				model.addAttribute("error", "商品发货状态" + canRefundDays + "天后不允许退货！");
				return refundGoods(tid, model);
			}
			// 判断是否全部退货
			int allRefund = 0;
			Map<String, Long> orderMap = new HashMap();
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
			for (Order temp : orderList) {
				orderMap.put(String.valueOf(temp.getId()), new Long(temp.getGoodsNumber()));
				allRefund++;
			}

			// 是否有退款信息
			Map param = new HashMap();
			param.put("tid", trade.getTid());
			List<Refund> refs = refundManager.getRefundByParameterMap(param);
			int r = 0;
			for (Refund refund : refs) {

				if (EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(refund.getStatus())
						|| EnumRefundStatus.Refund_Close.getKey().equals(refund.getStatus())
						|| EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())) {

					r++;

				}
				if (EnumRefundStatus.Goods_Confirm_Success.getKey().equals(refund.getStatus())) {
					if (EnumRefundType.CHANGE_GOODS.getKey().equals(refund.getType())) {
						hasChangeRefund = true;
					}

					List<RefundOrder> listRefundOrder = refundOrderManager.getRefundOrderByRefundId(refund
							.getRefundId());
					for (RefundOrder ro : listRefundOrder) {
						Long gNum = (Long) orderMap.get(String.valueOf(ro.getOrderId()));
						if (gNum != null) {
							long canrefNum = gNum.longValue() - ro.getRefAmount();
							orderMap.put(String.valueOf(ro.getOrderId()), new Long(canrefNum));

						}

					}

				}
			}
			if (refs != null && refs.size() > r) {
				model.addAttribute("error", "当前有未完成的退货记录，请处理完以后再操作！");
				return refundGoods(tid, model);
			}

			Map parm = new HashMap();
			parm.put("tid", tid);
			parm.put("orderIds", orderId);
			parm.put("goodsNum", goodsNum);
			parm.put("remark", remark);
			parm.put("duFees", duFees);
			parm.put("factFees", factFees);
			// String tid, Integer[] orderId, Integer[] goodsNum, String remark,
			// Double[] duFees, Double[] factFees

			int j = 0;

			for (int i = 0; goodsNum.length > i; i++) {
				if (StringUtil.isNotBlank(goodsNum[i])) {
					if (!NumberUtils.isNumber(factFees[i])) {
						model.addAttribute("error", "实退款必须输入，且必须为数字");
						return refundGoods(tid, model);
					}

					Long canrefNum = (Long) orderMap.get(orderId[i]);
					if (canrefNum != null && Long.valueOf(goodsNum[i]).longValue() > canrefNum.longValue()) {
						model.addAttribute("error", "退货数量大于实际可退数量,请重新输入！");
						return refundGoods(tid, model);
					}
					if (Long.valueOf(goodsNum[i]).longValue() > 0) {
						j++;
					}
					long leftNum = canrefNum.longValue() - Long.valueOf(goodsNum[i]).longValue();
					if (leftNum == 0) {
						allRefund = allRefund - 1;
					}
				}
			}
			if (j < 1) {
				model.addAttribute("error", "退货数量都为0,不需要产生退款！");
				return refundGoods(tid, model);
			}

			if (allRefund == 0 && !hasChangeRefund) {
				parm.put("allRefund", "true");
			}

			String msg = inAndOutDepositoryManager.refundGoods(parm, agent);
			if (msg.equals("success")) {
				model.addAttribute("success", "退货操作成功！");
			} else {
				model.addAttribute("error", msg);
			}
			return refundGoods(tid, model);
		} catch (Exception e) {
			log.error("", e);
			model.addAttribute("error", "退货失败");
			return refundGoods(tid, model);
		}
	}

	/**
	 * 拒绝退货
	 * 
	 * @return
	 */
	@RequestMapping("refuseRefundGoods")
	public String refuseRefundGoods(@RequestParam("id") Long id,
			@RequestParam(value = "note", required = false) String note, Model model, AdminAgent agent) {
		Refund refund = refundManager.getRefund(id);
		if (!EnumRefundStatus.Wait_Seller_Check_Goods.getKey().equals(refund.getStatus())) {
			model.addAttribute("success", false);
			model.addAttribute("message", "退货状态不正确，验收退货操作失败");
			return confirmRefund(refund.getRefundId(), null, null, null, model);
		}

		refund.setGmtModify(new Date());
		refund.setStatus(EnumRefundStatus.Refund_Close.getKey());
		if (StringUtil.isNotBlank(note)) {
			refund.setNote(note);
		}
		inAndOutDepositoryManager.editRefundGoods(refund);

		model.addAttribute("success", true);
		model.addAttribute("message", "验收退货操作成功！");

		return confirmRefund(refund.getRefundId(), null, null, null, model);
	}

	/**
	 * 同意退货
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("agreeRefundGoods")
	public String agreeRefundGoods(@RequestParam("id") Long id, Model model, AdminAgent agent) {
		Refund refund = refundManager.getRefund(id);
		if (!EnumRefundStatus.Wait_Seller_Check_Goods.getKey().equals(refund.getStatus())) {
			model.addAttribute("success", false);
			model.addAttribute("message", "退货状态不正确，验收退货操作失败");
			return confirmRefund(refund.getRefundId(), null, null, null, model);
		}

		Map parm = new HashMap();
		parm.put("id", id.toString());
		String message = inAndOutDepositoryManager.checkRefundGoods(parm, agent);

		if (message.equals("success")) {
			model.addAttribute("success", true);
			model.addAttribute("message", "验收退货操作成功！");
		} else {
			model.addAttribute("success", false);
			model.addAttribute("message", "验收退货操作失败");
		}
		return confirmRefund(refund.getRefundId(), null, null, null, model);
	}

	/**
	 * 换货
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "changeGoods", method = RequestMethod.GET)
	public String changeGoods(@RequestParam("tid") String tid, Model model) {
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		// 订单中的普通商品
		Map<String, Long> orderMap = new HashMap<String, Long>();
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		for (Order temp : orderList) {
			orderMap.put(String.valueOf(temp.getId()), temp.getGoodsNumber());
			GoodsInstance g = goodsInstanceManager.getInstance(temp.getGoodsInstanceId());
			if (g != null) {
				temp.setGoodsSn(g.getCode());
			}
		}
		model.addAttribute("orderList", orderList);

		// 订单中的套餐及套餐商品信
		Map packageMap = new HashMap();

		Map parameterMap = new HashMap();
		parameterMap.put("tid", trade.getTid());

		List<PackageTrade> packages = packageManager.getPackagesByTid(trade.getTid());
		for (PackageTrade packageinstance : packages) {
			parameterMap.put("packageId", packageinstance.getId());
			List<Order> orders = orderManager.getOrdersByParameterMap(parameterMap);
			for (Order o : orders) {
				GoodsInstance g = goodsInstanceManager.getInstance(o.getGoodsInstanceId());
				if (g != null) {
					o.setGoodsSn(g.getCode());
				}
			}
			packageMap.put(packageinstance, orders);
		}
		model.addAttribute("packageMap", packageMap);

		// 是否有退款信息
		Map param = new HashMap();
		param.put("tid", trade.getTid());
		List<Refund> refs = refundManager.getRefundByParameterMap(param);
		List<RefundOrder> changeRefund = new ArrayList<RefundOrder>();
		for (Refund r : refs) {
			if (EnumRefundStatus.Seller_Refuse_Refund.getKey().equals(r.getStatus())
					|| EnumRefundStatus.Refund_Close.getKey().equals(r.getStatus())) {
				continue;
			}

			List<RefundOrder> listRefundOrder = refundOrderManager.getRefundOrderByRefundId(r.getRefundId());
			for (RefundOrder ro : listRefundOrder) {
				Long gNum = (Long) orderMap.get(String.valueOf(ro.getOrderId()));
				if (gNum != null) {
					long canrefNum = gNum.longValue() - ro.getRefAmount();

					orderMap.put(String.valueOf(ro.getOrderId()), new Long(canrefNum));
				}
				if (EnumRefundType.CHANGE_GOODS.getKey().equals(r.getType())
						&& EnumRefundStatus.Goods_Confirm_Success.getKey().equals(r.getStatus())) {

					GoodsInstance g = goodsInstanceManager.getInstance(ro.getGoodsInstanceId());
					if (g != null) {
						ro.setBuyAttr(g.getAttrs());
						ro.setGoodsSn(g.getCode());
						ro.setGoodsTitle(g.getInstanceName());
					}
					changeRefund.add(ro);
				}

			}

		}
		model.addAttribute("changeRefund", changeRefund);
		model.addAttribute("refsList", refs);
		model.addAttribute("orderMap", orderMap);

		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("enumRefundStatusMap", EnumRefundStatus.toMap());
		model.addAttribute("enumRefundTypeMap", EnumRefundType.toMap());

		return "/trade/changeGoods";
	}

	// 换货
	@RequestMapping(value = "changeGoods", method = RequestMethod.POST)
	public String submitChange(ServletRequest req, Model model, AdminAgent agent) {
		String tid = req.getParameter("tid");
		String[] orderId = req.getParameterValues("orderIdText");
		String[] goodsNum = req.getParameterValues("ordersNumberText");
		String[] goodsInsId = req.getParameterValues("goodsInstIdText");

		String remark = req.getParameter("remark");
		String[] goodsChangePrice = req.getParameterValues("goodsChangePriceText");
		Double shipFee = Double.valueOf(req.getParameter("shipFee"));
		try {
			int j = 0;
			if (goodsInsId.length < 1) {
				model.addAttribute("error", "换货产品不能为空！");
				return changeGoods(tid, model);
			}

			for (int i = 0; goodsNum.length > i; i++) {
				if (StringUtil.isNotBlank(goodsNum[i]) && Long.valueOf(goodsNum[i]).longValue() > 0) {

					j++;
				}
			}
			if (j == 0) {
				model.addAttribute("error", "没有需要换货的产品！");
				return changeGoods(tid, model);
			}

			if (StringUtil.isBlank(remark)) {
				model.addAttribute("error", "请填写换货原因！");
				return changeGoods(tid, model);
			}

			String r = inAndOutDepositoryManager.goodsOption(tid, orderId, goodsInsId, goodsNum, goodsChangePrice,
					remark, null, null, shipFee, false, agent);
			if ("success".equals(r)) {
				model.addAttribute("success", "换货操作成功！");
			} else {
				model.addAttribute("error", r);
			}
			return changeGoods(tid, model);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("error", "换货失败！");
			return changeGoods(tid, model);
		}
	}

	// 换货
	@RequestMapping(value = "changeRefundGoods", method = RequestMethod.POST)
	public String submitRefundChange(ServletRequest req, Model model, AdminAgent agent) {
		String tid = req.getParameter("tid");
		String[] refundorderId = req.getParameterValues("refundorderIdText");
		String[] goodsNum = req.getParameterValues("refundordersNumberText");
		String[] goodsInsId = req.getParameterValues("refundgoodsInstIdText");
		String remark = req.getParameter("refundremark");
		String[] goodsChangePrice = req.getParameterValues("refundgoodsChangePriceText");
		Double shipFee = Double.valueOf(req.getParameter("shipFee"));
		String[] refundOrderRefundIds = req.getParameterValues("refundOrderRefundId");
		boolean isRefund = true;
		try {
			int j = 0;
			if (goodsInsId.length < 1) {
				model.addAttribute("error", "换货产品不能为空！");
				return changeGoods(tid, model);
			}
			boolean sameRefund = true;
			String refundOrderRefundId = "";
			for (int i = 0; goodsNum.length > i; i++) {
				if (StringUtil.isNotBlank(goodsNum[i]) && Long.valueOf(goodsNum[i]).longValue() > 0) {
					if (StringUtil.isNotBlank(refundOrderRefundId)) {
						if (!refundOrderRefundId.equals(refundOrderRefundIds[i])) {
							sameRefund = false;
						}
					}
					refundOrderRefundId = refundOrderRefundIds[i];
					j++;
				}
			}
			if (!sameRefund) {
				model.addAttribute("error", "操作失败，请选择相同的换货单号，进行换货！");
				return changeGoods(tid, model);
			}
			if (j == 0) {
				model.addAttribute("error", "没有需要换货的产品！");
				return changeGoods(tid, model);
			}

			String r = inAndOutDepositoryManager.goodsOption(tid, refundorderId, goodsInsId, goodsNum,
					goodsChangePrice, remark, null, null, shipFee, isRefund, agent);
			if ("success".equals(r)) {
				model.addAttribute("success", "换货操作成功！");
			} else {
				model.addAttribute("error", r);
			}
			return changeGoods(tid, model);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("error", "换货失败！");
			return changeGoods(tid, model);
		}
	}

	// 退货换货管理中添加客服留言功能
	@RequestMapping("messageConserveforRefund")
	public String csMessageForReund(@RequestParam("csMessage") String csMessage, @RequestParam("id") Long id,
			@RequestParam("refundId") String refundId, Model model, AdminAgent agent) {

		refundManager.updateMessageForRefundByTradeId(refundId, csMessage);
		model.addAttribute("success", true);
		model.addAttribute("message", "保存成功");

		return confirmRefund(null, id, "yes", null, model);
	}

}
