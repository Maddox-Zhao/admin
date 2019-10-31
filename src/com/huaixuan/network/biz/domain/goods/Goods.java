package com.huaixuan.network.biz.domain.goods;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.shop.Brand;
import com.huaixuan.network.biz.domain.shop.Cabinet;
import com.huaixuan.network.biz.domain.shop.Series;
import com.hundsun.network.melody.common.util.StringUtil;

public class Goods extends BaseObject implements Cloneable {

	/**
	 * 
    */
	private static final long serialVersionUID = 8904923765449649062L;
	protected Log log = LogFactory.getLog(this.getClass());
	/* @property: */
	private Long id;
	/* @property: */
	private String catCode;
	/* add a new property for goods */
	private String catName;
	/* @property: */
	private String goodsSn;
	/* @property: */
	private String title;
	/* @property: */
	private Long brandId;
	
	private Brand brand;
	/* @property: */
	private int saleNumber;
	/* @property: */
	private Double marketPrice;  //大陆市场价格
	/* @property: */
	private Double goodsPrice; 	//大陆销售价格
	/* @property: */
	private Double goodsPriceStart; 	//大陆销售价格		
	/* @property: */
	private Double goodsPriceEnd; 	//大陆销售价格			
	/* @property: */
	private int goodsNumber;
	
	private int hkGoodsNumber;//香港可用库存
	
	private String goodsNumberStr;
	
	private String hkGoodsNumberStr;
	
	/* @property: */
	private String goodsDesc;
	/* @property: */
	private String imgLarge;
	/* @property: */
	private String imgMiddle;
	/* @property: */
	private String imgSmall;
	/* @property: */
	private String attrValue;
	/* @property: */
	private String attrDesc;
	/* @property: */
	private String goodsStatus;
	/* @property: */
	private Date gmtListing;
	/* @property: */
	private String gmtListingStart;		
	/* @property: */
	private String gmtListingEnd;		
	/* @property: */
	private Date gmtDelisting;
	/* @property: */
	private int clickCount;
	/* @property: */
	private Double goodsWeight;
	/* @property: */
	private int warnNumber;
	/* @property: */
	private String originalImg;
	/* @property: */
	private Date gmtCreate;
	/* @property: */
	private Date gmtModify;

	private String sortOrder;
	/* @property: */
	private double goodsRank;

	private String priceMin;

	private String priceMax;

	private Cabinet cabinet;

	/*
	<result column="account" property="account" />
	<result column="shoppingCartNumber" property="shoppingCartNumber" />
	<result column="isshgoods" property="isshgoods" />*/
	private String account;//账户名
	
	private String shoppingCartNumber;//购物车
	
	private String isshgoods;//国内国外
	
	private String gaattrValue;//尺寸 goodsPriceStart
	
	private Double salePriceStart;//促销价
	
	private Double salePriceEnd;//促销价
	
	
	private int hkthNumber; //三月内的销售数量
	
	
	private Date sellDateStart;
	
	private Date sellDateEnd;
	
	private String oneList;  //本质是catCode
	private String oneListName;//本质是catName
	private String twoList;
	private String twoListName;
	private String threeList;
	private String threeListName;
	
	private String goodsWeightStr;  //重量
	private String securityTC;      //安全技术类别
	private String materialdes;// 材质描述
	private String colordes;// 颜色描述
	private String origin;// 产地
	private String implementationS; //执行标准

	
	
	
	public String getOneListName() {
		return oneListName;
	}

	public void setOneListName(String oneListName) {
		this.oneListName = oneListName;
	}

	public String getTwoListName() {
		return twoListName;
	}

	public void setTwoListName(String twoListName) {
		this.twoListName = twoListName;
	}

	public String getThreeListName() {
		return threeListName;
	}

	public void setThreeListName(String threeListName) {
		this.threeListName = threeListName;
	}

	public String getOneList() {
		return oneList;
	}

	public void setOneList(String oneList) {
		this.oneList = oneList;
	}

	public String getTwoList() {
		return twoList;
	}

	public void setTwoList(String twoList) {
		this.twoList = twoList;
	}

	public String getThreeList() {
		return threeList;
	}

	public void setThreeList(String threeList) {
		this.threeList = threeList;
	}

	public Date getSellDateStart() {
		return sellDateStart;
	}

	public void setSellDateStart(Date sellDateStart) {
		this.sellDateStart = sellDateStart;
	}

	public Date getSellDateEnd() {
		return sellDateEnd;
	}

	public void setSellDateEnd(Date sellDateEnd) {
		this.sellDateEnd = sellDateEnd;
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

	public String getGaattrValue() {
		return gaattrValue;
	}

	public void setGaattrValue(String gaattrValue) {
		this.gaattrValue = gaattrValue;
	}
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getShoppingCartNumber() {
		return shoppingCartNumber;
	}

	public void setShoppingCartNumber(String shoppingCartNumber) {
		this.shoppingCartNumber = shoppingCartNumber;
	}

	public String getIsshgoods() {
		return isshgoods;
	}

	public void setIsshgoods(String isshgoods) {
		this.isshgoods = isshgoods;
	}

	public Double getGoodsPriceStart() {
		return goodsPriceStart;
	}

	public void setGoodsPriceStart(Double goodsPriceStart) {
		this.goodsPriceStart = goodsPriceStart;
	}

	public Double getGoodsPriceEnd() {
		return goodsPriceEnd;
	}

	public void setGoodsPriceEnd(Double goodsPriceEnd) {
		this.goodsPriceEnd = goodsPriceEnd;
	}

	public String getIsGmtCutprice() {
		return isGmtCutprice;
	}

	public void setIsGmtCutprice(String isGmtCutprice) {
		this.isGmtCutprice = isGmtCutprice;
	}

	private List titles;

	private String isCutprice;

	private Date gmtCutprice;
	
	private String isGmtCutprice;

	private String choose;

	private Double salesProPrice; //

	private double wholesalePrice; //

	private String goodsUnit;

	private Double agentPrice; // 代销价格

	private String isAgent; // 是否代销商品

	private String brandName; // 品牌

	private String pointRule; // 返点规则

	private List<AvailableStock> availableStockList; // 仓库库存

	private String gmtDesc; // 商品销售时间描述

	private String isWholesale; // 是否批发商品

	private long startNum; // 起批数量

	private double firstPrice; // 批发一级价格

	private List<GoodsWholsale> goodsWholsaleList; // 商品批发等级

	private double wholeAllPrice; // 批发商品总价格

	private String goodsItem; // 商品货号

	private String firstStart;

	private String secondStart;

	private String thirdStart;

	private String firstEnd;

	private String secondEnd;

	private String firstPrices;

	private String secondPrice;

	private String thirdPrice;

	private String isPaipai;

	private Long paipaiClassId;

	private String paipaiAttr;

	private String checkflag;

	private String viceName;

	private String isTaobao; // 是否关联淘宝

	private Long taobaoClassId; // 淘宝类目ID

	private String taobaoAttr; // 淘宝属性串

	private String taobaoSkuProp; // 淘宝销售属性串

	private String taobaoInputStr; // 淘宝输入属性串

	private String taobaoInputPids; // 淘宝输入属性ID串

	private String taobaoSkuPropName; // 淘宝销售属性名字串

	private double cabinetWholePrice; // 橱窗批发价格

	private String checkflagTaobao;

	private String expressIds; // 商品关联物流公司号
	
	private String noPicture;
	
	private String zeroStock;
	
	private String returnPointId;
	
	private String goodsKind;
	
	private String ids;	
	
	private String order;
	
	private Long billId;
	
	private Long idSeries;
	
	private Series series;
	
	private String seriesName;
	
	private String size;
	 
	private Long targetCustomers;
	 
	private String color;
	
	private String material;
	 
	private String type;
	
	private Long  siteId;
    
    private Double cost;
    
    private int idCostCurrency;
    
    private Double	hkhxPrice;//香港销售价格
    
    private Double	euPrice;//欧洲市场价格
    
    private Double	hkPrice;//香港市场价格
    
    private Long  productId;
    
    private String ishavegoodsnumber;
    
    private String selectIds;

	public String getSelectIds()
	{
		return selectIds;
	}

	public void setSelectIds(String selectIds)
	{
		this.selectIds = selectIds;
	}

	public String getIshavegoodsnumber()
	{
		return ishavegoodsnumber;
	}

	public void setIshavegoodsnumber(String ishavegoodsnumber)
	{
		this.ishavegoodsnumber = ishavegoodsnumber;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getGoodsKind() {
		return goodsKind;
	}

	public void setGoodsKind(String goodsKind) {
		this.goodsKind = goodsKind;
	}

	public String getReturnPointId() {
		return returnPointId;
	}

	public void setReturnPointId(String returnPointId) {
		this.returnPointId = returnPointId;
	}

	public String getZeroStock() {
		return zeroStock;
	}

	public void setZeroStock(String zeroStock) {
		this.zeroStock = zeroStock;
	}

	public String getNoPicture() {
		return noPicture;
	}

	public void setNoPicture(String noPicture) {
		this.noPicture = noPicture;
	}

	/**
	 * 获取代销价格和批发价格
	 *
	 * @return
	 */
	public String getAgentAndWholsalePrice() {
		StringBuffer sb = new StringBuffer();
		if ("y".equals(this.isAgent)
				&& StringUtil.isNotBlank(this.agentPrice + "")) {
			sb.append("代销价格：" + this.agentPrice).append("元 ");
		}
//		if (this.isWholesale.equals("y")) {
//			if (StringUtil.isNotBlank(this.firstPrices)) {
//				sb.append("一级批发价格：" + this.firstPrices).append("元 ");
//			}
//			if (StringUtil.isNotBlank(this.secondPrice)) {
//				sb.append("二级批发价格：" + this.secondPrice).append("元 ");
//			}
//			if (StringUtil.isNotBlank(this.thirdPrice)) {
//				sb.append("三级批发价格：" + this.thirdPrice).append("元 ");
//			}
//		}
		return sb.toString();
	}

	public String getExpressIds() {
		return expressIds;
	}

	public void setExpressIds(String expressIds) {
		this.expressIds = expressIds;
	}

	public double getCabinetWholePrice() {
		return cabinetWholePrice;
	}

	public void setCabinetWholePrice(double cabinetWholePrice) {
		this.cabinetWholePrice = cabinetWholePrice;
	}

	/* begin add by shenzh Oct 25, 2010 说明： 同步失败原因 */
	private String reason;

	/* end by shenzh Oct 25, 2010 */

	public String getTaobaoSkuPropName() {
		return taobaoSkuPropName;
	}

	public void setTaobaoSkuPropName(String taobaoSkuPropName) {
		this.taobaoSkuPropName = taobaoSkuPropName;
	}

	public String getTaobaoSkuProp() {
		return taobaoSkuProp;
	}

	public void setTaobaoSkuProp(String taobaoSkuProp) {
		this.taobaoSkuProp = taobaoSkuProp;
	}

	public String getViceName() {
		return viceName;
	}

	public void setViceName(String viceName) {
		this.viceName = viceName;
	}

	public String getCheckflag() {
		return checkflag;
	}

	public void setCheckflag(String checkflag) {
		this.checkflag = checkflag;
	}

	public String getPaipaiAttr() {
		return paipaiAttr;
	}

	public void setPaipaiAttr(String paipaiAttr) {
		this.paipaiAttr = paipaiAttr;
	}

	public Long getPaipaiClassId() {
		return paipaiClassId;
	}

	public void setPaipaiClassId(Long paipaiClassId) {
		this.paipaiClassId = paipaiClassId;
	}

	public String getIsPaipai() {
		return isPaipai;
	}

	public void setIsPaipai(String isPaipai) {
		this.isPaipai = isPaipai;
	}

	public String getFirstStart() {
		return firstStart;
	}

	public void setFirstStart(String firstStart) {
		this.firstStart = firstStart;
	}

	public String getSecondStart() {
		return secondStart;
	}

	public void setSecondStart(String secondStart) {
		this.secondStart = secondStart;
	}

	public String getThirdStart() {
		return thirdStart;
	}

	public void setThirdStart(String thirdStart) {
		this.thirdStart = thirdStart;
	}

	public String getFirstEnd() {
		return firstEnd;
	}

	public void setFirstEnd(String firstEnd) {
		this.firstEnd = firstEnd;
	}

	public String getSecondEnd() {
		return secondEnd;
	}

	public void setSecondEnd(String secondEnd) {
		this.secondEnd = secondEnd;
	}

	public String getFirstPrices() {
		return firstPrices;
	}

	public void setFirstPrices(String firstPrices) {
		this.firstPrices = firstPrices;
	}

	public String getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(String secondPrice) {
		this.secondPrice = secondPrice;
	}

	public String getThirdPrice() {
		return thirdPrice;
	}

	public void setThirdPrice(String thirdPrice) {
		this.thirdPrice = thirdPrice;
	}

	public String getGoodsItem() {
		return goodsItem;
	}

	public void setGoodsItem(String goodsItem) {
		this.goodsItem = goodsItem;
	}

	public double getWholeAllPrice() {
		return wholeAllPrice;
	}

	public void setWholeAllPrice(double wholeAllPrice) {
		this.wholeAllPrice = wholeAllPrice;
	}

	public List<GoodsWholsale> getGoodsWholsaleList() {
		return goodsWholsaleList;
	}

	public void setGoodsWholsaleList(List<GoodsWholsale> goodsWholsaleList) {
		this.goodsWholsaleList = goodsWholsaleList;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	public String getGmtDesc() {
		return gmtDesc;
	}

	public void setGmtDesc(String gmtDesc) {
		this.gmtDesc = gmtDesc;
	}

	public Goods clone() {
		Goods o = null;
		try {
			o = (Goods) super.clone();
		} catch (CloneNotSupportedException e) {
			log.error(e.getMessage());
		}
		return o;
	}

	public String getDirectCategoryCode() {
		if (this.catCode == null) {
			return null;
		}
		int last = this.catCode.lastIndexOf('.');
		if (last == -1) {
			return this.catCode;
		}
		return this.catCode.substring(last + 1);
	}

	public Cabinet getCabinet() {
		return cabinet;
	}

	public List getTitles() {
		return titles;
	}

	public void setTitles(List titles) {
		this.titles = titles;
	}

	public void setCabinet(Cabinet cabinet) {
		this.cabinet = cabinet;
	}

	/* Default constructor - creates a new instance with no values set. */
	public Goods() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdString() {
		return Long.toString(this.id);
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGoodsSn(String obj) {
		this.goodsSn = obj;
	}

	/* @model:锟斤拷取 */
	public String getGoodsSn() {
		return this.goodsSn;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setTitle(String obj) {
		this.title = obj;
	}

	/* @model:锟斤拷取 */
	public String getTitle() {
		return this.title;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setSaleNumber(int obj) {
		this.saleNumber = obj;
	}

	/* @model:锟斤拷取 */
	public int getSaleNumber() {
		return this.saleNumber;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGoodsNumber(int obj) {
		this.goodsNumber = obj;
	}

	/* @model:锟斤拷取 */
	public int getGoodsNumber() {
		return this.goodsNumber;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGoodsDesc(String obj) {
		this.goodsDesc = obj;
	}

	/* @model:锟斤拷取 */
	public String getGoodsDesc() {
		return this.goodsDesc;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setImgLarge(String obj) {
		this.imgLarge = obj;
	}

	/* @model:锟斤拷取 */
	public String getImgLarge() {
		return this.imgLarge;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setImgMiddle(String obj) {
		this.imgMiddle = obj;
	}

	/* @model:锟斤拷取 */
	public String getImgMiddle() {
		return this.imgMiddle;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setImgSmall(String obj) {
		this.imgSmall = obj;
	}

	/* @model:锟斤拷取 */
	public String getImgSmall() {
		return this.imgSmall;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setAttrValue(String obj) {
		this.attrValue = obj;
	}

	/* @model:锟斤拷取 */
	public String getAttrValue() {
		return this.attrValue;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setAttrDesc(String obj) {
		this.attrDesc = obj;
	}

	/* @model:锟斤拷取 */
	public String getAttrDesc() {
		return this.attrDesc;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGoodsStatus(String obj) {
		this.goodsStatus = obj;
	}

	/* @model:锟斤拷取 */
	public String getGoodsStatus() {
		return this.goodsStatus;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGmtListing(Date obj) {
		this.gmtListing = obj;
	}

	/* @model:锟斤拷取 */
	public Date getGmtListing() {
		return this.gmtListing;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGmtDelisting(Date obj) {
		this.gmtDelisting = obj;
	}

	/* @model:锟斤拷取 */
	public Date getGmtDelisting() {
		return this.gmtDelisting;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setClickCount(int obj) {
		this.clickCount = obj;
	}

	/* @model:锟斤拷取 */
	public int getClickCount() {
		return this.clickCount;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setWarnNumber(int obj) {
		this.warnNumber = obj;
	}

	/* @model:锟斤拷取 */
	public int getWarnNumber() {
		return this.warnNumber;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setOriginalImg(String obj) {
		this.originalImg = obj;
	}

	/* @model:锟斤拷取 */
	public String getOriginalImg() {
		return this.originalImg;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGmtCreate(Date obj) {
		this.gmtCreate = obj;
	}

	/* @model:锟斤拷取 */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGmtModify(Date obj) {
		this.gmtModify = obj;
	}

	/* @model:锟斤拷取 */
	public Date getGmtModify() {
		return this.gmtModify;
	}

	/* @model:锟斤拷锟斤拷 */
	public void setGoodsRank(double obj) {
		this.goodsRank = obj;
	}

	/* @model:锟斤拷取 */
	public double getGoodsRank() {
		return this.goodsRank;
	}

	/* {@inheritDoc} */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Goods)) {
			return false;
		}
		final Goods goods = (Goods) o;
		return this.hashCode() == goods.hashCode();
	}

	/* {@inheritDoc} */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodsSn == null) ? 0 : goodsSn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((goodsDesc == null) ? 0 : goodsDesc.hashCode());
		result = prime * result
				+ ((imgLarge == null) ? 0 : imgLarge.hashCode());
		result = prime * result
				+ ((goodsStatus == null) ? 0 : goodsStatus.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());
		result = prime * result
				+ ((gmtListing == null) ? 0 : gmtListing.hashCode());
		result = prime * result
				+ ((gmtDelisting == null) ? 0 : gmtDelisting.hashCode());
		result = prime * result
				+ ((originalImg == null) ? 0 : originalImg.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModify == null) ? 0 : gmtModify.hashCode());

		return result;
	}

	@Override
	public String toString() {
		return "Goods [log=" + log + ", id=" + id + ", catCode=" + catCode
				+ ", catName=" + catName + ", goodsSn=" + goodsSn + ", title="
				+ title + ", brandId=" + brandId + ", saleNumber=" + saleNumber
				+ ", marketPrice=" + marketPrice + ", goodsPrice=" + goodsPrice
				+ ", goodsNumber=" + goodsNumber + ", hkGoodsNumber="
				+ hkGoodsNumber + ", goodsNumberStr=" + goodsNumberStr
				+ ", hkGoodsNumberStr=" + hkGoodsNumberStr + ", goodsDesc="
				+ goodsDesc + ", imgLarge=" + imgLarge + ", imgMiddle="
				+ imgMiddle + ", imgSmall=" + imgSmall + ", attrValue="
				+ attrValue + ", attrDesc=" + attrDesc + ", goodsStatus="
				+ goodsStatus + ", gmtListing=" + gmtListing
				+ ", gmtDelisting=" + gmtDelisting + ", clickCount="
				+ clickCount + ", goodsWeight=" + goodsWeight + ", warnNumber="
				+ warnNumber + ", originalImg=" + originalImg + ", gmtCreate="
				+ gmtCreate + ", gmtModify=" + gmtModify + ", sortOrder="
				+ sortOrder + ", goodsRank=" + goodsRank + ", priceMin="
				+ priceMin + ", priceMax=" + priceMax + ", cabinet=" + cabinet
				+ ", titles=" + titles + ", isCutprice=" + isCutprice
				+ ", gmtCutprice=" + gmtCutprice + ", isGmtCutprice="
				+ isGmtCutprice + ", choose=" + choose + ", salesProPrice="
				+ salesProPrice + ", wholesalePrice=" + wholesalePrice
				+ ", goodsUnit=" + goodsUnit + ", agentPrice=" + agentPrice
				+ ", isAgent=" + isAgent + ", brandName=" + brandName
				+ ", pointRule=" + pointRule + ", availableStockList="
				+ availableStockList + ", gmtDesc=" + gmtDesc
				+ ", isWholesale=" + isWholesale + ", startNum=" + startNum
				+ ", firstPrice=" + firstPrice + ", goodsWholsaleList="
				+ goodsWholsaleList + ", wholeAllPrice=" + wholeAllPrice
				+ ", goodsItem=" + goodsItem + ", firstStart=" + firstStart
				+ ", secondStart=" + secondStart + ", thirdStart=" + thirdStart
				+ ", firstEnd=" + firstEnd + ", secondEnd=" + secondEnd
				+ ", firstPrices=" + firstPrices + ", secondPrice="
				+ secondPrice + ", thirdPrice=" + thirdPrice + ", isPaipai="
				+ isPaipai + ", paipaiClassId=" + paipaiClassId
				+ ", paipaiAttr=" + paipaiAttr + ", checkflag=" + checkflag
				+ ", viceName=" + viceName + ", isTaobao=" + isTaobao
				+ ", taobaoClassId=" + taobaoClassId + ", taobaoAttr="
				+ taobaoAttr + ", taobaoSkuProp=" + taobaoSkuProp
				+ ", taobaoInputStr=" + taobaoInputStr + ", taobaoInputPids="
				+ taobaoInputPids + ", taobaoSkuPropName=" + taobaoSkuPropName
				+ ", cabinetWholePrice=" + cabinetWholePrice
				+ ", checkflagTaobao=" + checkflagTaobao + ", expressIds="
				+ expressIds + ", noPicture=" + noPicture + ", zeroStock="
				+ zeroStock + ", returnPointId=" + returnPointId
				+ ", goodsKind=" + goodsKind + ", ids=" + ids + ", order="
				+ order + ", billId=" + billId + ", idSeries=" + idSeries
				+ ", size=" + size + ", targetCustomers=" + targetCustomers
				+ ", color=" + color + ", material=" + material + ", type="
				+ type + ", siteId=" + siteId + ", cost=" + cost
				+ ", idCostCurrency=" + idCostCurrency + ", hkhxPrice="
				+ hkhxPrice + ", euPrice=" + euPrice + ", hkPrice=" + hkPrice
				+ ", productId=" + productId + ", ishavegoodsnumber="
				+ ishavegoodsnumber + ", selectIds=" + selectIds + ", reason="
				+ reason + "]";
	}

	public String getCatCode() {
		return catCode;
	}

	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(String priceMin) {
		this.priceMin = priceMin;
	}

	public String getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getIsCutprice() {
		return isCutprice;
	}

	public void setIsCutprice(String isCutprice) {
		this.isCutprice = isCutprice;
	}

	public Date getGmtCutprice() {
		return gmtCutprice;
	}

	public void setGmtCutprice(Date gmtCutprice) {
		this.gmtCutprice = gmtCutprice;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public Double getSalesProPrice() {
		return salesProPrice;
	}

	public void setSalesProPrice(Double salesProPrice) {
		this.salesProPrice = salesProPrice;
	}

	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public Double getAgentPrice() {
		return agentPrice;
	}

	public void setAgentPrice(Double agentPrice) {
		this.agentPrice = agentPrice;
	}

	/**
	 * @return the isAgent
	 */
	public String getIsAgent() {
		return isAgent;
	}

	/**
	 * @param isAgent
	 *            the isAgent to set
	 */
	public void setIsAgent(String isAgent) {
		this.isAgent = isAgent;
	}

	public List<AvailableStock> getAvailableStockList() {
		return availableStockList;
	}

	public void setAvailableStockList(List<AvailableStock> availableStockList) {
		this.availableStockList = availableStockList;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPointRule() {
		return pointRule;
	}

	public void setPointRule(String pointRule) {
		this.pointRule = pointRule;
	}

	public long getStartNum() {
		return startNum;
	}

	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}

	public double getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(double firstPrice) {
		this.firstPrice = firstPrice;
	}

	public String getIsTaobao() {
		return isTaobao;
	}

	public void setIsTaobao(String isTaobao) {
		this.isTaobao = isTaobao;
	}

	public Long getTaobaoClassId() {
		return taobaoClassId;
	}

	public void setTaobaoClassId(Long taobaoClassId) {
		this.taobaoClassId = taobaoClassId;
	}

	public String getTaobaoAttr() {
		return taobaoAttr;
	}

	public void setTaobaoAttr(String taobaoAttr) {
		this.taobaoAttr = taobaoAttr;
	}

	public String getTaobaoInputStr() {
		return taobaoInputStr;
	}

	public void setTaobaoInputStr(String taobaoInputStr) {
		this.taobaoInputStr = taobaoInputStr;
	}

	public String getTaobaoInputPids() {
		return taobaoInputPids;
	}

	public void setTaobaoInputPids(String taobaoInputPids) {
		this.taobaoInputPids = taobaoInputPids;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCheckflagTaobao() {
		return checkflagTaobao;
	}

	public void setCheckflagTaobao(String checkflagTaobao) {
		this.checkflagTaobao = checkflagTaobao;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getTargetCustomers() {
		return targetCustomers;
	}

	public void setTargetCustomers(Long targetCustomers) {
		this.targetCustomers = targetCustomers;
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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public int getIdCostCurrency() {
		return idCostCurrency;
	}

	public void setIdCostCurrency(int idCostCurrency) {
		this.idCostCurrency = idCostCurrency;
	}

	public Double getHkhxPrice() {
		return hkhxPrice;
	}

	public void setHkhxPrice(Double hkhxPrice) {
		this.hkhxPrice = hkhxPrice;
	}

	public Double getEuPrice() {
		return euPrice;
	}

	public void setEuPrice(Double euPrice) {
		this.euPrice = euPrice;
	}

	public Double getHkPrice() {
		return hkPrice;
	}

	public void setHkPrice(Double hkPrice) {
		this.hkPrice = hkPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getHkGoodsNumber() {
		return hkGoodsNumber;
	}

	public void setHkGoodsNumber(int hkGoodsNumber) {
		this.hkGoodsNumber = hkGoodsNumber;
	}

	public String getGoodsNumberStr() {
		return goodsNumberStr;
	}

	public void setGoodsNumberStr(String goodsNumberStr) {
		this.goodsNumberStr = goodsNumberStr;
	}

	public String getHkGoodsNumberStr() {
		return hkGoodsNumberStr;
	}

	public void setHkGoodsNumberStr(String hkGoodsNumberStr) {
		this.hkGoodsNumberStr = hkGoodsNumberStr;
	}

	
	public String getGmtListingStart() {
		return gmtListingStart;
	}

	public void setGmtListingStart(String gmtListingStart) {
		this.gmtListingStart = gmtListingStart;
	}

	public String getGmtListingEnd() {
		return gmtListingEnd;
	}

	public void setGmtListingEnd(String gmtListingEnd) {
		this.gmtListingEnd = gmtListingEnd;
	}
	/*			*/
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public int getHkthNumber() {
		return hkthNumber;
	}

	public void setHkthNumber(int hkthNumber) {
		this.hkthNumber = hkthNumber;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getGoodsWeightStr() {
		return goodsWeightStr;
	}

	public void setGoodsWeightStr(String goodsWeightStr) {
		this.goodsWeightStr = goodsWeightStr;
	}
	
	
	

	
}
