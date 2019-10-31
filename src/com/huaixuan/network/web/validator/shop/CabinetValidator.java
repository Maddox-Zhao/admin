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
			errors.rejectValue("cabinetName", "", "����д��������");
		} else {
			if (cabinet.getCabinetName().length() > 60) {
				errors.rejectValue("cabinetName", "", "�������Ƴ��Ȳ��ܴ���60���ַ�");
			}
		}

		if (StringUtil.isBlank(cabinet.getCabinetKeyword().trim())
				|| StringUtil.isEmpty(cabinet.getCabinetKeyword().trim())) {
			errors.rejectValue("cabinetKeyword", "", "����д�ؼ���");
		} else {
			if (cabinet.getCabinetKeyword().length() > 60) {
				errors.rejectValue("cabinetKeyword", "", "�ؼ��ֳ��Ȳ��ܴ���40���ַ�");
			}
		}

	}
}
