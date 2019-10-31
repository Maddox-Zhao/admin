package com.huaixuan.network.biz.dao.goods.ibatis;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsInstanceDao;
import com.huaixuan.network.biz.domain.goods.GoodsInstance;
import com.huaixuan.network.biz.domain.storage.StockAge;
import com.huaixuan.network.biz.domain.storage.StorageCheck;
import com.huaixuan.network.biz.domain.taobao.ProductForTaobaoFxAdd;
import com.huaixuan.network.biz.query.QueryPage;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@Repository("goodsInstanceDao")
public class GoodsInstanceDaoIbatis implements GoodsInstanceDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClient;

    public void createGoodsInstance(GoodsInstance gi) {
        this.sqlMapClient.insert("createGoodsInstance", gi);
    }

    public void insertBatch(final List<GoodsInstance> goodsList) {
        sqlMapClient.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < goodsList.size(); i++) {
                    GoodsInstance o = goodsList.get(i);
                    executor.insert("createGoodsInstance", o);
                }
                executor.executeBatch();
                return null;
            }
        });
        return;
    }

    public List<GoodsInstance> findGoodsInstancesByGoodsId(Long goodsId) {
        return this.sqlMapClient.queryForList("findGoodsInstanceByGoodsId", goodsId);
    }

    public GoodsInstance getInstance(Long id) {
        return (GoodsInstance) this.sqlMapClient.queryForObject("loadGoodsInstanceById", id);
    }
    
    public GoodsInstance getClientInstance(Map pramas){
        return (GoodsInstance) this.sqlMapClient.queryForObject("getClientInstance", pramas);
    }

    public GoodsInstance getInstanceByCode(String code) {
        return (GoodsInstance) this.sqlMapClient.queryForObject("getInstanceByCode", code);
    }

    public void updateGoodsInstance(GoodsInstance gi) {
        this.sqlMapClient.update("updateGoodsInstance", gi);

    }

    //    public List<GoodsInstance> searchGoodsInstances(GoodsInstance search, Page page) {
    //        Map parameter = new HashMap();
    //        parameter.put("search", search);
    //        if (null == page) {
    //            return this.sqlMapClient.queryForList("searchGoodsInstance", parameter);
    //        }
    //        parameter.put("page", page);
    //        Integer total = (Integer) this.sqlMapClient.queryForObject(
    //            "searchGoodsInstanceCount", parameter);
    //        int current = page.getCurrentPage();
    //        page.setTotalRowsAmount(total);
    //        page.setCurrentPage(current);
    //        if (total == 0) {
    //            return Collections.EMPTY_LIST;
    //        }
    //
    //        return this.findQueryPage("searchGoodsInstance", parameter, page);
    //    }

    @SuppressWarnings("unchecked")
    public QueryPage searchGoodsInstancesHasStorage(GoodsInstance search, int currentPage,
                                                    int pageSize) {
		QueryPage queryPage = new QueryPage(search);
		Map pramas = queryPage.getParameters();

        int count = (Integer) sqlMapClient.queryForObject("searchGoodsInstancesHasStorageCount", pramas);
        if (count > 0) {
			/* 当前页 */
			queryPage.setCurrentPage(currentPage);
			/* 每页总数 */
			queryPage.setPageSize(pageSize);
			queryPage.setTotalItem(count);

			pramas.put("startRow", queryPage.getPageFristItem());
			pramas.put("endRow", queryPage.getPageLastItem());
            /* 分页查询  */
            queryPage.setItems(sqlMapClient.queryForList("searchGoodsInstancesHasStorage", pramas));
        }
        return queryPage;
    }

    public void updateGoodsInstanceLocations(Long id, List<Long> los) {
        this.sqlMapClient.delete("deleteGoodsInstanceLocations", id);
        for (Long loid : los) {
            Map map = new HashMap();
            map.put("goodsInstanceId", id);
            map.put("loid", loid);
            this.sqlMapClient.insert("insertGoodsInstanceLocations", map);
        }
    }

    public List<Long> findGoodsInstanceLocations(Long id) {
        return this.sqlMapClient.queryForList("findGoodsInstanceLocations", id);
    }

    public List<Long> findGoodsInstanceInLocations(Long locId) {
        return this.sqlMapClient.queryForList("findGoodsInstanceInLocations", locId);
    }

    public List<GoodsInstance> findGoodsInstancesByCode(String code) {
        return this.sqlMapClient.queryForList("findGoodsInstanceByCode", code);
    }

    @SuppressWarnings("unchecked")
    public int updateGoodsInstanceExistNumById(Long id, Long count) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("count", count);
        return this.sqlMapClient.update("updateGoodsInstanceExistNumById", map);
    }
    
    
    @SuppressWarnings("unchecked")
    public int updateGoodsInstanceSaleNumberById(Long id, Long count) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("count", count);
        return this.sqlMapClient.update("updateGoodsInstanceSaleNumberById", map);
    }
    
    @SuppressWarnings("unchecked")
    public int updateGoodsInstanceHKExistNumById(Long id, Long count) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("count", count);
        return this.sqlMapClient.update("updateGoodsInstanceHKExistNumById", map);
    }
    
    @SuppressWarnings("unchecked")
    public int updateGoodsInstanceNumberZero() {
        Map map = new HashMap();
        return this.sqlMapClient.update("updateGoodsInstanceNumberZero", map);
    }
    
    
    /*
     * （非 Javadoc＄1??7
     *
     * @see com.hundsun.bible.dao.ios.GoodsInstanceDao#updateWayNumById(java.lang.Long,
     *      java.lang.Long)
     */
    public int updateWayNumById(Long id, Long count) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("count", count);
        return this.sqlMapClient.update("updateWayNumById", map);
    }

    public void updateGoodsUnit(long id, String goodsUnit) {
        Map map = new HashMap();
        map.put("goodsId", id);
        map.put("goodsUnit", goodsUnit);
        this.sqlMapClient.update("updateGoodsInstanceUnit", map);
    }

    //    public List<GoodsInstance> searchSupplierGoodsInstances(GoodsInstance search, Page page,
    //                                                            String supplierId) {
    //        Map parameter = new HashMap();
    //        parameter.put("search", search);
    //        parameter.put("page", page);
    //        parameter.put("supplierId", supplierId);
    //        Integer total = (Integer) this.sqlMapClient.queryForObject(
    //            "searchSupplierGoodsInstanceCount", parameter);
    //        int current = page.getCurrentPage();
    //        page.setTotalRowsAmount(total);
    //        page.setCurrentPage(current);
    //        if (total == 0) {
    //            return Collections.EMPTY_LIST;
    //        }
    //        return this.findQueryPage("searchSupplierGoodsInstance", parameter, page);
    //    }

    public void updateGoodsInstanceByMap(Map map) {
        this.sqlMapClient.update("updateGoodsInstanceByMap", map);
    }

    public void moveGoodsInstanceAttrs(long id) {
        this.sqlMapClient.delete("deleteGoodsInstanceAttrs", id);
    }

    public int getStockNumByInstanceId(Map map) {
        Integer num = (Integer) this.sqlMapClient.queryForObject("getStockNumByInstanceId", map);
        if (num != null) {
            return num;
        }
        return 0;
    }

    public List<StockAge> getStockSupplierByInstanceId(Long id) {
        return this.sqlMapClient.queryForList("getStockSupplierByInstanceId", id);
    }

    public long getStockAgeListByInstanceId(Long locId) {
        return (Long) this.sqlMapClient.queryForObject("getStockAgeListByInstanceId", locId);
    }

    public void insertStockAgeBySa(StockAge sa) {
        this.sqlMapClient.insert("insertStockAgeBySa", sa);
    }

    public int updateStockAgeBysa(StockAge sa) {
        return (Integer) this.sqlMapClient.update("updateStockAgeBysa", sa);
    }

    public List<GoodsInstance> getGoodsInstances() {
        return this.sqlMapClient.queryForList("getGoodsInstances");
    }

    //    public List<ProductForTaobaoFxAdd> getProductsForTaobaoFxAdd(
    //			ProductForTaobaoFxAdd productForTaobaoFxAdd, Page page) {
    //		if (page == null) {
    //			Map parm = new HashMap();
    //			//parm.put("name", supplier.getName());
    //			//parm.put("grade", supplier.getGrade());
    //			//parm.put("status", supplier.getStatus());
    //			//parm.put("brand", supplier.getBrand());
    //
    //			return this.sqlMapClient.queryForList(
    //					"getProductsForTaobaoFxAdd", parm);
    //		}
    //		return this.findQueryPage("getProductsForTaobaoFxAdd",
    //				productForTaobaoFxAdd, page);
    //	}

    public int getProductsForTaobaoFxAddCount(ProductForTaobaoFxAdd productForTaobaoFxAdd) {
        return (Integer) this.sqlMapClient.queryForObject("getProductsForTaobaoFxAddCount",
            productForTaobaoFxAdd);
    }

    /**
     * 根据goodsInsId查询淘宝属性key
     * @param id
     * @return
     * @see com.hundsun.bible.dao.ios.GoodsInstanceDao#getInstanceTaobaoSkuPropById(java.lang.Long)
     */
    public GoodsInstance getInstanceTaobaoSkuPropById(Long id) {
        if (id != null) {
            return (GoodsInstance) this.sqlMapClient.queryForObject(
                "searchTaobaoSkuPropByGoodsInsId", id);
        }
        return null;
    }

    public List<String> getSkuPropertyListByGoodsId(Long goodsId) {
        return this.sqlMapClient.queryForList("getSkuPropertyListByGoodsId", goodsId);
    }

    //	public List<GoodsInstance> getSaleGiftGoodsInstance(Map parMap, Page page) {
    //		if(page == null){
    //			return this.sqlMapClient.queryForList("getSaleGiftGoodsInstance", parMap);
    //		}else{
    //			return this.findQueryPage("getSaleGiftGoodsInstance", "getSaleGiftGoodsInstanceCount", parMap, page);
    //		}
    //	}

    public List<GoodsInstance> getFullGiveGoodsInstance(Map map) {
        return this.sqlMapClient.queryForList("getFullGiveGoodsInstance", map);
    }

    @Override
    public Integer getInstanceListByConditionWithPageCount(Map parMap) {
        return (Integer) sqlMapClient.queryForObject("searchGoodsInstanceCount", parMap);
    }

    @Override
    public List<GoodsInstance> getInstanceListByConditionWithPage(Map parMap) {
        return sqlMapClient.queryForList("searchGoodsInstance", parMap);
    }

    @Override
    public Integer getSupplierInstanceListByConditionWithPageCount(Map parMap) {
        return (Integer) sqlMapClient.queryForObject("searchSupplierGoodsInstanceCount", parMap);
    }

    @Override
    public List<GoodsInstance> getSupplierInstanceListByConditionWithPage(Map parMap) {
        return sqlMapClient.queryForList("searchSupplierGoodsInstance", parMap);
    }

    @Override
    public Integer getSaleGiftInstanceListByConditionWithPageCount(Map parMap) {
        return (Integer) sqlMapClient.queryForObject("getSaleGiftGoodsInstanceCount", parMap);
    }

    @Override
    public List<GoodsInstance> getSaleGiftInstanceListByConditionWithPage(Map parMap) {
        return sqlMapClient.queryForList("getSaleGiftGoodsInstance", parMap);
    }

    @Override
    public Integer getBackInstanceListByConditionWithPageCount(Map parMap) {
        return (Integer) sqlMapClient.queryForObject("searchBackGoodsInstanceCount", parMap);
    }

    @Override
    public List<GoodsInstance> getBackInstanceListByConditionWithPage(Map parMap) {
        return sqlMapClient.queryForList("searchBackGoodsInstance", parMap);
    }

    @Override
    public List<GoodsInstance> searchGoodsInstancesHasStorage(Map<String, String> pramas) {
        return this.sqlMapClient.queryForList("searchGoodsInstancesHasStorage", pramas);
    }

}
