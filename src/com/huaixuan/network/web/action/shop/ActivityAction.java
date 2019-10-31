package com.huaixuan.network.web.action.shop;

import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.shop.Activity;
import com.huaixuan.network.biz.domain.shop.ActivityGoods;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.goods.GoodsManager;
import com.huaixuan.network.biz.service.shop.ActivityGoodsService;
import com.huaixuan.network.biz.service.shop.ActivityService;
import com.huaixuan.network.web.action.BaseAction;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class ActivityAction extends BaseAction{
	private static final long serialVersionUID = 2141188134410687276L;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityGoodsService activityGoodsService;
	@Autowired
	private GoodsManager goodsManager;
	private static final int PAGE_SIZE = 10;

	private @Value("${liangpin99.url}")
	String liangpin99url;

	private static int maxSize = 200; // 200KB

	/**
	 *
	 * @author chenhang 2011-3-17
	 * @description 专场页面列表
	 * @return
	 * @throws Exception
	 */
	@AdminAccess({ EnumAdminPermission.A_ACTIVITY_VIEW_USER })
	@RequestMapping(value = "/shop/activitylist")
	public String activitylist(
			Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage)
			throws Exception {
		QueryPage queryPage = activityService.getActivitysPage(currPage,
				PAGE_SIZE);
		if (queryPage != null) {
			model.addAttribute("query", queryPage);
		}
		model.addAttribute("liangpin99url", liangpin99url);
		return "/shop/activitylist";
	}

	/**
	 *
	 * @author chenhang 2011-3-17
	 * @description 新增专场页面初始化
	 * @return
	 */
	@AdminAccess({ EnumAdminPermission.A_ACTIVITY_ADD_USER })
	@RequestMapping(value = "/shop/addActivityPage")
	public String addActivityPage(@ModelAttribute("activity") Activity activity) {
		return "/shop/activityadd";
	}

	/**
	 *
	 * @author chenhang 2011-3-17
	 * @description 新增专场
	 * @return
	 */
	@RequestMapping(value = "/shop/insertActivity")
	public String insertActivity(@ModelAttribute("activity") Activity activity,
			BindingResult result, Model model,
			MultipartHttpServletRequest request) throws Exception {
		Date date = new Date();
		activity.setStatus("1");// 鍙戝竷鐘舵
		activity.setGmtCreate(date);
		activity.setGmtModify(date);

		try {
			List<MultipartFile> files = request.getFiles("images");
			/** 取出图片信息 */

			if (files != null && files.size() > 0) {
				int i = 0;
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						if (extIsAllowed(getExtension(file
								.getOriginalFilename()))) {
							if (file.getSize() > (maxSize * 1024)) {
								if (i == 0) {
									result.rejectValue("img", "",
											"图片应小于200k");
								} else if (i == 1) {
									result.rejectValue("imgSmall", "",
											"图片应小于200k");
								} else {
									result.rejectValue("imgHomeSamll", "",
											"图片应小于200k");
								}
								return "/shop/activityadd";
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
							if (i == 0) {
								result.rejectValue("img", "", "图片格式不正确");
							} else if (i == 1) {
								result.rejectValue("imgSmall", "", "图片格式不正确");
							} else {
								result.rejectValue("imgHomeSamll", "", "图片格式不正确");
							}
							return "/shop/activityadd";
						}
					}
					i++;
				}
			}

			long activityId = activityService.addActivity(activity, files);
			return "redirect:/shop/updateActivityPage.html?id=" + activityId;
//			return "/shop/activityadd";
		} catch (Exception e) {
			return "/shop/shop_error";
		}
	}

	/**
	 *
	 * @author chenhang 2011-3-17
	 * @description 选择活动商品
	 * @param activity
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/addGoodsByActivityId")
	public String addGoodsByActivityId(
			@ModelAttribute("activity") Activity activity,
			HttpServletRequest request, Model model) {
		String idStr = request.getParameter("activityId");
		String categoryName = request.getParameter("categoryName");
		String oldCategoryName = request.getParameter("oldCategoryName");
		String goodsIds = request.getParameter("goodsIds");
		long activityId = 0;
		if (StringUtil.isNotBlank(idStr)) {
			activityId = Long.parseLong(idStr);
		} else {
			model.addAttribute("message", "该专场不存在！");
			return "/shop/shop_error";
		}
		if (StringUtil.isBlank(categoryName)) {
			return "redirect:/shop//shop/updateActivityPage.html?id="
					+ activityId;
		}
		String[] idsArray = goodsIds.split(",");
		if(idsArray!=null && idsArray.length>0){
			Map parMap = new HashMap();
			parMap.put("activityId", activityId);
			parMap.put("goodsIds", idsArray);
			parMap.put("category", categoryName);
			parMap.put("oldCategoryName", oldCategoryName);
			activityGoodsService.addMoreActivityGoods(parMap);
		}
		return "redirect:/shop/updateActivityPage.html?id=" + activityId;
	}

	/**
	 *
	 * @author chenhang 2011-3-17
	 * @description 删除关联的活动商品
	 * @param activity
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/deleteActivityGoods")
	public String deleteActivityGoods(
			@ModelAttribute("activity") Activity activity,
			@ModelAttribute("goods")Goods goods,
			HttpServletRequest request, Model model) {
		String idStr = request.getParameter("activityId");
		String categoryName = request.getParameter("categoryName");
		String goodsId =null;
		goodsId = request.getParameter("goodsId");
		if(goodsId==null){
			goodsId=goods.getIds();
		}
		
		long gId = 0;
		long activityId = 0;
		
		
		
		
		if (StringUtil.isNotBlank(idStr)) {
			activityId = Long.parseLong(idStr);
		} else {
			return "redirect:/shop/updateActivityPage.html?id=" + activityId;
		}
		if (StringUtil.isBlank(categoryName)) {
			return "redirect:/shop/updateActivityPage.html?id=" + activityId;
		}
/*		if (StringUtil.isNotBlank(goodsId)) {
			gId = Long.parseLong(goodsId);
		} else {
			return "redirect:/shop/updateActivityPage.html?id=" + activityId;
		}*/
		Map parMap = new HashMap();
		parMap.put("activityId", activityId);
		parMap.put("category", categoryName);
		
		
		//批量删除
		if(goodsId!=null){
			String[] goodIds = goodsId.split(",");
			for(String ids: goodIds){
				gId = Long.parseLong(ids);
				parMap.put("goodsId", gId);
				List<ActivityGoods> actGoodsList = activityGoodsService
						.getActivityGoodssByMap(parMap);
				if (actGoodsList != null && actGoodsList.size() > 0) {
					activityGoodsService.removeActivityGoods(actGoodsList.get(0)
							.getId());
				}
			}
			//return "redirect:/shop/updateActivityPage.html?id=" + activityId;
		}
		
		return "redirect:/shop/updateActivityPage.html?id=" + activityId;
		
	}

	@AdminAccess({ EnumAdminPermission.A_ACTIVITY_MODIFY_USER })
	@RequestMapping(value = "/shop/updateActivityPage")
	public String updateActivityPage(
			@ModelAttribute("goods") Goods goods,
			@ModelAttribute("activity") Activity activity,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currPage,
			@RequestParam("id") String idstr, Model model) throws Exception {
		long activityId = 0;
		if (StringUtil.isNotBlank(idstr)) {
			activityId = Long.parseLong(idstr);
		} else {
			return "/shop/shop_error";
		}
		activity = activityService.getActivity(activityId);
		List<ActivityGoods> activityGoodsCategorys = activityGoodsService
				.getCategoryByActivityId(activityId);
//		List<ActivityGoods> activityGoodss = activityGoodsService
//				.getByActivityId(activityId);//
		String category1 = new String();
		String category2 = new String();
		String category3 = new String();
		String category4 = new String();
		String category5 = new String();
		List<Goods> listgoods1 = new ArrayList<Goods>();
		List<Goods> listgoods2 = new ArrayList<Goods>();
		List<Goods> listgoods3 = new ArrayList<Goods>();
		List<Goods> listgoods4 = new ArrayList<Goods>();
		List<Goods> listgoods5 = new ArrayList<Goods>();
		if (null != activityGoodsCategorys && activityGoodsCategorys.size() > 0) {
			for (int i = 0; i < activityGoodsCategorys.size(); i++) {
				if (i == 0) {
					List<Long> goodsIdList0 = new ArrayList<Long>();
					category1 = activityGoodsCategorys.get(i).getCategory();
					model.addAttribute("category1", category1);
/*					if (null != category1 && category1.length() > 0) {
						for (int j = 0; j < activityGoodss.size(); j++) {
							String thecategory = activityGoodss.get(j)
									.getCategory().trim();
							String thegoodsId = String.valueOf(activityGoodss
									.get(j).getGoodsId());
							if (thecategory.equals(category1)) {
								goodsIdList0.add(Long.valueOf(thegoodsId));
							}
						}
					}*/
					//修改专场分页
					QueryPage page = goodsManager.getGoodsActivityByCnditionWithPage(activity, currPage, this.pageSize);
					model.addAttribute("query", page);
					//listgoods1 = goodsManager.getGoodsByIds(goodsIdList0);
					model.addAttribute("listgoods1", page.getItems());
				}
				/*if (i == 1) {
					List<Long> goodsIdList1 = new ArrayList<Long>();
					category2 = activityGoodsCategorys.get(i).getCategory();
					model.addAttribute("category2", category2);
					if (null != category2 && category2.length() > 0) {
						for (int j = 0; j < activityGoodss.size(); j++) {
							String thecategory = activityGoodss.get(j)
									.getCategory().trim();
							String thegoodsId = String.valueOf(activityGoodss
									.get(j).getGoodsId());
							if (thecategory.equals(category2)) {
								goodsIdList1.add(Long.valueOf(thegoodsId));
							}
						}
					}
					listgoods2 = goodsManager.getGoodsByIds(goodsIdList1);
					model.addAttribute("listgoods2", listgoods2);
				}*/
				/*if (i == 2) {
					List<Long> goodsIdList2 = new ArrayList<Long>();
					category3 = activityGoodsCategorys.get(i).getCategory();
					model.addAttribute("category3", category3);
					if (null != category3 && category3.length() > 0) {
						for (int j = 0; j < activityGoodss.size(); j++) {
							String thecategory = activityGoodss.get(j)
									.getCategory().trim();
							String thegoodsId = String.valueOf(activityGoodss
									.get(j).getGoodsId());
							if (thecategory.equals(category3)) {
								goodsIdList2.add(Long.valueOf(thegoodsId));
							}
						}
					}
					listgoods3 = goodsManager.getGoodsByIds(goodsIdList2);
					model.addAttribute("listgoods3", listgoods3);
				}*/
				/*if (i == 3) {
					List<Long> goodsIdList3 = new ArrayList<Long>();
					category4 = activityGoodsCategorys.get(i).getCategory();
					model.addAttribute("category4", category4);
					if (null != category4 && category4.length() > 0) {
						for (int j = 0; j < activityGoodss.size(); j++) {
							String thecategory = activityGoodss.get(j)
									.getCategory().trim();
							String thegoodsId = String.valueOf(activityGoodss
									.get(j).getGoodsId());
							if (thecategory.equals(category4)) {
								goodsIdList3.add(Long.valueOf(thegoodsId));
							}
						}
					}
					listgoods4 = goodsManager.getGoodsByIds(goodsIdList3);
					model.addAttribute("listgoods4", listgoods4);
				}*/
				/*if (i == 4) {
					List<Long> goodsIdList4 = new ArrayList<Long>();
					category5 = activityGoodsCategorys.get(i).getCategory();
					model.addAttribute("category5", category5);
					if (null != category5 && category5.length() > 0) {
						for (int j = 0; j < activityGoodss.size(); j++) {
							String thecategory = activityGoodss.get(j)
									.getCategory().trim();
							String thegoodsId = String.valueOf(activityGoodss
									.get(j).getGoodsId());
							if (thecategory.equals(category5)) {
								goodsIdList4.add(Long.valueOf(thegoodsId));
							}
						}
					}
					listgoods5 = goodsManager.getGoodsByIds(goodsIdList4);
					model.addAttribute("listgoods5", listgoods5);
				}*/
			}
		}
		model.addAttribute("activity", activity);
		return "/shop/activityupdate";
	}

	@RequestMapping(value = "/shop/updateActity")
	public String updateActity(@ModelAttribute("activity") Activity activity,
			BindingResult result, Model model,
			MultipartHttpServletRequest request) {
		Date date = new Date();
		Activity activity2 = activityService.getActivity(activity.getId());
		activity2.setTitle(activity.getTitle());
		activity2.setGmtStart(activity.getGmtStart());
		activity2.setGmtEnd(activity.getGmtEnd());
		activity2.setContext(activity.getContext());
		// activity2.setImg(activity.getImg());
		activity2.setGmtModify(date);
		activity2.setActivityPosition(activity.getActivityPosition());

		try {
			List<MultipartFile> files = request.getFiles("images");
			if (files != null && files.size() > 0) {
				int i = 0;
				for (MultipartFile file : files) {
					if (file.getSize() > 0) {
						if (extIsAllowed(getExtension(file
								.getOriginalFilename()))) {
							if (file.getSize() > (maxSize * 1024)) {
								if (i == 0) {
									result.rejectValue("img", "",
											"图片应小于200k");
									model.addAttribute("errorMessage1", "图片应小于200k");
								} else if (i == 1) {
									result.rejectValue("imgSmall", "",
											"图片应小于200k");
									model.addAttribute("errorMessage2", "图片应小于200k");
								} else {
									result.rejectValue("imgHomeSamll", "",
											"图片应小于200k");
									model.addAttribute("errorMessage3", "图片应小于200k");
								}
								activity = activityService.getActivity(activity.getId());
								model.addAttribute("activity", activity);
								return "/shop/activityupdate";
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
							if (i == 0) {
								result.rejectValue("img", "", "图片格式不正确");
								model.addAttribute("errorMessage1", "图片格式不正确");
							} else if (i == 1) {
								result.rejectValue("imgSmall", "", "图片格式不正确");
								model.addAttribute("errorMessage2", "图片格式不正确");
							} else {
								result.rejectValue("imgHomeSamll", "", "图片格式不正确");
								model.addAttribute("errorMessage3", "图片格式不正确");
							}
							activity = activityService.getActivity(activity.getId());
							model.addAttribute("activity", activity);
							return "/shop/activityupdate";
						}
					}
					i++;
				}
			}
			activityService.editActivity(activity2, files);
		} catch (Exception e) {
			return "/shop/shop_error";
		}

		return "redirect:/shop/activitylist.html";
	}

	/*modify:增加发布逻辑，每个位置当且仅当没有任何已发布的的活动信息时，才可以发布
	 * time:2018/5/2
	 * by:Hu Honfei
	 * 
	 * 
	 * */
	@RequestMapping(value = "/shop/publishActivity")
	public String publishActivity(HttpServletRequest request, Model model) {
		String idstr = request.getParameter("id");
		
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "专场不存在！");
			return "/shop/shop_error";
		}
		String position = activityService.getActivityPosition(id);
		int count = activityService.validateGoodsCount(id);
		if(activityService.validatePublish(position) && count > 0){//活动商品不为0时允许发布
		//获取活动
		Activity activity = activityService.getActivity(id);
		//设置状态为2（已发布）
		activity.setStatus("2");
		//执行update
		activityService.editActivity(activity);
		return "redirect:/shop/activitylist.html";
		}else if (activityService.validatePublish(position) && count == 0) {//活动商品为0时无法发布
			model.addAttribute("message","当前活动没有活动商品，请先添加活动商品再执行发布！");
			return "/shop/shop_error";
		}else{
			model.addAttribute("message","当前位置已存在已发布的活动，请先下架再执行发布！");
			return "/shop/shop_error";
		}
	}

	@RequestMapping(value = "/shop/cancelActivity")
	public String cancelActivity(HttpServletRequest request, Model model) {
		String idstr = request.getParameter("id");
		Long id = null;
		if (StringUtil.isNotBlank(idstr)) {
			id = Long.parseLong(idstr);
		} else {
			model.addAttribute("message", "专场不存在！");
			return "/shop/shop_error";
		}
		Activity activity = activityService.getActivity(id);
		activity.setStatus("1");// 鐘舵
		activityService.editActivity(activity);
		return "redirect:/shop/activitylist.html";
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
