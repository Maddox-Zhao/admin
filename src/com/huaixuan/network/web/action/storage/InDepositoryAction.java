package com.huaixuan.network.web.action.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.GatherInDepository;
import com.huaixuan.network.biz.domain.storage.GoodsForLocation;
import com.huaixuan.network.biz.domain.storage.InDepository;
import com.huaixuan.network.biz.domain.storage.InDetail;
import com.huaixuan.network.biz.domain.storage.InDetailBaseInfo;
import com.huaixuan.network.biz.domain.storage.InDetailGoods;
import com.huaixuan.network.biz.domain.storage.ProdRelationIn;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.FinanceDepositoryQuery;
import com.huaixuan.network.biz.domain.storage.query.GatherQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.trade.Refund;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumInDepository;
import com.huaixuan.network.biz.enums.EnumInDetailStatus;
import com.huaixuan.network.biz.enums.EnumInStatus;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.query.InDepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.stock.ShoppingListService;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.InDepositoryManager;
import com.huaixuan.network.biz.service.storage.InDetailManager;
import com.huaixuan.network.biz.service.storage.ProdRelationInManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.storage.StorageRefundApplyManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.biz.service.trade.RefundManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.common.util.MoneyUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminDeniedException;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping(value = "/storage")
public class InDepositoryAction extends BaseAction {

	private static final long serialVersionUID = 7308220377080047045L;
	// Springע��
	@Autowired
	AttributeManager attributeManager;
	@Autowired
	CategoryManager categoryManager;
	@Autowired
	InDetailManager inDetailManager;
	@Autowired
	ShoppingListService shoppingListService;

	@Autowired
	private InDepositoryManager inDepositoryManager;
	// private InDetailManager inDetailManager;
	// private UserManager userManager;
	// private CategoryManager categoryManager;
	// private StorageManager storageManager;
	@Autowired
	private DepositoryService depositoryService;
	@Autowired
	private DepLocationManager depLocationManager;
	@Autowired
	private GoodsBatchManager goodsBatch;
	@Autowired
	private ProdRelationInManager prodRelationInManager;
	// private ShoppingListManager shoppingListManager;
	@Autowired
	private AdminService adminService;
	// private Long shoppingRefId;
	// private String aType;
	// private String cType;
	// private RoleManager roleManager;
	// private AuthorityManager authorityManager;
	@Autowired
	private RefundManager refundManager;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;

	// private GoodsInstanceManager goodsInstanceManager;
	@Autowired
	private SupplierService supplierService;

	// //�Ƿ���ʾ��ҳ��
	// private Boolean disPage;
	// private GoodsManager goodsManager;
	// private String applyRelationNum;
	// private StorageRefundApplyManager storageRefundApplyManager;
	//
	// // ����
	// private InDepository inDepositoryDispaly;
	// private Map<String, String> inDepositoryTypeMap = EnumInDepository.toMap();
	// private Map<String, String> inDepositoryStatusMap = EnumInStatus.toMap();
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private Map<Long, String> depMap = new HashMap<Long, String>();
	// private Map<String, String> enumShoppingRefundReason = EnumShoppingRefundReason.toMap();
	// private Page page;
	// private List<InDepository> inDepositoryDisLists;
	// private List<User> userLists;
	// private List<InDetailGoods> inDetailGoodsLists;
	private List<GatherInDepository> gatherInDepositoryLists;

	// private List<GoodsForLocation> goodsForLocationLists;
	// private String optId;
	// private String optType;
	// private String dfi;
	// private String selectDepId;
	// private String errorInfo;
	// private Boolean succFlag = Boolean.FALSE;
	// private String iId;
	// private Date currDate;
	// private InDetailBaseInfo inDetailBaseInfo;
	// private List<Storage> storageList;
	// private List<Depository> depositoryList;
	// private Boolean notAllowEdit;
	// private List<DepLocation> depLocationInfoList;
	// private List<GoodsForLocation> inDetailInfoForDisList;
	// private List<ProdRelationIn> prodRelationInLists;
	// private List<DepLocation> depLocationLists;
	// private List<DepLocation> depLocationListInit;
	// private List<Category> catList; //һ����Ŀ
	// private List<Category> twocatList; //������Ŀ
	// private List<Category> twocatListInit; //������Ŀ����
	// private Map<String, String> stockAgeMap = EnumStockAge.toMap(); //����ö��
	// private Storage storage;
	// private List<DepositoryFirst> depositoryFirstList; //һ���ֿ��б�
	// private List<Depository> selectdeplist; //Json���صĲֿ��б�
	// private List<DepLocation> selectloclist;
	// private List<GoodsInstance> goodsInstanceList;
	// //Json���صĿ�λ�б�
	// private Map<String, String> storTypeMap = EnumStorType.toMap();
	// private String message;
	//

	@Autowired
	private StorageManager storageManager;

	@Autowired
	private StorageRefundApplyManager storageRefundApplyManager;

	/**
	 * ��ⵥ�ɱ�����ͳ�Ƶ���
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author fanyj 2009/09/15
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportGetherInDep")
	public void exportGetherInDep(@ModelAttribute("gatherQuery") GatherQuery gatherQuery,
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
			res.setHeader("Content-disposition", "attachment; filename=GetherInDepository_" + date + ".xls");
			// һ���ֿ�Ȩ�޹���
			gatherQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
			QueryPage queryPage = inDetailManager.gatherInDepositoryLists(gatherQuery, 1, pageSize, false);
			gatherInDepositoryLists = (List<GatherInDepository>) queryPage.getItems();
			if (gatherInDepositoryLists != null && gatherInDepositoryLists.size() > 0) {
				List<GatherInDepository> gatherSearchLists = new ArrayList<GatherInDepository>();
				for (GatherInDepository obj : gatherInDepositoryLists) {
					obj.setAttrs(attributeManager.getFullAttributeStringByAttrs(obj.getAttrs()));
					gatherSearchLists.add(obj);
				}
				gatherInDepositoryLists = gatherSearchLists;
			}
			String[] title = { "���ݱ��", "��Ʒ����", "��Ʒ����", "��Ʒ����", "����", "���ʱ��", "���ۣ�����", "����", "������" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					if (obj.getStorType() != null && obj.getStorType().equals("v")) {
						obj.setStorType("�ݹ�");
					} else {
						obj.setStorType("ʵ��");
					}
					if (obj.getUnitPrice() == null) {
						obj.setUnitPrice(new Double(0.00));
					}
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					Object[] data = { obj.getBillNum(), obj.getInstanceName(), obj.getGoodsInstanceCode(),
							obj.getAttrs(), obj.getStorType() + "", df.format(obj.getGmtInDep()),
							DoubleUtil.round(obj.getUnitPrice(), 2), obj.getAmount(),
							DoubleUtil.round(obj.getSumMoney(), 2) };
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
	 * ��ⵥ�ɱ�����ͳ�Ʋ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gather_in_depository")
	public String gatherInDepository(@ModelAttribute("gatherQuery") GatherQuery gatherQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "init", required = false, defaultValue = "") String init,
			AdminAgent adminAgent,Model model) throws Exception {
		// һ���ֿ�Ȩ�޹���
		gatherQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		// Ĭ�ϲ�ѯ30�������
		if ("true".equals(init)) {
			gatherQuery.setInDepTimeStart(DateUtil.getDiffDate(new Date(), -30));
			gatherQuery.setInDepTimeEnd(DateUtil.getDateToString(new Date()));
		}
		QueryPage queryPage = inDetailManager.gatherInDepositoryLists(gatherQuery, currPage, pageSize, true);
		model.addAttribute("attributeManager", attributeManager);
		model.addAttribute("query", queryPage);
		return "/storage/gather_in_depository";
	}

	/**
	 * ��ӡ��ⵥ
	 *
	 * @param id
	 *            ���ⵥID
	 * @param pageNum
	 *            ÿҳ��ʾ����
	 * @param fontSize
	 *            �����С
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("printInDep")
	public String printInDepository(@RequestParam("id") Long id,
			@RequestParam(value = "pageNum", required = false, defaultValue = "8") Integer pageNum,
			@RequestParam(value = "fontSize", required = false, defaultValue = "3") Integer fontSize, Model model,
			AdminAgent agent) throws Exception {
		model.addAttribute("id", id);
		model.addAttribute("pageNum", Integer.valueOf(pageNum).intValue());
		model.addAttribute("fontSize", Integer.valueOf(fontSize).intValue());

		// ȡ����ⵥ������Ϣ
		InDepository inDepositoryDispaly = inDepositoryManager.getInDepository(id);
		model.addAttribute("inDepositoryDispaly", inDepositoryDispaly);

		List<ProdRelationIn> prodRelationInLists = prodRelationInManager.getPrintList(id);
		model.addAttribute("prodRelationInLists", prodRelationInLists);

		String buyNick = "";
		String remark = "";
		if (inDepositoryDispaly != null) {
			// ȡ�ɹ����ı�ע��Ϣ
			if (EnumInDepository.IN_SHOPPING.getKey().equals(inDepositoryDispaly.getType())) {
				ShoppingList shoppingList = shoppingListService.getShoppingListByShoppingNum(inDepositoryDispaly
						.getRelationNum());
				if (shoppingList != null) {
					remark = shoppingList.getRemark();
				}
			}
			// ȡ�����˻����ǳ���Ϣ
			if (EnumInDepository.IN_SALES_REFUND.getKey().equals(inDepositoryDispaly.getType())) {
				Refund refund = refundManager.getRefundByRefundId(inDepositoryDispaly.getRelationNum());
				if (refund != null) {
					buyNick = refund.getBuyNick();
				}
			}
		}
		model.addAttribute("remark", remark);
		model.addAttribute("buyNick", buyNick);

		double sumMoney = 0;
		int sumCount = 0;
		String suppilerName = "";
		for (ProdRelationIn obj : prodRelationInLists) {
			sumMoney += obj.getMoney();
			sumCount += obj.getAmount();
			suppilerName = obj.getSupplierName();
		}
		model.addAttribute("sumMoney", sumMoney);
		model.addAttribute("sumCount", sumCount);
		model.addAttribute("suppilerName", suppilerName);

		model.addAttribute("userName", agent.getUsername());

		model.addAttribute("inDepositoryTypeMap", EnumInDepository.toMap());
		model.addAttribute("attributeManager", attributeManager);

		return "/storage/printInDep";
	}

	/**
	 * ��ⵥ��ѯ
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/21
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("addInDepository")
	public String addInDepository(@ModelAttribute("query") InDepositoryQuery query,
			@RequestParam(value = "init", required = false) String initFlag,
			@RequestParam(value = "optFlag", required = false) String optFlag,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent, HttpServletResponse res) throws Exception {
		// ȡ��һ���ֿ��б�
		List<Long> depositoryFirstIds = getDepfirstIdForQuery(agent);
		query.setDepfirstIds(depositoryFirstIds);
		if (depositoryFirstIds == null || depositoryFirstIds.size() == 0) {
			return "/storage/addInDepository";
		}
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(depositoryFirstIds);
		model.addAttribute("depositoryFirstList", depositoryFirstList);

		// ��ʼ��ʱ����Ҫ����Ĭ��ʱ��
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			query.setOptTimeStart(DateUtil.getDiffDate(new Date(), -30));
			query.setOptTimeEnd(DateUtil.getDateToString(new Date()));
		}

		// ȡ��URL�е�ID����������ȡ������.
		if (StringUtil.isNotBlank(optFlag) && optFlag.equals("exportExcel")) {
			// Excel����
			exportInDepository(query, res);
			return "/storage/addInDepository";
		}

		// ȡ�÷�����������������
		// ȡ����ⵥ����
		QueryPage page = inDepositoryManager.getInDepositoryLists(query, currPage, pageSize, true);
		model.addAttribute("page", page);

		// û��ָ����Ӧ�̲�ѯ�����
		if (StringUtil.isBlank(query.getSupplierId())) {
			List<String> inDepositoryIds = new ArrayList<String>();
			if (page.getItems() != null) {
				for (InDepository ind : (List<InDepository>) page.getItems()) {
					if (ind.getType().equals("in_shopping")) {
						inDepositoryIds.add(ind.getRelationNum());
					}
				}
			}
			if (inDepositoryIds.size() > 0) {
				Map supplierNameMap = inDepositoryManager.getSupplierNameById(inDepositoryIds);

				for (InDepository ind : (List<InDepository>) page.getItems()) {
					ind.setSupplierName((String) supplierNameMap.get(new BigDecimal(ind.getId())));
				}
			}
		}
		setDepFirstNameList((List<InDepository>) page.getItems());

		// ȡ�ù���ԱȨ�޵Ļ�Ա�б�
		// userLists = userManager.getUserByIsAdmin(1);
		// �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		List<Admin> userList = adminService.getAdminUserList();
		model.addAttribute("userList", userList);

		// �����ȡ�����еĹ�Ӧ����Ϣ
		List<Supplier> supplierList = supplierService.getSupplier();
		model.addAttribute("supplierList", supplierList);

		model.addAttribute("inDepositoryTypeMap", EnumInDepository.toMap());
		model.addAttribute("inDepositoryStatusMap", EnumInStatus.toMap());

		return "/storage/addInDepository";
	}

	/**
	 * ȡ��List�е�һ���ֿ�ID������һ���ֿ�����
	 *
	 * @param inDepositoryList
	 *            List
	 * @author chenyan 2010/03/15
	 */
	private void setDepFirstNameList(List<InDepository> inDepositoryList) {
		// ����id����һ���ֿ����ƣ��ظ���id������ѯ��ֱ����ʾ��һ�λ�õ�����
		Long depFirstId = null;
		String depFirstName = "";
		if (inDepositoryList != null) {
			for (InDepository inDepositoryTmp : inDepositoryList) {
				if (inDepositoryTmp == null || inDepositoryTmp.getDepFirstId() == null) {
					continue;
				}
				if (inDepositoryTmp.getDepFirstId() == depFirstId) {
					inDepositoryTmp.setDepFirstName(depFirstName);
				} else {
					depFirstId = inDepositoryTmp.getDepFirstId();
					if (inDepositoryTmp.getDepFirstId() != null) {
						DepositoryFirst df = depositoryFirstManager.getDepositoryById(inDepositoryTmp.getDepFirstId());
						if (df != null) {
							inDepositoryTmp.setDepFirstName(df.getDepFirstName());
							depFirstName = df.getDepFirstName();
						} else {
							depFirstName = "";
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Boolean exportInDepository(InDepositoryQuery query, HttpServletResponse res) throws Exception {
		OutputStream outwt = null;
		try {
			// ����������������
			List<InDepository> inDepositoryForExport = (List<InDepository>) inDepositoryManager.getInDepositoryLists(
					query, 0, pageSize, false).getItems();

			// û��ָ����Ӧ�̲�ѯ�����
			if (StringUtil.isBlank(query.getSupplierId())) {
				List<String> inDepositoryIds = new ArrayList<String>();
				for (InDepository inDepository : inDepositoryForExport) {
					if (inDepository.getType().equals("in_shopping")) {
						inDepositoryIds.add(inDepository.getRelationNum());
					}
				}
				if (inDepositoryIds.size() > 0) {
					Map supplierNameMap = this.inDepositoryManager.getSupplierNameById(inDepositoryIds);

					for (InDepository inDepository : inDepositoryForExport) {
						inDepository
								.setSupplierName((String) supplierNameMap.get(new BigDecimal(inDepository.getId())));
					}
				}
			}

			setDepFirstNameList(inDepositoryForExport);

			Date da = new Date();
			outwt = res.getOutputStream();
			// ȡ�õ���excel��ʱ�䣬�����ļ�����
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=InDepository" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> inDepositoryExportList = new ArrayList<Object[]>();
			String[] title = { "���ݱ��", "��������", "�������ݺ�", "����ʱ��", "������", "һ���ֿ�", "״̬", "�ܽ��", "��Ӧ��" };
			inDepositoryExportList.add(title);
			if (inDepositoryForExport != null) {
				for (InDepository tmp : inDepositoryForExport) {
					// zhangwy ������ⵥ�ܽ��
					List<InDetailGoods> inDetailGoodsLists = inDetailManager.getInDetailGoodsLists(tmp.getId(),
							tmp.getType(), tmp.getRelationNum());
					double totalAmount = 0;
					for (InDetailGoods inDetailGoodsInfo : inDetailGoodsLists) {
						totalAmount = totalAmount + inDetailGoodsInfo.getUnitPrice() * inDetailGoodsInfo.getAmount();
					}
					SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
					Object[] data = {
							tmp.getBillNum() + "",
							StringUtil.isBlank(EnumInDepository.toMap().get((tmp.getType()))) ? "" : EnumInDepository
									.toMap().get((tmp.getType())) + "",
							tmp.getRelationNum() + "",
							df2.format(tmp.getGmtModify()) + "",
							StringUtil.isBlank(tmp.getCreater()) ? "" : tmp.getCreater() + "",
							tmp.getDepFirstName(),
							StringUtil.isBlank(EnumInStatus.toMap().get(tmp.getStatus())) ? "" : EnumInStatus.toMap()
									.get(tmp.getStatus()) + "", DoubleUtil.round(totalAmount, 2),
							StringUtil.isBlank(tmp.getSupplierName()) ? "" : tmp.getSupplierName() };
					inDepositoryExportList.add(data);
				}
			}
			goodsBatch.exportExcelByObject(outwt, inDepositoryExportList);
			outwt.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			close(outwt);
		}
		return Boolean.TRUE;
	}

	/**
	 * ����ID��ʾ��ⵥ����
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/22
	 */
	@RequestMapping("showInDetail")
	public String showInDetail(@RequestParam("iId") Long id,
			@RequestParam(value = "optType", required = false) String optType,
			@RequestParam(value = "inDepositoryTime", required = false) String gmtInDep, Model model, AdminAgent agent)
			throws Exception {
		// ȡ����ⵥ������Ϣ
		InDepository inDepositoryDispaly = inDepositoryManager.getInDepository(id);

		// �ж��Ƿ�����ʾ��ҳ���Ȩ��
		if (!hasDepFirstAuth(agent, inDepositoryDispaly.getDepFirstId())) {
			model.addAttribute("disPage", false);
			return "/storage/showInDetail";
		} else {
			model.addAttribute("disPage", true);
		}

		if (StringUtil.isBlank(gmtInDep) && inDepositoryDispaly.getGmtInDep() == null) {
			model.addAttribute("currDate", new Date());
		}

		// ��ⵥ�Ƿ���������λ
		model.addAttribute("notAllowEdit", EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus()));

		// ȡ����ⵥ������Ϣ
		List<InDetailGoods> inDetailGoodsLists = inDetailManager.getInDetailGoodsLists(inDepositoryDispaly.getId(),
				inDepositoryDispaly.getType(), inDepositoryDispaly.getRelationNum());
		if (inDetailGoodsLists != null && inDetailGoodsLists.size() > 0) {
			for (InDetailGoods inDetailGoodsInfo : inDetailGoodsLists) {
				inDetailGoodsInfo.setCatName(categoryManager.getCatFullNameByCatcode(inDetailGoodsInfo.getCatCode()));
				inDetailGoodsInfo.setAttributeName(attributeManager.getFullAttributeStringByAttrs(inDetailGoodsInfo
						.getAttrs()));

			}
		}
		model.addAttribute("inDetailGoodsLists", inDetailGoodsLists);

		// �����ɵĲ���
		if (StringUtil.isNotBlank(optType) && optType.equals("finishOpt")) {
			// �����ɵ����
			List<InDetailGoods> inDetailNotFinishList = inDetailManager.listInDetailNotFinish(id,
					EnumInDetailStatus.IN_FINISHED.getKey());
			// ���ʱ��Ĭ���õ�����ʱ�䣬�����ж��� zhangwy 2010/11/23
			// if (StringUtil.isBlank(gmtInDep)
			// || DateUtil.strToDate(gmtInDep, "yyyy-MM-dd").getTime() > new Date().getTime()) {
			// // ���ʱ�䲻��ȷ
			// errorInfo = "���ʱ�䲻��ȷ";
			// } else
			if (inDetailNotFinishList != null && inDetailNotFinishList.size() > 0) {
				// �в�Ʒδ�����λ
				String notFinishInstanceName = "";
				for (InDetailGoods inDetailNotFinishInfo : inDetailNotFinishList) {
					if (StringUtil.isBlank(notFinishInstanceName)) {
						notFinishInstanceName = inDetailNotFinishInfo.getInstanceName();
					} else {
						notFinishInstanceName = notFinishInstanceName + "," + inDetailNotFinishInfo.getInstanceName();
					}
				}
				model.addAttribute("errorInfo", "�в�Ʒ��" + notFinishInstanceName + "  δ�����λ");
			} else if (EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus())) {
				// ��ⵥ״̬Ϊ�����
				model.addAttribute("errorInfo", "����ⵥ״̬Ϊ�����");
			} else {
				// if (DateUtil.strToDate(gmtInDep, "yyyy-MM-dd HH:mm:ss") == null) {
				// SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				// String hms = df.format(new Date());
				// StringBuffer temp = new StringBuffer();
				// gmtInDep = temp.append(gmtInDep).append(" ").append(hms).toString();
				// }
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("inDepId", new Long(id));
				param.put("inDetailGoodsLists", inDetailGoodsLists);
				param.put("inDepositoryDispaly", inDepositoryDispaly);
				try {
					// ��ɲ����ܿ�
					model.addAttribute("succFlag", inDepositoryManager.addStorageOpt(param));
				} catch (Exception e) {
					// ��λ���̵��е����
					model.addAttribute("succFlag", false);
					model.addAttribute("errorInfo", "�п�λ���̵��л��λ�ѱ�ɾ��,�����·����Ʒ");
					model.addAttribute("currDate", DateUtil.strToDate(gmtInDep, "yyyy-MM-dd HH:mm:ss"));
				}

				// ����ȡ����ⵥ������Ϣ������ԭҳ����ʾ
				inDepositoryDispaly = inDepositoryManager.getInDepository(id);
				model.addAttribute("notAllowEdit",
						EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus()));
			}
			// currDate = DateUtil.strToDate(gmtInDep, "yyyy-MM-dd HH:mm:ss");
			model.addAttribute("currDate", new Date());
		}

		// ����IDȡ��һ���ֿ�����
		if (inDepositoryDispaly != null && inDepositoryDispaly.getDepFirstId() != null) {
			DepositoryFirst df = depositoryFirstManager.getDepositoryById(inDepositoryDispaly.getDepFirstId());
			if (df != null) {
				inDepositoryDispaly.setDepFirstName(df.getDepFirstName());
			}
		}
		model.addAttribute("inDepositoryDispaly", inDepositoryDispaly);

		model.addAttribute("inDepositoryTypeMap", EnumInDepository.toMap());
		model.addAttribute("inDepositoryStatusMap", EnumInStatus.toMap());
		return "/storage/showInDetail";
	}

	/**
	 * ��Ʒ��λ�������
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author chenyan 2009/07/22
	 */
	@RequestMapping("inDepositoryOpt")
	public String inDepositoryOpt(@RequestParam("optId") Long id, @RequestParam("optType") String type,
			@RequestParam("dfi") Long depFirstId, @RequestParam(value = "selectDepId", required = false) Long depId,
			@RequestParam(value = "actionType", required = false) String actionType, Model model, AdminAgent agent,
			ServletRequest req) throws Exception {
		// ѡ��Ĳֿ�
		if (!hasDepFirstAuth(agent, depFirstId)) {
			throw new AdminDeniedException();
		}

		// ����ҳ�����
		model.addAttribute("optId", id);
		model.addAttribute("optType", type);
		model.addAttribute("dfi", depFirstId);
		model.addAttribute("selectDepId", depId);

		String inDepStatus = inDepositoryManager.getInDepositoryStatusByDetailId(id);
		if (StringUtil.isBlank(inDepStatus) || inDepStatus.equals(EnumInStatus.IN_FINISHED.getKey())) {
			// �������ɣ������ٱ༭
			model.addAttribute("notAllowEdit", true);
			model.addAttribute("inDetailInfoForDisList", inDetailManager.listInDetailForDisByDetailId(id));
		}

		String vmRedirect = "";
		if (StringUtil.isNotBlank(type) && type.equals(EnumInDepository.IN_SHOPPING.getKey())) {
			// �ɹ����
			vmRedirect = inDepositoryInShopping(id, type, depFirstId, depId, actionType, model, req);
		} else if (StringUtil.isNotBlank(type)
				&& (type.equals(EnumInDepository.IN_SALES_CHANGE.getKey()) || type
						.equals(EnumInDepository.IN_SALES_REFUND.getKey()))) {
			// �����˻��������ۻ���
			vmRedirect = inDepositoryInSale(id, type, depFirstId, model, req);
		} else if (StringUtil.isNotBlank(type)
				&& (type.equals(EnumInDepository.IN_CHECK_MORE.getKey())
						|| type.equals(EnumInDepository.IN_ZERO_CHECK_MORE.getKey())
						|| type.equals(EnumInDepository.IN_MOVE_STORAGE.getKey())
						|| type.equals(EnumInDepository.IN_BORROW_STORAGE.getKey()) || type
						.equals(EnumInDepository.IN_BACK_STORAGE.getKey()))) {
			// �̵��������������ӯ
			vmRedirect = inDepositoryCheckMore(id, type, depFirstId, depId, actionType, model, req);
		}

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		return vmRedirect;

	}

	/**
	 * ���������˻������
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @param depFirstId
	 *            Long
	 * @return String
	 * @author chenyan 2009/07/25
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String inDepositoryInSale(Long id, String type, Long depFirstId, Model model, ServletRequest req)
			throws Exception {
		String[] locationNumArray = req.getParameterValues("locationNum");
		String[] disCountArray = req.getParameterValues("disCount");
		// ȡ�û�����Ϣ
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getShoppingOrSalesInDetailBaseInfo(id, type);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		if (inDetailBaseInfo != null) {
			DepositoryFirst dfTemp = depositoryFirstManager.getDepositoryById(inDetailBaseInfo.getDepFirstId());
			if (dfTemp != null) {
				inDetailBaseInfo.setDepFirstName(dfTemp.getDepFirstName());
			}
		}

		// ��λ����(������ȡ�û�����Ϣ)
		inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);

		// ȡ������
		List<GoodsForLocation> goodsForLocationLists = inDetailManager.getSalesLocationForGoods(new Long(id));
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		// depositoryList = depositoryService.getDeplistByFirstDepId(depFirstId);
		// zhangwy ��ⲻ��������
		List<Depository> depositoryList = depositoryService.getRightDeplistByFirstDepId(depFirstId);
		model.addAttribute("depositoryList", depositoryList);

		List<DepLocation> depLocationInfoList;
		if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
			// ����Ʒ�Ϳ�λ������ʱ��ȡ�ö�Ӧ�Ŀ��ÿ�λ��Ϣ
			depLocationInfoList = depLocationManager.getCheckLocationInfo(inDetailBaseInfo.getGoodsInstanceId(),
					EnumDepLocationIsCheck.N.getKey(), depFirstId);
		} else {
			// û�н�����Ʒ�Ϳ�λ������ʱ��ȡ�����п�λ�Ŀ��ÿ�λ��Ϣ
			depLocationInfoList = depLocationManager.listUseabledLocInfo(depFirstId);
		}
		model.addAttribute("depLocationInfoList", depLocationInfoList);

		// ȡ�ÿ��ÿ�λ��Ϣ

		List filterId = new ArrayList();
		int i = 0;
		// ȡ���ѷ�������
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			if (disCountArray != null && disCountArray.length == goodsForLocationLists.size()
					&& locationNumArray != null && locationNumArray.length == goodsForLocationLists.size()) {
				goodsForLocationInfo.setOriAmountForDis(disCountArray[i]);
				goodsForLocationInfo.setOriLocIdForDis(locationNumArray[i]);
				if (StringUtil.isNotBlank(locationNumArray[i])) {
					DepLocation depLocationTemp = depLocationManager.getDepLocationByLocationId(Long
							.parseLong(locationNumArray[i]));
					if (depLocationTemp != null && depLocationTemp.getDepId() != null) {
						goodsForLocationInfo.setOriDepIdForDis(String.valueOf(depLocationTemp.getDepId()));
					}
				}
			} else {
				Map getAmountMap = new HashMap();
				getAmountMap.put("inDepId", inDetailBaseInfo.getInDepId());
				getAmountMap.put("goodsInstanceId", inDetailBaseInfo.getGoodsInstanceId());
				getAmountMap.put("goodsId", inDetailBaseInfo.getGoodsId());
				getAmountMap.put("inDetailId", inDetailBaseInfo.getInDetailId());
				getAmountMap.put("supplierId", goodsForLocationInfo.getSupplierId());
				getAmountMap.put("batchNum", goodsForLocationInfo.getBatchNum());
				List<ProdRelationIn> oriAmountForDisList = prodRelationInManager
						.getSalesDistributedAmount(getAmountMap);
				for (ProdRelationIn filterProdRelationInId : oriAmountForDisList) {
					if (filterId.contains(filterProdRelationInId.getId())) {
						continue;
					} else {
						filterId.add(filterProdRelationInId.getId());
						for (DepLocation depLocationInfo : depLocationInfoList) {
							// ����λ��Ȼ����ʱ��Ż��ԡ�
							if (depLocationInfo != null
									&& depLocationInfo.getId().equals(filterProdRelationInId.getLocId())) {
								goodsForLocationInfo.setOriAmountForDis(String.valueOf(filterProdRelationInId
										.getAmount()));
								goodsForLocationInfo
										.setOriLocIdForDis(String.valueOf(filterProdRelationInId.getLocId()));
								DepLocation depLocationTemp = depLocationManager
										.getDepLocationByLocationId(filterProdRelationInId.getLocId());
								if (depLocationTemp != null && depLocationTemp.getDepId() != null) {
									goodsForLocationInfo.setOriDepIdForDis(String.valueOf(depLocationTemp.getDepId()));
								}
							}
						}
						break;
					}
				}
			}
			i = i + 1;
		}

		return "/storage/inDepositorySalesOpt";
	}

	/**
	 * �����̵�������
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @param depFirstId
	 *            Long
	 * @return String
	 * @author chenyan 2009/07/24
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String inDepositoryCheckMore(Long id, String type, Long depFirstId, Long depId, String actionType,
			Model model, ServletRequest req) throws Exception {
		String[] locationNumArray = null;
		String[] disCountArray = null;

		// ȡ�û�����Ϣ
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getCheckInDetailBaseInfo(id);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		// ��װһ���ֿ�Ϳ�����͵�������Ϣ
		if (inDetailBaseInfo != null && inDetailBaseInfo.getDepFirstId() != null) {
			DepositoryFirst depositoryFirstTmp = depositoryFirstManager.getDepositoryById(inDetailBaseInfo
					.getDepFirstId());
			if (depositoryFirstTmp != null) {
				inDetailBaseInfo.setDepFirstName(depositoryFirstTmp.getDepFirstName());
			}
			InDepository indepository = inDepositoryManager.getInDepository(inDetailBaseInfo.getInDepId());
			if (indepository != null) {
				inDetailBaseInfo.setType(indepository.getType());
			}
		}
		if (inDetailBaseInfo == null) {
			// ������Ϣ����ȷ����ת������ҳ��
			throw new Exception("�޷���ȡ�ɹ������˻������ʱ��Ļ�����Ϣ");
		}

		// ��ȡ��ⵥ���� add by zhangwy 2010-11-3
		if (!EnumInDepository.IN_ZERO_CHECK_MORE.getKey().equals(type)) {
			if (StringUtils.isNotBlank(actionType)) {
				locationNumArray = req.getParameterValues("locationNum");
				disCountArray = req.getParameterValues("disCount");

				// ��λ����(������ȡ�û�����Ϣ)
				inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);
			}
		}
		// // ����ѡ��ֿ���˿�λ������ add by fanyj 2010-10-12
		// if(StringUtils.isNotBlank(actionType)){
		// locationNumArray = request.getParameterValues("locationNum");
		// disCountArray = request.getParameterValues("disCount");
		//
		// // ��λ����(������ȡ�û�����Ϣ)
		// inDepositoryForSaleAndShopping(id, type);
		// }

		DepLocation depLocation = depLocationManager.getDepLocationByLocationId(inDetailBaseInfo.getOriLocationId());
		Depository depository = depositoryService.getDepository(depLocation.getDepId());

		// ��Ʒ���������λ����,ֻ�������Լ�ԭ���Ĳֿ�Ϳ�λ zhangwy
		List<GoodsForLocation> goodsForLocationLists;
		if (EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType())) {
			goodsForLocationLists = inDetailManager.getLocationForDefect(new Long(id), depLocation.getId());
		} else {
			if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
				// ����Ʒ�Ϳ�λ������ʱ��ȡ�ö�Ӧ�Ŀ��ÿ�λ��Ϣ
				goodsForLocationLists = inDetailManager.getLocationForGoods(new Long(id), depFirstId);
			} else {
				// û�н�����Ʒ�Ϳ�λ������ʱ��ȡ�����п�λ�Ŀ��ÿ�λ��Ϣ
				goodsForLocationLists = inDetailManager.getLocationForGoodsNoMatch(new Long(id), depFirstId);
			}
		}
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		List<GoodsForLocation> lists = new ArrayList<GoodsForLocation>();
		// ͨ��ѡ��ֿ��������
		Map depMap = new HashMap();
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			// ȡ�òֿ���Ϣ
			depMap.put(goodsForLocationInfo.getDepId(), goodsForLocationInfo.getDepName());
			// ͨ����ѡ�ֿ�������ݼ���
			if (depId != null && depId.equals(goodsForLocationInfo.getDepId())) {
				lists.add(goodsForLocationInfo);
			}
		}
		if (depMap != null) {
			Depository dep = null;
			List<Depository> selectdeplist = new ArrayList<Depository>();
			for (Iterator iter = depMap.entrySet().iterator(); iter.hasNext();) {
				dep = new Depository();
				Map.Entry entry = (Map.Entry) iter.next();
				dep.setId(Long.valueOf((Long) entry.getKey()));
				dep.setName(String.valueOf(entry.getValue()));
				selectdeplist.add(dep);
			}
			model.addAttribute("selectdeplist", selectdeplist);
		}

		if (depId != null) {
			goodsForLocationLists = lists;// �����˺�ļ��Ϸ���
		}
		// ȡ���ѷ�������
		int i = 0;
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			if (disCountArray != null && disCountArray.length == goodsForLocationLists.size()
					&& locationNumArray != null && locationNumArray.length == goodsForLocationLists.size()) {
				goodsForLocationInfo.setOriAmountForDis(disCountArray[i]);
				goodsForLocationInfo.setOriLocIdForDis(locationNumArray[i]);
			} else {
				Map getAmountMap = new HashMap();
				getAmountMap.put("inDepId", inDetailBaseInfo.getInDepId());
				getAmountMap.put("goodsInstanceId", inDetailBaseInfo.getGoodsInstanceId());
				getAmountMap.put("goodsId", inDetailBaseInfo.getGoodsId());
				getAmountMap.put("inDetailId", inDetailBaseInfo.getInDetailId());
				getAmountMap.put("locId", goodsForLocationInfo.getLocationId());
				Long oriAmountForDis = prodRelationInManager.getShoppingAndCheckDistributedAmount(getAmountMap);
				goodsForLocationInfo.setOriAmountForDis(oriAmountForDis == null ? "" : String.valueOf(oriAmountForDis));
			}
			i = i + 1;
		}
		return "/storage/inDepositoryCheckOpt";
	}

	/**
	 * ����ɹ����
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @param depFirstId
	 *            Long
	 * @return String
	 * @author chenyan 2009/07/24
	 */
	private String inDepositoryInShopping(Long id, String type, Long depFirstId, Long depId, String actionType,
			Model model, ServletRequest req) throws Exception {
		String[] locationNumArray = null;
		String[] disCountArray = null;

		// ȡ��һ���ֿ�Ϳ������
		InDetailBaseInfo shoppingListInfo = inDetailManager.getInfoByInDetailIdForShoppingList(id);
		// ȡ�û�����Ϣ
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getShoppingOrSalesInDetailBaseInfo(id, type);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		// ��װһ���ֿ�Ϳ�����͵�������Ϣ
		if (shoppingListInfo != null && inDetailBaseInfo != null) {
			inDetailBaseInfo.setDepFirstId(shoppingListInfo.getDepFirstId());
			inDetailBaseInfo.setDepFirstName(shoppingListInfo.getDepFirstName());
			inDetailBaseInfo.setStorType(shoppingListInfo.getStorType());
		}
		if (inDetailBaseInfo == null) {
			throw new Exception("�޷���ȡ�ɹ������˻������ʱ��Ļ�����Ϣ");
		}

		// ����ѡ��ֿ���˿�λ������ add by fanyj 2010-10-12
		if (StringUtils.isNotBlank(actionType)) {
			locationNumArray = req.getParameterValues("locationNum");
			disCountArray = req.getParameterValues("disCount");

			// ��λ����(������ȡ�û�����Ϣ)
			inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);
		}

		List<GoodsForLocation> goodsForLocationLists;
		if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
			// ����Ʒ�Ϳ�λ������ʱ��ȡ�ö�Ӧ�Ŀ��ÿ�λ��Ϣ
			goodsForLocationLists = inDetailManager.getLocationForGoods(id, depFirstId);
		} else {
			// û�н�����Ʒ�Ϳ�λ������ʱ��ȡ�����п�λ�Ŀ��ÿ�λ��Ϣ
			goodsForLocationLists = inDetailManager.getLocationForGoodsNoMatch(id, depFirstId);
		}
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		List<GoodsForLocation> lists = new ArrayList<GoodsForLocation>();
		Map<Long, String> depMap = new HashMap<Long, String>();
		// ͨ��ѡ��ֿ��������
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			// ȡ�òֿ���Ϣ
			depMap.put(goodsForLocationInfo.getDepId(), goodsForLocationInfo.getDepName());
			// ͨ����ѡ�ֿ�������ݼ���
			if (depId != null && depId.equals(goodsForLocationInfo.getDepId())) {
				lists.add(goodsForLocationInfo);
			}
		}
		if (depMap != null) {
			List<Depository> selectdeplist = new ArrayList<Depository>();
			model.addAttribute("selectdeplist", selectdeplist);

			for (Entry<Long, String> entry : depMap.entrySet()) {
				Depository dep = new Depository();
				dep.setId(entry.getKey());
				dep.setName(entry.getValue());
				selectdeplist.add(dep);
			}
		}

		if (depId != null) {
			goodsForLocationLists = lists;// �����˺�ļ��Ϸ���
			model.addAttribute("goodsForLocationLists", goodsForLocationLists);
		}

		// ȡ���ѷ�������
		int i = 0;
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			if (disCountArray != null && disCountArray.length == goodsForLocationLists.size()
					&& locationNumArray != null && locationNumArray.length == goodsForLocationLists.size()) {
				goodsForLocationInfo.setOriAmountForDis(disCountArray[i]);
				goodsForLocationInfo.setOriLocIdForDis(locationNumArray[i]);
			} else {
				Map<String, Long> getAmountMap = new HashMap<String, Long>();
				getAmountMap.put("inDepId", inDetailBaseInfo.getInDepId());
				getAmountMap.put("goodsInstanceId", inDetailBaseInfo.getGoodsInstanceId());
				getAmountMap.put("goodsId", inDetailBaseInfo.getGoodsId());
				getAmountMap.put("inDetailId", inDetailBaseInfo.getInDetailId());
				getAmountMap.put("locId", goodsForLocationInfo.getLocationId());
				Long oriAmountForDis = prodRelationInManager.getShoppingAndCheckDistributedAmount(getAmountMap);
				goodsForLocationInfo.setOriAmountForDis(oriAmountForDis == null ? "" : String.valueOf(oriAmountForDis));
			}
			i = i + 1;
		}

		return "/storage/inDepositoryOpt";
	}

	/**
	 * ��λ����
	 *
	 * @param id
	 *            Long
	 * @param type
	 *            String
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void inDepositoryForSaleAndShopping(Long id, String type, InDetailBaseInfo inDetailBaseInfo, Model model,
			ServletRequest req) throws Exception {
		String[] locationNumArray = req.getParameterValues("locationNum");
		String[] disCountArray = req.getParameterValues("disCount");
		String[] supplierIdForUpdateArray = req.getParameterValues("supplierIdForUpdate");
		String[] batchNumForUpdateArray = req.getParameterValues("batchNumForUpdate");
		String[] selfCostForUpdateArray = req.getParameterValues("selfCostForUpdate");
		String[] storTypeForUpdateArray = req.getParameterValues("storTypeForUpdate");

		Map mapForUpdate = new HashMap();
		mapForUpdate.put("locationNumArray", locationNumArray);
		mapForUpdate.put("disCountArray", disCountArray);
		// �����ֶ�ֻ���������˻���������ʱ�����ֵ��
		mapForUpdate.put("supplierIdForUpdateArray", supplierIdForUpdateArray);
		mapForUpdate.put("batchNumForUpdateArray", batchNumForUpdateArray);
		mapForUpdate.put("selfCostForUpdateArray", selfCostForUpdateArray);
		mapForUpdate.put("storTypeForUpdateArray", storTypeForUpdateArray);
		mapForUpdate.put("type", type);

		if (disCountArray != null && disCountArray.length > 0 && locationNumArray != null
				&& locationNumArray.length > 0 && disCountArray.length == locationNumArray.length) {
			// ������������
			// 1��֤�Ƿ���ȫ����
			// ʵ�ʿɷ��������
			Long actualCount = 0L;
			InDetail inDetailInfo = inDetailManager.getInDetail(id);
			if (inDetailInfo != null) {
				actualCount = inDetailInfo.getAmount();
			}

			if (EnumInDepository.IN_SALES_CHANGE.getKey().equals(type)
					|| EnumInDepository.IN_SALES_REFUND.getKey().equals(type)) {
				// �����˻���ʱ�����ж��Ƿ�����ͬ�Ŀ������
				if (storTypeForUpdateArray != null && storTypeForUpdateArray.length > 1) {
					String storTypeTmp = "";
					for (String storTypeForCmp : storTypeForUpdateArray) {
						if (StringUtil.isNotBlank(storTypeTmp) && !storTypeTmp.equals(storTypeForCmp)) {
							// �̵���
							model.addAttribute("errorInfo", "������ͱ�����ͬ�ſɷ���");
							break;
						}
						storTypeTmp = storTypeForCmp;
					}
				}
			}
			// ҳ�������������
			Long inputCount = 0L;
			for (int i = 0; i < disCountArray.length; i++) {
				if (!StringUtil.isNumeric(disCountArray[i])) {
					model.addAttribute("errorInfo", "�������������ȷ");
					break;
				}
				if (StringUtil.isNotBlank(disCountArray[i])) {
					if (new Long(disCountArray[i]) > 0 && StringUtil.isNotBlank(locationNumArray[i])) {
						// �жϿ�λ�Ƿ����̵���
						if (depLocationManager.getIsCheckCountById(new Long(locationNumArray[i]),
								EnumDepLocationIsCheck.Y.getKey()) > 0) {
							// �̵���
							model.addAttribute("errorInfo", "�п�λ���̵���");
							break;
						}
						inputCount = inputCount + new Long(disCountArray[i]);
					}
				}
			}
			if (StringUtil.isNotBlank((String) model.asMap().get("errorInfo"))) {
				// �����в�����ֱ����ʾ������Ϣ
			} else if (!actualCount.equals(inputCount)) {
				// ����������������һ�£��޷���������
				model.addAttribute("errorInfo", "������һ�£��޷���������");
			} else {
				// ���
				try {
					inDetailManager.updateInDetailRelationIn(inDetailInfo, mapForUpdate, inDetailBaseInfo);
				} catch (Exception e) {
					throw new Exception("��⴦�����", e);
				}
				model.addAttribute("succFlag", true);
			}
		}
	}

	/**
	 * ��������ѡ������Ʒ (add by fanyj 2010-10-15) TODO
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectStorageGoodsByMap")
	public String selectStorageGoodsByMap(@ModelAttribute("storageQuery") StorageQuery storageQuery, Model model,
			HttpServletRequest request) throws Exception {
		String supplierId = request.getParameter("supplierId");// ��Ӧ��ID
		String locId = request.getParameter("locId");// ��λID
		String storType = request.getParameter("storType");// ��λID

		QueryPage queryPage = new QueryPage(storageQuery);
		Map parMap = queryPage.getParameters();

		parMap.put("supplierId", supplierId);
		parMap.put("locId", locId);

		List<Storage> storageList = storageManager.getStoragesByCondition(parMap);

		Storage storage = new Storage();

		if (storageList != null && storageList.size() > 0) {
			storage = storageList.get(0);
		}

		model.addAttribute("supplierId", supplierId);
		model.addAttribute("locId", locId);
		model.addAttribute("storType", storType);
		model.addAttribute("storageList", storageList);
		model.addAttribute("storage", storage);
		return "/storage/selectStorageGoodsByMap";
	}

	/**
	 * ѡ������Ʒ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectStorageGoods")
	public String selectStorageGoods(@ModelAttribute("storageQuery") StorageQuery storageQuery,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent adminAgent) throws Exception {

		// ��ҳ����ʧ��ѡ����
		String selectGoodsCount = request.getParameter("selectGoodsCount");
		String sIds = request.getParameter("selectedIds");
		String[] goodsIds = request.getParameterValues("sid");
		List<String> selectList = new ArrayList<String>();
		Map<String, String> selectMap = new HashMap<String, String>();
		if (goodsIds != null) {
			// ������ѡ���
			for (String goodsId : goodsIds) {
				sIds += goodsId + ",";
			}
			// ����¾�ѡ��Ķ�PUT��MAP��
			if (sIds != null) {
				String[] seleIds2 = sIds.split(",");
				for (String sid : seleIds2) {
					selectMap.put(sid, sid);
					selectList.add(sid);
				}
			}
		}
		model.addAttribute("selectGoodsCount", selectGoodsCount);
		model.addAttribute("selectedIds", sIds == null ? "" : sIds);
		model.addAttribute("selectList", selectList);
		model.addAttribute("selectMap", selectMap);
		model.addAttribute("queryObject", storageQuery);
		model.addAttribute("categoryManager", categoryManager);
		model.addAttribute("attributeManager", attributeManager);

		List<DepositoryFirst> depositoryFirstList = initDepository(storageQuery, adminAgent, model);
		storageQuery.setDepfirstIds(this.getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			return "/storage/selectStorageGoods";
		}

		// // ����ȫ����һ���ֿ�ID
		// List<DepositoryFirst> depositoryFirstList =
		// depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		// if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
		// return "/storage/selectStorageGoods";
		// }
		// if (StringUtil.isNotBlank(storageQuery.getDepfirstId())) {
		// depositoryList = getDeplistInit(parMap);
		// }
		// if (StringUtil.isNotBlank(storageQuery.getDepId())) {
		// depLocationLists = getLocationsInit(parMap);
		// }

		List<Category> catList = categoryManager.getCatInfoByDepth(1);
		List<Category> twocatList = categoryManager.getCatInfoByDepth(2);
		List<Category> twocatListInit = null;
		if (StringUtil.isNotBlank(storageQuery.getCatCode())) {
			twocatListInit = categoryManager.getRightChildInfoOfTheParent(storageQuery.getCatCode());
		}
		model.addAttribute("catList", catList);
		model.addAttribute("twocatList", twocatList);
		model.addAttribute("twocatListInit", twocatListInit);

		// ���δ�ҳ�治���в�ѯ
		if ("true".equals(request.getParameter("isSearch"))) {
			storageQuery.setConditionTwo("conditionTwo"); // ��conditionOne:û������Ŀ
															// conditionTwo��������Ŀ
			// parMap.put("depType", EnumDepositoryType.COMMON_DEP.getKey());//
			// ֻ��ѯ��ͨ���е���Ʒ
			if (StringUtil.isNotBlank(storageQuery.getTwoCatCode())) {
				{
					List<Category> tempCategoryList = new ArrayList<Category>();
					tempCategoryList = categoryManager.getRightChildInfoOfTheParent(storageQuery.getTwoCatCode());
					if (tempCategoryList.isEmpty()) {
						storageQuery.setConditionOne("conditionOne");
						storageQuery.setConditionTwo("");
					}
				}
			}
			QueryPage query = storageManager.getStoragesByCondition(storageQuery, currPage, pageSize, false, true);
			model.addAttribute("query", query);

		}

		return "/storage/selectStorageGoods";
	}

	//
	// /***************************************************************************
	// * ����ѯ
	// *
	// * @return String �ɹ����
	// * @throws Exception
	// * @author xieyx 2009/07/24
	// */
	// public String searchStock() throws Exception {
	// //����ȫ����һ���ֿ�ID
	// parMap.put("type", EnumDepositoryFirstType.EnumDepositoryFirst_TYPE_N.getKey());
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// if (depositoryFirstList == null||depositoryFirstList.size() == 0) {
	// return SUCCESS;
	// }
	// if (StringUtil.isNotBlank(parMap.get("depfirstId"))) {
	// depositoryList = getDeplistInit(parMap);
	// }
	// if (StringUtil.isNotBlank(parMap.get("depId"))) {
	// depLocationLists = getLocationsInit(parMap);
	// }
	//
	// catList = categoryManager.getCatInfoByDepth(1);
	// twocatList = categoryManager.getCatInfoByDepth(2);
	// if (StringUtil.isNotBlank(parMap.get("catCode"))) {
	// twocatListInit = categoryManager.getRightChildInfoOfTheParent(parMap.get("catCode"));
	// }
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setCurrentPage(currentPage);
	// parMap.put("conditionTwo", "conditionTwo"); //��conditionOne:û������Ŀ conditionTwo��������Ŀ
	// if (StringUtil.isNotBlank(parMap.get("twoCatCode"))) {
	// {
	// List<Category> tempCategoryList = new ArrayList<Category>();
	// tempCategoryList = categoryManager.getRightChildInfoOfTheParent(parMap
	// .get("twoCatCode"));
	// if (tempCategoryList.isEmpty()) {
	// parMap.put("conditionOne", "conditionOne");
	// parMap.remove("conditionTwo");
	// }
	// }
	// }
	// storageList = storageManager.getStoragesByCondition(parMap, page, true);
	// storage = storageManager.getStorageAmountByCondition(parMap);
	// return SUCCESS;
	// }
	//
	// /**
	// * ��浼��
	// *
	// * @return String �ɹ����
	// * @throws Exception
	// * @author xieyx 2009/07/25
	// */
	// public String exportStorage() throws Exception {
	//
	// ActionContext context = ActionContext.getContext();
	// context.put("catList", categoryManager.getCatInfoByDepth(1));
	// depositoryList = depositoryService.getDepositorys();
	//
	// OutputStream outwt = null;
	// HttpServletRequest request = getRequest();
	// try {
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setHeader("Content-disposition", "attachment; filename=storage" + date + ".xls");
	// res.setContentType("application/octet-stream;charset=utf-8");
	//
	// depositoryFirstList = getDepositoryFirstInit(parMap);
	// parMap.put("conditionTwo", "conditionTwo"); //��conditionOne:û������Ŀ conditionTwo��������Ŀ
	// if (StringUtil.isNotBlank(parMap.get("twoCatCode"))) {
	// {
	// List<Category> tempCategoryList = new ArrayList<Category>();
	// tempCategoryList = categoryManager.getRightChildInfoOfTheParent(parMap
	// .get("twoCatCode"));
	// if (tempCategoryList.isEmpty()) {
	// parMap.put("conditionOne", "conditionOne");
	// parMap.remove("conditionTwo");
	// }
	// }
	// }
	// // ȫ��ѯ
	// List<Storage> storageListAll = new ArrayList<Storage>();
	// if(depositoryFirstList == null||depositoryFirstList.size() == 0){
	// return SUCCESS;
	// }
	// storageListAll = storageManager.getStoragesByCondition(parMap, null, true);
	// List<Object[]> storageExportList = new ArrayList<Object[]>();
	// boolean flag = false;
	// AdminUser admin = getLoginAdminUser();
	// List<Role> userRoleList = roleManager.getUserRoleListByUserId(admin.getId());
	// for (Role role : userRoleList) {
	// List<Authority> authorityList = authorityManager.getRoleAuthorityListByRoleId(role
	// .getId());
	// for (Authority authority : authorityList) {
	// if (authority.getName().equals("A_STORAGE_VIEW_USER")) {
	// flag = true;
	// break;
	// }
	// }
	// }
	// if (flag) {
	// String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "һ���ֿ�", "�ֿ�", "��λ", "��Ӧ��",
	// "���", "��;���", "���ÿ��", "����", "�ɱ�����", "���ɱ�", "�̳Ǽ�", "������" };
	// storageExportList.add(title);
	// } else {
	// String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "һ���ֿ�", "�ֿ�", "��λ", "��Ӧ��",
	// "���", "��;���", "���ÿ��", "����" };
	// storageExportList.add(title);
	// }
	// if (storageListAll != null) {
	// for (Storage tmp : storageListAll) {
	// if (tmp.getStorType()!=null&&tmp.getStorType().equals("v")) {
	// tmp.setStorType("�ݹ�");
	// } else {
	// tmp.setStorType("ʵ��");
	// }
	// if (flag) {
	// Goods goodsTmp = this.goodsManager.getGoods(tmp.getGoodsId());
	// if(goodsTmp!=null){
	// tmp.setGoodsPrice(goodsTmp.getGoodsPrice());
	// tmp.setAgentPrice(goodsTmp.getAgentPrice());
	// }
	//
	// Object[] data = { tmp.getCode(), tmp.getInstanceName(),
	// categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
	// attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
	// tmp.getGoodsUnit(), tmp.getDepFirstName() + "",
	// tmp.getDepositoryName() + "", tmp.getDepLocationName() + "",
	// tmp.getSupplierName() + "", tmp.getStorageNumSum(),
	// tmp.getWayNum(), tmp.getInstanceExistNum(),
	// tmp.getStorType() + "",DoubleUtil.round(tmp.getAveragePrice(), 2),
	// DoubleUtil.round(tmp.getStorageCost(), 2),DoubleUtil.round(tmp.getGoodsPrice(), 2),
	// DoubleUtil.round(tmp.getAgentPrice(), 2)};
	// storageExportList.add(data);
	// } else {
	// Object[] data = { tmp.getCode(), tmp.getInstanceName(),
	// categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
	// attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
	// tmp.getGoodsUnit(), tmp.getDepFirstName() + "",
	// tmp.getDepositoryName() + "", tmp.getDepLocationName() + "",
	// tmp.getSupplierName() + "", tmp.getStorageNumSum(),
	// tmp.getWayNum(), tmp.getInstanceExistNum(),
	// tmp.getStorType() + "" };
	// storageExportList.add(data);
	// }
	// }
	// }
	// goodsBatch.exportExcelByObject(outwt, storageExportList);
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
	// * ���ݣ������ѯ
	// *
	// * @author zhangwy
	// * @return
	// * @throws Exception
	// */
	// public String searchStockAge() throws Exception {
	// catList = categoryManager.getCatInfoByDepth(1);
	// twocatList = categoryManager.getCatInfoByDepth(2);
	// if (StringUtil.isNotBlank(parMap.get("catCode"))) {
	// twocatListInit = categoryManager.getRightChildInfoOfTheParent(parMap.get("catCode"));
	// }
	// depositoryList = depositoryService.getDepositorys();
	// depLocationLists = depLocationManager.getAllRightLocations();
	// if (StringUtil.isNotBlank(parMap.get("depId"))) {
	// depLocationListInit = depLocationManager.getRightLocationsByDepositoryId(Long
	// .parseLong(parMap.get("depId")));
	// }
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setCurrentPage(currentPage);
	// parMap.put("conditionTwo", "conditionTwo"); //��conditionOne:û������Ŀ conditionTwo��������Ŀ
	// if (StringUtil.isNotBlank(parMap.get("twoCatCode"))) {
	// {
	// List<Category> tempCategoryList = new ArrayList<Category>();
	// tempCategoryList = categoryManager.getRightChildInfoOfTheParent(parMap
	// .get("twoCatCode"));
	// if (tempCategoryList.isEmpty()) {
	// parMap.put("conditionOne", "conditionOne");
	// parMap.remove("conditionTwo");
	// }
	// }
	// }
	// storage = storageManager.getStorageAmountByCondition(parMap);
	// storageList = storageManager.getStockAgeByCondition(parMap, page);
	// return SUCCESS;
	// }
	//
	// /**
	// * ���ݣ������ѯ
	// *
	// * @param out
	// * @return ���������б�
	// */
	// public String searchZeroStock() throws Exception {
	// ActionContext context = ActionContext.getContext();
	// context.put("catList", categoryManager.getCatInfoByDepth(1));
	// depositoryList = depositoryService.getDepositorys();
	// String flag = getRequest().getParameter("flag");
	// if(flag!=null && flag.equals("true")){
	// storageList = null;
	// context.put("flag", "false");
	// return SUCCESS;
	// }
	// page = new Page();
	// page.setPageSize(pageSize);
	// page.setCurrentPage(currentPage);
	// storageList = storageManager.searchZeroStock(parMap, page);
	// return SUCCESS;
	// }
	//
	// /**
	// * ����Excel����
	// *
	// * @param out
	// */
	// public String exportZeroStorage() throws Exception {
	//
	// ActionContext context = ActionContext.getContext();
	// context.put("catList", categoryManager.getCatInfoByDepth(1));
	// depositoryList = depositoryService.getDepositorys();
	//
	// OutputStream outwt = null;
	// HttpServletRequest request = getRequest();
	// try {
	// HttpServletResponse res = getResponse();
	// Date da = new Date();
	// outwt = res.getOutputStream();
	// SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
	// String date = df.format(da);
	// res.setHeader("Content-disposition", "attachment; filename=storage" + date + ".xls");
	// res.setContentType("application/octet-stream;charset=utf-8");
	//
	// List<Object[]> storageExportList = new ArrayList<Object[]>();
	// String[] title = { "��Ʒ����", "��Ӧ�̲�Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "���", "��;���", "���ÿ��",
	// "��������", "ʱ��" };
	// storageExportList.add(title);
	// // ���ѯ
	// List<Storage> storageListAll = storageManager.searchZeroStock(parMap, null);
	// String flag = getRequest().getParameter("flag");
	// if(flag!=null&&flag.equals("false")){
	// storageListAll = null;
	// }
	// SimpleDateFormat usedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// if (storageListAll != null) {
	// for (Storage tmp : storageListAll) {
	// Object[] data = { tmp.getCode(), tmp.getSupplier_code(), tmp.getInstanceName(),
	// categoryManager.getCatFullNameByCatcode(tmp.getCatCode()),
	// attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()),
	// tmp.getGoodsUnit(), 0, tmp.getWayNum(),
	// tmp.getInstanceExistNum(), DoubleUtil.round(tmp.getSalesSum(), 2),
	// usedf.format(tmp.getGmtModify()) + "" };
	// storageExportList.add(data);
	// }
	// }
	// goodsBatch.exportExcelByObject(outwt, storageExportList);
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
	// * ����˻�ҳ��
	// * @return TODO
	// */
	// public String storageRefundPage() {
	// HttpServletRequest request = getRequest();
	// String ids = request.getParameter("ids");
	// String avPrices = request.getParameter("avPrices");
	// String[] goodsInstanceIds = null;
	// String[] averagePriceArray = null;
	//
	// try {
	// if(StringUtils.isNotBlank(ids)){
	// goodsInstanceIds = ids.split(",");
	// averagePriceArray = avPrices.split(",");
	// }else{
	// goodsInstanceIds = new String[1];
	// goodsInstanceIds[0] = request.getParameter("goodsInstanceId");
	// ids = request.getParameter("goodsInstanceId");
	// averagePriceArray = new String[1];
	// averagePriceArray[0] = request.getParameter("averagePrice");
	// avPrices = request.getParameter("averagePrice");
	// }
	// // ȥ���ظ��ļ�¼
	// Map<String, String> map = new HashMap<String, String>();
	// if(goodsInstanceIds != null && goodsInstanceIds.length > 0){
	// for(int j=0;j<goodsInstanceIds.length;j++){
	// map.put(goodsInstanceIds[j], averagePriceArray[j]);
	// }
	// List keyList = new ArrayList(map.keySet());
	// List valueList = new ArrayList(map.values());
	// goodsInstanceIds = (String[]) keyList.toArray(new String[keyList.size()]);
	// averagePriceArray = (String[]) valueList.toArray(new String[valueList.size()]);
	// ids = StringUtils.join(goodsInstanceIds, ",");
	// avPrices = StringUtils.join(averagePriceArray, ",");
	// }
	// // ѭ����ȡ����¼
	// List<Storage> list;
	// storageList = new ArrayList<Storage>();
	// parMap.put("supplierId", request.getParameter("supplierId"));
	// parMap.put("locId", request.getParameter("locId"));
	// for(int i=0;i<goodsInstanceIds.length;i++){
	// if(StringUtils.isBlank(goodsInstanceIds[i])
	// || !StringUtils.isNumeric(goodsInstanceIds[i])){
	// continue;
	// }
	// parMap.put("goodsInstanceId", goodsInstanceIds[i]);
	// parMap.put("averagePrice", averagePriceArray[i]);
	// // parMap.put("storType", request.getParameter("storType"));
	// list = this.storageManager.getRefundStoragesByMap(parMap, null);
	// storageList.addAll(list);
	// }
	// // ��ȡһЩ������Ϣ
	// if(storageList != null && storageList.size() > 0){
	// storage = storageList.get(0);
	// Depository dp = depLocationManager.getDepositoryByLocationId(storage.getLocId());
	// if (dp != null) {
	// storage.setDepositoryName(dp.getName());
	// if (EnumDepositoryType.OUT_BORROW_DEP.getKey().equals(dp.getType())) {
	// request.setAttribute("message", "waijie");
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// request.setAttribute("supplierId", request.getParameter("supplierId"));
	// request.setAttribute("locId", request.getParameter("locId"));
	// // request.setAttribute("storType", request.getParameter("storType"));
	// request.setAttribute("ids", ids);
	// request.setAttribute("avPrices", avPrices);
	// request.setAttribute("reasonSet", enumShoppingRefundReason.entrySet());
	// request.setAttribute("errorInfo", request.getParameter("errorInfo"));
	// return SUCCESS;
	// }
	//
	//
	// /**
	// * ����˻���Ҫ�ж��˻���������Ƿ���δ��ɵ�
	// * fangqing
	// * @return
	// */
	// public String validateRefund(){
	// HttpServletRequest request = getRequest();
	// String storageId = request.getParameter("param1");
	// boolean isExist = false;
	// if(storageId!=null && storageId.length()>0){
	// int count = storageRefundApplyManager.getStorageRefundApplyCountByStorageId(storageId);
	// if(count > 0){
	// isExist = true;
	// }
	// }
	// if(isExist){
	// message = "isExist";
	// }
	// return SUCCESS;
	//
	// }
	//


	public static void close(OutputStream out) {

		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	//
	// public void setadminService(adminService adminService) {
	// this.adminService = adminService;
	// }
	//
	/**
	 * ��ⵥ������ܲ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gatherFinanceInDepository")
	public String gatherFinanceInDepository(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {

		if (StringUtil.isBlank(financeDepositoryQuery.getStartTime())
				&& StringUtil.isBlank(financeDepositoryQuery.getEndTime())) {
			financeDepositoryQuery.setStartTime(DateUtil.getDiffMon(new Date(), -1));
			financeDepositoryQuery.setEndTime(DateUtil.getDateToString(new Date()));
		}

		// ȡ�÷�����������������
		GatherInDepository countList = inDetailManager.gatherFinanceInDepositoryCount(financeDepositoryQuery);
		model.addAttribute("count", countList.getCount());
		model.addAttribute("totalSum", countList.getTotalSum());

		QueryPage query = inDetailManager.gatherFinanceInDepositoryLists(financeDepositoryQuery, currPage, pageSize);
		model.addAttribute("query", query);
		model.addAttribute("queryObject", financeDepositoryQuery);
		return "/storage/gatherFinanceInDepository";
	}

	@RequestMapping(value = "/exportFinanceGetherInDep")
	public void exportFinanceGetherInDep(
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
			res.setHeader("Content-disposition", "attachment; filename=GetherFinanceInDepository_" + date + ".xls");
			GatherInDepository countList = inDetailManager.gatherFinanceInDepositoryCount(financeDepositoryQuery);
			QueryPage queryPage = inDetailManager.gatherFinanceInDepositoryLists(financeDepositoryQuery, 1,
					Integer.MAX_VALUE);
			List<GatherInDepository> gatherInDepositoryLists = (List<GatherInDepository>) queryPage.getItems();
			String[] sum = { " ", "", "����: " + countList.getCount().intValue() + "��",
					"�ܽ��:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "Ԫ" };
			gatherInDepList.add(sum);
			String[] title = { "���ݱ��", "���ʱ��", "������", "����״̬" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					String financeStatus = "δȷ��";
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					if ("y".equals(obj.getFinanceStatus())) {
						financeStatus = "��ȷ��";
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtInDep()),
							obj.getSumMoney() + "", financeStatus };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "����ʧ�ܣ�");
			// log.error(e);
		} finally {
			// close(outwt);
			outwt.close();
		}
	}

	//
	// /**
	// * Json����һ���ֿ��ȡȫ����Ч�ֿ�
	// */
	// public String selectDep() {
	// String depfirstId = getRequest().getParameter("param");
	// if (StringUtil.isNotEmpty(depfirstId)) {
	// selectdeplist = depositoryService.getDeplistByFirstDepId(Long.parseLong(depfirstId));
	// } else {
	// selectdeplist = null;
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * Json���ݲֿ��ȡȫ����Ч��λ
	// */
	// public String selectLoc() {
	// String depId = getRequest().getParameter("param");
	// if (StringUtil.isNotEmpty(depId)) {
	// selectloclist = depLocationManager.getRightLocationsByDepositoryId(Long
	// .parseLong(depId));
	// } else {
	// selectloclist = null;
	// }
	// return SUCCESS;
	// }

	/**
	 * ��ⵥ����ȷ��
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/financeInDepositoryConfirm")
	public @ResponseBody
	String financeInDepositoryConfirm(@RequestParam("param") String id) throws Exception {

		String message = "";
		if (StringUtil.isBlank(id)) {
			message = "nothing";
			return message;
		}
		InDepository inDepositoryDispaly = inDepositoryManager.getInDepository(new Long(id));
		if (inDepositoryDispaly == null) {
			message = "nothing";
			return message;
		} else if (inDepositoryDispaly.getFinanceStatus().equals("y")) {
			message = "already true";
			return message;
		} else {
			inDepositoryDispaly.setFinanceStatus("y");
			inDepositoryManager.editInDepository(inDepositoryDispaly);
			message = "true";
		}
		return message;
	}

	/**
	 * �ݹ���ⵥ������ܲ�ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/estimateFinanceInDepository")
	public String estimateFinanceInDepository(
			@ModelAttribute("financeDepositoryQuery") FinanceDepositoryQuery financeDepositoryQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {

		if (StringUtil.isBlank(financeDepositoryQuery.getStartTime())
				&& StringUtil.isBlank(financeDepositoryQuery.getEndTime())) {
			financeDepositoryQuery.setStartTime(DateUtil.getDiffMon(new Date(), -1));
			financeDepositoryQuery.setEndTime(DateUtil.getDateToString(new Date()));
		}

		// ȡ�÷�����������������
		GatherInDepository countList = inDetailManager.estimateFinanceInDepositoryCount(financeDepositoryQuery);
		model.addAttribute("count", countList.getCount());
		model.addAttribute("totalSum", countList.getTotalSum());

		QueryPage query = inDetailManager.estimateFinanceInDepositoryLists(financeDepositoryQuery, currPage, pageSize);

		model.addAttribute("query", query);
		model.addAttribute("queryObject", financeDepositoryQuery);
		return "/storage/estimateFinanceInDepository";
	}

	/**
	 * �ݹ���ⵥ������ܲ�ѯ����
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportEstimateFinanceIn")
	public void exportEstimateFinanceIn(
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
			res.setHeader("Content-disposition", "attachment; filename=EstimateFinanceInDepository_" + date + ".xls");
			GatherInDepository countList = inDetailManager.estimateFinanceInDepositoryCount(financeDepositoryQuery);
			QueryPage queryPage = inDetailManager.estimateFinanceInDepositoryLists(financeDepositoryQuery, 1,
					Integer.MAX_VALUE);
			List<GatherInDepository> gatherInDepositoryLists = (List<GatherInDepository>) queryPage.getItems();
			String[] sum = { " ", "", "����: " + countList.getCount().intValue() + "��",
					"�ܽ��:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "Ԫ" };
			gatherInDepList.add(sum);
			String[] title = { "���ݱ��", "���ʱ��", "������", "����״̬" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					String financeStatus = "δȷ��";
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					if ("y".equals(obj.getFinanceStatus())) {
						financeStatus = "��ȷ��";
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtInDep()),
							obj.getSumMoney() + "", financeStatus };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "����ʧ�ܣ�");
			// log.error(e);
		} finally {
			// close(outwt);
			outwt.close();
		}
	}

	// =====================================================================================================================================
	/*
	 * ��ʼ���ֿ���Ϣ
	 *
	 * @param moveStorageQuery
	 *
	 * @param adminAgent
	 *
	 * @param model
	 */
	private List<DepositoryFirst> initDepository(StorageQuery storageQuery, AdminAgent adminAgent, Model model) {
		// ����ȫ����һ���ֿ�ID
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		storageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "һ���ֿ��¼Ϊ�գ�");
		} else {
			model.addAttribute("depositoryFirstList", depositoryFirstList);
			if (StringUtils.isNotBlank(storageQuery.getDepfirstId())
					&& StringUtils.isNumeric(storageQuery.getDepfirstId())) {
				model.addAttribute("depositoryList",
						depositoryService.getDeplistByFirstDepId(Long.valueOf(storageQuery.getDepfirstId())));
			}
			if (StringUtils.isNotBlank(storageQuery.getDepId()) && StringUtils.isNumeric(storageQuery.getDepId())) {
				model.addAttribute("depLocationLists",
						depLocationManager.getLocationsByDepositoryId(Long.valueOf(storageQuery.getDepId())));
			}
		}
		return depositoryFirstList;
	}
}
