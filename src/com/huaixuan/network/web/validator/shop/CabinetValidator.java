/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-22
 */
public class CabinetValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {

		Cabinet cabinet = (Cabinet) obj;

		if (StringUtil.isBlank(cabinet.getCabinetName().trim())
				|| StringUtil.isEmpty(cabinet.getCabinetName().trim())) {
			errors.rejectValue("cabinetName", "", "请填写标题名称");
		} else {
			if (cabinet.getCabinetName().length() > 60) {
				errors.rejectValue("cabinetName", "", "标题名称长度不能大于60个字符");
			}
		}

		if (StringUtil.isBlank(cabinet.getCabinetKeyword().trim())
				|| StringUtil.isEmpty(cabinet.getCabinetKeyword().trim())) {
			errors.rejectValue("cabinetKeyword", "", "请填写关键字");
		} else {
			if (cabinet.getCabinetKeyword().length() > 60) {
				errors.rejectValue("cabinetKeyword", "", "关键字长度不能大于40个字符");
			}
		}

	}
}
