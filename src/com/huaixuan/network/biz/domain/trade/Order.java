package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.hy.Product;
import com.huaixuan.network.biz.domain.storage.Storage;

/**
 * ï¿bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class Order extends BaseObject implements Serializable,  Comparable<Order>{
    /* @property: */
    private long                id;

    /* @property: */
    private String              tid;
    /* @property: */
    private long                goodsId;

    // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Æ·ï¿½ï¿½ï¿½Úµï¿½ï¿½×²ï¿½
    private Long                packageId;

    /* @property: */
    private String              goodsTitle;

    /* @property: */
    private long                goodsNumber;

    /* @property: */
    private double              goodsPrice;

    /* @property: */
    private double              goodsPriceSc;
    
    /* @property: */
    private double              marketPrice;

    /* @property: */
    private String              goodsAttr;

    /* @property: */
    private String              refundStauts;

    /* @property: */
    private double              goodWeight;

    /* @property: */
    private double              shippingAmount;

    /* @property: */
    private Date                gmtCreate;

    /* @property: */
    private Date                gmtModify;

    private Trade               trade;

    /* @property: */
    private String              buyAttr;

    private boolean             ischeck;

    private Long                goodsInstanceId;

    private String              goodsInstanceName;

    private Map<String, String> properties;

    private String              goodsSn;

    private String              status;

    private List<Storage>       storagelist;

    private long                refAmount;

    private String              code;

    private Integer             showAllStorageNum;

    private Double              agentSellPrice;    //ï¿½ï¿½ï¿½ï¿½ï¿½Ã»ï¿½ï¿½Ô±ï¿½ï¿½ï¿½ï¿½Û¼ï¿½

    private String              buyNick;//ï¿½Ã»ï¿½ï¿½ï¿½ï¿

    private long                buyId;//ï¿½Ã»ï¿½ID

    private long                salesSum;//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½

    private int              orderType;//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½

    private String               imgSmall;//Ð¡Í¼ï¿½ï¿½Ö·

    private String itemId;	// ï¿½ï¿½ï¿½ï¿½Æ½Ì¨ï¿½ï¿½Æ·id


    private String              goodsInstanceCode;

    private Long			depFirstId;

    private String 			depFirstName;

	private List<String> depNames;//ï¿½Ö¿ï¿½ï¿½ï¿½ï¿

	private Long         outDepId;//ï¿½ï¿½ï¿½âµ¥ï¿½ï¿½

	private double       totalPrice;//ï¿½Ü¼ï¿½
	
	private Long			instanceHkExistNum;//Ïã¸Û¿â´æ
	//json ·µ»Ø¶ÔÏóÅÐ¶ÏÀàÐÍ
	private String 			jsonMsgType;
	//json ·µ»Ø¶ÔÏóÐÅÏ¢
	private String 			jsonMessage;
	//json ·µ»Ø¶ÔÏó¶©µ¥Êµ¼Ê½ð¶î
	private double       	jsonTradeAmount;
	//json ·µ»Ø¶ÔÏóÔ­¼Û¼Û²î
	private double       	jsonComSc;
	//json ·µ»Ø¶ÔÏóorder×Ü¼Û
	private double       	jsonOrderPriceAll;
	
	private long            productNumber;
	
	private List<Product> products;

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getOutDepId() {
		return outDepId;
	}

	public void setOutDepId(Long outDepId) {
		this.outDepId = outDepId;
	}

	public String getDepFirstName() {
		return depFirstName;
	}

	public void setDepFirstName(String depFirstId) {
		this.depFirstName = depFirstId;
	}

	public List<String> getDepNames() {
		return depNames;
	}

	public void setDepNames(List<String> depNames) {
		this.depNames = depNames;
	}

	public String getGoodsInstanceCode() {
		return goodsInstanceCode;
	}

	public void setGoodsInstanceCode(String goodsInstanceCode) {
		this.goodsInstanceCode = goodsInstanceCode;
	}

	public String getImgSmall() {
		return imgSmall;
	}

	public void setImgSmall(String imgSmall) {
		this.imgSmall = imgSmall;
	}
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public long getSalesSum() {
		return salesSum;
	}

	public void setSalesSum(long salesSum) {
		this.salesSum = salesSum;
	}

	public long getBuyId() {
		return buyId;
	}

	public void setBuyId(long buyId) {
		this.buyId = buyId;
	}

	public String getBuyNick() {
		return buyNick;
	}

	public void setBuyNick(String buyNick) {
		this.buyNick = buyNick;
	}

	/**
     * @return the storagelist
     */
    public List<Storage> getStoragelist() {
        return storagelist;
    }

    /**
     * @param storagelist the storagelist to set
     */
    public void setStoragelist(List<Storage> storagelist) {
        this.storagelist = storagelist;
    }

    public String getBuyAttr() {
        return buyAttr;
    }

    public void setBuyAttr(String buyAttr) {
        this.buyAttr = buyAttr;
    }

    /* Default constructor - creates a new instance with no values set. */
    public Order() {
    }

    /* @model:ï¿*/
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model:ï¿*/
    public long getId() {
        return this.id;
    }

    /* @model:ï¿*/
    public void setTid(String obj) {
        this.tid = obj;
    }

    /* @model:ï¿*/
    public String getTid() {
        return this.tid;
    }

    /* @model:ï¿*/
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model:ï¿*/
    public long getGoodsId() {
        return this.goodsId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    /* @model:ï¿*/
    public void setGoodsTitle(String obj) {
        this.goodsTitle = obj;
    }

    /* @model:ï¿*/
    public String getGoodsTitle() {
        return this.goodsTitle;
    }

    /* @model:ï¿*/
    public void setGoodsNumber(long obj) {
        this.goodsNumber = obj;
    }

    /* @model:ï¿*/
    public long getGoodsNumber() {
        return this.goodsNumber;
    }

    /* @model:ï¿*/
    public void setGoodsPrice(double obj) {
        this.goodsPrice = obj;
    }

    /* @model:ï¿*/
    public double getGoodsPrice() {
        return this.goodsPrice;
    }

    /* @model:ï¿*/
    public void setMarketPrice(double obj) {
        this.marketPrice = obj;
    }

    /* @model:ï¿*/
    public double getMarketPrice() {
        return this.marketPrice;
    }

    /* @model:ï¿*/
    public void setGoodsAttr(String obj) {
        this.goodsAttr = obj;
    }

    /* @model:ï¿*/
    public String getGoodsAttr() {
        return this.goodsAttr;
    }

    /* @model:ï¿*/
    public void setRefundStauts(String obj) {
        this.refundStauts = obj;
    }

    /* @model:ï¿*/
    public String getRefundStauts() {
        return this.refundStauts;
    }

    /* @model:ï¿*/
    public void setGoodWeight(double obj) {
        this.goodWeight = obj;
    }

    /* @model:ï¿*/
    public double getGoodWeight() {
        return this.goodWeight;
    }

    /* @model:ï¿*/
    public void setShippingAmount(double obj) {
        this.shippingAmount = obj;
    }

    /* @model:ï¿*/
    public double getShippingAmount() {
        return this.shippingAmount;
    }

    /* @model:ï¿*/
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model:ï¿*/
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model:ï¿*/
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model:ï¿*/
    public Date getGmtModify() {
        return this.gmtModify;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    /* {@inheritDoc} */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        final Order order = (Order) o;
        return this.hashCode() == order.hashCode();
    }

    /* {@inheritDoc} */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : new Long(id).hashCode());
        result = prime * result + ((tid == null) ? 0 : tid.hashCode());
        result = prime * result + ((goodsTitle == null) ? 0 : goodsTitle.hashCode());
        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
        result = prime * result + ((goodsAttr == null) ? 0 : goodsAttr.hashCode());
        result = prime * result + ((refundStauts == null) ? 0 : refundStauts.hashCode());
        result = prime * result + ((goodsNumber == 0) ? 0 : new Long(goodsNumber).hashCode());
        result = prime * result + ((goodsPrice == 0) ? 0 : new Double(goodsPrice).hashCode());
        result = prime * result + ((marketPrice == 0) ? 0 : new Double(marketPrice).hashCode());

        return result;
    }

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("tid", this.tid).append("goodsId", this.goodsId).append("packageId",
            this.packageId).append("goodsTitle", this.goodsTitle).append("goodsNumber",
            this.goodsNumber).append("goodsPrice", this.goodsPrice).append("marketPrice",
            this.marketPrice).append("goodsAttr", this.goodsAttr).append("refundStauts",
            this.refundStauts).append("goodWeight", this.goodWeight).append("shippingAmount",
            this.shippingAmount).append("gmtCreate", this.gmtCreate).append("gmtModify",
            this.gmtModify);
        return sb.toString();
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public String getGoodsInstanceName() {
        return goodsInstanceName;
    }

    public void setGoodsInstanceName(String goodsInstanceName) {
        this.goodsInstanceName = goodsInstanceName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getRefAmount() {
        return refAmount;
    }

    public void setRefAmount(long refAmount) {
        this.refAmount = refAmount;
    }

    public long getCanRefAmount() {
        return this.goodsNumber - this.refAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getShowAllStorageNum() {
        return showAllStorageNum;
    }

    public void setShowAllStorageNum(Integer showAllStorageNum) {
        this.showAllStorageNum = showAllStorageNum;
    }

    public Double getAgentSellPrice() {
        return agentSellPrice;
    }

    public void setAgentSellPrice(Double agentSellPrice) {
        this.agentSellPrice = agentSellPrice;
    }


    public String getItemId(){
    	return itemId;
    }


    public void setItemId(String itemId){
    	this.itemId = itemId;
    }

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	@Override
	public int compareTo(Order o) {
		Order sc = (Order)o;
		return this.goodsInstanceId.compareTo(sc.getGoodsInstanceId());
	}

	public Long getInstanceHkExistNum() {
		return instanceHkExistNum;
	}

	public void setInstanceHkExistNum(Long instanceHkExistNum) {
		this.instanceHkExistNum = instanceHkExistNum;
	}

	public double getGoodsPriceSc() {
		return goodsPriceSc;
	}

	public void setGoodsPriceSc(double goodsPriceSc) {
		this.goodsPriceSc = goodsPriceSc;
	}

	public String getJsonMsgType() {
		return jsonMsgType;
	}

	public void setJsonMsgType(String jsonMsgType) {
		this.jsonMsgType = jsonMsgType;
	}

	public String getJsonMessage() {
		return jsonMessage;
	}

	public void setJsonMessage(String jsonMessage) {
		this.jsonMessage = jsonMessage;
	}

	public double getJsonTradeAmount() {
		return jsonTradeAmount;
	}

	public void setJsonTradeAmount(double jsonTradeAmount) {
		this.jsonTradeAmount = jsonTradeAmount;
	}

	public double getJsonComSc() {
		return jsonComSc;
	}

	public void setJsonComSc(double jsonComSc) {
		this.jsonComSc = jsonComSc;
	}

	public double getJsonOrderPriceAll() {
		return jsonOrderPriceAll;
	}

	public void setJsonOrderPriceAll(double jsonOrderPriceAll) {
		this.jsonOrderPriceAll = jsonOrderPriceAll;
	}

	public long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(long productNumber) {
		this.productNumber = productNumber;
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public void setProducts(List<Product> products)
	{
		this.products = products;
	}
	
	
	
}
