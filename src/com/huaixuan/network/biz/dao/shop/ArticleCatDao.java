package com.huaixuan.network.biz.dao.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.ArticleCat;

public interface ArticleCatDao {
	void addArticleCat(ArticleCat articleCat) throws Exception;

	void editArticleCat(ArticleCat articleCat) throws Exception;

	void removeArticleCat(Long articleCatId) throws Exception;

	ArticleCat getArticleCat(Long articleCatId) throws Exception;

	List<ArticleCat> getArticleCats() throws Exception;
}
