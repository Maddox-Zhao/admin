package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;
import java.util.Date;

import com.huaixuan.network.biz.domain.hx.Department;

public class Role implements Serializable {

	private Long           id;
    private String         name;
    private String         display;
    private Integer        status;
    private Date           gmtCreate;
    private Date           gmtModify;
    private String         depCode;
    private Department Department;
    private String type; //客户端还是web端的角色
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

	public String getDepCode()
	{
		return depCode;
	}

	public void setDepCode(String depCode)
	{
		this.depCode = depCode;
	}

	public Department getDepartment()
	{
		return Department;
	}

	public void setDepartment(Department department)
	{
		Department = department;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
    
}
