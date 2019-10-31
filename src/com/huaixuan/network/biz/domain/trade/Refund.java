package com.huaixuan.network.biz.domain.trade;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * (bibleUtil auto code generation)
 *
 * @version 3.2.0
 */
public class Refund extends BaseObject implements Serializable {
    private static final long serialVersionUID = 5617386776574010283L;

    /* @property: */
    private Long              id;

    /* @property: */
    private String            refundId;

    /* @property: */
    private String            tid;

    /* @property: */
    private Long              shopId;

    /* @property: */
    private String            status;

    /* @property: */
    private Long              buyId;

    /* @property: */
    private String            buyNick;

    /* @property: */
    private Long              sellerId;

    /* @property: */
    private String            sellerNick;

    /* @property: */
    private Double            goodsAmount;

    /* @property: */
    private Double            shippingAmount;

    /* @property: */
    private Double            amount;

    /* @property: */
    private String            isGoodsRecevied;

    /* @property: */
    private String            isGoodsUntread;

    /* @property: */
    private Double            refundAmount;

    /* @property: */
    private String            note;

    /* @property: */
    private Date              gmtCreate;

    /* @property: */
    private Date              gmtModify;

    private String            type;

    private int               ticketAmount;

    private String            creater;

    private String            isRefund;                               //是否退货减积分

    private String            serviceNote;                            //客服备注

    private Long              depFirstId;                             //一级仓库ID

    private String            relRefundId;

    /* add by jinxx  */
    private String            reason;

    public String getRelRefundId() {
        return relRefundId;
    }

    public void setRelRefundId(String relRefundId) {
        this.relRefundId = relRefundId;
    }

    /* Default constructor - creates a new instance with no values set. */
    public Refund() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBuyId() {
        return buyId;
    }

    public void setBuyId(Long buyId) {
        this.buyId = buyId;
    }

    public String getBuyNick() {
        return buyNick;
    }

    public void setBuyNick(String buyNick) {
        this.buyNick = buyNick;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getIsGoodsRecevied() {
        return isGoodsRecevied;
    }

    public void setIsGoodsRecevied(String isGoodsRecevied) {
        this.isGoodsRecevied = isGoodsRecevied;
    }

    public String getIsGoodsUntread() {
        return isGoodsUntread;
    }

    public void setIsGoodsUntread(String isGoodsUntread) {
        this.isGoodsUntread = isGoodsUntread;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTicketAmount() {
        return ticketAmount;
    }

    public void setTicketAmount(int ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    /* {@inheritDoc} */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Refund)) {
            return false;
        }
        final Refund refund = (Refund) o;
        return this.hashCode() == refund.hashCode();
    }

    public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/* {@inheritDoc} */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : new Long(id).hashCode());
        result = prime * result + ((refundId == null) ? 0 : refundId.hashCode());
        result = prime * result + ((tid == null) ? 0 : tid.hashCode());
        result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((buyId == null) ? 0 : buyId.hashCode());
        result = prime * result + ((buyNick == null) ? 0 : buyNick.hashCode());
        result = prime * result + ((sellerId == null) ? 0 : sellerId.hashCode());
        result = prime * result + ((sellerNick == null) ? 0 : sellerNick.hashCode());
        result = prime * result + ((goodsAmount == null) ? 0 : goodsAmount.hashCode());
        result = prime * result + ((shippingAmount == null) ? 0 : shippingAmount.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((isGoodsRecevied == null) ? 0 : isGoodsRecevied.hashCode());
        result = prime * result + ((isGoodsUntread == null) ? 0 : isGoodsUntread.hashCode());
        result = prime * result + ((refundAmount == null) ? 0 : refundAmount.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
        result = prime * result + ((gmtModify == null) ? 0 : gmtModify.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("refundId", this.refundId).append("tid", this.tid).append("shopId",
            this.shopId).append("status", this.status).append("buyId", this.buyId).append(
            "buyNick", this.buyNick).append("sellerId", this.sellerId).append("sellerNick",
            this.sellerNick).append("goodsAmount", this.goodsAmount).append("shippingAmount",
            this.shippingAmount).append("amount", this.amount).append("isGoodsRecevied",
            this.isGoodsRecevied).append("isGoodsUntread", this.isGoodsUntread).append(
            "refundAmount", this.refundAmount).append("note", this.note).append("gmtCreate",
            this.gmtCreate).append("gmtModify", this.gmtModify).append("type", this.type);
        return sb.toString();
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }

    public String getServiceNote() {
        return serviceNote;
    }

    public void setServiceNote(String serviceNote) {
        this.serviceNote = serviceNote;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

}
