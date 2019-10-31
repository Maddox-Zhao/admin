package com.huaixuan.network.biz.dao.supplier;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-7 ����02:07:20
 **/

public interface SupplierFileSourceDao
{
	/**
	 * ��ҳ��ѯ������Դ�ļ�
	 * @return
	 */
	public List<SupplierFileSource> searchFileSource(Map paramMap);
	
	
	public int searchFileSourceCount(Map paramMap);
	
	
	/**
	 * ���ݹ�����Դ�ļ�ID��ȡ�ļ���Ϣ
	 * @param id
	 * @return
	 */
	public SupplierFileSource getSupplierFileSourceByFileId(Long id);
	
	
	/**
	 * ɾ��������Դ�ļ�
	 * @param id
	 */
	public void deleteSupplierSourceFile(Long id);
	
	
	/**
	 * ���¹�����Դ�ļ���Ϣ
	 * @param supplierFileSource
	 */
	public void updateSupplierSourceFile(SupplierFileSource supplierFileSource);
	
	
	
	
	
	
	//�������Excel�ļ�
	
	
	/**
	 * ��Ӵ������Excel�ļ�
	 */
	public Long insertSupplierFile(SupplierFile supplierFile);
	
	
	
	
	
	/**
	 * ͨ���ͺ� ���� ��ɫ ������ID��ѯgoods
	 * @param map
	 * @return
	 */
	public SupplierGoods getSupplierGoodsByMap(Map map);
	
	/**
	 * ���빩���̲�Ʒ
	 * @param supplierGoods
	 */
	public void insertSupplierGoods(SupplierGoods supplierGoods);
	
	
	/**
	 * ���¹����̲�Ʒ
	 * @param supplierGoods
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	
	
	
	
	
	
	/**
	 ��Ʒsize
	 */
	
	/**
	 * ͨ���ͺ� ���� ��ɫ ������ID��ѯgoods
	 * @param map
	 * @return
	 */
	public SupplierGoodsSize getSupplierGoodsSizeByMap(Map map);
	
	/**
	 * ���빩���̲�Ʒ
	 * @param supplierGoods
	 */
	public void insertSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
	
	
	/**
	 * ���¹����̲�Ʒ
	 * @param supplierGoods
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
	
	
	
	
}
 
