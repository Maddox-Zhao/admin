package com.huaixuan.network.biz.dao.shop.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.shop.KeywordDao;
import com.huaixuan.network.biz.domain.shop.Keyword;

@Service("keywordDao")
public class KeywordDaoiBatis implements KeywordDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public Long addKeyword(Keyword keyword) throws Exception {
		Long id = (Long) this.sqlMapClient.insert("addKeyword", keyword);
		return id;
	}

	@Override
	public void editKeyword(Keyword keyword) throws Exception {
		this.sqlMapClient.update("editKeyword", keyword);
	}

	@Override
	public void removeKeyword(Long keywordId) throws Exception {
		this.sqlMapClient.delete("removeKeyword", keywordId);
	}

	@Override
	public Keyword getKeyword(Long keywordId) throws Exception {
		return (Keyword) this.sqlMapClient.queryForObject("getKeyword",
				keywordId);
	}

	@Override
	public List<Keyword> getKeywords() throws Exception {
		return this.sqlMapClient.queryForList("getKeywords", null);
	}

	@Override
	public void updateKeywordSort(int maxid, long shopId) throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("shopId", shopId);
		this.sqlMapClient.update("updateKeywordSort", param);
	}

	@Override
	public void updateKeywordSortUpdtae(int maxid, long shopId, int maxid2)
			throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		param.put("shopId", shopId);
		this.sqlMapClient.update("updateKeywordSortUpdtae", param);
	}

	@Override
	public void updateKeywordSortlow(int maxid, long shopId, int maxid2)
			throws Exception {
		Map param = new HashMap();
		param.put("maxid", maxid);
		param.put("maxid2", maxid2);
		param.put("shopId", shopId);
		this.sqlMapClient.update("updateKeywordSortlow", param);
	}

	@Override
	public void updateKeywordSortsamll(int sort, long shopId) throws Exception {
		Map param = new HashMap();
		param.put("sort", sort);
		param.put("shopId", shopId);
		this.sqlMapClient.update("updateKeywordSortsamll", param);
	}

	@Override
	public Long getKeywordMaxSort(long shopId) throws Exception {

		return (Long) this.sqlMapClient.queryForObject("getKeywordMaxSort",
				shopId);
	}

	@Override
	public Integer getKeywordsCount(long shopId) throws Exception {
		return (Integer) this.sqlMapClient.queryForObject("getKeywordsCount",
				shopId);
	}

	public List<Keyword> getKeywordsPage(Map<String, String> conditions) {
		return this.sqlMapClient.queryForList("getKeywordsPage", conditions);
	}

}
