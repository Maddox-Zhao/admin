/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideGoodsXiYou {

	private Long id;
	
	private String ids;
	
	private Long prodId; //银泰商品唯一标识
	
	private String ssProdid; //hx_stock_update的sku,由字母XY和prodid和随机数组成
	
	private String brandName; //品牌名
	 
	private String prodName;  //商品名
	
	private Double taxRate;  //税率
	
	private int providerId; //供应商ID
	
	private String providerName;
	
	private Double price; //售价
	
	private Double salePriceStart;//促销价
	
	private Double salePriceEnd;//促销价
	
	private Double cost; //成本价
	
	private Double marketPrice; //市场价
	
	private Double ourSalePrice; //我们
	
	private String size;  //尺码
	
	private String color; //颜色
	
    private String provideSkuNumbr; //barcode,供应商的sku
	
	private String detailDescription; //商品分类树
	
	private String imageAddresses; //获取主图的地址
	private String stockChangeTime; //时间段内库存变化，改变当前库存的时间 
	private String costChangeTime; //时间段内成本价格变化的时间
	private String insertTime; //商品插入的时间
	public String updateTime;
	private Integer stock;
	
	private Integer stockStr; //用于查询大于库存的
	
	
	
	public Integer getStock() {
		return stock;
	}

	public Double getSalePriceStart() {
		return salePriceStart;
	}

	public void setSalePriceStart(Double salePriceStart) {
		this.salePriceStart = salePriceStart;
	}

	public Double getSalePriceEnd() {
		return salePriceEnd;
	}

	public void setSalePriceEnd(Double salePriceEnd) {
		this.salePriceEnd = salePriceEnd;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getSsProdid() {
		return ssProdid;
	}

	public void setSsProdid(String ssProdid) {
		this.ssProdid = ssProdid;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	

	public String getDetailDescription() {
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getImageAddresses() {
		return imageAddresses;
	}

	public void setImageAddresses(String imageAddresses) {
		this.imageAddresses = imageAddresses;
	}

	public String getProvideSkuNumbr() {
		return provideSkuNumbr;
	}

	public String getStockChangeTime() {
		return stockChangeTime;
	}

	public void setStockChangeTime(String stockChangeTime) {
		this.stockChangeTime = stockChangeTime;
	}

	
	public String getCostChangeTime() {
		return costChangeTime;
	}

	public void setCostChangeTime(String costChangeTime) {
		this.costChangeTime = costChangeTime;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public void setProvideSkuNumbr(String provideSkuNumbr) {
		this.provideSkuNumbr = provideSkuNumbr;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Double getOurSalePrice() {
		return ourSalePrice;
	}

	public void setOurSalePrice(Double ourSalePrice) {
		this.ourSalePrice = ourSalePrice;
	}

	public Integer getStockStr() {
		return stockStr;
	}

	public void setStockStr(Integer stockStr) {
		this.stockStr = stockStr;
	}
	
	
	
}
