package com.huaixuan.network.web.action.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Keyword;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumNoticeType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.shop.KeywordService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class KeywordAction extends BaseAction {
	private static final int PAGE_SIZE = 10;
	private static final long serialVersionUID = -1592793257929666515L;
	@Autowired
	private KeywordService keywordService;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private ShopInfoService shopInfoService;
	@Autowired
	private Validator keywordValidator;

	/**
	 * Grab the entity from the database before populating with request
	 * parameters
	 */
	public void prepare() throws Exception {

	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 关键字列表
	 * @param model
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_KW_VIEW_USER })
	@RequestMapping(value = "/shop/kwlist")
	public String keywordList(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {

		QueryPage queryPage = this.keywordService.getKeywordsPage(1, currPage,
				PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		return "/shop/keywordList";
	}

	@AdminAccess({ EnumAdminPermission.A_KW_DELETE_USER })
	@RequestMapping(value = "/shop/deletekw")
	public String deleteKeyword(@RequestParam("keywordId") String idstr,
			Model model) throws Exception {
		Long keywordId = null;
		if (StringUtil.isNotBlank(idstr)) {
			keywordId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}

		keywordService.removeKeyword(keywordId);
		return "redirect:/shop/kwlist.html";
	}

	@AdminAccess({ EnumAdminPermission.A_KW_ADD_USER })
	@RequestMapping(value = "/shop/addkwp", method = RequestMethod.GET)
	public String addKeywordPage(@ModelAttribute("keyword") Keyword keyword,
			Model model) throws Exception {
		List<Category> categorysList = categoryManager.getCategoryForGuide();
		for (Category category : categorysList) {
			category.setCache(genarateListString(category.getDepth()));
		}
		model.addAttribute("categorysList", categorysList);
		return "/shop/addKeyword";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 新增热门关键字
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/addkw", method = RequestMethod.POST)
	public String addKeyword(@ModelAttribute("keyword") Keyword keyword,
			BindingResult result, Model model) throws Exception {

		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		if (shopInfo != null) {
			keyword.setShopId(shopInfo.getId());
		} else {
			model.addAttribute("message", "店铺信息有误");
			return "/shop/shop_error";
		}
		if (StringUtil.isNotBlank(keyword.getSortstr())) {
			keyword.setSort((int) Integer.parseInt(keyword.getSortstr()));
		}
		keywordValidator.validate(keyword, result);
		if (result.hasErrors()) {
			model.addAttribute("keyword", keyword);
			List<Category> categorysList = categoryManager
					.getCategoryForGuide();
			for (Category category : categorysList) {
				category.setCache(genarateListString(category.getDepth()));
			}
			model.addAttribute("categorysList", categorysList);
			return "/shop/addKeyword";
		}

		keyword.setName(keyword.getName().trim());
		if (StringUtil.isNotBlank(keyword.getDesc())) {
			keyword.setDesc(keyword.getDesc().trim());
		}

		keywordService.addKeyword(keyword);
		return "redirect:/shop/kwlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 修改热门链接初始化页面
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_KW_MODIFY_USER })
	@RequestMapping(value = "/shop/updatekwp", method = RequestMethod.GET)
	public String updateKeywordPage(@ModelAttribute("keyword") Keyword keyword,
			@RequestParam("keywordId") String idstr, Model model)
			throws Exception {
		Long keywordId = null;
		if (StringUtil.isNotBlank(idstr)) {
			keywordId = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "出错啦");
			return "/shop/shop_error";
		}

		keyword = keywordService.getKeyword(keywordId);
		keyword.setSortstr(((Integer) keyword.getSort()).toString());
		List<Category> categorysList = categoryManager.getCategoryForGuide();
		for (Category category : categorysList) {
			category.setCache(genarateListString(category.getDepth()));
		}
		model.addAttribute("categorysList", categorysList);
		model.addAttribute("keyword", keyword);
		return "/shop/updateKeyword";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 修改热门关键字
	 * @param keyword
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/updatekw", method = RequestMethod.POST)
	public String updateKeyword(@ModelAttribute("keyword") Keyword keyword,
			BindingResult result, Model model) throws Exception {
		Long id = keyword.getId();
		if (id == null) {
			model.addAttribute("message", "出错啦");
			return "/shop/shop_error";
		}
		if (StringUtil.isNotBlank(keyword.getSortstr())) {
			keyword.setSort((int) Integer.parseInt(keyword.getSortstr()));
		}

		keyword.setName(keyword.getName().trim());
		keyword.setDesc(keyword.getDesc().trim());
		keywordValidator.validate(keyword, result);
		if (result.hasErrors()) {
			model.addAttribute("keyword", keyword);
			List<Category> categorysList = categoryManager
					.getCategoryForGuide();
			for (Category category : categorysList) {
				category.setCache(genarateListString(category.getDepth()));
			}
			model.addAttribute("categorysList", categorysList);
			return "/shop/addKeyword";
		}
		keywordService.editKeyword(keyword);
		return "redirect:/shop/kwlist.html";
	}

	private String genarateListString(int depth) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < depth; i++) {
			sb.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;");
		}
		return sb.toString();
	}
}
