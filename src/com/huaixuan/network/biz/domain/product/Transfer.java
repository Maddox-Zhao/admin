package com.huaixuan.network.biz.domain.product;

public class Transfer {
	private Integer id; //编号  （自增）
	private String createUser;//修改人
	private String changeTime;//创建时间
	private Integer afterLocation;//修改后站点
	private Integer beforelocation;//原站点
	private Integer idStatus;//状态
	private String idCustomer;//客户iD
	private String remark;//备注
	private String cname;//客户名称

	
	
	private String siteName;//现站点名
	private String beforesiteName;//原站点名
	private String reallName;//真实姓名
	private String createDate;//创建日期
	private String statu;//状态
	
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getReallName() {
		return reallName;
	}
	public void setReallName(String reallName) {
		this.reallName = reallName;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}
	public Integer getAfterLocation() {
		return afterLocation;
	}
	public void setAfterLocation(Integer afterLocation) {
		this.afterLocation = afterLocation;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getBeforelocation() {
		return beforelocation;
	}
	public void setBeforelocation(Integer beforelocation) {
		this.beforelocation = beforelocation;
	}
	public String getBeforesiteName() {
		return beforesiteName;
	}
	public void setBeforesiteName(String beforesiteName) {
		this.beforesiteName = beforesiteName;
	}
	public Integer getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
	
	
}
