package com.huaixuan.network.biz.domain.customer;



/**
 * @author Mr_Yang   2015-12-8 上午11:50:23
 **/

public class Customer
{
	private Long idCustomer;
	private String name;
	private Integer type;
	private String vipNum;
	private String integral;
	private String phone;
	private String address;
	private String email;
	private String password;
	private String remark;
	private String province;//省份ID
	private String provinceName; //省份
	private String manager; //客户经理
	private String managerId; //客户经理ID
 
	
	public Long getIdCustomer()
	{
		return idCustomer;
	}
	public void setIdCustomer(Long idCustomer)
	{
		this.idCustomer = idCustomer;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public String getVipNum()
	{
		return vipNum;
	}
	public void setVipNum(String vipNum)
	{
		this.vipNum = vipNum;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	public String getProvince()
	{
		return province;
	}
	public void setProvince(String province)
	{
		this.province = province;
	}
	public String getManager()
	{
		return manager;
	}
	public void setManager(String manager)
	{
		this.manager = manager;
	}
	public String getManagerId()
	{
		return managerId;
	}
	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}
	public String getProvinceName()
	{
		return provinceName;
	}
	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
	
	
}
 
