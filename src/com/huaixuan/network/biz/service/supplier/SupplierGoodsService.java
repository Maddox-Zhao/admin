package com.huaixuan.network.biz.service.supplier;

import java.util.List;

import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;
import com.huaixuan.network.biz.query.QueryPage;



/**
 * @author Mr_Yang   2015-9-15 ����11:11:19
 * �����̲�Ʒ
 **/

public interface SupplierGoodsService
{
	public QueryPage searchSupplierGoods(SupplierGoods supplierGoods,int currPage,int pageSize);
	
	/**
	 * ��ѯ�����̲�Ʒ
	 * @param supplierGoods
	 * @return
	 */
	public List<SupplierGoods> selectSupplierGoods(SupplierGoods supplierGoods);
	/**
	 * ͨ��goodsId��ѯ��Ʒ��С���
	 * @param goodsId
	 * @return
	 */
	public String getGoodsSizeByGoodsId(Long goodsId);
	
	/**
	 * ͨ��goodsId��ѯ��Ʒ��С���
	 * @param goodsId
	 * @return
	 */
	public List<SupplierGoodsSize> getGoodsSizeByGoodsId2List(Long goodsId);
	
	
	/**
	 * ͨ��goodsId��ѯ��Ʒ
	 */
	public SupplierGoods getGoodsByGoodsId(Long goodsId);
	
	
	/**
	 * ���¹����̲�Ʒ
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	
	/**
	 * ���¹����̲�Ʒsize
	 * @param supplierGoodsSize
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);

 
}
 
