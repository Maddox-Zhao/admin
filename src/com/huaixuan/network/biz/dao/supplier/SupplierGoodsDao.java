package com.huaixuan.network.biz.dao.supplier;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-15 上午10:55:09
 * 供货商产品
 **/

public interface SupplierGoodsDao
{
	/**
	 * 查询供货商产品
	 * @param supplierGoods
	 * @return
	 */
	public List<SupplierGoods> searchSupplierGoods(Map map);
	
	/**
	 * 分页查询数量
	 * @param supplierGoods
	 * @return
	 */
	public int searchSupplierGoodsCount(Map map);
	
	
	/**
	 * 通过产品ID查询该产品大小
	 * @param goodsId
	 * @return
	 */
	public String getGoodsSizeByGoodsId(Long goodsId);
	
	
	/**
	 * 通过产品ID查询该产品大小 返回对象
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
	 * @param supplierGoods
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	/**
	 * 更新供货商产品大小
	 * @param supplierGoodsSize
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
}
 
