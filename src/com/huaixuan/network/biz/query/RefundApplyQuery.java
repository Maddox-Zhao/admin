package com.huaixuan.network.biz.query;

public class RefundApplyQuery {
	private String tid;
	private String status;
	private String dateStart;
	private String dateEnd;
	private Long buyUserId;
	private String buyNick;
	private Integer startRow;
	private Integer endRow;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Long getBuyUserId() {
		return buyUserId;
	}

	public void setBuyUserId(Long buyUserId) {
		this.buyUserId = buyUserId;
	}

	public String getBuyNick() {
		return buyNick;
	}

	public void setBuyNick(String buyNick) {
		this.buyNick = buyNick;
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

}
