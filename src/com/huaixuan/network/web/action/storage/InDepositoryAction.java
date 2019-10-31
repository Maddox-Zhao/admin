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
	// Spring注入
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

	// //是否显示此页面
	// private Boolean disPage;
	// private GoodsManager goodsManager;
	// private String applyRelationNum;
	// private StorageRefundApplyManager storageRefundApplyManager;
	//
	// // 变量
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
	// private List<Category> catList; //一级类目
	// private List<Category> twocatList; //二级类目
	// private List<Category> twocatListInit; //二级类目联动
	// private Map<String, String> stockAgeMap = EnumStockAge.toMap(); //库龄枚举
	// private Storage storage;
	// private List<DepositoryFirst> depositoryFirstList; //一级仓库列表
	// private List<Depository> selectdeplist; //Json返回的仓库列表
	// private List<DepLocation> selectloclist;
	// private List<GoodsInstance> goodsInstanceList;
	// //Json返回的库位列表
	// private Map<String, String> storTypeMap = EnumStorType.toMap();
	// private String message;
	//

	@Autowired
	private StorageManager storageManager;

	@Autowired
	private StorageRefundApplyManager storageRefundApplyManager;

	/**
	 * 入库单成本汇总统计导出
	 *
	 * @return String 成功标记
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
			// 一级仓库权限过滤
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
			String[] title = { "单据编号", "产品名称", "产品编码", "产品属性", "类型", "入库时间", "单价（￥）", "数量", "金额（￥）" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					if (obj.getStorType() != null && obj.getStorType().equals("v")) {
						obj.setStorType("暂估");
					} else {
						obj.setStorType("实际");
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
	 * 入库单成本汇总统计查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gather_in_depository")
	public String gatherInDepository(@ModelAttribute("gatherQuery") GatherQuery gatherQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam(value = "init", required = false, defaultValue = "") String init,
			AdminAgent adminAgent,Model model) throws Exception {
		// 一级仓库权限过滤
		gatherQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		// 默认查询30天的数据
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
	 * 打印入库单
	 *
	 * @param id
	 *            出库单ID
	 * @param pageNum
	 *            每页显示条数
	 * @param fontSize
	 *            字体大小
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

		// 取得入库单主表信息
		InDepository inDepositoryDispaly = inDepositoryManager.getInDepository(id);
		model.addAttribute("inDepositoryDispaly", inDepositoryDispaly);

		List<ProdRelationIn> prodRelationInLists = prodRelationInManager.getPrintList(id);
		model.addAttribute("prodRelationInLists", prodRelationInLists);

		String buyNick = "";
		String remark = "";
		if (inDepositoryDispaly != null) {
			// 取采购入库的备注信息
			if (EnumInDepository.IN_SHOPPING.getKey().equals(inDepositoryDispaly.getType())) {
				ShoppingList shoppingList = shoppingListService.getShoppingListByShoppingNum(inDepositoryDispaly
						.getRelationNum());
				if (shoppingList != null) {
					remark = shoppingList.getRemark();
				}
			}
			// 取销售退货的昵称信息
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
	 * 入库单查询
	 *
	 * @return String 成功标记
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
		// 取得一级仓库列表
		List<Long> depositoryFirstIds = getDepfirstIdForQuery(agent);
		query.setDepfirstIds(depositoryFirstIds);
		if (depositoryFirstIds == null || depositoryFirstIds.size() == 0) {
			return "/storage/addInDepository";
		}
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(depositoryFirstIds);
		model.addAttribute("depositoryFirstList", depositoryFirstList);

		// 初始化时候，需要设置默认时间
		if (StringUtil.isNotBlank(initFlag) && initFlag.equals("true")) {
			query.setOptTimeStart(DateUtil.getDiffDate(new Date(), -30));
			query.setOptTimeEnd(DateUtil.getDateToString(new Date()));
		}

		// 取得URL中的ID参数，用于取得数据.
		if (StringUtil.isNotBlank(optFlag) && optFlag.equals("exportExcel")) {
			// Excel导出
			exportInDepository(query, res);
			return "/storage/addInDepository";
		}

		// 取得符合条件的数据总数
		// 取得入库单数据
		QueryPage page = inDepositoryManager.getInDepositoryLists(query, currPage, pageSize, true);
		model.addAttribute("page", page);

		// 没有指定供应商查询的情况
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

		// 取得管理员权限的会员列表
		// userLists = userManager.getUserByIsAdmin(1);
		// 这里获取了后台所有用户，这个以后可以按照客户需求来改掉
		List<Admin> userList = adminService.getAdminUserList();
		model.addAttribute("userList", userList);

		// 这里获取了所有的供应商信息
		List<Supplier> supplierList = supplierService.getSupplier();
		model.addAttribute("supplierList", supplierList);

		model.addAttribute("inDepositoryTypeMap", EnumInDepository.toMap());
		model.addAttribute("inDepositoryStatusMap", EnumInStatus.toMap());

		return "/storage/addInDepository";
	}

	/**
	 * 取得List中的一级仓库ID，放入一级仓库名称
	 *
	 * @param inDepositoryList
	 *            List
	 * @author chenyan 2010/03/15
	 */
	private void setDepFirstNameList(List<InDepository> inDepositoryList) {
		// 根据id或者一级仓库名称，重复的id，不查询，直接显示上一次获得的名称
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
			// 检索待导出的数据
			List<InDepository> inDepositoryForExport = (List<InDepository>) inDepositoryManager.getInDepositoryLists(
					query, 0, pageSize, false).getItems();

			// 没有指定供应商查询的情况
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
			// 取得导出excel的时间，用于文件名中
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			res.setHeader("Content-disposition", "attachment; filename=InDepository" + date + ".xls");
			res.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> inDepositoryExportList = new ArrayList<Object[]>();
			String[] title = { "单据编号", "单据类型", "关联单据号", "操作时间", "操作人", "一级仓库", "状态", "总金额", "供应商" };
			inDepositoryExportList.add(title);
			if (inDepositoryForExport != null) {
				for (InDepository tmp : inDepositoryForExport) {
					// zhangwy 增加入库单总金额
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
	 * 根据ID显示入库单详情
	 *
	 * @return String 成功标记
	 * @throws Exception
	 * @author chenyan 2009/07/22
	 */
	@RequestMapping("showInDetail")
	public String showInDetail(@RequestParam("iId") Long id,
			@RequestParam(value = "optType", required = false) String optType,
			@RequestParam(value = "inDepositoryTime", required = false) String gmtInDep, Model model, AdminAgent agent)
			throws Exception {
		// 取得入库单主表信息
		InDepository inDepositoryDispaly = inDepositoryManager.getInDepository(id);

		// 判断是否有显示此页面的权限
		if (!hasDepFirstAuth(agent, inDepositoryDispaly.getDepFirstId())) {
			model.addAttribute("disPage", false);
			return "/storage/showInDetail";
		} else {
			model.addAttribute("disPage", true);
		}

		if (StringUtil.isBlank(gmtInDep) && inDepositoryDispaly.getGmtInDep() == null) {
			model.addAttribute("currDate", new Date());
		}

		// 入库单是否允许分配库位
		model.addAttribute("notAllowEdit", EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus()));

		// 取得入库单详情信息
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

		// 点击完成的操作
		if (StringUtil.isNotBlank(optType) && optType.equals("finishOpt")) {
			// 点击完成的情况
			List<InDetailGoods> inDetailNotFinishList = inDetailManager.listInDetailNotFinish(id,
					EnumInDetailStatus.IN_FINISHED.getKey());
			// 入库时间默认用点击完成时间，不用判断了 zhangwy 2010/11/23
			// if (StringUtil.isBlank(gmtInDep)
			// || DateUtil.strToDate(gmtInDep, "yyyy-MM-dd").getTime() > new Date().getTime()) {
			// // 入库时间不正确
			// errorInfo = "入库时间不正确";
			// } else
			if (inDetailNotFinishList != null && inDetailNotFinishList.size() > 0) {
				// 有产品未分配库位
				String notFinishInstanceName = "";
				for (InDetailGoods inDetailNotFinishInfo : inDetailNotFinishList) {
					if (StringUtil.isBlank(notFinishInstanceName)) {
						notFinishInstanceName = inDetailNotFinishInfo.getInstanceName();
					} else {
						notFinishInstanceName = notFinishInstanceName + "," + inDetailNotFinishInfo.getInstanceName();
					}
				}
				model.addAttribute("errorInfo", "有产品：" + notFinishInstanceName + "  未分配库位");
			} else if (EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus())) {
				// 入库单状态为已完成
				model.addAttribute("errorInfo", "此入库单状态为已完成");
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
					// 完成操作总控
					model.addAttribute("succFlag", inDepositoryManager.addStorageOpt(param));
				} catch (Exception e) {
					// 库位在盘点中的情况
					model.addAttribute("succFlag", false);
					model.addAttribute("errorInfo", "有库位在盘点中或库位已被删除,请重新分配产品");
					model.addAttribute("currDate", DateUtil.strToDate(gmtInDep, "yyyy-MM-dd HH:mm:ss"));
				}

				// 重新取得入库单主表信息，用于原页面显示
				inDepositoryDispaly = inDepositoryManager.getInDepository(id);
				model.addAttribute("notAllowEdit",
						EnumInStatus.IN_FINISHED.getKey().equals(inDepositoryDispaly.getStatus()));
			}
			// currDate = DateUtil.strToDate(gmtInDep, "yyyy-MM-dd HH:mm:ss");
			model.addAttribute("currDate", new Date());
		}

		// 根据ID取得一级仓库名称
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
	 * 产品库位分配操作
	 *
	 * @return String 成功标记
	 * @throws Exception
	 * @author chenyan 2009/07/22
	 */
	@RequestMapping("inDepositoryOpt")
	public String inDepositoryOpt(@RequestParam("optId") Long id, @RequestParam("optType") String type,
			@RequestParam("dfi") Long depFirstId, @RequestParam(value = "selectDepId", required = false) Long depId,
			@RequestParam(value = "actionType", required = false) String actionType, Model model, AdminAgent agent,
			ServletRequest req) throws Exception {
		// 选择的仓库
		if (!hasDepFirstAuth(agent, depFirstId)) {
			throw new AdminDeniedException();
		}

		// 用于页面回显
		model.addAttribute("optId", id);
		model.addAttribute("optType", type);
		model.addAttribute("dfi", depFirstId);
		model.addAttribute("selectDepId", depId);

		String inDepStatus = inDepositoryManager.getInDepositoryStatusByDetailId(id);
		if (StringUtil.isBlank(inDepStatus) || inDepStatus.equals(EnumInStatus.IN_FINISHED.getKey())) {
			// 入库已完成，不可再编辑
			model.addAttribute("notAllowEdit", true);
			model.addAttribute("inDetailInfoForDisList", inDetailManager.listInDetailForDisByDetailId(id));
		}

		String vmRedirect = "";
		if (StringUtil.isNotBlank(type) && type.equals(EnumInDepository.IN_SHOPPING.getKey())) {
			// 采购入库
			vmRedirect = inDepositoryInShopping(id, type, depFirstId, depId, actionType, model, req);
		} else if (StringUtil.isNotBlank(type)
				&& (type.equals(EnumInDepository.IN_SALES_CHANGE.getKey()) || type
						.equals(EnumInDepository.IN_SALES_REFUND.getKey()))) {
			// 销售退货或者销售换货
			vmRedirect = inDepositoryInSale(id, type, depFirstId, model, req);
		} else if (StringUtil.isNotBlank(type)
				&& (type.equals(EnumInDepository.IN_CHECK_MORE.getKey())
						|| type.equals(EnumInDepository.IN_ZERO_CHECK_MORE.getKey())
						|| type.equals(EnumInDepository.IN_MOVE_STORAGE.getKey())
						|| type.equals(EnumInDepository.IN_BORROW_STORAGE.getKey()) || type
						.equals(EnumInDepository.IN_BACK_STORAGE.getKey()))) {
			// 盘点溢出或者零库存盘盈
			vmRedirect = inDepositoryCheckMore(id, type, depFirstId, depId, actionType, model, req);
		}

		model.addAttribute("storTypeMap", EnumStorType.toMap());
		return vmRedirect;

	}

	/**
	 * 分配销售退换货入库
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
		// 取得基本信息
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getShoppingOrSalesInDetailBaseInfo(id, type);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		if (inDetailBaseInfo != null) {
			DepositoryFirst dfTemp = depositoryFirstManager.getDepositoryById(inDetailBaseInfo.getDepFirstId());
			if (dfTemp != null) {
				inDetailBaseInfo.setDepFirstName(dfTemp.getDepFirstName());
			}
		}

		// 库位分配(必须先取得基本信息)
		inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);

		// 取得数据
		List<GoodsForLocation> goodsForLocationLists = inDetailManager.getSalesLocationForGoods(new Long(id));
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		// depositoryList = depositoryService.getDeplistByFirstDepId(depFirstId);
		// zhangwy 入库不能入外借库
		List<Depository> depositoryList = depositoryService.getRightDeplistByFirstDepId(depFirstId);
		model.addAttribute("depositoryList", depositoryList);

		List<DepLocation> depLocationInfoList;
		if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
			// 有商品和库位关联的时候，取得对应的可用库位信息
			depLocationInfoList = depLocationManager.getCheckLocationInfo(inDetailBaseInfo.getGoodsInstanceId(),
					EnumDepLocationIsCheck.N.getKey(), depFirstId);
		} else {
			// 没有建立商品和库位关联的时候，取得所有库位的可用库位信息
			depLocationInfoList = depLocationManager.listUseabledLocInfo(depFirstId);
		}
		model.addAttribute("depLocationInfoList", depLocationInfoList);

		// 取得可用库位信息

		List filterId = new ArrayList();
		int i = 0;
		// 取得已分配数量
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
							// 当库位仍然可用时候才回显。
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
	 * 分配盘点溢出入库
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

		// 取得基本信息
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getCheckInDetailBaseInfo(id);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		// 组装一级仓库和库存类型到基本信息
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
			// 基本信息不正确，跳转到错误页面
			throw new Exception("无法获取采购或者退换货入库时候的基本信息");
		}

		// 获取入库单类型 add by zhangwy 2010-11-3
		if (!EnumInDepository.IN_ZERO_CHECK_MORE.getKey().equals(type)) {
			if (StringUtils.isNotBlank(actionType)) {
				locationNumArray = req.getParameterValues("locationNum");
				disCountArray = req.getParameterValues("disCount");

				// 库位分配(必须先取得基本信息)
				inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);
			}
		}
		// // 增加选择仓库过滤库位的需求 add by fanyj 2010-10-12
		// if(StringUtils.isNotBlank(actionType)){
		// locationNumArray = request.getParameterValues("locationNum");
		// disCountArray = request.getParameterValues("disCount");
		//
		// // 库位分配(必须先取得基本信息)
		// inDepositoryForSaleAndShopping(id, type);
		// }

		DepLocation depLocation = depLocationManager.getDepLocationByLocationId(inDetailBaseInfo.getOriLocationId());
		Depository depository = depositoryService.getDepository(depLocation.getDepId());

		// 次品库入库分配库位限制,只能是他自己原来的仓库和库位 zhangwy
		List<GoodsForLocation> goodsForLocationLists;
		if (EnumDepositoryType.DEFECT_DEP.getKey().equals(depository.getType())) {
			goodsForLocationLists = inDetailManager.getLocationForDefect(new Long(id), depLocation.getId());
		} else {
			if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
				// 有商品和库位关联的时候，取得对应的可用库位信息
				goodsForLocationLists = inDetailManager.getLocationForGoods(new Long(id), depFirstId);
			} else {
				// 没有建立商品和库位关联的时候，取得所有库位的可用库位信息
				goodsForLocationLists = inDetailManager.getLocationForGoodsNoMatch(new Long(id), depFirstId);
			}
		}
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		List<GoodsForLocation> lists = new ArrayList<GoodsForLocation>();
		// 通过选择仓库过滤数据
		Map depMap = new HashMap();
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			// 取得仓库信息
			depMap.put(goodsForLocationInfo.getDepId(), goodsForLocationInfo.getDepName());
			// 通过所选仓库过滤数据集合
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
			goodsForLocationLists = lists;// 将过滤后的集合返回
		}
		// 取得已分配数量
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
	 * 分配采购入库
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

		// 取得一级仓库和库存类型
		InDetailBaseInfo shoppingListInfo = inDetailManager.getInfoByInDetailIdForShoppingList(id);
		// 取得基本信息
		InDetailBaseInfo inDetailBaseInfo = inDetailManager.getShoppingOrSalesInDetailBaseInfo(id, type);
		model.addAttribute("inDetailBaseInfo", inDetailBaseInfo);

		// 组装一级仓库和库存类型到基本信息
		if (shoppingListInfo != null && inDetailBaseInfo != null) {
			inDetailBaseInfo.setDepFirstId(shoppingListInfo.getDepFirstId());
			inDetailBaseInfo.setDepFirstName(shoppingListInfo.getDepFirstName());
			inDetailBaseInfo.setStorType(shoppingListInfo.getStorType());
		}
		if (inDetailBaseInfo == null) {
			throw new Exception("无法获取采购或者退换货入库时候的基本信息");
		}

		// 增加选择仓库过滤库位的需求 add by fanyj 2010-10-12
		if (StringUtils.isNotBlank(actionType)) {
			locationNumArray = req.getParameterValues("locationNum");
			disCountArray = req.getParameterValues("disCount");

			// 库位分配(必须先取得基本信息)
			inDepositoryForSaleAndShopping(id, type, inDetailBaseInfo, model, req);
		}

		List<GoodsForLocation> goodsForLocationLists;
		if (inDetailManager.getGoodsLocationCountByInDetailId(id) > 0) {
			// 有商品和库位关联的时候，取得对应的可用库位信息
			goodsForLocationLists = inDetailManager.getLocationForGoods(id, depFirstId);
		} else {
			// 没有建立商品和库位关联的时候，取得所有库位的可用库位信息
			goodsForLocationLists = inDetailManager.getLocationForGoodsNoMatch(id, depFirstId);
		}
		model.addAttribute("goodsForLocationLists", goodsForLocationLists);

		List<GoodsForLocation> lists = new ArrayList<GoodsForLocation>();
		Map<Long, String> depMap = new HashMap<Long, String>();
		// 通过选择仓库过滤数据
		for (GoodsForLocation goodsForLocationInfo : goodsForLocationLists) {
			// 取得仓库信息
			depMap.put(goodsForLocationInfo.getDepId(), goodsForLocationInfo.getDepName());
			// 通过所选仓库过滤数据集合
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
			goodsForLocationLists = lists;// 将过滤后的集合返回
			model.addAttribute("goodsForLocationLists", goodsForLocationLists);
		}

		// 取得已分配数量
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
	 * 库位分配
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
		// 此俩字段只有在销售退换货的类型时候才有值。
		mapForUpdate.put("supplierIdForUpdateArray", supplierIdForUpdateArray);
		mapForUpdate.put("batchNumForUpdateArray", batchNumForUpdateArray);
		mapForUpdate.put("selfCostForUpdateArray", selfCostForUpdateArray);
		mapForUpdate.put("storTypeForUpdateArray", storTypeForUpdateArray);
		mapForUpdate.put("type", type);

		if (disCountArray != null && disCountArray.length > 0 && locationNumArray != null
				&& locationNumArray.length > 0 && disCountArray.length == locationNumArray.length) {
			// 分配操作的情况
			// 1验证是否完全分配
			// 实际可分配的数量
			Long actualCount = 0L;
			InDetail inDetailInfo = inDetailManager.getInDetail(id);
			if (inDetailInfo != null) {
				actualCount = inDetailInfo.getAmount();
			}

			if (EnumInDepository.IN_SALES_CHANGE.getKey().equals(type)
					|| EnumInDepository.IN_SALES_REFUND.getKey().equals(type)) {
				// 销售退换货时候需判断是否是相同的库存类型
				if (storTypeForUpdateArray != null && storTypeForUpdateArray.length > 1) {
					String storTypeTmp = "";
					for (String storTypeForCmp : storTypeForUpdateArray) {
						if (StringUtil.isNotBlank(storTypeTmp) && !storTypeTmp.equals(storTypeForCmp)) {
							// 盘点中
							model.addAttribute("errorInfo", "库存类型必须相同才可分配");
							break;
						}
						storTypeTmp = storTypeForCmp;
					}
				}
			}
			// 页面输入的数量和
			Long inputCount = 0L;
			for (int i = 0; i < disCountArray.length; i++) {
				if (!StringUtil.isNumeric(disCountArray[i])) {
					model.addAttribute("errorInfo", "分配的数量不正确");
					break;
				}
				if (StringUtil.isNotBlank(disCountArray[i])) {
					if (new Long(disCountArray[i]) > 0 && StringUtil.isNotBlank(locationNumArray[i])) {
						// 判断库位是否在盘点中
						if (depLocationManager.getIsCheckCountById(new Long(locationNumArray[i]),
								EnumDepLocationIsCheck.Y.getKey()) > 0) {
							// 盘点中
							model.addAttribute("errorInfo", "有库位在盘点中");
							break;
						}
						inputCount = inputCount + new Long(disCountArray[i]);
					}
				}
			}
			if (StringUtil.isNotBlank((String) model.asMap().get("errorInfo"))) {
				// 不进行操作，直接显示错误消息
			} else if (!actualCount.equals(inputCount)) {
				// 输入错误或者数量不一致，无法更新数据
				model.addAttribute("errorInfo", "数量不一致，无法更新数据");
			} else {
				// 入库
				try {
					inDetailManager.updateInDetailRelationIn(inDetailInfo, mapForUpdate, inDetailBaseInfo);
				} catch (Exception e) {
					throw new Exception("入库处理出错", e);
				}
				model.addAttribute("succFlag", true);
			}
		}
	}

	/**
	 * 根据条件选择库存商品 (add by fanyj 2010-10-15) TODO
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectStorageGoodsByMap")
	public String selectStorageGoodsByMap(@ModelAttribute("storageQuery") StorageQuery storageQuery, Model model,
			HttpServletRequest request) throws Exception {
		String supplierId = request.getParameter("supplierId");// 供应商ID
		String locId = request.getParameter("locId");// 库位ID
		String storType = request.getParameter("storType");// 库位ID

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
	 * 选择库存商品
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectStorageGoods")
	public String selectStorageGoods(@ModelAttribute("storageQuery") StorageQuery storageQuery,
			HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent adminAgent) throws Exception {

		// 翻页不丢失已选择项
		String selectGoodsCount = request.getParameter("selectGoodsCount");
		String sIds = request.getParameter("selectedIds");
		String[] goodsIds = request.getParameterValues("sid");
		List<String> selectList = new ArrayList<String>();
		Map<String, String> selectMap = new HashMap<String, String>();
		if (goodsIds != null) {
			// 增加新选择的
			for (String goodsId : goodsIds) {
				sIds += goodsId + ",";
			}
			// 最后将新旧选择的都PUT到MAP中
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

		// // 加入全部的一级仓库ID
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

		// 初次打开页面不进行查询
		if ("true".equals(request.getParameter("isSearch"))) {
			storageQuery.setConditionTwo("conditionTwo"); // 分conditionOne:没有子类目
															// conditionTwo：有子类目
			// parMap.put("depType", EnumDepositoryType.COMMON_DEP.getKey());//
			// 只查询普通库中的商品
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
	// * 库存查询
	// *
	// * @return String 成功标记
	// * @throws Exception
	// * @author xieyx 2009/07/24
	// */
	// public String searchStock() throws Exception {
	// //加入全部的一级仓库ID
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
	// parMap.put("conditionTwo", "conditionTwo"); //分conditionOne:没有子类目 conditionTwo：有子类目
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
	// * 库存导出
	// *
	// * @return String 成功标记
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
	// parMap.put("conditionTwo", "conditionTwo"); //分conditionOne:没有子类目 conditionTwo：有子类目
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
	// // 全查询
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
	// String[] title = { "产品编码", "产品名称", "类目", "属性", "单位", "一级仓库", "仓库", "库位", "供应商",
	// "库存", "在途库存", "可用库存", "类型", "成本均价", "库存成本", "商城价", "代销价" };
	// storageExportList.add(title);
	// } else {
	// String[] title = { "产品编码", "产品名称", "类目", "属性", "单位", "一级仓库", "仓库", "库位", "供应商",
	// "库存", "在途库存", "可用库存", "类型" };
	// storageExportList.add(title);
	// }
	// if (storageListAll != null) {
	// for (Storage tmp : storageListAll) {
	// if (tmp.getStorType()!=null&&tmp.getStorType().equals("v")) {
	// tmp.setStorType("暂估");
	// } else {
	// tmp.setStorType("实际");
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
	// request.setAttribute("errorMessage", "导出失败！");
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 内容：库龄查询
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
	// parMap.put("conditionTwo", "conditionTwo"); //分conditionOne:没有子类目 conditionTwo：有子类目
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
	// * 内容：零库存查询
	// *
	// * @param out
	// * @return 返回零库存列表
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
	// * 零库存Excel导出
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
	// String[] title = { "产品编码", "供应商产品编码", "产品名称", "类目", "属性", "单位", "库存", "在途库存", "可用库存",
	// "已售数量", "时间" };
	// storageExportList.add(title);
	// // 零查询
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
	// request.setAttribute("errorMessage", "导出失败！");
	// log.error(e);
	// } finally {
	// close(outwt);
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 库存退货页面
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
	// // 去除重复的记录
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
	// // 循环获取库存记录
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
	// // 抽取一些公用信息
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
	// * 库存退货需要判断退货申请表里是否有未完成的
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
	 * 入库单财务汇总查询
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

		// 取得符合条件的数据总数
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
			String[] sum = { " ", "", "汇总: " + countList.getCount().intValue() + "笔",
					"总金额:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "元" };
			gatherInDepList.add(sum);
			String[] title = { "单据编号", "入库时间", "金额（￥）", "财务状态" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					String financeStatus = "未确认";
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					if ("y".equals(obj.getFinanceStatus())) {
						financeStatus = "已确认";
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtInDep()),
							obj.getSumMoney() + "", financeStatus };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "导出失败！");
			// log.error(e);
		} finally {
			// close(outwt);
			outwt.close();
		}
	}

	//
	// /**
	// * Json根据一级仓库获取全部有效仓库
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
	// * Json根据仓库获取全部有效库位
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
	 * 入库单财务确认
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
	 * 暂估入库单财务汇总查询
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

		// 取得符合条件的数据总数
		GatherInDepository countList = inDetailManager.estimateFinanceInDepositoryCount(financeDepositoryQuery);
		model.addAttribute("count", countList.getCount());
		model.addAttribute("totalSum", countList.getTotalSum());

		QueryPage query = inDetailManager.estimateFinanceInDepositoryLists(financeDepositoryQuery, currPage, pageSize);

		model.addAttribute("query", query);
		model.addAttribute("queryObject", financeDepositoryQuery);
		return "/storage/estimateFinanceInDepository";
	}

	/**
	 * 暂估入库单财务汇总查询导出
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
			String[] sum = { " ", "", "汇总: " + countList.getCount().intValue() + "笔",
					"总金额:" + MoneyUtil.getFormatMoney((countList.getTotalSum()), "0.00") + "元" };
			gatherInDepList.add(sum);
			String[] title = { "单据编号", "入库时间", "金额（￥）", "财务状态" };
			gatherInDepList.add(title);
			if (gatherInDepositoryLists != null) {
				for (GatherInDepository obj : gatherInDepositoryLists) {
					String financeStatus = "未确认";
					if (obj.getSumMoney() == null) {
						obj.setSumMoney(new Double(0.00));
					}
					if ("y".equals(obj.getFinanceStatus())) {
						financeStatus = "已确认";
					}
					String[] data = { obj.getBillNum(), DateUtil.getDateToString(obj.getGmtInDep()),
							obj.getSumMoney() + "", financeStatus };
					gatherInDepList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, gatherInDepList);
			outwt.flush();
		} catch (Exception e) {
			// request.setAttribute("errorMessage", "导出失败！");
			// log.error(e);
		} finally {
			// close(outwt);
			outwt.close();
		}
	}

	// =====================================================================================================================================
	/*
	 * 初始化仓库信息
	 *
	 * @param moveStorageQuery
	 *
	 * @param adminAgent
	 *
	 * @param model
	 */
	private List<DepositoryFirst> initDepository(StorageQuery storageQuery, AdminAgent adminAgent, Model model) {
		// 加入全部的一级仓库ID
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		storageQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "一级仓库记录为空！");
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
