package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsInstanceSupplierDao;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSupplier;
import com.huaixuan.network.biz.query.QueryPage;

/**
 * @version 3.2.0
 */
@Repository("goodsInstanceSupplierDao")
public class GoodsInstanceSupplierDaoiBatis implements GoodsInstanceSupplierDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) throws Exception {
		this.sqlMapClient.insert("addIossGoodsInstanceSupplier", iossGoodsInstanceSupplier);
	}

	@Override
	public void editGoodsInstanceSupplier(GoodsInstanceSupplier iossGoodsInstanceSupplier) throws Exception {
		this.sqlMapClient.update("editIossGoodsInstanceSupplier", iossGoodsInstanceSupplier);
	}

	@Override
	public void removeGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) throws Exception {
		this.sqlMapClient.delete("removeIossGoodsInstanceSupplier", iossGoodsInstanceSupplierId);
	}

	@Override
	public GoodsInstanceSupplier getGoodsInstanceSupplier(Long iossGoodsInstanceSupplierId) throws Exception {
		return (GoodsInstanceSupplier) this.sqlMapClient.queryForObject("getIossGoodsInstanceSupplier",
				iossGoodsInstanceSupplierId);
	}
	
	@Override
	public List<GoodsInstanceSupplier> getGoodsInstanceSuppliers() throws Exception {
		return this.sqlMapClient.queryForList("getIossGoodsInstanceSuppliers", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getGoodsInstanceSuppliersByParameterMap(Map parameterMap, int currPage, int pageSize) {
		QueryPage queryPage = new QueryPage(parameterMap);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);

		int count = (Integer) sqlMapClient.queryForObject("getIossGoodsInstanceSuppliersByParameterMapCount",
				parameterMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parameterMap.put("startRow", queryPage.getPageFristItem());
			parameterMap.put("endRow", queryPage.getPageLastItem());

			queryPage.setItems(sqlMapClient.queryForList("getIossGoodsInstanceSuppliersByParameterMap", parameterMap));
		}
		return queryPage;
	}

	@Override
	public int getGoodsInstanceSuppliersCountByParameterMap(Map parameterMap) {
		return (Integer) this.sqlMapClient.queryForObject("getIossGoodsInstanceSuppliersByParameterMapCount",
				parameterMap);
	}

	@Override
	public List<GoodsInstanceSupplier> findGoodsInstanceSuppliers(Long goodsInstanceId) {
		return this.sqlMapClient.queryForList("findGoodsInstanceSuppliers", goodsInstanceId);
	}
}
