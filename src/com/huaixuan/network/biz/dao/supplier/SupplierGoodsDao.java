package com.huaixuan.network.biz.dao.supplier;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-15 ����10:55:09
 * �����̲�Ʒ
 **/

public interface SupplierGoodsDao
{
	/**
	 * ��ѯ�����̲�Ʒ
	 * @param supplierGoods
	 * @return
	 */
	public List<SupplierGoods> searchSupplierGoods(Map map);
	
	/**
	 * ��ҳ��ѯ����
	 * @param supplierGoods
	 * @return
	 */
	public int searchSupplierGoodsCount(Map map);
	
	
	/**
	 * ͨ����ƷID��ѯ�ò�Ʒ��С
	 * @param goodsId
	 * @return
	 */
	public String getGoodsSizeByGoodsId(Long goodsId);
	
	
	/**
	 * ͨ����ƷID��ѯ�ò�Ʒ��С ���ض���
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
	 * @param supplierGoods
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	/**
	 * ���¹����̲�Ʒ��С
	 * @param supplierGoodsSize
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
}
 
