package com.huaixuan.network.biz.domain.supplier;

import java.util.Date;



/**
 * @author Mr_Yang   2015-9-7 ����12:00:27
 **/

public class SupplierFileSource 
{
	private Long id;		//
	private Long idSupplier;		//����id
	private String path;		//�ļ������·��
	private String sourceName;		//�ϴ�����
	private String fileName;		//�ϴ�������
	private Long isDeal;		//״̬ �Ƿ��� 0-δɾ�� 1-��ɾ��
	private Long isDelete;		//״̬ �Ƿ�ɾ�� 0-δɾ�� 1-��ɾ��
	private Date gmtCreate;		//
	private Date gmtModify;		//
	private String supplierName; //����������
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getIdSupplier()
	{
		return idSupplier;
	}
	public void setIdSupplier(Long idSupplier)
	{
		this.idSupplier = idSupplier;
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	public String getSourceName()
	{
		return sourceName;
	}
	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public Long getIsDeal()
	{
		return isDeal;
	}
	public void setIsDeal(Long isDeal)
	{
		this.isDeal = isDeal;
	}
	public Long getIsDelete()
	{
		return isDelete;
	}
	public void setIsDelete(Long isDelete)
	{
		this.isDelete = isDelete;
	}
	public Date getGmtCreate()
	{
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate)
	{
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModify()
	{
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify)
	{
		this.gmtModify = gmtModify;
	}
	public String getSupplierName()
	{
		return supplierName;
	}
	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}
	
	
	
}


 
