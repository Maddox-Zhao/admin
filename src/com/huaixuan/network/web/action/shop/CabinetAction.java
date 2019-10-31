package com.huaixuan.network.web.action.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.goods.Category;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.shop.Showcase;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.enums.EnumCabinetType;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.CategoryManager;
import com.huaixuan.network.biz.service.shop.CabinetService;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.biz.service.shop.ShowcaseService;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class CabinetAction {
	private static final long serialVersionUID = -5368640116134065978L;
	@Autowired
	private CabinetService cabinetService;
	@Autowired
	private ShowcaseService showcaseService;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private ShopInfoService shopInfoService;
	@Autowired
	private Validator cabinetValidator;
	private static final int PAGE_SIZE = 10;

	private static int maxSize = 200; // 200KB

	// /**
	// * Grab the entity from the database before populating with request
	// * parameters
	// */
	// public void prepare() throws Exception {
	// cabinets = cabinetManager.getCabinets();
	// }

	/**
	 * @author chenhang
	 * @description 橱窗列表
	 */
	@AdminAccess({ EnumAdminPermission.A_CAB_VIEW_USER })
	@RequestMapping(value = "/shop/cablist")
	public String cabinetList(@ModelAttribute("cabinet") Cabinet cabinet,
			Model model) throws Exception {
		List<Cabinet> cabinets = cabinetService.getCabinets();
		model.addAttribute("cabinets", cabinets);
		model.addAttribute("enumCabinetTypeMap", EnumCabinetType.toMap());
		return "/shop/cabinetList";
	}

	@RequestMapping(value = "/shop/showcp")
	public String showcasePage(
			@ModelAttribute("cabinet") Cabinet cabinet,
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		String cabinetIdstr = request.getParameter("cabinetId");
		String catCode = request.getParameter("catCode");
		String pageFlag = request.getParameter("pageFlag");
		if (StringUtil.isBlank(cabinetIdstr)) {
			return "/shop/shop_error";
		}
		Long cabinetId = Long.parseLong(cabinetIdstr);
		cabinet = cabinetService.getCabinet(cabinetId);
		cabinet.setIsShowstr(((Integer) cabinet.getIsShow()).toString());
		cabinet.setSortstr(((Integer) cabinet.getSort()).toString());
		cabinet.setCabinetKeyword(StringUtil.substringAfterLast(
				cabinet.getCabinetUrl(), "="));
		List<Category> categorysFirst = categoryManager.getCategoryForGuide();
		for (Category category : categorysFirst) {
			category.setCache(genarateListString(category.getDepth()));
		}
		model.addAttribute("categorysFirst", categorysFirst);
		if ("first".equals(pageFlag) || "y".equals(pageFlag)) {
			getshowcaseQueryPage(cabinet, model, currPage);
		} else {
			getshowcaseQueryPage(cabinet, model, 1);
		}
		if ("first".equals(pageFlag) || "n".equals(pageFlag)) {
			getgoodsQueryPage(model, currPage);
		} else {
			getgoodsQueryPage(model, 1);
		}
		model.addAttribute("cabinet", cabinet);
		model.addAttribute("enumCabinetTypeMap", EnumCabinetType.toMap());
		return "/shop/showcase";
	}

	public void getgoodsQueryPage(Model model, int currPage) throws Exception {
		QueryPage queryGoodsPage = cabinetService.showcaseGoods("", "", "",
				0.0, 0.0, currPage, PAGE_SIZE, "", "", null);
		if (queryGoodsPage != null) {
			model.addAttribute("queryGoodsPage", queryGoodsPage);
		}
	}

	public void getshowcaseQueryPage(Cabinet cabinet, Model model, int currPage)
			throws Exception {
		QueryPage queryshowcasePage = showcaseService.getShowcasesPage(
				cabinet.getId(), currPage, PAGE_SIZE);
		if (queryshowcasePage != null) {
			model.addAttribute("queryshowcasePage", queryshowcasePage);
		}
	}

	// /**
	// * 鍒犻櫎showcase
	// *
	// * @return "success" if no exceptions thrown
	// */
	// public String deleteShowcase() throws Exception {
	// String showcaseIdstr = getRequest().getParameter("showcaseId");
	// Long showcaseId = null;
	// if (StringUtils.isNotBlank(showcaseIdstr)) {
	// showcaseId = Long.parseLong(showcaseIdstr);
	// } else {
	// return ERROR;
	// }
	//
	// showcaseManager.removeShowcase(showcaseId);
	//
	// return SUCCESS;
	// }

	/**
	 * @author chenhang 2011-3-18
	 * @description 新增橱窗
	 */
	@AdminAccess({ EnumAdminPermission.A_CAB_ADD_USER })
	@RequestMapping(value = "/shop/addcab")
	public String addCabinet(@ModelAttribute("cabinet") Cabinet cabinet,
			BindingResult result, Model model, MultipartHttpServletRequest request) throws Exception {

		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);

		String isshowstr = cabinet.getIsShowstr();
		if (StringUtil.isNotBlank(isshowstr)) {
			if ("1".equals(isshowstr)) {
				cabinet.setIsShow(Cabinet.isShow_show);
			} else {
				cabinet.setIsShow(Cabinet.isShow_noshow);
			}
		}

		if (shopInfo != null) {
			cabinet.setShopId(shopInfo.getId());
		} else {
			model.addAttribute("message", "店铺信息有误！");
			return "/shop/shop_error";
		}
		cabinetValidator.validate(cabinet, result);
		if (result.hasErrors()) {
			cabinet.setIsadd("yes");
			model.addAttribute("cabinet", cabinet);
			List<Cabinet> cabinets = cabinetService.getCabinets();
			model.addAttribute("cabinets", cabinets);
			return "/shop/cabinetList";
		}
		String urlPrefix = "l/search.html?more=";
		cabinet.setCabinetUrl(urlPrefix + cabinet.getCabinetKeyword().trim());

		cabinet.setCabinetName(cabinet.getCabinetName().trim());
		cabinet.setCabinetKeyword(StringUtil.substringAfterLast(
				cabinet.getCabinetUrl(), "="));

		/** 验证图片信息 */
		List<MultipartFile> files = request.getFiles("images");
		if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
                        if (file.getSize() > (maxSize * 1024)) {
                            result.rejectValue("cabinetPic", "", "图片应小于200k");
                            cabinet.setIsadd("yes");
                            model.addAttribute("cabinet", cabinet);
                            return "/shop/cabinetList";
                        }
                    } else {
                        result.rejectValue("cabinetPic", "", "图片格式不正确");
                        cabinet.setIsadd("yes");
                        model.addAttribute("cabinet", cabinet);
                        return "/shop/cabinetList";
                    }
                }
            }
        }

		cabinetService.addCabinet(cabinet, files);
		return "redirect:/shop/cablist.html";
	}

	@AdminAccess({ EnumAdminPermission.A_CAB_MODIFY_USER })
	@RequestMapping("/shop/updatecab")
	public String updateCabinet(@ModelAttribute("cabinet") Cabinet cabinet,
			BindingResult result, Model model, MultipartHttpServletRequest request) throws Exception {

		String isshowstr = cabinet.getIsShowstr();
		if (StringUtil.isNotBlank(isshowstr)) {
			if ("1".equals(isshowstr)) {
				cabinet.setIsShow(Cabinet.isShow_show);
			} else {
				cabinet.setIsShow(Cabinet.isShow_noshow);
			}
		}

		String sortstr = cabinet.getSortstr();
		if (StringUtil.isNotBlank(sortstr)) {
			cabinet.setSort((int) Integer.parseInt(sortstr));
		}

		String urlPrefix = "l/search.html?more=";
		cabinet.setCabinetUrl(urlPrefix + cabinet.getCabinetKeyword().trim());
		cabinet.setCabinetName(cabinet.getCabinetName().trim());

		/** 验证图片信息 */
		List<MultipartFile> files = request.getFiles("images");
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
                        if (file.getSize() > (maxSize * 1024)) {
                            result.rejectValue("cabinetPic", "", "图片应小于200k");
                            cabinet = cabinetService.getCabinet(cabinet.getId());
                            model.addAttribute("cabinet", cabinet);
                            model.addAttribute("errormessage", "图片应小于200k");
                            return "/shop/updateBrand";
                        }
                    } else {
                        result.rejectValue("brandTemp", "", "图片格式不正确");
                        cabinet = cabinetService.getCabinet(cabinet.getId());
                        model.addAttribute("cabinet", cabinet);
                        model.addAttribute("errormessage", "图片应小于200k");
                        return "/shop/updateBrand";
                    }
                }
            }
        }

		cabinetService.editCabinet(cabinet, files);
		return "redirect:/shop/cablist.html";
	}

	/**
	 * 更新showcase上升
	 *
	 * @return "success" if no exceptions thrown
	 */
	public String showcaseUp(HttpServletRequest request, Model model)
			throws Exception {
		// 获取展示ID参数
		String showcaseIdstr = request.getParameter("showcaseId");
		Long showcaseId = null;
		if (StringUtils.isNotBlank(showcaseIdstr)) {
			showcaseId = Long.parseLong(showcaseIdstr);
		} else {
			return "/shop/shop_error";
		}

		// 进行展示上升操作
		showcaseService.showcaseUp(showcaseId);

		return "redirect:/shop/showcp";
	}

	/**
	 * 更新showcase下降
	 *
	 * @return "success" if no exceptions thrown
	 */
	public String showcaseDown(HttpServletRequest request, Model model)
			throws Exception {
		// 获取展示ID参数
		String showcaseIdstr = request.getParameter("showcaseId");
		Long showcaseId = null;
		if (StringUtils.isNotBlank(showcaseIdstr)) {
			showcaseId = Long.parseLong(showcaseIdstr);
		} else {
			return "/shop/shop_error";
		}

		// 进行展示下降操作
		showcaseService.showcaseDown(showcaseId);

		return "redirect:/shop/showcp";
	}

	/**
	 *
	 * @author chenhang 2011-3-18
	 * @description 添加关联商品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/scg")
	public String showcaseGoods(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {

		Long cabId = Long.parseLong(request.getParameter("cabinetId"));
		Cabinet cabinet = cabinetService.getCabinet(cabId);
		cabinet.setIsShowstr(((Integer) cabinet.getIsShow()).toString());
		cabinet.setSortstr(((Integer) cabinet.getSort()).toString());

		/* 获取类目 */
		List<Category> categorysFirst = categoryManager.getCategoryForGuide();
		for (Category category : categorysFirst) {
			category.setCache(genarateListString(category.getDepth()));
		}

		String deleteGoodsTag = request.getParameter("deleteGoodsTag");
		/* 删除橱柜中的商品 */
		if ("yes".equals(deleteGoodsTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "该橱窗位不存在！");
				return "/shop/shop_error";
			}

			showcaseService.removeShowcase(showcaseId);
		}

		String modifyTag = request.getParameter("modifyTag");
		/* 修改商品副名称 */
		if ("yes".equals(modifyTag)) {
			String showcaseViceName = request.getParameter("viceName");
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "不存在该橱窗位！");
				return "/shop/shop_error";
			}
			Showcase showcaseModify = showcaseService.getShowcase(showcaseId);

			showcaseModify.setViceName(showcaseViceName);
			showcaseService.editShowcase(showcaseModify);

		}

		String upTag = request.getParameter("upTag");
		/* 上升橱柜中的商品的位置 */
		if ("yes".equals(upTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "不存在该橱窗位！");
				return "/shop/shop_error";
			}

			showcaseService.showcaseUp(showcaseId);
		}

		String downTag = request.getParameter("downTag");
		/* 下降橱柜中的商品的位置 */
		if ("yes".equals(downTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "不存在该橱窗位！");
				return "/shop/shop_error";
			}

			showcaseService.showcaseDown(showcaseId);
		}

		String addTag = request.getParameter("addTag");
		/* 添加商品到橱柜中 */
		if ("yes".equals(addTag)) {
			String arr = request.getParameter("objs");
			String[] ids = arr.split(",");
			if (ids != null && ids.length > 0) {
				List<Long> goodsIds = new ArrayList();
				for (int i = 0; ids.length > i; i++) {
					if (StringUtil.isNotBlank(ids[i])) {
						Long id = Long.valueOf(ids[i]);
						goodsIds.add(id);
					}
				}
				// 每个橱窗中的商品数不能超过15个 edit by zyq for更新为不能超过25个针对熙浪需求
				int goodsnum = showcaseService.getGoodsNumByCabId(cabId);

				if ((goodsnum + ids.length) <= 25) {
					Boolean doadd = true;
					List<Showcase> showcaseList = new ArrayList<Showcase>();
					for (int i = 0; i < goodsIds.size(); i++) {
						Showcase showcaseold = showcaseService
								.getShowcaseByGoodsIdAndCabId(goodsIds.get(i),
										cabId);
						if (showcaseold != null) {
							doadd = false;
							showcaseList.add(showcaseold);
						}
					}
					if (doadd) {
						showcaseService.addGoodsToCabinet(goodsIds, cabId);
					} else {
						StringBuffer mes = new StringBuffer();
						for (int j = 0; j < showcaseList.size(); j++) {
							Showcase showcaseinfo = showcaseList.get(j);
							if (j == 0) {
								mes = mes.append(showcaseinfo.getGoodsTitle());
							} else {
								mes = mes.append(","
										+ showcaseinfo.getGoodsTitle());
							}
						}
						model.addAttribute("addGoodsError",
								"添加失败。选中商品中有已经存在于橱窗中的商品，不能重复添加");
					}
				} else {
					model.addAttribute("addGoodsError", "添加失败，橱柜不能超过25个商品。原有"
							+ goodsnum + "个商品，现在再加" + ids.length + "个商品超过容量");
				}
			}
		}

		List<Cabinet> cabinets = cabinetService.getCabinets();
		getshowcaseQueryPage(cabinet, model, currPage);

		// 恢复参数默认值
		addTag = "no";
		deleteGoodsTag = "no";
		upTag = "no";
		downTag = "no";

		// 搜索条件价格区间
		double priceLow = 0.0;
		double priceHigh = 0.0;
		String priceLowstr = request.getParameter("priceLowstr");
		Pattern emailRegex = Pattern.compile("^\\d*(\\.\\d{0,2})?$");
		if (StringUtils.isNotBlank(priceLowstr)) {

			// 正则表达式对价格参数进行验证
			Matcher matcher = emailRegex.matcher(priceLowstr);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				priceLow = (double) Double.parseDouble(priceLowstr);
			} else {
				// 不符合正则表达式的，返回页面
				// ActionContext.getContext().put("priceLowstrError",
				// getText("admin.cabinet.priceLowstr.error"));
				// return INPUT;
				return "/shop/shop_error";
			}
		}
		String priceHighstr = request.getParameter("priceHighstr");
		if (StringUtils.isNotBlank(priceHighstr)) {
			// 正则表达式对价格参数进行验证
			Matcher matcher = emailRegex.matcher(priceHighstr);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				priceHigh = (double) Double.parseDouble(priceHighstr);
			} else {
				// 不符合正则表达式的，返回页面
				// ActionContext.getContext().put("priceHighstrError",
				// getText("admin.cabinet.priceHighstr.error"));
				// return INPUT;
				return "/shop/shop_error";
			}

			if (priceLow > priceHigh) {
				// 价格从小到大填写
				// ActionContext.getContext().put("priceHighstrError",
				// getText("admin.cabinet.priceLowerHigh.error"));
				// return INPUT;
			}
		}
		String catCode = request.getParameter("catCode");
		String goodsName = request.getParameter("goodsName");
		String goodsSn = request.getParameter("goodsSn");
		String selectSort = request.getParameter("selectSort");
		String isCutprice = request.getParameter("isCutprice");
		String goodsNumber = request.getParameter("goodsNumber");
		model.addAttribute("catCode", catCode);
		model.addAttribute("goodsName", goodsName);
		model.addAttribute("goodsSn", goodsSn);
		model.addAttribute("selectSort", selectSort);
		model.addAttribute("isCutprice", isCutprice);
		model.addAttribute("goodsNumber", goodsNumber);
		model.addAttribute("priceLowstr", priceLowstr);
		model.addAttribute("priceHighstr", priceHighstr);
		Long gn = null;
		if (StringUtil.isNotBlank(goodsNumber)) {
			gn = Long.valueOf(goodsNumber);
		}
		QueryPage queryGoodsPage = cabinetService.showcaseGoods(catCode,
				goodsName, goodsSn, priceLow, priceHigh, currPage, PAGE_SIZE,
				selectSort, isCutprice, gn);
		if (queryGoodsPage != null) {
			model.addAttribute("queryGoodsPage", queryGoodsPage);
		}
		cabinet.setIsShowstr(((Integer) cabinet.getIsShow()).toString());
		cabinet.setSortstr(((Integer) cabinet.getSort()).toString());
		cabinet.setCabinetKeyword(StringUtil.substringAfterLast(
				cabinet.getCabinetUrl(), "="));
		model.addAttribute("categorysFirst", categorysFirst);
		model.addAttribute("cabinet", cabinet);

		return "/shop/showcase";
	}

	private String genarateListString(int depth) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < depth; i++) {
			sb.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;");
		}
		return sb.toString();
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
