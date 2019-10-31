package com.huaixuan.network.biz.dao.goods;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * �����Զ����(bibleUtil auto code generation)
 * 
 * @version 3.2.0
 */
public interface GoodsInstanceSupplierDao {
	/* @interface model: ���һ��IossGoodsInstanceSupplier��¼ */
	void addGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) throws Exception;

	/* @interface model: ����һ��IossGoodsInstanceSupplier��¼ */
	void editGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) throws Exception;

	/* @interface model: ɾ��һ��IossGoodsInstanceSupplier��¼ */
	void removeGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) throws Exception;

	/*
	 * @interface model: ��ѯһ��IossGoodsInstanceSupplier���,����IossGoodsInstanceSupplier����
	 */
	GoodsInstanceSupplier getGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) throws Exception;

	/*
	 * @interface model: ��ѯ����IossGoodsInstanceSupplier���,����IossGoodsInstanceSupplier����ļ���
	 */
	List<GoodsInstanceSupplier> getGoodsInstanceSuppliers() throws Exception;

	QueryPage getGoodsInstanceSuppliersByParameterMap(Map parameterMap, int currPage, int pageSize);

	int getGoodsInstanceSuppliersCountByParameterMap(Map parameterMap) throws Exception;

	List<GoodsInstanceSupplier> findGoodsInstanceSuppliers(Long goodsInstanceId);
}
