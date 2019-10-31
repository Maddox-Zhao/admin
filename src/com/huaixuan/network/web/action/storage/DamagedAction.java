/**
 *
 */
package com.huaixuan.network.web.action.storage;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.domain.storage.Damaged;
import com.huaixuan.network.biz.domain.storage.DamagedDetail;
import com.huaixuan.network.biz.domain.storage.DamagedDetailView;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.query.DamagedDetailQuery;
import com.huaixuan.network.biz.domain.storage.query.DamagedQuery;
import com.huaixuan.network.biz.domain.supplier.Supplier;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.enums.EnumDamagedReason;
import com.huaixuan.network.biz.enums.EnumDamagedStatus;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.common.CodeManager;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DamagedDetailManager;
import com.huaixuan.network.biz.service.storage.DamagedManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.OutDepositoryManager;
import com.huaixuan.network.biz.service.storage.OutDetailManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.biz.service.supplier.SupplierService;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author fanyj
 */
@Controller
@RequestMapping(value = "/storage")
public class DamagedAction extends BaseAction {

	/**
     *
     */
	private static final long serialVersionUID = 4946660868685947645L;

	/* Spring ע�� */
	@Autowired
	DamagedManager damagedManager;
	@Autowired
	DamagedDetailManager damagedDetailManager;
	@Autowired
	DepositoryService depositoryService;
	@Autowired
	DepLocationManager depLocationManager;
	@Autowired
	CodeManager codeManager;
	@Autowired
	UserManager userManager;
	@Autowired
	SupplierService supplierService;
	@Autowired
	StorageManager storageManager;
	@Autowired
	GoodsInstanceManager goodsInstanceManager;
	@Autowired
	OutDepositoryManager outDepositoryManager;
	@Autowired
	OutDetailManager outDetailManager;
	@Autowired
	CategoryManager categoryManager;
	@Autowired
	AttributeManager attributeManager;
	@Autowired
	GoodsBatchManager goodsBatch;
	@Autowired
	DepositoryFirstManager depositoryFirstManager;
	@Autowired
	AdminService adminService;
	private static final int PAGE_SIZE = 10;
	/*
	 * ����
	 */

	private List<Depository> depositorys;
	private List<Supplier> supplierLists;
	private List<GoodsInstanceSupplier> goodsInstanceSuppliers;
	private Map<String, String> enumDamagedStatus = EnumDamagedStatus.toMap();
	private Map<String, String> enumDamagedReason = EnumDamagedReason.toMap();
	private Map depositoryNameMap = new HashMap();

	/* ���� */
	/**
	 * �������е���ʼҳ��
	 *
	 * @return "success" if no exceptions thrown
	 */
	@RequestMapping(value = "/damaged/addDamagedPage")
	public String addDamagedPage(@ModelAttribute("damaged") Damaged damaged,
			AdminAgent adminAgent, Model model) {

		// ����Ȩ���ڵĵ�һ���ֿ�
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		model.addAttribute("depositoryFirstList", depositoryFirstList);
		// ȡ�ù���ԱȨ�޵Ļ�Ա�б�
		// userLists = userManager.getUserByIsAdmin(1);
		// TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		List<Admin> userList = adminService.getAdminUserList();
		model.addAttribute("userList", userList);
		model.addAttribute("damagedId", codeManager.buildCodeByDateAndType(
				CodeManager.BAOCAN_CODE, 6, BAOCAN));

		return "/storage/damaged/add_damaged_page";
	}

	/**
	 * �������е�������Ϣ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/addDamaged")
	public String addDamaged(@ModelAttribute("damaged") Damaged damaged,
			HttpServletRequest request, Model model) throws Exception {
		String flag = request.getParameter("flag");
		String id = request.getParameter("damagedId");
		damaged.setGmtCreate(new Date());
		damaged.setGmtModify(new Date());
		damaged.setStatus(EnumDamagedStatus.DAM_NEW.getKey());
		Long damagedId;
		if (StringUtil.isNotBlank(flag) && "y".equals(flag)) {
			Map<String, String> parMap = new HashMap<String, String>();
			parMap.put("damagedId", String.valueOf(new Long(id)));
			damaged = damagedManager.getDamaged(parMap);
		} else {
			damagedId = damagedManager.addDamaged(damaged);
		}
		getDepNameMap();
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		model.addAttribute("damaged", damaged);
		return "/storage/damaged/addDamagedGoodsPage";
	}

	public void getDepNameMap() {
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(),
					depositoryFirst.getDepFirstName());
		}
	}

	/**
	 * �������е���Ʒ��ʼҳ��
	 *
	 * @return "success" if no exceptions thrown
	 */
	@RequestMapping(value = "/damaged/addDamagedGoodsPage")
	public String addDamagedGoodsPage(
			@ModelAttribute("damaged") Damaged damaged,
			HttpServletRequest request, Model model) throws Exception {
		String damagedId = request.getParameter("damagedId");
		Map parMap = new HashMap();
		parMap.put("damagedId", String.valueOf(new Long(damagedId)));
		QueryPage queryDamagedDetailViews = null;
		if (null == damagedId) {
			return "/storage/storage_error";
		} else {
			parMap.put("damagedId", String.valueOf(damagedId));
			damaged = damagedManager.getDamaged(parMap);
			if (damaged != null) {
				parMap.put("damagedId", String.valueOf(damagedId));
				queryDamagedDetailViews = damagedDetailManager
						.getDamagedDetailsByParameterMap(parMap, 0, 10);
				getNewLists((List<DamagedDetailView>) queryDamagedDetailViews
						.getItems());
			}
		}
		List<DepLocation> depLocations = depLocationManager
				.getLocationsByDepositoryId(damaged.getDepId());
		model.addAttribute("queryDamagedDetailViews", queryDamagedDetailViews);
		model.addAttribute("depLocations", depLocations);
		model.addAttribute("damNew", EnumDamagedStatus.DAM_NEW.getKey());
		model.addAttribute("damagedId", damagedId);
		model.addAttribute("damaged", damaged);
		getDepNameMap();
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		return "/storage/damaged/addDamagedGoodsPage";
	}

	/**
	 * ��ѯ�����Ʒ
	 *
	 * @return
	 */
	@RequestMapping(value = "/damaged/searchStorageGoods")
	public String searchStorageGoods(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("damagedId");
		String actionType = request.getParameter("actionType");
		String instanceName = request.getParameter("instanceName");
		Long damagedId;
		if (id == null || id.length() == 0) {
			return "/storage/storage_error";
		} else {
			damagedId = Long.parseLong(id);
		}
		model.addAttribute("damagedId", damagedId);
		model.addAttribute("instanceName", instanceName);
		supplierLists = supplierService.getSupplier();
		// �����ѯ�����еĲ�Ʒ���벻Ϊ�ղ�ִ�в�ѯ
		String goodsInstanceId = request.getParameter("goodsInstanceId");
		model.addAttribute("goodsInstanceId", goodsInstanceId);
		GoodsInstance goodsInstance = null;
		Damaged damaged = null;
		List<Storage> storageList = null;
		if (StringUtil.isNotEmpty(goodsInstanceId)) {
			model.addAttribute("reasonSet", enumDamagedReason.entrySet());
			goodsInstance = goodsInstanceManager.getInstance(Long
					.parseLong(goodsInstanceId));
			if (goodsInstance != null) {
				goodsInstance.setCatCode(categoryManager
						.getCatFullNameByCatcode(goodsInstance.getCatCode()));
				goodsInstance
						.setAttrs(attributeManager
								.getFullAttributeStringByAttrs(goodsInstance
										.getAttrs()));
			}
			Map<String, String> parMap = new HashMap<String, String>();
			parMap.put("goodsInstanceId", goodsInstanceId);
			parMap.put("damagedId", String.valueOf(new Long(id)));
			parMap.put("instanceName", instanceName);
			damaged = damagedManager.getDamaged(parMap);
			parMap.put("depId", String.valueOf(damaged.getDepId()));
			storageList = storageManager.getStorageListsByMap(parMap);
			if (storageList != null) {
				for (Storage tmp : storageList) {
					DepLocation dp = depLocationManager
							.getDepLocationByLocationId(tmp.getLocId());
					if (dp != null) {
						tmp.setDepLocationName(dp.getLocName());
						Depository dep = depLocationManager
								.getDepositoryByLocationId(tmp.getLocId());
						if (dep != null) {
							tmp.setDepType(dep.getType());
						}
					}
				}
			}
		}
		model.addAttribute("storageList", storageList);
		model.addAttribute("goodsInstance", goodsInstance);
		model.addAttribute("actionType", actionType);
		return "/storage/damaged/addDamagedGoods";
	}

	/**
	 * �������е���Ʒ��Ϣ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/addDamagedGoods")
	public String addDamagedGoods(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("damagedId");
		String goodsInstanceId = request.getParameter("goodsInstanceId");
		String actionType = request.getParameter("actionType");
		String instanceName = request.getParameter("instanceName");
		Long damagedId;
		Map<String, String> parMap = new HashMap<String, String>();
		Damaged damaged = null;
		if (id == null || id.length() == 0) {
			return "/storage/storage_error";
		} else {
			damagedId = Long.parseLong(id);
			parMap.put("damagedId", String.valueOf(damagedId));
			parMap.put("goodsInstanceId", goodsInstanceId);
			parMap.put("instanceName", instanceName);
			damaged = damagedManager.getDamaged(parMap);
		}
		// �ж��ǲ������������δͨ��״̬
		if (this.checkStatus("edit", damaged)) {
			if ("edit".equals(actionType)) {
				return "redirect:/storage/damaged/editDamagedPage.html?damagedId="
						+ id;
			}
			return "redirect:/storage/damaged/addDamagedGoodsPage.html?damagedId="
					+ id;
		}
		GoodsInstance goodsInstance = null;
		if (goodsInstanceId != null) {
			goodsInstance = goodsInstanceManager.getInstance(Long
					.parseLong(goodsInstanceId));
			if (goodsInstance != null) {
				String[] supplierId = request.getParameterValues("supplierId");// ��Ӧ��ID
				String[] batchNum = request.getParameterValues("batchNum");// ����
				String[] locId = request.getParameterValues("locId");// ��λID
				String[] amount = request.getParameterValues("amount");// �������
				String[] unitCost = request.getParameterValues("unitCost");// ��λ�ɱ�
				String[] costCount = request.getParameterValues("costCount");// �ϼƳɱ�
				String[] reason = request.getParameterValues("reason");// ����ԭ��
				String[] remark = request.getParameterValues("remark");// ����
				DamagedDetail obj = null;
				for (int i = 0; i < supplierId.length; i++) {
					// �жϴ˱��е��Ƿ������˴˱��в�Ʒ
					parMap.put("damagedId", damagedId.toString());
					parMap.put("goodsInstanceId", goodsInstance.getId()
							.toString());
					parMap.put("supplierId", supplierId[i]);
					parMap.put("locId", locId[i]);
					parMap.put("batchNum", batchNum[i]);
					int count = damagedDetailManager
							.getCountByParameterMap(parMap);
					// ���ӱ��в�Ʒ
					if (count == 0 && StringUtil.isNotEmpty(amount[i])
							&& StringUtil.isNumeric(amount[i])
							&& Long.parseLong(amount[i]) > 0) {
						obj = new DamagedDetail();
						obj.setDamagedId(damagedId);
						obj.setGoodsId(goodsInstance.getGoodsId());
						obj.setGoodsInstanceId(goodsInstance.getId());
						obj.setGoodsName(goodsInstance.getInstanceName());
						obj.setUnit(goodsInstance.getGoodsUnit());
						obj.setAmount(Long.parseLong(amount[i]));
						obj.setSupplierId(supplierId[i]);
						obj.setBatchNum(batchNum[i]);
						obj.setLocId(Long.parseLong(locId[i]));
						obj.setUnitCost(Double.parseDouble(unitCost[i]));
						obj.setCostCount(Double.parseDouble(costCount[i]));
						obj.setReason(reason[i]);
						obj.setRemark(remark[i]);
						damagedDetailManager.addDamagedDetail(obj);
					}
				}
			}
		} else {
			return "/storage/storage_error";
		}
		if ("edit".equals(actionType)) {
			return "redirect:/storage/damaged/editDamagedPage.html?damagedId="
					+ id;
		}
		return "redirect:/storage/damaged/addDamagedGoodsPage.html?damagedId="
				+ id;
	}

	/**
	 * ��ѯ���е��б� TODO
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/list_damaged")
	public String searchDamagedList(
			@ModelAttribute("damagedQuery") DamagedQuery damagedQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			AdminAgent adminAgent, Model model) throws Exception {

		if (StringUtil.isBlank(damagedQuery.getDamagedTimeStart())
				&& StringUtil.isBlank(damagedQuery.getDamagedTimeEnd())) {
			damagedQuery.setDamagedTimeStart(DateUtil.getDiffDate(new Date(),
					-30));
			damagedQuery
					.setDamagedTimeEnd(DateUtil.getDateToString(new Date()));
		}

		// ����Ȩ���ڵĵ�һ���ֿ�
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "û��һ���ֿ��ѯȨ�ޣ�");
			return "/storage/storage_error";
		}
		model.addAttribute("depositoryFirstList", depositoryFirstList);

		if (StringUtil.isBlank(damagedQuery.getDepFirstId())) {
			damagedQuery.setDepFirstIds(getDepfirstIdForQuery(adminAgent));
		}

		QueryPage query = damagedManager.getDamagedListsByParameterMap(
				damagedQuery, currPage, PAGE_SIZE);
		if (query != null) {
			model.addAttribute("query", query);
		}
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(),
					depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		// userLists = userManager.getUserByIsAdmin(1);
		// TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		model.addAttribute("userList", adminService.getAdminUserList());
		model.addAttribute("damagedQuery", damagedQuery);
		return "/storage/damaged/list_damaged";
	}

	/**
	 * ��ѯ���е�����
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/searchDamagedDetail")
	public String searchDamagedDetail(HttpServletRequest request, Model model)
			throws Exception {
		String message = request.getParameter("message");
		String id = request.getParameter("damagedId");
		String damagedCode = request.getParameter("damagedCode");
		String actionType = request.getParameter("actionType");

		getDepNameMap();
		model.addAttribute("enumDamagedReason", enumDamagedReason);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		Map<String, String> parMap = new HashMap<String, String>();
		Damaged damaged = null;
		if (damagedCode != null) {
			parMap.put("damagedCode", damagedCode);
			damaged = damagedManager.getDamaged(parMap);
		} else if (id != null && id.length() > 0) {
			parMap.put("damagedId", String.valueOf(new Long(id)));
			damaged = damagedManager.getDamaged(parMap);
		} else {
			return "/storage/storage_error";
		}
		QueryPage damagedDetailViews = null;
		if (damaged != null) {
			parMap.put("damagedId", String.valueOf(damaged.getId()));
			damagedDetailViews = damagedDetailManager
					.getDamagedDetailsByParameterMap(parMap, 0, 10);
			getNewLists((List<DamagedDetailView>) damagedDetailViews.getItems());
		}
		model.addAttribute("damagedDetailViews", damagedDetailViews);
		model.addAttribute("damNew", EnumDamagedStatus.DAM_NEW.getKey());
		model.addAttribute("damWaitCheck",
				EnumDamagedStatus.DAM_WAIT_CHECK.getKey());
		model.addAttribute("damChecked", EnumDamagedStatus.DAM_CHECKED.getKey());
		model.addAttribute("damNoPassChecked",
				EnumDamagedStatus.DAM_NO_PASS_CHECKED.getKey());
		model.addAttribute("damaged", damaged);
		model.addAttribute("message", message);
		if ("check".equals(actionType)) {
			return "/storage/damaged/searchDamagedDetail";
		}
		return "/storage/damaged/searchDamagedDetail";
	}

	/**
	 * �༭���е���ʼ��ҳ��
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/editDamagedPage")
	public String editDamagedPage(HttpServletRequest request, Model model)
			throws Exception {
		String id = request.getParameter("damagedId");
		Long damagedId = null;
		if (damagedId == null || damagedId.intValue() <= 0) {
			damagedId = Long.parseLong(id);
		}
		model.addAttribute("enumDamagedReason", enumDamagedReason);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("damagedId", String.valueOf(damagedId));
		Damaged damaged = damagedManager.getDamaged(parMap);
		QueryPage damagedDetailViews = null;
		if (damaged != null) {
			parMap.put("damagedId", String.valueOf(damagedId));
			damagedDetailViews = damagedDetailManager
					.getDamagedDetailsByParameterMap(parMap, 0, 10);
			getNewLists((List<DamagedDetailView>) damagedDetailViews.getItems());
			model.addAttribute("damagedDetailViews", damagedDetailViews);
		}
		model.addAttribute("damaged", damaged);
		return "/storage/damaged/editDamaged";
	}

	/**
	 * �༭���е����ԣ�״̬�ȣ�
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/damaged/editDamagedAttribute")
	public String editDamagedAttribute(AdminAgent adminAgent,
			HttpServletRequest request, Model model) throws Exception {
		String message = request.getParameter("message");
		Boolean succFlag = Boolean.FALSE;
		String actionType = request.getParameter("actionType");
		String id = request.getParameter("damagedId");
		Long damagedId = null;
		if (id == null || id.length() == 0) {
			return "storage/storage_error";
		} else {
			damagedId = Long.parseLong(id);
		}
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("damagedId", String.valueOf(damagedId));
		Damaged damaged = damagedManager.getDamaged(parMap);
		if (damaged != null) {
			damaged.setGmtModify(new Date());
			// �ȴ�����״̬
			if ("waitCheck".equals(actionType)) {
				if (this.checkStatus("edit", damaged)) {
					model.addAttribute("message", message);
					return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
							+ id + "&actionType=" + actionType;
				}
				damaged.setStatus(EnumDamagedStatus.DAM_WAIT_CHECK.getKey());
				// �༭����״̬
				damagedManager.editDamaged(damaged);
				succFlag = Boolean.TRUE;
			}
			// ���ͨ��
			else if ("check".equals(actionType)) {
				if (this.checkStatus("check", damaged)) {
					model.addAttribute("message", message);
					return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
							+ id + "&actionType=" + actionType;
				}
				damaged.setStatus(EnumDamagedStatus.DAM_CHECKED.getKey());
				// �༭����״̬
				damagedManager.editDamaged(damaged);
				succFlag = Boolean.TRUE;
			}
			// ���δͨ��
			else if ("noPassCheck".equals(actionType)) {
				if (this.checkStatus("check", damaged)) {
					model.addAttribute("message", message);
					return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
							+ id + "&actionType=" + actionType;
				}
				damaged.setStatus(EnumDamagedStatus.DAM_NO_PASS_CHECKED
						.getKey());
				// �༭����״̬
				damagedManager.editDamaged(damaged);
				succFlag = Boolean.TRUE;
			}
			// �������״̬
			else if ("finished".equals(actionType)) {
				if (this.checkStatus("finish", damaged)) {
					model.addAttribute("message", message);
					return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
							+ id + "&actionType=" + actionType;
				}
				// �޸ı��е�״̬�������������Ʒ�����Ʒ���еĿ������
				parMap.put("damagedId", String.valueOf(damagedId));
				List<DamagedDetailView> damagedDetailViews = damagedDetailManager
						.getDamagedDetails(parMap);
				if (damagedDetailViews != null) {
					// ��鱨�������Ƿ���ڲ�Ʒ�Ŀ��ÿ������
					List<DamagedDetail> damagedDetails = damagedDetailManager
							.getGroupCountListByMap(parMap);
					GoodsInstance goodsInstance = null;
					if (damagedDetails != null && damagedDetails.size() > 0) {
						for (DamagedDetail obj : damagedDetails) {
							goodsInstance = goodsInstanceManager
									.getInstance(obj.getGoodsInstanceId());
							if (goodsInstance != null
									&& goodsInstance.getExistNum() < obj
											.getAmount()) {
								message = "���г�����Ʒ��"
										+ goodsInstance.getInstanceName()
										+ "�����ÿ������("
										+ goodsInstance.getExistNum()
										+ ")С�ڱ�������(" + obj.getAmount() + ")��";
								model.addAttribute("message", message);
								return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
										+ id + "&actionType=" + actionType;
							}
						}
					}

					Map map = new HashMap();
					map.put("billNum", codeManager.buildCodeByDateAndType(
							CodeManager.CHUKU_CODE, 6, CHUKU));
					map.put("damaged", damaged);
					map.put("userName", adminAgent.getUsername());
					map.put("damagedDetailViews", damagedDetailViews);
					succFlag = damagedDetailManager.finishedDamaged(map);
				}
			}
		}
		model.addAttribute("message", message);
		return "redirect:/storage/damaged/searchDamagedDetail.html?damagedId="
				+ id + "&actionType=" + actionType;
	}

	// /**
	// * �༭���е���Ϣ
	// *
	// * @return
	// * @throws Exception
	// */
	// public String editDamaged() throws Exception {
	// damagedId = damaged.getId();
	// damaged.setGmtModify(new Date());
	// damaged.setAgent(this.getLoginAdminUser().getUsername());
	// damagedManager.editDamaged(damaged);
	// return SUCCESS;
	// }

	/**
	 * ɾ��������Ʒ
	 *
	 * @return "success" if no exceptions thrown
	 */
	@RequestMapping(value = "/damaged/deleteDamagedGoods")
	public String deleteDamagedGoods(HttpServletRequest request, Model model) {
		// ��ȡɾ����Ad��id����
		String idstr = request.getParameter("id");
		String damIdStr = request.getParameter("damagedId");
		String actionType = request.getParameter("actionType");
		Long damagedDetailId = null;
		Long damagedId = null;
		Damaged damaged = null;
		if (StringUtil.isNotBlank(idstr)) {
			damagedDetailId = Long.parseLong(idstr);
		} else {
			return "/storage/storage_error";
		}
		Map<String, String> parMap = new HashMap<String, String>();
		if (StringUtil.isNotBlank(damIdStr)) {
			damagedId = Long.parseLong(damIdStr);
			parMap.put("damagedId", String.valueOf(damagedId));
			damaged = damagedManager.getDamaged(parMap);
		} else {
			return "/storage/storage_error";
		}

		if (this.checkStatus("edit", damaged)) {
			return "redirect:/storage/damaged/addDamagedGoodsPage.html?damagedId="
					+ damIdStr;
		}
		// ����ɾ������
		damagedDetailManager.removeDamagedDetail(damagedDetailId);
		if ("add".equals(actionType)) {
			return "redirect:/storage/damaged/addDamagedGoodsPage.html?damagedId="
					+ damIdStr;
		}
		return "redirect:/storage/damaged/editDamagedPage.html?damagedId="
				+ damIdStr;
	}

	/**
	 * ���е���Ʒ��ѯ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/damaged/list_damaged_goods")
	public String damagedDetailSearch(
			@ModelAttribute("damagedDetailQuery") DamagedDetailQuery damagedDetailQuery,
			HttpServletRequest request,
			Model model,
			AdminAgent adminAgent,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		String goodsName = request.getParameter("goodsName");
		String goodsCode = request.getParameter("goodsCode");
		String reason = request.getParameter("reason");
		String damagedTimeStart = request.getParameter("damagedTimeStart");
		String damagedTimeEnd = request.getParameter("damagedTimeEnd");
		String depFirstId = request.getParameter("depFirstId");

		if (StringUtil.isBlank(damagedTimeStart)
				&& StringUtil.isBlank(damagedTimeEnd)) {
			damagedTimeStart = DateUtil.getDiffDate(new Date(), -30);
			damagedTimeEnd = DateUtil.getDateToString(new Date());
		}
		damagedDetailQuery.setGoodsName(goodsName);
		damagedDetailQuery.setGoodsCode(goodsCode);
		damagedDetailQuery.setReason(reason);
		damagedDetailQuery.setDamagedTimeStart(damagedTimeStart);
		damagedDetailQuery.setDamagedTimeEnd(damagedTimeEnd);
		damagedDetailQuery.setDepFirstId(depFirstId);
		model.addAttribute("enumDamagedReason", enumDamagedReason);
		model.addAttribute("enumDamagedStatus", enumDamagedStatus);
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "û��һ���ֿ��ѯȨ�ޣ�");
			return "/storage/storage_error";
		}
		model.addAttribute("depositoryFirstList", depositoryFirstList);
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(),
					depositoryFirst.getDepFirstName());
		}
		model.addAttribute("depositoryNameMap", depositoryNameMap);
		List<Long> depFirst = getDepfirstIdForQuery(adminAgent);
		String[] depFir = new String[depFirst.size()];
		int i = 0;
		for (Long h : depFirst) {
			depFir[i] = h.toString();
			i++;
		}
		damagedDetailQuery.setDepfirstIds(depFirst);
		Map damageParMap = new HashMap();
		damageParMap.put("goodsName", goodsName);
		damageParMap.put("goodsCode", goodsCode);
		damageParMap.put("reason", reason);
		damageParMap.put("damagedTimeStart", damagedTimeStart);
		damageParMap.put("damagedTimeEnd", damagedTimeEnd);
		damageParMap.put("depFirstId", depFirstId);
		damageParMap.put("depfirstIds", depFir);
		QueryPage query = damagedDetailManager.getDamagedDetailsByParameterMap(
				damageParMap, currPage, PAGE_SIZE);
		if (query != null) {
			getNewLists((List<DamagedDetailView>) query.getItems());
			model.addAttribute("query", query);
		}
		model.addAttribute("damagedDetailQuery", damagedDetailQuery);
		return "/storage/damaged/list_damaged_goods";
	}

	/**
	 * ���е�����
	 *
	 * @return String �ɹ����
	 * @throws Exception
	 * @author xieyx 2009/07/25
	 */
	@RequestMapping(value = "/damaged/exportDamagedList")
	public void exportDamagedList(AdminAgent adminAgent,
			@ModelAttribute("damagedQuery") DamagedQuery damagedQuery,
			HttpServletResponse response, Model model) throws Exception {
		OutputStream outwt = null;
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setContentType("application/octet-stream;charset=utf-8");
			List<String[]> storageExportList = new ArrayList<String[]>();
			response.setHeader("Content-disposition",
					"attachment; filename=DamagedList_" + date + ".xls");

			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
					.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
			// if (depositoryFirstList == null || depositoryFirstList.size() ==
			// 0) {
			// return "redirect:/storage/damaged/searchStorageGoods.html";
			// }
			for (DepositoryFirst depositoryFirst : depositoryFirstList) {
				depositoryNameMap.put(depositoryFirst.getId(),
						depositoryFirst.getDepFirstName());
			}
			if (StringUtil.isBlank(damagedQuery.getDepFirstId())) {
				damagedQuery.setDepFirstIds(getDepfirstIdForQuery(adminAgent));
			}
			QueryPage damageds = damagedManager.getDamagedListsByParameterMap(
					damagedQuery, 1, Integer.MAX_VALUE);

			String[] title = { "���е���", "����ʱ��", "һ���ֿ�", "�ֿ�", "״̬", "�Ƶ���",
					"������", "����ʱ��", "�޸�ʱ��" };
			storageExportList.add(title);
			for (DepositoryFirst depositoryFirst : depositoryFirstList) {
				depositoryNameMap.put(depositoryFirst.getId(),
						depositoryFirst.getDepFirstName());
			}
			if (damageds != null) {
				for (Damaged damaged : (List<Damaged>) damageds.getItems()) {
					String[] data = {
							damaged.getDamagedCode(),
							damaged.getGmtDamaged_str(),
							(String) depositoryNameMap.get(damaged
									.getDepfirstId()), damaged.getDepName(),
							enumDamagedStatus.get(damaged.getStatus()),
							damaged.getCreater(), damaged.getAgent(),
							df.format(damaged.getGmtCreate()),
							df.format(damaged.getGmtModify()) };
					storageExportList.add(data);
				}
			}
			goodsBatch.exportExcel(outwt, storageExportList);
			outwt.flush();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "����ʧ�ܣ�");
		} finally {
			outwt.close();
		}
	}

	// public static void close(OutputStream out) {
	//
	// try {
	// if (out != null) {
	// out.close();
	// }
	// } catch (IOException ioe) {
	// // ignore
	// }
	//
	// }

	/**
	 * DWR��ʽ�޸ĵ�λ�ɱ�
	 *
	 * @param damagedDetailId
	 *            �ɹ���Ʒ��ID
	 * @param unitCost
	 *            �µĵ�λ�ɱ�
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/damaged/editDwrUnitCost")
	@ResponseBody
	public String editDwrUnitCost(AdminAgent adminAgent,
			HttpServletRequest request, Model model) {
		String message = "";
		String damagedDetailIdStr = request.getParameter("param1");
		String unitCostStr = request.getParameter("param2");
		Long damagedDetailId = Long.parseLong(damagedDetailIdStr);
		Double unitCost = Double.parseDouble(unitCostStr);
		checkBeforeEdit(damagedDetailId, unitCostStr, adminAgent);
		if (StringUtil.isNotBlank(message)) {
			return message;
		}
		DamagedDetail damagedDetail = damagedDetailManager
				.getDamagedDetailById(damagedDetailId);
		damagedDetail.setId(damagedDetailId);
		damagedDetail.setUnitCost(unitCost);
		damagedDetail.setCostCount(unitCost * damagedDetail.getAmount());
		damagedDetail.setGmtModify(new Date());
		damagedDetailManager.editDamagedDetail(damagedDetail);
		message = "['true','edit success!']";
		return message;
	}

	/**
	 * DWR��ʽ�޸ĺϼƳɱ�
	 *
	 * @param damagedDetailId
	 *            �ɹ���Ʒ��ID
	 * @param costCount
	 *            �µ�ʵ�����
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/damaged/editDwrCostCount")
	@ResponseBody
	public String editDwrCostCount(AdminAgent adminAgent,
			HttpServletRequest request, Model model) {
		String message = "";
		String damagedDetailIdStr = request.getParameter("param1");
		String costCountStr = request.getParameter("param2");
		Long damagedDetailId = Long.parseLong(damagedDetailIdStr);
		Double costCount = Double.parseDouble(costCountStr);
		checkBeforeEdit(damagedDetailId, costCountStr, adminAgent);
		if (StringUtil.isNotBlank(message)) {
			return message;
		}
		DamagedDetail damagedDetail = damagedDetailManager
				.getDamagedDetailById(damagedDetailId);
		damagedDetail.setCostCount(costCount);
		damagedDetail.setGmtModify(new Date());
		damagedDetailManager.editDamagedDetail(damagedDetail);
		message = "['true','edit success!']";
		return message;
	}

	// /**
	// * DWR��ʽ�޸Ĳɹ�����
	// *
	// * @param damagedDetailId �ɹ���Ʒ��ID
	// * @param amount �µĲɹ�����
	// * @return ���ز�����Ϣ
	// */
	// public String editDwrAmount() {
	// message = "";
	// String damagedDetailIdStr = getRequest().getParameter("param1");
	// String amountStr = getRequest().getParameter("param2");
	// Long damagedDetailId = Long.parseLong(damagedDetailIdStr);
	// Long amount = Long.parseLong(amountStr);
	// checkBeforeEdit(damagedDetailId, amountStr);
	// if (StringUtil.isNotBlank(message)) {
	// return SUCCESS;
	// }
	// damagedDetail.setAmount(amount);
	// damagedDetail.setCostCount(amount * damagedDetail.getUnitCost());
	// damagedDetail.setGmtModify(new Date());
	// damagedDetailManager.editDamagedDetail(damagedDetail);
	// message = "['true','edit success!']";
	// return SUCCESS;
	// }
	//
	/*
	 * ���������жϱ��е�״̬ @param type @return
	 */
	private boolean checkStatus(String type, Damaged damaged) {
		// ���Ա༭��״̬�ж�
		String message;
		if ("edit".equals(type)) {
			// �ж��ǲ������������δͨ��״̬
			if (!(EnumDamagedStatus.DAM_NEW.getKey()
					.equals(damaged.getStatus()) || EnumDamagedStatus.DAM_NO_PASS_CHECKED
					.getKey().equals(damaged.getStatus()))) {
				message = "edit";
				return true;
			}
		}
		// ������˵�״̬�ж�
		else if ("check".equals(type)) {
			// �ж��ǲ��ǵȴ����״̬
			if (!(EnumDamagedStatus.DAM_WAIT_CHECK.getKey().equals(damaged
					.getStatus()))) {
				message = "notCheck";
				return true;
			}
		}
		// ���Խ�����״̬�ж�
		else if ("finish".equals(type)) {
			// �ж��ǲ������ͨ��״̬
			if (!(EnumDamagedStatus.DAM_CHECKED.getKey().equals(damaged
					.getStatus()))) {
				message = "notFinish";
				return true;
			}
		}
		return false;
	}

	/*
	 * �޸�ǰ�ļ�� @param damagedDetailId @param costCount
	 */
	private void checkBeforeEdit(Long damagedDetailId, String numberStr,
			AdminAgent adminAgent) {
		String message;
		if (!super.isAdminLoged(adminAgent)) {
			message = "['false','you have not login!']";
		}
		if (damagedDetailId == null || numberStr == null) {
			message = "['false','damagedDetailId and numberStr must be not null!']";
		}
		if (!StringUtil.isNumeric(numberStr.trim())) {
			message = "['false','numberStr must be number!']";
		}
		DamagedDetail damagedDetail = damagedDetailManager
				.getDamagedDetailById(damagedDetailId);
		if (damagedDetail == null) {
			message = "['false','the damagedDetail is not exist!']";
		}
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("damagedId", String.valueOf(damagedDetail.getDamagedId()));
		Damaged damaged = damagedManager.getDamaged(parMap);
		// �жϱ��е��Ƿ����½�״̬
		if (this.checkStatus("edit", damaged)) {
			message = "['false','you can edit it only when the Status of damaged is wrong!']";
		}
	}

	/*
	 * ������װ�õ��ı��в�Ʒ�б� @throws Exception
	 */
	private void getNewLists(List<DamagedDetailView> damagedDetailViews)
			throws Exception {
		if (damagedDetailViews != null) {
			for (DamagedDetailView tmp : damagedDetailViews) {
				tmp.setCatCode(categoryManager.getCatFullNameByCatcode(tmp
						.getCatCode()));
				tmp.setAttrs(attributeManager.getFullAttributeStringByAttrs(tmp
						.getAttrs()));
			}
		}
	}

}
