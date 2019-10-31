package com.huaixuan.network.biz.dao.goods.ibatis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.goods.GoodsInstanceSalesDao;
import com.huaixuan.network.biz.domain.crm.query.CrmRankQuery;
import com.huaixuan.network.biz.domain.goods.Goods;
import com.huaixuan.network.biz.domain.goods.GoodsInstanceSales;
import com.huaixuan.network.biz.query.QueryPage;

@Repository("goodsInstanceSalesDao")
public class GoodsInstanceSalesDaoiBatis implements GoodsInstanceSalesDao {
	@Autowired
	private SqlMapClientTemplate sqlMapClient;

	@Override
	public void addGoodsInstanceSales(GoodsInstanceSales goodsInstanceSales) throws Exception{
		this.sqlMapClient.insert("addGoodsInstanceSales", goodsInstanceSales);
	}

	@Override
	public double getRefundAmountInfo(Map parMap) throws Exception{
		return (Double)this.sqlMapClient.queryForObject("getRefundAmountInfo", parMap);
	}

	@Override
	public double getSalesAmountInfo(Map parMap) throws Exception{
		return (Double)this.sqlMapClient.queryForObject("getSalesAmountInfo", parMap);
	}

	@Override
	public int getProductByParameterMap(Map parMap) throws Exception{
		return (Integer)this.sqlMapClient.queryForObject("getProductByParameterMap", parMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryPage getProductListsByParameterMap(CrmRankQuery crmProductRankQuery, int currPage, int pageSize) throws Exception{
		QueryPage queryPage = new QueryPage(crmProductRankQuery);
		queryPage.setCurrentPage(currPage);
		queryPage.setPageSize(pageSize);
		Map parMap = queryPage.getParameters();
		int count = (Integer) sqlMapClient.queryForObject("getProductByParameterMap", parMap);
		queryPage.setTotalItem(count);

		if (count > 0) {
			parMap.put("startRow", queryPage.getPageFristItem());
			parMap.put("endRow", queryPage.getPageLastItem());
			queryPage.setItems(sqlMapClient.queryForList("getProductListsByParameterMap", parMap));
		}
		return queryPage;
	}

	@Override
	public void deleteProductByParameterMap(Map parMap) throws Exception{
		this.sqlMapClient.delete("deleteProductByParameterMap", parMap);
	}

	@Override
	public int getSearchDayByMap(Map parMap) throws Exception{
		return (Integer)this.sqlMapClient.queryForObject("getGoodsSearchDayByMap", parMap);
	}

}
