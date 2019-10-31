package com.huaixuan.network.biz.domain.storage;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class DepositoryFirst extends BaseObject {

	/**
     *
     */
	private static final long serialVersionUID = 7798194722660517657L;

	private long id;

	private Date gmtCreate;

	private Date gmtModify;

	private String depFirstName;

	private String regionCode;

	private String address;

	private String particularAddress;// ��ϸ��ַ

	private String type;// n:��ͨ�ֿ⣨Ĭ�ϣ���w�����������

	private String isStocked;// n:������ y:����

	// -------------------- ������ -----------------
	private String province;
	private String city;
	private String district;

	public String getIsStocked() {
		return isStocked;
	}

	public void setIsStocked(String isStocked) {
		this.isStocked = isStocked;
	}

	/**
	 * �Բֿ����ͽ����жϣ�n:��ͨ�ֿ⣨Ĭ�ϣ���w�����������
	 * 
	 * @return
	 */
	public String getType() {
		return type;

	}

	public void setType(String type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDepFirstName() {
		return depFirstName;
	}

	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParticularAddress() {
		return particularAddress;
	}

	public void setParticularAddress(String particularAddress) {
		this.particularAddress = particularAddress;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
