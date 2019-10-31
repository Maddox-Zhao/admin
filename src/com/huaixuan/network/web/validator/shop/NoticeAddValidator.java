/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Notice;
import com.huaixuan.network.biz.service.shop.NoticeService;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-9
 */
public class NoticeAddValidator extends ValangValidator {
	@Autowired
	private NoticeService noticeService;

	public void validate(Object obj, Errors errors) {

		Notice notice = (Notice) obj;

		if (StringUtil.isBlank(notice.getTitle())
				|| StringUtil.isEmpty(notice.getTitle())) {
			errors.rejectValue("title", "", "����д�������");
		} else {
			if (notice.getTitle().length() > 64) {
				errors.rejectValue("title", "", "������ⲻ�ܳ���64���ַ�");
			}
		}

		if (StringUtil.isBlank(notice.getContent())
				|| StringUtil.isEmpty(notice.getContent())) {
			errors.rejectValue("content", "", "����д��������");
		} else {
			if (notice.getContent().length() > 3000) {
				errors.rejectValue("content", "", "������ⲻ�ܳ���3000���ַ�");
			}
		}
	}
}
