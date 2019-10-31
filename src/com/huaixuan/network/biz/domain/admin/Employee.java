package com.huaixuan.network.biz.domain.admin;

import java.io.Serializable;

public class Employee implements Serializable{
     
	private int idEmployee;   //存入的是用户名
	private String name;     	//用户的真实姓名
	private String ID;   			//	
	private Long idSite;        //站点	
	private String phone;      //电话号码	
	private int grade;   
	private double salary;

	
	public Employee() {
		super();
	}
	public Employee(int idEmployee, String name, String iD, Long idSite,
			String phone, int grade, double salary) {
		super();
		this.idEmployee = idEmployee;
		this.name = name;
		ID = iD;
		this.idSite = idSite;
		this.phone = phone;
		this.grade = grade;
		this.salary = salary;
	}
	public int getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Long getIdSite() {
		return idSite;
	}
	public void setIdSite(Long idSite) {
		this.idSite = idSite;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	} 
	
}
