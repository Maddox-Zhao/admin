package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.ArticleCatDao;
import com.huaixuan.network.biz.domain.shop.ArticleCat;

@Service("articleCatDao")
public class ArticleCatDaoiBatis implements ArticleCatDao {

	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addArticleCat(ArticleCat articleCat) throws Exception {
		this.sqlMapClient.insert("addArticleCat", articleCat);
	}

	@Override
	public void editArticleCat(ArticleCat articleCat) throws Exception {
		this.sqlMapClient.update("editArticleCat", articleCat);
	}

	@Override
	public void removeArticleCat(Long articleCatId) throws Exception {
		this.sqlMapClient.delete("removeArticleCat", articleCatId);
	}

	@Override
	public ArticleCat getArticleCat(Long articleCatId) throws Exception {
		return (ArticleCat) this.sqlMapClient.queryForObject("getArticleCat",
				articleCatId);
	}

	@Override
	public List<ArticleCat> getArticleCats() throws Exception {
		return this.sqlMapClient.queryForList("getArticleCats", null);
	}
}
