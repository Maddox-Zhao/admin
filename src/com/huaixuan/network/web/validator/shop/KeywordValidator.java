/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Keyword;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-4-8
 */
public class KeywordValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {

		Keyword keyword = (Keyword) obj;
		if (StringUtil.isBlank(keyword.getDesc())
				|| StringUtil.isEmpty(keyword.getDesc())) {

		} else if (keyword.getDesc().length() > 255) {
			errors.rejectValue("desc", "", "备注长度不能大于255个字符");
		}

		if (StringUtil.isBlank(keyword.getName())
				|| StringUtil.isEmpty(keyword.getName())) {
			errors.rejectValue("name", "", "请填写关键字");
		} else {
			if (keyword.getName().length() > 200) {
				errors.rejectValue("name", "", "关键字长度不能大于200个字符");
			}
		}
	}

}
