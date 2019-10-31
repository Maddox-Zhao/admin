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
			errors.rejectValue("brandName", "", "请填写品牌名称");
		} else {
			if (brand.getBrandName().length() > 60) {
				errors.rejectValue("brandName", "", "品牌名称不能超过60个字符");
			}
		}

		{
			if (brand.getBrandDesc().length() > 5000) {
				errors.rejectValue("brandDesc", "", "备注长度不能大于5000个字符");
			}
		}

		if (StringUtil.isBlank(brand.getLink().trim())
				|| StringUtil.isEmpty(brand.getLink().trim())) {
			errors.rejectValue("link", "", "请填写URL");
		}
	}
}
