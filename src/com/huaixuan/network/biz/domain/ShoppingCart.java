package com.huaixuan.network.biz.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class ShoppingCart extends BaseObject implements Serializable, Comparable {
    /* @property: */
    private long id;
    /* @property: */
    private long userId;
    /* @property: */
    private long goodsId;
    /* @property: */
    private int  goodsNumber;
    /* @property: */
    private Date gmtModify;
    /* @property: */
    private Date gmtCreate;

    private Long promationId;

    private String timeTag;

    private String goodsAttrIds;

    private String goodsAttrNameAndValues;

    private String goodsName;

    private String goodsDisplayTag;

    private Long goodsInstanceId;
    
    private double goodsWeight; //商品重量
    
    private String isWholesale;
    
    private double wholesalePrice; //批发等级价格
    
    private boolean isExemptPostage; // 是否包邮活动产品

    
	public boolean getIsExemptPostage() {
		return isExemptPostage;
	}

	public void setExemptPostage(boolean isExemptPostage) {
		this.isExemptPostage = isExemptPostage;
	}
	public double getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	/**
     * @return the goodsWeight
     */
    public double getGoodsWeight() {
        return goodsWeight;
    }

    /**
     * @param goodsWeight the goodsWeight to set
     */
    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsDisplayTag() {
		return goodsDisplayTag;
	}

	public void setGoodsDisplayTag(String goodsDisplayTag) {
		this.goodsDisplayTag = goodsDisplayTag;
	}

	public String getGoodsAttrNameAndValues() {
		return goodsAttrNameAndValues;
	}

	public void setGoodsAttrNameAndValues(String goodsAttrNameAndValues) {
		this.goodsAttrNameAndValues = goodsAttrNameAndValues;
	}

	/* Default constructor - creates a new instance with no values set. */
    public ShoppingCart() {
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
    public void setUserId(long obj) {
        this.userId = obj;
    }

    /* @model: */
    public long getUserId() {
        return this.userId;
    }

    /* @model: */
    public void setGoodsId(long obj) {
        this.goodsId = obj;
    }

    /* @model: */
    public long getGoodsId() {
        return this.goodsId;
    }

    /* @model: */
    public void setGoodsNumber(int obj) {
        this.goodsNumber = obj;
    }

    /* @model: */
    public int getGoodsNumber() {
        return this.goodsNumber;
    }

    /* @model: */
    public void setGmtModify(Date obj) {
        this.gmtModify = obj;
    }

    /* @model: */
    public Date getGmtModify() {
        return this.gmtModify;
    }

    /* @model: */
    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    /* @model: */
    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        final ShoppingCart shoppingcart = (ShoppingCart) o;
        return this.hashCode() == shoppingcart.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result += ((result + prime) * id ^ 2) % Integer.MAX_VALUE;
        result += ((result + prime) * userId ^ 2) % Integer.MAX_VALUE;
        result += ((result + prime) * this.goodsNumber ^ 2) % Integer.MAX_VALUE;
        result += ((result + prime) * this.goodsId ^ 2) % Integer.MAX_VALUE;
        result += ((result + prime) * this.gmtCreate.getTime()) % Integer.MAX_VALUE;
        result += ((result + prime) * this.gmtModify.getTime()) % Integer.MAX_VALUE;
        return result;

    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("userId", this.userId).append("goodsId", this.goodsId).append(
            "goodsNumber", this.goodsNumber).append("gmtModify", this.gmtModify).append(
            "gmtCreate", this.gmtCreate).append("isExemptPostage" , this.isExemptPostage);
        return sb.toString();
    }

    public int compareTo(Object o) {
        ShoppingCart sc = (ShoppingCart) o;
        if (equals(sc))
            return 0;
        if (sc.getId() < getId())
            return 1;
        if (sc.getId() > getId())
            return -1;
        if (sc.getGoodsId() < getGoodsId())
            return 1;
        if (sc.getGoodsId() > getGoodsId())
            return -1;
        if (sc.getGoodsNumber() < getGoodsNumber())
            return 1;
        if (sc.getGoodsNumber() > getGoodsNumber())
            return -1;
        return 0;
    }



	public String getTimeTag() {
		return timeTag;
	}


	public Long getPromationId() {
		return promationId;
	}

	public void setPromationId(Long promationId) {
		this.promationId = promationId;
	}

	public void setTimeTag(String timeTag) {
		this.timeTag = timeTag;
	}

	public String getGoodsAttrIds() {
		return goodsAttrIds;
	}

	public void setGoodsAttrIds(String goodsAttrIds) {
		this.goodsAttrIds = goodsAttrIds;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getGoodsInstanceId() {
		return goodsInstanceId;
	}

	public void setGoodsInstanceId(Long goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}





}
