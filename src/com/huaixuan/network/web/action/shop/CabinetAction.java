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
	 * @description �����б�
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
	// * 删除showcase
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
	 * @description ��������
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
			model.addAttribute("message", "������Ϣ����");
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

		/** ��֤ͼƬ��Ϣ */
		List<MultipartFile> files = request.getFiles("images");
		if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
                        if (file.getSize() > (maxSize * 1024)) {
                            result.rejectValue("cabinetPic", "", "ͼƬӦС��200k");
                            cabinet.setIsadd("yes");
                            model.addAttribute("cabinet", cabinet);
                            return "/shop/cabinetList";
                        }
                    } else {
                        result.rejectValue("cabinetPic", "", "ͼƬ��ʽ����ȷ");
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

		/** ��֤ͼƬ��Ϣ */
		List<MultipartFile> files = request.getFiles("images");
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    if (extIsAllowed(getExtension(file.getOriginalFilename()))) {
                        if (file.getSize() > (maxSize * 1024)) {
                            result.rejectValue("cabinetPic", "", "ͼƬӦС��200k");
                            cabinet = cabinetService.getCabinet(cabinet.getId());
                            model.addAttribute("cabinet", cabinet);
                            model.addAttribute("errormessage", "ͼƬӦС��200k");
                            return "/shop/updateBrand";
                        }
                    } else {
                        result.rejectValue("brandTemp", "", "ͼƬ��ʽ����ȷ");
                        cabinet = cabinetService.getCabinet(cabinet.getId());
                        model.addAttribute("cabinet", cabinet);
                        model.addAttribute("errormessage", "ͼƬӦС��200k");
                        return "/shop/updateBrand";
                    }
                }
            }
        }

		cabinetService.editCabinet(cabinet, files);
		return "redirect:/shop/cablist.html";
	}

	/**
	 * ����showcase����
	 *
	 * @return "success" if no exceptions thrown
	 */
	public String showcaseUp(HttpServletRequest request, Model model)
			throws Exception {
		// ��ȡչʾID����
		String showcaseIdstr = request.getParameter("showcaseId");
		Long showcaseId = null;
		if (StringUtils.isNotBlank(showcaseIdstr)) {
			showcaseId = Long.parseLong(showcaseIdstr);
		} else {
			return "/shop/shop_error";
		}

		// ����չʾ��������
		showcaseService.showcaseUp(showcaseId);

		return "redirect:/shop/showcp";
	}

	/**
	 * ����showcase�½�
	 *
	 * @return "success" if no exceptions thrown
	 */
	public String showcaseDown(HttpServletRequest request, Model model)
			throws Exception {
		// ��ȡչʾID����
		String showcaseIdstr = request.getParameter("showcaseId");
		Long showcaseId = null;
		if (StringUtils.isNotBlank(showcaseIdstr)) {
			showcaseId = Long.parseLong(showcaseIdstr);
		} else {
			return "/shop/shop_error";
		}

		// ����չʾ�½�����
		showcaseService.showcaseDown(showcaseId);

		return "redirect:/shop/showcp";
	}

	/**
	 *
	 * @author chenhang 2011-3-18
	 * @description ��ӹ�����Ʒ
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

		/* ��ȡ��Ŀ */
		List<Category> categorysFirst = categoryManager.getCategoryForGuide();
		for (Category category : categorysFirst) {
			category.setCache(genarateListString(category.getDepth()));
		}

		String deleteGoodsTag = request.getParameter("deleteGoodsTag");
		/* ɾ�������е���Ʒ */
		if ("yes".equals(deleteGoodsTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "�ó���λ�����ڣ�");
				return "/shop/shop_error";
			}

			showcaseService.removeShowcase(showcaseId);
		}

		String modifyTag = request.getParameter("modifyTag");
		/* �޸���Ʒ������ */
		if ("yes".equals(modifyTag)) {
			String showcaseViceName = request.getParameter("viceName");
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "�����ڸó���λ��");
				return "/shop/shop_error";
			}
			Showcase showcaseModify = showcaseService.getShowcase(showcaseId);

			showcaseModify.setViceName(showcaseViceName);
			showcaseService.editShowcase(showcaseModify);

		}

		String upTag = request.getParameter("upTag");
		/* ���������е���Ʒ��λ�� */
		if ("yes".equals(upTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "�����ڸó���λ��");
				return "/shop/shop_error";
			}

			showcaseService.showcaseUp(showcaseId);
		}

		String downTag = request.getParameter("downTag");
		/* �½������е���Ʒ��λ�� */
		if ("yes".equals(downTag)) {
			String showcaseIdstr = request.getParameter("showcaseId");
			Long showcaseId = null;
			if (StringUtils.isNotBlank(showcaseIdstr)) {
				showcaseId = Long.parseLong(showcaseIdstr);
			} else {
				model.addAttribute("message", "�����ڸó���λ��");
				return "/shop/shop_error";
			}

			showcaseService.showcaseDown(showcaseId);
		}

		String addTag = request.getParameter("addTag");
		/* �����Ʒ�������� */
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
				// ÿ�������е���Ʒ�����ܳ���15�� edit by zyq for����Ϊ���ܳ���25�������������
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
								"���ʧ�ܡ�ѡ����Ʒ�����Ѿ������ڳ����е���Ʒ�������ظ����");
					}
				} else {
					model.addAttribute("addGoodsError", "���ʧ�ܣ������ܳ���25����Ʒ��ԭ��"
							+ goodsnum + "����Ʒ�������ټ�" + ids.length + "����Ʒ��������");
				}
			}
		}

		List<Cabinet> cabinets = cabinetService.getCabinets();
		getshowcaseQueryPage(cabinet, model, currPage);

		// �ָ�����Ĭ��ֵ
		addTag = "no";
		deleteGoodsTag = "no";
		upTag = "no";
		downTag = "no";

		// ���������۸�����
		double priceLow = 0.0;
		double priceHigh = 0.0;
		String priceLowstr = request.getParameter("priceLowstr");
		Pattern emailRegex = Pattern.compile("^\\d*(\\.\\d{0,2})?$");
		if (StringUtils.isNotBlank(priceLowstr)) {

			// ������ʽ�Լ۸����������֤
			Matcher matcher = emailRegex.matcher(priceLowstr);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				priceLow = (double) Double.parseDouble(priceLowstr);
			} else {
				// ������������ʽ�ģ�����ҳ��
				// ActionContext.getContext().put("priceLowstrError",
				// getText("admin.cabinet.priceLowstr.error"));
				// return INPUT;
				return "/shop/shop_error";
			}
		}
		String priceHighstr = request.getParameter("priceHighstr");
		if (StringUtils.isNotBlank(priceHighstr)) {
			// ������ʽ�Լ۸����������֤
			Matcher matcher = emailRegex.matcher(priceHighstr);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				priceHigh = (double) Double.parseDouble(priceHighstr);
			} else {
				// ������������ʽ�ģ�����ҳ��
				// ActionContext.getContext().put("priceHighstrError",
				// getText("admin.cabinet.priceHighstr.error"));
				// return INPUT;
				return "/shop/shop_error";
			}

			if (priceLow > priceHigh) {
				// �۸��С������д
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
     * ��ȡ��չ���ķ���
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
