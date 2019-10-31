package com.huaixuan.network.biz.domain.express.query;

import com.huaixuan.network.biz.domain.BaseObject;

public class MotorTransQuery extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String gmtExpressDateStart;		    //物流开始日期
	private String gmtExpressDateEnd;		    //物流结束日期
	private String tid;							//订单号
	private String expressCode;					//运单号
	private String expressName;					//承运公司
	private String num;							//数量
	private String receiver;					//收货人
	private String payType;						//付款方式
	private String status;						//是否可见


	public String getGmtExpressDateStart() {
		return gmtExpressDateStart;
	}
	public void setGmtExpressDateStart(String gmtExpressDateStart) {
		this.gmtExpressDateStart = gmtExpressDateStart;
	}
	public String getGmtExpressDateEnd() {
		return gmtExpressDateEnd;
	}
	public void setGmtExpressDateEnd(String gmtExpressDateEnd) {
		this.gmtExpressDateEnd = gmtExpressDateEnd;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}