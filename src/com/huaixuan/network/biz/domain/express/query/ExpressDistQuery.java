package com.huaixuan.network.biz.domain.express.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ������Ϣ��ѯ��
 * @version 3.2.0
 */
public class ExpressDistQuery extends BaseObject{

    private static final long serialVersionUID = 555304194480854099L;

    private String            optTimeStart; //�޸Ŀ�ʼʱ��
    private String            optTimeEnd; //�޸Ľ���ʱ��

    private String            provinceStart; //��ʼʡ
    private String            cityStart; //��ʼ����
    private String            districtStart; //��ʼ����

    private String            provinceEnd; //����ʡ
    private String            cityEnd; //�������
    private String            districtEnd; //��������

    private String            expressId; //������˾
    private String            payment; //֧����ʽ
    private String            status; //״̬

    private String            gmtOutDepStart;//ͳ�ƿ�ʼʱ��
    private String            gmtOutDepEnd;//ͳ�ƽ���ʱ��
    private String            regionCode;//ʡ�ݱ���

    private List<String>            regionCodeStartList;
    private List<String>             regionCodeEndList;


	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getGmtOutDepStart() {
		return gmtOutDepStart;
	}
	public void setGmtOutDepStart(String gmtOutDepStart) {
		this.gmtOutDepStart = gmtOutDepStart;
	}
	public String getGmtOutDepEnd() {
		return gmtOutDepEnd;
	}
	public void setGmtOutDepEnd(String gmtOutDepEnd) {
		this.gmtOutDepEnd = gmtOutDepEnd;
	}
	public String getOptTimeStart() {
		return optTimeStart;
	}
	public void setOptTimeStart(String optTimeStart) {
		this.optTimeStart = optTimeStart;
	}
	public String getOptTimeEnd() {
		return optTimeEnd;
	}
	public void setOptTimeEnd(String optTimeEnd) {
		this.optTimeEnd = optTimeEnd;
	}
	public String getProvinceStart() {
		return provinceStart;
	}
	public void setProvinceStart(String provinceStart) {
		this.provinceStart = provinceStart;
	}
	public String getCityStart() {
		return cityStart;
	}
	public void setCityStart(String cityStart) {
		this.cityStart = cityStart;
	}
	public String getDistrictStart() {
		return districtStart;
	}
	public void setDistrictStart(String districtStart) {
		this.districtStart = districtStart;
	}
	public String getProvinceEnd() {
		return provinceEnd;
	}
	public void setProvinceEnd(String provinceEnd) {
		this.provinceEnd = provinceEnd;
	}
	public String getCityEnd() {
		return cityEnd;
	}
	public void setCityEnd(String cityEnd) {
		this.cityEnd = cityEnd;
	}
	public String getDistrictEnd() {
		return districtEnd;
	}
	public void setDistrictEnd(String districtEnd) {
		this.districtEnd = districtEnd;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getRegionCodeStartList() {
		return regionCodeStartList;
	}
	public void setRegionCodeStartList(List<String> regionCodeStartList) {
		this.regionCodeStartList = regionCodeStartList;
	}
	public List<String> getRegionCodeEndList() {
		return regionCodeEndList;
	}
	public void setRegionCodeEndList(List<String> regionCodeEndList) {
		this.regionCodeEndList = regionCodeEndList;
	}

}
