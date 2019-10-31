package com.huaixuan.network.biz.domain.express.query;

import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 物流信息查询类
 * @version 3.2.0
 */
public class ExpressDistQuery extends BaseObject{

    private static final long serialVersionUID = 555304194480854099L;

    private String            optTimeStart; //修改开始时间
    private String            optTimeEnd; //修改结束时间

    private String            provinceStart; //起始省
    private String            cityStart; //起始城市
    private String            districtStart; //起始县区

    private String            provinceEnd; //到达省
    private String            cityEnd; //到达城市
    private String            districtEnd; //到达县区

    private String            expressId; //物流公司
    private String            payment; //支付方式
    private String            status; //状态

    private String            gmtOutDepStart;//统计开始时间
    private String            gmtOutDepEnd;//统计结束时间
    private String            regionCode;//省份编码

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
