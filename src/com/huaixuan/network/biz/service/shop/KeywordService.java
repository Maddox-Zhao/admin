package com.huaixuan.network.biz.service.shop;

import java.util.List;

import com.huaixuan.network.biz.domain.shop.Keyword;
import com.huaixuan.network.biz.query.QueryPage;

public interface KeywordService {
	public Long addKeyword(Keyword keyword);

	public void editKeyword(Keyword keyword);

	public void removeKeyword(Long keywordId);

	public Keyword getKeyword(Long keywordId);

	public List<Keyword> getKeywords();

	public QueryPage getKeywordsPage(long shopId, int currentPage, int pageSize);
}
