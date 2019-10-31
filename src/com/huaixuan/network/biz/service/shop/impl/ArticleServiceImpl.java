package com.huaixuan.network.biz.service.shop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ArticleDao;
import com.huaixuan.network.biz.domain.shop.Article;
import com.huaixuan.network.biz.exception.ManagerException;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.shop.ArticleService;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public ArticleDao articleDao;

	@Override
	public void addArticle(Article articleDao) throws Exception {
		log.info("ArticleManagerImpl.addArticle method");

		this.articleDao.addArticle(articleDao);

	}

	@Override
	public void editArticle(Article article) throws Exception {
		log.info("ArticleManagerImpl.editArticle method");

		this.articleDao.editArticle(article);

	}

	@Override
	public void removeArticle(Long articleId) throws Exception {
		log.info("ArticleManagerImpl.removeArticle method");

		this.articleDao.removeArticle(articleId);

	}

	@Override
	public Article getArticle(Long articleId) throws Exception {
		log.info("ArticleManagerImpl.getArticle method");

		return this.articleDao.getArticle(articleId);

	}

	@Override
	public List<Article> getArticles() throws Exception {
		log.info("ArticleManagerImpl.getArticles method");

		return this.articleDao.getArticles();

	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getArticlesPage(int currentPage, int pageSize)
			throws Exception {
		log.info("ArticleManagerImpl.getArticlesPage method");
		try {
			Article article=new Article();
			QueryPage queryPage = new QueryPage(article);
			Map pramas = queryPage.getParameters();

			Integer count = this.articleDao.getArticlesCount();

			if (count > 0) {

				/* 当前页 */
				queryPage.setCurrentPage(currentPage);
				/* 每页总数 */
				queryPage.setPageSize(pageSize);
				queryPage.setTotalItem(count);

				pramas.put("startRow",
						queryPage.getPageFristItem());
				pramas.put("endRow",
						queryPage.getPageLastItem());

				/* 分页查询操作员记录 */
				List<Article> articleList = this.articleDao
						.getArticlesPage(pramas);
				if (articleList != null && articleList.size() > 0) {
					queryPage.setItems(articleList);
				}
			}
			return queryPage;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateShow(Long articleId) throws Exception {
		log.info("ArticleManagerImpl.updateShow method");
		Article articledNew = this.articleDao.getArticle(articleId);
		int isshow = 1;
		if (articledNew != null) {
			if (articledNew.getIsShow() == 1) {
				isshow = 0;
			} else {
				isshow = 1;
			}
		}

		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("id", articleId);
		prama.put("isShow", isshow);
		this.articleDao.updateShow(prama);

	}

	@Override
	public void updateTop(Long articleId) throws Exception {
		log.info("ArticleManagerImpl.updateTop method");
		Article articledNew = this.articleDao.getArticle(articleId);
		int istop = 1;
		if (articledNew != null) {
			if (articledNew.getIsTop() == 1) {
				istop = 0;
			} else {
				istop = 1;
			}
		}

		Map<String, Object> prama = new HashMap<String, Object>();
		prama.put("id", articleId);
		prama.put("isTop", istop);
		this.articleDao.updateTop(prama);

	}

	// public void refreshCacheByCacheModelId(String cacheModelId)
	// throws Exception {
	// log.info("ArticleManagerImpl.refreshCacheByCacheModelId method");
	// articleDao.refreshCacheByCacheModelId(cacheModelId);
	// }
}
