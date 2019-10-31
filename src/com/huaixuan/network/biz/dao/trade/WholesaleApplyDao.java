package com.huaixuan.network.biz.dao.trade;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.trade.WholesaleApply;
import com.huaixuan.network.biz.query.QueryPage;

public interface WholesaleApplyDao {

    long addWholesaleApply(WholesaleApply wholesaleApply) throws Exception; 
  
    void editWholesaleApply(WholesaleApply wholesaleApply) throws Exception; 
  
    void removeWholesaleApply(Long wholesaleApplyId) throws Exception; 
  
    WholesaleApply getWholesaleApply(Long wholesaleApplyId) throws Exception; 
    
    WholesaleApply getWholesaleApplyByTid(String tid) throws Exception; 
  
    List <WholesaleApply> getWholesaleApplys() throws Exception; 
    
    QueryPage getWholesaleApplysByParMap(Map parMap, int currentPage, int pageSize) throws Exception; 
    
    int getCountWholesaleApplysByParMap(Map parMap) throws Exception; 
}
