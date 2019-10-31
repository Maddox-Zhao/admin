package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * 供应商商品service
 *
 * @version 3.2.0
 */
public interface GoodsInstanceSupplierManager {
    /**
     * 供应商商品新增
     * @param iossGoodsInstanceSupplier
     */
    public void addGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier);

    /**
     * 供应商商品编辑
     * @param iossGoodsInstanceSupplier
     */
    public void editGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier);

    /**
     * 供应商商品删除
     * @param iossGoodsInstanceSupplierId
     */
    public void removeGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId);

    /**
     * 获得供应商商品
     * @param iossGoodsInstanceSupplierId
     * @return
     */
    public GoodsInstanceSupplier getGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId);

    /**
     * 获取所有供应商商品
     * @return
     */
    public List<GoodsInstanceSupplier> getGoodsInstanceSuppliers();

    List<GoodsInstanceSupplier> getGoodsInstanceSuppliersByParameterMap(Map parameterMap,
                                                                        int currPage, int pageSize);

    public QueryPage getGoodsInstanceSuppliersByQuery(Map parameterMap, int currPage, int pageSize);

    int getGoodsInstanceSuppliersCountByParameterMap(Map parameterMap);

    List<GoodsInstanceSupplier> findGoodsInstanceSuppliers(Long goodsId);

    public void createInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier)
                                                                                       throws Exception;
}
