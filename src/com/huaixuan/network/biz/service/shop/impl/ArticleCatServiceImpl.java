package com.huaixuan.network.biz.service.shop.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ArticleCatDao;
import com.huaixuan.network.biz.domain.shop.ArticleCat;
import com.huaixuan.network.biz.service.shop.ArticleCatService;

@Service("ArticleCatService")
public class ArticleCatServiceImpl implements ArticleCatService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	public ArticleCatDao articleCatDao;

	@Override
	public void addArticleCat(ArticleCat articleCatDao) {
		log.info("ArticleCatManagerImpl.addArticleCat method");
		try {
			this.articleCatDao.addArticleCat(articleCatDao);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editArticleCat(ArticleCat articleCat) {
		log.info("ArticleCatManagerImpl.editArticleCat method");
		try {
			this.articleCatDao.editArticleCat(articleCat);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void removeArticleCat(Long articleCatId) {
		log.info("ArticleCatManagerImpl.removeArticleCat method");
		try {
			this.articleCatDao.removeArticleCat(articleCatId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public ArticleCat getArticleCat(Long articleCatId) {
		log.info("ArticleCatManagerImpl.getArticleCat method");
		try {
			return this.articleCatDao.getArticleCat(articleCatId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<ArticleCat> getArticleCats() {
		log.info("ArticleCatManagerImpl.getArticleCats method");
		try {
			return this.articleCatDao.getArticleCats();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
