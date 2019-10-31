package com.huaixuan.network.biz.dao.stock.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.stock.ShoppingListDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetailSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingGatherSearch;
import com.huaixuan.network.biz.domain.stock.ShoppingList;
import com.huaixuan.network.biz.domain.stock.V_SupplierShoppingList;
import com.huaixuan.network.biz.domain.stock.query.StockDetailSearchQuery;

/**
* @ClassName: ShoppingListDaoiBatis
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:30
 */
@Repository("shoppingListDao")
public class ShoppingListDaoiBatis implements ShoppingListDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public long addShoppingList(ShoppingList shoppingList) throws Exception {
        return (Long) sqlMapClient.insert("addShoppingList", shoppingList);
    }

    public void editShoppingList(ShoppingList shoppingList) throws Exception {
        sqlMapClient.update("editShoppingList", shoppingList);
    }

    public void editShoppingListAllInfo(ShoppingList shoppingList) throws Exception {
        sqlMapClient.update("editShoppingListAllInfo", shoppingList);
    }

    public void removeShoppingList(Long shoppingListId) throws Exception {
        sqlMapClient.delete("removeShoppingList", shoppingListId);
    }

    public ShoppingList getShoppingList(Long shoppingListId) throws Exception {
        return (ShoppingList) sqlMapClient.queryForObject("getShoppingList",
            shoppingListId);
    }

    public ShoppingList getShoppingListByShoppingNum(String shoppingNum) throws Exception {
        return (ShoppingList) sqlMapClient.queryForObject(
            "getShoppingListByShoppingNum", shoppingNum);
    }

    public int getCountByShoppingNum(String shoppingNum) throws Exception {
        return (Integer) sqlMapClient.queryForObject("getCountByShoppingNum",
            shoppingNum);
    }

    public List<ShoppingList> getShoppingListByParameterMap(Map parameterMap)throws Exception {
//        if (page != null) {
//        	return this.findQueryPage("getShoppingLists", parameterMap, page);
//        }
        return sqlMapClient.queryForList("getShoppingLists", parameterMap);
    }

    public int getCountByParameterMap(Map<String, String> parMap) {
        return (Integer) sqlMapClient.queryForObject("getShoppingListsCount",
            parMap);
    }

    public int getDueSearchCountByParameterMap(Map<String, String> parMap) throws Exception {
        return (Integer) sqlMapClient.queryForObject("getDueSearchListCount",
            parMap);
    }

    public List<ShoppingList> getDueSearchListsByParameterMap(Map parameterMap)throws Exception {
//        if (page != null) {
//        	return this.findQueryPage("getDueSearchList", parameterMap, page);
//        }
        return sqlMapClient.queryForList("getDueSearchList", parameterMap);
    }

    public int getShoppingCountDetailSearchByParameterMap(Map<String, String> parMap) {
        return (Integer) sqlMapClient.queryForObject(
            "getShoppingDetailSearchCount", parMap);
    }

    @SuppressWarnings("unchecked")
    public List<ShoppingDetailSearch> getShoppingDetailSearchRusultByParameterMap(Map parameterMap) {
//        if (page != null) {
//        	return this.findQueryPage("getShoppingDetailSearchList", parameterMap, page);
//        }
        return sqlMapClient.queryForList("getShoppingDetailSearchList",parameterMap);
    }

    public int getShoppingCountGatherSearchByParameterMap(Map<String, String> parMap) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
            "getShoppingGatherSearchCount", parMap);
    }

    public List<ShoppingGatherSearch> getShoppingGatherSearchRusultByParameterMap(Map parameterMap)
                                                                                            throws Exception {
//        if (page != null) {
//        	return this.findQueryPage("getShoppingGatherSearchList", parameterMap, page);
//        }
        return sqlMapClient.queryForList("getShoppingGatherSearchList",parameterMap);
    }

    /**
     *
     * @param v_SearchShoppingList
     * @return
     * @throws Exception
     */
    public List<V_SupplierShoppingList> getSearchShoppingLists(Map map) throws Exception {

        return sqlMapClient.queryForList("getSupplierShoppingLists", map);
    }

    /**
     *
     * @param v_SearchShoppingList
     * @return
     * @throws Exception
     */
    public int getCountSupplierShoppingLists(Map map) throws Exception {

        return (Integer) sqlMapClient.queryForObject(
            "getCountSupplierShoppingLists", map);
    }

    public Long getSupplierIdByShoppingNum(String shoppingNum) {
        return (Long) sqlMapClient.queryForObject("getSupplierIdByShoppingNum",
            shoppingNum);
    }

    public ShoppingList getSupplierIdAndGmtCreateForUpdate(Long inDepId) {
        if (inDepId == null) {
            return null;
        }
        return (ShoppingList) sqlMapClient.queryForObject(
            "getSupplierIdAndGmtCreateForUpdate", inDepId);
    }

    @SuppressWarnings("unchecked")
    public List<ShoppingDetailSearch> getShoppingDetailStorageNum(Map<String, String> paramMap) {
        return sqlMapClient.queryForList("getShoppingDetailStorageNum", paramMap);
    }

	public int getDueEstimateSearchCountByParameterMap(
			Map<String, String> parMap) throws Exception {
		return (Integer)sqlMapClient.queryForObject("getDueEstimateSearchCountByParameterMap", parMap);
	}

	public List<ShoppingList> getDueEstimateSearchListsByParameterMap(
			Map parameterMap) throws Exception {
//		if(page == null){
//			return sqlMapClient.queryForList("getDueEstimateSearchListsByParameterMap", parameterMap);
//		}
		return sqlMapClient.queryForList("getDueEstimateSearchListsByParameterMap", parameterMap);
	}

	public List<V_SupplierShoppingList> getSupplierShoppingExportList(
			StockDetailSearchQuery stockDetailSearchQuery) {
		return sqlMapClient.queryForList("getSupplierShoppingExportList", stockDetailSearchQuery);
	}
}
