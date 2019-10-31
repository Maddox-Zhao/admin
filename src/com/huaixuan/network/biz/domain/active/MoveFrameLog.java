package com.huaixuan.network.biz.domain.active;

import java.sql.Date;

public class MoveFrameLog
{
	private Long id;		//
	private Long moveframeId;		//活动框编号
	private Long adminId;		//操作员工id
	private String note;		//操作内容
	private String type;		//1-活动新建 2-活动修改 3-产品添加 4- 删除产品 5-
	private Long isdeal;		//是否处理过该记录0.未处理 1.已经处理
	private Date gmtCreate;		//创建时间
	private Date gmtModify;		//修改时间
	private Integer area;//区域 1.代销 2.特惠
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
