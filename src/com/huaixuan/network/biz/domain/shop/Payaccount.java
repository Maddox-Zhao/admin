package com.huaixuan.network.biz.domain.shop;

public class Payaccount {

/**
* @author zhao 2017-01-16 早上10:04:51 获取银行信息
**/
	private int idPayAccount;
	private String name;
	private String number;
	private int idCurrency;
	
	public int getIdPayAccount() {
		return idPayAccount;
	}
	public void setIdPayAccount(int idPayAccount) {
		this.idPayAccount = idPayAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getIdCurrency() {
		return idCurrency;
	}
	public void setIdCurrency(int idCurrency) {
		this.idCurrency = idCurrency;
	}
}
