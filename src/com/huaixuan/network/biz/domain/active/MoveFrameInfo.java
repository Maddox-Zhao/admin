package com.huaixuan.network.biz.domain.active;

import java.util.Date;

/**
 *@author Mr_Yang
 *@version ����ʱ�䣺2012-4-26 ����02:31:59 <br/>
 *���ڴ������޸Ļ���ʱ�����ʼ�����Ӧ��Ա��
 */
public class MoveFrameInfo
{
	private Long moveFrameId; //���id
	private  String moveFrameName; //������� 
	private String author;//��򴴽���
	private  String depName; //�����������
	private Date gmtStart;//���ʼʱ��
	private Date gmtEnd;//�����ʱ��
	private  String email;//�������Ա����Ӧ��Email
	private String customerName; //�ͻ�����
	private String note; //�޸ĵ���Ϣ
	private String customerEmail; //�ͻ��ʼ�
	private  String userRank; //�ͻ��ȼ�
	private Integer area; //����1.���� 2.�ػ�
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
