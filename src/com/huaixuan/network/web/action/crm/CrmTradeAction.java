package com.huaixuan.network.web.action.crm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.ExpressDist;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.trade.Order;
import com.huaixuan.network.biz.domain.trade.PackageTrade;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumOrderKindType;
import com.huaixuan.network.biz.enums.EnumTradeStatus;
import com.huaixuan.network.biz.enums.EnumTradeWholesaleStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.query.TradeListQuery;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.express.ExpressDistManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.OrderManager;
import com.huaixuan.network.biz.service.trade.PackageManager;
import com.huaixuan.network.biz.service.trade.PayRecordManager;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.DateUtil;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * CRM��������
 *
 * @author chenyan 2011/03/21
 *
 */
@Controller
public class CrmTradeAction extends BaseAction {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	private TradeManager tradeManager;
	@Autowired
	private OrderManager orderManager;
	@Autowired
	private RefundManager refundManager;
	@Autowired
	private OutDepositoryManager outDepositoryManager;
	@Autowired
	private ExpressManager expressManager;
	@Autowired
	private ShopInfoService shopInfoManager;
	@Autowired
	private GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private RegionManager regionManager;
	@Autowired
	private ExpressDistManager expressDistManager;
	@Autowired
	private StorageManager storageManager;
	@Autowired
	private PackageManager packageManager;
	@Autowired
	private PayRecordManager payRecordManager;
	@Autowired
	private GoodsBatchManager goodsBatch;
	@Autowired
	private AttributeManager attributeManager;
	@Autowired
	private GoodsManager goodsManager;
	@Autowired
	private AdminService adminService;

	private @Value("${liangpin99.url}")
	String liangpin99url;

	/**
	 * ��ʾCRM�����б���Ϣ
	 *
	 * @return String
	 * @author chenyan 2011/03/20
	 */
	@RequestMapping(value = "/crm/crmShowl")
	public String showCrmAdminTradeList(
			@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			Model model, AdminAgent agent) {
		model.addAttribute("liangpin99url", liangpin99url);
		// ��ѯ��չ��Ա
		List<Admin> adminUsers = adminService.getAdminUserList();
		model.addAttribute("adminUsers", adminUsers);

		String gmtCreateStart = tradeListQuery.getGmtCreateStart();
		String gmtCreateEnd = tradeListQuery.getGmtCreateEnd();
		String tid = tradeListQuery.getTid();
		if (StringUtil.isBlank(tid)) {
			if (StringUtil.isBlank(gmtCreateStart)
					&& StringUtil.isBlank(gmtCreateEnd)) {
				gmtCreateStart = DateUtil.convertDateToString(DateUtil
						.getRelativeDate(new Date(), -7));
				gmtCreateEnd = DateUtil.convertDateToString(new Date());
				tradeListQuery.setGmtCreateStart(gmtCreateStart);
				tradeListQuery.setGmtCreateEnd(gmtCreateEnd);
			}
		}

		List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
		if (depfirstIdList != null && depfirstIdList.size() > 0) {
			tradeListQuery.setDepfirstIds(depfirstIdList);
		}

		// �����漰����һ���ֿ�
		model.addAttribute("depositoryFirstList", this.depositoryFirstManager
				.getDepositoryFirstListByIds(tradeListQuery.getDepfirstIds()));

		// ����һ���ֿ�"ID������"ӳ��
		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(),
					depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		// ������ʱ������Ե�״ֵ̬
		String statusTemp = tradeListQuery.getStatus();
		if (null != depfirstIdList) {
			if (StringUtil.isNotBlank(tradeListQuery.getOpp())) {
				if (StringUtil.isNotBlank(tradeListQuery.getStatus())) {
					tradeListQuery.setOpp(tradeListQuery.getStatus());
					tradeListQuery.setStatus(null);
				} else {
					tradeListQuery.setStatus("opp");
				}
			}

			Trade trade = tradeManager.getTradesGoodsAmountSum(tradeListQuery);
			model.addAttribute("trade", trade);

			QueryPage page = tradeManager.getTradesByParameterMap(
					tradeListQuery, currPage, pageSize);
			model.addAttribute("page", page);

			// ȡ�ö�������Ʒ��Ϣ
			if (page.getItems() != null) {
				for (Trade t : (List<Trade>) page.getItems()) {
					List<Order> orderList = orderManager
							.getOrdersNotInPackage(t.getTid());
					t.setOrderList(orderList);
				}
			}
		}
		// д����ԵĲ���ֵ
		tradeListQuery.setStatus(statusTemp);
		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("enumOrderKindMap", EnumOrderKindType.toMap());
		model.addAttribute("enumTradeWholesaleStatus",
				EnumTradeWholesaleStatus.toMap());
		return "/crm/crmShowl";
	}

	/**
	 * CRM��ʾ��������
	 *
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/crm/crmTradeDetail")
	public String showCrmAdminTradeDetail(
			@RequestParam(value = "refundId", required = false, defaultValue = "") String refundId,
			@RequestParam(value = "tradeId", required = false, defaultValue = "") String tid,
			Model model, AdminAgent agent) {
		model.addAttribute("liangpin99url", liangpin99url);
		// �������Ĳ�����refund_id,�����refund_id��ѯ��Ӧ��tid
		if (!StringUtil.isBlank(refundId)) {
			Refund refund = refundManager.getRefundByRefundId(refundId);
			if (refund != null) {
				String tradeId = refund.getTid();
				Trade trade = tradeManager.getTradeByTid(tradeId);
				if (trade != null) {
					tid = trade.getTid();
				}
			}
		}

		// ����tid��ѯtrade
		Trade trade = tradeManager.getTradeByTid(tid);
		// �����۸���ʾΪ�Ǹ����� zhangwy
		trade.setShippingAmount(DoubleUtil.round(trade.getShippingAmount(), 1));
		if (trade != null) {
			// ������������Ϣ����ʾadded by chenyan 2009/09/10
			if (trade.getExpressId() != null) {
				Express expressTemp = expressManager.getExpress(trade
						.getExpressId());
				if (expressTemp != null) {
					trade.setExpressName(expressTemp.getExpressName());
				}
			}

			// ��������������
			if (trade.getTid() != null) {
				OutDepository outDepositoryTemp = outDepositoryManager
						.getOutDepositoryByTid(trade.getTid());
				if (outDepositoryTemp != null) {
					trade.setExpressCode(outDepositoryTemp.getExpressCode());
				}
			}

			String address = this.regionManager.getAddressInfo(
					trade.getProvince(), trade.getCity(), trade.getDistrict());
			address += trade.getAddress();
			model.addAttribute("address", address);
			ShopInfo shopInfo = null;
			try {
				shopInfo = this.shopInfoManager.getShopInfo(trade.getShopId());
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			if (shopInfo != null) {
				model.addAttribute("shopInfo", shopInfo);
			}
			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
					.getDepositoryFirstList();
			Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
			for (DepositoryFirst depositoryFirst : depositoryFirstList) {
				depositoryNameMap.put(depositoryFirst.getId(),
						depositoryFirst.getDepFirstName());
			}
			model.addAttribute("depositoryNameMap", depositoryNameMap);
			// �����е���ͨ�� ������������Ϣ zhangwy
			List<Order> orderList = orderManager.getOrdersNotInPackage(trade
					.getTid());
			String regionCodeStart = "330782";
			if (null != trade.getDepFirstId()) {// ȡ������ʼ��
				DepositoryFirst depositoryFirst = depositoryFirstManager
						.getDepositoryById(trade.getDepFirstId());
				if (null != depositoryFirst) {
					regionCodeStart = depositoryFirst.getRegionCode();
				}
			}
			double orderWeight = 0;
			if (orderList != null)
				for (Order temp : orderList) {
					orderWeight = orderWeight + temp.getGoodWeight()
							* temp.getGoodsNumber();
					GoodsInstance g = goodsInstanceManager.getInstance(temp
							.getGoodsInstanceId());
					if (g != null) {
						temp.setGoodsSn(g.getCode());
					}

					List<Storage> storageList = new ArrayList<Storage>();
					storageList = storageManager.getStorageWithTrade(
							temp.getGoodsInstanceId(), true);
					if (storageList != null) {
						temp.setStoragelist(storageList);
					}
				}
			// ������Ϣ(����,�����Լ����ǵļ۸�)
			boolean isPeriod = false;
			if (trade.getExpressPayment().equals("period_pay")) {
				trade.setExpressPayment(EnumExpressDistPayment.PAYMENT_FIRST
						.getKey());
				isPeriod = true;
			}
			if (trade.getDistrict() != null
					&& StringUtil.isNotBlank(trade.getDistrict())) {
				ExpressDist expressDist = expressDistManager
						.getExpressDistByRegionCodeEnd(regionCodeStart,
								trade.getDistrict(), trade.getExpressPayment(),
								trade.getExpressId());
				if (isPeriod) {
					trade.setExpressPayment("period_pay");
				}
				if (expressDist != null) {
					trade.setTotalWeight(DoubleUtil.round(orderWeight, 2));
					trade.setWeightFirst(expressDist.getWeightFirst());
					trade.setWeightFirstMoney(expressDist.getWeightFirstMoney());
					trade.setWeightNext(expressDist.getWeightNext());
					trade.setWeightNextMoney(expressDist.getWeightNextMoney());
				}
			} else {
				ExpressDist expressDist = expressDistManager
						.getExpressDistByRegionCodeEnd(regionCodeStart,
								trade.getCity(), trade.getExpressPayment(),
								trade.getExpressId());
				if (isPeriod) {
					trade.setExpressPayment("period_pay");
				}
				if (expressDist != null) {
					trade.setTotalWeight(DoubleUtil.round(orderWeight, 2));
					trade.setWeightFirst(expressDist.getWeightFirst());
					trade.setWeightFirstMoney(expressDist.getWeightFirstMoney());
					trade.setWeightNext(expressDist.getWeightNext());
					trade.setWeightNextMoney(expressDist.getWeightNextMoney());
				}
			}
			model.addAttribute("orderList", orderList);
			// �����е��ײͼ��ײ���Ʒ��
			Map parameterMap = new HashMap();
			List<PackageTrade> packages = packageManager.getPackagesByTid(trade
					.getTid());
			parameterMap.put("tid", trade.getTid());
			for (PackageTrade packageinstance : packages) {
				parameterMap.put("packageId", packageinstance.getId());
				List<Order> orders = orderManager
						.getOrdersByParameterMap(parameterMap);
				for (Order o : orders) {
					GoodsInstance g = goodsInstanceManager.getInstance(o
							.getGoodsInstanceId());
					if (g != null) {
						o.setGoodsSn(g.getCode());
					}
				}
				Map packageMap = new HashMap();
				packageMap.put(packageinstance, orders);
				model.addAttribute("packageMap", packageMap);
			}
			// ֧����ʽ
			if (EnumExpressDistPayment.PAYMENT_FIRST.getKey().equals(
					trade.getExpressPayment())) {
				PayRecord payRecord = payRecordManager.getPayRecordByTid(trade
						.getTid());
				model.addAttribute("payRecord", payRecord);
			}
		}
		// ��ȡ�����вֿ����͵Ķ���
		Map paramMap = new HashMap();
		model.addAttribute("trade", trade);
		model.addAttribute("enumTradeStatusMap", EnumTradeStatus.toMap());
		model.addAttribute("depositoryFirstList", this.depositoryFirstManager
				.getDepositoryFirstListByParMap(paramMap));
		return "/crm/crmTradeDetail";
	}

	/**
	 * ����Excel����
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/crm/exportOrder")
	public void crmExportOrder(
			@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			AdminAgent agent, HttpServletResponse res, Model model)
			throws Exception {
		OutputStream outwt = null;

		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=trade"
					+ date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
			if (depfirstIdList != null && depfirstIdList.size() > 0) {
				tradeListQuery.setDepfirstIds(depfirstIdList);
			}

			if (depfirstIdList != null) {
				if (StringUtil.isNotBlank(tradeListQuery.getOpp())) {
					if (StringUtil.isNotBlank(tradeListQuery.getStatus())) {
						tradeListQuery.setOpp(tradeListQuery.getStatus());
						tradeListQuery.setStatus(null);
					} else {
						tradeListQuery.setStatus("opp");
					}
				}
			}

			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "������", "�µ�ʱ��", "����ǳ�", "�ܽ��(Ԫ)", "����ʱ��", "������ʽ",
					"����״̬" };
			tradeExportList.add(title);
			List<Trade> tradeListAll = tradeManager
					.getTradesByParameterMap(tradeListQuery);
			if (tradeListAll != null) {
				df.applyPattern("yyyy-MM-dd HH:mm:ss");

				for (Trade tmp : tradeListAll) {
					String createTime = "";
					String payTime = "";
					if (tmp.getGmtCreate() != null) {
						createTime = df.format(tmp.getGmtCreate());
					}
					if (tmp.getPayTime() != null) {

						payTime = df.format(tmp.getPayTime());
					}
					String[] data = {
							tmp.getTid(),
							createTime,
							tmp.getBuyNick(),
							DoubleUtil.add(tmp.getGoodsAmount(),
									tmp.getShippingAmount())
									+ "",
							payTime,
							EnumExpressDistPayment.toMap().get(
									tmp.getExpressPayment()),
							EnumTradeStatus.toMap().get(tmp.getStatus()) };
					tradeExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "����ʧ�ܣ�");
			log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * ������ƷExcel����
	 *
	 * @throws Exception
	 */

	public void crmExportOrderTwo(
			@ModelAttribute("tradeListQuery") TradeListQuery tradeListQuery,
			AdminAgent agent, HttpServletResponse res, Model model)
			throws Exception {

		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition",
					"attachment; filename=tradeGoods" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Long> depfirstIdList = getDepfirstIdForQuery(agent);
			if (depfirstIdList != null && depfirstIdList.size() > 0) {
				tradeListQuery.setDepfirstIds(depfirstIdList);
			}

			if (depfirstIdList != null) {
				if (StringUtil.isNotBlank(tradeListQuery.getOpp())) {
					if (StringUtil.isNotBlank(tradeListQuery.getStatus())) {
						tradeListQuery.setOpp(tradeListQuery.getStatus());
						tradeListQuery.setStatus(null);
					} else {
						tradeListQuery.setStatus("opp");
					}
				}
			}

			List<String[]> tradeExportList = new ArrayList<String[]>();
			String[] title = { "������", "������", "����ǳ�", "��Ʒ����", "��Ʒ����", "��Ʒ����",
					"������λ", "����", "�ɹ����ۣ�Ԫ��", "�ɹ����", "��������", "���ۣ�Ԫ��", "�ܶԪ��",
					"�˷�", "�ջ���", "��ϵ�绰", "�ջ���ַ", "����" };
			tradeExportList.add(title);
			List<Trade> tradeListAll = tradeManager
					.getTradesByParameterMap(tradeListQuery);
			if (tradeListAll != null) {
				df.applyPattern("yyyy-MM-dd HH:mm:ss");
				int orderNum = 1;
				for (Trade tmp : tradeListAll) {
					List<Order> orderList = orderManager
							.getOrdersNotInPackage(tmp.getTid());
					// ȡ����ϸ���ջ���ַ
					String address = this.regionManager
							.getAddressInfo(tmp.getProvince(), tmp.getCity(),
									tmp.getDistrict());
					address += tmp.getAddress();
					// ȡ��������ʽ
					String expressName = "";
					if (tmp.getExpressId() != null) {
						Express expressTemp = expressManager.getExpress(tmp
								.getExpressId());
						if (expressTemp != null) {
							expressName = expressTemp.getExpressName();
						}
					}

					if (orderList != null)
						for (Order order : orderList) {

							Goods good = goodsManager.getGoods(order
									.getGoodsId());
							GoodsInstance g = goodsInstanceManager
									.getInstance(order.getGoodsInstanceId());

							String[] data = {
									orderNum + "",
									tmp.getTid(),
									tmp.getBuyNick(),
									good.getGoodsSn(),
									order.getGoodsTitle(),
									attributeManager
											.getFullAttributeStringByAttrs(g
													.getAttrs()),
									g.getGoodsUnit(),
									order.getGoodsNumber() + "",
									"",
									"",
									"",
									order.getGoodsPrice() + "",
									order.getGoodsNumber()
											* order.getGoodsPrice() + "",
									tmp.getShippingAmount() + "",
									tmp.getReceiver(), tmp.getMobile(),
									address, expressName };
							tradeExportList.add(data);
							orderNum++;
						}
				}
			}
			goodsBatch.exportExcel(outwt, tradeExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "����ʧ�ܣ�");
			log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * �ر��ļ�����
	 *
	 * @param out
	 *            OutputStream
	 */
	public static void close(OutputStream out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * �ͷ���������
	 *
	 * @return
	 */
	@RequestMapping(value = "/crm/crmMessageConserve")
	public String crmMessageConserve(
			@RequestParam(value = "csMessage", required = false, defaultValue = "") String csMessage,
			@RequestParam(value = "tid", required = false, defaultValue = "") String tid,
			Model model, AdminAgent agent) {
		if (StringUtil.isNotBlank(tid)) {
			tradeManager.updateMessageByTradeId(Long.parseLong(tid), csMessage);
		}
		this.showCrmAdminTradeDetail(null, tid, model, agent);
		return "/crm/crmTradeDetail";
	}
}
