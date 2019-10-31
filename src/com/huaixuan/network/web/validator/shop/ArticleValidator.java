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
			errors.rejectValue("title", "", "����д��Ѷ����");
		} else {
			if (article.getTitle().length() > 120) {
				errors.rejectValue("title", "", "��Ѷ���ⲻ�ܴ���120���ַ�");
			}
		}

		if (StringUtil.isBlank(article.getContent().trim())
				|| StringUtil.isEmpty(article.getContent().trim())) {
			errors.rejectValue("content", "", "����д�������ݣ�");
		} else {
			if (article.getContent().length() > 40000) {
				errors.rejectValue("content", "", "��ע���ܳ���40000���ַ�");
			}
		}

			if (article.getContent().length() > 120) {
				errors.rejectValue("keywords", "", "�ؼ��ֲ��ܴ���120���ַ�");
			}

			if (article.getContent().length() > 5000) {
				errors.rejectValue("digest", "", "ժҪ���ܴ���5000���ַ�");
			}

			if (article.getContent().length() > 255) {
				errors.rejectValue("source", "", "������Դ���ܴ���255���ַ�");
			}

			if (article.getContent().length() > 255) {
				errors.rejectValue("sourceUrl", "", "������Դ��ַ���ܴ���255���ַ�");
			}
	}
}
