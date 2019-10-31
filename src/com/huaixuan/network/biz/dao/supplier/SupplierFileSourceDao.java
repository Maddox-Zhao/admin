package com.huaixuan.network.biz.dao.supplier;

import java.util.List;
import java.util.Map;

import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-7 下午02:07:20
 **/

public interface SupplierFileSourceDao
{
	/**
	 * 分页查询供货商源文件
	 * @return
	 */
	public List<SupplierFileSource> searchFileSource(Map paramMap);
	
	
	public int searchFileSourceCount(Map paramMap);
	
	
	/**
	 * 根据供货商源文件ID获取文件信息
	 * @param id
	 * @return
	 */
	public SupplierFileSource getSupplierFileSourceByFileId(Long id);
	
	
	/**
	 * 删除供货商源文件
	 * @param id
	 */
	public void deleteSupplierSourceFile(Long id);
	
	
	/**
	 * 更新供货商源文件信息
	 * @param supplierFileSource
	 */
	public void updateSupplierSourceFile(SupplierFileSource supplierFileSource);
	
	
	
	
	
	
	//处理过的Excel文件
	
	
	/**
	 * 添加处理过的Excel文件
	 */
	public Long insertSupplierFile(SupplierFile supplierFile);
	
	
	
	
	
	/**
	 * 通过型号 材质 颜色 供货商ID查询goods
	 * @param map
	 * @return
	 */
	public SupplierGoods getSupplierGoodsByMap(Map map);
	
	/**
	 * 插入供货商产品
	 * @param supplierGoods
	 */
	public void insertSupplierGoods(SupplierGoods supplierGoods);
	
	
	/**
	 * 更新供货商产品
	 * @param supplierGoods
	 */
	public void updateSupplierGoods(SupplierGoods supplierGoods);
	
	
	
	
	
	
	
	/**
	 产品size
	 */
	
	/**
	 * 通过型号 材质 颜色 供货商ID查询goods
	 * @param map
	 * @return
	 */
	public SupplierGoodsSize getSupplierGoodsSizeByMap(Map map);
	
	/**
	 * 插入供货商产品
	 * @param supplierGoods
	 */
	public void insertSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
	
	
	/**
	 * 更新供货商产品
	 * @param supplierGoods
	 */
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize);
	
	
	
	
}
 
