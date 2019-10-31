package com.huaixuan.network.web.action.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.BrandCategory;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.goods.GoodsRuleManager;
import com.huaixuan.network.biz.service.shop.BrandCategoryService;
import com.huaixuan.network.biz.service.shop.BrandService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class BrandAction {
	private static final int PAGE_SIZE = 10;
	private static final long serialVersionUID = 7254054243169712138L;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ShopInfoService shopInfoService;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private BrandCategoryService brandCategoryService;
	// private List<BrandCategory> brandCategoryList;
	// private List<Category> categoryList;
	// private ReturnPointManager returnPointManager;
	// private List<ReturnPoint> returnPointList = new ArrayList<ReturnPoint>();
	// private List<BrandRule> brandRules = new ArrayList<BrandRule>();
	// private List<BrandRule> brandRulelist;
	@Autowired
	private GoodsRuleManager goodsRuleManager;
	@Autowired
	private Validator brandValidator;

	private static int maxSize = 200; // 200KB

	/*
	 * public List<ReturnPoint> getReturnPointList() { return returnPointList; }
	 *
	 * public void setReturnPointList(List<ReturnPoint> returnPointList) {
	 * this.returnPointList = returnPointList; }
	 */

	// public void validate() {
	// try {
	// String flag = getRequest().getParameter("flag");
	// if (StringUtils.isNotBlank(flag) && "add".equalsIgnoreCase(flag)) {
	// /* 判断上传图片是否为空的，为空则返回错� */
	// if (images == null || images.length == 0) {
	// this.addFieldError("images", "必须上传图片");
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// }
	// }
	//

	/**
	 * @author chenhang
	 * @description Ʒ�ƹ����ʼ��ҳ��
	 */
	@AdminAccess({ EnumAdminPermission.A_BRAND_VIEW_USER })
	@RequestMapping(value = "/shop/blist")
	public String brandList(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			HttpServletRequest request) throws Exception {

		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		if (shopInfo == null) {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}

		// String tag = getRequest().getParameter("tag");
		// if (StringUtils.isNotBlank(tag) && "yes".equalsIgnoreCase(tag)) {
		// ActionContext.getContext().put("brandDeleteError",
		// getText("admin.user.brand.deleteError"));
		// }

		QueryPage queryPage = this.brandService.getBrandListPage(1, currPage,
				PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		// ������Ϣ
		String message = "n";
		message = request.getParameter("message");
		if ("y".equals(message))// �ж�ҳ���
			model.addAttribute("message", message);
		return "/shop/brandList";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description ɾ��Ʒ��
	 * @param idstr
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_BRAND_DELETE_USER })
	@RequestMapping(value = "/shop/deletebr")
	public String deleteBrand(@RequestParam("brandId") String idstr, Model model)
			throws Exception {
		Long brandId = null;
		if (StringUtil.isNotBlank(idstr)) {
			brandId = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "��������");
			return "/shop/shop_error";
		}

		Boolean isHaveGoods = brandService.getBrandisHaveGoods(brandId);
		if (isHaveGoods) {
			return "redirect:/shop/blist.html?message=y";
		}

		brandService.removeBrand(brandId);
		return "redirect:/shop/blist.html";
	}

	/**
	 * @author chenhang 2011-3-15
	 * @description ����Ʒ�Ƴ�ʼ��ҳ��
	 */
	@AdminAccess({ EnumAdminPermission.A_BRAND_ADD_USER })
	@RequestMapping(value = "/shop/addbrp")
	public String addBrandPage(@ModelAttribute("brand") Brand brand, Model model)
			throws Exception {
		return "/shop/addBrand";
	}

	@RequestMapping(value = "/shop/addbr")
	public String addBrand(@ModelAttribute("brand") Brand brand,
			BindingResult result, Model model,
			MultipartHttpServletRequest request, HttpServletRequest request1)
			throws Exception {
		String isShow = request1.getParameter("isShow");
		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		if (shopInfo == null) {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}
		List<MultipartFile> files = request.getFiles("images");

		// brand.setIsShow(Brand.IsShow_yes);
		brand.setShopId(shopInfo.getId());

		brand.setBrandName(brand.getBrandName().trim());
		List<Brand> brandList = brandService.getBrandsByName(brand
				.getBrandName());
		if (brandList != null && brandList.size() > 0) {
			model.addAttribute("message", "�Ѿ����ڸ�Ʒ����ƣ�");
			model.addAttribute("isShow", isShow);
			return "/shop/shop_error";
		}

		brand.setLink(brand.getLink().trim());
		brand.setBrandDesc(brand.getBrandDesc().trim());
		brand.setIsShow(Integer.parseInt(isShow));
		brandValidator.validate(brand, result);
		if (result.hasErrors()) {
			model.addAttribute("brand", brand);
			return "/shop/addBrand";
		}
		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				if (file.getSize() > 0) {
					if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
						if (file.getSize() > (maxSize * 1024)) {
							result.rejectValue("brandLogo", "", "ͼƬӦС��200k");
							model.addAttribute("brand", brand);
							model.addAttribute("isShow", isShow);
							return "/shop/addBrand";
						} else {
							// InputStream stream = file.getInputStream();
							// Image src = ImageIO.read(stream);
							// int width = src.getWidth(null);
							// int height = src.getHeight(null);
							// if (width > 250 || height > 100) {
							// result.rejectValue("shopLogo", "",
							// "ͼƬ���ؿ������250�����ڣ��߶���100�����ԣ�");
							// return "/shop/shopInfo";
							// }
						}
					} else {
						result.rejectValue("brandLogo", "", "ͼƬ��ʽ����ȷ");
						model.addAttribute("brand", brand);
						model.addAttribute("isShow", isShow);
						return "/shop/addBrand";
					}
				}
			}
		}
		brandService.addBrand(brand, files);
		return "redirect:/shop/blist.html";
	}

	@AdminAccess({ EnumAdminPermission.A_BRAND_MODIFY_USER })
	@RequestMapping(value = "/shop/updatebrp")
	public String updateBrandPage(@RequestParam("brandId") String idstr,
			@ModelAttribute("brand") Brand brand, Model model) throws Exception {

		Long brandId = null;
		if (StringUtil.isNotBlank(idstr)) {
			brandId = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "��������");
			return "/shop/shop_error";
		}

		brand = brandService.getBrand(brandId);
		model.addAttribute("brand", brand);
		return "/shop/updateBrand";
	}

	@RequestMapping(value = "/shop/updatebr")
	public String updateBrand(@ModelAttribute("brand") Brand brand,
			BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {

		Long id = brand.getId();
		if (id == null || id == 0L) {
			model.addAttribute("message", "������Ϣ����");
			return "/shop/shop_error";
		}

		brand.setBrandName(brand.getBrandName().trim());
		brand.setLink(brand.getLink().trim());
		brand.setBrandDesc(brand.getBrandDesc().trim());
		brandValidator.validate(brand, result);
		if (result.hasErrors()) {
			brand = brandService.getBrand(id);
			model.addAttribute("brand", brand);
			return "/shop/updateBrand";
		}
		List<MultipartFile> files = request.getFiles("images");
		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				if (file.getSize() > 0) {
					if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
						if (file.getSize() > (maxSize * 1024)) {
							result.rejectValue("brandTemp", "", "ͼƬӦС��200k");
							brand = brandService.getBrand(id);
							model.addAttribute("brand", brand);
							model.addAttribute("errormessage", "ͼƬӦС��200k");
							return "/shop/updateBrand";
						} else {
							// InputStream stream = file.getInputStream();
							// Image src = ImageIO.read(stream);
							// int width = src.getWidth(null);
							// int height = src.getHeight(null);
							// if (width > 250 || height > 100) {
							// result.rejectValue("shopLogo", "",
							// "ͼƬ���ؿ������250�����ڣ��߶���100�����ԣ�");
							// return "/shop/shopInfo";
							// }
						}
					} else {
						result.rejectValue("brandTemp", "", "ͼƬ��ʽ����ȷ");
						brand = brandService.getBrand(id);
						model.addAttribute("brand", brand);
						model.addAttribute("errormessage", "ͼƬӦС��200k");
						return "/shop/updateBrand";
					}
				}
			}
		}

		List<Brand> brandList = brandService.getBrandsByName(brand
				.getBrandName());
		if (brandList != null && brandList.size() > 0) {
			if (brandList.size() == 1
					&& brandList.get(0).getId() != brand.getId()) {
				model.addAttribute("message", "�Ѿ����ڸ�Ʒ����ƣ�");
				return "/shop/shop_error";
			}
			if (brandList.size() > 1) {
				model.addAttribute("message", "�Ѿ����ڸ�Ʒ����ƣ�");
				return "/shop/shop_error";
			}
		}

		/* 进行更新brand操作 */
		brandService.updateBrand(brand, files);
		return "redirect:/shop/blist.html";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description Ʒ�ƹ�����Ŀҳ��
	 * @param idstr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/searchbrandcategory")
	public String searchBrandCategory(@RequestParam("brandId") String idstr,
			Model model) {
		Long brandId = null;
		if (StringUtil.isNotBlank(idstr)) {
			brandId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		Brand brand = brandService.getBrand(brandId);
		if (brand != null) {
			List<BrandCategory> brandCategoryList = brandCategoryService
					.getCategoryBybrandId(brand.getId());
			model.addAttribute("brandCategoryList", brandCategoryList);
			model.addAttribute("categoryManager", categoryManager);
			model.addAttribute("brand", brand);
		} else {
			return "/shop/shop_error";
		}
		return "/shop/searchBrandCategory";
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description ɾ�������Ŀ
	 * @return
	 */
	@RequestMapping(value = "/shop/deleteBrandCategory")
	public String deleteBrandCategory(@RequestParam("brandId") String brandstr,
			@RequestParam("brandCategoryId") String idstr, Model model) {
		BrandCategory brandCategory = brandCategoryService
				.getBrandCategoryById(Long.parseLong(idstr));
		if (brandCategory != null) {
			brandCategory.setStatus("n");
			brandCategoryService.updateBrandCategoryStatusById(brandCategory);
		}
		return "redirect:/shop/searchbrandcategory.html?brandId=" + brandstr;
	}

	/**
	 *
	 * @author chenhang 2011-3-15
	 * @description ����������Ŀ��ʼ��ҳ��
	 * @param brandstr
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/addBrandCategoryPage")
	public String addBrandCategoryPage(@RequestParam("brandId") String idstr,
			Model model) {
		Long brandId = null;
		if (StringUtil.isNotBlank(idstr)) {
			brandId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		Brand brand = brandService.getBrand(brandId);
		List<Category> categoryList = categoryManager.getCategorys(true);
		model.addAttribute("brand", brand);
		model.addAttribute("categoryList", categoryList);
		return "/shop/addBrandCategory";
	}

	@RequestMapping(value = "/shop/addBrandCategory")
	public String addBrandCategory(@RequestParam("brandId") String brandId,
			@RequestParam("catCode") String catCode, Model model) {

		if (StringUtil.isNotBlank(catCode) && StringUtil.isNotBlank(brandId)) {
			Map<String, String> parMap = new HashMap<String, String>();
			parMap.put("brandId", brandId);
			parMap.put("catCode", catCode);
			BrandCategory brandCategory = brandCategoryService
					.getBrandCategoryByMap(parMap);
			if (brandCategory != null) {
				brandCategory.setStatus("y");
				brandCategoryService
						.updateBrandCategoryStatusById(brandCategory);
			} else {
				BrandCategory brandCategorytemp = new BrandCategory();
				brandCategorytemp.setBrandId(new Long(brandId));
				brandCategorytemp.setCatCode(catCode);
				brandCategoryService.addBrandCategory(brandCategorytemp);
			}
		}
		return "redirect:/shop/searchbrandcategory.html?brandId=" + brandId;
	}

	// public String relReturnPoint() {
	// ActionContext context = ActionContext.getContext();
	// String brandId = getRequest().getParameter("brandId");
	// String message = getRequest().getParameter("message");
	// String errorMessage = getRequest().getParameter("errorMessage");
	// if (StringUtil.isNotBlank(message)) {
	// context.put("message", message);
	// }
	// if (StringUtil.isNotBlank(errorMessage)) {
	// context.put("errorMessage", errorMessage);
	// }
	// if (StringUtils.isNotBlank(brandId)) {
	// brand = brandManager.getBrand(new Long(brandId));
	// brandRules = brandRuleDao.getRuleBybrandIdWithNames(new Long(
	// brandId));
	// }
	// return SUCCESS;
	// }
	//
	// /**
	// * 跳转添加关联页面
	// *
	// * @return
	// */
	// public String addBrandRulePage() {
	// ActionContext context = ActionContext.getContext();
	// String brandId = getRequest().getParameter("brandId");
	// String message = getRequest().getParameter("message");
	// String errorMessage = getRequest().getParameter("errorMessage");
	// context.put("brandId", brandId);
	// if (StringUtil.isNotBlank(message)) {
	// context.put("message", message);
	// }
	// if (StringUtil.isNotBlank(errorMessage)) {
	// context.put("errorMessage", errorMessage);
	// }
	//
	// if (StringUtils.isBlank(brandId)) {
	// return SUCCESS;
	// }
	// brand = brandManager.getBrand(Long.parseLong(brandId));
	// parMap.put("status", "y");
	// /*
	// * int count = returnPointManager.getCountReturnPointsByMap(parMap);
	// * page = new Page(); page.setPageSize(pageSize);
	// * page.setTotalRowsAmount(count); page.setCurrentPage(currentPage); if
	// * (count > 0) { returnPointList =
	// * returnPointManager.getReturnPointsByMap(parMap, page); }
	// */
	// return SUCCESS;
	// }
	//
	// public String addBrandRule() {
	// /*
	// * ActionContext context = ActionContext.getContext(); String brandId =
	// * getRequest().getParameter("brandId"); context.put("brandId",
	// * brandId); String ruleId = getRequest().getParameter("ruleId"); try {
	// * if (StringUtils.isNotBlank(ruleId) &&
	// * StringUtils.isNotBlank(brandId)) { parMap.put("brandId", "");
	// * parMap.put("returnId", ruleId); parMap.put("status", "y"); brandRules
	// * = brandRuleDao.getBrandRuleByMap(parMap); if (brandRules.size() > 0)
	// * { context.put("errorMessage", "此规则已经关联到品牌，不允许重复添加");
	// * parMap.put("brandId", brandId); return ERROR; } else {
	// * parMap.put("brandId", brandId); parMap.put("returnId", ruleId);
	// * parMap.put("status", "n"); brandRules =
	// * brandRuleDao.getBrandRuleByMap(parMap); if (brandRules.size() > 0) {
	// * for (BrandRule br : brandRules) { br.setReturnId(new Long(ruleId));
	// * br.setStatus("y"); brandRuleDao.updateBrandRuleStatusById(br); } }
	// * else { BrandRule brandRule = new BrandRule();
	// * brandRule.setBrandId(new Long(brandId)); brandRule.setReturnId(new
	// * Long(ruleId)); brandRule.setStatus("y");
	// *
	// * brandRuleDao.addBrandRule(brandRule);
	// *
	// * } ReturnPoint returnPoint = new ReturnPoint(); returnPoint.setId(new
	// * Long(ruleId));
	// * returnPoint.setStatus(EnumReturnPointStatus.RELATED.getKey());
	// * returnPointManager.updateReturnPointStatus(returnPoint);
	// * context.put("message", "添加关联成功�"); } }
	// *
	// * } catch (Exception e) { context.put("errorMessage",
	// * "添加关联失败，请稍�再试！"); return ERROR; }
	// */
	// return SUCCESS;
	// }
	//
	// public String deleteBrandRule() {
	// /*
	// * ActionContext context = ActionContext.getContext(); String
	// * returnPointId = getRequest().getParameter("returnPointId"); String
	// * brandId = getRequest().getParameter("brandId");
	// * context.put("brandId", brandId); try { if
	// * (StringUtils.isNotBlank(returnPointId)) { int i =
	// * goodsRuleManager.countGoodsRuleByReturnId(new Long(returnPointId));
	// * if (i > 0) { context.put("errorMessage",
	// * "该返点规则有商品关联，不允许操作�"); return SUCCESS; } BrandRule brandRule
	// * = new BrandRule(); brandRule.setReturnId(new Long(returnPointId));
	// * brandRule.setStatus("n");
	// *
	// * brandRuleDao.updateBrandRuleStatusById(brandRule);
	// *
	// * ReturnPoint returnPoint = new ReturnPoint(); returnPoint.setId(new
	// * Long(returnPointId));
	// * returnPoint.setStatus(EnumReturnPointStatus.ABLE.getKey());
	// * returnPointManager.updateReturnPointStatus(returnPoint);
	// *
	// * context.put("message", "删除关联成功"); }
	// *
	// * } catch (Exception e) { context.put("errorMessage", "删除关联失败"); }
	// */
	// return SUCCESS;
	// }
	//
	// /**
	// * JQ根据品牌获取规则列表
	// *
	// * @return
	// */
	// public String selectRule() {
	// /*
	// * String brandId = getRequest().getParameter("param"); List<BrandRule>
	// * brandRulelist =
	// * brandRuleDao.getRuleBybrandId(Long.parseLong(brandId)); if
	// * (brandRulelist != null) { for (BrandRule tmp : brandRulelist) {
	// * ReturnPoint rule =
	// * returnPointManager.getReturnPoint(tmp.getReturnId());
	// * returnPointList.add(rule); } } else { returnPointList = null; }
	// */
	// return SUCCESS;
	// }
	//
	// /**
	// * JQ商品修改
	// *
	// * @return
	// */
	// public String selectRuleEdit() {
	// /*
	// * String brandId = getRequest().getParameter("param1"); String goodsId
	// * = getRequest().getParameter("param2"); brandRulelist =
	// * brandRuleDao.getRuleBybrandId(Long.parseLong(brandId));
	// * List<GoodsRule> goodsRuleList =
	// * goodsRuleManager.getGoodsRuleByGoodsId(Long .parseLong(goodsId)); if
	// * (goodsRuleList != null && goodsRuleList.size() > 0) { for (BrandRule
	// * tmp : brandRulelist) { for (GoodsRule tmp2 : goodsRuleList) { if
	// * ((tmp2.getReturnId().equals(tmp.getReturnId())) &&
	// * (tmp2.getStatus().equals("y"))) { tmp.setIsCheck("y"); } else {
	// * tmp.setIsCheck("n"); } ReturnPoint rule =
	// * returnPointManager.getReturnPoint(tmp.getReturnId());
	// * tmp.setRuleName(rule.getName()); } } } else { for (BrandRule tmp :
	// * brandRulelist) { ReturnPoint rule =
	// * returnPointManager.getReturnPoint(tmp.getReturnId());
	// * tmp.setRuleName(rule.getName()); tmp.setIsCheck("n"); } }
	// */
	// return SUCCESS;
	// }

	/**
	 * �ж���չ���Ƿ�����ķ���
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
	 * ��ȡ��չ��ķ���
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	
	@RequestMapping(value = "/shop/addBrandStoreDay")
	public String addBrandStoreDay(HttpServletRequest request, Model model) {
		
		brandService.addBrandStoreDay();
		return "/goods/timetask/checkGoodsNum"; 
	}
	
	@RequestMapping("/brand/getListByBrandName")
	public @ResponseBody Object getBrandByBrandName(HttpServletRequest request)
	{
		String brandName = request.getParameter("brandName");
		if(null != brandName)
			brandName = brandName.toUpperCase();
		List<Brand> brands = brandService.getBrandsByName(brandName);
		return brands;
	}
	
	
	@RequestMapping("/series/getAllSeries")
	public @ResponseBody Object getAllSeries(HttpServletRequest request)
	{
		return brandService.getAllSeries();
	}
	
	@RequestMapping("/size/getAllSize")
	public @ResponseBody Object getAllSize(HttpServletRequest request){
		return brandService.getAllSize();
	}
	
	
}
