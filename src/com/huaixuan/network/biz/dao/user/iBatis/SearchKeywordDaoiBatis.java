package com.huaixuan.network.biz.dao.user.iBatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.user.SearchKeywordDao;
import com.huaixuan.network.biz.domain.user.SearchKeyword;


@Repository("searchKeywordDao")
public class SearchKeywordDaoiBatis implements SearchKeywordDao{

    @Autowired
    private SqlMapClientTemplate sqlMapClient;
    
    @Override
    public void addSearchKeyword(SearchKeyword searchKeyword) throws Exception {
        this.sqlMapClient.insert("addSearchKeyword", searchKeyword);
    }
    @Override
    public void editSearchKeyword(SearchKeyword searchKeyword) throws Exception {
        this.sqlMapClient.update("editSearchKeyword", searchKeyword);
    }
    @Override
    public void removeSearchKeyword(Long searchKeywordId) throws Exception {
        this.sqlMapClient.delete("removeSearchKeyword", searchKeywordId);
    }
    @Override
    public SearchKeyword getSearchKeyword(Long searchKeywordId) throws Exception {
        return (SearchKeyword) this.sqlMapClient.queryForObject("getSearchKeyword",
            searchKeywordId);
    }
    @Override
    public List<SearchKeyword> getSearchKeywords() throws Exception {
        return this.sqlMapClient.queryForList("getSearchKeywords", null);
    }
    @Override
    public boolean updateKeyword(String keyword) throws Exception {
        int row = this.sqlMapClient.update("updateSearchKeyword", keyword);
        return row > 0;
    }
    @Override
    public void createSearchKeyword(String canonical) throws Exception {
        this.sqlMapClient.insert("createSearchKeyword", canonical);
    }
    @Override
    public List<SearchKeyword> findWordsByModifyDate(Date lastModify, int end) {
        Map parameters = new HashMap();
        parameters.put("lastModify", lastModify);
        parameters.put("end", end);
        return this.sqlMapClient
            .queryForList("findSearchKeywordsByModify", parameters);
    }
}
