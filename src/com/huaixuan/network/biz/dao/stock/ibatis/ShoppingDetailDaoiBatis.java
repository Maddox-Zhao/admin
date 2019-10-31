package com.huaixuan.network.biz.dao.stock.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.stock.ShoppingDetailDao;
import com.huaixuan.network.biz.domain.stock.ShoppingDetail;
import com.huaixuan.network.biz.domain.stock.ShoppingMoreDetail;

/**
* @ClassName: ShoppingDetailDaoiBatis
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-24 ÏÂÎç04:49:23
 */
@Repository("shoppingDetailDao")
public class ShoppingDetailDaoiBatis implements ShoppingDetailDao {

    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public void addShoppingDetail(ShoppingDetail shoppingDetail) throws Exception {
    	sqlMapClient.insert("addShoppingDetail", shoppingDetail);
    }

    public void editShoppingDetail(ShoppingDetail shoppingDetail) throws Exception {
        sqlMapClient.update("editShoppingDetail", shoppingDetail);
    }

    public void removeShoppingDetail(Long shoppingDetailId) throws Exception {
        sqlMapClient.delete("removeShoppingDetail", shoppingDetailId);
    }

    public ShoppingDetail getShoppingDetail(Long shoppingDetailId) throws Exception {
        return (ShoppingDetail) sqlMapClient.queryForObject("getShoppingDetail",
            shoppingDetailId);
    }

    public List<ShoppingMoreDetail> getShoppingDetailsByShoppingListId(Long shoppingListId)
                                                                                           throws Exception {
        return sqlMapClient.queryForList("getShoppingDetailsByShoppingListId",
            shoppingListId);

    }

    public List<ShoppingDetail> getShopDetailsByShopListId(Long shoppingListId) throws Exception {
        return sqlMapClient.queryForList("getShopDetailsByShopListId",
            shoppingListId);

    }

    public int getMissingNumByShoppingListId(Long shoppingListId) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
            "getMissingNumByShoppingListId", shoppingListId);
    }

    public int getRejectNumByShoppingListId(Long shoppingListId) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
            "getRejectNumByShoppingListId", shoppingListId);
    }

    public int getCountByShoppingIdAndGoodsInsId(Map<String, String> parMap) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
            "getCountByShoppingIdAndGoodsInsId", parMap);
    }

    public ShoppingDetail getGatherNumBySupplierId(Long suplierId) throws Exception {
        return (ShoppingDetail) sqlMapClient.queryForObject(
            "getGatherNumBySupplierId", suplierId);
    }

    public int getCountShoppingDetailByCatCode(Map<String, String> parMap) {
        return (Integer) sqlMapClient.queryForObject(
            "getCountShoppingDetailByCatCode", parMap);
    }

}
