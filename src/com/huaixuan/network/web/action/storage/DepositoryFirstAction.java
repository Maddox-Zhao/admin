package com.huaixuan.network.web.action.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.storage.DepositoryFirst;
import com.huaixuan.network.biz.enums.EnumDepositoryFirstType;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.storage.DepositoryFirstManager;
import com.huaixuan.network.web.action.BaseAction;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
@RequestMapping("/storage")
public class DepositoryFirstAction extends BaseAction {
	// /**
	// *
	// */
	// private static final long serialVersionUID = 1L;
	@Autowired
	private DepositoryFirstManager depositoryFirstManager;
	@Autowired
	private RegionManager regionManager;

	/**
	 * �õ���̨�û���������Ϣ��һ���ֿ��е���Ϣ
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("depFirstAuthority")
	public String depFirstAuthority(@RequestParam("depFirstId") Long depFirstId, Model model) {
		DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(depFirstId);
		model.addAttribute("depFirst", depositoryFirst);

		List<Long> hasRightsIds = new ArrayList<Long>();
		List<Admin> userAdminList = (List<Admin>) adminService.getAdminListByConditionWithPage(new Admin(), 1, 10000)
				.getItems();
		for (Admin admin : userAdminList) {
			if (StringUtils.isNotBlank(admin.getDepFirstId())) {
				StringTokenizer st = new StringTokenizer(admin.getDepFirstId(), ",");
				while (st.hasMoreTokens()) {
					String df = st.nextToken();
					if (df.equalsIgnoreCase(depFirstId.toString())) {
						hasRightsIds.add(admin.getId());
					}
				}
			}
		}
		model.addAttribute("userAdminList", userAdminList);

		model.addAttribute("hasRightsIds", hasRightsIds);

		model.addAttribute("enumDepositoryFirstTypeMap", EnumDepositoryFirstType.toMap());
		return "/storage/depFirstAuthority";
	}

	/**
	 * �ж���ѡ��Ĳֿ��Ƿ���ڣ��ҷ��ش���λ��
	 * 
	 * @param param
	 * @param depFirstId
	 * @return
	 */
	private int getAdminValueStart(String param, String depFirstId) {
		int index = 0;
		String[] tmp = param.split(",");
		for (String temp : tmp) {
			if (temp.equalsIgnoreCase(depFirstId)) {
				return index;
			}
			index = index + temp.length() + 1;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("saveAdminAuthority")
	public String saveAdminAuthority(@RequestParam("depFirstId") Long depFirstId,
			@RequestParam(value = "selectIds", required = false) String[] userIds, Model model) {
		List<Admin> adminList = new ArrayList<Admin>();

		List<Admin> userAdminList = (List<Admin>) adminService.getAdminListByConditionWithPage(new Admin(), 1, 10000)
				.getItems();
		for (Admin ad : userAdminList) {
			StringBuffer depFirst = new StringBuffer();

			if (StringUtils.isNotBlank(ad.getDepFirstId())) {
				depFirst.append(ad.getDepFirstId());
				int index = getAdminValueStart(ad.getDepFirstId(), depFirstId.toString());
				int inTmp = index + depFirstId.toString().length();
				if (index != -1) {// �жϲֿ�ID�Ƿ�����û����� ,��������ɾ�� �ټӽ���
					if (ad.getDepFirstId().length() == inTmp) {
						depFirst.delete(index, index + depFirstId.toString().length());
					} else {
						depFirst.delete(index, index + depFirstId.toString().length() + 1);
					}
				}
			}

			if (userIds != null) {
				for (String admin : userIds) {
					if (ad.getId().toString().equalsIgnoreCase(admin)) {
						if (StringUtil.isNotBlank(ad.getDepFirstId())) {
							if (!depFirst.toString().endsWith(",")) {
								depFirst.append(",");
							}
						}
						if (getAdminValueStart(depFirst.toString(), depFirstId.toString()) == -1) {// ����ֿ��Ȩ���Ѿ����ڣ��򲻸���
							depFirst.append(depFirstId);// ���ֿ��Ȩ�޼����û�����
						}
					}
				}
			}
			if (depFirst.length() > 0) {
				if (depFirst.length() == 1 && depFirst.toString().equalsIgnoreCase(",")) {
					ad.setDepFirstId("");
				} else {
					ad.setDepFirstId(depFirst.toString());
				}
			} else if (depFirst.length() == 0) {
				ad.setDepFirstId("");
			}
			adminList.add(ad);
		}

		for (Admin admin : adminList) {
			adminService.updateAdminDepfirst(admin);
			model.addAttribute("success", "�޸�Ȩ�޳ɹ���");
		}
		return depFirstAuthority(depFirstId, model);
	}

	@RequestMapping("/searchDepFirst")
	public String searchDepFirst(Model model) {
		List<DepositoryFirst> depositoryFirstList = depositoryFirstManager.getDepositoryFirstList();
		model.addAttribute("depositoryFirstList", depositoryFirstList);
		model.addAttribute("enumDepositoryFirstType", EnumDepositoryFirstType.toMap());
		return "/storage/searchDepositoryFirst";
	}

	@RequestMapping("/addDepFirst")
	public String addDepFirst(@RequestParam("actionType") String actionType, Model model) {
		getRegionInit(model, null);

		model.addAttribute("depositoryFirst", new DepositoryFirst());
		model.addAttribute("actionType", actionType);
		model.addAttribute("enumDepositoryFirstType", EnumDepositoryFirstType.toMap());
		return "/storage/addDepFirst";
	}

	@RequestMapping("/updateDepFirst")
	public String updateDepFirst(@RequestParam("actionType") String actionType, @RequestParam("depFirstId") Long id,
			Model model) {
		DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(id);
		model.addAttribute("depositoryFirst", depositoryFirst);

		Region regionDistrict = regionManager.getRegionByCode(depositoryFirst.getRegionCode());
		// ��ʼ���ֿ��ַ
		if (null != regionDistrict) {
			Region regionCity = regionManager.getRegionByCode(regionDistrict.getParentCode());
			if (null != regionCity) {
				if (regionCity.getParentCode().equalsIgnoreCase("1")) {
					depositoryFirst.setCity(depositoryFirst.getRegionCode());
					depositoryFirst.setProvince(regionCity.getCode());
				} else {
					depositoryFirst.setDistrict(depositoryFirst.getRegionCode());
					depositoryFirst.setCity(regionCity.getCode());
					Region regionProvince = regionManager.getRegionByCode(regionCity.getParentCode());
					if (null != regionProvince) {
						depositoryFirst.setProvince(regionProvince.getCode());
					}
				}
			}
		}
		getRegionInit(model, depositoryFirst);

		model.addAttribute("actionType", actionType);
		model.addAttribute("enumDepositoryFirstType", EnumDepositoryFirstType.toMap());
		return "/storage/addDepFirst";
	}

	/**
	 * ��ʼ��������Դ��Ϣ
	 */
	public void getRegionInit(Model model, DepositoryFirst depFirst) {
		model.addAttribute("provinceList", regionManager.getRegionByType(2));
		model.addAttribute("cityList", regionManager.getRegionByType(3));
		model.addAttribute("distincList", regionManager.getRegionByType(4));

		if (depFirst != null) {
			if (StringUtil.isNotBlank(depFirst.getProvince())) {
				// ��ʼ����ǰ��ʼ��ʡ�ݵĳ�����Դ
				model.addAttribute("cityListInit", regionManager.getRegionChilds(depFirst.getProvince()));
			}
			if (StringUtil.isNotBlank(depFirst.getCity())) {
				// ��ʼ����ǰ��ʼ�س��е�������Դ
				model.addAttribute("distincListInit", regionManager.getRegionChilds(depFirst.getCity()));
			}
		}
	}

	@RequestMapping("/saveDepFirst")
	public String saveDepFirst(@ModelAttribute("depositoryFirst") DepositoryFirst depositoryFirst,
			@RequestParam("actionType") String actionType, Model model) {
		String firstName = depositoryFirst.getDepFirstName();
		if (StringUtils.isBlank(firstName)) {
			model.addAttribute("message", "һ���ֿ����Ʋ����ڣ�����������!");
			getRegionInit(model, depositoryFirst);

			model.addAttribute("actionType", actionType);
			model.addAttribute("enumDepositoryFirstType", EnumDepositoryFirstType.toMap());
			return "/storage/addDepFirst";
		}

		boolean isNameExisted = false;
		if (actionType.equalsIgnoreCase("add")) {
			DepositoryFirst depFirstTmp = depositoryFirstManager.getDepositoryByName(firstName);
			isNameExisted = depFirstTmp != null;
		} else {
			DepositoryFirst ori = depositoryFirstManager.getDepositoryById(depositoryFirst.getId());
			if (!firstName.equals(ori.getDepFirstName())) {
				DepositoryFirst depFirstTmp = depositoryFirstManager.getDepositoryByName(firstName);
				isNameExisted = depFirstTmp != null;
			}
		}
		if (isNameExisted) {
			model.addAttribute("message", "һ���ֿ������Ѿ����ڣ�����������!");
			getRegionInit(model, depositoryFirst);

			model.addAttribute("actionType", actionType);
			model.addAttribute("enumDepositoryFirstType", EnumDepositoryFirstType.toMap());
			return "/storage/addDepFirst";
		}

		if (actionType.equalsIgnoreCase("add")) {
			depositoryFirstManager.insertDepositoryFirst(depositoryFirst);
		} else {
			depositoryFirstManager.updateDepositoryFirst(depositoryFirst);
		}
		return "redirect:/storage/searchDepFirst.html";
	}

	/**
	 * JQuery���һ���ֿ�����
	 * 
	 * @return
	 */
	@RequestMapping("/checkDepFirstType")
	@ResponseBody
	public Object checkDepFirstType(@RequestParam("param") String depfirstId) {
		String message;
		if (StringUtil.isNotEmpty(depfirstId) && StringUtil.isNotBlank(depfirstId)) {
			DepositoryFirst depositoryFirst = depositoryFirstManager.getDepositoryById(Long.parseLong(depfirstId));
			if (depositoryFirst != null) {
				if (depositoryFirst.getType().equals("w")) {
					message = "pifa";
				} else {
					message = "putong";
				}
			} else {
				message = "nodepfirst";
			}
		} else {
			message = "nodepfirst";
		}
		return message;
	}

}
