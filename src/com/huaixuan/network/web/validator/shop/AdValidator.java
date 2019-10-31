/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Ad;
import com.huaixuan.network.biz.domain.shop.AdDetail;
import com.huaixuan.network.biz.domain.shop.AdPosition;
import com.huaixuan.network.biz.service.shop.AdService;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-10
 */
public class AdValidator extends ValangValidator {
	@Autowired
	private AdService adService;

	public void validate(Object obj, Errors errors) {

		Ad ad = (Ad) obj;

		if (StringUtil.isBlank(ad.getAdName().trim())
				|| StringUtil.isEmpty(ad.getAdName().trim())) {
			errors.rejectValue("adName", "", "请填写广告标题");
		} else {
			if (ad.getAdName().length() > 64) {
				errors.rejectValue("adName", "", "广告标题不能超过64个字符");
			}
		}

		if (StringUtil.isBlank(ad.getLink().trim())
				|| StringUtil.isEmpty(ad.getLink().trim())) {
			errors.rejectValue("link", "", "请填写广告链接");
		} else {
			if (ad.getLink().length() > 3000) {
				errors.rejectValue("link", "", "广告链接不能超过255个字符");
			}
		}

		if (StringUtil.isBlank(ad.getAdType().trim())
				|| StringUtil.isEmpty(ad.getAdType().trim())) {
			errors.rejectValue("adType", "", "请选择广告分类");
		}

		if (StringUtil.isBlank(ad.getMediaType().trim())
				|| StringUtil.isEmpty(ad.getMediaType().trim())) {
			errors.rejectValue("mediaType", "", "请选择广告类型");
		}
	}
}
