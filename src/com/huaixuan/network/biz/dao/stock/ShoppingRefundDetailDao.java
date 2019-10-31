package com.huaixuan.network.biz.dao.stock;

 import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingRefundDetail;

/**
* @ClassName: ShoppingRefundDetailDao
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:50:18
 */
public interface ShoppingRefundDetailDao  {

	 long addShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail) throws Exception;

	 void editShoppingRefundDetail(ShoppingRefundDetail shoppingRefundDetail) throws Exception;

	 void removeShoppingRefundDetail(Long shoppingRefundDetailId) throws Exception;

	 ShoppingRefundDetail getShoppingRefundDetail(Long shoppingRefundDetailId) throws Exception;

	 List <ShoppingRefundDetail> getShoppingRefundDetails() throws Exception;

	 List <ShoppingRefundDetail> getShoppingRefundDetails(Long shoppingRefundDetailId) throws Exception;

 	int getCountRefundByShoppingIdAndStatus(Map<String, String> parMap) throws Exception;


 	/**
 	 * @param parMap
 	 * @return
 	 * @throws Exception
 	 */
 	public List <ShoppingRefundDetail> getRefundDetial(Map<String, Object> parMap)throws Exception;

 	/**
 	 * @param parMap
 	 * @return
 	 * @throws Exception
 	 */
 	public List <ShoppingRefundDetail> getStorageRefundDetails(Map<String, Object> parMap)throws Exception;
	/**
	 * @param paramMap
	 * @return
	 */
	public int getCountRefundDetail(Map<String,Object> paramMap)throws Exception;

	/**
	 *
	 * @param param
	 * @return
	 */
	public ShoppingRefundDetail sumRefundDetailByShoppingId(Map<String,Object> param)throws Exception;

	/**
	 *
	 * @param shoppingRefundId
	 * @return
	 */
	public List<ShoppingRefundDetail> getShoppingRefundDetailByShoppingRefundId(Long shoppingRefundId);
 }
