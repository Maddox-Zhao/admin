package com.huaixuan.network.web.action.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.shop.Article;
import com.huaixuan.network.biz.domain.shop.ArticleCat;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.ArticleCatService;
import com.huaixuan.network.biz.service.shop.ArticleService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class ArticleAction {
	private static final int PAGE_SIZE = 10;
	private static final long serialVersionUID = 5785274431688006295L;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCatService articleCatService;
	@Autowired
	private Validator articleValidator;

	private @Value("${liangpin99.url}")
	String liangpin99url;

	// /**
	// * Grab the entity from the database before populating with request
	// * parameters
	// */
	// public void validate() {
	// articleCatList = articleCatManager.getArticleCats();
	// }

	/**
	 * @author chenhang 2011-3-15
	 * @description 资讯列表页面
	 */
	@AdminAccess({EnumAdminPermission.A_AR_VIEW_USER})
	@RequestMapping(value = "/shop/arlist")
	public String articleList(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {

		QueryPage queryPage = this.articleService.getArticlesPage(currPage,
				PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		model.addAttribute("liangpin99url", liangpin99url);
		return "/shop/articleList";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description 新增资讯初始化页面
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_AR_ADD_USER})
	@RequestMapping(value = "/shop/addarp")
	public String addArticlePage(@ModelAttribute("article") Article article,
			Model model) throws Exception {
		List<ArticleCat> articleCatList = articleCatService.getArticleCats();
		model.addAttribute("articleCatList", articleCatList);
		return "/shop/addArticle";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description 新增资讯
	 * @param article
	 * @param result
	 * @param model
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/addar")
	public String addArticle(@ModelAttribute("article") Article article,
			BindingResult result, Model model, AdminAgent agent)
			throws Exception {

		String userName = agent.getUsername();
		if (StringUtil.isBlank(userName)) {
			model.addAttribute("message", "未登录！");
			return "/shop/shop_error";
		}

		article.setAuthor(agent.getUsername());
		article.setIsShow(1);
		article.setIsTop(0);

		article.setTitle(article.getTitle().trim());
		article.setKeywords(article.getKeywords().trim());
		article.setDigest(article.getDigest().trim());

		articleValidator.validate(article, result);
		if (result.hasErrors()) {
			model.addAttribute("article", article);
			List<ArticleCat> articleCatList = articleCatService
					.getArticleCats();
			model.addAttribute("articleCatList", articleCatList);
			return "/shop/addArticle";
		}
		articleService.addArticle(article);
		return "redirect:/shop/arlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description 删除资讯
	 * @param idStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_AR_DELETE_USER})
	@RequestMapping(value = "/shop/deletear")
	public String deleteArticle(@RequestParam("articleId") String idStr,
			Model model) throws Exception {

		long articleId = 0;
		if (StringUtil.isNotBlank(idStr)) {
			articleId = Long.parseLong(idStr);
		}

		articleService.removeArticle(articleId);

		return "redirect:/shop/arlist.html";
	}

	/**
	 * @author chenhang 2011-3-15
	 * @description 设置是否置顶
	 */
	@RequestMapping(value = "/shop/top")
	public String updateTop(@RequestParam("articleId") String idStr, Model model)
			throws Exception {
		long articleId = 0;
		if (StringUtil.isNotBlank(idStr)) {
			articleId = Long.parseLong(idStr);
		}

		articleService.updateTop(articleId);

		return "redirect:/shop/arlist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description 设置是否显示
	 * @param idStr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/ushow")
	public String updateShow(@RequestParam("articleId") String idStr,
			Model model) throws Exception {

		long articleId = 0;
		if (StringUtil.isNotBlank(idStr)) {
			articleId = Long.parseLong(idStr);
		}

		articleService.updateShow(articleId);

		return "redirect:/shop/arlist.html";
	}

	/**
	 * @author chenhang 2011-3-15
	 * @description 修改咨询页面初始化
	 */
	@AdminAccess({EnumAdminPermission.A_AR_MODIFY_USER})
	@RequestMapping(value = "/shop/updatearp")
	public String updateArticlePage(@RequestParam("articleId") String idStr,
			Model model) throws Exception {

		long articleId = 0;
		if (StringUtil.isNotBlank(idStr)) {
			articleId = Long.parseLong(idStr);
		}

		Article article = articleService.getArticle(articleId);
		// articleCatList = articleCatManager.getArticleCats();
		model.addAttribute("article", article);
		List<ArticleCat> articleCatList = articleCatService.getArticleCats();
		model.addAttribute("articleCatList", articleCatList);
		return "/shop/updateArticle";
	}

	@RequestMapping(value = "/shop/updatear")
	public String updateArticle(@ModelAttribute("article") Article article,
			BindingResult result, Model model, AdminAgent agent)
			throws Exception {

		String userName = agent.getUsername();
		if (StringUtil.isBlank(userName)) {
			model.addAttribute("message", "未登录！");
			return "/shop/shop_error";
		}
		article.setAuthor(userName);

		article.setTitle(article.getTitle().trim());
		article.setKeywords(article.getKeywords().trim());
		article.setDigest(article.getDigest().trim());
		articleValidator.validate(article, result);
		if (result.hasErrors()) {
			model.addAttribute("article", article);
			List<ArticleCat> articleCatList = articleCatService
					.getArticleCats();
			model.addAttribute("articleCatList", articleCatList);
			return "/shop/updateArticle";
		}
		articleService.editArticle(article);
		return "redirect:/shop/arlist.html";
	}

}
