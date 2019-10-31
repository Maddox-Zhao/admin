package com.huaixuan.network.web.action.shop;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.Nav;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.shop.NavService;
import com.huaixuan.network.biz.service.shop.NoticeService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;
import org.springframework.validation.Validator;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class NavAction {
	private static final long serialVersionUID = -3658995131625975931L;
	private static final int PAGE_SIZE = 10;
	@Autowired
	private NoticeService noticeService;
	// private ShopInfoManager shopInfoManager;
	@Autowired
	private NavService navService;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private Validator navValidator;

	// public void validate() {
	// try {
	// categorysList = categoryManager.getCategoryForGuide();
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// if (categorysList != null && categorysList.size() > 0) {
	// for (Category category : categorysList) {
	// category.setCache(genarateListString(category.getDepth()));
	// }
	// }
	//
	// }

	/**
	 * @author chenhang 2011-3-16
	 * @description �����б�
	 */
	@AdminAccess({EnumAdminPermission.A_NAV_VIEW_USER})
	@RequestMapping(value = "/shop/navlist")
	public String navlist(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		QueryPage queryPage = navService.getNavsPage(0, currPage, PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		return "/shop/navList";
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description ��������
	 * @param nav
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/addnav")
	public String addNav(@ModelAttribute("nav") Nav nav, BindingResult result,
			Model model) throws Exception {

		nav.setTitle(nav.getTitle().trim());
		nav.setLink(nav.getLink().trim());

		String isshowstr = nav.getIsShowstr();
		if (StringUtil.isNotBlank(isshowstr)) {
			if ("1".equals(isshowstr)) {
				nav.setIsShow(Nav.Isshow_yes);
			} else {
				nav.setIsShow(Nav.Isshow_no);
			}
		}

		String isopenstr = nav.getIsOpenstr();
		if (StringUtil.isNotBlank(isopenstr)) {
			if ("1".equals(isopenstr)) {
				nav.setOpennew(Nav.Isopen_yes);
			} else {
				nav.setOpennew(Nav.Isopen_no);
			}
		}
		navValidator.validate(nav, result);
		if (result.hasErrors()) {
			model.addAttribute("nav", nav);
			List<Category> categorysList = getCategorysList();
			model.addAttribute("categorysList", categorysList);
			return "/shop/addNav";
		}
		navService.addNav(nav);
		return "redirect:/shop/navlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description ����������ʼ��ҳ��
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_NAV_ADD_USER})
	@RequestMapping(value = "/shop/addnavp")
	public String addNavPage(@ModelAttribute("nav") Nav nav, Model model)
			throws Exception {
		List<Category> categorysList = getCategorysList();
		model.addAttribute("categorysList", categorysList);

		return "/shop/addNav";
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description �޸ĵ����Ƿ���ʾ
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/shownav")
	public String isshowNav(@RequestParam("navId") String idstr, Model model)
			throws Exception {

		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}

		Nav newNav = navService.getNav(id);
		if (newNav == null) {
			return "/shop/shop_error";
		}

		if (Nav.Isshow_no == newNav.getIsShow()) {
			newNav.setIsShow(Nav.Isshow_yes);
		} else {
			newNav.setIsShow(Nav.Isshow_no);
		}

		navService.isshowNav(newNav);
		return "redirect:/shop/navlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description �޸��Ƿ���ʾ�´���
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/openNav")
	public String isOpenNav(@ModelAttribute("nav") Nav nav,
			@RequestParam("navId") String idstr, Model model) throws Exception {

		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "�����ڸü�¼��");
			return "/shop/shop_error";
		}

		Nav newNav = navService.getNav(id);
		if (newNav == null) {
			return "/shop/shop_error";
		}
		if (Nav.Isopen_no == newNav.getOpennew()) {
			newNav.setOpennew(Nav.Isopen_yes);
		} else {
			newNav.setOpennew(Nav.Isopen_no);
		}

		navService.isOpenNav(newNav);
		return "redirect:/shop/navlist.html";
	}

	/**
	 * @author chenhang 2011-3-16
	 * @description �޸ĵ�����ʼ��ҳ��
	 */
	@AdminAccess({EnumAdminPermission.A_NAV_MODIFY_USER})
	@RequestMapping(value = "/shop/updatenavp")
	public String updateNavPage(@ModelAttribute("nav") Nav nav,
			@RequestParam("navId") String idstr, Model model) throws Exception {
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "�����ڸü�¼��");
			return "/shop/shop_error";
		}
		nav = navService.getNav(id);
		nav.setSortstr(((Integer) nav.getSort()).toString());
		nav.setIsShowstr(((Integer) nav.getIsShow()).toString());
		nav.setIsOpenstr(((Integer) nav.getOpennew()).toString());
		model.addAttribute("nav", nav);

		List<Category> categorysList = getCategorysList();
		model.addAttribute("categorysList", categorysList);
		return "/shop/updateNav";
	}

	public List<Category> getCategorysList() throws Exception {
		List<Category> categorysList = categoryManager.getCategoryForGuide();

		if (categorysList != null && categorysList.size() > 0) {
			for (Category category : categorysList) {
				category.setCache(genarateListString(category.getDepth()));
			}
		}
		return categorysList;
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description ���µ���
	 * @param nav
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/updatenav")
	public String updateNav(@ModelAttribute("nav") Nav nav,
			BindingResult result, Model model) throws Exception {

		Long id = nav.getId();
		if (id == null) {
			model.addAttribute("message", "�����ڸü�¼��");
			return "/shop/shop_error";
		}

		nav.setTitle(nav.getTitle().trim());
		nav.setLink(nav.getLink().trim());
		navValidator.validate(nav, result);
		if (result.hasErrors()) {
			model.addAttribute("nav", nav);
			List<Category> categorysList = getCategorysList();
			model.addAttribute("categorysList", categorysList);
			return "/shop/updateNav";
		}
		String isshowstr = nav.getIsShowstr();
		if (StringUtil.isNotBlank(isshowstr)) {
			if ("1".equals(isshowstr)) {
				nav.setIsShow(Nav.Isshow_yes);
			} else {
				nav.setIsShow(Nav.Isshow_no);
			}
		}

		String isopenstr = nav.getIsOpenstr();
		if (StringUtil.isNotBlank(isopenstr)) {
			if ("1".equals(isopenstr)) {
				nav.setOpennew(Nav.Isopen_yes);
			} else {
				nav.setOpennew(Nav.Isopen_no);
			}
		}

		if (StringUtil.isNotBlank(nav.getSortstr())) {
			nav.setSort((int) Integer.parseInt(nav.getSortstr()));
		}

		navService.editNav(nav);

		return "redirect:/shop/navlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-16
	 * @description ɾ������������¼
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_NAV_DELETE_USER})
	@RequestMapping(value = "/shop/deletenav")
	public String deleteNav(@RequestParam("navId") String idstr, Model model)
			throws Exception {

		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "�ü�¼�����ڣ�");
			return "/shop/shop_error";
		}
		navService.removeNav(id);

		return "redirect:/shop/navlist.html";
	}

	private String genarateListString(int depth) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < depth; i++) {
			sb.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;");
		}
		return sb.toString();
	}

}
