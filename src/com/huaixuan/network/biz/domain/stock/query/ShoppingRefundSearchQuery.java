/**
 * @Title: ShoppingRefundSearchQuery.java
 * @Package com.huaixuan.network.biz.domain.stock.query
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ÏÂÎç04:59:46
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.stock.query;

import java.util.List;

/**
 * @ClassName: ShoppingRefundSearchQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ÏÂÎç04:59:46
 *
 */
public class ShoppingRefundSearchQuery {

	private String refNum;

	private String type;

	private String supplierName;

	private String supplierId;

	private String creater;

	private String refTimeStart;

	private String refTimeEnd;

	private List<Long> depfirstIds;

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

	public String getRefNum() {
		return refNum;
	}

	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getRefTimeStart() {
		return refTimeStart;
	}

	public void setRefTimeStart(String refTimeStart) {
		this.refTimeStart = refTimeStart;
	}

	public String getRefTimeEnd() {
		return refTimeEnd;
	}

	public void setRefTimeEnd(String refTimeEnd) {
		this.refTimeEnd = refTimeEnd;
	}


}
