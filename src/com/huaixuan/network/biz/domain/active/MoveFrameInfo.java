package com.huaixuan.network.biz.domain.active;

import java.util.Date;

/**
 *@author Mr_Yang
 *@version 创建时间：2012-4-26 下午02:31:59 <br/>
 *用于创建和修改活动框的时候发送邮件给对应的员工
 */
public class MoveFrameInfo
{
	private Long moveFrameId; //活动框id
	private  String moveFrameName; //活动框名字 
	private String author;//活动框创建人
	private  String depName; //活动框所属部门
	private Date gmtStart;//活动开始时间
	private Date gmtEnd;//活动结束时间
	private  String email;//活动框所属员工对应的Email
	private String customerName; //客户姓名
	private String note; //修改的信息
	private String customerEmail; //客户邮件
	private  String userRank; //客户等级
	private Integer area; //区域1.代销 2.特惠
	public Long getMoveFrameId()
	{
		return moveFrameId;
	}
	public void setMoveFrameId(Long moveFrameId)
	{
		this.moveFrameId = moveFrameId;
	}
	public String getMoveFrameName()
	{
		return moveFrameName;
	}
	public void setMoveFrameName(String moveFrameName)
	{
		this.moveFrameName = moveFrameName;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getDepName()
	{
		return depName;
	}
	public void setDepName(String depName)
	{
		this.depName = depName;
	}
	public Date getGmtStart()
	{
		return gmtStart;
	}
	public void setGmtStart(Date gmtStart)
	{
		this.gmtStart = gmtStart;
	}
	public Date getGmtEnd()
	{
		return gmtEnd;
	}
	public void setGmtEnd(Date gmtEnd)
	{
		this.gmtEnd = gmtEnd;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getCustomerName()
	{
		return customerName;
	}
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getCustomerEmail()
	{
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail)
	{
		this.customerEmail = customerEmail;
	}
	
	public String getUserRank()
	{
		return userRank;
	}
	public void setUserRank(String userRank)
	{
		this.userRank = userRank;
	}
	
	public Integer getArea()
	{
		return area;
	}
	public void setArea(Integer area)
	{
		this.area = area;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoveFrameInfo other = (MoveFrameInfo) obj;
		if (note == null)
		{
			if (other.note != null)
				return false;
		}
		else if (!note.equals(other.note))
			return false;
		return true;
	}

	
		
}
