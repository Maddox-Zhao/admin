/**
 * @Title: RefundDetailSearchQuery.java
 * @Package com.huaixuan.network.biz.domain.stock.query
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ����07:53:35
 * @version V1.0
 */
package com.huaixuan.network.biz.domain.stock.query;

import java.util.List;

/**
 * @ClassName: RefundDetailSearchQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-7 ����07:53:35
 *
 */
public class RefundDetailSearchQuery {

	/**
	 * ��Ʒ����
	 */
	private String instanceCode;

	/**
	 * ��Ʒ����
	 */
	private String instanceName;

	/**
	 * �ֿ�id
	 */
	private String depFirstId;

	/**
	 * ��Ӧ��id
	 */
	private String supplierId;

	/**
	 * �˻�������ʼʱ��
	 */
	private String startTime;

	/**
	 * �˻���������ʱ��
	 */
	private String endTime;

	/**
	 * һ���ֿ�ids
	 */
	private List<Long> depfirstIds;

	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDepFirstId() {
		return depFirstId;
	}

	public void setDepFirstId(String depFirstId) {
		this.depFirstId = depFirstId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Long> getDepfirstIds() {
		return depfirstIds;
	}

	public void setDepfirstIds(List<Long> depfirstIds) {
		this.depfirstIds = depfirstIds;
	}
}
