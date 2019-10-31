package com.huaixuan.network.biz.service.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * ��Ӧ����Ʒservice
 *
 * @version 3.2.0
 */
public interface GoodsInstanceSupplierManager {
    /**
     * ��Ӧ����Ʒ����
     * @param iossGoodsInstanceSupplier
     */
    public void addGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier);

    /**
     * ��Ӧ����Ʒ�༭
     * @param iossGoodsInstanceSupplier
     */
    public void editGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier);

    /**
     * ��Ӧ����Ʒɾ��
     * @param iossGoodsInstanceSupplierId
     */
    public void removeGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId);

    /**
     * ��ù�Ӧ����Ʒ
     * @param iossGoodsInstanceSupplierId
     * @return
     */
    public GoodsInstanceSupplier getGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId);

    /**
     * ��ȡ���й�Ӧ����Ʒ
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
