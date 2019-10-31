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
			errors.rejectValue("shopName", "", "�������Ʋ��ܳ���200���ַ�");
		}

		if (StringUtil.isBlank(shopInfo.getShopTitle().trim())
				|| StringUtil.isEmpty(shopInfo.getShopTitle().trim())) {
			errors.rejectValue("shopTitle", "", "����д���̱���");
		} else {
			if (shopInfo.getShopTitle().length() > 64) {
				errors.rejectValue("shopTitle", "", "���̱��ⲻ�ܳ���40���ַ�");
			}
		}

		if (shopInfo.getShopDesc().length() > 200) {
			errors.rejectValue("shopDesc", "", "��Ʒ�������ܳ���5000���ַ�");
		}

		if (shopInfo.getAddress().length() > 200) {
			errors.rejectValue("address", "", "��ַ���ܳ���120���ַ�");
		}

		if (shopInfo.getQq().length() > 200) {
			errors.rejectValue("qq", "", "qq���ܳ���200���ַ�");
		}

		if (shopInfo.getWw().length() > 200) {
			errors.rejectValue("ww", "", "�������ܳ���255���ַ�");
		}

		if (shopInfo.getYm().length() > 200) {
			errors.rejectValue("ym", "", "Yahoo Message���ܳ���255���ַ�");
		}

		if (shopInfo.getMsn().length() > 200) {
			errors.rejectValue("msn", "", "msn���ܳ���200���ַ�");
		}

		if (shopInfo.getEmail().length() > 200) {
			errors.rejectValue("email", "", "email���ܳ���60���ַ�");
		}

		if (shopInfo.getPhone().length() > 200) {
			errors.rejectValue("phone", "", "��ϵ�绰���ܳ���255���ַ�");
		}

		if (shopInfo.getServiceTel().length() > 200) {
			errors.rejectValue("serviceTel", "", "�������߲��ܳ���30���ַ�");
		}

		if (shopInfo.getReceiveAccount().length() > 200) {
			errors.rejectValue("receiveAccount", "", "�տ��ʺŲ��ܳ���60���ַ�");
		}
	}
}
