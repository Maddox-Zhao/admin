package com.huaixuan.network.biz.query;

import java.util.List;

public class OutDepositoryQuery {
	private String printExpressId;
	private String billNum;
	private String type;
	private String optTimeStart;
	private String optTimeEnd;
	private String creater;
	private String status;
	private String depfirstId;
	private String relationNum;
	private String outDepTimeStart;
	private String outDepTimeEnd;
	private String expressId;
	private String gmtCreateStart;
	private String gmtCreateEnd;
	private String isOutDepositoryPrinted;
	private String isExpressPrinted;
	private List<Long> depfirstIds;
	private Long handleAdminId; //处理出库单的后台人员
	
	public Long getHandleAdminId() {
        return handleAdminId;
    }

    public void setHandleAdminId(Long handleAdminId) {
        this.handleAdminId = handleAdminId;
    }

    public String getPrintExpressId() {
		return printExpressId;
	}

	public void setPrintExpressId(String printExpressId) {
		this.printExpressId = printExpressId;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepfirstId() {
		return depfirstId;
	}

	public void setDepfirstId(String depfirstId) {
		this.depfirstId = depfirstId;
	}

	public String getRelationNum() {
		return relationNum;
	}

	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
	}

	public String getOutDepTimeStart() {
		return outDepTimeStart;
	}

	public void setOutDepTimeStart(String outDepTimeStart) {
		this.outDepTimeStart = outDepTimeStart;
	}

	public String getOutDepTimeEnd() {
		return outDepTimeEnd;
	}

	public void setOutDepTimeEnd(String outDepTimeEnd) {
		this.outDepTimeEnd = outDepTimeEnd;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getGmtCreateStart() {
		return gmtCreateStart;
	}

	public void setGmtCreateStart(String gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}

	public String getGmtCreateEnd() {
		return gmtCreateEnd;
	}

	public void setGmtCreateEnd(String gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
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

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

}
