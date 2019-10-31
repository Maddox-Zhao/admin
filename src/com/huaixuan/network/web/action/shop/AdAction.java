package com.huaixuan.network.web.action.shop;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.domain.shop.AdPosition;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.AdDetailService;
import com.huaixuan.network.biz.service.shop.AdPositionService;
import com.huaixuan.network.biz.service.shop.AdService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.huaixuan.network.web.validator.shop.AdValidator;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class AdAction {

	// private static final long serialVersionUID = 3153392977387508657L;
	private static final int PAGE_SIZE = 10;
	@Autowired
	private AdService adService;
	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private AdDetailService adDetailService;
	@Autowired
	private AdValidator adValidator;

	// /**
	// * Grab the entity from the database before populating with request
	// parameters
	// */
	//
	// public void validate(){
	// try {
	// adPositions = adPositionManager.getAdPositions();
	//
	// String flag = getRequest().getParameter("flag");
	// if(StringUtils.isNotBlank(flag) && "add".equalsIgnoreCase(flag)){
	// //鍥剧墖鍍忕礌鍒ゆ柇
	// Boolean imagetag = false;
	// if(images!=null && images.length>0){
	// /*File image = images[0];
	// //浣嶇疆1瀵瑰簲鐨勫浘鐗囧儚绱
	// if(ad.getAdPositionId()==1){
	// imagetag = ImageUtil.isImageGreatThanSize(image, 525, 215);
	// if(!imagetag){
	// this.addFieldError("images",
	// getText("admin.shop.add.ad.imageposition1"));
	// }
	// }
	// //浣嶇疆2瀵瑰簲鐨勫浘鐗囧儚绱
	// else if(ad.getAdPositionId()==2){
	// imagetag = ImageUtil.isImageGreatThanSize(image, 250, 80);
	// if(!imagetag){
	// this.addFieldError("images",
	// getText("admin.shop.add.ad.imageposition2"));
	//
	// }
	// }*/
	//
	// }else{
	// this.addFieldError("images", "蹇呴』涓婁紶鍥剧墖");
	// }
	// }
	//
	// /*if(StringUtils.isNotBlank(flag) && "update".equalsIgnoreCase(flag)){
	// //鍥剧墖鍍忕礌鍒ゆ柇
	// Ad adold = adManager.getAd(ad.getId());
	// if(adold.getAdPositionId()!=ad.getAdPositionId()){
	// if(images==null || images.length==0){
	// this.addFieldError("images", "骞垮憡浣嶆敼鍙橈紝蹇呴』閫夋嫨鐩稿簲鍍忕礌鐨勫浘鐗");
	// }
	// }
	//
	// Boolean imagetag = false;
	// if(images!=null && images.length>0){
	// File image = images[0];
	// //浣嶇疆1瀵瑰簲鐨勫浘鐗囧儚绱
	// if(ad.getAdPositionId()==1){
	// imagetag = ImageUtil.isImageGreatThanSize(image, 525, 215);
	// if(!imagetag){
	// this.addFieldError("images",
	// getText("admin.shop.add.ad.imageposition1"));
	// }
	// }
	// //浣嶇疆2瀵瑰簲鐨勫浘鐗囧儚绱
	// else if(ad.getAdPositionId()==2){
	// imagetag = ImageUtil.isImageGreatThanSize(image, 250, 80);
	// if(!imagetag){
	// this.addFieldError("images",
	// getText("admin.shop.add.ad.imageposition2"));
	// }
	// }
	//
	// }
	// }*/
	//
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	//
	//
	//
	// }
	/**
	 * @author chenhang 2011-3-3
	 * @description 广告页面显示
	 *
	 */
	@AdminAccess({EnumAdminPermission.A_AD_VIEW_USER})
	@RequestMapping(value = "/shop/adListAdmin")
	public String adlist(
			Model model,
			@RequestParam("adType") String adType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		if (StringUtil.isEmpty(adType)) {
			adType = "d";
		}
		Ad ad = new Ad();
		ad.setAdType(adType);
		QueryPage queryPage = adService.getByMapByConditionWithPage(ad,
				currPage, PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		model.addAttribute("adType", adType);
		return "/shop/ad";
	}

	/**
	 * @author chenhang 2011-3-7
	 * @description 删除广告
	 *
	 */
	@AdminAccess({EnumAdminPermission.A_AD_DELETE_USER})
	@RequestMapping(value = "/shop/deletead", method = RequestMethod.POST)
	public String deletead(@RequestParam("adId") String idstr) {
		Long adId = null;
		if (StringUtil.isNotBlank(idstr)) {
			adId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}

		adService.removeAd(adId);
		return "redirect:/shop/adListAdmin.html?adType=d";
	}

	/**
	 * @author chenhang 2011-3-4
	 * @description 新增广告初始化页面
	 */
	@AdminAccess({EnumAdminPermission.A_AD_ADD_USER})
	@RequestMapping(value = "/shop/addap", method = RequestMethod.GET)
	public String addAdPage(@ModelAttribute("ad") Ad ad, Model model)
			throws Exception {

		List<AdPosition> adPositions = adPositionService.getAdPositions();
		model.addAttribute("adPositions", adPositions);
		return "/shop/addAd";
	}

	/**
	 *
	 * @author chenhang 2011-3-7
	 * @description 新增广告
	 * @param ad
	 * @param adDetail
	 * @param images
	 * @param imagesFileName
	 * @param result
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/addad", method = RequestMethod.POST)
	public String addAd(@ModelAttribute("adDetail") AdDetail adDetail,
			@ModelAttribute("ad") Ad ad, BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {
		List<AdPosition> adPositions = adPositionService.getAdPositions();
		model.addAttribute("adPositions", adPositions);
		ad.setStatus(Ad.Status_open);
		ad.setAdName(ad.getAdName().trim());
		adDetail.setLink(StringEscapeUtils.escapeHtml(ad.getLink().trim()));
		adDetail.setMediaType(ad.getMediaType());
		List<MultipartFile> files = request.getFiles("images");

		adValidator.validate(ad, result);
		if (result.hasErrors()) {
			model.addAttribute("ad", ad);
			return "/shop/addAd";
		}

		try {
			adService.addAd(ad, adDetail, files);
		} catch (ManagerException ex) {
			model.addAttribute("message", "新增广告出错");
			return "/shop/shop_error";
		}
		return "redirect:/shop/adListAdmin.html?adType=d";
	}

	/**
	 *
	 * @author chenhang 2011-3-7
	 * @description
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({EnumAdminPermission.A_AD_MODIFY_USER})
	@RequestMapping(value = "/shop/updateap", method = RequestMethod.GET)
	public String updateAdPage(@RequestParam("adId") String idstr, Model model)
			throws Exception {
		List<AdPosition> adPositions = adPositionService.getAdPositions();

		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}

		Ad ad = adService.getAd(id);
		AdDetail adDetail = adDetailService.getAdDetailByAdId(id);
		if (adDetail == null) {
			return "/shop/shop_error";
		}
		adDetail.setSortstr(((Integer) adDetail.getSort()).toString());
		adDetail.setMediaType(adDetail.getMediaType().trim());
		ad.setAdPositionIdstr(((Long) ad.getAdPositionId()).toString());
		ad.setStartTimeStr(ad.getStartTimeStr());
		ad.setEndTimeStr(ad.getEndTimeStr());
		ad.setSort(adDetail.getSort());
		ad.setLink(adDetail.getLink());
		ad.setMediaType(adDetail.getMediaType());
		model.addAttribute("ad", ad);
		model.addAttribute("adDetail", adDetail);
		model.addAttribute("adPositions", adPositions);
		return "/shop/updateAd";
	}

	@RequestMapping(value = "/shop/updateAd", method = RequestMethod.POST)
	public String updateAd(@ModelAttribute("adDetail") AdDetail adDetail,
			@ModelAttribute("ad") Ad ad, BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {
		List<AdPosition> adPositions = adPositionService.getAdPositions();
		List<MultipartFile> files = request.getFiles("images");
		Long id = ad.getId();
		if (id == null) {
			return "/shop/shop_error";
		}

		adValidator.validate(ad, result);
		if (result.hasErrors()) {
			model.addAttribute("adPositions", adPositions);
			model.addAttribute("ad", ad);
			return "/shop/updateAd";
		}
		Boolean tag = Boolean.TRUE;
		if (ad.getAdPositionId() == 0) {
			// ActionContext.getContext().put("postionIdnotExist",
			// getText("admin.ad.postionId.error"));
		} else {
			for (AdPosition adPosition : adPositions) {
				if (ad.getAdPositionId() == adPosition.getId()) {
					tag = Boolean.FALSE;
				}
			}
			if (tag) {
				// ActionContext.getContext().put("postionIdnotExist",
				// getText("admin.ad.postionId.error"));
			}
		}

		if (tag) {
			// return INPUT;
			return "/shop/shop_error";
		}

		ad.setAdName(ad.getAdName().trim());
		adDetail.setMediaCode(ad.getMediaCode());
		adDetail.setLink(StringEscapeUtils.escapeHtml(ad.getLink().trim()));
		adDetail.setMediaType(ad.getMediaType());
		adDetail.setSort(ad.getSort());

		try {
			adService.updateAd(ad, adDetail, files);
		} catch (ManagerException ex) {
			// request.setAttribute("errorMessage",
			// getText("admin.shop.update.ad.fail"));
			// model.addAttribute("exception", ex);
			return "/shop/shop_error";
		}
		return "redirect:/shop/adListAdmin.html?adType=d";
	}

}
