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
			errors.rejectValue("title", "", "请填写公告标题");
		} else {
			if (notice.getTitle().length() > 64) {
				errors.rejectValue("title", "", "公告标题不能超过64个字符");
			}
		}

		if (StringUtil.isBlank(notice.getContent())
				|| StringUtil.isEmpty(notice.getContent())) {
			errors.rejectValue("content", "", "请填写公告内容");
		} else {
			if (notice.getContent().length() > 3000) {
				errors.rejectValue("content", "", "公告标题不能超过3000个字符");
			}
		}
	}
}
