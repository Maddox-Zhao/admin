package com.huaixuan.network.biz.dao.stock;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;

/**
* @ClassName: ShoppingListDao
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:55
*/
public interface ShoppingListDao {
	long addShoppingList(ShoppingList shoppingList) throws Exception;

 	void editShoppingList(ShoppingList shoppingList) throws Exception;

 	void editShoppingListAllInfo(ShoppingList shoppingList) throws Exception;

 	void removeShoppingList(Long shoppingListId) throws Exception;

 	ShoppingList getShoppingList(Long shoppingListId) throws Exception;

 	ShoppingList getShoppingListByShoppingNum(String shoppingNum) throws Exception;;

 	int getCountByShoppingNum(String shoppingNum) throws Exception;

 	//List <ShoppingList> getShoppingListByParameterMap(Map parameterMap, Page page) throws Exception;
 	List <ShoppingList> getShoppingListByParameterMap(Map parameterMap) throws Exception;

 	int getCountByParameterMap(Map<String, String> parMap);

    int getDueSearchCountByParameterMap(Map<String, String> parMap) throws Exception;

    //List<ShoppingList> getDueSearchListsByParameterMap(Map parameterMap, Page page) throws Exception;
    List<ShoppingList> getDueSearchListsByParameterMap(Map parameterMap) throws Exception;

//    List<ShoppingDetailSearch> getShoppingDetailSearchRusultByParameterMap(Map parameterMap, Page page)  throws Exception;
    List<ShoppingDetailSearch> getShoppingDetailSearchRusultByParameterMap(Map parameterMap)  throws Exception;

    int getShoppingCountDetailSearchByParameterMap(Map<String, String> parMap);

//    List<ShoppingGatherSearch> getShoppingGatherSearchRusultByParameterMap(Map parameterMap, Page page)  throws Exception;
    List<ShoppingGatherSearch> getShoppingGatherSearchRusultByParameterMap(Map parameterMap)  throws Exception;

    int getShoppingCountGatherSearchByParameterMap(Map<String, String> parMap) throws Exception;

//    List<V_SupplierShoppingList> getSearchShoppingLists(V_SearchShoppingList v_SearchShoppingList,Page page) throws Exception;
    List<V_SupplierShoppingList> getSearchShoppingLists(Map map) throws Exception;

 	 	int getCountSupplierShoppingLists(Map map) throws Exception;

 	 	Long getSupplierIdByShoppingNum(String shoppingNum);

 	 	ShoppingList getSupplierIdAndGmtCreateForUpdate(Long inDepId);

 	 	List<ShoppingDetailSearch>  getShoppingDetailStorageNum(Map<String,String> paramMap);

 	    int getDueEstimateSearchCountByParameterMap(Map<String, String> parMap) throws Exception;

// 	   List<ShoppingList> getDueEstimateSearchListsByParameterMap(Map parameterMap, Page page) throws Exception;
 	  List<ShoppingList> getDueEstimateSearchListsByParameterMap(Map parameterMap) throws Exception;

 	    List<V_SupplierShoppingList> getSupplierShoppingExportList(StockDetailSearchQuery stockDetailSearchQuery);
 }
