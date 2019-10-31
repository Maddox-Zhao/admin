package com.huaixuan.network.biz.service.supplier;

import java.io.InputStream;

import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.Result;



/**
 * @author Mr_Yang   2015-9-7 下午02:17:35
 * 供货商源文件处理
 **/

public interface SupplierFileSourceService
{
     /**
      * 查询供货商源文件	
      * @param supplier 供货商
      * @param currPage 当前页面 
      * @param pageSize 页面大小
      * @return
      */
	 public QueryPage searchSupplierFileSourceListWithPage(SupplierFileSource supplierFileSource, int currPage, int pageSize);
	 
	 /**
	  * 查询源文件ID
	  * @param id
	  * @return
	  */
	 public SupplierFileSource getSupplierFileSourceById(Long id);
	 
	 
	 public void updateSupplierFileSourceByNotNull(SupplierFileSource supplierFileSource);
	 
	 
	 
	 //Excel文件处理
	 
	 
	 //保存上传的Excel文件
	 public Long insertSupplierFile(SupplierFile supplierFile);
	 
	 
	 
	 /**
	  * 将excel数据导入supplierGoods表
	  * @param path 路径
	  * @return
	  */
	
	 public Result exportExcel2SupplierGoods(String path,Long supplerId,Long fileId);
	 
	 
	 /**
	  * 将excel数据导入supplierGoods表
	  * @param is 输入流
	  * @return
	  */
	 public Result exportExcel2SupplierGoods(InputStream is,Long supplerId,Long fileId);
}
 
