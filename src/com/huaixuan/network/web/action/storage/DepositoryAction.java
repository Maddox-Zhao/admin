package com.huaixuan.network.web.action.storage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.storage.Depository;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.enums.EnumDepositoryType;
import com.huaixuan.network.biz.query.DepositoryQuery;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.biz.service.storage.DepositoryService;
import com.huaixuan.network.web.action.BaseAction;

/**
 * @author fanyj
 */
@Controller
@RequestMapping("/storage")
public class DepositoryAction extends BaseAction {

	@Autowired
	private DepositoryService depositoryService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private DepositoryFirstManager depositoryFirstManager;// һ���ֿ�

	/*
	 * ����
	 */
	// private Depository depository;
	// private Long depositoryId;
	// private List<Depository> depositoryLists;
	// private List<User> userLists;
	// private Map<String, String> parMap = new HashMap<String, String>();
	// private Map<String, String> enumDepositoryType = EnumDepositoryType.toMap();
	// private String message;
	// private Page page;
	//
	// private UserManager userManager;
	// private Map<Long, String> depositoryNameMap = new HashMap<Long, String>();

	/**
	 * �������༭���ֿ��ʼҳ��
	 * 
	 * @return "success" if no exceptions thrown
	 */
	@RequestMapping("/addDepositoryPage")
	public String addDepositoryPage(@RequestParam("actionType") String actionType,
			@RequestParam(value = "depositoryId", required = false) Long depositoryId, Model model, AdminAgent agent) {
		model.addAttribute("actionType", actionType);

		if ("edit".equals(actionType)) {
			Depository depository = depositoryService.getDepository(depositoryId);

			model.addAttribute("depository", depository);
		} else {
			model.addAttribute("depository", new Depository());
		}

		List<Admin> adminUsers = adminService.getAdminUserList();
		model.addAttribute("adminUsers", adminUsers);

		List<Long> depositoryFirstIdList = getDepfirstIdForQuery(agent);
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
				.getDepositoryFirstListByIds(depositoryFirstIdList);

		model.addAttribute("depositoryFirstList", depositoryFirstList);

		model.addAttribute("enumDepositoryType", EnumDepositoryType.toMap());

		return "/storage/addDepository";
	}

	/**
	 * ����/�༭�ֿ���Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addDepository")
	public String addDepository(@ModelAttribute("depository") Depository depository,
			@RequestParam("actionType") String actionType, Model model, AdminAgent agent) {
		// У��ֿ������Ƿ����
		boolean isExisted = false;

		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("name", depository.getName());
		int shoppintCount = depositoryService.getCountByParMap(parMap);
		if ("edit".equals(actionType)) {
			Depository ori = depositoryService.getDepository(depository.getId());
			if (!ori.getName().equals(depository.getName())) {
				isExisted = shoppintCount > 0;
			}
		} else {
			isExisted = shoppintCount > 0;
		}
		if (isExisted) {
			model.addAttribute("actionType", actionType);

			List<Long> depositoryFirstIdList = getDepfirstIdForQuery(agent);
			List<DepositoryFirst> depositoryFirstList = depositoryFirstManager
					.getDepositoryFirstListByIds(depositoryFirstIdList);
			model.addAttribute("depositoryFirstList", depositoryFirstList);

			List<Admin> adminUsers = adminService.getAdminUserList();
			model.addAttribute("adminUsers", adminUsers);

			model.addAttribute("enumDepositoryType", EnumDepositoryType.toMap());
			model.addAttribute("message", "�ֿ������Ѵ��ڣ�");
			return "/storage/addDepository";
		}

		if ("edit".equals(actionType)) {
			depositoryService.editDepository(depository);
		} else {
			depository.setStatus("v");
			depository.setId(depositoryService.addDepository(depository));
		}

		return "redirect:/storage/detailDepository.html?depositoryId=" + depository.getId();
	}

	/**
	 * ��ѯ�ֿ��б�
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	@RequestMapping("/searchDepository")
	public String searchDepositoryList(@ModelAttribute("query") DepositoryQuery query,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage, Model model,
			AdminAgent agent) throws Exception {
		query.setDepfirstIds(getDepfirstIdForQuery(agent));
		QueryPage page = depositoryService.getDepositorysByParMap(query, currPage, pageSize, true);
		model.addAttribute("page", page);

		// ȡ�ù���ԱȨ�޵Ļ�Ա�б�
		// userLists = userManager.getUserByIsAdmin(1);
		// TODO �����ȡ�˺�̨�����û�������Ժ���԰��տͻ��������ĵ�
		model.addAttribute("userList", adminService.getAdminUserList());

		// ��ʼ��һ��ֿ�����
		Map<Long, String> depositoryNameMap = new HashMap<Long, String>();
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		for (DepositoryFirst depositoryFirst : depositoryFirstList) {
			depositoryNameMap.put(depositoryFirst.getId(), depositoryFirst.getDepFirstName());
		}

		model.addAttribute("depositoryNameMap", depositoryNameMap);

		model.addAttribute("enumDepositoryType", EnumDepositoryType.toMap());

		return "/storage/searchDepository";
	}

	/**
	 * ��ѯ�ֿ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detailDepository")
	public String searchDepositoryDetail(@RequestParam("depositoryId") Long depositoryId, Model model) {
		Depository depository = depositoryService.getDepository(depositoryId);
		if (depository.getDepFirstId() != null) {
			DepositoryFirst depFirst = depositoryFirstManager.getDepositoryById(depository.getDepFirstId());
			depository.setDepFirstName(depFirst.getDepFirstName());
		}

		model.addAttribute("depository", depository);

		model.addAttribute("enumDepositoryType", EnumDepositoryType.toMap());
		return "/storage/detailDepository";
	}

	//
	// /**
	// * ɾ���ֿ���Ϣ
	// *
	// * @return "success" if no exceptions thrown
	// */
	// public String deleteDepository() {
	//
	// return SUCCESS;
	// }

	/**
	 * DWR��ʽ���ֿ������Ƿ����
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDepIsExistByDwr")
	public Object checkDepIsExistByDwr(@RequestParam("param1") String depName) {
		String message;

		if (depName == null) {
			message = "['false','name must be not null!']";
		} else {
			// У��ֿ������Ƿ����
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("name", depName);
			int shoppintCount = depositoryService.getCountByParMap(parm);
			if (shoppintCount > 0) {
				message = "['false','the name is exist!']";
			} else {
				message = "['true','check success!']";
			}
		}

		return message;
	}

}
