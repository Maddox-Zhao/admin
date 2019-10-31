/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.ShopInfo;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-16
 */
public class ShopInfoUpdateValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {

		ShopInfo shopInfo = (ShopInfo) obj;

		if (shopInfo.getShopName().length() > 200) {
			errors.rejectValue("shopName", "", "店铺名称不能超过200个字符");
		}

		if (StringUtil.isBlank(shopInfo.getShopTitle().trim())
				|| StringUtil.isEmpty(shopInfo.getShopTitle().trim())) {
			errors.rejectValue("shopTitle", "", "请填写店铺标题");
		} else {
			if (shopInfo.getShopTitle().length() > 64) {
				errors.rejectValue("shopTitle", "", "店铺标题不能超过40个字符");
			}
		}

		if (shopInfo.getShopDesc().length() > 200) {
			errors.rejectValue("shopDesc", "", "商品描述不能超过5000个字符");
		}

		if (shopInfo.getAddress().length() > 200) {
			errors.rejectValue("address", "", "地址不能超过120个字符");
		}

		if (shopInfo.getQq().length() > 200) {
			errors.rejectValue("qq", "", "qq不能超过200个字符");
		}

		if (shopInfo.getWw().length() > 200) {
			errors.rejectValue("ww", "", "旺旺不能超过255个字符");
		}

		if (shopInfo.getYm().length() > 200) {
			errors.rejectValue("ym", "", "Yahoo Message不能超过255个字符");
		}

		if (shopInfo.getMsn().length() > 200) {
			errors.rejectValue("msn", "", "msn不能超过200个字符");
		}

		if (shopInfo.getEmail().length() > 200) {
			errors.rejectValue("email", "", "email不能超过60个字符");
		}

		if (shopInfo.getPhone().length() > 200) {
			errors.rejectValue("phone", "", "联系电话不能超过255个字符");
		}

		if (shopInfo.getServiceTel().length() > 200) {
			errors.rejectValue("serviceTel", "", "服务热线不能超过30个字符");
		}

		if (shopInfo.getReceiveAccount().length() > 200) {
			errors.rejectValue("receiveAccount", "", "收款帐号不能超过60个字符");
		}
	}
}
