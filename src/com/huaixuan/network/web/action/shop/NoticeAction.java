package com.huaixuan.network.web.action.shop;

import java.util.ArrayList;
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

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.shop.Notice;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumNoticeType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.NoticeService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class NoticeAction extends BaseAction {
	private static final int PAGE_SIZE = 10;
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private ShopInfoService shopInfoService;

	@Autowired
	private Validator noticeAddValidator;

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 公告的list页面
	 * @param model
	 * @param currPage
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_VIEW_USER })
	@RequestMapping(value = "/shop/list")
	public String list(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		QueryPage queryPage = noticeService.getByMapByConditionWithPage(0,
				currPage, PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/notice";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 新增公告
	 * @return
	 */
	@RequestMapping(value = "/shop/addn", method = RequestMethod.POST)
	public String addNotice(@ModelAttribute("notice") Notice notice,
			BindingResult result, Model model, AdminAgent agent)
			throws Exception {

		// 获取用户店铺信息
		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		// 设定公告shopid
		if (shopInfo != null) {
			notice.setShopId(shopInfo.getId());
		} else {
			model.addAttribute("message", "店铺信息有误");
			return "/shop/shop_error";
		}

		noticeAddValidator.validate(notice, result);
		if (result.hasErrors()) {
			model.addAttribute("notice", notice);
			model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
			return "/shop/addNotice";
		}

		notice.setTitle(notice.getTitle().trim());
		notice.setStatus(Notice.Status_open);
		notice.setAuthor(agent.getUsername());
		notice.setIsShow(Notice.Isshow_yes);
		notice.setShopId(1);
		noticeService.addNotice(notice);
		return "redirect:/shop/list.html";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 初始化公告添加页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_ADD_USER })
	@RequestMapping(value = "/shop/anp", method = RequestMethod.GET)
	public String addNoticePage(@ModelAttribute("notice") Notice notice,
			Model model) {
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/addNotice";
	}

	/**
	 * @author chenhang 2011-3-1
	 * @description 批量删除公告
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_DELETE_USER })
	@RequestMapping(value = "/shop/deleten")
	public String deleteNotice(@RequestParam("ids") String[] ids)
			throws Exception {
		// 获取删除的公告的id数组
		List<Long> noticeIds = new ArrayList();
		if (ids != null && ids.length > 0) {
			for (int i = 0; ids.length > i; i++) {
				Long id = Long.parseLong(ids[i]);
				noticeIds.add(id);
			}
		}
		// 进行删除操作
		noticeService.deleteNotices(noticeIds);
		return "redirect:/shop/list.html";
	}

	/**
	 * @author chenhang 2011-2-28
	 * @description 设置notice是否显示
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/showno", method = RequestMethod.POST)
	public String isshowNotice(@RequestParam("noticeId") String idstr,
			Model model) throws Exception {

		// 获取公告id参数
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "公告信息有误");
			return "/shop/shop_error";
		}

		// 根据id取得公告信息
		Notice newnotice = noticeService.getNotice(id);
		if (newnotice == null) {
			model.addAttribute("message", "公告信息有误");
			return "/shop/shop_error";
		}
		// 如果原来为显示状态则设置成不显示，如果为不显示状态则设置成显示
		if (Notice.Isshow_no == newnotice.getIsShow()) {
			newnotice.setIsShow(Notice.Isshow_yes);
		} else {
			newnotice.setIsShow(Notice.Isshow_no);
		}
		// 进行是否显示操作
		noticeService.isshowNotice(newnotice);
		return "redirect:/shop/list.html";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 修改公告
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/updaten", method = RequestMethod.POST)
	public String updateNotice(@ModelAttribute("notice") Notice notice,
			BindingResult result, Model model,
			@RequestParam("noticeId") String idstr) throws Exception {

		Long id = Long.parseLong(idstr);
		if (id == null) {
			model.addAttribute("message", "公告信息有误");
			return "/shop/shop_error";
		}

		noticeAddValidator.validate(notice, result);
		if (result.hasErrors()) {
			model.addAttribute("noticeId", idstr);
			model.addAttribute("notice", notice);
			model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
			return "/shop/updateNotice";
		}

		notice.setId(id);
		notice.setTitle(notice.getTitle().trim());
		// 进行更新操作
		noticeService.updateNotice(notice);
		return "redirect:/shop/list.html";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 初始化公告修改页面
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_MODIFY_USER })
	@RequestMapping(value = "/shop/unp", method = RequestMethod.GET)
	public String updateNoticePage(@RequestParam("noticeId") String idstr,
			Model model) throws Exception {
		// 获取公告id参数
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "公告信息有误");
			return "/shop/shop_error";
		}

		// 根据id取得公告信息
		Notice notice = noticeService.getNotice(id);
		model.addAttribute("notice", notice);
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/updateNotice";
	}

	/**
	 * @author chenhang 2011-2-29
	 * @description 公告详细页面
	 */
	@RequestMapping(value = "/shop/previewn")
	public String previewNotice(Model model,
			@RequestParam("noticeId") String idstr) throws Exception {

		// 获取公告id参数
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		// 根据id取得公告信息
		Notice notice = noticeService.getNotice(id);
		model.addAttribute("notice", notice);
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/previewNotice";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description 单个公告删除
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_DELETE_USER })
	@RequestMapping(value = "/shop/deletens", method = RequestMethod.POST)
	public String deleteNoticeSingle(@RequestParam("noticeId") String idstr)
			throws Exception {

		// 获取公告id参数
		if (StringUtil.isNotBlank(idstr)) {
			Long id = Long.parseLong(idstr);
			noticeService.removeNotice(id);
		}
		return "redirect:/shop/list.html";
	}
}
