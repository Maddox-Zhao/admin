package com.huaixuan.network.biz.query;

import java.util.List;

public class RefundQuery {
	private String tid;
	private String buyNick;
	private String status;
	private String type;
	private String gmtCreateStart;
	private String gmtCreateEnd;
	private List<Long> depfirstIds;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getBuyNick() {
		return buyNick;
	}

	public void setBuyNick(String buyNick) {
		this.buyNick = buyNick;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

}
