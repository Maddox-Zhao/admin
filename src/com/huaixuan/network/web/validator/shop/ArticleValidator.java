/**
 *
 */
package com.huaixuan.network.web.validator.shop;

import org.springframework.validation.Errors;
import org.springmodules.validation.valang.ValangValidator;

import com.huaixuan.network.biz.domain.shop.Article;
import com.hundsun.network.melody.common.util.StringUtil;

/**
 * @author chenhang 2011-3-22
 */
public class ArticleValidator extends ValangValidator {
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		Article article = (Article) obj;

		if (StringUtil.isBlank(article.getTitle().trim())
				|| StringUtil.isEmpty(article.getTitle().trim())) {
			errors.rejectValue("title", "", "请填写资讯标题");
		} else {
			if (article.getTitle().length() > 120) {
				errors.rejectValue("title", "", "资讯标题不能大于120个字符");
			}
		}

		if (StringUtil.isBlank(article.getContent().trim())
				|| StringUtil.isEmpty(article.getContent().trim())) {
			errors.rejectValue("content", "", "请填写文章内容！");
		} else {
			if (article.getContent().length() > 40000) {
				errors.rejectValue("content", "", "备注不能超过40000个字符");
			}
		}

			if (article.getContent().length() > 120) {
				errors.rejectValue("keywords", "", "关键字不能大于120个字符");
			}

			if (article.getContent().length() > 5000) {
				errors.rejectValue("digest", "", "摘要不能大于5000个字符");
			}

			if (article.getContent().length() > 255) {
				errors.rejectValue("source", "", "文章来源不能大于255个字符");
			}

			if (article.getContent().length() > 255) {
				errors.rejectValue("sourceUrl", "", "文章来源地址不能大于255个字符");
			}
	}
}
