package com.huaixuan.network.biz.domain.product;

import java.util.Date;

/**
 * @author Mr_Yang 2015-11-20 下午12:15:51 产品类 型号 材质 颜色 等等
 **/

public class Product {
	private Double exchangeRate; // 汇率
	private Double taxesReate; // 税率
	private String idProduct; // ID
	private String brandName;// 品牌
	private String brandID; // 品牌id
	private String seriesName; // /品类
	private String seriesId;// 品类ID
	private String type;
	private String material;
	private String color;
	private String size;
	private Double dlPrice;// 大陆价
	private Double euPrice;// 欧洲价
	private Double dxPrice;// 代销价
	private Double smPrice;// 尚美价
	private Double ssPrice; // 尚上价
	private Double cost;// 成本
	private Integer idCostCurrency;// 成本价币种
	private String curSiteName;// 当前仓库位置
	private String curSiteId; // 当前仓库id
	private String status;// 当前状态
	private String statusID;// 当前状态ID
	private Double salePrice;// 售价
	private Integer salePriceCurrency;// 售价币种
	private Date instock;// 入库时间
	private Date sellDate;// 销售时间
	private Integer sellIdOrder;// 销售订单ID
	private String sellChannel;// 销售渠道
	private Integer sellChannelID;// 销售渠道ID
	private String customerName;// 客户
	private String customerId;// 客户ID
	private String sku;
	private String picture;//商品图片
	private String name;// 名称
	private String materialdes;// 材质描述
	private String colordes;// 颜色描述
	private String sizedes;// 长*宽*高
	private String uuid;
	private String auuid;
	private String remark;// 备注
	   private String origin;// 产地
	private Integer secondHand;// 类别
	private Integer hasValidCard;// 真品卡
	private String currencyName;
	private String salePriceCurrencyName; //销售币种
	private String targetCustomers;// 客户群体
	private Long idSupply; //供货商ID
	private Long goodsId;
	private Long idPurchase;//采购ID
	private String isDisplay;//是否显示
	private String idLocation;//当前站点
	private String idLastLocation;//前站点
	private String idOrder;//销售订单ID
	private String idOrder2;//退货订单ID
	private String idLastOperator;//最后操作人
	private String isFlaw;//是否瑕疵
	private Long idLifeCycle; 
	private String managerUserName;//客户经理
	
	private String years; //年份
	private String month;  //季节
	private Double activePrice;//活动价
	
	private String securityTC;      //安全技术类别
	private String implementationS; //执行标准
	
	
	
	private String back; //勿删
    
	
	private Long idBrand;      
	private Long idSeries;
	private String idCustomer;
	
	
	
	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandID() {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public Double getEuPrice() {
		return euPrice;
	}

	public void setEuPrice(Double euPrice) {
		this.euPrice = euPrice;
	}

	public Double getDxPrice() {
		return dxPrice;
	}

	public void setDxPrice(Double dxPrice) {
		this.dxPrice = dxPrice;
	}

	public Double getSmPrice() {
		return smPrice;
	}

	public void setSmPrice(Double smPrice) {
		this.smPrice = smPrice;
	}

	public Double getSsPrice() {
		return ssPrice;
	}

	public void setSsPrice(Double ssPrice) {
		this.ssPrice = ssPrice;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getIdCostCurrency() {
		return idCostCurrency;
	}

	public void setIdCostCurrency(Integer idCostCurrency) {
		this.idCostCurrency = idCostCurrency;
	}

	public String getCurSiteName() {
		return curSiteName;
	}

	public void setCurSiteName(String curSiteName) {
		this.curSiteName = curSiteName;
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

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getSalePriceCurrency() {
		return salePriceCurrency;
	}

	public void setSalePriceCurrency(Integer salePriceCurrency) {
		this.salePriceCurrency = salePriceCurrency;
	}

	public Date getInstock() {
		return instock;
	}

	public void setInstock(Date instock) {
		this.instock = instock;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public Integer getSellIdOrder() {
		return sellIdOrder;
	}

	public void setSellIdOrder(Integer sellIdOrder) {
		this.sellIdOrder = sellIdOrder;
	}

	public String getSellChannel() {
		return sellChannel;
	}

	public void setSellChannel(String sellChannel) {
		this.sellChannel = sellChannel;
	}

	public Integer getSellChannelID() {
		return sellChannelID;
	}

	public void setSellChannelID(Integer sellChannelID) {
		this.sellChannelID = sellChannelID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterialdes() {
		return materialdes;
	}

	public void setMaterialdes(String materialdes) {
		this.materialdes = materialdes;
	}

	public String getColordes() {
		return colordes;
	}

	public void setColordes(String colordes) {
		this.colordes = colordes;
	}

	public String getSizedes() {
		return sizedes;
	}

	public void setSizedes(String sizedes) {
		this.sizedes = sizedes;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getSecondHand() {
		return secondHand;
	}

	public void setSecondHand(Integer secondHand) {
		this.secondHand = secondHand;
	}

	public Integer getHasValidCard() {
		return hasValidCard;
	}

	public void setHasValidCard(Integer hasValidCard) {
		this.hasValidCard = hasValidCard;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getTargetCustomers() {
		return targetCustomers;
	}

	public void setTargetCustomers(String targetCustomers) {
		this.targetCustomers = targetCustomers;
	}

	public String getAuuid()
	{
		return auuid;
	}

	public void setAuuid(String auuid)
	{
		this.auuid = auuid;
	}

	public Long getIdSupply()
	{
		return idSupply;
	}

	public void setIdSupply(Long idSupply)
	{
		this.idSupply = idSupply;
	}

	public Long getGoodsId()
	{
		return goodsId;
	}

	public void setGoodsId(Long goodsId)
	{
		this.goodsId = goodsId;
	}

	public String getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	 

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public Long getIdPurchase()
	{
		return idPurchase;
	}

	public void setIdPurchase(Long idPurchase)
	{
		this.idPurchase = idPurchase;
	}

	public String getIsDisplay()
	{
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay)
	{
		this.isDisplay = isDisplay;
	}

	public String getIdLocation()
	{
		return idLocation;
	}

	public void setIdLocation(String idLocation)
	{
		this.idLocation = idLocation;
	}

	public String getIdLastLocation()
	{
		return idLastLocation;
	}

	public void setIdLastLocation(String idLastLocation)
	{
		this.idLastLocation = idLastLocation;
	}

	public String getIdOrder()
	{
		return idOrder;
	}

	public void setIdOrder(String idOrder)
	{
		this.idOrder = idOrder;
	}

	public String getIdOrder2()
	{
		return idOrder2;
	}

	public void setIdOrder2(String idOrder2)
	{
		this.idOrder2 = idOrder2;
	}

	public String getIdLastOperator()
	{
		return idLastOperator;
	}

	public void setIdLastOperator(String idLastOperator)
	{
		this.idLastOperator = idLastOperator;
	}

	public String getIsFlaw()
	{
		return isFlaw;
	}

	public void setIsFlaw(String isFlaw)
	{
		this.isFlaw = isFlaw;
	}

	public String getBack()
	{
		return back;
	}

	public void setBack(String back)
	{
		this.back = back;
	}

	public Long getIdLifeCycle()
	{
		return idLifeCycle;
	}

	public void setIdLifeCycle(Long idLifeCycle)
	{
		this.idLifeCycle = idLifeCycle;
	}

	public String getSalePriceCurrencyName()
	{
		return salePriceCurrencyName;
	}

	public void setSalePriceCurrencyName(String salePriceCurrencyName)
	{
		this.salePriceCurrencyName = salePriceCurrencyName;
	}

	public String getYears()
	{
		return years;
	}

	public void setYears(String years)
	{
		this.years = years;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public Double getActivePrice()
	{
		return activePrice;
	}

	public void setActivePrice(Double activePrice)
	{
		this.activePrice = activePrice;
	}

	public Double getTaxesReate()
	{
		return taxesReate;
	}

	public void setTaxesReate(Double taxesReate)
	{
		this.taxesReate = taxesReate;
	}

	public String getManagerUserName()
	{
		return managerUserName;
	}

	public void setManagerUserName(String managerUserName)
	{
		this.managerUserName = managerUserName;
	}

	public String getSecurityTC() {
		return securityTC;
	}

	public void setSecurityTC(String securityTC) {
		this.securityTC = securityTC;
	}

	public String getImplementationS() {
		return implementationS;
	}

	public void setImplementationS(String implementationS) {
		this.implementationS = implementationS;
	}

	public Long getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}

	

	
	
	
 
}
