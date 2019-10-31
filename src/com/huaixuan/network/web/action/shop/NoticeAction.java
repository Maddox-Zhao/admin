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
	 * @description �����listҳ��
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
	 * @description ��������
	 * @return
	 */
	@RequestMapping(value = "/shop/addn", method = RequestMethod.POST)
	public String addNotice(@ModelAttribute("notice") Notice notice,
			BindingResult result, Model model, AdminAgent agent)
			throws Exception {

		// ��ȡ�û�������Ϣ
		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		// �趨����shopid
		if (shopInfo != null) {
			notice.setShopId(shopInfo.getId());
		} else {
			model.addAttribute("message", "������Ϣ����");
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
	 * @description ��ʼ���������ҳ��
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
	 * @description ����ɾ������
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_DELETE_USER })
	@RequestMapping(value = "/shop/deleten")
	public String deleteNotice(@RequestParam("ids") String[] ids)
			throws Exception {
		// ��ȡɾ���Ĺ����id����
		List<Long> noticeIds = new ArrayList();
		if (ids != null && ids.length > 0) {
			for (int i = 0; ids.length > i; i++) {
				Long id = Long.parseLong(ids[i]);
				noticeIds.add(id);
			}
		}
		// ����ɾ������
		noticeService.deleteNotices(noticeIds);
		return "redirect:/shop/list.html";
	}

	/**
	 * @author chenhang 2011-2-28
	 * @description ����notice�Ƿ���ʾ
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/showno", method = RequestMethod.POST)
	public String isshowNotice(@RequestParam("noticeId") String idstr,
			Model model) throws Exception {

		// ��ȡ����id����
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}

		// ����idȡ�ù�����Ϣ
		Notice newnotice = noticeService.getNotice(id);
		if (newnotice == null) {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}
		// ���ԭ��Ϊ��ʾ״̬�����óɲ���ʾ�����Ϊ����ʾ״̬�����ó���ʾ
		if (Notice.Isshow_no == newnotice.getIsShow()) {
			newnotice.setIsShow(Notice.Isshow_yes);
		} else {
			newnotice.setIsShow(Notice.Isshow_no);
		}
		// �����Ƿ���ʾ����
		noticeService.isshowNotice(newnotice);
		return "redirect:/shop/list.html";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description �޸Ĺ���
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
			model.addAttribute("message", "������Ϣ����");
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
		// ���и��²���
		noticeService.updateNotice(notice);
		return "redirect:/shop/list.html";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description ��ʼ�������޸�ҳ��
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_MODIFY_USER })
	@RequestMapping(value = "/shop/unp", method = RequestMethod.GET)
	public String updateNoticePage(@RequestParam("noticeId") String idstr,
			Model model) throws Exception {
		// ��ȡ����id����
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}

		// ����idȡ�ù�����Ϣ
		Notice notice = noticeService.getNotice(id);
		model.addAttribute("notice", notice);
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/updateNotice";
	}

	/**
	 * @author chenhang 2011-2-29
	 * @description ������ϸҳ��
	 */
	@RequestMapping(value = "/shop/previewn")
	public String previewNotice(Model model,
			@RequestParam("noticeId") String idstr) throws Exception {

		// ��ȡ����id����
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		// ����idȡ�ù�����Ϣ
		Notice notice = noticeService.getNotice(id);
		model.addAttribute("notice", notice);
		model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
		return "/shop/previewNotice";
	}

	/**
	 *
	 * @author chenhang 2011-2-28
	 * @description ��������ɾ��
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_AFFICHE_DELETE_USER })
	@RequestMapping(value = "/shop/deletens", method = RequestMethod.POST)
	public String deleteNoticeSingle(@RequestParam("noticeId") String idstr)
			throws Exception {

		// ��ȡ����id����
		if (StringUtil.isNotBlank(idstr)) {
			Long id = Long.parseLong(idstr);
			noticeService.removeNotice(id);
		}
		return "redirect:/shop/list.html";
	}
}
