/**
 * 
 */
package com.huaixuan.network.biz.domain.provider;

/**
 * @author TT
 * 
 */
public class ProvideGoodsYShang {

	private Long id;
	private String skuId; //云尚的sku
	private String ourSku; //我们的sku
	private String spuId; //云尚的spu
	
	private Integer stock; // 库存
	private String color; //颜色	
	private String size; //尺寸
	private String model; //型号
	
	private String productName; //商品标题	
	private String brandChname; //品牌名称	
	private String brandId; // 品牌 ID
	private String categoryId; //类别 ID
	
	private String categoryName;//类别名称
	private Double marketPrice; //市场价
	private Double settlePrice;//结算价价格
	private Double ourPrice; //我们的销售价
	
	private Integer source; //商品来源：2 联营
	private Integer areaType; //商品区域 0-大陆 1-香港
	private Integer sellStatus; //销售状态 1-可销售 2-不可销售
	private Integer isExchange; //是否支持退换货 ０－不支持 １－支持
	
	private String mainImg; //商品主图
	private String level; //商品等级 N-全新 AB-二手未拆封
	private String refProductId; //相关联的 skuId
	private String stockChangeTime; //时间段内库存变化，改变当前库存的时间 
	
	private String costChangeTime; //时间段内成本价格变化的时间
	private String insertTime; //商品插入的时间
	public String updateTime;
	
	private Integer stockStr; //用于查询大于库存的
	private Double salePriceStart;//售价
	private String ids;
	private Double salePriceEnd;//售价
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getOurSku() {
		return ourSku;
	}
	public void setOurSku(String ourSku) {
		this.ourSku = ourSku;
	}
	public String getSpuId() {
		return spuId;
	}
	public void setSpuId(String spuId) {
		this.spuId = spuId;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandChname() {
		return brandChname;
	}
	public void setBrandChname(String brandChname) {
		this.brandChname = brandChname;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getSettlePrice() {
		return settlePrice;
	}
	public void setSettlePrice(Double settlePrice) {
		this.settlePrice = settlePrice;
	}
	public Double getOurPrice() {
		return ourPrice;
	}
	public void setOurPrice(Double ourPrice) {
		this.ourPrice = ourPrice;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getAreaType() {
		return areaType;
	}
	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}
	public Integer getSellStatus() {
		return sellStatus;
	}
	public void setSellStatus(Integer sellStatus) {
		this.sellStatus = sellStatus;
	}
	public Integer getIsExchange() {
		return isExchange;
	}
	public void setIsExchange(Integer isExchange) {
		this.isExchange = isExchange;
	}
	public String getMainImg() {
		return mainImg;
	}
	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRefProductId() {
		return refProductId;
	}
	public void setRefProductId(String refProductId) {
		this.refProductId = refProductId;
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStockStr() {
		return stockStr;
	}
	public void setStockStr(Integer stockStr) {
		this.stockStr = stockStr;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
	
}
