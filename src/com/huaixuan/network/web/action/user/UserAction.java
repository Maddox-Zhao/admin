package com.huaixuan.network.web.action.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huaixuan.network.biz.domain.admin.Admin;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.user.AgentInfo;
import com.huaixuan.network.biz.domain.user.User;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.domain.user.UserInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumCashStatus;
import com.huaixuan.network.biz.enums.EnumInPrestige;
import com.huaixuan.network.biz.enums.EnumInTbYouaPrestige;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.admin.AdminService;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.user.UserAddressManager;
import com.huaixuan.network.biz.service.user.UserAgentManager;
import com.huaixuan.network.biz.service.user.UserInfoManager;
import com.huaixuan.network.biz.service.user.UserManager;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;

@Controller
@RequestMapping(value = "/user")
public class UserAction extends BaseAction
{

	@Autowired
	private UserManager userManager;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserInfoManager userInfoManager;

	@Autowired
	private UserAgentManager userAgentManager;

	@Autowired
	private UserAddressManager userAddressManager;

	@Autowired
	private RegionManager regionManager;

	@Autowired
	private CategoryManager categoryManager;

	private static final Double cashPercent = 0.01;

	/**
	 * 用户注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@AdminAccess( { EnumAdminPermission.A_REG_USER_VIEW_USER })
	@SuppressWarnings("unchecked")
	@RequestMapping("/regusers")
	public String registedUsers(
			@ModelAttribute("user") User user,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
	{
		List<User> users = new ArrayList<User>();

		QueryPage page = userManager.serchUserListByConditionWithPage(user,
				currPage, this.pageSize);

		users = (List<User>) page.getItems();
		// 获取后台登录账号
		List<Admin> adminUsers = adminService.getAdminUserList();
		model.addAttribute("adminUsers", adminUsers);
		// 
		User usertemp;
		AgentInfo info = null;
		Admin admin = null;
		// 
		if (users != null && users.size() > 0)
		{
			for (int i = 0; i < users.size(); i++)
			{
				usertemp = users.get(i);
				// 判断客户类型
				if ("d".equals(usertemp.getType())
						|| "w".equals(usertemp.getType()))
				{
					info = userAgentManager.getUserAgentById(usertemp.getId());
					if (info != null)
					{
						for (int j = 0; j < adminUsers.size(); j++)
						{
							admin = adminUsers.get(j);
							// 
							if (info.getAgent_manager_id() != null
									&& info.getAgent_manager_id().equals(
											admin.getId()))
							{
								usertemp.setAgentManagerName(admin
										.getUserName());
							}
						}
					}
				}
				users.set(i, usertemp);
			}
		}

		if (page != null)
		{
			model.addAttribute("query", page);
		}

		Map<String, String> cashStatusMap = EnumCashStatus.toMap();
		model.addAttribute("cashStatusMap", cashStatusMap);
		return "/user/userlist";
	}

	/**
	 *用户详情页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userDetail", method = RequestMethod.GET)
	public String userDetail(@RequestParam("userId") String id, Model model)
	{
		List<Region> provinceList = regionManager.getRegionByType(2);
		List<Region> cityListInit = regionManager.getRegionByType(3);
		User user = userManager.getUserById(Long.parseLong(id));
		// AgentInfo agentInfo =
		// userAgentManager.getAgentInfoById(Long.parseLong(id));
		// if (agentInfo != null && agentInfo.getCashTicket() > 0) {
		// agentInfo.setCashTotal(agentInfo.getCashTicket() / (1 +
		// cashPercent));
		// }

		List<Admin> adminUsers = adminService.getAdminUserList();
		// boolean isshow = (null != null ? true : false);

		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityListInit", cityListInit);
		model.addAttribute("user", user);
		model.addAttribute("agentInfo", null);
		model.addAttribute("users", adminUsers);
		model.addAttribute("isshow", false);

		Map<String, String> presgtige = EnumInPrestige.toMap();
		Map<String, String> tbYoua = EnumInTbYouaPrestige.toMap();

		model.addAttribute("presgtige", presgtige);
		model.addAttribute("tbYoua", tbYoua);
		List<Category> categorys = categoryManager.getCategoryForGuide();
		model.addAttribute("categorys", categorys);
		return "/user/userDetail";
	}

	@RequestMapping("/editInput")
	public String editUserInput(@RequestParam("userId") Long id, Model model) throws Exception
	{
		User user = userManager.getUserById(id);

		UserInfo userInfo = userInfoManager.getUserInfoByUserId(user.getId());

		// 获取省份信息
		List<Region> provinceList = regionManager.getRegionByType(2);
		model.addAttribute("provinceList", provinceList);
		if (userInfo != null)
		{
			UserAddress userAddress = userAddressManager.getUserAddress(userInfo.getAddress_id());
			model.addAttribute("userAddress", userAddress);
			// 
			if (userAddress!= null && userAddress.getProvince() != null)
			{
				List<Region> cityListInit = regionManager.getRegionChilds(userAddress.getProvince());
				model.addAttribute("cityListInit", cityListInit);
			}
			//
			if (userAddress!= null && userAddress.getCity() != null)
			{
				List<Region> distincListInit = regionManager.getRegionChilds(userAddress.getCity());
				model.addAttribute("distincListInit", distincListInit);
			}
			user.setUserAddress(userAddress);
		}
		user.setUserInfo(userInfo);
		Map<String, String> presgtige = EnumInPrestige.toMap();
		Map<String, String> userType = EnumUserType.toMap();
		model.addAttribute("presgtige", presgtige); 
		model.addAttribute("userType", userType); 
		model.addAttribute("user", user);
		return "/user/userEdit";
	}

	/**
	 * 省市联动
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("/select")
	public @ResponseBody
	Object selectChild(@RequestParam(value = "code") String code)
	{
		List<Region> list = regionManager.getRegionChilds(code);
		return list;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	public String updateUserInfo(@ModelAttribute("user")User user,Model model)throws Exception
	{
		
		userManager.updateUserByNotNull(user);		
		userManager.editUser(user, user.getUserInfo(), user.getUserAddress());				
		model.addAttribute("userId", user.getId());
		model.addAttribute("message", "editsuccess");
		return editUserInput(user.getId(),model);
	}
}
