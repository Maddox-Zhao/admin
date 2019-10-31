package com.huaixuan.network.biz.service.supplier;

import java.io.InputStream;

import com.huaixuan.network.biz.domain.supplier.SupplierFile;
import com.huaixuan.network.biz.domain.supplier.SupplierFileSource;
import com.huaixuan.network.biz.query.QueryPage;
import com.huaixuan.network.biz.service.autosyn.Result;



/**
 * @author Mr_Yang   2015-9-7 ����02:17:35
 * ������Դ�ļ�����
 **/

public interface SupplierFileSourceService
{
     /**
      * ��ѯ������Դ�ļ�	
      * @param supplier ������
      * @param currPage ��ǰҳ�� 
      * @param pageSize ҳ���С
      * @return
      */
	 public QueryPage searchSupplierFileSourceListWithPage(SupplierFileSource supplierFileSource, int currPage, int pageSize);
	 
	 /**
	  * ��ѯԴ�ļ�ID
	  * @param id
	  * @return
	  */
	 public SupplierFileSource getSupplierFileSourceById(Long id);
	 
	 
	 public void updateSupplierFileSourceByNotNull(SupplierFileSource supplierFileSource);
	 
	 
	 
	 //Excel�ļ�����
	 
	 
	 //�����ϴ���Excel�ļ�
	 public Long insertSupplierFile(SupplierFile supplierFile);
	 
	 
	 
	 /**
	  * ��excel���ݵ���supplierGoods��
	  * @param path ·��
	  * @return
	  */
	
	 public Result exportExcel2SupplierGoods(String path,Long supplerId,Long fileId);
	 
	 
	 /**
	  * ��excel���ݵ���supplierGoods��
	  * @param is ������
	  * @return
	  */
	 public Result exportExcel2SupplierGoods(InputStream is,Long supplerId,Long fileId);
}
 
