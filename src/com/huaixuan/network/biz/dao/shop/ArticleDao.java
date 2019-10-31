package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Article;

public interface ArticleDao {
	/* @interface model: */
	void addArticle(Article article) throws Exception;

	/* @interface model: */
	void editArticle(Article article) throws Exception;

	/* @interface model: */
	void removeArticle(Long articleId) throws Exception;

	/* @interface model: */
	Article getArticle(Long articleId) throws Exception;

	/* @interface model: */
	List<Article> getArticles() throws Exception;

	public Integer getArticlesCount() throws Exception;

	List<Article> getArticlesPage(Map<String, String> conditions);

	public void updateShow(Map<String, Object> prama) throws Exception;

	public void updateTop(Map<String, Object> prama) throws Exception;

	List<Article> getArticleInfo() throws Exception;

	/**
	 * Ë¢ÐÂ»º´æ
	 *
	 * @param cacheModelId
	 * @throws Exception
	 */
	// void refreshCacheByCacheModelId(String cacheModelId) throws Exception;
}
