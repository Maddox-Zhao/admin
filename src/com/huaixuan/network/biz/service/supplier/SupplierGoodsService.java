package com.huaixuan.network.biz.service.supplier;

import java.util.List;

import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;
import com.huaixuan.network.biz.query.QueryPage;



/**
 * @author Mr_Yang   2015-9-15 上午11:11:19
 * 供货商产品
 **/

public interface SupplierGoodsService
{
	public QueryPage searchSupplierGoods(SupplierGoods supplierGoods,int currPage,int pageSize);
	
	/**
	 * 查询供货商产品
	 * @param supplierGoods
	 * @return
	 */
	public List<SupplierGoods> selectSupplierGoods(SupplierGoods supplierGoods);
	/**
	 * 通过goodsId查询产品大小库存
	 * @param goodsId
	 * @return
	 */
	public String getGoodsSizeByGoodsId(Long goodsId);
	
	/**
	 * 通过goodsId查询产品大小库存
	 * @param goodsId
	 * @return
	 */
	public List<SupplierGoodsSize> getGoodsSizeByGoodsId2List(Long goodsId);
	
	
	/**
	 * 通过goodsId查询产品
	 */
	public SupplierGoods getGoodsByGoodsId(Long goodsId);
	
	
	/**
	 * 更新供货商产品
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	
	/**
	 * 更新供货商产品size
	 * @param supplierGoodsSize
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);

 
}
 
