package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.Page;
import com.huaixuan.network.biz.dao.shop.ArticleDao;
import com.huaixuan.network.biz.domain.shop.Article;

@Service("articleDao")
public class ArticleDaoiBatis implements ArticleDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addArticle(Article article) throws Exception {
		this.sqlMapClient.insert("addArticle", article);
	}

	@Override
	public void editArticle(Article article) throws Exception {
		this.sqlMapClient.update("editArticle", article);
	}

	@Override
	public void removeArticle(Long articleId) throws Exception {
		this.sqlMapClient.delete("removeArticle", articleId);
	}

	@Override
	public Article getArticle(Long articleId) throws Exception {
		return (Article) this.sqlMapClient.queryForObject("getArticle",
				articleId);
	}

	@Override
	public List<Article> getArticles() throws Exception {
		return this.sqlMapClient.queryForList("getArticles", null);
	}

	@Override
	public Integer getArticlesCount() throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getArticlesCount",
				null);
	}

	@Override
	public List<Article> getArticlesPage(Map<String, String> conditions) {
		return this.sqlMapClient.queryForList("getArticlesPage", conditions);
	}

	@Override
	public void updateShow(Map<String, Object> prama) throws Exception {

		this.sqlMapClient.update("updateShow", prama);
	}

	@Override
	public void updateTop(Map<String, Object> prama) throws Exception {
		this.sqlMapClient.update("updateTop", prama);
	}

	@Override
	public List<Article> getArticleInfo() throws Exception {
		return this.sqlMapClient.queryForList("getArticleIndex");
	}

	// /**
	// * ÊÖ¶¯Ë¢ÐÂ»º´æ
	// *
	// * @param cacheModelId
	// * @throws Exception
	// */
	// public void refreshCacheByCacheModelId(String cacheModelId) {
	// if (StringUtil.isBlank(cacheModelId)) {
	// this.getSqlMapClient().flushDataCache();
	// } else {
	// this.getSqlMapClient().flushDataCache(cacheModelId);
	// }
	// }
}
