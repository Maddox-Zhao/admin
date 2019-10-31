package com.huaixuan.network.biz.domain.storage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.trade.TradePackage;

/**
 * (bibleUtil auto code generation)
 * @version 3.2.0
 */
public class OutDepository extends BaseObject {

    private static final long   serialVersionUID = 1355638955613104709L;
    //顺风快递坐标位置
    public static final int[][] positionSf       = new int[][] { { 130, 30 }, { 85, 120 },
            { 80, 140 }, { 80, 160 }, { 135, 180 }, { 85, 210 }, { 80, 230 }, { 80, 250 },
            { 150, 290 }                        };
    public static final double  pageWidthSf      = 595;
    public static final double  pageHeightSf     = 421;
    //EMS坐标位置
    public static final int[][] positionEms      = new int[][] { { 110, 55 }, { 105, 125 },
            { 100, 145 }, { 100, 160 }, { 220, 102 }, { 370, 102 }, { 375, 145 }, { 375, 160 },
            { 495, 102 }                        };
    public static final double  pageWidthEms     = 800;
    public static final double  pageHeightEms    = 492;

    /* @property: */
    private long                id;
    /* @property: */
    private String              billNum;
    /* @property: */
    private String              type;
    /* @property: */
    private String              creater;
    private String              modifier;
    /* @property: */
    private Date                gmtCreate;
    /* @property: */
    private Date                gmtModify;
    /* @property: */
    private String              relationNum;
    private List<String>        relationNumList;                       //合并出库订单号集合
    /* @property: */
    private String              status;
    private Date                gmtOutDep;
    private String              remark;
    /*
     * 物流单号
     */
    private String              expressCode;
    /*
     * 物流名称
     */
    private String              expressName;
    /*
     * 支付方式
     */
    private String              payment;
    private String              expressUrl;
    private Double              expressAmount;
    private long                amount;                                 //出库数量
    private String              instanceName;                           //产品名称
    private String              goodCode;                               //产品编码
    private String              attrs;                                  //产品属性
    private long                leftNum;                                //当前库存数量

    private String              financeStatus;

    private Long                depFirstId;

    private String              depFirstName;

    private long                leftDepNum;                             //剩余一级仓库库存

    private String              isWholesale;
    private String              tid;

    private String				expressId;								//物流公司id
    private String				isOutDepositoryPrinted;					//物流单是否打印过
    private String				isExpressPrinted;						//运单是否打印过
    private Double 				actualInventory;						//实际运费
    private Double				goodsWeight;							//商品重量
    private Double 				actualWeight;							//实际重量
    private Double 				castWeight;								//计抛重量
    private String              receiver;                               //收货人

    private List<TradePackage>  tradePackageList;                       //订单合并记录集合

    private Long                handleAdminId;                          //处理出库单的后台人员

    public Long getHandleAdminId() {
        return handleAdminId;
    }

    public void setHandleAdminId(Long handleAdminId) {
        this.handleAdminId = handleAdminId;
    }
    
    public String getRelationNumStr(){
    	String result = "";int i = 0;
    	if(relationNumList != null && relationNumList.size() > 0){
    		for(String obj:relationNumList){
    			result += obj;
    			if(i++ != relationNumList.size()-1){
    				result += ",";
    			}
    		}
    	}
    	return result;
    }
	public List<String> getRelationNumList() {
		return relationNumList;
	}

	public void setRelationNumList(List<String> relationNumList) {
		this.relationNumList = relationNumList;
	}

	public Double getActualInventory() {
		return actualInventory;
	}

	public void setActualInventory(Double actualInventory) {
		this.actualInventory = actualInventory;
	}

	public long getLeftDepNum() {
        return leftDepNum;
    }

    public void setLeftDepNum(long leftDepNum) {
        this.leftDepNum = leftDepNum;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the leftNum
     */
    public long getLeftNum() {
        return leftNum;
    }

    /**
     * @param leftNum the leftNum to set
     */
    public void setLeftNum(long leftNum) {
        this.leftNum = leftNum;
    }

    /**
     * @return the attrs
     */
    public String getAttrs() {
        return attrs;
    }

    /**
     * @param attrs the attrs to set
     */
    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    /**
     * @return the goodCode
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * @param goodCode the goodCode to set
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return the instanceName
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * @param instanceName the instanceName to set
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public Double getExpressAmount() {
        return expressAmount;
    }

    public void setExpressAmount(Double expressAmount) {
        this.expressAmount = expressAmount;
    }

    public String getExpressUrl() {
        return expressUrl;
    }

    public void setExpressUrl(String expressUrl) {
        this.expressUrl = expressUrl;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Date getGmtOutDep() {
        return gmtOutDep;
    }

    public void setGmtOutDep(Date gmtOutDep) {
        this.gmtOutDep = gmtOutDep;
    }

    /* Default constructor - creates a new instance with no values set. */
    public OutDepository() {
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
    public void setBillNum(String obj) {
        this.billNum = obj;
    }

    /* @model: */
    public String getBillNum() {
        return this.billNum;
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
    public void setCreater(String obj) {
        this.creater = obj;
    }

    /* @model: */
    public String getCreater() {
        return this.creater;
    }

    /**
     * @return the modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param modifier the modifier to set
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
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

    /* @model: */
    public void setRelationNum(String obj) {
        this.relationNum = obj;
    }

    /* @model: */
    public String getRelationNum() {
        return this.relationNum;
    }

    /* @model: */
    public void setStatus(String obj) {
        this.status = obj;
    }

    /* @model: */
    public String getStatus() {
        return this.status;
    }

    /*{@inheritDoc}*/
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutDepository)) {
            return false;
        }
        final OutDepository outdepository = (OutDepository) o;
        return this.hashCode() == outdepository.hashCode();
    }

    /*{@inheritDoc}*/
    public int hashCode() {
        return (type != null ? type.hashCode() : 0);
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

    public String getDepFirstName() {
        return depFirstName;
    }

    public void setDepFirstName(String depFirstName) {
        this.depFirstName = depFirstName;
    }

    public Long getDepFirstId() {
        return depFirstId;
    }

    public void setDepFirstId(Long depFirstId) {
        this.depFirstId = depFirstId;
    }

    /*{@inheritDoc}*/
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("id",
            this.id).append("billNum", this.billNum).append("type", this.type).append("creater",
            this.creater).append("modifier", this.modifier).append("gmtCreate", this.gmtCreate)
            .append("gmtModify", this.gmtModify).append("relationNum", this.relationNum).append(
                "status", this.status).append("financeStatus", this.financeStatus);
        return sb.toString();
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

	public String getIsOutDepositoryPrinted() {
		return isOutDepositoryPrinted;
	}

	public void setIsOutDepositoryPrinted(String isOutDepositoryPrinted) {
		this.isOutDepositoryPrinted = isOutDepositoryPrinted;
	}

	public String getIsExpressPrinted() {
		return isExpressPrinted;
	}

	public void setIsExpressPrinted(String isExpressPrinted) {
		this.isExpressPrinted = isExpressPrinted;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public Double getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Double goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public Double getCastWeight() {
		return castWeight;
	}

	public void setCastWeight(Double castWeight) {
		this.castWeight = castWeight;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public List<TradePackage> getTradePackageList() {
		return tradePackageList;
	}

	public void setTradePackageList(List<TradePackage> tradePackageList) {
		this.tradePackageList = tradePackageList;
	}

}
