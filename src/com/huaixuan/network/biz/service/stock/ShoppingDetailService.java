/**
 * @Title: ShoppingDetailService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ����02:39:44
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
 * @date 2011-3-1 ����02:39:44
 *
 */
public interface ShoppingDetailService {
	    /**
	     * ����
	     * @param shoppingDetail
	     */
	    public void addShoppingDetail(ShoppingDetail shoppingDetail);

	    /**
	     * �༭
	     * @param shoppingDetail
	     */
	    public void editShoppingDetail(ShoppingDetail shoppingDetail);

	    /**
	     * ɾ��
	     * @param shoppingDetailId
	     */
	    public void removeShoppingDetail(Long shoppingDetailId);

	    /**
	     * ����shoppingDetailId���ShoppingDetail
	     * @param shoppingDetailId
	     * @return
	     */
	    public ShoppingDetail getShoppingDetail(Long shoppingDetailId);

	    /**
	     * ����shoppingListId���ShoppingMoreDetail
	     * @param shoppingListId
	     * @return
	     */
	    public List<ShoppingMoreDetail> getShoppingDetailsByShoppingListId(Long shoppingListId);

	    /**
	     * ����shoppingListId���ShoppingDetail
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
	     *  ��ѯһ���ɹ������Ƿ��Ѳɹ�ĳ����Ʒ
	     * @param parMap
	     * @return
	     */
	    public int getCountByShoppingIdAndGoodsInsId(Map<String, String> parMap);
//
//	    /**
//	     * �����û��ɲ�ѯ��Ŀ�����ѯ���ڴ���Ŀ�µļ�¼��
//	     * @param parMap
//	     * @return
//	     */
//	    public int getCountShoppingDetailByCatCode(Map<String, String> parMap);

	}