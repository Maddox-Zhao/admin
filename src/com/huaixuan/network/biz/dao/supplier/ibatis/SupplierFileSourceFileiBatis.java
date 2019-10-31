package com.huaixuan.network.biz.dao.supplier.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.huaixuan.network.biz.dao.supplier.SupplierFileSourceDao;
import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.domain.supplier.SupplierGoods;
import com.huaixuan.network.biz.domain.supplier.SupplierGoodsSize;



/**
 * @author Mr_Yang   2015-9-7 下午02:13:36
 **/

@Repository("supplierFileSourceDao")
public class SupplierFileSourceFileiBatis implements SupplierFileSourceDao
{
	 @Autowired
	 private SqlMapClientTemplate sqlMapClient;

	@Override
	public void deleteSupplierSourceFile(Long id)
	{
		
	}

	@Override
	public SupplierFileSource getSupplierFileSourceByFileId(Long id)
	{
		return  (SupplierFileSource)this.sqlMapClient.queryForObject("selectFileById", id);
	}

	@Override
	public List<SupplierFileSource> searchFileSource(Map paramMap)
	{
		  return this.sqlMapClient.queryForList("searchSupplierFileSourceList", paramMap);
	}
	
	@Override
	public int  searchFileSourceCount(Map paramMap) 
	{
		return (Integer)sqlMapClient.queryForObject("searchSupplierFileSourceListCount", paramMap);
	}

	@Override
	public void updateSupplierSourceFile(SupplierFileSource supplierFileSource)
	{
		 sqlMapClient.update("updateFileByNotNull",supplierFileSource);
	}

	
	
	
	
	
	//上传处理过的Excel文件
	
	
	//添加Excel文件
	@Override
	public Long insertSupplierFile(SupplierFile supplierFile)
	{
		return  (Long)sqlMapClient.insert("insertSupplierFile",supplierFile);
	}

	
	
	
	//产品导入所需的操作
	
	
	@Override
	public SupplierGoods getSupplierGoodsByMap(Map map)
	{
		Object obj = sqlMapClient.queryForObject("selectSupplierGoodsByNotNull",map);
		if(obj == null) return null;
		SupplierGoods  s =  (SupplierGoods)obj;
		return s;
	}
	
	@Override
	public void insertSupplierGoods(SupplierGoods supplierGoods)
	{
		sqlMapClient.insert("insertSupplierGoods",supplierGoods);
	}
	
	@Override
	public void updateSupplierGoods(SupplierGoods supplierGoods)
	{
		sqlMapClient.update("updateSupplierGoodsByNotNull",supplierGoods);
	}

	@Override
	public SupplierGoodsSize getSupplierGoodsSizeByMap(Map map)
	{
		Object obj = sqlMapClient.queryForObject("selectSupplierGoodsSizeByNotNull",map);
		if(obj == null) return null;
		SupplierGoodsSize  s =  (SupplierGoodsSize)obj;
		return s;
	}

	

	@Override
	public void insertSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize)
	{
	 
		sqlMapClient.insert("insertSupplierGoodsSize",supplierGoodsSize);
	}


	@Override
	public void updateSupplierGoodsSize(SupplierGoodsSize supplierGoodsSize)
	{
		sqlMapClient.update("updateSupplierGoodsSizeByNotNull",supplierGoodsSize);
	}

}
 
