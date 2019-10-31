/**
 * @Title: ShoppingListService.java
 * @Package com.huaixuan.network.biz.service.stock
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ����02:39:27
 * @version V1.0
 */
package com.huaixuan.network.biz.service.stock;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.ShoppingListQuery;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @ClassName: ShoppingListService
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ����02:39:27
 *
 */
public interface ShoppingListService {
	/**
	 *
	 * @param shoppingList
	 * @return
	 */
	public long addShoppingList(ShoppingList shoppingList) throws Exception;

	/**
	 * �޸ĳ�ȥʱ���������Ϣ
	 *
	 * @param shoppingList
	 */
	public void editShoppingList(ShoppingList shoppingList, String oldStatus, String actionType);


	 /**
	 * �޸İ���ʱ�����ڵ�������Ϣ
	 * @param shoppingList
	 */
	 public void editShoppingListAllInfo(ShoppingList shoppingList);

	// /**
	// *
	// * @param shoppingListId
	// */
	// public void removeShoppingList(Long shoppingListId);
	//
	/**
	 *
	 * @param shoppingListId
	 * @return
	 */
	public ShoppingList getShoppingList(Long shoppingListId) throws Exception;

	//
	/**
	 *
	 * ���ݲɹ�������ŵõ��ɹ���������
	 *
	 * @param shoppingNum
	 * @return
	 */
	public ShoppingList getShoppingListByShoppingNum(String shoppingNum)
			throws Exception;

	//
	/**
	 *
	 * @param shoppingNum
	 * @return
	 */
	public int getCountByShoppingNum(String shoppingNum) throws Exception;

	//
	// /**
	// *
	// * @param parameterMap
	// * @param page
	// * @return
	// */
	public QueryPage getShoppingListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	// /**
	// *
	// * @param parMap
	// * @return
	// */
	// public int getShoppingCountByParameterMap(Map<String, String>
	// parMap)throws Exception;
	//
	/**
	 * ���ݲ�ѯ��������ѯ�ɹ�������ϸ��¼����
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getShoppingDetailSearchRusult(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	//
	// /**
	// * ���ݲ�ѯ��������ѯ�ɹ���������Ӧ�տ��¼������
	// * @param parMap
	// * @return
	// */
	// public int getDueSearchCountByParameterMap(Map<String, String> parMap);
	//
	/**
	 * ���ݲ�ѯ��������ѯ�ɹ���������Ӧ�տ��¼����
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getDueSearchListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	/**
	 * ���ݲ�ѯ��������ѯ�ɹ�������ϸ��¼������
	 */
	public int getShoppingCountDetailSearchByParameterMap(
			Map<String, String> parMap) throws Exception;

	/**
	 * ���ݲ�ѯ��������ѯ�ɹ��������ܼ�¼����
	 */
	public QueryPage getShoppingGatherSearchRusult(
			StockDetailSearchQuery stockDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	/**
	 * ���ݲ�ѯ��������ѯ�ɹ��������ܼ�¼������
	 */
	// public QueryPage getShoppingCountGatherSearchByParameterMap(
	// StockDetailSearchQuery stockDetailSearchQuery, int currPage,
	// int pageSize) throws Exception;
	//
	/**
	 * ���ݲ�ѯ�����������Ӧ�Ĺ����̶�Ӧ�ļ�¼
	 *
	 * @param v_SearchShoppingList
	 *            ��Ӧ�Ĳ�ѯvo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public QueryPage getSearchShoppingLists(
			StockDetailSearchQuery refundDetailSearchQuery, int currPage,
			int pageSize) throws Exception;

	//
	// /**
	// * ���ݲ�ѯ�����������Ӧ�Ĺ����̶�Ӧ�ļ�¼������
	// * @param v_SearchShoppingList
	// * @return
	// * @throws Exception
	// */
	// public int getCountSupplierShoppingLists(V_SearchShoppingList
	// v_SearchShoppingList)throws Exception;
	//
	 /**
	 * ���ղɹ������޸���;��桢������ⵥ���޸Ĳ�Ʒʵ�������Ʒ���еĿ�棩
	 */
	 @Transactional
	 @SuppressWarnings("unchecked")
	 public Boolean checkShoppingList(Map parMap) throws Exception;

	/**
	 * �ɹ�����ϸ,�ɹ����ͳ�Ʋ�ѯ
	 *
	 * @param paramMap
	 * @return
	 */
	public List<ShoppingDetailSearch> getShoppingDetailStorageNum(
			Map<String, String> paramMap) throws Exception;

	//
	//
	// /**
	// * ���ݲ�ѯ��������ѯ�ɹ����������ݹ�Ӧ�տ��¼������
	// * @param parMap
	// * @return
	// */
	// public int getDueEstimateSearchCountByParameterMap(Map<String, String>
	// parMap)throws Exception;
	//
	/**
	 * ���ݲ�ѯ��������ѯ�ɹ����������ݹ�Ӧ�տ��¼����
	 *
	 * @param parameterMap
	 * @param page
	 * @return
	 */
	public QueryPage getDueEstimateSearchListsByParameterMap(
			ShoppingListQuery shoppingListQuery, int currPage, int pageSize)
			throws Exception;

	//
	/**
	 * ��Ӧ�̹�Ӧ��Ʒ����
	 *
	 * @param v_SearchShoppingList
	 * @return
	 */
	public List<V_SupplierShoppingList> getSupplierShoppingExportList(
			StockDetailSearchQuery stockDetailSearchQuery) throws Exception;
}
