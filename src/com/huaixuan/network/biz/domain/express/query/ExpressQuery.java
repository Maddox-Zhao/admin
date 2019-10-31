package com.huaixuan.network.biz.domain.express.query;

import com.huaixuan.network.biz.domain.BaseObject;

/**
 * 物流公司查询类
 *
 * @version 3.2.0
 */
public class ExpressQuery extends BaseObject {

	private static final long serialVersionUID = 2745311035992578803L;
	/* @property: */
	private String createTimeStart;
	/* @property: */
	private String createTimeEnd;
	/* @property: */
	private String expressName;
	//
	private String expressCode;
	/* @property: */
	private String expressDesc;
	/* @property: */
	private String expressUrl;
	/* @property: */
	private String status;

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
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

	public String getExpressDesc() {
		return expressDesc;
	}

	public void setExpressDesc(String expressDesc) {
		this.expressDesc = expressDesc;
	}

	public String getExpressUrl() {
		return expressUrl;
	}

	public void setExpressUrl(String expressUrl) {
		this.expressUrl = expressUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
