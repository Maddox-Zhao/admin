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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.shop.FriendLink;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumNoticeType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.FriendLinkService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class FriendLinkAction extends BaseAction {
	private static final int PAGE_SIZE = 10;
	private static final long serialVersionUID = 6163599870107849549L;
	@Autowired
	private FriendLinkService friendLinkService;
	@Autowired
	private ShopInfoService shopInfoService;
	@Autowired
	private Validator friendLinkAddValidator;

	private static int maxSize = 200; // 200KB

	/**
	 * @author chenhang
	 * @description 友情链接列表页
	 */
	@AdminAccess({ EnumAdminPermission.A_FL_VIEW_USER })
	@RequestMapping(value = "/shop/fllist")
	public String friendLinkList(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {

		QueryPage queryPage = this.friendLinkService.getFriendLinksPage(1,
				currPage, PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}

		return "/shop/friendLinkList";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 删除友情链接
	 * @param idstr
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_FL_DELETE_USER })
	@RequestMapping(value = "/shop/deletefl")
	public String deleteFriendLink(@RequestParam("friendLinkId") String idstr)
			throws Exception {
		Long friendLinkId = null;
		if (StringUtil.isNotBlank(idstr)) {
			friendLinkId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		friendLinkService.removeFriendLink(friendLinkId);
		return "redirect:/shop/fllist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-8
	 * @description 新增友情链接初始化页面
	 * @param friendLink
	 * @param model
	 * @return
	 */
	@AdminAccess({ EnumAdminPermission.A_FL_ADD_USER })
	@RequestMapping(value = "/shop/addflp", method = RequestMethod.GET)
	public String addFriendLinkPage(
			@ModelAttribute("friendLink") FriendLink friendLink, Model model) {
		return "/shop/addFriendLink";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 新增友情链接
	 * @param friendLink
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/addfl", method = RequestMethod.POST)
	public String addFriendLink(
			@ModelAttribute("friendLink") FriendLink friendLink,
			BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {

		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		if (shopInfo != null) {
			friendLink.setShopId(shopInfo.getId());
		} else {
			model.addAttribute("message", "店铺信息有误");
			return "/shop/shop_error";
		}

		friendLinkAddValidator.validate(friendLink, result);
		if (result.hasErrors()) {
			model.addAttribute("friendLink", friendLink);
			model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
			return "/shop/addFriendLink";
		}

		List<MultipartFile> files = request.getFiles("images");
		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				if (file.getSize() > 0) {
					if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
						if (file.getSize() > (maxSize * 1024)) {
							result.rejectValue("linkLogo", "", "图片应小于200k");
							model.addAttribute("friendLink", friendLink);
							model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
							return "/shop/addFriendLink";
						} else {
							// InputStream stream = file.getInputStream();
							// Image src = ImageIO.read(stream);
							// int width = src.getWidth(null);
							// int height = src.getHeight(null);
							// if (width > 250 || height > 100) {
							// result.rejectValue("shopLogo", "",
							// "图片像素宽必须在250像素内，高度在100像素以！");
							// return "/shop/shopInfo";
							// }
						}
					} else {
						result.rejectValue("linkLogo", "", "图片格式不正确");
						model.addAttribute("friendLink", friendLink);
						model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
						return "/shop/addFriendLink";
					}
				}
			}
		}
		friendLink.setLinkName(friendLink.getLinkName().trim());
		friendLink.setLinkUrl(friendLink.getLinkUrl().trim());

		friendLinkService.addFriendLink(friendLink, files);
		return "redirect:/shop/fllist.html";
	}

	/**
	 * @author chenhang 2011-3-8
	 * @description 修改友情链接初始化页面
	 *
	 */
	@AdminAccess({ EnumAdminPermission.A_FL_MODIFY_USER })
	@RequestMapping(value = "/shop/updateflp", method = RequestMethod.GET)
	public String updateFriendLinkPage(
			@RequestParam("friendLinkId") String idstr,
			@ModelAttribute("friendLink") FriendLink friendLink, Model model)
			throws Exception {
		Long friendLinkId = null;
		if (StringUtil.isNotBlank(idstr)) {
			friendLinkId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}

		friendLink = friendLinkService.getFriendLink(friendLinkId);
		friendLink.setSortstr(((Integer) friendLink.getSort()).toString());
		model.addAttribute("friendLink", friendLink);
		return "/shop/updateFriendLink";
	}

	/**
	 *
	 * @author chenhang 2011-3-14
	 * @description 修改链接
	 * @param friendLink
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/updatefl", method = RequestMethod.POST)
	public String updateFriendLink(
			@ModelAttribute("friendLink") FriendLink friendLink,
			BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {
		Long id = friendLink.getId();
		if (id == null) {
			model.addAttribute("message", "该链接不存在");
			return "/shop/shop_error";
		}
		friendLinkAddValidator.validate(friendLink, result);
		if (result.hasErrors()) {
			model.addAttribute("friendLink", friendLink);
			model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
			return "/shop/updateFriendLink";
		}
		List<MultipartFile> files = request.getFiles("images");
		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				if (file.getSize() > 0) {
					if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
						if (file.getSize() > (maxSize * 1024)) {
							result.rejectValue("linkTemp", "", "图片应小于200k");
							friendLink = friendLinkService.getFriendLink(id);
							model.addAttribute("friendLink", friendLink);
							model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
							model.addAttribute("errormessage", "图片应小于200k");
							return "/shop/updateFriendLink";
						} else {
							// InputStream stream = file.getInputStream();
							// Image src = ImageIO.read(stream);
							// int width = src.getWidth(null);
							// int height = src.getHeight(null);
							// if (width > 250 || height > 100) {
							// result.rejectValue("shopLogo", "",
							// "图片像素宽必须在250像素内，高度在100像素以！");
							// return "/shop/shopInfo";
							// }
						}
					} else {
						result.rejectValue("linkTemp", "", "图片格式不正确");
						friendLink = friendLinkService.getFriendLink(id);
						model.addAttribute("friendLink", friendLink);
						model.addAttribute("enumNoticeTypeMap", EnumNoticeType.toMap());
						model.addAttribute("errormessage", "图片格式不正确");
						return "/shop/updateFriendLink";
					}
				}
			}
		}
		friendLink.setLinkName(friendLink.getLinkName().trim());
		friendLink.setLinkUrl(friendLink.getLinkUrl().trim());
		friendLinkService.editFriendLink(friendLink, files);
		return "redirect:/shop/fllist.html";
	}

	/**
	 * 判断扩展名是否允许的方法
	 */
	private boolean extIsAllowed(String ext) {
		ext = ext.toLowerCase();
		ArrayList allowList = new ArrayList();
		allowList.add("jpg");
		allowList.add("gif");
		allowList.add("png");

		if (allowList.contains(ext)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取扩展名的方法
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
