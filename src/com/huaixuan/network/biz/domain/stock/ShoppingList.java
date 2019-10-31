package com.huaixuan.network.biz.domain.stock;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* @ClassName: ShoppingList
* @Description: TODO
* @author liuwd weida-liu@foxmail.com
* @date 2011-2-23 上午10:38:59
 */
public class ShoppingList implements Serializable {

    private static final long serialVersionUID = -278536097899516219L;

    protected Log             log              = LogFactory.getLog(this.getClass());
    /* @property: */
    private long              id;
    /* @property: */
    private String            shoppingNum;
    /* @property: */
    private String            primitiveNum;
    /* @property: */
    private long              supplierId;
    /* @property: */
    private String            supplierName;
    /* @property: */
    private Date              shoppingTime;
    /* @property: */
    private Date              arriveTime;
    /* @property: */
    private Date              factArriveTime;
    /* @property: */
    private String            status;
    /* @property: */
    private String            creater;
    /* @property: */
    private Date              gmtCreate;

    private String            relationShoppingNum;

    private String            remark;

    private String            shoppingTime_str;

    private String            arriveTime_str;

    private String            factArriveTime_str;

    private String            gmtCreate_str;

    /*
     * 财务确认状�
     */
    private String            financeStatus;

    /* @property: 应收款�和*/
    private Double            sumDueFee;
    /* @property: 实收款�和*/
    private Double            sumFactFee;
    /* 库存类型 */
    private String            storType;

    private Long              depFirstId;

    private String            depFirstName;

    private String            isWholesale; // 订单类型: n：普通采购单（默认），w：批发采购单

    private String            tid; // 批发订单交易代码

    private String             billNum; //入库单号

    private long               indepId; //入库单id


    public long getIndepId() {
		return indepId;
	}

	public void setIndepId(long indepId) {
		this.indepId = indepId;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	/* Default constructor - creates a new instance with no values set. */
    public ShoppingList() {
    }

    public void setId(long obj) {
        this.id = obj;
    }

    public long getId() {
        return this.id;
    }

    public void setShoppingNum(String obj) {
        this.shoppingNum = obj;
    }

    public String getShoppingNum() {
        return this.shoppingNum;
    }

    public void setPrimitiveNum(String obj) {
        this.primitiveNum = obj;
    }

    public String getPrimitiveNum() {
        return this.primitiveNum;
    }

    public void setSupplierId(long obj) {
        this.supplierId = obj;
    }

    public long getSupplierId() {
        return this.supplierId;
    }

    public void setShoppingTime(Date obj) {
        this.shoppingTime = obj;
    }

    public Date getShoppingTime() {
        return this.shoppingTime;
    }

    public void setArriveTime(Date obj) {
        this.arriveTime = obj;
    }

    public Date getArriveTime() {
        return this.arriveTime;
    }

    /**
     * @return factArriveTime
     */
    public Date getFactArriveTime() {
        return factArriveTime;
    }

    public void setFactArriveTime(Date factArriveTime) {
        this.factArriveTime = factArriveTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreater(String obj) {
        this.creater = obj;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setGmtCreate(Date obj) {
        this.gmtCreate = obj;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    /* {@inheritDoc} */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoppingList)) {
            return false;
        }
        final ShoppingList shoppinglist = (ShoppingList) o;
        return this.hashCode() == shoppinglist.hashCode();
    }

    /**
     * @return relationShoppingNum
     */
    public String getRelationShoppingNum() {
        return relationShoppingNum;
    }

    public void setRelationShoppingNum(String relationShoppingNum) {
        this.relationShoppingNum = relationShoppingNum;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /* {@inheritDoc} */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((shoppingNum == null) ? 0 : shoppingNum.hashCode());
        result = prime * result + ((shoppingTime == null) ? 0 : shoppingTime.hashCode());
        result = prime * result + ((arriveTime == null) ? 0 : arriveTime.hashCode());
        result = prime * result + ((factArriveTime == null) ? 0 : factArriveTime.hashCode());
        result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((creater == null) ? 0 : creater.hashCode());

        return result;
    }

    /* {@inheritDoc} */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("shoppingNum", this.shoppingNum).append("primitiveNum",
            this.primitiveNum).append("supplierId", this.supplierId).append("shoppingTime",
            this.shoppingTime).append("arriveTime", this.arriveTime).append("status", this.status)
            .append("creater", this.creater).append("gmtCreate", this.gmtCreate);
        return sb.toString();
    }

    public String getArriveTime_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (getArriveTime() != null) {
            return df.format(this.getArriveTime());
        } else {
            return null;
        }
    }

    public void setArriveTime_str(String arriveTime_str) {
        this.arriveTime_str = arriveTime_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date ts = null;
        if (StringUtils.isNotBlank(arriveTime_str)) {
            try {
                ts = df.parse(arriveTime_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.arriveTime = ts;
    }

    public String getShoppingTime_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (this.getShoppingTime() != null) {
            return df.format(this.getShoppingTime());
        } else {
            return null;
        }
    }

    public void setShoppingTime_str(String shoppingTime_str) {
        this.shoppingTime_str = shoppingTime_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date ts = null;
        if (StringUtils.isNotBlank(shoppingTime_str)) {
            try {
                ts = df.parse(shoppingTime_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.shoppingTime = ts;
    }

    /**
     * @return factArriveTime_str
     */
    public String getFactArriveTime_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (this.getFactArriveTime() != null) {
            return df.format(this.getFactArriveTime());
        } else {
            return null;
        }
    }

    public void setFactArriveTime_str(String factArriveTime_str) {
        this.factArriveTime_str = factArriveTime_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date ts = null;
        if (StringUtils.isNotBlank(factArriveTime_str)) {
            try {
                ts = df.parse(factArriveTime_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.factArriveTime = ts;
    }

    /**
     * @return gmtCreate_str
     */
    public String getGmtCreate_str() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (this.getGmtCreate() != null) {
            return df.format(this.getGmtCreate());
        } else {
            return null;
        }
    }

    public void setGmtCreate_str(String gmtCreate_str) {
        this.gmtCreate_str = gmtCreate_str;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date ts = null;
        if (StringUtils.isNotBlank(gmtCreate_str)) {
            try {
                ts = df.parse(gmtCreate_str);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        this.gmtCreate = ts;
    }

    /**
     * @return supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

    public Double getSumDueFee() {
        return sumDueFee;
    }

    public void setSumDueFee(Double sumDueFee) {
        this.sumDueFee = sumDueFee;
    }

    public Double getSumFactFee() {
        return sumFactFee;
    }

    public void setSumFactFee(Double sumFactFee) {
        this.sumFactFee = sumFactFee;
    }

    public String getStorType() {
        return storType;
    }

    public void setStorType(String storType) {
        this.storType = storType;
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



}
