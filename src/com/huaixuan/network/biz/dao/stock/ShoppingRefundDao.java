package com.huaixuan.network.biz.dao.stock;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingRefund;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingRefundSearch;

 /**
 * @ClassName: ShoppingRefundDao
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-2-24 ÏÂÎç04:50:10
  */
 public interface ShoppingRefundDao  {

 	long addShoppingRefund(ShoppingRefund shoppingRefund) throws Exception;

 	void editShoppingRefund(ShoppingRefund shoppingRefund) throws Exception;

 	void editShoppingRefundStatus(ShoppingRefund shoppingRefund) throws Exception;

 	void removeShoppingRefund(Long shoppingRefundId) throws Exception;

 	ShoppingRefund getShoppingRefund(Long shoppingRefundId) throws Exception;

 	public ShoppingRefund getShoppingRefunds(Map<String,String> paramMap) throws Exception;

 	int getCountByParameterMap(Map<String, String> parMap);

// 	List <ShoppingRefund> getShoppingRefundListByParameterMap(Map<String, Object> parameterMap, Page page) throws Exception;
 	List <ShoppingRefund> getShoppingRefundListByParameterMap(Map<String, String> parameterMap) throws Exception;

 	List <ShoppingRefundSearch> getShoppingRefundNem (Long shoppingRefundId) throws Exception;

 	List <ShoppingRefundDSearch> getByParameterMap(Map<String, String> parMap) throws Exception;

 	/*
     */
//    List<ShoppingRefundDetailSearch> getShoppingRefundDetailSearchRusultByParameterMap(Map parameterMap, Page page) throws Exception;
    List<ShoppingRefundDetailSearch> getShoppingRefundDetailSearchRusultByParameterMap(Map parameterMap) throws Exception;

    /*
     */
    int getShoppingRefundCountDetailSearchByParameterMap(Map<String, String> parMap) throws Exception;

    /*
     */
//    List<ShoppingRefundGatherSearch> getShoppingRefundGatherSearchRusultByParameterMap(Map parameterMap, Page page) throws Exception;
    List<ShoppingRefundGatherSearch> getShoppingRefundGatherSearchRusultByParameterMap(Map parameterMap) throws Exception;

    /*
     */
    int getShoppingRefundCountGatherSearchByParameterMap(Map<String, String> parMap) throws Exception;

    /**
     * @param relationNum
     * @return
     */
    List<ShoppingRefundDetail> getBatchNumByRelationNum(String relationNum);

 }
