package com.huaixuan.network.biz.query;

import java.util.List;

public class InDepositoryQuery {
	private String billNum;
	private String type;
	private String optTimeStart;
	private String optTimeEnd;
	private String creater;
	private String status;
	private String relationNum;
	private String inDepTimeStart;
	private String inDepTimeEnd;
	private Long depFirstId;
	private String supplierId;
	private Integer startRow;
	private Integer endRow;
	private List<Long> depfirstIds;

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

	public String getRelationNum() {
		return relationNum;
	}

	public void setRelationNum(String relationNum) {
		this.relationNum = relationNum;
	}

	public String getInDepTimeStart() {
		return inDepTimeStart;
	}

	public void setInDepTimeStart(String inDepTimeStart) {
		this.inDepTimeStart = inDepTimeStart;
	}

	public String getInDepTimeEnd() {
		return inDepTimeEnd;
	}

	public void setInDepTimeEnd(String inDepTimeEnd) {
		this.inDepTimeEnd = inDepTimeEnd;
	}

	public Long getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(Long depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

}
