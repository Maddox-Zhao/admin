package com.huaixuan.network.biz.domain.active;

import java.sql.Date;

public class MoveFrameLog
{
	private Long id;		//
	private Long moveframeId;		//�����
	private Long adminId;		//����Ա��id
	private String note;		//��������
	private String type;		//1-��½� 2-��޸� 3-��Ʒ��� 4- ɾ����Ʒ 5-
	private Long isdeal;		//�Ƿ�����ü�¼0.δ���� 1.�Ѿ�����
	private Date gmtCreate;		//����ʱ��
	private Date gmtModify;		//�޸�ʱ��
	private Integer area;//���� 1.���� 2.�ػ�
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getMoveframeId()
	{
		return moveframeId;
	}
	public void setMoveframeId(Long moveframeId)
	{
		this.moveframeId = moveframeId;
	}
	public Long getAdminId()
	{
		return adminId;
	}
	public void setAdminId(Long adminId)
	{
		this.adminId = adminId;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Long getIsdeal()
	{
		return isdeal;
	}
	public void setIsdeal(Long isdeal)
	{
		this.isdeal = isdeal;
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
	public Integer getArea()
	{
		return area;
	}
	public void setArea(Integer area)
	{
		this.area = area;
	}
	
	
	
	
}
