/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Brand;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-22
 */
public class BrandValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		Brand brand = (Brand) obj;

		if (StringUtil.isBlank(brand.getBrandName().trim())
				|| StringUtil.isEmpty(brand.getBrandName().trim())) {
			errors.rejectValue("brandName", "", "����дƷ������");
		} else {
			if (brand.getBrandName().length() > 60) {
				errors.rejectValue("brandName", "", "Ʒ�����Ʋ��ܳ���60���ַ�");
			}
		}

		{
			if (brand.getBrandDesc().length() > 5000) {
				errors.rejectValue("brandDesc", "", "��ע���Ȳ��ܴ���5000���ַ�");
			}
		}

		if (StringUtil.isBlank(brand.getLink().trim())
				|| StringUtil.isEmpty(brand.getLink().trim())) {
			errors.rejectValue("link", "", "����дURL");
		}
	}
}
