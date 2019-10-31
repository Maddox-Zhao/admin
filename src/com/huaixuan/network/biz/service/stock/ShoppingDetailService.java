/**
 * @Title: ShoppingDetailService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:39:44
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingMoreDetail;

/**
 * @ClassName: ShoppingDetailService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:39:44
 *
 */
public interface ShoppingDetailService {
	    /**
	     * 新增
	     * @param shoppingDetail
	     */
	    public void addShoppingDetail(ShoppingDetail shoppingDetail);

	    /**
	     * 编辑
	     * @param shoppingDetail
	     */
	    public void editShoppingDetail(ShoppingDetail shoppingDetail);

	    /**
	     * 删除
	     * @param shoppingDetailId
	     */
	    public void removeShoppingDetail(Long shoppingDetailId);

	    /**
	     * 根据shoppingDetailId获得ShoppingDetail
	     * @param shoppingDetailId
	     * @return
	     */
	    public ShoppingDetail getShoppingDetail(Long shoppingDetailId);

	    /**
	     * 根据shoppingListId获得ShoppingMoreDetail
	     * @param shoppingListId
	     * @return
	     */
	    public List<ShoppingMoreDetail> getShoppingDetailsByShoppingListId(Long shoppingListId);

	    /**
	     * 根据shoppingListId获得ShoppingDetail
	     * @param shoppingListId
	     * @return
	     */
	    public List<ShoppingDetail> getShopDetailsByShopListId(Long shoppingListId);

	    /**
	     *
	     * @param shoppingListId
	     * @return
	     */
	    public int getMissingNumByShoppingListId(Long shoppingListId);
//
	    /**
	     *
	     * @param shoppingListId
	     * @return
	     */
	    public int getRejectNumByShoppingListId(Long shoppingListId);
//
	    /**
	     *  查询一个采购订单是否已采购某个产品
	     * @param parMap
	     * @return
	     */
	    public int getCountByShoppingIdAndGoodsInsId(Map<String, String> parMap);
//
//	    /**
//	     * 根据用户可查询类目编码查询不在此类目下的记录数
//	     * @param parMap
//	     * @return
//	     */
//	    public int getCountShoppingDetailByCatCode(Map<String, String> parMap);

	}