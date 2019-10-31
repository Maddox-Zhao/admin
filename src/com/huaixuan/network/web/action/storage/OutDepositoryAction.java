package com.huaixuan.network.web.action.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.admin.Resources;
import com.huaixuan.network.biz.domain.agent.InterfaceUserTrade;
import com.huaixuan.network.biz.domain.express.Express;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.express.query.ExpressQuery;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.domain.remote.InterfaceApply;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.GatherOutDepository;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.OutDepAnalysis;
import com.huaixuan.network.biz.domain.storage.OutDepository;
import com.huaixuan.network.biz.domain.storage.OutDepositoryStorage;
import com.huaixuan.network.biz.domain.storage.OutDepositoryVO;
import com.huaixuan.network.biz.domain.storage.OutDetail;
import com.huaixuan.network.biz.domain.storage.OutDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.OutDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationOut;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.domain.taobao.TaobaoApply;
import com.huaixuan.network.biz.domain.trade.PayRecord;
import com.huaixuan.network.biz.domain.trade.Trade;
import com.huaixuan.network.biz.domain.trade.TradePackage;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDirectRegionCode;
import com.huaixuan.network.biz.enums.EnumExpressDistPayment;
import com.huaixuan.network.biz.enums.EnumExpressStatus;
import com.huaixuan.network.biz.enums.EnumFinanceStatus;
import com.huaixuan.network.biz.enums.EnumInOutDepository;
import com.huaixuan.network.biz.enums.EnumInterfaceType;
import com.huaixuan.network.biz.enums.EnumOutDepository;
import com.huaixuan.network.biz.enums.EnumOutDetailStatus;
import com.huaixuan.network.biz.enums.EnumOutStatus;
import com.huaixuan.network.biz.enums.EnumRegionCode;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.OutDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.admin.ResourcesManager;
import com.huaixuan.network.biz.service.agent.InterfaceUserTradeManager;
import com.huaixuan.network.biz.service.express.ExpressManager;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.remote.InterfaceApplyManager;
import com.huaixuan.network.biz.service.remote.TaobaoInterfaceApplyManager;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundDetailService;
import com.huaixuan.network.biz.service.stock.ShoppingRefundService;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.InDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.huaixuan.network.biz.service.storage.ProdRelationOutManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.trade.PayRecordManager;
import com.huaixuan.network.biz.service.trade.TradeManager;
import com.huaixuan.network.biz.service.trade.TradePackageManager;
import com.huaixuan.network.biz.service.user.MailEngine;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.biz.service.user.WebSiteEmailManager;
import com.huaixuan.network.common.util.Billing_Deal_DetailsUtil;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.common.util.RequestUtil;
import com.huaixuan.network.common.util.remote.TaobaoUtils;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/storage")
public class OutDepositoryAction extends BaseAction {

	Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = -2085112849749418089L;

	// Springע��
	@Autowired
	OutDepositoryManager outDepositoryManager;
	@Autowired
	ExpressManager expressManager;
	@Autowired
	AttributeManager attributeManager;
	@Autowired
	CategoryManager categoryManager;
	@Autowired
	GoodsBatchManager goodsBatch;
	@Autowired
	OutDetailManager outDetailManager;
	@Autowired
	TradeManager tradeManager;
	@Autowired
	TradePackageManager tradePackageManager;
	@Autowired
	RegionManager regionManager;
	@Autowired
	PayRecordManager payRecordManager;
	@Autowired
	UserManager userManager;
	@Autowired
	InterfaceApplyManager interfaceApplyManager;
	@Autowired
	InterfaceUserTradeManager interfaceUserTradeManager;
	@Autowired
	WebSiteEmailManager webSiteEmailManager;
	@Autowired
	ShoppingRefundService shoppingRefundService;
	@Autowired
	ShoppingRefundDetailService shoppingRefundDetailService;
	@Autowired
	ShoppingListService shoppingListService;
	@Autowired
	private AdminService adminService;
	// private CategoryManager categoryManager;
	@Autowired
	private DepLocationManager depLocationManager;
	@Autowired
	private StorageManager storageManager;
	@Autowired
	private ProdRelationOutManager prodRelationOutManager;
	@Autowired
	private InDepositoryManager inDepositoryManager;
	// private GoodsBatchManager goodsBatch;
	private List<Express> expressInfoList;
	private String message;

	@Autowired
	private MailEngine mailEngine;
	// private List<Depository> depositoryList;
	// private List<DepLocation> depLocationLists;
	// private String goodsCodeStr = "";// ȡ���ⶩ���еĲ�Ʒ���� add by fanyj 2010-11-17
	// private List<OutDepositoryVO> outDepositoryVOList;
	@Autowired
	private ResourcesManager resourcesManager;// add by shenzh 2010-10-22 ��ѯһЩ��Դ������Ϣ
	@Autowired
	private TaobaoInterfaceApplyManager taobaoInterfaceApplyManager;
	//
	//
	// //����
	// private Map<String, String> outDepositoryTypeMap = EnumOutDepository.toMap();
	// private Map<String, String> outDepositoryStatusMap = EnumOutStatus.toMap();
	// private Map<String, String> expressDistPaymentMap = EnumExpressDistPayment.toMap();
	// private Map<String, String> printMap = new HashMap<String, String>();
	// private Page page;
	// private String outDepId;
	// private Date currDate;
	private List<OutDepository> outDepositoryDisLists;
	// private List<ProdRelationOut> prodRelationOutLists;
	// private List<User> userLists;
	// private String errorInfo;
	// private String succInfo;
	// private Boolean succFlag = Boolean.FALSE;
	// private OutDepository outDepositoryDispaly;
	// private List<OutDetailGoods> outDetailGoodsLists;
	private List<GatherOutDepository> gatherOutDepositoryLists;

	// private String optId;
	// private String optType;
	// private OutDetailBaseInfo outDetailBaseInfo;
	// private List<OutDepositoryStorage> outStorageList;
	// private List<OutDepositoryStorage> outDetailInfoForDisList;
	// private Boolean disExpressFlag = Boolean.FALSE;
	// private String ctx;
	// private String[] batchNums;
	// private List<ShoppingRefundDetail> shoppingRefundDetailList;
	// private List<Admin> userList;
	// private List<DepositoryFirst> depositoryFirstList; //һ���ֿ��б�
	// private Map<String, String> financenStatusMap = EnumFinanceStatus.toMap(); //����ȷ��״̬
	// private Map<String, String> storTypeMap = EnumStorType.toMap();
	// private Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;

	// private List<OutDepAnalysis> outDepAnalysisList ;//���ڳ���ͳ��
	// private Map<String,String> outDepParam = new HashMap();//���ڳ���ͳ��

	/**
	 * ����ͳ��
	 *
	 * @author chenhang 2011/01/13
	 * @return
	 */
	@RequestMapping("/outDepAnalysis")
	public String outDepAnalysis(@RequestParam(value = "dateStart", required = false) String gmtOutDepStart,
			@RequestParam(value = "dateEnd", required = false) String gmtOutDepEnd,
			@RequestParam(value = "init", required = false) String initFlag,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model) {
		DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");// ����ʱ����������¼������
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
			gmtOutDepStart = fm.format(DateUtil.getDate(new Date(), -30));
			gmtOutDepEnd = fm.format(new Date());
		}

		Map<String, String> outDepParam = new HashMap<String, String>();
		outDepParam.put("gmtOutDepStart", gmtOutDepStart);
		outDepParam.put("gmtOutDepEnd", gmtOutDepEnd);

		QueryPage page = outDepositoryManager.getOutDepAnalysis(outDepParam, currPage, pageSize, true);
		model.addAttribute("page", page);

		model.addAttribute("gmtOutDepStart", gmtOutDepStart);
		model.addAttribute("gmtOutDepEnd", gmtOutDepEnd);
		return "/storage/outDepAnalysis";
	}

	/**
	 * ����ͳ��excel����
	 *
	 * @author chenhang 2011/01/13
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportOutDepAnalysis")
	public String exportOutDepAnalysis(@RequestParam(value = "dateStart", required = false) String gmtOutDepStart,
			@RequestParam(value = "dateEnd", required = false) String gmtOutDepEnd,
			@RequestParam(value = "init", required = false) String initFlag, HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");// ����ʱ����������¼������
			if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
				// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
				gmtOutDepStart = fm.format(DateUtil.getDate(new Date(), -30));
				gmtOutDepEnd = fm.format(new Date());
			}

			Map<String, String> outDepParam = new HashMap<String, String>();
			outDepParam.put("gmtOutDepStart", gmtOutDepStart);
			outDepParam.put("gmtOutDepEnd", gmtOutDepEnd);

			QueryPage page = outDepositoryManager.getOutDepAnalysis(outDepParam, 0, pageSize, false);
			List<OutDepAnalysis> outDepAnalysisList = (List<OutDepAnalysis>) page.getItems();

			Date da = new Date();
			outwt = res.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=outDepAnalysisList" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> outDepAnalysisExportList = new ArrayList<String[]>();
			String[] title = { "ʱ���", "����", "��������" };
			outDepAnalysisExportList.add(title);
			if (outDepAnalysisList != null && outDepAnalysisList.size() > 0) {
				for (OutDepAnalysis tmp : outDepAnalysisList) {
					String[] data = { tmp.getGmtOutDep() == null ? "" : df.format(tmp.getGmtOutDep()),
							String.valueOf(tmp.getOutDepSum()), String.valueOf(tmp.getGoodsNum()) };
					outDepAnalysisExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, outDepAnalysisExportList);
			outwt.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			close(outwt);
		}
		return "/storage/outDepAnalysis";
	}

	/**
	 * ���ⵥ�ɱ�����ͳ�Ƶ���
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author fanyj 2009/09/15
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportGetherOutDep")
	public void exportGetherOutDep(@ModelAttribute("gatherQuery") GatherQuery gatherQuery,
			AdminAgent adminAgent,HttpServletResponse res)
			throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setContentType("application/octet-stream;charset=utf-8");
			List<Object[]> gatherInDepList = new ArrayList<Object[]>();
			res.setHeader("Content-disposition", "attachment; filename=GetherOutDepository_" + date + ".xls");

			// һ���ֿ�Ȩ�޹���
			gatherQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
			QueryPage queryPage = outDepositoryManager.gatherOutDepositoryLists(gatherQuery, 1, pageSize, false);
			gatherOutDepositoryLists = (List<GatherOutDepository>) queryPage.getItems();
			if (gatherOutDepositoryLists != null && gatherOutDepositoryLists.size() > 0) {
				List<GatherOutDepository> gatherSearchLists = new ArrayList<GatherOutDepository>();
				for (GatherOutDepository obj : gatherOutDepositoryLists) {
					obj.setAttrs(attributeManager.getFullAttributeStringByAttrs(obj.getAttrs()));
					gatherSearchLists.add(obj);
				}
				gatherOutDepositoryLists = gatherSearchLists;
			}

			String[] title = { "���ݱ��", "��Ʒ����", "��Ʒ����", "��Ʒ����", "���ʱ��", "���ۣ�����", "����", "������" };
			gatherInDepList.add(title);
			if (gatherOutDepositoryLists != null) {
				for (GatherOutDepository obj : gatherOutDepositoryLists) {
					if (obj.getUnitPrice() == null) {
						obj.setUnitPrice(new Double(0.00));
					}
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					Object[] data = { obj.getBillNum(), obj.getInstanceName(), obj.getGoodsInstanceCode(),
							obj.getAttrs(), df.format(obj.getGmtOutDep()), DoubleUtil.round(obj.getUnitPrice(), 2),
							obj.getOutNum(), DoubleUtil.round(obj.getSumMoney(), 2) };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(outwt);
		}
	}

	/**
	 * ���ⵥ�ɱ�����ͳ�Ʋ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gather_out_depository")
	public String gatherOutDepository(@ModelAttribute("gatherQuery") GatherQuery gatherQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "init", required = false, defaultValue = "") String init,
			AdminAgent adminAgent, Model model) throws Exception {
		// һ���ֿ�Ȩ�޹���
		gatherQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		// Ĭ�ϲ�ѯ30�������
		if ("true".equals(init)) {
			gatherQuery.setOutDepTimeStart(DateUtil.getDiffDate(new Date(), -30));
			gatherQuery.setOutDepTimeEnd(DateUtil.getDateToString(new Date()));
		}
		// ȡ����ⵥ����
		QueryPage queryPage = outDepositoryManager.gatherOutDepositoryLists(gatherQuery, currPage, pageSize, true);
		model.addAttribute("attributeManager", attributeManager);
		model.addAttribute("query", queryPage);

		return "/storage/gather_out_depository";
	}

	/**
	 * ��ӡ���ⵥ
	 *
	 * @param id
	 *            ���ⵥID
	 * @param pageNum
	 *            ÿҳ��ʾ����
	 * @param fontSize
	 *            �����С
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("printOutDep")
	public String printOutDepository(@RequestParam("id") Long id,
			@RequestParam(value = "pageNum", required = false, defaultValue = "8") Integer pageNum,
			@RequestParam(value = "fontSize", required = false, defaultValue = "3") Integer fontSize, Model model,
			AdminAgent agent) throws Exception {
		model.addAttribute("id", id);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("fontSize", fontSize);

		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);

		// ȡ�ó��ⵥ������Ϣ
		OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(id);
		model.addAttribute("outDepositoryDispaly", outDepositoryDispaly);

		List<ProdRelationOut> prodRelationOutLists = prodRelationOutManager.getPrintList(id);
		model.addAttribute("prodRelationOutLists", prodRelationOutLists);

		// ������Ϣ
		Trade tradeInfoTemp = null;
		Set<String> shoppingNumSet = new HashSet<String>(); // �ɹ�����
		Set<String> supplierNameSet = new HashSet<String>(); // ��Ӧ������
		if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
			// ���۶���
			tradeInfoTemp = tradeManager.getTradeByTid(getRelationNumString(outDepositoryDispaly.getRelationNum(),
					outDepositoryDispaly.getType(), true));
		} else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
			// ���ۻ���
			tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly.getRelationNum());
		} else if (EnumOutDepository.OUT_SHOPPING.getKey().equals(outDepositoryDispaly.getType())) {
			// �ɹ��˻� zhangwy
			Map paramMap = new HashMap();
			paramMap.put("refNum", outDepositoryDispaly.getRelationNum());
			ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefunds(paramMap);

			paramMap.clear();
			paramMap.put("shopRefId", shoppingRefund.getId());
			List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService.getRefundDetail(paramMap);
			for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
				ShoppingList shoppingList = shoppingListService.getShoppingList(obj.getShoppingId());
				shoppingNumSet.add(shoppingList.getShoppingNum());
				supplierNameSet.add(shoppingList.getSupplierName());
			}
		}
		model.addAttribute("shoppingNumSet", shoppingNumSet);
		model.addAttribute("supplierNameSet", supplierNameSet);

		String buyNick = "";// �ͻ�����
		String expressPayment = "";// ֧����ʽ
		String remark = ""; // ��ע��Ϣ
		String expressName = "";// ������˾����
		double shippingAmount = 0;// �˷�
		String invoiceName = ""; // ��Ʊ̧ͷ
		String serviceNote = ""; // �ͷ���ע
		if (tradeInfoTemp != null) {
			if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
				if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
					shippingAmount = getShippingAmount(outDepositoryDispaly.getRelationNum());
				} else {
					shippingAmount = tradeInfoTemp.getShippingAmount();
				}
			} else {
				shippingAmount = new Double(0);
			}
			buyNick = tradeInfoTemp.getBuyNick();
			expressPayment = tradeInfoTemp.getExpressPayment();
			remark = tradeInfoTemp.getBuyerNote();
			invoiceName = tradeInfoTemp.getInvoiceName();
			serviceNote = tradeInfoTemp.getSeviceNote();
			Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
			if (expressInfoTemp != null) {
				expressName = expressInfoTemp.getExpressName();
			}
		}
		model.addAttribute("remark", remark);
		model.addAttribute("expressName", expressName);
		model.addAttribute("shippingAmount", shippingAmount);
		model.addAttribute("buyNick", buyNick);
		model.addAttribute("expressPayment", expressPayment);
		model.addAttribute("invoiceName", invoiceName);
		model.addAttribute("serviceNote", serviceNote);

		double sumMoney = 0;
		int sumCount = 0;
		for (ProdRelationOut obj : prodRelationOutLists) {
			sumMoney += obj.getMoney();
			sumCount += obj.getAmount();
		}
		model.addAttribute("sumMoney", sumMoney);
		model.addAttribute("sumCount", sumCount);

		model.addAttribute("countMoney", shippingAmount + sumMoney);
		model.addAttribute("userName", agent.getUsername());

		model.addAttribute("outDepositoryTypeMap", EnumOutDepository.toMap());
		model.addAttribute("expressDistPaymentMap", EnumExpressDistPayment.toMap());
		model.addAttribute("attributeManager", attributeManager);
		return "/storage/printOutDep";
	}

	/**
	 * ��ӡ���ų��ⵥ
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("printMulOutDep")
	public String printMulOutDepository(@RequestParam("ids") String idStr, Model model, AdminAgent agent)
			throws Exception {
		model.addAttribute("idStr", idStr);

		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);

		List<OutDepositoryVO> outDepositoryVOList = new ArrayList<OutDepositoryVO>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			if (id == null || id.length() == 0) {
				continue;
			}

			OutDepositoryVO outDepositoryVO = new OutDepositoryVO();

			// ȡ�ó��ⵥ������Ϣ
			OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(Long.valueOf(id));
			outDepositoryVO.setOutDepository(outDepositoryDispaly);

			List<ProdRelationOut> prodRelationOutLists = prodRelationOutManager.getPrintList(Long.valueOf(id));
			outDepositoryVO.setProdRelationOutList(prodRelationOutLists);

			// ������Ϣ
			Trade tradeInfoTemp = null;
			String expressName = "";// ������˾����
			double shippingAmount = 0;// �˷�
			String buyNick = "";// �ͻ�����
			String expressPayment = "";// ֧����ʽ
			String remark = ""; // ��ע��Ϣ
			Set<String> shoppingNumSet = new HashSet<String>(); // �ɹ�����
			Set<String> supplierNameSet = new HashSet<String>(); // ��Ӧ������
			String invoiceName = ""; //��Ʊ̧ͷ
            String serviceNote = ""; //�ͷ���ע
			if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
				// ���۶���
				tradeInfoTemp = tradeManager.getTradeByTid(getRelationNumString(outDepositoryDispaly.getRelationNum(),
						outDepositoryDispaly.getType(), true));
			} else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
				// ���ۻ���
				tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly.getRelationNum());
			} else if (EnumOutDepository.OUT_SHOPPING.getKey().equals(outDepositoryDispaly.getType())) {
				// �ɹ��˻� zhangwy
				Map paramMap = new HashMap();
				paramMap.put("refNum", outDepositoryDispaly.getRelationNum());
				ShoppingRefund shoppingRefund = shoppingRefundService.getShoppingRefunds(paramMap);

				paramMap.clear();
				paramMap.put("shopRefId", shoppingRefund.getId());
				List<ShoppingRefundDetail> shoppingRefundDetailList = shoppingRefundDetailService
						.getRefundDetail(paramMap);

				for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
					ShoppingList shoppingList = new ShoppingList();
					shoppingList = shoppingListService.getShoppingList(obj.getShoppingId());
					shoppingNumSet.add(shoppingList.getShoppingNum());
					supplierNameSet.add(shoppingList.getSupplierName());
				}
			}
			if (tradeInfoTemp != null) {
				if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
					if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
						shippingAmount = getShippingAmount(outDepositoryDispaly.getRelationNum());
					} else {
						shippingAmount = tradeInfoTemp.getShippingAmount();
					}
				} else {
					shippingAmount = new Double(0);
				}
				buyNick = tradeInfoTemp.getBuyNick();
				expressPayment = tradeInfoTemp.getExpressPayment();
				remark = tradeInfoTemp.getBuyerNote();
				invoiceName = tradeInfoTemp.getInvoiceName();
                serviceNote = tradeInfoTemp.getSeviceNote();
				Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
				if (expressInfoTemp != null) {
					expressName = expressInfoTemp.getExpressName();
				}
			}

			double sumMoney = 0;
			int sumCount = 0;
			for (ProdRelationOut obj : prodRelationOutLists) {
				sumMoney += obj.getMoney();
				sumCount += obj.getAmount();
			}
			// �������ݵ�Ĭ������Ϊ3�Ŵ�С
            model.addAttribute("fontSize", 3);
            // ���ñ����Ĭ������Ϊ3�Ŵ�С
            model.addAttribute("titleSize", 3);

			Map otherInfoMap = new HashMap();
			otherInfoMap.put("remark", remark);
			otherInfoMap.put("sumMoney", sumMoney);
			otherInfoMap.put("sumCount", sumCount);
			otherInfoMap.put("expressName", expressName);
			otherInfoMap.put("shippingAmount", shippingAmount);
			otherInfoMap.put("buyNick", buyNick);
			otherInfoMap.put("expressPayment", expressPayment);
			otherInfoMap.put("countMoney", shippingAmount + sumMoney);
			otherInfoMap.put("userName", agent.getUsername());
			otherInfoMap.put("shoppingNumSet", shoppingNumSet);
			otherInfoMap.put("supplierNameSet", supplierNameSet);
			otherInfoMap.put("invoiceName", invoiceName);
            otherInfoMap.put("serviceNote", serviceNote);
			outDepositoryVO.setOtherInfoMap(otherInfoMap);

			outDepositoryVOList.add(outDepositoryVO);
		}

		model.addAttribute("outDepositoryVOList", outDepositoryVOList);

		model.addAttribute("outDepositoryTypeMap", EnumOutDepository.toMap());
		model.addAttribute("expressDistPaymentMap", EnumExpressDistPayment.toMap());
		model.addAttribute("attributeManager", attributeManager);
		return "/storage/printMulOutDep";
	}

	// /**
	// * ���ⵥ�ɱ�����ͳ�Ƶ���
	// *
	// * @return String �ɹ����
	// * @throws Exception
	// * @author fanyj 2009/09/15
	// */
	// public String exportGetherOutDep() throws Exception {
	// OutputStream outwt = null;
	// HttpServletRequest request = getRequest();
	// try {
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setContentType("application/octet-stream;charset=utf-8");
	// List<Object[]> getherInDepList = new ArrayList<Object[]>();
	// res.setHeader("Content-disposition", "attachment; filename=GetherOutDepository_" + date
	// + ".xls");
	// AdminUser adminUser = getLoginAdminUser();
	// //����ȫ����һ���ֿ�ID
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
	// gatherOutDepositoryLists = null;
	// } else {
	//
	// gatherOutDepositoryLists = outDepositoryManager.gatherOutDepositoryLists(parMap,
	// null);
	// if (gatherOutDepositoryLists != null && gatherOutDepositoryLists.size() > 0) {
	// List<GatherOutDepository> gatherSearchLists = new ArrayList<GatherOutDepository>();
	// for (GatherOutDepository obj : gatherOutDepositoryLists) {
	// obj
	// .setAttrs(attributeManager
	// .getFullAttributeStringByAttrs(obj.getAttrs()));
	// gatherSearchLists.add(obj);
	// }
	// gatherOutDepositoryLists = gatherSearchLists;
	// }
	//
	// }
	// String[] title = { "���ݱ��", "��Ʒ����", "��Ʒ����", "��Ʒ����", "���ʱ��", "���ۣ�����", "����", "������" };
	// getherInDepList.add(title);
	// if (gatherOutDepositoryLists != null) {
	// for (GatherOutDepository obj : gatherOutDepositoryLists) {
	// if (obj.getUnitPrice() == null) {
	// obj.setUnitPrice(new Double(0.00));
	// }
	// if (obj.getSumMoney() == null) {
	// obj.setSumMoney(new Double(0.00));
	// }
	// Object[] data = { obj.getBillNum(), obj.getInstanceName(),
	// obj.getGoodsInstanceCode(), obj.getAttrs(),
	// df.format(obj.getGmtOutDep()), DoubleUtil.round(obj.getUnitPrice(), 2),
	// obj.getOutNum(), DoubleUtil.round(obj.getSumMoney(), 2)};
	// getherInDepList.add(data);
	// }
	// }
	// goodsBatch.exportExcelByObject(outwt, getherInDepList);
	// outwt.flush();
	// } catch (Exception e) {
	// request.setAttribute("errorMessage", "����ʧ�ܣ�");
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * ���ⵥ�ɱ�����ͳ�Ʋ�ѯ
	// * @return
	// * @throws Exception
	// */
	// public String getherOutDepository() throws Exception {
	// //����ȫ����һ���ֿ�ID
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
	// gatherOutDepositoryLists = null;
	// return SUCCESS;
	// }
	// // Ĭ�ϲ�ѯ30�������
	// HttpServletRequest request = getRequest();
	// String isFirst = request.getParameter("isFirst");
	//
	// if ("true".equals(isFirst)) {
	// Date now = new Date();
	// Timestamp nowTs = new Timestamp(now.getTime());
	// Date before30 = DateUtil.getDate(now, -30);
	// Timestamp beforeTs = new Timestamp(before30.getTime());
	//
	// if (StringUtils.isBlank((String) parMap.get("outDepTimeStart"))) {
	// parMap.put("outDepTimeStart", DateUtil.getTimestampToString(beforeTs));
	// }
	// if (StringUtils.isBlank((String) parMap.get("outDepTimeEnd"))) {
	// parMap.put("outDepTimeEnd", DateUtil.getTimestampToString(nowTs));
	// }
	// }
	// // ȡ�÷�����������������
	// int count = outDepositoryManager.gatherOutDepositoryListsCount(parMap);
	// // ���з�ҳ������
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setTotalRowsAmount(count);
	// page.setCurrentPage(currentPage);
	// // ȡ����ⵥ����
	// if (count > 0) {
	// gatherOutDepositoryLists = outDepositoryManager.gatherOutDepositoryLists(parMap, page);
	// for (GatherOutDepository obj : gatherOutDepositoryLists) {
	// obj.setAttrs(attributeManager.getFullAttributeStringByAttrs(obj.getAttrs()));
	// }
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * ��ӡ���ⵥ
	// * @return
	// * @throws Exception
	// */
	// public String printOutDepository() throws Exception {
	// HttpServletRequest request = getRequest();
	// String pageNum = request.getParameter("pageNum");//ÿҳ��ʾ����
	// String fontSize = request.getParameter("fontSize");//�����С
	// String id = request.getParameter("id");// ���ⵥID
	// if (id != null) {
	// optId = id;
	// }
	// if (StringUtil.isBlank(pageNum)) {
	// pageNum = "8";
	// }
	// if (StringUtil.isBlank(fontSize)) {
	// fontSize = "3";
	// }
	// List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
	// for (DepositoryFirst depositoryFirst : depositoryFirstList) {
	// depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
	// }
	//
	// // ȡ�ó��ⵥ������Ϣ
	// outDepositoryDispaly = outDepositoryManager.getOutDepository(id);
	// prodRelationOutLists = prodRelationOutManager.getPrintList(Long.parseLong(id));
	// double sumMoney = 0;
	// int sumCount = 0;
	// for (ProdRelationOut obj : prodRelationOutLists) {
	// sumMoney += obj.getMoney();
	// sumCount += obj.getAmount();
	// }
	// // ������Ϣ
	// Trade tradeInfoTemp = null;
	// String expressName = "";// ������˾����
	// double shippingAmount = 0;// �˷�
	// String buyNick = "";// �ͻ�����
	// String expressPayment = "";// ֧����ʽ
	// String remark = ""; // ��ע��Ϣ
	// Set<String> shoppingNumSet = new HashSet<String>(); //�ɹ�����
	// Set<String> supplierNameSet = new HashSet<String>(); //��Ӧ������
	// if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
	// //���۶���
	// tradeInfoTemp = tradeManager.getTradeByTid(
	// getRelationNumString(outDepositoryDispaly.getRelationNum(), outDepositoryDispaly.getType(), true));
	// } else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(
	// outDepositoryDispaly.getType())) {
	// //���ۻ���
	// tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly
	// .getRelationNum());
	// } else if (EnumOutDepository.OUT_SHOPPING.getKey().equals(outDepositoryDispaly.getType())) {
	// //�ɹ��˻� zhangwy
	// ShoppingRefund shoppingRefund = new ShoppingRefund();
	// Map paramMap = new HashMap();
	// paramMap.put("refNum", outDepositoryDispaly.getRelationNum());
	// shoppingRefund = shoppingRefundService.getShoppingRefunds(paramMap);
	// paramMap.clear();
	// paramMap.put("shopRefId", shoppingRefund.getId());
	// shoppingRefundDetailList = shoppingRefundDetailService.getRefundDetail(paramMap);
	// long shoppingId = 0;
	// for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
	// ShoppingList shoppingList = new ShoppingList();
	// shoppingList = shoppingListService.getShoppingList(obj.getShoppingId());
	// shoppingNumSet.add(shoppingList.getShoppingNum());
	// supplierNameSet.add(shoppingList.getSupplierName());
	// }
	// }
	// if (tradeInfoTemp != null) {
	// if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(
	// outDepositoryDispaly.getType())) {
	// if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
	// shippingAmount=getShippingAmount(outDepositoryDispaly.getRelationNum());
	// }else{
	// shippingAmount=tradeInfoTemp.getShippingAmount();
	// }
	// } else {
	// shippingAmount=new Double(0);
	// }
	// buyNick = tradeInfoTemp.getBuyNick();
	// expressPayment = tradeInfoTemp.getExpressPayment();
	// remark = tradeInfoTemp.getBuyerNote();
	// Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
	// if (expressInfoTemp != null) {
	// expressName = expressInfoTemp.getExpressName();
	// }
	// }
	// request.setAttribute("id", id);
	// request.setAttribute("pageNum", Integer.valueOf(pageNum).intValue());
	// request.setAttribute("fontSize", Integer.valueOf(fontSize).intValue());
	// request.setAttribute("remark", remark);
	// request.setAttribute("sumMoney", sumMoney);
	// request.setAttribute("sumCount", sumCount);
	// request.setAttribute("expressName", expressName);
	// request.setAttribute("shippingAmount", shippingAmount);
	// request.setAttribute("buyNick", buyNick);
	// request.setAttribute("expressPayment", expressPayment);
	// request.setAttribute("countMoney", shippingAmount + sumMoney);
	// request.setAttribute("userName", getLoginAdminUser().getUsername());
	// request.setAttribute("shoppingNumSet", shoppingNumSet);
	// request.setAttribute("supplierNameSet", supplierNameSet);
	// return SUCCESS;
	// }
	//
	// /**
	// * ��ӡ���ų��ⵥ
	// * @return
	// * @throws Exception
	// */
	// public String printMulOutDepository() throws Exception {
	// HttpServletRequest request = getRequest();
	// String idStr = request.getParameter("ids");
	// String[] ids = null;
	// if(idStr != null && idStr.length()>0)
	// {
	// ids = idStr.split(",");
	// }
	// else
	// {
	// log.info("error paraments \"ids\", \"ids\" is null ");
	// }
	//
	// request.setAttribute("ids", idStr);
	//
	// if (ids != null) {
	// // optId = id;
	// }
	// List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
	// for (DepositoryFirst depositoryFirst : depositoryFirstList) {
	// depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
	// }
	// outDepositoryVOList = new ArrayList<OutDepositoryVO>();
	//
	// OutDepositoryVO outDepositoryVO = null;
	//
	//
	// for(int i=0;i<ids.length;i++){
	//
	// if(ids[i]==null || ids[i].length()==0)
	// {
	// continue;
	// }
	// outDepositoryVO = new OutDepositoryVO();
	//
	// // ȡ�ó��ⵥ������Ϣ
	// outDepositoryDispaly = outDepositoryManager.getOutDepository(new Long(ids[i]));
	// outDepositoryVO.setOutDepository(outDepositoryDispaly);
	//
	// prodRelationOutLists = prodRelationOutManager.getPrintList(Long.parseLong(ids[i]));
	// outDepositoryVO.setProdRelationOutList(prodRelationOutLists);
	//
	// Map otherInfoMap = new HashMap();
	//
	//
	// double sumMoney = 0;
	// int sumCount = 0;
	// for (ProdRelationOut obj : prodRelationOutLists) {
	// sumMoney += obj.getMoney();
	// sumCount += obj.getAmount();
	// }
	// // ������Ϣ
	// Trade tradeInfoTemp = null;
	// String expressName = "";// ������˾����
	// double shippingAmount = 0;// �˷�
	// String buyNick = "";// �ͻ�����
	// String expressPayment = "";// ֧����ʽ
	// String remark = ""; // ��ע��Ϣ
	// Set<String> shoppingNumSet = new HashSet<String>(); //�ɹ�����
	// Set<String> supplierNameSet = new HashSet<String>(); //��Ӧ������
	// if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
	// //���۶���
	// tradeInfoTemp = tradeManager.getTradeByTid(
	// getRelationNumString(outDepositoryDispaly.getRelationNum(), outDepositoryDispaly.getType(), true));
	// } else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(
	// outDepositoryDispaly.getType())) {
	// //���ۻ���
	// tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly
	// .getRelationNum());
	// } else if (EnumOutDepository.OUT_SHOPPING.getKey().equals(outDepositoryDispaly.getType())) {
	// //�ɹ��˻� zhangwy
	// ShoppingRefund shoppingRefund = new ShoppingRefund();
	// Map paramMap = new HashMap();
	// paramMap.put("refNum", outDepositoryDispaly.getRelationNum());
	// shoppingRefund = shoppingRefundService.getShoppingRefunds(paramMap);
	// paramMap.clear();
	// paramMap.put("shopRefId", shoppingRefund.getId());
	// shoppingRefundDetailList = shoppingRefundDetailService.getRefundDetail(paramMap);
	// long shoppingId = 0;
	// for (ShoppingRefundDetail obj : shoppingRefundDetailList) {
	// ShoppingList shoppingList = new ShoppingList();
	// shoppingList = shoppingListService.getShoppingList(obj.getShoppingId());
	// shoppingNumSet.add(shoppingList.getShoppingNum());
	// supplierNameSet.add(shoppingList.getSupplierName());
	// }
	// }
	// if (tradeInfoTemp != null) {
	// if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(
	// outDepositoryDispaly.getType())) {
	// if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
	// shippingAmount=getShippingAmount(outDepositoryDispaly.getRelationNum());
	// }else{
	// shippingAmount=tradeInfoTemp.getShippingAmount();
	// }
	// } else {
	// shippingAmount=new Double(0);
	// }
	// buyNick = tradeInfoTemp.getBuyNick();
	// expressPayment = tradeInfoTemp.getExpressPayment();
	// remark = tradeInfoTemp.getBuyerNote();
	// Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
	// if (expressInfoTemp != null) {
	// expressName = expressInfoTemp.getExpressName();
	// }
	// }
	//
	// otherInfoMap.put("remark", remark);
	// otherInfoMap.put("sumMoney", sumMoney);
	// otherInfoMap.put("sumCount", sumCount);
	// otherInfoMap.put("expressName", expressName);
	// otherInfoMap.put("shippingAmount", shippingAmount);
	// otherInfoMap.put("buyNick", buyNick);
	// otherInfoMap.put("expressPayment", expressPayment);
	// otherInfoMap.put("countMoney", shippingAmount + sumMoney);
	// otherInfoMap.put("userName", getLoginAdminUser().getUsername());
	// otherInfoMap.put("shoppingNumSet", shoppingNumSet);
	// otherInfoMap.put("supplierNameSet", supplierNameSet);
	// outDepositoryVO.setOtherInfoMap(otherInfoMap);
	//
	// outDepositoryVOList.add(outDepositoryVO);
	// }
	// return SUCCESS;
	// }

	/**
	 * ʵ���˷Ѳ�ѯ TODO
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenhang 2010/11/16
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/actual_inventory")
	public String actualInventory(@RequestParam(value = "expressId", required = false) String expressId,
			@RequestParam(value = "expressCode", required = false) String expressCode,
			@RequestParam(value = "relationNum", required = false) String relationNum,
			@RequestParam(value = "billNum", required = false) String billNum,
			@RequestParam(value = "outDepTimeStart", required = false) String outDepTimeStart,
			@RequestParam(value = "outDepTimeEnd", required = false) String outDepTimeEnd,
			@RequestParam(value = "init", required = false) String init,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		Map parMap = new HashMap();

		if (expressId != null && expressId.length() > 0) {
			String[] tmp = expressId.split(",");
			if (tmp.length > 1) {
				parMap.put("expressId", tmp[1]);
			} else if (StringUtils.isNumeric(expressId)) {
				parMap.put("expressId", expressId);
			}
		}
		parMap.put("expressCode", expressCode);
		parMap.put("relationNum", relationNum);
		parMap.put("billNum", billNum);

		if (StringUtils.isNotBlank(init)) {
			parMap.put("outDepTimeStart", DateUtil.getDiffDate(new Date(), -30));
			parMap.put("outDepTimeEnd", DateUtil.getDateToString(new Date()));
		} else {
			parMap.put("outDepTimeStart", outDepTimeStart);
			parMap.put("outDepTimeEnd", outDepTimeEnd);
		}

		String[] type = { "out_sales", "out_sales_change" };
		parMap.put("types", type);
		QueryPage queryPage = outDepositoryManager.getActualInventoryLists(parMap, currPage, pageSize, true);
		model.addAttribute("parMap", parMap);
		model.addAttribute("expressInfoList", expressManager.listExpressByStatus(EnumExpressStatus.ENABLED.getKey()));
		model.addAttribute("query", queryPage);
		return "/storage/actual_inventory";
	}

	/**
	 * @Title: exportOutDepositoryList TODO
	 * @Description: ʵ���˷ѹ�����
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/exportOutDepositoryList")
	public void exportOutDepositoryList(@RequestParam(value = "expressId", required = false) String expressParam,
			@RequestParam(value = "expressCode", required = false) String expressCode,
			@RequestParam(value = "relationNum", required = false) String relationNum,
			@RequestParam(value = "billNum", required = false) String billNum,
			@RequestParam(value = "outDepTimeStart", required = false) String outDepTimeStart,
			@RequestParam(value = "outDepTimeEnd", required = false) String outDepTimeEnd,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			HttpServletResponse response) throws Exception {
		OutputStream outwt = null;
		try {
			Map parMap = new HashMap();
			String[] type = { "out_sales", "out_sales_change" };
			parMap.put("types", type);
			String[] expressTmp;
			if (expressParam != null && expressParam.length() > 0) {
				expressTmp = expressParam.split(",");
				parMap.put("expressId", expressTmp[1]);
			}
			parMap.put("expressCode", expressCode);
			parMap.put("relationNum", relationNum);
			parMap.put("billNum", billNum);
			parMap.put("outDepTimeStart", outDepTimeStart);
			parMap.put("outDepTimeEnd", outDepTimeEnd);

			// ȡ�÷�����������������
			QueryPage queryPage = outDepositoryManager.getActualInventoryLists(parMap, currPage, pageSize, false);
			outDepositoryDisLists = (List<OutDepository>) queryPage.getItems();

			Date da = new Date();
			outwt = response.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition", "attachment; filename=OutDepositoryList" + date + ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> outDepositoryExportList = new ArrayList<String[]>();
			String[] title = { "������ʽ", "��������", "����ʱ��", "������", "���ⵥ��", "��Ʒ����", "ʵ������", "��������", "ʵ���˷�" };
			outDepositoryExportList.add(title);
			if (outDepositoryDisLists != null) {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				for (OutDepository tmp : outDepositoryDisLists) {

					Long expressId = 0L;
					try {
						expressId = Long.valueOf(tmp.getExpressId());
					} catch (Exception e) {
					}
					String goodsWeightString = "";
					String actualInventoryString = "";
					if (tmp.getTradePackageList() != null && tmp.getTradePackageList().size() != 0) {
						for (TradePackage p : tmp.getTradePackageList()) {
							Express expressInfoTemp = expressManager.getExpress(p.getExpressId());
							if (expressInfoTemp != null) {
								// ����Ĭ�ϵ�������Ϣ
								tmp.setExpressName(expressInfoTemp.getExpressName());
								break;
							}
						}
						goodsWeightString = getGoodsWeightString(tmp.getTradePackageList());
						actualInventoryString = getActualInventoryString(tmp.getTradePackageList());
					} else {
						Express expressInfoTemp = expressManager.getExpress(expressId);
						if (expressInfoTemp != null) {
							// ����Ĭ�ϵ�������Ϣ
							tmp.setExpressName(expressInfoTemp.getExpressName());
						}
						goodsWeightString = String.valueOf(tmp.getGoodsWeight());
						actualInventoryString = MoneyUtil.getFormatMoney(tmp.getActualInventory(), "0.00");
					}

					String[] data = { tmp.getExpressName(), tmp.getExpressCode(),
							tmp.getGmtOutDep() == null ? "" : sf.format(tmp.getGmtOutDep()),
							getRelationNumString(tmp.getRelationNum(), tmp.getType(), false),
							tmp.getBillNum(), goodsWeightString,
							String.valueOf(tmp.getActualWeight()) == null ? "0.0":String.valueOf(tmp.getActualWeight()),
							String.valueOf(tmp.getCastWeight()) == null ? "0.0":String.valueOf(tmp.getCastWeight()),
							actualInventoryString };
					outDepositoryExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, outDepositoryExportList);
			outwt.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(outwt);
		}
		// return SUCCESS;
	}

	/**
	 * ���ⵥ��ѯ
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/28
	 */
	@RequestMapping("searchOutDepository")
	public String searchOutDepository(@ModelAttribute("query") OutDepositoryQuery query, Model model,
			@RequestParam(value = "init", required = false) String initFlag,
			@RequestParam(value = "outDepositoryIds", required = false, defaultValue = "") String outDepositoryIds,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, AdminAgent agent, ServletRequest req,
			HttpServletResponse res) throws Exception {
		model.addAttribute("outDepositoryTypeMap", EnumOutDepository.toMap());
		model.addAttribute("outDepositoryStatusMap", EnumOutStatus.toMap());

		expressInfoList = expressManager.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		model.addAttribute("expressInfoList", expressInfoList);

		List<Long> depositoryFirstIds = getDepfirstIdForQuery(agent);
		query.setDepfirstIds(depositoryFirstIds);
		List<DepositoryFirst> depositoryFirstList = null;
		if (depositoryFirstIds != null) {
			depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(depositoryFirstIds);
		}
		if (depositoryFirstList == null) {
			return "/storage/searchOutDepository";
		}
		model.addAttribute("depositoryFirstList", depositoryFirstList);

		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
		    //modified by chenyan 2011/03/14 �޸��˼������ڴ�30���ڸ�Ϊ3����
			// ��ʼ��ʱ����Ҫ����Ĭ�����ʱ��
			query.setGmtCreateStart(DateUtil.getDiffDate(new Date(), -2));
			query.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
			//added by chenyan 2011/03/25 start
			query.setStatus(EnumOutStatus.OUT_NEW.getKey());
			//added by chenyan 2011/03/25 end
		}

		if(pageSize == null) {
			pageSize = this.pageSize;
		}

		searchListByMap(outDepositoryIds, query, req, res, agent, currPage, pageSize, model);

		return "/storage/searchOutDepository";
	}

	/**
	 * �������۶������ⵥ��ѯ
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author fanyj 2009/11/28
	 */
	@RequestMapping("searchTodayList")
	public String searchTodayList(@RequestParam(value = "outDepositoryIds", required = false, defaultValue = "") String outDepositoryIds,
	                              @RequestParam(value = "page", required = false, defaultValue = "1") int currPage, AdminAgent agent,
	                              @ModelAttribute("query") OutDepositoryQuery query, Model model,
	                              ServletRequest req,
	                              HttpServletResponse res) throws Exception {
		model.addAttribute("outDepositoryTypeMap", EnumOutDepository.toMap());
		model.addAttribute("outDepositoryStatusMap", EnumOutStatus.toMap());

		List<Long> depfirstIds = getDepfirstIdForQuery(agent);
		if (depfirstIds == null || depfirstIds.size() == 0) {
			return "/storage/searchTodayList";
		}
		query.setDepfirstIds(depfirstIds);

		if (StringUtil.isBlank(query.getGmtCreateStart()) && StringUtil.isBlank(query.getGmtCreateEnd())) {
			// ��ʼ��ʱ����Ҫ����Ĭ�����ʱ��
			query.setGmtCreateStart(DateUtil.getDateToString(new Date()));
			query.setGmtCreateEnd(DateUtil.getDateToString(new Date()));
		}
		
		if(StringUtil.isBlank(query.getOptTimeStart()) && StringUtil.isBlank(query.getOptTimeEnd())){
			query.setOptTimeStart(DateUtil.getDateToString(new Date()));
			query.setOptTimeEnd(DateUtil.getDateToString(new Date()));
		}

		query.setType(EnumOutDepository.OUT_SALES.getKey());

		searchListByMap(outDepositoryIds, query, req, res, agent, currPage, pageSize, model);
		return "/storage/searchTodayList";
	}

	/*
	 * ����������ѯ���ⵥ�б�
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void searchListByMap(String outDepositoryIds,
	                             OutDepositoryQuery query,
	                             ServletRequest req,
	                             HttpServletResponse res,
	                             AdminAgent agent,
	                             int currPage,
	                             int pageSize,
			Model model) throws Exception {
		// ȡ��URL�е�ID����������ȡ������.
		String optFlag = req.getParameter("optFlag");
		if (StringUtil.isNotBlank(optFlag) && optFlag.equals("exportExcel")) {
			// Excel����
			exportOutDepository(query, res);
			return;
		}
		if (StringUtil.isNotBlank(optFlag) && optFlag.equals("handleOutDepository")) {
            //�ӹܴ�����ⵥ
            String optResult = handleOutDepository(outDepositoryIds, agent);
            //��������Ϣ����
            model.addAttribute("optResult", optResult);
        }
		// ȡ�÷�����������������
		Map param = BeanUtils.describe(query);
		param.put("depfirstIds", query.getDepfirstIds());
		QueryPage outDepositoryDisLists = outDepositoryManager.getOutDepositoryLists(param, currPage, pageSize, true);
		model.addAttribute("page", outDepositoryDisLists);

		// ����id����һ���ֿ����ƣ��ظ���id������ѯ��ֱ����ʾ��һ�λ�õ�����
		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);

		// ȡ�ù���ԱȨ�޵Ļ�Ա�б�
		// userLists = userManager.getUserByIsAdmin(1);
		// �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		List<Admin> userList = adminService.getAdminUserList();
		model.addAttribute("userList", userList);
	}

	 /**
     * ��¼�߽ӹܴ�����ⵥ
     * @param outDepositoryIds String
     * @param agent AdminAgent ��ǰ��¼�ߵ���Ϣ
     * @return String
     * @author chenyan 2011/03/25
     */
    private String handleOutDepository(String outDepositoryIds, AdminAgent agent) {
        Long adminIdTemp = null;
        // �ɹ���������
        int succCount = 0;
        // ʧ�ܴ�������
        int failCount = 0;
        if (agent != null) {
            adminIdTemp = agent.getId();
            //�ָ���ȡ���ĳ��ⵥID
            String[] outDepositoryIdsTemp = outDepositoryIds.split(",");
            if (outDepositoryIdsTemp != null && outDepositoryIdsTemp.length > 0) {
                for (String outDepositoryIdTemp : outDepositoryIdsTemp) {
                    int returnCount = outDepositoryManager.updateHandleAdminIdByUser(adminIdTemp, Long.parseLong(outDepositoryIdTemp));
                    if (returnCount > 0) {
                        succCount++;
                    } else {
                        failCount++;
                    }
                }
            }
        }

        return "�ӹܲ����ɹ���" + succCount + "����ʧ��" + failCount + "��(�ѱ����˽ӹ�)��";
    }

	/**
	 * �����������۷��������б�
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("exportTodaySalesList")
	public void exportTodaySalesList(@ModelAttribute("query") OutDepositoryQuery query, HttpServletResponse res,
			AdminAgent agent) throws Exception {
		OutputStream outwt = null;
		try {
			// ����������������
			List<Long> depfirstIds = getDepfirstIdForQuery(agent);
			query.setDepfirstIds(depfirstIds);

			Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
			for (DepositoryFirst depositoryFirst : depositoryFirstList) {
				depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
			}

			Map param = BeanUtils.describe(query);
			param.put("depfirstIds", query.getDepfirstIds());
			List<OutDepository> outDepositoryForExport = (List<OutDepository>) outDepositoryManager
					.getOutDepositoryLists(param, 0, pageSize, false).getItems();

			Date da = new Date();
			outwt = res.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=OutDepositoryList" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> outDepositoryExportList = new ArrayList<String[]>();
			String[] title = { "����", "���˵���", "���˷�", "������", "һ���ֿ�", "���ⵥ��", "�ջ���", "��ַ", "δ����ԭ��" };
			outDepositoryExportList.add(title);
			String receiveAddress = "";
			String receiver = "";
			if (outDepositoryForExport != null) {
				for (OutDepository tmp : outDepositoryForExport) {
					Trade tradeInfoTemp = null;
					// ���۶���ʱ����Ҫ��д��������
					tradeInfoTemp = tradeManager.getTradeByTid(getRelationNumString(tmp.getRelationNum(),
							tmp.getType(), true));
					if (tradeInfoTemp != null) {
						receiver = tradeInfoTemp.getReceiver();// �ջ���
						receiveAddress = regionManager.getAddressInfo(tradeInfoTemp.getProvince(),
								tradeInfoTemp.getCity(), tradeInfoTemp.getDistrict())
								+ tradeInfoTemp.getAddress();// �ջ���ַ
						if (tradeInfoTemp.getExpressId() != null) {
							Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
							if (expressInfoTemp != null) {
								// ����Ĭ�ϵ�������Ϣ
								tmp.setExpressName(expressInfoTemp.getExpressName());
							}
						}
					}

					String[] data = { df.format(tmp.getGmtModify()) + "", tmp.getExpressCode(), tmp.getExpressName(),
							getRelationNumString(tmp.getRelationNum(), tmp.getType(), false),
							depositoryNameMap.get(tmp.getDepFirstId()), tmp.getBillNum(), receiver, receiveAddress,
							StringUtil.isBlank(tmp.getRemark()) ? "" : tmp.getRemark() };
					outDepositoryExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, outDepositoryExportList);
			outwt.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			close(outwt);
		}
	}

	@SuppressWarnings("unchecked")
	private void exportOutDepository(OutDepositoryQuery query, HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			// ����������������
			Map param = BeanUtils.describe(query);
			param.put("depfirstIds", query.getDepfirstIds());

			List<OutDepository> outDepositoryForExport = (List<OutDepository>) outDepositoryManager
					.getOutDepositoryLists(param, 0, pageSize, false).getItems();

			Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
			for (DepositoryFirst depositoryFirst : depositoryFirstList) {
				depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
			}

			Date da = new Date();
			outwt = res.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=OutDepository" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> outDepositoryExportList = new ArrayList<Object[]>();
			String[] title = { "���ݱ��", "��������", "�������ݺ�", "���ʱ��", "��������", "����ʱ��", "������", "һ���ֿ�", "״̬", "�ܽ��" };
			outDepositoryExportList.add(title);
			if (outDepositoryForExport != null) {
				for (OutDepository tmp : outDepositoryForExport) {
					// zhangwy ���ӳ��ⵥ�ܽ��
					List<OutDetailGoods> outDetailGoodsLists = outDetailManager.getOutDetailGoodsLists(tmp.getId());
					double totalAmount = 0;
					for (OutDetailGoods outDetailGoods : outDetailGoodsLists) {
						totalAmount = totalAmount + outDetailGoods.getUnitPrice() * outDetailGoods.getOutNum();
					}
					SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Object[] data = {
							tmp.getBillNum() + "",
							StringUtil.isBlank(EnumOutDepository.toMap().get((tmp.getType()))) ? "" : EnumOutDepository
									.toMap().get((tmp.getType())) + "",
							getRelationNumString(tmp.getRelationNum(), tmp.getType(), false) + "",
							df2.format(tmp.getGmtCreate()) + "",
							tmp.getExpressCode() == null ? "" : tmp.getExpressCode() + "",
							df2.format(tmp.getGmtModify()) + "",
							StringUtil.isBlank(tmp.getCreater()) ? "" : tmp.getCreater() + "",

							depositoryNameMap.get(tmp.getDepFirstId()) + "",
							StringUtil.isBlank(EnumOutStatus.toMap().get(tmp.getStatus())) ? "" : EnumOutStatus.toMap()
									.get(tmp.getStatus()) + "", DoubleUtil.round(totalAmount, 2) };
					outDepositoryExportList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, outDepositoryExportList);
			outwt.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			close(outwt);
		}
	}

	public static void close(OutputStream out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	// /**
	// * �����������۷��������б�
	// * @return
	// * @throws Exception
	// */
	// public String exportTodaySalesList() throws Exception {
	// OutputStream outwt = null;
	// try {
	// //����������������
	// AdminUser adminUser = getLoginAdminUser();
	// String[] depFirst = adminUser.getDepfirstIdForQuery();
	// parMap.put("depfirstIds", depFirst);
	// List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
	// .getDepositoryFirstList();
	// for (DepositoryFirst depositoryFirst : depositoryFirstList) {
	// depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
	// }
	//
	// List<OutDepository> outDepositoryForExport = outDepositoryManager
	// .getOutDepositoryLists(parMap, null);
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// //ȡ�õ���excel��ʱ�䣬�����ļ�����
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setHeader("Content-disposition", "attachment; filename=OutDepositoryList" + date
	// + ".xls");
	// res.setContentType("application/octet-stream;charset=utf-8");
	// List<String[]> outDepositoryExportList = new ArrayList<String[]>();
	// String[] title = { "����", "���˵���", "���˷�", "������", "һ���ֿ�", "���ⵥ��", "�ջ���", "��ַ", "δ����ԭ��" };
	// outDepositoryExportList.add(title);
	// String receiveAddress = "";
	// String receiver = "";
	// if (outDepositoryForExport != null) {
	// for (OutDepository tmp : outDepositoryForExport) {
	// Trade tradeInfoTemp = null;
	// //���۶���ʱ����Ҫ��д��������
	// tradeInfoTemp = tradeManager.getTradeByTid(
	// getRelationNumString(tmp.getRelationNum(), tmp.getType(), true));
	// if (tradeInfoTemp != null) {
	// receiver = tradeInfoTemp.getReceiver();// �ջ���
	// receiveAddress = regionManager.getAddressInfo(tradeInfoTemp.getProvince(),
	// tradeInfoTemp.getCity(), tradeInfoTemp.getDistrict())
	// + tradeInfoTemp.getAddress();// �ջ���ַ
	// if (tradeInfoTemp.getExpressId() != null) {
	// Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp
	// .getExpressId());
	// if (expressInfoTemp != null) {
	// //����Ĭ�ϵ�������Ϣ
	// tmp.setExpressName(expressInfoTemp.getExpressName());
	// }
	// }
	// }
	//
	// String[] data = { df.format(tmp.getGmtModify()) + "", tmp.getExpressCode(),
	// tmp.getExpressName(), getRelationNumString(tmp.getRelationNum(), tmp.getType(), false),
	// depositoryNameMap.get(tmp.getDepFirstId()), tmp.getBillNum(), receiver,
	// receiveAddress,
	// StringUtil.isBlank(tmp.getRemark()) ? "" : tmp.getRemark() };
	// outDepositoryExportList.add(data);
	// }
	// }
	// goodsBatch.exportExcel(outwt, outDepositoryExportList);
	// outwt.flush();
	// } catch (Exception e) {
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }
	//
	// public String exportOutDepository() throws Exception {
	// OutputStream outwt = null;
	// try {
	// //����������������
	// List<OutDepository> outDepositoryForExport = outDepositoryManager
	// .getOutDepositoryLists(parMap, null);
	// List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
	// .getDepositoryFirstList();
	// for (DepositoryFirst depositoryFirst : depositoryFirstList) {
	// depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
	// }
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// //ȡ�õ���excel��ʱ�䣬�����ļ�����
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setHeader("Content-disposition", "attachment; filename=OutDepository" + date
	// + ".xls");
	// res.setContentType("application/octet-stream;charset=utf-8");
	//
	// List<Object[]> outDepositoryExportList = new ArrayList<Object[]>();
	// String[] title = { "���ݱ��", "��������", "�������ݺ�", "���ʱ��", "��������", "����ʱ��", "������", "һ���ֿ�", "״̬", "�ܽ��" };
	// outDepositoryExportList.add(title);
	// if (outDepositoryForExport != null) {
	// for (OutDepository tmp : outDepositoryForExport) {
	// //zhangwy ���ӳ��ⵥ�ܽ��
	// outDetailGoodsLists = outDetailManager.getOutDetailGoodsLists(tmp.getId());
	// double totalAmount = 0;
	// for (OutDetailGoods outDetailGoods : outDetailGoodsLists) {
	// totalAmount = totalAmount + outDetailGoods.getUnitPrice()
	// * outDetailGoods.getOutNum();
	// }
	// SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	// Object[] data = {
	// tmp.getBillNum() + "",
	// StringUtil.isBlank(EnumOutDepository.toMap().get((tmp.getType()))) ? ""
	// : EnumOutDepository.toMap().get((tmp.getType())) + "",
	// getRelationNumString(tmp.getRelationNum(), tmp.getType(), false) + "",
	// df2.format(tmp.getGmtCreate())+"",
	// tmp.getExpressCode() + "",
	// df2.format(tmp.getGmtModify()) + "",
	// StringUtil.isBlank(tmp.getCreater()) ? "" : tmp.getCreater() + "",
	//
	// depositoryNameMap.get(tmp.getDepFirstId()) + "",
	// StringUtil.isBlank(EnumOutStatus.toMap().get(tmp.getStatus())) ? ""
	// : EnumOutStatus.toMap().get(tmp.getStatus()) + "",
	// DoubleUtil.round(totalAmount, 2)};
	// outDepositoryExportList.add(data);
	// }
	// }
	// goodsBatch.exportExcelByObject(outwt, outDepositoryExportList);
	// outwt.flush();
	// } catch (Exception e) {
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }
	//
	// public static void close(OutputStream out) {
	//
	// try {
	// if (out != null) {
	// out.close();
	// }
	// } catch (IOException ioe) {
	// // ignore
	// }
	// }

	/**
	 * ����ID��ʾ���ⵥ����
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/28
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showOutDetail")
	public String showOutDetail(@RequestParam("outDepId") Long id,
			@RequestParam(value = "optType", required = false) String optType,
			@RequestParam(value = "outDepositoryTime", required = false) String gmtOutDep,
			@RequestParam(value = "goodsCodeStr", required = false) String goodsCodeStr, Model model,
			HttpServletRequest req, AdminAgent agent) throws Exception {
		model.addAttribute("disExpressFlag", false);

		// ȡ�ó��ⵥ������Ϣ
		OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(id);
		model.addAttribute("outDepositoryDispaly", outDepositoryDispaly);

		List<Express> expressInfoList = expressManager.listExpressByStatus(EnumExpressStatus.ENABLED.getKey());
		model.addAttribute("expressInfoList", expressInfoList);

		Trade tradeInfoTemp = null;
		if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())
				|| EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
			// ���۶������������˻���ʱ�����Ҫ��ʾ.
			model.addAttribute("disExpressFlag", true);
		}

		if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
			// ���۶���ʱ����Ҫ��д�������� TODO
			tradeInfoTemp = tradeManager.getTradeByTid(getRelationNumString(outDepositoryDispaly.getRelationNum(),
					outDepositoryDispaly.getType(), true));
		} else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
			// ���ۻ���ʱ����Ҫ��д��������
			tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly.getRelationNum());
		}

		if (tradeInfoTemp != null) {
			// ����������ʽ
			outDepositoryDispaly.setPayment(tradeInfoTemp.getExpressPayment());

			if (EnumExpressDistPayment.PAYMENT_FIRST.getKey().equals(tradeInfoTemp.getExpressPayment())) {
				PayRecord payRecord = payRecordManager.getPayRecordByTid(tradeInfoTemp.getTid());
				model.addAttribute("payRecord", payRecord);
			}

			if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
				if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
					outDepositoryDispaly.setExpressAmount(getShippingAmount(outDepositoryDispaly.getRelationNum()));
				} else {
					outDepositoryDispaly.setExpressAmount(tradeInfoTemp.getShippingAmount());
				}
			} else {
				outDepositoryDispaly.setExpressAmount(new Double(0));
			}

			Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
			if (expressInfoTemp != null) {
				// ����Ĭ�ϵ�������Ϣ
				outDepositoryDispaly.setExpressName(expressInfoTemp.getExpressName());
				outDepositoryDispaly.setExpressUrl(expressInfoTemp.getExpressUrl());
			}
		}

		// ���ó���ʱ��
		if (StringUtil.isBlank(gmtOutDep) && outDepositoryDispaly.getGmtOutDep() == null) {
			model.addAttribute("currDate", new Date());
		}
		// ȡ�ó��ⵥ������Ϣ
		List<OutDetailGoods> outDetailGoodsLists = outDetailManager
				.getOutDetailGoodsLists(outDepositoryDispaly.getId());
		model.addAttribute("outDetailGoodsLists", outDetailGoodsLists);
		if (outDetailGoodsLists != null && outDetailGoodsLists.size() > 0) {
			String codeStr = "";
			for (OutDetailGoods outDetailGoodsInfo : outDetailGoodsLists) {
				outDetailGoodsInfo.setCatName(categoryManager.getCatFullNameByCatcode(outDetailGoodsInfo.getCatCode()));
				outDetailGoodsInfo.setAttributeName(attributeManager.getFullAttributeStringByAttrs(outDetailGoodsInfo
						.getAttrs()));
				if (StringUtils.isNotBlank(outDetailGoodsInfo.getCode())) {
					codeStr += outDetailGoodsInfo.getCode() + ",";// add by fanyj 2010-11-17
				}
			}
			if (StringUtils.isBlank(goodsCodeStr)) {// add by fanyj 2010-11-17
				goodsCodeStr = codeStr;
			}
			model.addAttribute("goodsCodeStr", goodsCodeStr);
		}

		// �����ɵĲ���
		if (StringUtil.isNotBlank(optType) && optType.equals("finishOpt")) {
			// �����ɵ����
			List<OutDetailGoods> outDetailNotFinishList = outDetailManager.listOutDetailNotFinish(id,
					EnumOutDetailStatus.OUT_FINISHED.getKey());
			// ����ʱ��Ĭ���õ�����ʱ�䣬�����ж��� zhangwy 2010/11/23
			// if (StringUtil.isBlank(gmtOutDep)
			// || DateUtil.strToDate(gmtOutDep, "yyyy-MM-dd").getTime() > new Date().getTime()) {
			// //����ʱ�䲻��ȷ
			// succFlag = Boolean.FALSE;
			// errorInfo = "����ʱ�䲻��ȷ";
			// } else
			if (outDetailNotFinishList != null && outDetailNotFinishList.size() > 0) {
				// �в�Ʒδ�����λ
				String notFinishInstanceName = "";
				for (OutDetailGoods outDetailNotFinishInfo : outDetailNotFinishList) {
					if (StringUtil.isBlank(notFinishInstanceName)) {
						notFinishInstanceName = outDetailNotFinishInfo.getInstanceName();
					} else {
						notFinishInstanceName = notFinishInstanceName + "," + outDetailNotFinishInfo.getInstanceName();
					}
				}
				model.addAttribute("succFlag", false);
				model.addAttribute("errorInfo", "�в�Ʒ��" + notFinishInstanceName + "  δ�����λ");
			}
			// modified by fanyj 2010-11-16 start ���۶��������ۻ��������������ű�����д�ļ���
			else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())
					&& EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())
					&& StringUtil.isBlank(outDepositoryDispaly.getExpressCode())) {
				model.addAttribute("succFlag", false);
				model.addAttribute("errorInfo", "�������ű�����д��");
			}
			// modified by fanyj 2010-11-16 end
			else if (EnumOutStatus.OUT_FINISHED.getKey().equals(outDepositoryDispaly.getStatus())) {
				// ���ⵥ״̬Ϊ�����
				model.addAttribute("succFlag", false);
				model.addAttribute("errorInfo", "�˳��ⵥ״̬Ϊ�����");
			} else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())
					&& inDepositoryManager.getUnFinishedInDepByRelNum(outDepositoryDispaly.getRelationNum()) > 0) {
				// ���ۻ������ʱ����ⵥδ��ɡ�
				model.addAttribute("succFlag", false);
				model.addAttribute("errorInfo", "�˻�����Ʒ��δ��⣬���������");
			 } else if (outDepositoryDispaly.getHandleAdminId() != null &&
	                    !outDepositoryDispaly.getHandleAdminId().equals(agent.getId())) {
			        model.addAttribute("succFlag", Boolean.FALSE);
	                model.addAttribute("errorInfo", "�˳��ⵥ�ѱ����˽ӹܴ����������������");
			} else {
				// if (DateUtil.strToDate(gmtOutDep, "yyyy-MM-dd HH:mm:ss") == null) {
				// SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				// String hms = df.format(new Date());
				// StringBuffer temp = new StringBuffer();
				// gmtOutDep = temp.append(gmtOutDep).append(" ").append(hms).toString();
				// }

				Map param = new HashMap();
				param.put("outDepId", id);
				// param.put("gmtOutDep", DateUtil.strToDate(gmtOutDep, "yyyy-MM-dd HH:mm:ss"));
				param.put("outDetailGoodsLists", outDetailGoodsLists);
				param.put("outDepType", outDepositoryDispaly.getType());
				param.put("relationNum", outDepositoryDispaly.getRelationNum());
				// ���ù������ż���
				if (EnumInOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
					outDepositoryDispaly.setRelationNumList(tradePackageManager.getTidByMergeTid(outDepositoryDispaly
							.getRelationNum()));
					param.put("relationNumList", outDepositoryDispaly.getRelationNumList());
				}
				param.put("modifier", agent.getUsername());
				param.put("isWholesale", outDepositoryDispaly.getIsWholesale());
				param.put("tid", outDepositoryDispaly.getTid());
				try {
					boolean succFlag = outDepositoryManager.removeStorageOpt(param);
					model.addAttribute("succFlag", succFlag);
				} catch (Exception e) {
					// ��λ���̵��е����
					model.addAttribute("succFlag", false);
					model.addAttribute("errorInfo", "��λ���̵��л��߿�治�����䣬������ѡ���λ���䡣");
					model.addAttribute("currDate", DateUtil.strToDate(gmtOutDep, "yyyy-MM-dd HH:mm:ss"));
				}

				/* @@@@@@ add by fanyj 2010-11-16 start ��ȡ�����������ύ�Ĺ��ܣ�������߼���ӵ���������һ���� */
				String messageFromTaobao = null;
				if (tradeInfoTemp != null) {
					User user = userManager.getUser(tradeInfoTemp.getBuyId());
					if (user != null) {
						Map<String, Object> emailMap = new HashMap<String, Object>();
						if (outDetailGoodsLists != null && outDetailGoodsLists.size() > 0) {
							Double partSum = new Double(0);
							int countOutNum = 0;
							double sumMoney = 0;
							for (OutDetailGoods outDetailGoods : outDetailGoodsLists) {
								partSum = outDetailGoods.getUnitPrice() * outDetailGoods.getOutNum();
								outDetailGoods.setCatCode(partSum.toString());
								sumMoney += partSum;
								countOutNum += outDetailGoods.getOutNum();
							}
							emailMap.put("expressAmount", outDepositoryDispaly.getExpressAmount());// ��������
							// ��������۶�������������ų��»�ȡ
							if (EnumInOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
								emailMap.put("relationNum", outDepositoryDispaly.getRelationNumStr());// ��������
							} else {
								emailMap.put("relationNum", outDepositoryDispaly.getRelationNum());// ��������
							}
							emailMap.put("outDetailList", outDetailGoodsLists);// �������Ʒ��Ϣ�б�
							emailMap.put("countOutNum", countOutNum);// �ϼ�����
							emailMap.put("sumMoney", sumMoney);// �ϼƽ��
						}
						// ��д���������������

						emailMap.put("userName", user.getAccount());
						emailMap.put("expressName", outDepositoryDispaly.getExpressName());
						emailMap.put("expressCode", outDepositoryDispaly.getExpressCode());
						emailMap.put("expressUrl", outDepositoryDispaly.getExpressUrl());
						emailMap.put("ctx", RequestUtil.getUrlContext(req));
						String email = user.getEmail();

						// ����վ����
						if (StringUtil.isNotEmpty(user.getType())
								&& ("d".equals(user.getType()) || "w".equals(user.getType()))) {
							saveWebSite(outDepositoryDispaly, user, req, agent);
						}

						// ���ʼ�֪ͨ�ͻ��ѳ���
						sendEmailForCustom(emailMap, email);
						// outDepositoryManager.updateExpressCodeById(StringUtil.trim(expressCode), id);
						// //TODO

						// �����ͬ�����������޸�Զ�̶���״̬ (add by fanyj 2010-10-26 start)
						String reNumStr = getRelationNumString(outDepositoryDispaly.getRelationNum(),
								outDepositoryDispaly.getType(), false);
						String[] reNumArray = reNumStr.split(",");
						if (reNumArray != null && reNumArray.length > 0) {
							for (int i = 0; i < reNumArray.length; i++) {
								Trade trade = tradeManager.getTradeByTid(reNumArray[i]);
								//���Ķ��������
								if (trade != null && trade.getTradeType() == 2) {
									String resultStr = updateInterfaceTrade(trade,
											outDepositoryDispaly.getExpressName(),
											outDepositoryDispaly.getExpressName(),
											outDepositoryDispaly.getExpressCode());
									if (StringUtils.isNotBlank(resultStr)) {
										model.addAttribute("errorInfo", "Զ�̵��ýӿ��޸����Ķ���״̬���ִ���---" + resultStr);
									} else {
										model.addAttribute("succFlag", true);
									}
								}
								// (add by fanyj 2010-10-26 end)
								//�Ա����������
								// �����ͬ�����������޸�Զ�̶���״̬
								if (trade != null && trade.getTradeType() == 3) {
									// ��ѯ������˾����
									ExpressQuery query = new ExpressQuery();
									query.setStatus(EnumExpressStatus.ENABLED.getKey());
									query.setExpressName(outDepositoryDispaly.getExpressName());
									List<Express> expressList = (List<Express>) expressManager.listExpressByCond(query,
											1, pageSize).getItems();
									DepositoryFirst depositoryFirst = depositoryFirstManager
											.getDepositoryById(outDepositoryDispaly.getDepFirstId());
									for (Express express : expressList) {
										messageFromTaobao = updateInterfaceTradeForTaobao(trade,
												outDepositoryDispaly.getRemark()/* ��ע */,
												express.getExpressCode()/* ������˾���� */,
												express.getExpressName()/* ������˾���� */,
												outDepositoryDispaly.getExpressCode()/* �������� */, depositoryFirst/* �����ֿ� */);
										break;
									}
								}
							}
						}

						if (messageFromTaobao != null) {
							model.addAttribute("errorInfo", "ͬ���Ա�����������Ϣʱ���ִ���---" + messageFromTaobao);
						} else {
							model.addAttribute("succFlag", true);
						}
					} else {
						model.addAttribute("errorInfo", "�����ʼ�����ʧ��(�޷��ҵ��ռ���Email)");
						model.addAttribute("succFlag", false);
					}
				}
				// add by fanyj 2010-11-16 end
			}
			// currDate = DateUtil.strToDate(gmtOutDep, "yyyy-MM-dd HH:mm:ss");
			model.addAttribute("currDate", new Date());
		}
		// �ύ��ע��Ϣ
		else if (StringUtil.isNotBlank(optType) && optType.equals("remark")) {
			outDepositoryDispaly.setRemark(req.getParameter("remark"));
			outDepositoryManager.editOutDepository(outDepositoryDispaly);
			model.addAttribute("succFlag", true);
			model.addAttribute("currDate", new Date());
		}
		// ��ӡ���ⵥ����
		else if (StringUtil.isNotBlank(optType) && optType.equals("printExpress")) {
			DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(outDepositoryDispaly
					.getDepFirstId());
			String expressTmp = req.getParameter("expressTmp");
			Print(outDepositoryDispaly, tradeInfoTemp, depositoryFirst, model,expressTmp);
			if (StringUtil.isNotBlank(expressTmp)) {
				if (expressTmp.equalsIgnoreCase("EMS") || expressTmp.equalsIgnoreCase("YWEMS")) { // ʹ��EMS�ĸ�ʽ
					// ʹ��EMS�ĸ�ʽ
					return "/storage/printSinEms";
				} else if (expressTmp.equalsIgnoreCase("SFO")) {
					// ʹ��˳��ĸ�ʽ(Ĭ�����)
					return "/storage/printSinShunFeng";
				} else if (expressTmp.equalsIgnoreCase("ZTO")) {
					// ʹ����ͨ�ĸ�ʽ
					return "/storage/printSinZhongTong";
				} else if (expressTmp.equalsIgnoreCase("YTO")) {
					// ʹ��Բͨ�ĸ�ʽ
					return "/storage/printSinYuanTong";
				} else if (expressTmp.equalsIgnoreCase("STO") || expressTmp.equalsIgnoreCase("YWSTO")) {
					// ʹ����ͨ�ĸ�ʽ
					return "/storage/printSinShenTong";
				} else if (expressTmp.equalsIgnoreCase("DBO")) {
					// ʹ�õ°�ĸ�ʽ
					return "/storage/printSinDeBang";
				} else if (expressTmp.equalsIgnoreCase("YWYD")) {
					// ʹ���ϴ�ĸ�ʽ
					return "/storage/printSinYunDa";
				} else if (expressTmp.equalsIgnoreCase("SUER")) {
					// ʹ���ٶ��ĸ�ʽ
					return "/storage/printSinSuEr";
				} else if (expressTmp.equalsIgnoreCase("UC")) {
					// ʹ�����ٵĸ�ʽ
					return "/storage/printSinYouSu";
				}
			}
		}

		// ����ȡ����ⵥ������Ϣ������ԭҳ����ʾ
		outDepositoryDispaly = outDepositoryManager.getOutDepository(id);
		model.addAttribute("outDepositoryDispaly", outDepositoryDispaly);

		if (outDepositoryDispaly.getDepFirstId() != null) {
			DepositoryFirst df = depositoryFirstManager
					.getDepositoryById(new Long(outDepositoryDispaly.getDepFirstId()));
			if (df != null) {
				outDepositoryDispaly.setDepFirstName(df.getDepFirstName());
			}
		}

		if (tradeInfoTemp != null) {
			// ����������ʽ
			outDepositoryDispaly.setPayment(tradeInfoTemp.getExpressPayment());
			if (!EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
				if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
					outDepositoryDispaly.setExpressAmount(getShippingAmount(outDepositoryDispaly.getRelationNum()));
				} else {
					outDepositoryDispaly.setExpressAmount(tradeInfoTemp.getShippingAmount());
				}
			} else {
				outDepositoryDispaly.setExpressAmount(new Double(0));
			}
			Express expressInfoTemp = expressManager.getExpress(tradeInfoTemp.getExpressId());
			if (expressInfoTemp != null) {
				// ����Ĭ�ϵ�������Ϣ
				outDepositoryDispaly.setExpressName(expressInfoTemp.getExpressName());
				outDepositoryDispaly.setExpressUrl(expressInfoTemp.getExpressUrl());
			}
		}

		model.addAttribute("outDepositoryTypeMap", EnumOutDepository.toMap());
		model.addAttribute("outDepositoryStatusMap", EnumOutStatus.toMap());
		model.addAttribute("expressDistPaymentMap", EnumExpressDistPayment.toMap());

		return "/storage/showOutDetail";
	}

	/**
	 * ͨ���ӿ��޸Ķ�����Ϣ
	 *
	 * @param trade
	 * @param logisticsName
	 *            ��������
	 * @param logisticsDesc
	 *            ��������
	 * @param logisticsCode
	 *            ��������
	 * @return
	 */
	private String updateInterfaceTrade(Trade trade, String logisticsName, String logisticsDesc, String logisticsCode) {
		InterfaceApply interfaceApply = interfaceApplyManager.getInterfaceApplyByUserId(trade.getBuyId(),
				EnumInterfaceType.PAIPAI.getKey());
		InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager.getInterfaceUserTradeByTid(trade.getTid());
		String result = null;
		if (interfaceApply != null) {
			TreeMap<String, String> signParams = new TreeMap<String, String>();
			signParams.put("uin", interfaceApply.getParamOne());
			signParams.put("token", interfaceApply.getParamTwo());
			signParams.put("spid", interfaceApply.getParamThree());
			signParams.put("seckey", interfaceApply.getParamFour());
			signParams.put("sellerUin", interfaceApply.getParamOne());

			if (interfaceUserTrade != null) {
				String itemUrl = "";
				Map<String, String> resultMap = null;
				if (StringUtil.isBlank(logisticsName)) {
					logisticsName = "��������";
					logisticsDesc = "��������";
				}
				if (StringUtil.isBlank(logisticsCode)) {
					logisticsCode = "000000";
				}
				// ���ұ�Ƕ����ѷ���
				itemUrl = Billing_Deal_DetailsUtil.CreateDealConsignUrl(signParams,
						interfaceUserTrade.getPaipaiTradeId(), logisticsName, logisticsDesc, logisticsCode, 3);
				resultMap = Billing_Deal_DetailsUtil.parseInterfaceDealStatusXml(itemUrl);
				// ����޸ĳɹ�
				if ("0".equals(resultMap.get("errorCode"))) {
					return result;
				} else {
					log.error("�޸Ľӿڶ���[" + interfaceUserTrade.getPaipaiTradeId() + "]ʧ�ܣ�",
							new Exception(resultMap.get("errorMessage")));
					result = resultMap.get("errorMessage");
				}
			}
		}
		return result;
	}

	/**
	 * ͨ���Ա��ӿ��޸Ķ�����Ϣ
	 *
	 * @param trade
	 * @param remark
	 *            ��ע
	 * @param codeOfExpress
	 *            ������˾����
	 * @param nameOfExpress
	 *            ������˾����
	 * @param logisticsCode
	 *            ��������
	 * @param depositoryFirst
	 *            �����ֿ�
	 * @return
	 * @modified by chenyan 2011/03/11 ʹ�õ�������������Ϣ
     * @modified by chenyan 2011/04/11 ʹ�ö����е��Ա�����������Ϣ
	 */
	private String updateInterfaceTradeForTaobao(Trade trade, String remark, String codeOfExpress,
			String nameOfExpress, String logisticsCode, DepositoryFirst depositoryFirst) {
		TaobaoApply interfaceApply = taobaoInterfaceApplyManager.getInterfaceApplyByUserId(trade.getBuyId(),
				EnumInterfaceType.TAOBAO.getKey());
		InterfaceUserTrade interfaceUserTrade = interfaceUserTradeManager.getInterfaceUserTradeByTid(trade.getTid());

		if (interfaceApply != null && interfaceUserTrade != null) {

			String app_key = interfaceApply.getParamOne();
			String app_sercet = interfaceApply.getParamTwo();
			String nick = interfaceApply.getParamThree();

			Map<String, String> sellerMap = new HashMap<String, String>();
			sellerMap.put("remark", remark);// ��ע
			if (StringUtil.isBlank(trade.getInterfaceExpressCode())) {
                //δ�����Ա����������
                sellerMap.put("codeOfExpress", codeOfExpress);//������˾����
                sellerMap.put("nameOfExpress", nameOfExpress);//������˾����
            } else {
                sellerMap.put("codeOfExpress", trade.getInterfaceExpressCode());//������˾����
                sellerMap.put("nameOfExpress", "YWSTO");//������˾����,���ڲ�����taobao��SAND���Ի���������û�õġ���˴˴�ֱ��д��YWSTO,
            }
			sellerMap.put("logisticsCode", logisticsCode);// ��������
			Resources resources;
			resources = resourcesManager.getResourcesByTypeAndName("taobao", "sellerName");
			sellerMap.put("sellerName", resources.getValue());// ������ʾ����������
			resources = resourcesManager.getResourcesByTypeAndName("taobao", "depFirstZip");
			sellerMap.put("sellerZip", resources.getValue());// ������ʾ�������ʱ�
			resources = resourcesManager.getResourcesByTypeAndName("taobao", "sellerPhone");
			sellerMap.put("sellerPhone", resources.getValue());// ������ʾ��������ϵ�绰

			return TaobaoUtils.updateTaobaoLogisticsMsg(app_key, app_sercet, nick, interfaceUserTrade, depositoryFirst,
					sellerMap);
		}

		return "ȱ��ͬ���Ա��������������û�����";
	}

	/*
	 * ��ӡ������
	 *
	 * @param request
	 *
	 * @param tradeInfoTemp
	 *
	 * @throws Exception
	 */
	private void Print(OutDepository outDepository, Trade tradeInfoTemp, DepositoryFirst depositoryFirst, Model model, String expressTmp)
			throws Exception {
		Map<String, Object> printMap = new HashMap<String, Object>();
		// ��ӡ�����������
		String regionInfo = "";
		if (StringUtil.isNotBlank(tradeInfoTemp.getProvince())) {
			Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
			if (regionProvince != null) {
				regionInfo = regionProvince.getRegionName();
			}
		}
		if (StringUtil.isNotBlank(tradeInfoTemp.getCity())) {
			Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
			if (regionCity != null) {
				regionInfo = regionInfo + regionCity.getRegionName();
			}
		}
		if (StringUtil.isNotBlank(tradeInfoTemp.getDistrict())) {
			Region regionDistrict = regionManager.getRegionByCode(tradeInfoTemp.getDistrict());
			if (regionDistrict != null) {
				regionInfo = regionInfo + regionDistrict.getRegionName();
			}
		}
		//������ͨ���ֵ��ж� zhangwy 2011/05/05
		if(expressTmp.equalsIgnoreCase("STO") || expressTmp.equalsIgnoreCase("YWSTO")){
			if(StringUtil.isNotBlank(tradeInfoTemp.getDistrict())){
				Map parMap = new HashMap();
				parMap.put("district", tradeInfoTemp.getDistrict());
				//3����Ϣȡ��
				Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
				Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
				Region regionDistrict = regionManager.getRegionByCode(tradeInfoTemp.getDistrict());
				//�Ƿ�����5��ʡ
				EnumRegionCode enumRegion = EnumRegionCode.findRegion(tradeInfoTemp.getProvince());
				//�Ƿ�ֱϽ��
				EnumDirectRegionCode enumDirectRegion = EnumDirectRegionCode.findRegion(tradeInfoTemp.getProvince());
				//��ȫ��ַ�ж�
				String allAddress = regionInfo + tradeInfoTemp.getAddress();
				if(enumRegion != null){
					//��ȡ��������
					//1.�������򺣻��صģ���ͷ��д'��'��'����' �򺣣�330211  ���أ�330206
					if(tradeInfoTemp.getDistrict().equals("330211") || tradeInfoTemp.getDistrict().equals("330206")){
						printMap.put("receiveCityForShentong", regionDistrict.getRegionName());
					}else if(allAddress.contains("��ɽ����")){
					//2.����ɽ�����ģ���ͷ��һ��Ҫд��'���ݱ���'
						printMap.put("receiveCityForShentong", "���ݱ���");
					}else if(tradeInfoTemp.getCity().equals("331000")){
					//3.���㽭̨�ݵģ�������Ϊ��λ����ͷ��һ��Ҫд��'̨��**��' ̨�ݣ�331000
						List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
						if(specialRegion != null && specialRegion.size() == 1){
							printMap.put("receiveCityForShentong", specialRegion.get(0).getRegionName());
						}else{
							printMap.put("receiveCityForShentong", regionCity.getRegionName() + regionDistrict.getRegionName());
						}
					}else{
						//���ա��㽭�����ա�ɽ�� �������л�ȡ
						parMap.put("specialCodes", enumRegion.toList());
						List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
						if(specialRegion != null && specialRegion.size() == 1){
							printMap.put("receiveCityForShentong", specialRegion.get(0).getRegionName());
						}else{
							printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
						}
					}
				}else if(enumDirectRegion != null){
					//�������Ϻ�����������Ĵ�ֱϽ�У�дֱϽ��������
					printMap.put("receiveCityForShentong", regionProvince.getRegionName());
				}else{
					List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
					if(specialRegion != null && specialRegion.size() == 1){
						printMap.put("receiveCityForShentong", regionProvince.getRegionName() + specialRegion.get(0).getRegionName());
					}else{
						printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
					}
				}
			}else{
				//û�е��������ֱ��дʡ������
				Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
				if (StringUtil.isNotBlank(tradeInfoTemp.getCity())) {
					Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
					printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
				}else{
					printMap.put("receiveCityForShentong", regionProvince.getRegionName());
				}
			}
		}
		
		// outDepositoryDispaly.getBillNum(), "����������Ϣ�������޹�˾", "�㽭ʡ�������ϳ���", "����·887����������1705��",
		// "400 826 8200", tradeInfoTemp.getReceiver(), regionInfo, tradeInfoTemp.getAddress(),
		// tradeInfoTemp.getMobile()

		if (depositoryFirst != null) {
			printMap.put("companyAddress1", depositoryFirst.getAddress());
			printMap.put("companyAddress2", depositoryFirst.getParticularAddress());
		}
		printMap.put("id", String.valueOf(outDepository.getId()));
		printMap.put("billNum", outDepository.getBillNum());
		printMap.put("company", "�������ӹɷ����޹�˾");
		printMap.put("phone", "400 826 8200");
		printMap.put("receiver", tradeInfoTemp.getReceiver());
		printMap.put("receiveAddress1", regionInfo);
		printMap.put("receiveAddress2", tradeInfoTemp.getAddress());
		printMap.put("mobile", tradeInfoTemp.getMobile());
		printMap.put("zipCode", tradeInfoTemp.getZipcode());

		model.addAttribute("printMap", printMap);
	}

	/**
	 * @Title: Print
	 * @Description: ��ӡ����������
	 * @param @param request
	 * @param @param tradeInfoTemp
	 * @param @param depositoryFirst
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	@RequestMapping("printMul")
	public String PrintMul(@RequestParam("ids") String idStr, @RequestParam("expressTmp") String expressTmp, Model model) {
		model.addAttribute("idStr", idStr);

		List<Map<String, String>> printList = new ArrayList<Map<String, String>>();// ���������

		// ȡ��URL�е�ID����������ȡ������.
		String[] ids = idStr.split(",");
		for (String id : ids) {
			if (id == null || id.length() == 0) {
				continue;
			}

			Map<String, String> printMap = new HashMap<String, String>();// ���������

			OutDepository outDepositoryDispaly = outDepositoryManager.getOutDepository(Long.valueOf(id));

			Trade tradeInfoTemp = null;
			if (EnumOutDepository.OUT_SALES.getKey().equals(outDepositoryDispaly.getType())) {
				// ���۶���ʱ����Ҫ��д��������
				tradeInfoTemp = tradeManager.getTradeByTid(getRelationNumString(outDepositoryDispaly.getRelationNum(),
						outDepositoryDispaly.getType(), true));
			} else if (EnumOutDepository.OUT_SALES_CHANGE.getKey().equals(outDepositoryDispaly.getType())) {
				// ���ۻ���ʱ����Ҫ��д��������
				tradeInfoTemp = tradeManager.getTradeByRefundId(outDepositoryDispaly.getRelationNum());
			}

			String regionInfo = "";
			if (tradeInfoTemp != null) {
				// ��ӡ�����������
				if (StringUtil.isNotBlank(tradeInfoTemp.getProvince())) {
					Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
					if (regionProvince != null) {
						regionInfo = regionProvince.getRegionName();
					}
				}
				if (StringUtil.isNotBlank(tradeInfoTemp.getCity())) {
					Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
					if (regionCity != null) {
						regionInfo = regionInfo + regionCity.getRegionName();
					}
				}
				if (StringUtil.isNotBlank(tradeInfoTemp.getDistrict())) {
					Region regionDistrict = regionManager.getRegionByCode(tradeInfoTemp.getDistrict());
					if (regionDistrict != null) {
						regionInfo = regionInfo + regionDistrict.getRegionName();
					}
				}
				
				//������ͨ���ֵ��ж� zhangwy 2011/05/05
				if(expressTmp.equalsIgnoreCase("STO") || expressTmp.equalsIgnoreCase("YWSTO")){
					if(StringUtil.isNotBlank(tradeInfoTemp.getDistrict())){
						Map parMap = new HashMap();
						parMap.put("district", tradeInfoTemp.getDistrict());
						//3����Ϣȡ��
						Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
						Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
						Region regionDistrict = regionManager.getRegionByCode(tradeInfoTemp.getDistrict());
						//�Ƿ�����5��ʡ
						EnumRegionCode enumRegion = EnumRegionCode.findRegion(tradeInfoTemp.getProvince());
						//�Ƿ�ֱϽ��
						EnumDirectRegionCode enumDirectRegion = EnumDirectRegionCode.findRegion(tradeInfoTemp.getProvince());
						//��ȫ��ַ�ж�
						String allAddress = regionInfo + tradeInfoTemp.getAddress();
						if(enumRegion != null){
							//��ȡ��������
							//1.�������򺣻��صģ���ͷ��д'��'��'����' �򺣣�330211  ���أ�330206
							if(tradeInfoTemp.getDistrict().equals("330211") || tradeInfoTemp.getDistrict().equals("330206")){
								printMap.put("receiveCityForShentong", regionDistrict.getRegionName());
							}else if(allAddress.contains("��ɽ����")){
							//2.����ɽ�����ģ���ͷ��һ��Ҫд��'���ݱ���'
								printMap.put("receiveCityForShentong", "���ݱ���");
							}else if(tradeInfoTemp.getCity().equals("331000")){
							//3.���㽭̨�ݵģ�������Ϊ��λ����ͷ��һ��Ҫд��'̨��**��' ̨�ݣ�331000
								List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
								if(specialRegion != null && specialRegion.size() == 1){
									printMap.put("receiveCityForShentong", specialRegion.get(0).getRegionName());
								}else{
									printMap.put("receiveCityForShentong", regionCity.getRegionName() + regionDistrict.getRegionName());
								}
							}else{
								//���ա��㽭�����ա�ɽ�� �������л�ȡ
								parMap.put("specialCodes", enumRegion.toList());
								List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
								if(specialRegion != null && specialRegion.size() == 1){
									printMap.put("receiveCityForShentong", specialRegion.get(0).getRegionName());
								}else{
									printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
								}
							}
						}else if(enumDirectRegion != null){
							//�������Ϻ�����������Ĵ�ֱϽ�У�дֱϽ��������
							printMap.put("receiveCityForShentong", regionProvince.getRegionName());
						}else{
							List<Region> specialRegion = regionManager.getSpecialRegion(parMap);
							if(specialRegion != null && specialRegion.size() == 1){
								printMap.put("receiveCityForShentong", regionProvince.getRegionName() + specialRegion.get(0).getRegionName());
							}else{
								printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
							}
						}
					}else{
						//û�е��������ֱ��дʡ������
						Region regionProvince = regionManager.getRegionByCode(tradeInfoTemp.getProvince());
						if (StringUtil.isNotBlank(tradeInfoTemp.getCity())) {
							Region regionCity = regionManager.getRegionByCode(tradeInfoTemp.getCity());
							printMap.put("receiveCityForShentong", regionProvince.getRegionName() + regionCity.getRegionName());
						}else{
							printMap.put("receiveCityForShentong", regionProvince.getRegionName());
						}
					}
				}
			}

			// outDepositoryDispaly.getBillNum(), "����������Ϣ�������޹�˾", "�㽭ʡ�������ϳ���", "����·887����������1705��",
			// "400 826 8200", tradeInfoTemp.getReceiver(), regionInfo, tradeInfoTemp.getAddress(),
			// tradeInfoTemp.getMobile()

			DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(outDepositoryDispaly
					.getDepFirstId());
			if (depositoryFirst != null) {
				printMap.put("companyAddress1", depositoryFirst.getAddress());
				printMap.put("companyAddress2", depositoryFirst.getParticularAddress());
			}
			printMap.put("id", String.valueOf(outDepositoryDispaly.getId()));
			printMap.put("billNum", outDepositoryDispaly.getBillNum());
			printMap.put("company", "����������Ϣ�������޹�˾");
			printMap.put("phone", "400 826 8200");
			printMap.put("receiver", tradeInfoTemp != null ? tradeInfoTemp.getReceiver() : "");
			printMap.put("receiveAddress1", regionInfo);
			printMap.put("receiveAddress2", tradeInfoTemp != null ? tradeInfoTemp.getAddress() : "");
			printMap.put("mobile", tradeInfoTemp != null ? tradeInfoTemp.getMobile() : "");
			printMap.put("zipCode", tradeInfoTemp != null ? tradeInfoTemp.getZipcode() : "");

			printList.add(printMap);
		}

		model.addAttribute("printList", printList);

		if (StringUtil.isNotBlank(expressTmp)) {
			if (expressTmp.equalsIgnoreCase("EMS") || expressTmp.equalsIgnoreCase("YWEMS")) { // ʹ��EMS�ĸ�ʽ
				// ʹ��EMS�ĸ�ʽ
				return "/storage/printMulEms";
			} else if (expressTmp.equalsIgnoreCase("SFO")) {
				// ʹ��˳��ĸ�ʽ(Ĭ�����)
				return "/storage/printMulShunFeng";
			} else if (expressTmp.equalsIgnoreCase("ZTO")) {
				// ʹ����ͨ�ĸ�ʽ
				return "/storage/printMulZhongTong";
			} else if (expressTmp.equalsIgnoreCase("YTO")) {
				// ʹ��Բͨ�ĸ�ʽ
				return "/storage/printMulYuanTong";
			} else if (expressTmp.equalsIgnoreCase("STO") || expressTmp.equalsIgnoreCase("YWSTO")) { // ʹ����ͨ�ĸ�ʽ
				return "/storage/printMulShenTong";
			} else if (expressTmp.equalsIgnoreCase("DBO")) {
				// ʹ�õ°�ĸ�ʽ
				return "/storage/printMulDeBang";
			} else if (expressTmp.equalsIgnoreCase("YWYD")) {
				// ʹ���ϴ�ĸ�ʽ
				return "/storage/printMulYunDa";
			} else if (expressTmp.equalsIgnoreCase("SUER")) {
				// ʹ���ٶ��ĸ�ʽ
				return "/storage/printMulSuEr";
			} else if (expressTmp.equalsIgnoreCase("UC")) {
				// ʹ�����ٵĸ�ʽ
				return "/storage/printMulYouSu";
			}
		}
		return "ERROR";
	}

	/**
	 * ������������
	 *
	 * @return
	 * @author chenyan 2009/08/18
	 */
	private void sendEmailForCustom(Map<String, Object> map, String email) {
		// ����֪ͨ�ʼ�
		try {
			mailEngine.sendMessage(email, "������������Ʒ�Ѿ�����", "goods_send_finish.vm", map);
		} catch (MessagingException e) {
			log.error("�������������ʼ�����ʧ��:", e);
		}

	}

	/**
	 * ������Ա���ӷ�վ���Ź���
	 *
	 * @return String
	 * @author fangqing 2009/12/10
	 */
	private void saveWebSite(OutDepository outDepository, User user, ServletRequest req, AdminAgent agent) {
		WebSiteEmail webSiteEmailRalation = new WebSiteEmail();
		StringBuffer sb = new StringBuffer();
		String relationNum = getRelationNumString(outDepository.getRelationNum(), outDepository.getType(), false);
		webSiteEmailRalation.setTitle("������������Ʒ�Ѿ�����");
		sb.append("�װ���").append(user.getAccount()).append("���ã���л��ʹ��1858����������������Ʒ������").append(" ")
				.append(relationNum).append(" ").append("�Ѿ�����.");
		webSiteEmailRalation.setContent(sb.toString());
		webSiteEmailRalation.setFromUser(agent.getUsername());
		webSiteEmailRalation.setStatus("new");
		webSiteEmailRalation.setType("system");
		webSiteEmailRalation.setToUser(user.getAccount());
		webSiteEmailManager.insertWebSiteEmail(webSiteEmailRalation);
	}

	/**
	 * ��Ʒ����������
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/28
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("outDepositoryOpt")
	public String outDepositoryOpt(@RequestParam("optId") Long id, @RequestParam("optType") String type,
			@RequestParam(value = "optTypeFinish", required = false) String optTypeFinish, HttpServletRequest req,
			Model model, AdminAgent agent) throws Exception {
		// ����ҳ�����
		model.addAttribute("optId", id);
		model.addAttribute("optType", type);

		// ȡ�ó���������Ϣ
		OutDepository outDeposioryTemp = outDepositoryManager.getOutDepositoryByDetailId(id);

		String outDepStatus = outDeposioryTemp.getStatus();
		model.addAttribute("outDepStatus", outDepStatus);

		if (StringUtil.isBlank(outDepStatus) || outDepStatus.equals(EnumOutStatus.OUT_FINISHED.getKey())) {
			List<OutDepositoryStorage> outDetailInfoForDisList = storageManager.listOutDetailForDisByDetailId(id);
			model.addAttribute("outDetailInfoForDisList", outDetailInfoForDisList);
		}
		// ȡ�û�����Ϣ
		OutDetailBaseInfo outDetailBaseInfo = outDetailManager.getOutDetailBaseInfo(id, type);
		model.addAttribute("outDetailBaseInfo", outDetailBaseInfo);

		if (StringUtil.isNotBlank(outDetailBaseInfo.getDepFirstId())) {
			DepositoryFirst df = depositoryFirstManager.getDepositoryById(Long.valueOf(outDetailBaseInfo
					.getDepFirstId()));
			if (df != null) {
				outDetailBaseInfo.setDepFirstName(df.getDepFirstName());
			}
		}

		String[] storageIdArray = req.getParameterValues("storageId");
		String[] disCountArray = req.getParameterValues("disCount");
		String[] locIdArray = req.getParameterValues("locId");
		// �ж��Ƿ�����ɲ���
		if (StringUtil.isNotBlank(optTypeFinish) && optTypeFinish.equals("y")) {
			String allowedInfo = outDepositoryAllowed(outDetailBaseInfo, storageIdArray, disCountArray, locIdArray);
			if (StringUtil.isBlank(allowedInfo)) {
				// ��Ʒ����
				Map map = new HashMap();
				map.put("outDetailBaseInfo", outDetailBaseInfo);
				map.put("storageIdArray", storageIdArray);
				map.put("disCountArray", disCountArray);
				map.put("locIdArray", locIdArray);
				map.put("isWholesale", outDeposioryTemp.getIsWholesale());
				map.put("tid", outDeposioryTemp.getTid());
				Boolean optFlag = outDetailManager.outDepositoryOpt(map, type);
				if (optFlag) {
					model.addAttribute("succFlag", true);
				} else {
					model.addAttribute("succFlag", false);
					model.addAttribute("errorInfo", "�����Ʒ�������ʧ��");
				}
			} else {
				model.addAttribute("succFlag", false);
				model.addAttribute("errorInfo", allowedInfo);
			}
		}

		// �ж��Ƿ��ǲɹ��˻�
		String[] batchNums = null;
		if (type.equals(EnumOutDepository.OUT_SHOPPING.getKey())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("relationNum", outDetailBaseInfo.getRelationNum());
			InDepository inDep = inDepositoryManager.getInDepository(param);
			if (inDep != null) {
				batchNums = new String[] { inDep.getBatchNum() };
			}
		}
		// ȡ�ÿ������
		if (StringUtil.isBlank(outDetailBaseInfo.getDepFirstId())) {
			return "/storage/outDepositoryOpt";
		}

		Map mapSearch = new HashMap();
		mapSearch.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
		mapSearch.put("supplierId", outDetailBaseInfo.getSupplierId());
		mapSearch.put("batchNums", batchNums);
		mapSearch.put("depFirstId", new Long(outDetailBaseInfo.getDepFirstId()));
		// ����˻����ɹ��˻���ʱ���ж��Ƿ�����
		if (!(EnumOutDepository.OUT_SHOPPING.getKey().equals(type) || EnumOutDepository.OUT_STORAGE_REFUND.getKey()
				.equals(type))) {
			mapSearch.put("isWholesale", outDeposioryTemp.getIsWholesale());
		} else {
			mapSearch.put("isWholesale", null);
		}
		mapSearch.put("tid", outDeposioryTemp.getTid());
		// ������������,��ֻ�ܳ���Ӧtid�������Ʒ
		List<OutDepositoryStorage> outStorageList = outDetailManager.getOutStorageList(mapSearch);
		model.addAttribute("outStorageList", outStorageList);

		int i = 0;
		for (OutDepositoryStorage outDepositoryStorageInfo : outStorageList) {
			if (disCountArray != null && disCountArray.length == outStorageList.size() && locIdArray != null
					&& locIdArray.length == outStorageList.size()) {
				outDepositoryStorageInfo.setOriCount(disCountArray[i]);
			} else {
				Map getAmountMap = new HashMap();
				getAmountMap.put("outDepId", outDetailBaseInfo.getOutDepositoryId());
				getAmountMap.put("goodsInstanceId", outDetailBaseInfo.getGoodsInstanceId());
				getAmountMap.put("goodsId", outDetailBaseInfo.getGoodsId());
				getAmountMap.put("outDetailId", outDetailBaseInfo.getOutDetailId());
				getAmountMap.put("storageId", outDepositoryStorageInfo.getId());
				Long oriCount = prodRelationOutManager.getDistributedAmount(getAmountMap);
				outDepositoryStorageInfo.setOriCount(oriCount == null ? "" : String.valueOf(oriCount));
			}
			i = i + 1;
		}

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		return "/storage/outDepositoryOpt";
	}

	/**
	 * �ж��Ƿ���Գ���
	 *
	 * @param outDetailBaseInfoForOut
	 *            OutDetailBaseInfo
	 * @param storageIdArray
	 *            String[]
	 * @param disCountArray
	 *            String[]
	 * @param locIdArray
	 *            String[]
	 * @return String
	 * @author chenyan 2009/07/29
	 */
	private String outDepositoryAllowed(OutDetailBaseInfo outDetailBaseInfoForOut, String[] storageIdArray,
			String[] disCountArray, String[] locIdArray) {
		// ���������Ϣ��ʧ��ֱ�ӷ��ش�����Ϣ
		if (outDetailBaseInfoForOut == null) {
			return "���������Ϣ��ʧ";
		}

		if (disCountArray == null || disCountArray.length <= 0 || storageIdArray == null || storageIdArray.length <= 0
				|| locIdArray == null || locIdArray.length <= 0 || disCountArray.length != storageIdArray.length
				|| disCountArray.length != locIdArray.length) {
			// ҳ����Ϣ��ʧ�������
			return "ҳ��ش���Ϣ��ʧ";
		}
		// ����ĳ�������
		Long inputCount = 0L;
		for (int i = 0; i < disCountArray.length; i++) {
			if (StringUtil.isNotBlank(disCountArray[i])) {
				if (StringUtil.isNumeric(disCountArray[i])) {
					inputCount = inputCount + new Long(disCountArray[i]);

					if (new Long(disCountArray[i]) > 0) {
						// �жϿ�λ�Ƿ����̵���
						if (depLocationManager.getIsCheckCountById(new Long(locIdArray[i]),
								EnumDepLocationIsCheck.Y.getKey()) > 0) {
							// �̵���
							return "�п�λ���̵���";
						}
						// �жϿ���Ƿ�������������
						Storage storageInfo = storageManager.getStorage(new Long(storageIdArray[i]));
						if (storageInfo == null || storageInfo.getStorageNum() < new Long(disCountArray[i])) {
							// ������������
							return "��������������ѡ��λ�Ŀ������";
						}
					}
				} else {
					// ������ַ��з��������͵����.
					return "������ַ��з���������";
				}
			}
		}
		// �ж����������
		if (outDetailBaseInfoForOut.getOutNum() == null || !outDetailBaseInfoForOut.getOutNum().equals(inputCount)) {
			// �����������������������һ�µ���������ش�����Ϣ��
			return "�����������������������һ��";
		}
		return null;
	}

	// /**
	// * ���ڷ���ʱ���ʼ������
	// * @return String
	// * @author chenyan 2009/08/31
	// * @deprecated
	// */
	// public String initDataForOnce() {
	// HttpServletRequest request = getRequest();
	// String optFlag = request.getParameter("optFlag");
	// if (StringUtil.isNotBlank(optFlag) && optFlag.equals("storage")) {
	// //��ʼ�����ݵ��������
	// List<Storage> storageListTemp = storageManager.getDataForStorageOnce();
	// //��¼�ɹ�����
	// Long iSucc = 0L;
	// String exceptionGoodsInstanceId = "";
	// if (storageListTemp != null && storageListTemp.size() > 0) {
	// for (Storage storageInfoTemp : storageListTemp) {
	// try {
	// if (storageInfoTemp.getGoodsInstanceId() != null
	// && storageManager.getStorageByGoodsInstanceId(storageInfoTemp
	// .getGoodsInstanceId()) == null) {
	// //��λ����Ϊ1
	// storageInfoTemp.setLocId(1L);
	// //����ʹ�õ�ǰ����
	// storageInfoTemp.setBatchNum(DateUtil.convertDateToBatch(new Date())
	// .toString()
	// + codeManager.buildCode(
	// CodeManager.PICI_CODE, 4, ""));
	// storageManager.addStorage(storageInfoTemp);
	// if (storageInfoTemp.getGoodsId() != null
	// && storageInfoTemp.getStorageNum() != null) {
	// goodsManager.updateGoodsGoodsNumberById(storageInfoTemp
	// .getGoodsId(), storageInfoTemp.getStorageNum());
	// }
	// iSucc++;
	// }
	// } catch (Exception e) {
	// exceptionGoodsInstanceId = exceptionGoodsInstanceId + ","
	// + storageInfoTemp.getGoodsInstanceId();
	// }
	// }
	// succInfo = "��ʼ�����ݵ����������ɡ����гɹ�����" + iSucc + "�����Ѿ����ڿ�������"
	// + (storageListTemp.size() - iSucc) + "��(δ����)��";
	// if (StringUtil.isNotBlank(exceptionGoodsInstanceId)) {
	// errorInfo = errorInfo + "��Ʒʵ��ID��" + exceptionGoodsInstanceId + "�������쳣";
	// }
	// } else {
	// succInfo = "û��������Ҫ�������";
	// }
	// }
	//
	// return SUCCESS;
	// }
	//
	// public String getEverydaySendGoods() {
	// HttpServletRequest request = getRequest();
	// String outDate = request.getParameter("outDate");
	// if (StringUtil.isBlank(outDate)) {
	// return SUCCESS;
	// }
	// request.setAttribute("showDate", DateUtil.dtSimpleChineseFormatStrMD(outDate));
	// parMap.put("outDepTimeStart", outDate + " 23:59:59");
	// parMap.put("outDepTimeEnd", outDate + " 00:00:00");
	// // ȡ�÷�����������������
	// int count = outDepositoryManager.gatherOutDepositoryListsCount(parMap);
	// // ���з�ҳ������
	// page = new Page();
	// page.setPageSize(100);
	// page.setTotalRowsAmount(count);
	// page.setCurrentPage(currentPage);
	// // ȡ����ⵥ����
	// if (count > 0) {
	// gatherOutDepositoryLists = outDepositoryManager.gatherOutDepositoryLists(parMap, page);
	// }
	// return SUCCESS;
	//
	// }
	//

	/**
	 * ���ⵥ������ܲ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gatherFinanceOutDepository")
	public String gatherFinanceOutDepository(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		// Ĭ�ϲ�ѯһ���µ�����

		if (StringUtil.isBlank(financeDepositoryQuery.getStartTime())
				&& StringUtil.isBlank(financeDepositoryQuery.getEndTime())) {
			financeDepositoryQuery.setStartTime(DateUtil.getDiffMon(new Date(), -1));
			financeDepositoryQuery.setEndTime(DateUtil.getDateToString(new Date()));

		}

		// ȡ�÷�����������������
		GatherOutDepository countList = outDetailManager.gatherFinanceOutDepositoryCount(financeDepositoryQuery);
		model.addAttribute("count", countList.getCount());
		model.addAttribute("totalSum", countList.getTotalSum());

		// ���з�ҳ������
		QueryPage query = outDetailManager.gatherFinanceOutDepositoryLists(financeDepositoryQuery, currPage, pageSize);
		model.addAttribute("query", query);
		model.addAttribute("queryObject", financeDepositoryQuery);

		return "/storage/gatherFinanceOutDepository";
	}

	@RequestMapping(value = "/exportFinanceGetherOutDep")
	public void exportFinanceGetherOutDep(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> gatherInDepList = new ArrayList<String[]>();
			res.setHeader("Content-disposition", "attachment; filename=GetherFinanceOutDepository_" + date + ".xls");
			GatherOutDepository countList = outDetailManager.gatherFinanceOutDepositoryCount(financeDepositoryQuery);
			QueryPage queryPage = outDetailManager.gatherFinanceOutDepositoryLists(financeDepositoryQuery, 1,
					Integer.MAX_VALUE);

			List<GatherOutDepository> gatherOutDepositoryLists = (List<GatherOutDepository>) queryPage.getItems();

			String[] sum = { " ", "", "����: " + countList.getCount() + "��",
					"�ܽ��:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "Ԫ" };
			gatherInDepList.add(sum);
			String[] title = { "���ݱ��", "����ʱ��", "������", "����״̬" };
			gatherInDepList.add(title);
			if (queryPage.getItems() != null) {
				Map financenStatusMap = EnumFinanceStatus.toMap();
				for (GatherOutDepository obj : gatherOutDepositoryLists) {
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtOutDep()),
							obj.getSumMoney() + "", (String) financenStatusMap.get(obj.getFinanceStatus()) };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "����ʧ�ܣ�");
			// log.error(e);
		} finally {
			close(outwt);
		}
	}

	// public String financeOutDepositoryConfirm() throws Exception {
	// HttpServletRequest request = getRequest();
	// String id = (String) parMap.get("id");// ���ⵥID
	// if (StringUtil.isBlank(id)) {
	// request.setAttribute("succFlag", "false");
	// return SUCCESS;
	// }
	// outDepositoryDispaly = outDepositoryManager.getOutDepository(id);
	// outDepositoryDispaly.setFinanceStatus("y");
	// outDepositoryManager.editOutDepository(outDepositoryDispaly);
	// request.setAttribute("succFlag", "true");
	// return SUCCESS;
	// }

	/**
	 * �ݹ���ⵥ������ܲ�ѯ zhangwy
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/estimateFinanceOutDepository")
	public String estimateFinanceOutDepository(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		// Ĭ�ϲ�ѯһ���µ�����

		if (StringUtil.isBlank(financeDepositoryQuery.getStartTime())
				&& StringUtil.isBlank(financeDepositoryQuery.getEndTime())) {
			financeDepositoryQuery.setStartTime(DateUtil.getDiffMon(new Date(), -1));
			financeDepositoryQuery.setEndTime(DateUtil.getDateToString(new Date()));

		}

		// ȡ�÷�����������������
		GatherOutDepository countList = outDetailManager.estimateFinanceOutDepositoryCount(financeDepositoryQuery);

		model.addAttribute("count", countList.getCount());
		model.addAttribute("totalSum", countList.getTotalSum());

		QueryPage query = outDetailManager
				.estimateFinanceOutDepositoryLists(financeDepositoryQuery, currPage, pageSize);

		model.addAttribute("query", query);
		model.addAttribute("queryObject", financeDepositoryQuery);

		return "/storage/estimateFinanceOutDepository";
	}

	/**
	 * �ݹ���ⵥ������ܵ���
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportEstimateFinanceOut")
	public void exportEstimateFinanceOut(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = res.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> gatherInDepList = new ArrayList<String[]>();
			res.setHeader("Content-disposition", "attachment; filename=EstimateFinanceOutDepository_" + date + ".xls");
			GatherOutDepository countList = outDetailManager.estimateFinanceOutDepositoryCount(financeDepositoryQuery);
			QueryPage queryPage = outDetailManager.estimateFinanceOutDepositoryLists(financeDepositoryQuery, 1,
					Integer.MAX_VALUE);
			List<GatherOutDepository> gatherOutDepositoryLists = (List<GatherOutDepository>) queryPage.getItems();

			String[] sum = { " ", "", "����: " + countList.getCount() + "��",
					"�ܽ��:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "Ԫ" };
			gatherInDepList.add(sum);
			String[] title = { "���ݱ��", "����ʱ��", "������", "����״̬" };
			gatherInDepList.add(title);
			if (gatherOutDepositoryLists != null) {
				Map financenStatusMap = EnumFinanceStatus.toMap();
				for (GatherOutDepository obj : gatherOutDepositoryLists) {
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtOutDep()),
							obj.getSumMoney() + "", (String) financenStatusMap.get(obj.getFinanceStatus()) };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "����ʧ�ܣ�");
			// log.error(e);
		} finally {
			close(outwt);
		}
	}

	/**
	 * �ݹ����ⵥ����ȷ��
	 *
	 * @return
	 */
	@RequestMapping(value = "/estimateConfirm")
	public @ResponseBody
	String estimateConfirm(@RequestParam("param") String outid) throws Exception {
		String message = "";
		if (StringUtil.isNotBlank(outid) && StringUtil.isNotEmpty(outid)) {
			OutDepository outDepository = outDepositoryManager.getOutDepository(Long.parseLong(outid));
			List<OutDetail> outDetailList = outDetailManager.getOutDetailListByOutDepositoryId(outDepository.getId());
			if (outDepository.getFinanceStatus().equals("v")) {
				message = "easure already";
			} else if (outDepository.getFinanceStatus().equals("y")) {
				message = "allsure already";
			} else if (outDepository.getFinanceStatus().equals("f")) {
				String status = "y";
				outDepository.setFinanceStatus(status);
				outDepositoryManager.editOutDepository(outDepository);
				message = "allsuccess";
			} else {
				String status = "y";
				// ��1���굥״̬Ϊʵ�ʻ����м�״̬�������ݹ�����Ϊ0ʱ��ֻ���ݹ�ȷ�ϣ�����ֱ��ȫ��ȷ��
				for (OutDetail tmp : outDetailList) {
					if (tmp.getStorType().equals("f") || tmp.getStorType().equals("h")
							|| Long.valueOf(tmp.getOutVirtualNum()).equals(0)) {
						status = "v";
						break;
					}
				}
				outDepository.setFinanceStatus(status);
				outDepositoryManager.editOutDepository(outDepository);
				if (status.equals("y")) {
					message = "allsuccess";
				} else {
					message = "easuccess";
				}
			}
		} else {
			message = "outid is wrong";
		}
		return message;
	}

	/**
	 * ʵ�ʳ��ⵥ����ȷ��
	 *
	 * @return
	 */
	@RequestMapping("/factfinanceOutConfirm")
	public @ResponseBody
	String factfinanceOutConfirm(HttpServletRequest request) {
		String outid = request.getParameter("param");
		if (StringUtil.isNotBlank(outid) && StringUtil.isNotEmpty(outid)) {
			OutDepository outDepository = outDepositoryManager.getOutDepository(Long.parseLong(outid));
			List<OutDetail> outDetailList = outDetailManager.getOutDetailListByOutDepositoryId(outDepository.getId());
			if (outDepository.getFinanceStatus().equals("f")) {
				message = "factsure already";
			} else if (outDepository.getFinanceStatus().equals("y")) {
				message = "allsure already";
			} else if (outDepository.getFinanceStatus().equals("v")) {
				String status = "y";
				outDepository.setFinanceStatus(status);
				outDepositoryManager.editOutDepository(outDepository);
				message = "allsuccess";
			} else {
				String status = "y";
				// ��1���굥״̬Ϊ�ݹ������м�״̬�������ݹ���������0ʱ��ֻ��ʵ��ȷ�ϣ�����ֱ��ȫ��ȷ��
				for (OutDetail tmp : outDetailList) {
					if (tmp.getStorType().equals("v") || tmp.getStorType().equals("h")
							|| Long.valueOf(tmp.getOutVirtualNum()) > 0) {
						status = "f";
						break;
					}
				}
				outDepository.setFinanceStatus(status);
				outDepositoryManager.editOutDepository(outDepository);
				if (status.equals("y")) {
					message = "allsuccess";
				} else {
					message = "factsuccess";
				}
			}
		} else {
			message = "outid is wrong";
		}
		return message;
	}

	/**
	 * @Title: updateIsExpressPrintedById
	 * @Description: �����Ƿ��ӡ�˵�
	 * @return String
	 * @throws
	 */
	@RequestMapping("updateIsExpressPrinted")
	@ResponseBody
	public Object updateIsExpressPrintedById(@RequestParam("ids") String idStr) {
	    String messageTmp = null;
		String[] ids = idStr.split(",");
		if(idStr != null && idStr.length() > 0)
        {
            ids = idStr.split(",");
        } else {
            messageTmp = "���޷����ô�ӡ״̬������������˶ԣ�";
        }
		try {
		    int updateCount = outDepositoryManager.updateIsExpressPrintedById(ids);
		    if (updateCount != ids.length) {
                //����������һ�µ����
                messageTmp = "���޷����ô�ӡ״̬������������˶ԣ�";
            } 
		} catch (Exception e) {
			log.error(e);
		}
		return messageTmp;
	}

	/**
	 * @Title: updateIsOutDepositoryPrintedById
	 * @Description: �����Ƿ��ӡ���ⵥ
	 * @return String
	 * @throws
	 */
	@RequestMapping("updateIsOutDepositoryPrinted")
	@ResponseBody
	public String updateIsOutDepositoryPrintedById(@RequestParam("ids") String idStr) {
	    String messageTmp = null;
	    String [] ids = null;
	    if(idStr != null && idStr.length() > 0)
        {
            ids = idStr.split(",");
        } else {
            messageTmp = "���޷����ô�ӡ״̬�ĳ��ⵥ����˶ԣ�";
        }
		try {
		    int updateCount = outDepositoryManager.updateIsOutDepositoryPrintedById(ids);
		    if (updateCount != ids.length) {
                //����������һ�µ����
		        messageTmp = "���޷����ô�ӡ״̬�ĳ��ⵥ����˶ԣ�";
            }
		} catch (Exception e) {
			log.error(e);
		}
		return messageTmp;
	}

	/**
	 * JQUERY��ʽ�޸�ʵ���˷�
	 *
	 * @author chenhang 2010/11/18
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/updateActualInventoryByJQuery")
	@ResponseBody
	public String updateActualInventoryByJQuery(@RequestParam(value = "param1", required = false) String reNum,
			@RequestParam(value = "param2", required = false) String actualInventory) {
		int result = -1;
		if (reNum.indexOf("H") == 0) {
			result = outDepositoryManager.updateActualInventoryByIdRe(Double.valueOf(actualInventory), reNum);
		} else {
			result = outDepositoryManager.updateActualInventoryById(Double.valueOf(actualInventory), reNum);
		}
		if (result > 0) {
			message = "['true','edit success!']";
		} else {
			message = "['false','edit failed!']";
		}
		return message;
	}

	/**
	 * JQUERY��ʽ�޸�ʵ������
	 *
	 * @author liuwd 2010/12/15
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/updateActualWeightByJQuery")
	@ResponseBody
	public String updateActualWeightByJQuery(@RequestParam(value = "param1", required = false) String id,
			@RequestParam(value = "param2", required = false) String actualWeight) {
		int result = -1;

		result = outDepositoryManager.updateActualWeightById(Double.valueOf(actualWeight), id);

		if (result > 0) {
			message = "['true','edit success!']";
		} else {
			message = "['false','edit failed!']";
		}
		return message;
	}

	/**
	 * JQUERY��ʽ�޸ļ�������
	 *
	 * @author liuwd 2010/12/15
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/updateCastWeightByJQuery")
	@ResponseBody
	public String updateCastWeightByJQuery(@RequestParam(value = "param1", required = false) String id,
			@RequestParam(value = "param2", required = false) String castWeight) {
		int result = -1;

		result = outDepositoryManager.updateCastWeightById(Double.valueOf(castWeight), id);

		if (result > 0) {
			message = "['true','edit success!']";
		} else {
			message = "['false','edit failed!']";
		}
		return message;
	}

	/**
	 * JQUERY��ʽ�޸���������
	 *
	 * @return ���ز�����Ϣ
	 * @throws Exception
	 */
	@RequestMapping("updateExpressCodeByJQuery")
	@ResponseBody
	public Object updateExpressCodeByJQuery(@RequestParam("param1") Long id, @RequestParam("param2") String expressCode)
			throws Exception {
		String message;
		try {
			outDepositoryManager.updateExpressCodeById(StringUtil.trim(expressCode), id);
			message = "['true','edit success!']";
		} catch (Exception ex) {
			message = "['false','edit failed!']";
			throw ex;
		}
		return message;
	}

	/**
	 * JQUERY��ʽ�˶Բ�Ʒ
	 *
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping("checkOutGoodsByJQuery")
	@ResponseBody
	public Object checkOutGoodsByJQuery(@RequestParam("param1") String goodsCodeStr,
			@RequestParam("param2") String scanGoodsCode) {
		// ȡ�ó��ⵥ������Ϣ
		if (StringUtils.isBlank(goodsCodeStr)) {
			return "['false','���ⵥ��ƷΪ�գ�']";
		}

		String[] goodsCode = goodsCodeStr.split(",");
		if (goodsCode != null && goodsCode.length > 0) {
			for (String code : goodsCode) {
				if (scanGoodsCode.equals(code)) {
					return "['true','" + code + "']";
				}
			}
		}

		return "['false','���ⵥ��û�д˲�Ʒ��Ϣ��']";
	}

	/*
	 * ��ȡ���˷�
	 *
	 * @param relationNum
	 *
	 * @return
	 */
	public double getShippingAmount(String relationNum) {
		double result = 0;
		try {
			List<String> listStr = tradePackageManager.getTidByMergeTid(relationNum);
			Trade trade = null;
			for (String obj : listStr) {
				trade = tradeManager.getTradeByTid(obj);
				if (trade != null) {
					result += trade.getShippingAmount();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * ��ȡ��Ʒ�����ַ���
	 *
	 * @author chenhang 2011-01-28
	 */
	public String getGoodsWeightString(List<TradePackage> p) {
		String result = "";
		double temp = 0.00;
		try {
			List<Double> listStr = new ArrayList<Double>();
			for (TradePackage tmp : p) {
				if (null != tmp.getGoodsWeight()) {
					listStr.add(tmp.getGoodsWeight());
				} else {
					listStr.add(0.00);
				}
			}
			if (listStr != null && listStr.size() > 0) {
				for (Double obj : listStr) {
					if (null == obj) {
						obj = 0.00 * 100;
					}
					double d = obj * 100;
					temp += d;

				}
			}
			result = String.valueOf(temp / 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * ��ȡʵ���˷��ַ���
	 *
	 * @author chenhang 2011-01-28
	 */
	public String getActualInventoryString(List<TradePackage> p) {
		String result = "";
		double temp = 0.00;
		try {
			List<Double> listStr = new ArrayList<Double>();
			for (TradePackage tmp : p) {
				if (null != tmp.getGoodsWeight()) {
					listStr.add(Double.valueOf(MoneyUtil.getFormatMoney(tmp.getActualInventory(), "0.00")));
				} else {
					listStr.add(0.00);
				}
			}
			if (listStr != null && listStr.size() > 0) {
				for (Double obj : listStr) {
					if (null == obj) {
						obj = 0.00 * 100;
					}
					double d = obj * 100;
					temp += d;

				}
			}
			result = String.valueOf(temp / 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * ��ȡ���������ַ���
	 *
	 * @param type
	 *
	 * @param relationNum
	 *
	 * @param isOne
	 *
	 * @return
	 */
	public String getRelationNumString(String relationNum, String type, boolean isOne) {
		String result = "";
		if (EnumOutDepository.OUT_SALES.getKey().equals(type)) {
			List<String> listStr = tradePackageManager.getTidByMergeTid(relationNum);
			if (listStr != null && listStr.size() > 0) {
				int i = 1;
				if (isOne) {
					result = listStr.get(0);
				} else {
					for (String obj : listStr) {
						if (i == listStr.size()) {
							result += obj;
						} else {
							result += obj + ",";
						}
						i++;
					}
				}
			}
		} else {
			result = relationNum;
		}
		return result;
	}
}
