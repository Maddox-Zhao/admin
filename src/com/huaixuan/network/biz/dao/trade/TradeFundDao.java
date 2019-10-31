package com.huaixuan.network.biz.dao.trade; 
  
 import java.util.List;

import com.huaixuan.network.biz.domain.trade.TradeFund;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public interface TradeFundDao  { 
 	void addTradeFund(TradeFund tradeFund) throws Exception; 
  
 	void editTradeFund(TradeFund tradeFund) throws Exception; 
  
 	void removeTradeFund(Long tradeFundId) throws Exception; 
  
 	TradeFund getTradeFund(Long tradeFundId) throws Exception; 
  
 	List <TradeFund> getTradeFunds() throws Exception; 
 } 
 