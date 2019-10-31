package com.huaixuan.network.biz.dao.stock;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingMoreDetail;

/**
* @ClassName: ShoppingDetailDao
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:04
 */
public interface ShoppingDetailDao {

	/**
	* @Title: addShoppingDetail
	* @Description: TODO
	* @param shoppingDetail
	* @throws Exception
	 */
 	void addShoppingDetail(ShoppingDetail shoppingDetail) throws Exception;

 	/**
 	* @Title: editShoppingDetail
 	* @Description: TODO
 	* @param shoppingDetail
 	* @throws Exception
 	 */
 	void editShoppingDetail(ShoppingDetail shoppingDetail) throws Exception;

 	/**
 	* @Title: removeShoppingDetail
 	* @Description: TODO
 	* @param shoppingDetailId
 	* @throws Exception
 	 */
 	void removeShoppingDetail(Long shoppingDetailId) throws Exception;

 	/**
 	* @Title: getShoppingDetail
 	* @Description: TODO
 	* @param shoppingDetailId
 	* @return
 	* @throws Exception
 	 */
 	ShoppingDetail getShoppingDetail(Long shoppingDetailId) throws Exception;

 	/**
 	* @Title: getShoppingDetailsByShoppingListId
 	* @Description: TODO
 	* @param shoppingListId
 	* @return
 	* @throws Exception
 	 */
 	List <ShoppingMoreDetail> getShoppingDetailsByShoppingListId(Long shoppingListId) throws Exception;

 	/**
 	* @Title: getShopDetailsByShopListId
 	* @Description: TODO
 	* @param shoppingListId
 	* @return
 	* @throws Exception
 	 */
 	List<ShoppingDetail> getShopDetailsByShopListId(Long shoppingListId) throws Exception;

 	/**
 	* @Title: getMissingNumByShoppingListId
 	* @Description: TODO
 	* @param shoppingListId
 	* @return
 	* @throws Exception
 	 */
 	int getMissingNumByShoppingListId(Long shoppingListId) throws Exception;

 	/**
 	* @Title: getRejectNumByShoppingListId
 	* @Description: TODO
 	* @param shoppingListId
 	* @return
 	* @throws Exception
 	 */
 	int getRejectNumByShoppingListId(Long shoppingListId) throws Exception;

 	/**
 	* @Title: getCountByShoppingIdAndGoodsInsId
 	* @Description:
 	* @param parMap
 	* @return
 	* @throws Exception
 	 */
 	int getCountByShoppingIdAndGoodsInsId(Map<String, String> parMap) throws Exception;

 	/**
 	* @Title: getGatherNumBySupplierId
 	* @Description:
 	* @param suplierId
 	* @return
 	* @throws Exception
 	 */
 	ShoppingDetail getGatherNumBySupplierId(Long suplierId) throws Exception;

 	/**
 	* @Title: getCountShoppingDetailByCatCode
 	* @Description:
 	* @param parMap
 	* @return
 	 */
    int getCountShoppingDetailByCatCode(Map<String, String> parMap);
 }
