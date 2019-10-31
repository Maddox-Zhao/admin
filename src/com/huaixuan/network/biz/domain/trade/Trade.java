package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.base.AutoMath;
import com.huaixuan.network.biz.domain.express.ExpressDist;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class Trade extends BaseObject implements Serializable, Comparable<Trade>, AutoMath {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /* @property: */
    private long              id;

    /* @property: */
    private String            tid;

    /* @property: */
    private long              shopId;

    /* @property: */
    private String            status;

    /* @property: */
    private long              buyId;

    /* @property: */
    private String            buyNick;

    /* @property: */
    private long              sellerId;

    /* @property: */
    private String            sellerNick;

    /* @property: */
    private String            payStatus;

    /* @property: */
    private double            goodsAmount;

    private Long              expressId;

    private String            expressPayment;

    private String            expressName;

    /* @property: */
    private double            shippingAmount;

    /* @property: */
    private double            amount;

    /* @property: */
    private String            type;

    /* @property: */
    private String            buyerNote;

    /* @property: */
    private Date              payTime;

    /* @property: */
    private Date              finishTime;

    /* @property: */
    private String            receiver;

    /* @property: */
    private String            country;

    /* @property: */
    private String            province;

    /* @property: */
    private String            city;

    /* @property: */
    private String            district;

    /* @property: */
    private String            address;

    /* @property: */
    private String            zipcode;

    /* @property: */
    private String            mobile;

    private String            tel;

    /* @property: */
    private Date              gmtCreate;

    /* @property: */
    private Date              gmtModify;

    private Integer           userPoint;

    private Integer           sendPoint;

    private int               ticketAmount;

    private String            payment;                                //支付方式

    private String            expressCode;                            //出库单物流号

    private double            goodsAmountSum;

    private String            isPoint;                                //是否累计积分

    private String            seviceNote;                             //客服留言

    private double            totalWeight;                            //订单总重量

    private double            weightFirst;                            //首重重量

    private double            weightFirstMoney;                       //首重价格

    private double            weightNext;                             //续重重量

    private double            weightNextMoney;                        //续重价格

    private Double            agentSellAmount;                        //代销用户淘宝销售总价

    private Set<Order>        orders           = new HashSet<Order>();

    private ExpressDist       expressDist;

    private Long              depFirstId;                             //一级仓库ID

    private String            depFirstName;                           //一级仓库名称

    private int               tradeType;                              //交易类型

    private String            isWholesale;                            //n:普通订单（默认），w：批发订单

    private String            wholesaleStatus;                        //wait_confirm:等待客服人员确认批发订单(默认)，confirmed：客服确认完毕

    private Date              gmtPeriodPayEnd;                        //周期结算截止时间

    private List<Order>       orderList;

    // add by wangkun 2010/09/28 start 接口订单属性
    private String            dealCode;                               //接口订单编码
    private Date              createTime;                             //接口订单创建时间
    private Date              dealPayTime;                            //接口订单支付时间
    private String            purchaseId;                             // 淘宝分销平台采购单id
    // add by wangkun 2010/09/28 end

    private String            memo;                                   //修改价格记录

    /* begin add by shenzh Nov 4, 2010 说明： 订单表中新增字段，表明该订单的关闭状态下是否因为缺货*/
    private String            stockoutStatus;
    /* end by shenzh Nov 4, 2010 */
    private String            invoice;

    private String            interTid;                                // add by fanyj 2010-11-18 接口订单号

    private String            isPurchased;                             //是否采购

    private String            invoiceName;                             //added by chenhang 2011-01-05发票抬头

    private String            isStocked;                               //added by chenhang 2011-02-11 是否备货

    private String           interfaceExpressCode; //同步订单发货状态时候的物流公司标识(按订单同步)
    private Long             countWithStatus;

    private String           paipaiTradeId;
    
    private String           mobileRewrite;
    
    private int           	 customerorderIdOrder;
    
    private double           customerorderAmount;
    
    private Long           	 idlastoperator;
    
    private int           	 idPriceCurrency;
    
    private Integer 			cash;  //现金付款
    
    private Long 			customerId; 

    public String getMobileRewrite() {
		return mobileRewrite;
	}

	public void setMobileRewrite(String mobileRewrite) {
		this.mobileRewrite = mobileRewrite;
	}

	public Long getCountWithStatus() {
        return countWithStatus;
    }

    public void setCountWithStatus(Long countWithStatus) {
        this.countWithStatus = countWithStatus;
    }
    public String getPaipaiTradeId() {
		return paipaiTradeId;
	}

	public void setPaipaiTradeId(String paipaiTradeId) {
		this.paipaiTradeId = paipaiTradeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInterfaceExpressCode() {
        return interfaceExpressCode;
    }

    public void setInterfaceExpressCode(String interfaceExpressCode) {
        this.interfaceExpressCode = interfaceExpressCode;
    }

    public String getIsStocked() {
        return isStocked;
    }

    public void setIsStocked(String isStocked) {
        this.isStocked = isStocked;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInterTid() {
        return interTid;
    }

    public void setInterTid(String interTid) {
        this.interTid = interTid;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getDealCode() {
        return dealCode;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDealPayTime() {
        return dealPayTime;
    }

    public void setDealPayTime(Date dealPayTime) {
        this.dealPayTime = dealPayTime;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Date getGmtPeriodPayEnd() {
        return gmtPeriodPayEnd;
    }

    public void setGmtPeriodPayEnd(Date gmtPeriodPayEnd) {
        this.gmtPeriodPayEnd = gmtPeriodPayEnd;
    }

    public String getIsWholesale() {
        return isWholesale;
    }

    public void setIsWholesale(String isWholesale) {
        this.isWholesale = isWholesale;
    }

    public String getWholesaleStatus() {
        return wholesaleStatus;
    }

    public String getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = isPurchased;
    }

    public void setWholesaleStatus(String wholesaleStatus) {
        this.wholesaleStatus = wholesaleStatus;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public ExpressDist getExpressDist() {
        return expressDist;
    }

    public void setExpressDist(ExpressDist expressDist) {
        this.expressDist = expressDist;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    /**
     * @return the weightFirst
     */
    public double getWeightFirst() {
        return weightFirst;
    }

    /**
     * @param weightFirst the weightFirst to set
     */
    public void setWeightFirst(double weightFirst) {
        this.weightFirst = weightFirst;
    }

    /**
     * @return the weightFirstMoney
     */
    public double getWeightFirstMoney() {
        return weightFirstMoney;
    }

    /**
     * @param weightFirstMoney the weightFirstMoney to set
     */
    public void setWeightFirstMoney(double weightFirstMoney) {
        this.weightFirstMoney = weightFirstMoney;
    }

    /**
     * @return the weightNext
     */
    public double getWeightNext() {
        return weightNext;
    }

    /**
     * @param weightNext the weightNext to set
     */
    public void setWeightNext(double weightNext) {
        this.weightNext = weightNext;
    }

    /**
     * @return the weightNextMoney
     */
    public double getWeightNextMoney() {
        return weightNextMoney;
    }

    /**
     * @param weightNextMoney the weightNextMoney to set
     */
    public void setWeightNextMoney(double weightNextMoney) {
        this.weightNextMoney = weightNextMoney;
    }

    /**
     * @return the totalWeight
     */
    public double getTotalWeight() {
        return totalWeight;
    }

    /**
     * @param totalWeight the totalWeight to set
     */
    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * @return the goodsAmountSum
     */
    public double getGoodsAmountSum() {
        return goodsAmountSum;
    }

    /**
     * @param goodsAmountSum the goodsAmountSum to set
     */
    public void setGoodsAmountSum(double goodsAmountSum) {
        this.goodsAmountSum = goodsAmountSum;
    }

    /**
     * @return the expressCode
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * @param expressCode the expressCode to set
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /* Default constructor - creates a new instance with no values set. */
    public Trade() {
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getExpressPayment() {
        return expressPayment;
    }

    public void setExpressPayment(String expressPayment) {
        this.expressPayment = expressPayment;
    }

    /* @model: */
    public void setId(long obj) {
        this.id = obj;
    }

    /* @model: */
    public long getId() {
        return this.id;
    }

    /* @model: */
    public void setTid(String obj) {
        this.tid = obj;
    }

    /* @model: */
    public String getTid() {
        return this.tid;
    }

    /* @model: */
    public void setShopId(long obj) {
        this.shopId = obj;
    }

    /* @model: */
    public long getShopId() {
        return this.shopId;
    }

    /* @model: */
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model: */
    public String getStatus() {
        return this.status;
    }

    /* @model: */
    public void setBuyId(long obj) {
        this.buyId = obj;
    }

    /* @model: */
    public long getBuyId() {
        return this.buyId;
    }

    /* @model: */
    public void setBuyNick(String obj) {
        this.buyNick = obj;
    }

    /* @model: */
    public String getBuyNick() {
        return this.buyNick;
    }

    /* @model: */
    public void setSellerId(long obj) {
        this.sellerId = obj;
    }

    /* @model: */
    public long getSellerId() {
        return this.sellerId;
    }

    /* @model: */
    public void setSellerNick(String obj) {
        this.sellerNick = obj;
    }

    /* @model: */
    public String getSellerNick() {
        return this.sellerNick;
    }

    /* @model: */
    public void setPayStatus(String obj) {
        this.payStatus = obj;
    }

    /* @model: */
    public String getPayStatus() {
        return this.payStatus;
    }

    /* @model: */
    public void setGoodsAmount(double obj) {
        this.goodsAmount = obj;
    }

    /* @model: */
    public double getGoodsAmount() {
        return this.goodsAmount;
    }

    /* @model: */
    public void setShippingAmount(double obj) {
        this.shippingAmount = obj;
    }

    /* @model: */
    public double getShippingAmount() {
        return this.shippingAmount;
    }

    /* @model: */
    public void setAmount(double obj) {
        this.amount = obj;
    }

    /* @model: */
    public double getAmount() {
        return this.amount;
    }

    /* @model: */
    public void setType(String obj) {
        this.type = obj;
    }

    /* @model: */
    public String getType() {
        return this.type;
    }

    /* @model: */
    public void setBuyerNote(String obj) {
        this.buyerNote = obj;
    }

    /* @model: */
    public String getBuyerNote() {
        return this.buyerNote;
    }

    /* @model: */
    public void setPayTime(Date obj) {
        this.payTime = obj;
    }

    /* @model: */
    public Date getPayTime() {
        return this.payTime;
    }

    /* @model: */
    public void setFinishTime(Date obj) {
        this.finishTime = obj;
    }

    /* @model: */
    public Date getFinishTime() {
        return this.finishTime;
    }

    /* @model: */
    public void setReceiver(String obj) {
        this.receiver = obj;
    }

    /* @model: */
    public String getReceiver() {
        return this.receiver;
    }

    /* @model: */
    public void setCountry(String obj) {
        this.country = obj;
    }

    /* @model: */
    public String getCountry() {
        return this.country;
    }

    /* @model: */
    public void setProvince(String obj) {
        this.province = obj;
    }

    /* @model: */
    public String getProvince() {
        return this.province;
    }

    /* @model: */
    public void setCity(String obj) {
        this.city = obj;
    }

    /* @model: */
    public String getCity() {
        return this.city;
    }

    /* @model: */
    public void setDistrict(String obj) {
        this.district = obj;
    }

    /* @model: */
    public String getDistrict() {
        return this.district;
    }

    /* @model: */
    public void setAddress(String obj) {
        this.address = obj;
    }

    /* @model: */
    public String getAddress() {
        return this.address;
    }

    /* @model: */
    public void setZipcode(String obj) {
        this.zipcode = obj;
    }

    /* @model: */
    public String getZipcode() {
        return this.zipcode;
    }

    /* @model: */
    public void setMobile(String obj) {
        this.mobile = obj;
    }

    /* @model: */
    public String getMobile() {
        return this.mobile;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* @model: */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model: */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(Integer sendPoint) {
        this.sendPoint = sendPoint;
    }

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }
    
    
    
    

	public Integer getCash()
	{
		return cash;
	}

	public void setCash(Integer cash)
	{
		this.cash = cash;
	}
	
	

	public Long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(Long customerId)
	{
		this.customerId = customerId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trade)) {
            return false;
        }
        final Trade trade = (Trade) o;
        return this.hashCode() == trade.hashCode();
    }

    // modify by wangkun 2010/09/28 start 创建时间和修改时间为空的时候会报空异常
    /*
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result + (int) this.id * prime;
        result = result + (int) this.gmtCreate.getTime() * prime;
        result = result + (int) this.gmtModify.getTime() * prime;
        return result;
    }
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    // modify by wangkun 2010/09/28 end

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("tid", this.tid).append("shopId", this.shopId).append("status",
            this.status).append("buyId", this.buyId).append("buyNick", this.buyNick).append(
            "sellerId", this.sellerId).append("sellerNick", this.sellerNick).append("payStatus",
            this.payStatus).append("goodsAmount", this.goodsAmount).append("shippingAmount",
            this.shippingAmount).append("amount", this.amount).append("type", this.type).append(
            "buyerNote", this.buyerNote).append("payTime", this.payTime).append("finishTime",
            this.finishTime).append("receiver", this.receiver).append("country", this.country)
            .append("province", this.province).append("city", this.city).append("district",
                this.district).append("address", this.address).append("zipcode", this.zipcode)
            .append("mobile", this.mobile).append("gmtCreate", this.gmtCreate).append("gmtModify",
                this.gmtModify).append("userPoint", this.userPoint).append("sendPoint",
                this.sendPoint);
        return sb.toString();
    }

    public int compareTo(Trade o) {
        return gmtCreate.compareTo(o.getGmtCreate());
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(String isPoint) {
        this.isPoint = isPoint;
    }

    public String getSeviceNote() {
        return seviceNote;
    }

    public void setSeviceNote(String seviceNote) {
        this.seviceNote = seviceNote;
    }

    public Double getAgentSellAmount() {
        return agentSellAmount;
    }

    public void setAgentSellAmount(Double agentSellAmount) {
        this.agentSellAmount = agentSellAmount;
    }

    public void math() {
        mathTotalWeight();
        mathWeightFirst();
        mathWeightFirstMoney();
        mathWeightNext();
        mathWeightNextMoney();
    }

    public void mathTotalWeight() {
        this.totalWeight = 0;
        if (orders != null)
            for (Order order : orders) {
                this.totalWeight += order.getGoodWeight() * order.getGoodsNumber();
            }
    }

    public void mathWeightFirst() {
        if (expressDist != null) {
            this.weightFirst = this.expressDist.getWeightFirst();
        }
    }

    public void mathWeightFirstMoney() {
        if (expressDist != null) {
            this.weightFirstMoney = this.expressDist.getWeightFirstMoney();
        }
    }

    public void mathWeightNext() {
        if (expressDist != null) {
            this.weightNext = this.expressDist.getWeightNext();
        }
    }

    public void mathWeightNextMoney() {
        if (expressDist != null) {
            this.weightNextMoney = this.expressDist.getWeightNextMoney();
        }
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public boolean canRefundApply() {
        if (this.finishTime != null) {
            Date today = new Date();
            int diffDays = (int) ((today.getTime() - (this.finishTime).getTime()) / (1000 * 60 * 60 * 24));
            if (diffDays > 15) {
                return false;
            }
        }
        return true;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStockoutStatus() {
        return stockoutStatus;
    }

    public void setStockoutStatus(String stockoutStatus) {
        this.stockoutStatus = stockoutStatus;
    }

	public int getCustomerorderIdOrder() {
		return customerorderIdOrder;
	}

	public void setCustomerorderIdOrder(int customerorderIdOrder) {
		this.customerorderIdOrder = customerorderIdOrder;
	}

	public double getCustomerorderAmount() {
		return customerorderAmount;
	}

	public void setCustomerorderAmount(double customerorderAmount) {
		this.customerorderAmount = customerorderAmount;
	}

	public Long getIdlastoperator() {
		return idlastoperator;
	}

	public void setIdlastoperator(Long idlastoperator) {
		this.idlastoperator = idlastoperator;
	}

	public int getIdPriceCurrency() {
		return idPriceCurrency;
	}

	public void setIdPriceCurrency(int idPriceCurrency) {
		this.idPriceCurrency = idPriceCurrency;
	}
	
}
