package com.huaixuan.network.biz.domain.supplier;

import java.util.Date;



/**
 * @author Mr_Yang   2015-9-8 ����06:14:31
 **/

public class SupplierFile
{
	private Long id;		//
	private Long sourceFile;		//������Դ�ļ�id
	private String path;		//�ļ������·��
	private String sourceName;		//�ϴ�����
	private String fileName;		//�ϴ�������
	private Long isDelete;		//״̬ �Ƿ�ɾ�� 0-δɾ�� 1-��ɾ��
	private Date gmtCreate;		//
	private Date gmtModify;		//
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getSourceFile()
	{
		return sourceFile;
	}
	public void setSourceFile(Long sourceFile)
	{
		this.sourceFile = sourceFile;
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

	
	

}
 
