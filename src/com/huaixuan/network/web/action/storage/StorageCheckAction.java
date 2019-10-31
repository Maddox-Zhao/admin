/**
 * created since 2009-7-22
 */
package com.huaixuan.network.web.action.storage;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.Storage;
import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.domain.storage.StorageCheckDetail;
import com.huaixuan.network.biz.domain.storage.StorageCheckList;
import com.huaixuan.network.biz.domain.storage.query.StorageCheckQuery;
import com.huaixuan.network.biz.domain.storage.query.StorageQuery;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumDepLocationIsCheck;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.enums.EnumStorType;
import com.huaixuan.network.biz.enums.EnumStorageCheckStatus;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.AttributeManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsBatchManager;
import com.huaixuan.network.biz.service.goods.GoodsInstanceManager;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.biz.service.storage.StorageCheckDetailManager;
import com.huaixuan.network.biz.service.storage.StorageCheckListManager;
import com.huaixuan.network.biz.service.storage.StorageCheckManager;
import com.huaixuan.network.biz.service.storage.StorageManager;
import com.huaixuan.network.common.util.CsvWriter;
import com.huaixuan.network.common.util.DateUtil;
import com.huaixuan.network.common.util.DoubleUtil;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author shengyong
 * @version $Id: StorageCheckAction.java,v 0.1 2009-7-22 ����03:58:23 shengyong Exp $
 */
@Controller
@RequestMapping(value = "/storage")
public class StorageCheckAction extends BaseAction {

	private static final long serialVersionUID = -7988242405085055867L;

	@Autowired
	StorageCheckManager storageCheckManager;
	@Autowired
	DepLocationManager depLocationManager;
	@Autowired
	StorageManager storageManager;
	@Autowired
	GoodsInstanceManager goodsInstanceManager;
	@Autowired
	StorageCheckListManager storageCheckListManager;
	@Autowired
	DepositoryService depositoryService;
	@Autowired
	StorageCheckDetailManager storageCheckDetailManager;
	@Autowired
	AttributeManager attributeManager;
	@Autowired
	GoodsBatchManager goodsBatch;
	@Autowired
	DepositoryFirstManager depositoryFirstManager;
	@Autowired
	CategoryManager categoryManager;

	private List<Storage> storageLists = new ArrayList<Storage>();

	private String goodsInsId;

	private List<DepositoryFirst> depositoryFirstList;

	private List<Depository> depositoryList;

	private List<DepLocation> depLocationLists;

	private Map<String, String> storTypeMap = EnumStorType.toMap();

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * ��ѯ����̵��б� TODO
	 *
	 * @param storageCheckQuery
	 * @param currPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_STORAGE_CHECK_VIEW_USER })
	@RequestMapping(value = "/list_storage_check")
	public String searchPage(@ModelAttribute("storageCheckQuery") StorageCheckQuery storageCheckQuery,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, AdminAgent adminAgent,
			Model model) throws Exception {

		// ����ȫ����һ���ֿ�ID
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "û��һ���ֿ�Ȩ��");
			return "/error";
		} else {
			model.addAttribute("depositoryFirstList", depositoryFirstList);
			if (StringUtils.isNotBlank(storageCheckQuery.getDepfirstId())
					&& StringUtils.isNumeric(storageCheckQuery.getDepfirstId())) {
				model.addAttribute("depositoryList",
						depositoryService.getDeplistByFirstDepId(Long.valueOf(storageCheckQuery.getDepfirstId())));
			}
			if (StringUtils.isNotBlank(storageCheckQuery.getDepId())
					&& StringUtils.isNumeric(storageCheckQuery.getDepId())) {
				model.addAttribute("depLocationLists", depLocationManager.getRightLocationsByDepositoryId(Long
						.parseLong(storageCheckQuery.getDepId())));
			}
			storageCheckQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
		}

		if (StringUtil.isBlank(storageCheckQuery.getDateStart()) && StringUtil.isBlank(storageCheckQuery.getDateEnd())) {
			storageCheckQuery.setDateStart(DateUtil.getDiffDate(new Date(), -30));
			storageCheckQuery.setDateEnd(DateUtil.getDateToString(new Date()));
		}

		QueryPage query = storageCheckManager.getStorageChecksByParameterMap(storageCheckQuery, currPage, pageSize);

		List<StorageCheck> storageChecks = (List<StorageCheck>) query.getItems();
		if (storageChecks != null && storageChecks.size() > 0) {
			for (StorageCheck s : storageChecks) {
				if (s != null) {
					DepLocation l = depLocationManager.getDepLocationByLocationId(s.getLocId());
					if (l != null) {
						s.setLocName(l.getLocName());
					}
					Depository d = (Depository) depositoryService.getDepository(s.getDepId());
					if (d != null) {
						s.setDepName(d.getName());
						if (d.getDepFirstId() != null) {
							DepositoryFirst df = depositoryFirstManager.getDepositoryById(new Long(d.getDepFirstId()));
							if (df != null) {
								s.setDepFirstName(df.getDepFirstName());
							}
						}
					}
				}
			}
		}
		model.addAttribute("query", query);
		return "/storage/list_storage_check";
	}

	/**
	 * ��ѯ�̵��嵥
	 *
	 * @param storageCheckQuery
	 * @param isPrint
	 * @param currPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view_storage_check")
	public String viewStoreCheck(@ModelAttribute("storageCheckQuery") StorageCheckQuery storageCheckQuery,
			@RequestParam(value = "checkId", required = false) String checkId,
			@RequestParam(value = "isPrint", required = false) String isPrint,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		model.addAttribute("checkId", checkId);
		boolean isPage = true;
		if (StringUtil.isNotBlank(checkId)) {
			StorageCheck s = (StorageCheck) storageCheckManager.getStorageCheck(new Long(checkId));
			model.addAttribute("checkId", checkId);
			model.addAttribute("storageCheck", s);
			model.addAttribute("attributeManager", attributeManager);
			if (StringUtils.isNotBlank(isPrint)) {
				isPage = false;
			}
			storageCheckQuery.setCheckId(checkId);
			QueryPage queryPage = storageCheckManager.getStorageCheckListsByParameterMap(storageCheckQuery, currPage,
					pageSize, isPage);
			model.addAttribute("query", queryPage);
		}
		if (StringUtil.isNotBlank(isPrint) && StringUtil.isNotEmpty(isPrint)) {
			return "/storage/printStoreCheck";
		}
		return "/storage/view_storage_check";
	}

	// @SuppressWarnings("unchecked")
	// public String updateStorage() throws Exception {
	// HttpServletRequest request = getRequest();
	// String[] storageIds = request.getParameterValues("storageIds");
	// String[] checkNums = request.getParameterValues("checkNums");
	// String checkId = request.getParameter("checkId");
	// String goodsInstanceId = request.getParameter("goodsInstanceId");
	// String locId = request.getParameter("locId");
	// parMap.put("storageIds", storageIds);
	// parMap.put("checkNums", checkNums);
	// parMap.put("checkId", checkId);
	// parMap.put("goodsInstanceId", goodsInstanceId);
	// parMap.put("locId", locId);
	// request.setAttribute("parMap", parMap);
	// storageCheckManager.updateStorage(parMap);
	// return SUCCESS;
	// }
	//
	/**
	 * ������ӯ����
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/zeroStoreCheck")
	public String zeroStoreCheck(AdminAgent adminAgent, Model model, HttpServletRequest request,
			@ModelAttribute("storageQuery") StorageQuery storageQuery) throws Exception {

		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			return "/error";
		}
		String depFirstId = request.getParameter("depFirstId");
		Map parMap = new HashMap();
		if (StringUtil.isBlank(depFirstId)) {
			DepositoryFirst depFirst = (DepositoryFirst) depositoryFirstList.get(0);
			parMap.put("depFirstId", depFirst.getId());
		} else {
			parMap.put("depFirstId", depFirstId);
			storageQuery.setDepfirstId(depFirstId);
		}

		String goodsInsId = request.getParameter("goodsInsId");
		if (StringUtil.isNotBlank(goodsInsId)) {
			parMap.put("goodsInstanceId", goodsInsId);
			List<Storage> storageLists = storageManager.getZeroStorageByParameterMap(parMap);
			for (Storage s : storageLists) {
				DepositoryFirst df = depositoryFirstManager.getDepositoryById(s.getDepFirstId());
				if (df != null) {
					s.setDepFirstName(df.getDepFirstName());
				}
			}
			model.addAttribute("storageLists", storageLists);
		}
		Map<String, String> storTypeMap = EnumStorType.toMap();
		model.addAttribute("goodsInsId", goodsInsId);
		model.addAttribute("depositoryFirstList", depositoryFirstList);
		model.addAttribute("attributeManager", attributeManager);
		model.addAttribute("storTypeMap", storTypeMap);
		return "/storage/zeroStoreCheck";
	}

	//
	/**
	 * ���������ӯ����
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/finishZeroStoreCheck")
	public String finishZeroStoreCheck(HttpServletRequest request, Model model, AdminAgent adminAgent) throws Exception {
		String[] storageIds = request.getParameterValues("storageId");
		String[] checkNums = request.getParameterValues("checkNum");
		String goodsInsId = request.getParameter("goodsInsId");
		boolean isNull = false;
		for (String strNum : checkNums) {
			if (StringUtil.isNotBlank(strNum)) {
				isNull = true;
				break;
			}
		}
		String message = "";
		if (!isNull) {
			model.addAttribute("message", "��������дһ����ӯ������");
			model.addAttribute("goodsInsId", goodsInsId);
			return "redirect:/storage/zeroStoreCheck.html";
		}
		Map parMap = new HashMap();
		parMap.put("storageIds", storageIds);
		parMap.put("checkNums", checkNums);
		parMap.put("creater", adminAgent.getUsername());
		Map resultMap = storageCheckManager.finishZeroStorageCheck(parMap);
		if ("false".equals(resultMap.get("flag"))) {
			message = (String) resultMap.get("errorMsg");
			model.addAttribute("message", message);
			model.addAttribute("goodsInsId", goodsInsId);
			return "redirect:/storage/zeroStoreCheck.html";
		}
		model.addAttribute("message", "��ӯ�����ɹ���");
		return "redirect:/storage/list_zero_storage.html";
	}

	/**
	 * ����̵�
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/finishStoreCheck")
	public String finishStoreCheck(HttpServletRequest request, Model model, AdminAgent adminAgent) throws Exception {
		String checkId = request.getParameter("checkId");
		Map parMap = new HashMap();
		// ��������� zhangwy
		parMap.put("creater", adminAgent.getUsername());
		parMap.put("checkId", checkId);
		request.setAttribute("parMap", parMap);
		parMap.put("storType", "u");
		int uncheck = storageCheckManager.getStorageCheckDetailByCountParameterMap(parMap);
		if (uncheck > 0) {
			model.addAttribute("message", "���������е��̵����ݣ���������̵㣡");
			model.addAttribute("checkId", checkId);
			return "redirect:/storage/detail_storage_check.html";
		}
		parMap.put("storType", null);
		if (StringUtil.isNotBlank(checkId)) {
			StorageCheck sc = storageCheckManager.getStorageCheck(new Long(checkId));
			if (sc == null) {
				model.addAttribute("message", "�̵��¼������!");
				model.addAttribute("checkId", checkId);
				return "redirect:/storage/detail_storage_check.html";
			}
			if (sc.getStatus().equals(EnumStorageCheckStatus.FINDISH.getKey())) {
				model.addAttribute("message", "�����̵��Ѿ���ɣ�����ʧ��!");
				model.addAttribute("checkId", checkId);
				return "redirect:/storage/detail_storage_check.html";
			}

			// �ж��̿�ʱ���ÿ���Ƿ񹻼�
			parMap.put("storType", "k");
			List<StorageCheckDetail> storeDetails = storageCheckManager.getCheckDetailCountByMap(parMap);
			if (storeDetails != null && storeDetails.size() > 0) {
				for (StorageCheckDetail obj : storeDetails) {
					// ����һ���ֿ������жϣ����������һ���ֿ��򲻶Կ��ÿ������ж� zhangwy
					Storage storage = storageManager.getStorage(obj.getStorId());
					DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(storage.getDepFirstId());

					GoodsInstance gi = (GoodsInstance) goodsInstanceManager.getInstance(obj.getGoodsInstanceId());
					if (gi != null) {
						if (!depositoryFirst.getType().equals("w")) {
							if (gi.getExistNum() < obj.getProfitNum()) {
								model.addAttribute("message",
										"�̵�ʧ�ܣ���Ʒ��" + gi.getInstanceName() + "���Ŀ��ÿ������(" + gi.getExistNum() + ")С���̿�����("
												+ obj.getProfitNum() + ")��");
								model.addAttribute("checkId", checkId);
								return "redirect:/storage/detail_storage_check.html";
							}
						}
					} else {
						model.addAttribute("message", "�̵�Ĳ�Ʒ�����ڣ�");
						model.addAttribute("checkId", checkId);
						return "redirect:/storage/detail_storage_check.html";
					}
				}
			}
			parMap.put("storType", null);
			Map resultMap = storageCheckManager.finishStoreCheck(parMap);
			if ("false".equals(resultMap.get("flag"))) {
				model.addAttribute("message", (String) resultMap.get("errorMsg"));
				model.addAttribute("checkId", checkId);
				return "redirect:/storage/detail_storage_check.html";
			}

		}
		model.addAttribute("checkId", checkId);
		return "redirect:/storage/view_storage_check.html";
	}

	/**
	 * ����checkId��ñ����̵����ϸ
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail_storage_check")
	public String storeCheckDetail(@ModelAttribute("storageCheckQuery") StorageCheckQuery storageCheckQuery,
			@RequestParam(value = "checkId", required = false) String checkId,
			@RequestParam(value = "isPrint", required = false) String isPrint,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		model.addAttribute("message", message);
		if (StringUtil.isBlank(checkId)) {
			model.addAttribute("message", "�̵��¼IDΪ�գ�");
			return "/error";
		}
		boolean isPage = true;
		StorageCheck s = (StorageCheck) storageCheckManager.getStorageCheck(new Long(checkId));
		model.addAttribute("checkId", checkId);
		model.addAttribute("storageCheck", s);
		model.addAttribute("attributeManager", attributeManager);
		if (StringUtils.isNotBlank(isPrint)) {
			isPage = false;
		}
		storageCheckQuery.setCheckId(checkId);
		QueryPage queryPage = storageCheckManager.getStorageCheckDetailByParameterMap(storageCheckQuery, currPage,
				pageSize, isPage);
		model.addAttribute("query", queryPage);
		model.addAttribute("checkId", checkId);
		model.addAttribute("storageCheckQuery", storageCheckQuery);
		model.addAttribute("storTypeMap", EnumStorType.toMap());
		if (StringUtils.isNotBlank(isPrint)) {
			return "/storage/printStoreCheckDetail";
		}
		return "/storage/detail_storage_check";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addStorageCheck")
	public String addStorageCheck(AdminAgent adminAgent, Model model, HttpServletRequest request) throws Exception {
		Map parMap = new HashMap();
		// ��òֿ⣬��λ��Ϣ
		String locId = request.getParameter("locId");
		String depId = request.getParameter("depId");

		StorageCheck storageCheck = new StorageCheck();
		if (StringUtil.isNotBlank(depId)) {
			storageCheck.setDepId(new Long(depId));
			storageCheck.setCheckType("d");
			parMap.put("depId", depId);
		}
		if (StringUtil.isNotBlank(locId)) {
			storageCheck.setLocId(new Long(locId));
			storageCheck.setCheckType("l");
			parMap.put("locId", locId);
		}
		storageCheck.setCreater(adminAgent.getUsername());
		storageCheck.setStatus(EnumStorageCheckStatus.START.getKey());

		// parMap.put("status", storageCheck.getStatus());
		// int count =
		// storageCheckManager.getStorageChecksByCountParameterMap(parMap);
		// if (count > 0) {
		// message = "��ǰ��ͬ�����̵�����У�����������";
		// request.setAttribute("message", message);
		// return ERROR;
		// }

		if ("d".equals(storageCheck.getCheckType())) {
			List<DepLocation> deps = depLocationManager.getLocationsByDepositoryId(new Long(depId));
			if (deps == null) {
				model.addAttribute("message", "��λ������");
				return "redirect:/storage/add_storage_check.html";
			}

			for (DepLocation dep : deps) {
				// �����ֿ⣬��λ�ĳ�����¼
				if (dep.getIsCheck().equals(EnumDepLocationIsCheck.Y.getKey())) {
					model.addAttribute("message", "��ǰ��ͬ�����̵�����У�����������");
					return "redirect:/storage/add_storage_check.html";
				}
			}
			// �����̴��¼
			Long checkId = storageCheckManager.addStorageCheck(storageCheck);
			for (DepLocation dep : deps) {

				dep.setIsCheck(EnumDepLocationIsCheck.Y.getKey());
				depLocationManager.lockDepLocation(dep);
				addStorageCheckList(dep.getId(), checkId, new Long(depId));

			}
		}
		if ("l".equals(storageCheck.getCheckType())) {
			DepLocation depl = (DepLocation) depLocationManager.getDepLocationByLocationId(new Long(locId));
			if (depl == null) {
				model.addAttribute("message", "��λ�����ڣ�����ʧ��");
				return "redirect:/storage/add_storage_check.html";
			}
			if (depl.getIsCheck().equals(EnumDepLocationIsCheck.Y.getKey())) {
				model.addAttribute("message", "��ǰ��ͬ�����̵�����У�����������");
				return "redirect:/storage/add_storage_check.html";
			}

			// ���¶�Ӧ�Ĳֿ�id
			storageCheck.setDepId(depl.getDepId());
			// �����̴��¼
			Long checkId = storageCheckManager.addStorageCheck(storageCheck);
			// �����ֿ⣬��λ�ĳ�����¼
			depl.setIsCheck(EnumDepLocationIsCheck.Y.getKey());
			depLocationManager.lockDepLocation(depl);
			addStorageCheckList(new Long(locId), checkId, depl.getDepId());
		}

		return "redirect:/storage/list_storage_check.html";
	}

	@SuppressWarnings("unchecked")
	private void addStorageCheckList(Long locationId, Long checkId, Long depId) {
		List<Long> goodsInstanceIds = storageManager.getGoodsInstanceIdsByLocId(locationId);
		if (goodsInstanceIds != null && goodsInstanceIds.size() > 0) {
			for (int j = 0; j < goodsInstanceIds.size(); j++) {
				// ���Ʒinstance_id
				long goodsInstanceId = goodsInstanceIds.get(j).longValue();
				Map parMap = new HashMap();
				parMap.put("locId", locationId);
				parMap.put("goodsInstanceId", goodsInstanceId);
				Storage s = storageManager.sumStorageResultForCheck(parMap);
				if (s != null && s.getStorageNum() > 0) {
					StorageCheckList checkList = new StorageCheckList();
					checkList.setCheckId(checkId);
					// checkList.setCheckNum(new Long(sum));
					checkList.setDepId(depId);
					checkList.setGoodsInstanceId(goodsInstanceId);
					checkList.setLocId(locationId);
					checkList.setStorNum(s.getStorageNum());
					checkList.setStatus(EnumStorageCheckStatus.START.getKey());
					checkList.setPrice(s.getPrice());
					GoodsInstance gi = (GoodsInstance) goodsInstanceManager.getInstance(goodsInstanceId);
					if (gi != null) {
						checkList.setGoodsId(gi.getGoodsId());
					}
					storageCheckListManager.addStorageCheckList(checkList);
				}
			}

		}
	}

	/**
	 * �����̴���Ϣҳ��
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@AdminAccess({ EnumAdminPermission.A_STORAGE_CHECK_ADD_USER })
	@RequestMapping(value = "/add_storage_check")
	public String addStorCheck(HttpServletRequest request, Model model, AdminAgent adminAgent) throws Exception {

		// ����ȫ����һ���ֿ�ID
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "û��һ���ֿ�Ȩ��");
			return "/error";
		} else {
			model.addAttribute("depositoryFirstList", depositoryFirstList);
		}

		DepositoryQuery query = new DepositoryQuery();

		query.setDepfirstIds(getDepfirstIdForQuery(adminAgent));

		String depFirstId = request.getParameter("depFirstId");

		if (StringUtil.isBlank(depFirstId) && depositoryFirstList != null && depositoryFirstList.size() > 0) {
			DepositoryFirst depFirst = (DepositoryFirst) depositoryFirstList.get(0);
			query.setDepFirstId(depFirst.getId());
		} else {
			query.setDepFirstId(Long.valueOf(depFirstId));
		}

		query.setStatus("v");

		String[] storageType = { EnumDepositoryType.COMMON_DEP.getKey(), EnumDepositoryType.DEFECT_DEP.getKey() };
		query.setTypes(storageType);

		QueryPage queryPage = depositoryService.getDepositorysByParMap(query, 0, pageSize, false);
		model.addAttribute("depositorys", queryPage.getItems());

		QueryPage queryPageTemp = new QueryPage(query);
		Map parMap = queryPageTemp.getParameters();
		if (query.getTypes() != null && query.getTypes().length > 0) {
			parMap.put("types", query.getTypes());
		}
		List<DepLocation> depLocations = depLocationManager.getAllDepLocationByMap(parMap);
		model.addAttribute("depLocations", depLocations);

		model.addAttribute("depFirstId", query.getDepFirstId());
		return "/storage/add_storage_check";
	}

	/**
	 * dwr��ʽ�޸��̴����� TODO
	 *
	 * @param checkListId
	 * @param num
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/editDwrCheckNum")
	public @ResponseBody
	String editDwrCheckNum(@RequestParam(value = "param1", required = false) String checkListId_str,
			@RequestParam(value = "param2", required = false) String num_str, Model model, AdminAgent adminAgent) {
		String message = "";
		if (!this.isAdminLoged(adminAgent)) {
			message = "['false','��û�е�¼!']";
			return message;
		}
		if (checkListId_str == null || num_str == null) {
			message = "['false','�̵��嵥��Ż�����������Ϊ��!']";
			return message;
		}

		StorageCheckList checkList = storageCheckListManager.getStorageCheckList(Long.parseLong(checkListId_str));
		if (checkList == null) {
			message = "['false','�̵��嵥������!']";
			return message;
		}
		if (checkList.getStatus().equals(EnumStorageCheckStatus.FINDISH.getKey())) {
			message = "['false','�Ѿ������̵���ϸ���������޸�!']";
			return message;
		}
		checkList.setCheckNum(Long.parseLong(num_str));
		checkList.setStatus(EnumStorageCheckStatus.NOTDEAL.getKey());
		storageCheckListManager.editStorageCheckList(checkList);
		message = "['true','edit success!','" + checkListId_str + "']";
		return message;
	}

	/**
	 * �����̵���ϸ
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/finishStoreCheckList")
	public String finishStoreCheckList(@ModelAttribute("storageCheckQuery") StorageCheckQuery storageCheckQuery,
			HttpServletRequest request, Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage) {
		String checkId = request.getParameter("checkId");

		storageCheckQuery.setCheckId(checkId);
		storageCheckQuery.setStatus("s");

		QueryPage temp = new QueryPage(storageCheckQuery);
		Map tempMap = temp.getParameters();

		int count = storageCheckManager.getStorageCheckListsByCountParameterMap(tempMap);
		if (count > 0) {
			model.addAttribute("message", "���������е��̵����ݣ���������̵㣡");
			model.addAttribute("checkId", checkId);
			return "redirect:/storage/view_storage_check.html";
		}
		storageCheckQuery.setStatus("n");
		storageCheckQuery.setCheckId(checkId);
		QueryPage query = storageCheckManager.getStorageCheckListsByParameterMap(storageCheckQuery, currPage,
				this.pageSize, false);
		List<StorageCheckList> lists = (List<StorageCheckList>) query.getItems();
		try {
			for (StorageCheckList c : lists) {
				long locId = c.getLocId();
				long goodsInsId = c.getGoodsInstanceId();
				if (c.getCheckNum().longValue() != c.getStorNum().longValue()) {
					storageCheckManager.addStorCheckDetail(locId, goodsInsId, c.getCheckId());

				}
				c.setStatus(EnumStorageCheckStatus.FINDISH.getKey());
				storageCheckListManager.editStorageCheckList(c);
			}

			StorageCheck s = (StorageCheck) storageCheckManager.getStorageCheck(new Long(checkId));
			s.setStatus(EnumStorageCheckStatus.DETAIL.getKey());
			storageCheckManager.editStorageCheck(s);

		} catch (Exception e) {
			model.addAttribute("message", "�����̵���ϸ�������Ժ����ԣ�");
			model.addAttribute("checkId", checkId);
			log.error(e.getMessage());
			return "redirect:/storage/view_storage_check.html";
		}
		model.addAttribute("checkId", checkId);
		return "redirect:/storage/detail_storage_check.html";
	}

	/**
	 * dwr��ʽ�޸��̴���ϸ�̴����� TODO
	 *
	 * @param checkListId
	 * @param num
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/editDwrCheckNumDetail")
	public @ResponseBody
	String editDwrCheckNumDetail(@RequestParam(value = "param1", required = false) String detailId_str,
			@RequestParam(value = "param2", required = false) String num_str, Model model, AdminAgent adminAgent) {
		String message = "";
		if (!this.isAdminLoged(adminAgent)) {
			message = "['false','��û�е�¼!']";
			return message;
		}
		if (detailId_str == null || num_str == null) {
			message = "['false','�̵��Ż����̵���������Ϊ��!']";
			return message;
		}
		Long num = Long.parseLong(num_str);
		// User user = this.getLoginUser();
		// ������Ҫ�ж�����û��Ƿ����̳ǹ���Ա,ֻ���̼Ҳ���Ȩ���޸Ķ������.
		// if (user.getIsAdmin().intValue() != 1) {
		// return "['false','ֻ�й���Ա���ܲ���!']";
		// }
		// TODO
		StorageCheckDetail checkDetail = storageCheckDetailManager.getStorageCheckDetail(Long.parseLong(detailId_str));
		if (checkDetail == null) {
			message = "['false','�̵���ϸ������!']";
			return message;
		}
		StorageCheck sc = storageCheckManager.getStorageCheck(checkDetail.getCheckId());
		if (sc == null) {
			message = "['false','�̵��¼������!']";
			return message;
		}
		if (sc.getStatus().equals(EnumStorageCheckStatus.FINDISH.getKey())) {
			message = "['false','�����̵��Ѿ���ɣ����������!']";
			return message;
		}
		checkDetail.setCheckNum(num);
		long storNum = checkDetail.getStorNumber();
		long checkNum = num.longValue();
		long profitNum = checkNum - storNum;
		if (storNum > checkNum) {

			checkDetail.setStorType("k");
			checkDetail.setProfitNum(0 - profitNum);
		} else if (storNum < checkNum) {
			checkDetail.setStorType("y");
			checkDetail.setProfitNum(profitNum);
		} else {
			checkDetail.setStorType("n");
			checkDetail.setProfitNum(profitNum);
		}
		storageCheckDetailManager.editStorageCheckDetail(checkDetail);
		message = "['true','edit success!','" + detailId_str + "']";
		return message;
	}

	/**
	 * dwr��ʽ�޸��̴���ϸ��ע
	 *
	 * @param checkListId
	 * @param num
	 * @return ���ز�����Ϣ
	 */
	@RequestMapping(value = "/editDwrCheckRemarkDetail")
	public @ResponseBody
	String editDwrCheckRemarkDetail(@RequestParam(value = "param1", required = false) String detailId_str,
			@RequestParam(value = "param2", required = false) String remark, Model model, AdminAgent adminAgent) {
		String message = "";
		if (!this.isAdminLoged(adminAgent)) {
			message = "['false','��û�е�¼!']";
			return message;
		}
		if (detailId_str == null) {
			message = "['false','�̵��Ų���Ϊ��!']";
			return message;
		}
		// User user = this.getLoginUser();
		// ������Ҫ�ж�����û��Ƿ����̳ǹ���Ա,ֻ���̼Ҳ���Ȩ���޸Ķ������.
		// if (user.getIsAdmin().intValue() != 1) {
		// return "['false','ֻ�й���Ա���ܲ���!']";
		// }
		// TODO
		StorageCheckDetail checkDetail = storageCheckDetailManager.getStorageCheckDetail(Long.parseLong(detailId_str));
		if (checkDetail == null) {
			message = "['false','�̵���ϸ������!']";
			return message;
		}
		StorageCheck sc = storageCheckManager.getStorageCheck(checkDetail.getCheckId());
		if (sc == null) {
			message = "['false','�̵��¼������!']";
			return message;
		}
		if (sc.getStatus().equals(EnumStorageCheckStatus.FINDISH.getKey())) {
			message = "['false','�����̵��Ѿ���ɣ����������!']";
			return message;
		}
		checkDetail.setRemark(remark);
		storageCheckDetailManager.editStorageCheckDetail(checkDetail);
		message = "['true','edit success!','" + detailId_str + "']";
		return message;
	}

	/**
	 * ��������̵���
	 *
	 * @param checkId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export_storage_check")
	public String exportStoreCheck(@ModelAttribute("storageCheckQuery") StorageCheckQuery storageCheckQuery,
			@RequestParam(value = "checkId", required = false) String checkId, HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			HttpServletResponse response, Model model, AdminAgent adminAgent) throws Exception {
		OutputStream outwt = null;
		storageCheckQuery.setCheckId(checkId);
		try {
			Date da = new Date();
			outwt = response.getOutputStream();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition", "attachment; filename=storageCheck" + date + ".xls");
			response.setContentType("application/octet-stream;charset=utf-8");

			List<Object[]> storageExportList = new ArrayList<Object[]>();
			String[] title = { "�̵���", "��Ʒ����", "��Ʒ����", "����", "�ֿ�����", "��λ����", "�������", "ӯ��", "�ɱ���" };
			storageExportList.add(title);

			if (StringUtil.isNotBlank(checkId)) {
				// ����ȫ����һ���ֿ�ID
				List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
						.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
				if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
					model.addAttribute("message", "û��һ���ֿ�Ȩ��");
					return "/error";
				} else {
					model.addAttribute("depositoryFirstList", depositoryFirstList);
					if (StringUtils.isNotBlank(storageCheckQuery.getDepfirstId())
							&& StringUtils.isNumeric(storageCheckQuery.getDepfirstId())) {
						model.addAttribute("depositoryList", depositoryService.getDeplistByFirstDepId(Long
								.valueOf(storageCheckQuery.getDepfirstId())));
					}
					if (StringUtils.isNotBlank(storageCheckQuery.getDepId())
							&& StringUtils.isNumeric(storageCheckQuery.getDepId())) {
						model.addAttribute("depLocationLists", depLocationManager.getRightLocationsByDepositoryId(Long
								.parseLong(storageCheckQuery.getDepId())));
					}
					storageCheckQuery.setDepfirstIds(getDepfirstIdForQuery(adminAgent));
				}

				if (StringUtil.isBlank(storageCheckQuery.getDateStart())
						&& StringUtil.isBlank(storageCheckQuery.getDateEnd())) {
					storageCheckQuery.setDateStart(DateUtil.getDiffDate(new Date(), -30));
					storageCheckQuery.setDateEnd(DateUtil.getDateToString(new Date()));
				}
				QueryPage queryPage = storageCheckManager.getStorageCheckListsByParameterMap(storageCheckQuery,
						currPage, pageSize, false);
				List<StorageCheckList> storageCheckList = (List<StorageCheckList>) queryPage.getItems();
				if (storageCheckList != null) {
					for (StorageCheckList tmp : storageCheckList) {

						Object[] data = { checkId, tmp.getCode() + "", tmp.getGoodsInstanceName() + "",
								attributeManager.getFullAttributeStringByAttrs(tmp.getAttribute()) + "",
								tmp.getDepName() + "", tmp.getLocName() + "", tmp.getCheckNum(), tmp.getProfitNum(),
								DoubleUtil.round(tmp.getPrice(), 2) };
						storageExportList.add(data);
					}
				}
			}
			goodsBatch.exportExcelByObject(outwt, storageExportList);
			outwt.flush();
		} catch (Exception e) {
			request.setAttribute("errorMessage", "����ʧ�ܣ�");
			e.printStackTrace();
		} finally {
			outwt.close();
		}
		return "/storage/list_storage_check";

	}

	/**
	 * �����������ҳ��
	 */
	@RequestMapping(value = "/storage_analysis_export_page")
	public String storageAnalysisExportPage(@RequestParam(value = "queryDate", required = false) String queryDate,
			Model model) {
		if (StringUtil.isBlank(queryDate)) {
			queryDate = DateUtil.getDateToString(new Date());
			model.addAttribute("queryDate", queryDate);
		}
		return "/storage/storage_analysis_export_page";
	}

	/**
	 * �����������
	 *
	 * @param queryDate
	 * @param code
	 * @param supplierName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export_storage_analysis")
	public void exportStorageAnalysis(@RequestParam(value = "queryDate", required = false) String queryDate,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "supplierName", required = false) String supplierName, HttpServletResponse response)
			throws Exception {
		Map parMap = new HashMap();
		parMap.put("queryDate", queryDate);
		parMap.put("code", code);
		parMap.put("supplierName", supplierName);

		try {
			response.resetBuffer();
			response.getOutputStream().flush();
			Date da = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			String date = df.format(da);
			response.setHeader("Content-disposition", "attachment; filename=storageAgeExport" + date + ".csv");
			response.setContentType("application/octet-stream;charset=utf-8");

			CsvWriter writer = new CsvWriter(response.getOutputStream(), ',', Charset.forName("GBK"));

			String[] title = { "��Ʒ����", "��Ʒ����", "��Ŀ", "����", "��λ", "�ֿ�", "��λ", "��Ӧ��", "���", "�������", "�ɱ�����", "�ڿ�15��",
			                   "30������","30-90��","90-180��","180-360��","360������" };
			writer.writeRecord(title);

			if (StringUtil.isNotBlank(queryDate)) {
				List<StockAge> storageAgeExportList = storageCheckManager
						.getStockAgeAnalysisDataListsByParameterMap(parMap);
				if (storageAgeExportList != null) {
					for (StockAge tmp : storageAgeExportList) {

						String stype = tmp.getStorType();
						if (stype != null && stype.equals("f")) {
							stype = "ʵ�ʿ��";
						} else {
							stype = "������";
						}

						String[] data = {
			                                tmp.getCode() + "",
			                                tmp.getInstanceName() + "",
			                                categoryManager.getCatFullNameByCatcode(tmp.getCatCode()) + "",
			                                attributeManager.getFullAttributeStringByAttrs(tmp.getAttrs()) + "",
			                                tmp.getGoodsUnit() + "",
			                                tmp.getDepositoryName() + "",
			                                tmp.getLocName() + "",
			                                tmp.getSupplierName() + "",
			                                tmp.getStorageNum() + "",
			                                stype + "",
			                                DoubleUtil.round(tmp.getAverageStoragePrice(),2) + "",
			                                tmp.getHalfMonth() + "",
			                                tmp.getAfterOnemonth() + "",
			                                tmp.getOnemonthThreemonty() + "",
			                                tmp.getThreemontySixmonty() + "",
			                                tmp.getSixmontyTwelvemonth() + "",
			                                tmp.getAboveTwelvemonth() + ""};
						writer.writeRecord(data);
					}
				}

			}
			writer.flush();
			writer.close();
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���Ԥ����ѯ
	 *
	 * @param search
	 * @param type
	 * @param currPage
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@AdminAccess({ EnumAdminPermission.A_STORAGE_WARN_VIEW_USER })
	@RequestMapping(value = "/list_storage_warn")
	public String searchStorageWarn(@ModelAttribute("search") GoodsInstance search,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model)
			throws Exception {
		model.addAttribute("catList", categoryManager.getCatInfoByDepth(1));
		model.addAttribute("categoryManager", categoryManager);
		if (StringUtil.isNotBlank(type) && type.equals("min")) {
			search.setMin(type);
		}
		if (StringUtil.isNotBlank(type) && type.equals("max")) {
			search.setMax(type);
		}

		QueryPage queryPage = goodsInstanceManager.searchGoodsInstancesHasStorage(search, currPage, pageSize);
		List<GoodsInstance> list = (List<GoodsInstance>) queryPage.getItems();
		if (list != null && list.size() > 0) {
			for (GoodsInstance gi : list) {
				if (gi.getMinNum() != null && gi.getStorageNum().intValue() < gi.getMinNum().intValue()) {
					gi.setMin("min");
				}
				if (gi.getMaxNum() != null && gi.getStorageNum().intValue() > gi.getMaxNum().intValue()) {
					gi.setMax("max");
				}
			}
		}
		queryPage.setItems(list);
		model.addAttribute("query", queryPage);
		model.addAttribute("type", type);
		return "/storage/list_storage_warn";
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
	private void initDepository(StorageCheckQuery storageCheckQuery, AdminAgent adminAgent, Model model) {
		// ����ȫ����һ���ֿ�ID
		depositoryFirstList = depositoryFirstManager.getDepositoryFirstListByIds(getDepfirstIdForQuery(adminAgent));
		if (depositoryFirstList == null || depositoryFirstList.size() == 0) {
			model.addAttribute("message", "һ���ֿ��¼Ϊ�գ�");
		} else {
			model.addAttribute("depositoryFirstList", depositoryFirstList);
			if (StringUtils.isNotBlank(storageCheckQuery.getDepfirstId())
					&& StringUtils.isNumeric(storageCheckQuery.getDepfirstId())) {
				model.addAttribute("depositoryList",
						depositoryService.getDeplistByFirstDepId(Long.valueOf(storageCheckQuery.getDepfirstId())));
			}
			if (StringUtils.isNotBlank(storageCheckQuery.getDepId())
					&& StringUtils.isNumeric(storageCheckQuery.getDepId())) {
				model.addAttribute("depLocationLists",
						depLocationManager.getLocationsByDepositoryId(Long.valueOf(storageCheckQuery.getDepId())));
			}
		}
	}
}
