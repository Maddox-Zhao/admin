/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.FriendLink;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-14
 */
public class FriendLinkAddValidator extends ValangValidator {

	public void validate(Object obj, Errors errors) {

		FriendLink friendLink = (FriendLink) obj;

		String parttern = "^http(s)?://\\S*";// URL
		Pattern patternNum = Pattern.compile(parttern);

		if (StringUtil.isBlank(friendLink.getLinkName())
				|| StringUtil.isEmpty(friendLink.getLinkName())) {
			errors.rejectValue("linkName", "", "请填写链接名称");
		} else {
			if (friendLink.getLinkName().length() > 255) {
				errors.rejectValue("linkName", "", "链接名称长度不能大于255个字符");
			}
		}

		if (StringUtil.isBlank(friendLink.getLinkUrl())
				|| StringUtil.isEmpty(friendLink.getLinkUrl())) {
			errors.rejectValue("linkUrl", "", "URL不为空");
		} else {
			if (friendLink.getLinkUrl().length() > 256) {
				errors.rejectValue("linkUrl", "", "URL长度<256");
			} else {
				Matcher matcher1 = patternNum.matcher(friendLink.getLinkUrl()
						.toString());
				if (!matcher1.matches()) {
					errors.rejectValue("linkUrl", "", "URL请以http或https开头");
				}
			}

		}
	}
}
