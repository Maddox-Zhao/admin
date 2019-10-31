package com.huaixuan.network.web.action.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.storage.DepLocation;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.storage.DepLocationManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author fanyj
 */
@Controller
@RequestMapping("/storage")
public class DepLocationAction extends BaseAction {

	@Autowired
	private DepLocationManager depLocationManager;
	@Autowired
	private DepositoryService depositoryService;

	// /*
	// * 变量
	// */
	// private DepLocation depLocation;
	// private Long depLocationId;
	// private List<DepLocation> depLocationLists;
	// private List<Depository> depositoryLists;
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private String message;
	// private Page page;

	/**
	 * 新增库位初始页面
	 * 
	 */
	@RequestMapping("addDepLocationPage")
	public String addDepLocationPage(@ModelAttribute("loc") DepLocation loc, Model model, AdminAgent agent) {
		// 取得所有仓库列表
		Map<String, Object> paramMap = this.setAdminUserAuthority(null, agent);// 得到对一级仓库的权限
		List<Depository> depositoryLists = depositoryService.getDepositorysByDepFirstId(paramMap);
		model.addAttribute("depositoryLists", depositoryLists);

		return "/storage/addDepLocation";
	}

	/**
	 * 新增库位基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addDepLocation")
	public String addDepLocation(@ModelAttribute("loc") DepLocation loc, Model model, AdminAgent agent) {
		// 校验库位名称是否存在
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("locName", loc.getLocName());
		parMap.put("depId", loc.getDepId().toString());
		int count = depLocationManager.getCountByParMap(parMap);
		if (count > 0) {
			model.addAttribute("message", "isExist");
			return addDepLocationPage(loc, model, agent);
		}

		loc.setStatus("1");
		loc.setIsCheck("N");
		Long depLocationId = depLocationManager.addDepLocation(loc);

		return "redirect:/storage/detailDepLocation.html?depLocationId=" + depLocationId;
	}

	/**
	 * 编辑库位初始化页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("editDepLocationPage")
	public String editDepLocationPage(@RequestParam("depLocationId") Long id, Model model, AdminAgent agent,
			DepLocation loc) {
		if (id != null) {
			model.addAttribute("loc", depLocationManager.getDepLocationByLocationId(id));
		} else {
			model.addAttribute("loc", loc);
		}

		Map<String, Object> paramMap = setAdminUserAuthority(null, agent);// 得到对一级仓库的权限
		List<Depository> depositoryLists = depositoryService.getDepositorysByDepFirstId(paramMap);
		model.addAttribute("depositoryLists", depositoryLists);

		return "storage/editDepLocation";
	}

	/**
	 * 编辑库位信息
	 * 
	 * @return
	 */
	@RequestMapping("editDepLocation")
	public String editDepLocation(@ModelAttribute("loc") DepLocation loc, Model model, AdminAgent agent) {
		// 校验库位名称是否存在
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("locName", loc.getLocName());
		parMap.put("depId", loc.getDepId().toString());
		parMap.put("id", loc.getId().toString());
		int count = depLocationManager.getCountByParMap(parMap);
		if (count > 0) {
			model.addAttribute("message", "isExist");
			return editDepLocationPage(null, model, agent, loc);
		}
		loc.setStatus("1");
		depLocationManager.editDepLocation(loc);

		return "redirect:/storage/detailDepLocation.html?depLocationId=" + loc.getId();
	}

	/**
	 * 查询库位列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("searchDepLocation")
	public String searchDepLocationList(@ModelAttribute("loc") DepLocation loc,
			@RequestParam(value = "depLocationId", required = false) Long id,
			@RequestParam(value = "actionType", required = false) String actionType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) throws Exception {
		if (StringUtils.isNotBlank(actionType) && "delete".equals(actionType)) { // 逻辑删除库位信息
			// 判断要删除的库位是否有货物
			int num = depLocationManager.countStorageByDepLocId(id);
			if (num > 0) {
				model.addAttribute("message", "hasStorage");
			} else {
				DepLocation depLocation = depLocationManager.getDepLocationByLocationId(id);
				if (depLocation != null) {
					// 是否处于盘点状态
					if ("Y".equals(depLocation.getIsCheck())) {
						model.addAttribute("message", "ischeck");
					} else {
						depLocation.setStatus("0");
						depLocationManager.editDepLocation(depLocation);
						model.addAttribute("message", "editSuccess");
					}
				}
			}
		}

		// 取得所有仓库列表
		Map<String, Object> paramMap = this.setAdminUserAuthority(null, agent);// 得到对一级仓库的权限
		List<Depository> depositoryLists = depositoryService.getDepositorysByDepFirstId(paramMap);
		model.addAttribute("depositoryLists", depositoryLists);

		QueryPage page;
		if (depositoryLists.size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();

			List<Long> depList = new ArrayList<Long>();
			for (Depository depTmp : depositoryLists) {
				depList.add(depTmp.getId());// 将仓库ID加入列表中
			}
			param.putAll(BeanUtils.describe(loc));
			param.put("depIds", depList);
			page = depLocationManager.getDepLocationsByParMap(param, currPage, pageSize);
		} else {
			page = new QueryPage(loc);
		}
		model.addAttribute("page", page);

		return "/storage/searchDepLocation";
	}

	/**
	 * 查询库位详情
	 * 
	 * @return
	 */
	@RequestMapping("detailDepLocation")
	public String searchDepLocationDetail(@RequestParam("depLocationId") Long id, Model model) {
		DepLocation loc = depLocationManager.getDepLocationByLocationId(id);
		model.addAttribute("loc", loc);
		return "/storage/detailDepLocation";
	}

	/**
	 * DWR方式检查库位名称是否存在
	 * 
	 * @return
	 */
	@RequestMapping("checkLocNameIsExistByDwr")
	@ResponseBody
	public Object checkLocNameIsExistByDwr(@RequestParam(value = "param1", required = false) String id,
			@RequestParam("param2") String depId, @RequestParam("param3") String locName, AdminAgent agent) {
		// 校验仓库名称是否存在
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("locName", locName);
		parMap.put("depId", depId);
		if (id != null && StringUtil.isNotBlank(id)) {
			parMap.put("id", id);
		}

		String message;
		int count = depLocationManager.getCountByParMap(parMap);
		if (count > 0) {
			message = "['false','the name is exist!']";
		} else {
			message = "['true','check success!']";
		}
		return message;
	}
}
