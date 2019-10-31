package com.huaixuan.network.biz.dao.user;

import java.util.Date;
import java.util.List;

import com.huaixuan.network.biz.domain.user.SearchKeyword;

public interface SearchKeywordDao {

    void addSearchKeyword(SearchKeyword searchKeyword) throws Exception; 
  
    void editSearchKeyword(SearchKeyword searchKeyword) throws Exception; 
  
    void removeSearchKeyword(Long searchKeywordId) throws Exception; 
  
    SearchKeyword getSearchKeyword(Long searchKeywordId) throws Exception; 
  
    List <SearchKeyword> getSearchKeywords() throws Exception; 
    
    boolean updateKeyword(String keyword)throws Exception;

    void createSearchKeyword(String canonical)throws Exception; 
    
    List<SearchKeyword> findWordsByModifyDate(Date lastModify,int end);
}
