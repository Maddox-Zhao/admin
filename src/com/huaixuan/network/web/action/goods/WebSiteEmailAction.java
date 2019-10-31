package com.huaixuan.network.web.action.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.goods.WebSiteEmail;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.WebSiteEmailService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

@Controller
public class WebSiteEmailAction extends BaseAction {

	@Autowired
	private static final int PAGE_SIZE = 10;
	@Autowired
	private static final long serialVersionUID = 1L;
	@Autowired
	private WebSiteEmailService webSiteEmailService;

	// /**
	// * 鏂板
	// *
	// * @return
	// */
	// public String createWebSiteEmail() {
	// if (null != webSiteEmail) {
	// this.webSiteEmailManager.insertWebSiteEmail(webSiteEmail);
	// }
	// return SUCCESS;
	// }
	//
	// public String showPrivateMsg() {
	// String id = this.getRequest().getParameter("id");
	// if (StringUtils.isNotBlank(id)) {
	// webSiteEmail = this.webSiteEmailManager
	// .getWebSiteEmailByRelationId(Long.parseLong(id));
	// if (StringUtils.isNotBlank(tabFlag)
	// && tabFlag.equalsIgnoreCase("inbox")) {// 鏀朵欢绠遍槄璇伙紝鏇存柊鐘舵佷负宸茶
	// this.webSiteEmailManager.updateWebSiteRelation(
	// Long.parseLong(id), "read");
	// }
	// }
	// return SUCCESS;
	// }
	//
	// public String replayWebSiteEmail() {
	// if (null != webSiteEmail) {
	// webSiteEmail.setTitle("Re:  " + webSiteEmail.getTitle());
	// webSiteEmail.setContent("");
	// }
	// return SUCCESS;
	// }
	//
	// public String removeWebSiteEmail() {
	// List<String> webSiteIds = null;
	// if (webSiteId != null) {
	// webSiteIds = Arrays.asList(webSiteId);
	// }
	// if (null != webSiteIds) {
	// if (this.webSiteEmailManager.removeWebSiteRelation(webSiteIds)) {
	// return SUCCESS;
	// }
	// }
	// return INPUT;
	// }
	//
	// public String addPrivateMsg() {
	// return SUCCESS;
	// }
	//

	/**
	 * @author chenhang 2011-3-8
	 * @description 新增站内信初始化页面
	 */
	@RequestMapping(value = "/shop/addServerMsg", method = RequestMethod.GET)
	public String addServerMsg(
			@ModelAttribute("webSiteEmail") WebSiteEmail webSiteEmail,
			Model model) {
		return "/shop/addServerEmail";
	}

	/**
	 *
	 * @author chenhang 2011-3-8
	 * @description 站内信列表
	 * @param website
	 * @param model
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_WEBMSG_MANAGE_USER})
	@RequestMapping(value = "/shop/awebmsgist")
	public String getAdminWebSiteEmail(
			@ModelAttribute("website") WebSiteEmail website,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		QueryPage queryPage = this.webSiteEmailService.selectWebSiteEmail(
				website, currPage, PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		// int size = webSiteEmailList.size();
		// page.setTotalRowsAmount(size);
		return "/shop/webSitePage";
	}

	/**
	 *
	 * @author chenhang 2011-3-8
	 * @description 修改页面初始化
	 * @param id
	 * @param webSiteEmail
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/updateAdminWebSite", method = RequestMethod.GET)
	public String updateAdminWebSite(@RequestParam("id") String id,
			@RequestParam("str") String str,
			@ModelAttribute("webSiteEmail") WebSiteEmail webSiteEmail,
			Model model) {
		if (StringUtil.isNotBlank(id)) {
			webSiteEmail = this.webSiteEmailService.getWebSiteEmail(Long
					.parseLong(id));
		}
		model.addAttribute("webSiteEmail", webSiteEmail);
		model.addAttribute("str", str);
		return "/shop/addServerEmail";

	}

	//

	/**
	 * @author chenhang 2011-3-8
	 * @description 删除单个站内信
	 */
	@RequestMapping(value = "/shop/deleteAdminWebSite", method = RequestMethod.POST)
	public String deleteAdminWebSite(@RequestParam("id") String id) {
		if (StringUtil.isNotBlank(id)) {
			if (webSiteEmailService.updateWebSiteEmailStatus(
					Long.parseLong(id), "delete")) {
				return "redirect:/shop/awebmsgist.html";
			}
		}
		// return INPUT;
		return "";
	}

	/**
	 * @author chenhang 2011-3-8
	 * @description 新增站内信
	 */
	@RequestMapping(value = "/shop/saveWebSite", method = RequestMethod.POST)
	public String saveWebSite(
			@ModelAttribute("webSiteEmail") WebSiteEmail webSiteEmail,
			BindingResult result, Model model, AdminAgent agent) {
		if (null != webSiteEmail) {
			if (webSiteEmail.getId() != null) {
				if (this.webSiteEmailService.updateWebSite(webSiteEmail)) {
					return "redirect:/shop/awebmsgist.html";
				}
			} else {
				webSiteEmail.setFromUser(agent.getUsername());
				webSiteEmail.setStatus("new");
				webSiteEmail.setType("system");
				if (this.webSiteEmailService.insertWebSite(webSiteEmail)) {
					return "redirect:/shop/awebmsgist.html";
				}
			}
		} else {
			// return INPUT;
			return "";
		}
		// return INPUT;
		return "";
	}

}
