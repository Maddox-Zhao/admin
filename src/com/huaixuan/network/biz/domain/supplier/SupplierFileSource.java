package com.huaixuan.network.biz.domain.supplier;

import java.util.Date;



/**
 * @author Mr_Yang   2015-9-7 下午12:00:27
 **/

public class SupplierFileSource 
{
	private Long id;		//
	private Long idSupplier;		//供货id
	private String path;		//文件保存的路径
	private String sourceName;		//上传名称
	private String fileName;		//上传后名称
	private Long isDeal;		//状态 是否处理 0-未删除 1-已删除
	private Long isDelete;		//状态 是否删除 0-未删除 1-已删除
	private Date gmtCreate;		//
	private Date gmtModify;		//
	private String supplierName; //供货商名称
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


 
