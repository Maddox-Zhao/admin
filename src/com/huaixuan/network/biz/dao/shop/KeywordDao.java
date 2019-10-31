package com.huaixuan.network.biz.dao.shop;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.shop.Keyword;

public interface KeywordDao {
	Long addKeyword(Keyword keyword) throws Exception;

	void editKeyword(Keyword keyword) throws Exception;

	void removeKeyword(Long keywordId) throws Exception;

	Keyword getKeyword(Long keywordId) throws Exception;

	List<Keyword> getKeywords() throws Exception;

	Long getKeywordMaxSort(long shopId) throws Exception;

	void updateKeywordSort(int maxid, long shopId) throws Exception;

	void updateKeywordSortUpdtae(int maxid, long shopId, int maxid2)
			throws Exception;

	void updateKeywordSortlow(int maxid, long shopId, int maxid2)
			throws Exception;

	void updateKeywordSortsamll(int sort, long shopId) throws Exception;

	public Integer getKeywordsCount(long shopId) throws Exception;

	List<Keyword> getKeywordsPage(Map<String, String> conditions);

}
