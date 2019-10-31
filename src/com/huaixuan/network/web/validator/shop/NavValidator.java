/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Nav;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-16
 */
public class NavValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {

		Nav nav = (Nav) obj;

		if (StringUtil.isBlank(nav.getTitle().trim())
				|| StringUtil.isEmpty(nav.getTitle().trim())) {
			errors.rejectValue("title", "", "����д��������");
		} else {
			if (nav.getTitle().length() > 10) {
				errors.rejectValue("title", "", "�������Ʋ��ܴ���10���ַ�");
			}
		}

		if (StringUtil.isBlank(nav.getLink().trim())
				|| StringUtil.isEmpty(nav.getLink().trim())) {
			errors.rejectValue("link", "", "����д������ַ");
		} else {
			if (nav.getLink().length() > 255) {
				errors.rejectValue("link", "", "���ӵ�ַ���ܴ���255���ַ�");
			}
		}
	}
}
