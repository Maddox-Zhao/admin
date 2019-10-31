package com.huaixuan.network.biz.domain.goods;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿(bibleUtil auto code generation)
 * @version 3.2.0
 */
public class GoodsAttr extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* @property: */
    private Long   id;
    /* @property: */
    private String attrCode;
    /* @property: */
    private String attrId;
    /* @property: */
    private String attrName;
    /* @property: */
    private String attrValue;
    /* @property: */
    private Long   goodsId;
    /* @property: */
    private Double price;

    private int isUse;

    /* Default constructor - creates a new instance with no values set. */
    public GoodsAttr() {
    }

    /* @model:ï¿ï¿ï¿ï¿ï¿ï¿ï¿ï¿ */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final GoodsAttr other = (GoodsAttr) obj;
        if (attrCode == null) {
            if (other.attrCode != null)
                return false;
        } else if (!attrCode.equals(other.attrCode))
            return false;
        if (attrName == null) {
            if (other.attrName != null)
                return false;
        } else if (!attrName.equals(other.attrName))
            return false;
        if (attrValue == null) {
            if (other.attrValue != null)
                return false;
        } else if (!attrValue.equals(other.attrValue))
            return false;

        return true;
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((attrCode == null) ? 0 : attrCode.hashCode());
        result = prime * result + ((attrName == null) ? 0 : attrName.hashCode());
        result = prime * result + ((attrValue == null) ? 0 : attrValue.hashCode());
        result = prime * result + ((goodsId == null) ? 0 : goodsId.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("attrCode", this.attrCode).append("attrName", this.attrName).append(
            "attrValue", this.attrValue).append("goodsId", this.goodsId)
            .append("price", this.price);
        return sb.toString();
    }

}
