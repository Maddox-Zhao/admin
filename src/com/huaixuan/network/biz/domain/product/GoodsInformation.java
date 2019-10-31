package com.huaixuan.network.biz.domain.product;

import java.util.Date;

public class GoodsInformation {
	private Integer gId;
	private Integer id;
	private String idproduct;
	private String beforeLocation;
	private String idStatus;
	private String name;
	
	
	private String siteName;
	private String brandName;// 品牌
	private String seriesName; // /品类
	private String color;
	private String material;
	private String type;
	private String size;
	private Double dlPrice;// 大陆价
	private Double smPrice;// 尚美价
	private String curSiteId; // 当前仓库id
	private String status;// 当前状态
	private String sellChannel;// 销售渠道
	private String customerId;// 客户ID
	private String remark;//备注
	private String cname;//客户名称
	
	public String getBeforeLocation() {
		return beforeLocation;
	}
	public void setBeforeLocation(String beforeLocation) {
		this.beforeLocation = beforeLocation;
	}
	
	public Integer getgId() {
		return gId;
	}
	public void setgId(Integer gId) {
		this.gId = gId;
	}
	public String getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(String idproduct) {
		this.idproduct = idproduct;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Double getDlPrice() {
		return dlPrice;
	}
	public void setDlPrice(Double dlPrice) {
		this.dlPrice = dlPrice;
	}
	public Double getSmPrice() {
		return smPrice;
	}
	public void setSmPrice(Double smPrice) {
		this.smPrice = smPrice;
	}
	public String getCurSiteId() {
		return curSiteId;
	}
	public void setCurSiteId(String curSiteId) {
		this.curSiteId = curSiteId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSellChannel() {
		return sellChannel;
	}
	public void setSellChannel(String sellChannel) {
		this.sellChannel = sellChannel;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
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
