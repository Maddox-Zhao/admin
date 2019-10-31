package com.huaixuan.network.biz.domain.hx;


/**
 * 2012-02-23 13:04
 * @author Mr_Yang
 *
 */

public class Department
{
	private int id;
	private String depCode; //部门编号,用于区别部门的上下级关系比如 001下面有001.001 001.002 001.003
	private String name; //部门名称
	private String parentCode; //上级部门编号
	private String status; 
	private int staffNum; //部门员工数
	private String note;
	private String roleIds; //部门设置的职务id，多个职务id用 ‘;’号相隔
	private String roleNames;
	private int childNums; //直接下级部门数量，用于设置部门的编号
	private String gmtCreate;
	private String gmtModify;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getDepCode()
	{
		return depCode;
	}
	public void setDepCode(String depCode)
	{
		this.depCode = depCode;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getParentCode()
	{
		return parentCode;
	}
	public void setParentCode(String parentCode)
	{
		this.parentCode = parentCode;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public int getStaffNum()
	{
		return staffNum;
	}
	public void setStaffNum(int staffNum)
	{
		this.staffNum = staffNum;
	}
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	public String getRoleIds()
	{
		return roleIds;
	}
	public void setRoleIds(String roleIds)
	{
		this.roleIds = roleIds;
	}
	
	
	public String getRoleNames()
	{
		return roleNames;
	}
	public void setRoleNames(String roleNames)
	{
		this.roleNames = roleNames;
	}
	public int getChildNums()
	{
		return childNums;
	}
	public void setChildNums(int childNums)
	{
		this.childNums = childNums;
	}
	public String getGmtCreate()
	{
		return gmtCreate;
	}
	public void setGmtCreate(String gmtCreate)
	{
		this.gmtCreate = gmtCreate;
	}
	public String getGmtModify()
	{
		return gmtModify;
	}
	public void setGmtModify(String gmtModify)
	{
		this.gmtModify = gmtModify;
	}
	
	
	
}
