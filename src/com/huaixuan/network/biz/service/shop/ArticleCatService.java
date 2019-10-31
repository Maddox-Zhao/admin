package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.ArticleCat;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface ArticleCatService {
	/* @interface model: 新增资讯类型 */
	public void addArticleCat(ArticleCat articleCat);

	/* @interface model: 更新资讯类型 */
	public void editArticleCat(ArticleCat articleCat);

	/* @interface model: 删除资讯类型 */
	public void removeArticleCat(Long articleCatId);

	/* @interface model: 根据Id获取资讯类型 */
	public ArticleCat getArticleCat(Long articleCatId);

	/* @interface model: 获取�有的资讯类型 */
	public List<ArticleCat> getArticleCats();
}
