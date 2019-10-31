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
	 * �˿�/�˻���listҳ��
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

		// Ĭ��7�������
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

		// �����е���ͨ��Ʒ
		List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
		model.addAttribute("orderList", orderList);

		// �����е��ײͼ��ײ���Ʒ��Ϣ
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

		// ��ӡ����
		if ("print".equals(aType)) {
			double sumDueFee = 0;
			double sumFactFee = 0;
			int countNum = 0;
			for (RefundOrder refundOrder : listRefundOrder) {
				sumDueFee += refundOrder.getDueFee();// Ӧ�ս���ܺ�
				sumFactFee += refundOrder.getFactFee();// ʵ�ս���ܺ�
				countNum += refundOrder.getRefAmount();// ���������ܺ�
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
			model.addAttribute("message", "�����ɹ�");
		} else {
			model.addAttribute("success", false);
			model.addAttribute("message", "����ʧ��");
		}

		return confirmRefund(refundTwo.getRefundId(), null, null, null, model);
	}

	/**
	 * ����ȷ���յ��˻�
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
			model.addAttribute("error", refund.getRefundId() + ",���˿�ܲ�����");
			return refundSearchlist(query, 1, model, agent);
		}

		refund.setCreater(agent.getUsername());

		String tradeResult = refundManager.updateRefundStatusByRefund(refund, "3");
		if (tradeResult == null) {
			model.addAttribute("success", "�����ɹ�");
		} else {
			model.addAttribute("error", "����ʧ��");
		}

		return refundSearchlist(query, currPage, model, agent);
	}

	@RequestMapping(value = "refundGoods", method = RequestMethod.GET)
	public String refundGoods(@RequestParam("tid") String tid, Model model) {
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		// �����е���ͨ��Ʒ
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

		// �����е��ײͼ��ײ���Ʒ��
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

		// �Ƿ����˿���Ϣ
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
			// ��ͨ��Ա��Ʒ����״̬45��������˻���2688��Ա��Ʒ90��������˻�
			if (StringUtil.isBlank(tid)) {
				model.addAttribute("error", "���״��벻��ȷ�������²�����");
				return refundGoods(tid, model);
			}
			Trade trade = tradeManager.getTradeByTid(tid);
			if (trade == null) {
				model.addAttribute("error", "���״��벻��ȷ�������²�����");
				return refundGoods(tid, model);
			}

			OutDepository outd = outDepositoryManager.getOutDepositoryByTid(tid);
			// modified by chenyan 2010/05/28 �޸�����ͨ��Ա10���Ϊ45��
			int canRefundDays = 45;
			if (StringUtil.isNotBlank(reserveNick)) {
				String[] nick = reserveNick.split(",");
				for (int c = 0; c < nick.length; c++) {
					if (trade.getBuyNick().equals(nick[c])) {
						// ��ͨ��Ա��Ʒ����״̬45��������˻���2688��Ա��Ʒ90��������˻�
						// canRefundDays = 45;
						// 2010-2-2 �˻�ʱ���Ϊ90��
						canRefundDays = 90;
						break;
					}
				}
			}
			if (DateUtil.countDays(outd.getGmtOutDep(), new Date()) > canRefundDays) {
				model.addAttribute("error", "��Ʒ����״̬" + canRefundDays + "��������˻���");
				return refundGoods(tid, model);
			}
			// �ж��Ƿ�ȫ���˻�
			int allRefund = 0;
			Map<String, Long> orderMap = new HashMap();
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade.getTid());
			for (Order temp : orderList) {
				orderMap.put(String.valueOf(temp.getId()), new Long(temp.getGoodsNumber()));
				allRefund++;
			}

			// �Ƿ����˿���Ϣ
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
				model.addAttribute("error", "��ǰ��δ��ɵ��˻���¼���봦�����Ժ��ٲ�����");
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
						model.addAttribute("error", "ʵ�˿�������룬�ұ���Ϊ����");
						return refundGoods(tid, model);
					}

					Long canrefNum = (Long) orderMap.get(orderId[i]);
					if (canrefNum != null && Long.valueOf(goodsNum[i]).longValue() > canrefNum.longValue()) {
						model.addAttribute("error", "�˻���������ʵ�ʿ�������,���������룡");
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
				model.addAttribute("error", "�˻�������Ϊ0,����Ҫ�����˿");
				return refundGoods(tid, model);
			}

			if (allRefund == 0 && !hasChangeRefund) {
				parm.put("allRefund", "true");
			}

			String msg = inAndOutDepositoryManager.refundGoods(parm, agent);
			if (msg.equals("success")) {
				model.addAttribute("success", "�˻������ɹ���");
			} else {
				model.addAttribute("error", msg);
			}
			return refundGoods(tid, model);
		} catch (Exception e) {
			log.error("", e);
			model.addAttribute("error", "�˻�ʧ��");
			return refundGoods(tid, model);
		}
	}

	/**
	 * �ܾ��˻�
	 * 
	 * @return
	 */
	@RequestMapping("refuseRefundGoods")
	public String refuseRefundGoods(@RequestParam("id") Long id,
			@RequestParam(value = "note", required = false) String note, Model model, AdminAgent agent) {
		Refund refund = refundManager.getRefund(id);
		if (!EnumRefundStatus.Wait_Seller_Check_Goods.getKey().equals(refund.getStatus())) {
			model.addAttribute("success", false);
			model.addAttribute("message", "�˻�״̬����ȷ�������˻�����ʧ��");
			return confirmRefund(refund.getRefundId(), null, null, null, model);
		}

		refund.setGmtModify(new Date());
		refund.setStatus(EnumRefundStatus.Refund_Close.getKey());
		if (StringUtil.isNotBlank(note)) {
			refund.setNote(note);
		}
		inAndOutDepositoryManager.editRefundGoods(refund);

		model.addAttribute("success", true);
		model.addAttribute("message", "�����˻������ɹ���");

		return confirmRefund(refund.getRefundId(), null, null, null, model);
	}

	/**
	 * ͬ���˻�
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("agreeRefundGoods")
	public String agreeRefundGoods(@RequestParam("id") Long id, Model model, AdminAgent agent) {
		Refund refund = refundManager.getRefund(id);
		if (!EnumRefundStatus.Wait_Seller_Check_Goods.getKey().equals(refund.getStatus())) {
			model.addAttribute("success", false);
			model.addAttribute("message", "�˻�״̬����ȷ�������˻�����ʧ��");
			return confirmRefund(refund.getRefundId(), null, null, null, model);
		}

		Map parm = new HashMap();
		parm.put("id", id.toString());
		String message = inAndOutDepositoryManager.checkRefundGoods(parm, agent);

		if (message.equals("success")) {
			model.addAttribute("success", true);
			model.addAttribute("message", "�����˻������ɹ���");
		} else {
			model.addAttribute("success", false);
			model.addAttribute("message", "�����˻�����ʧ��");
		}
		return confirmRefund(refund.getRefundId(), null, null, null, model);
	}

	/**
	 * ����
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "changeGoods", method = RequestMethod.GET)
	public String changeGoods(@RequestParam("tid") String tid, Model model) {
		Trade trade = tradeManager.getTradeByTid(tid);
		model.addAttribute("trade", trade);

		// �����е���ͨ��Ʒ
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

		// �����е��ײͼ��ײ���Ʒ��
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

		// �Ƿ����˿���Ϣ
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

	// ����
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
				model.addAttribute("error", "������Ʒ����Ϊ�գ�");
				return changeGoods(tid, model);
			}

			for (int i = 0; goodsNum.length > i; i++) {
				if (StringUtil.isNotBlank(goodsNum[i]) && Long.valueOf(goodsNum[i]).longValue() > 0) {

					j++;
				}
			}
			if (j == 0) {
				model.addAttribute("error", "û����Ҫ�����Ĳ�Ʒ��");
				return changeGoods(tid, model);
			}

			if (StringUtil.isBlank(remark)) {
				model.addAttribute("error", "����д����ԭ��");
				return changeGoods(tid, model);
			}

			String r = inAndOutDepositoryManager.goodsOption(tid, orderId, goodsInsId, goodsNum, goodsChangePrice,
					remark, null, null, shipFee, false, agent);
			if ("success".equals(r)) {
				model.addAttribute("success", "���������ɹ���");
			} else {
				model.addAttribute("error", r);
			}
			return changeGoods(tid, model);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("error", "����ʧ�ܣ�");
			return changeGoods(tid, model);
		}
	}

	// ����
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
				model.addAttribute("error", "������Ʒ����Ϊ�գ�");
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
				model.addAttribute("error", "����ʧ�ܣ���ѡ����ͬ�Ļ������ţ����л�����");
				return changeGoods(tid, model);
			}
			if (j == 0) {
				model.addAttribute("error", "û����Ҫ�����Ĳ�Ʒ��");
				return changeGoods(tid, model);
			}

			String r = inAndOutDepositoryManager.goodsOption(tid, refundorderId, goodsInsId, goodsNum,
					goodsChangePrice, remark, null, null, shipFee, isRefund, agent);
			if ("success".equals(r)) {
				model.addAttribute("success", "���������ɹ���");
			} else {
				model.addAttribute("error", r);
			}
			return changeGoods(tid, model);
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("error", "����ʧ�ܣ�");
			return changeGoods(tid, model);
		}
	}

	// �˻�������������ӿͷ����Թ���
	@RequestMapping("messageConserveforRefund")
	public String csMessageForReund(@RequestParam("csMessage") String csMessage, @RequestParam("id") Long id,
			@RequestParam("refundId") String refundId, Model model, AdminAgent agent) {

		refundManager.updateMessageForRefundByTradeId(refundId, csMessage);
		model.addAttribute("success", true);
		model.addAttribute("message", "����ɹ�");

		return confirmRefund(null, id, "yes", null, model);
	}

}
