package com.huaixuan.network.biz.service.supplier.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huaixuan.network.biz.dao.supplier.SupplierGoodsDao;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.supplier.SupplierGoodsService;



/**
 * @author Mr_Yang   2015-9-15 上午11:12:25
 **/

@Service("supplierGoodsService")
public class SupplierGoodsServiceImpl implements SupplierGoodsService
{

	@Autowired
	private SupplierGoodsDao supplierGoodsDao;
	
	@Override
	public QueryPage searchSupplierGoods(SupplierGoods supplierGoods,int currPage, int pageSize)
	{
		   QueryPage queryPage = new QueryPage(supplierGoods);
	        Map pramas = queryPage.getParameters();
	        
	        int count = supplierGoodsDao.searchSupplierGoodsCount(pramas);
	        if (count > 0) {
	            /* 当前页 */
	            queryPage.setCurrentPage(currPage);
	            /* 每页总数 */
	            queryPage.setPageSize(pageSize);
	            queryPage.setTotalItem(count);

	            pramas.put("startRow", queryPage.getPageFristItem());
	            pramas.put("endRow", queryPage.getPageLastItem());

	            List<SupplierGoods> supplierGoodsList = supplierGoodsDao.searchSupplierGoods(pramas);
	            if (supplierGoodsList != null && supplierGoodsList.size() > 0) {
	                queryPage.setItems(supplierGoodsList);
	            }
	        }
	        else
	        {
	        	queryPage.setItems(new ArrayList<SupplierGoods>());
	        }
	        
	      return queryPage;
	}

	@Override
	public String getGoodsSizeByGoodsId(Long goodsId)
	{
		return  supplierGoodsDao.getGoodsSizeByGoodsId(goodsId);
	}

	@Override
	public SupplierGoods getGoodsByGoodsId(Long goodsId)
	{
		return supplierGoodsDao.getGoodsByGoodsId(goodsId);
	}

	@Override
	public List<SupplierGoodsSize> getGoodsSizeByGoodsId2List(Long goodsId)
	{
		return  supplierGoodsDao.getGoodsSizeByGoodsId2List(goodsId);
	}

	@Override
	public void updateSupplierGoods(SupplierGoods supplierGoods)
	{
		supplierGoodsDao.updateSupplierGoods(supplierGoods);
	}

	@Override
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize)
	{
			supplierGoodsDao.updateSupplierGoodsSize(supplierGoodsSize);
	}

	@Override
	public List<SupplierGoods> selectSupplierGoods(SupplierGoods supplierGoods)
	{
	    QueryPage queryPage = new QueryPage(supplierGoods);
        Map pramas = queryPage.getParameters();
		return supplierGoodsDao.searchSupplierGoods(pramas);
	}

 

 

}
 
