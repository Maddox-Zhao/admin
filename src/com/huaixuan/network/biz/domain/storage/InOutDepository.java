package com.huaixuan.network.biz.domain.storage;

import java.util.Date;
import java.util.List;

import com.huaixuan.network.biz.domain.BaseObject;
import com.huaixuan.network.biz.domain.trade.TradePackage;

public class InOutDepository extends BaseObject implements Comparable<InOutDepository>{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String code;
    private String name;
    private String type;
    private long amount;
    private Date gmtDate;
    private String attrs;
    private String billNum;
    private long outid;
    private long inid;
    private long leftNum;
	private String relationNum;
	private List<TradePackage>  tradePackageList;                       //订单合并记录集合
	private String depFirstName;
    private long   leftDepNum;                             //剩余一级仓库库存
    private Date gmtModify;
    private String             instanceName;

    private String             goodsInstanceId;
    
    private String             optTimeStart;
    
	private String             optTimeEnd;
	
	private String             depfirstId;
	
	private String             typea;
	
	private String             type1;
	
	private String             type2;
	
	private String             type3;

	public String getTypea() {
		return typea;
	}
	public void setTypea(String typea) {
		this.typea = typea;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	public List<TradePackage> getTradePackageList() {
		return tradePackageList;
	}
	public void setTradePackageList(List<TradePackage> tradePackageList) {
		this.tradePackageList = tradePackageList;
	}
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	public long getLeftDepNum() {
		return leftDepNum;
	}
	public void setLeftDepNum(long leftDepNum) {
		this.leftDepNum = leftDepNum;
	}
	public String getDepFirstName() {
		return depFirstName;
	}
	public void setDepFirstName(String depFirstName) {
		this.depFirstName = depFirstName;
	}
	/**
	 * @return the relationNum
	 */
	public String getRelationNum() {
		return relationNum;
	}
	/**
	 * @param relationNum the relationNum to set
	 */
	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
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
	 * @return the outid
	 */
	public long getOutid() {
		return outid;
	}
	/**
	 * @param outid the outid to set
	 */
	public void setOutid(long outid) {
		this.outid = outid;
	}
	/**
	 * @return the inid
	 */
	public long getInid() {
		return inid;
	}
	/**
	 * @param inid the inid to set
	 */
	public void setInid(long inid) {
		this.inid = inid;
	}
	/**
	 * @return the billNum
	 */
	public String getBillNum() {
		return billNum;
	}
	/**
	 * @param billNum the billNum to set
	 */
	public void setBillNum(String billNum) {
		this.billNum = billNum;
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
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the gmtDate
	 */
	public Date getGmtDate() {
		return gmtDate;
	}
	/**
	 * @param gmtDate the gmtDate to set
	 */
	public void setGmtDate(Date gmtDate) {
		this.gmtDate = gmtDate;
	}

	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	public String getGoodsInstanceId() {
		return goodsInstanceId;
	}
	public void setGoodsInstanceId(String goodsInstanceId) {
		this.goodsInstanceId = goodsInstanceId;
	}
	public String getOptTimeStart() {
		return optTimeStart;
	}
	public void setOptTimeStart(String optTimeStart) {
		this.optTimeStart = optTimeStart;
	}
	public String getOptTimeEnd() {
		return optTimeEnd;
	}
	public void setOptTimeEnd(String optTimeEnd) {
		this.optTimeEnd = optTimeEnd;
	}
	public String getDepfirstId() {
		return depfirstId;
	}
	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
	}
	public int compareTo(InOutDepository o) {
        InOutDepository sc = (InOutDepository)o;
        if(this.getGmtDate().equals(sc.getGmtDate())){
        	if(this.getGmtModify().compareTo(sc.getGmtModify()) > 0){
        		return 1;
        	}else{
        		return -1;
        	}
        }else{
        	return this.getGmtDate().compareTo(sc.getGmtDate());
        }
	}
}
