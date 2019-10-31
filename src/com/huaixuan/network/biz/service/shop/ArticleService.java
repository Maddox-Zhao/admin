package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Article;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����(bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public interface ArticleService {
	/* @interface model: 新增资讯 */
	public void addArticle(Article article) throws Exception;

	/* @interface model: 更新资讯 */
	public void editArticle(Article article) throws Exception;

	/* @interface model: 删除资讯 */
	public void removeArticle(Long articleId) throws Exception;

	/* @interface model: 根据Id获取资讯 */
	public Article getArticle(Long articleId) throws Exception;

	/* @interface model: 获取�有资� */
	public List<Article> getArticles() throws Exception;

	QueryPage getArticlesPage(int currentPage, int pageSize) throws Exception;

	// 设置资讯是否显示
	public void updateShow(Long articleId) throws Exception;

	// 设置资讯是否置顶
	public void updateTop(Long articleId) throws Exception;

	/**
	 * 刷新缓存
	 *
	 * @param cacheModelId
	 * @throws Exception
	 */
	// public void refreshCacheByCacheModelId(String cacheModelId)
	// throws Exception;
}
