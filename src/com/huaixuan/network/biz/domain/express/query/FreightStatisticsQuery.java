/**
 * @Title: FreightStatisticsQuery.java
 * @Package com.huaixuan.network.biz.domain.express.query
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-17 ÉÏÎç09:46:21
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.express.query;

import java.util.List;

/**
 * @ClassName: FreightStatisticsQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-17 ÉÏÎç09:46:21
 *
 */
public class FreightStatisticsQuery {

	private String userName;

	private String tid;

	private String depFirstId;

	private List<Long> depfirstIds;

	private String expressId;

	private String deliveryTimeStart;

	private String deliveryTimeEnd;

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(String depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	public String getDeliveryTimeEnd() {
		return deliveryTimeEnd;
	}

	public void setDeliveryTimeEnd(String deliveryTimeEnd) {
		this.deliveryTimeEnd = deliveryTimeEnd;
	}



}
