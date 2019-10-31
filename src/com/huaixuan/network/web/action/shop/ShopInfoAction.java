package com.huaixuan.network.web.action.shop;

import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.huaixuan.network.biz.domain.admin.AdminAgent;
import com.huaixuan.network.biz.domain.express.Region;
import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.huaixuan.network.biz.domain.user.UserAddress;
import com.huaixuan.network.biz.enums.EnumAdminPermission;
import com.huaixuan.network.biz.service.express.RegionManager;
import com.huaixuan.network.biz.service.shop.ShopInfoService;
import com.huaixuan.network.biz.service.user.UserAddressManager;
import com.huaixuan.network.web.action.interceptor.AdminAccess;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * Action for facilitating User Management feature.
 */
@Controller
public class ShopInfoAction {
	private static final long serialVersionUID = -8691976128481127548L;
	@Autowired
	private UserAddressManager userAddressManager;
	@Autowired
	private RegionManager regionManager;
	@Autowired
	private ShopInfoService shopInfoService;
	@Autowired
	private Validator shopInfoUpdateValidator;

	 private static  int maxSize = 200;  //200KB

	// public void validate() {
	// // 鍒濆鍖栫渷浠借祫婧
	// cityList = regionManager.getRegionByType(3);
	// // 鍒濆鍖栧煄甯傝祫婧
	// distincList = regionManager.getRegionByType(4);
	// // 鍒濆鍖栧尯鍘胯祫婧
	// provinceList = regionManager.getRegionByType(2);
	// if (userAddress != null) {
	// // 鍒濆鍖栧綋鍓嶇渷浠界殑鍩庡競璧勬簮
	// cityListInit = regionManager.getRegionChilds(userAddress
	// .getProvince());
	// // 鍒濆鍖栧綋鍓嶅煄甯傜殑鍖哄幙璧勬簮
	// distincListInit = regionManager.getRegionChilds(userAddress
	// .getCity());
	// }
	// try {
	// if (images != null && images.length > 0) {
	//
	// File image = images[0];
	// // 鍥剧墖鍍忕礌鍒ゆ柇
	// Boolean imagetag = false;
	// imagetag = ImageUtil.isImageSizeTooBig(image, 250, 100);
	// if (!imagetag) {
	// this.addFieldError("images",
	// getText("admin.shop.logo.images.type"));
	// }
	// }
	// } catch (IOException e) {
	// log.error(e.getMessage());
	// }
	//
	// }
	/**
	 * @author chenhang 2011-3-16
	 * @description 店铺信息页面初始化
	 */
	@AdminAccess({EnumAdminPermission.A_SINFO_MODIFY_USER})
	@RequestMapping(value = "/shop/sinfo")
	public String shopInfo(Model model, HttpServletRequest request)
			throws Exception {

		ShopInfo shopInfo = shopInfoService.getShopInfo(1L);
		// shopInfo = shopInfoManager.getShopInfoByUserId(user.getId());
		UserAddress userAddress = new UserAddress();
		if (shopInfo != null) {
			userAddress = userAddressManager.getUserAddress(shopInfo
					.getAddressId());
			model.addAttribute("userAddress", userAddress);
		}
		List<Region> cityList = regionManager.getRegionByType(3);
		List<Region> distincList = regionManager.getRegionByType(4);
		List<Region> provinceList = regionManager.getRegionByType(2);
		if (userAddress != null) {
			List<Region> cityListInit = regionManager
					.getRegionChilds(userAddress.getProvince());
			model.addAttribute("cityListInit", cityListInit);
			List<Region> distincListInit = regionManager
					.getRegionChilds(userAddress.getCity());
			model.addAttribute("distincListInit", distincListInit);
		}

		// String tag = getRequest().getParameter("tag");
		// if (StringUtils.isNotBlank(tag) && "yes".equalsIgnoreCase(tag)) {
		// ActionContext.getContext().put("updateSuccess",
		// getText("admin.shopinfo.update.success"));
		// }
		model.addAttribute("shopInfo", shopInfo);
		// 更新成功信息
		String message = "n";
		message = request.getParameter("message");
		if ("y".equals(message))// 判断页面的
			model.addAttribute("message", message);

		model.addAttribute("cityList", cityList);
		model.addAttribute("distincList", distincList);
		model.addAttribute("provinceList", provinceList);
		return "/shop/shopInfo";
	}

	@RequestMapping(value = "/shop/updatesi")
	public String updateShopInfo(@ModelAttribute("shopInfo") ShopInfo shopInfo,
			BindingResult result, Model model, AdminAgent agent,
			MultipartHttpServletRequest request) throws Exception {
		UserAddress userAddress = new UserAddress();
		if (StringUtil.isNotBlank(shopInfo.getAddress())) {
			userAddress.setAddress(shopInfo.getAddress().trim());
		}
		userAddress.setDistrict(shopInfo.getDistrict().trim());
		userAddress.setCity(shopInfo.getCity().trim());
		userAddress.setProvince(shopInfo.getProvince().trim());
		shopInfoUpdateValidator.validate(shopInfo, result);
		if (result.hasErrors()) {
			initShopInfo(shopInfo, model, userAddress);
			return "/shop/shopInfo";
		}

		// User user = this.getLoginUser();
		ShopInfo oldShopInfo = shopInfoService.getShopInfo(1L);
		oldShopInfo.setUserId(agent.getId());

		if (StringUtil.isNotBlank(shopInfo.getShopName())) {
			oldShopInfo.setShopName(shopInfo.getShopName().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getShopTitle())) {
			oldShopInfo.setShopTitle(shopInfo.getShopTitle().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getShopDesc())) {
			oldShopInfo.setShopDesc(shopInfo.getShopDesc().trim());
		}
		if (StringUtil.isNotBlank(userAddress.getAddress())) {
			userAddress.setAddress(userAddress.getAddress().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getQq())) {
			oldShopInfo.setQq(shopInfo.getQq().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getWw())) {
			oldShopInfo.setWw(shopInfo.getWw().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getYm())) {
			oldShopInfo.setYm(shopInfo.getYm().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getMsn())) {
			oldShopInfo.setMsn(shopInfo.getMsn().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getEmail())) {
			oldShopInfo.setEmail(shopInfo.getEmail().trim());
		}
		if (StringUtil.isNotBlank(shopInfo.getPhone())) {
			oldShopInfo.setPhone(shopInfo.getPhone().trim());
		}
		oldShopInfo.setReceiveAccount(shopInfo.getReceiveAccount().trim());

		ShopInfo shopInfonew = shopInfoService.getShopInfo(oldShopInfo.getId());
		List<MultipartFile> files = request.getFiles("images");


        /** 取出图片信息*/

        if(files!=null && files.size() > 0){
            for(MultipartFile file : files){
            	if(file.getSize() > 0){
            		if(extIsAllowed(getExtension(file.getOriginalFilename()))){
            			if(file.getSize() > (maxSize * 1024)){
                           result.rejectValue("shopLogo", "", "图片应小于200k");
               			initShopInfo(shopInfo, model, userAddress);
                   		   return "/shop/shopInfo";
            			}
            			else{
                    		InputStream stream = file.getInputStream();
                    		Image src = ImageIO.read(stream);
                    		int width = src.getWidth(null);
                    		int height = src.getHeight(null);
                    		if(width>250||height>100){
                    			result.rejectValue("shopLogo", "", "图片像素宽必须在250像素内，高度在100像素以！");
                    			return "/shop/shopInfo";
                    		}
            			}
            		}else{
                       result.rejectValue("shopLogo", "", "图片格式不正确");
           			initShopInfo(shopInfo, model, userAddress);
               		   return "/shop/shopInfo";
            		}
            	}
            }
        }


		if (shopInfonew == null) {
			shopInfoService.addShopInfo(oldShopInfo, userAddress, files, null);
		} else {
			shopInfoService.updateShopInfo(oldShopInfo, userAddress, files,
					null);
		}

		return "redirect:/shop/sinfo.html?message=y";
	}

	public void initShopInfo(ShopInfo shopInfo, Model model,
			UserAddress userAddress) {
		List<Region> cityList = regionManager.getRegionByType(3);
		List<Region> distincList = regionManager.getRegionByType(4);
		List<Region> provinceList = regionManager.getRegionByType(2);
		if (userAddress != null) {
			List<Region> cityListInit = regionManager
					.getRegionChilds(userAddress.getProvince());
			model.addAttribute("cityListInit", cityListInit);
			List<Region> distincListInit = regionManager
					.getRegionChilds(userAddress.getCity());
			model.addAttribute("distincListInit", distincListInit);
		}
		model.addAttribute("cityList", cityList);
		model.addAttribute("distincList", distincList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("shopInfo", shopInfo);
		model.addAttribute("userAddress", userAddress);
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
