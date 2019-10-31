package com.huaixuan.network.biz.domain.stock.query;

import java.util.List;
/**
 * @Title: ShoppingListQuery.java
 * @Package
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ����03:46:27
 * @version V1.0
 */

/**
 * @ClassName: ShoppingListQuery
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 ����03:46:27
 *
 */
public class ShoppingListQuery {

	/**
	 *	�ɹ�������
	 */
	private String shoppingNum;

	/**
	 * ��Ӧ��id
	 */
	private String supplierId;

	/**
	 *	��Ӧ��
	 */
	private String supplierName;

	/**
	 *	��ͬ���
	 */
	private String primitiveNum;

	/**
	 *	������
	 */
	private String creater;

	/**
	 *	�������
	 */
	private String storType;

	/**
	 *	����״̬
	 */
	private String financeStatus;

	/**
	 *	����״̬
	 */
	private String status;

	/**
	 *	��������
	 */
	private String isWholesale;

	/**
	 *	�ɹ���ʼʱ��
	 */
	private String shoppingTimeStart;

	/**
	 *	�ɹ�����ʱ��
	 */
	private String shoppingTimeEnd;

	/**
	 *	Ԥ�ڵ�����ʼʱ��
	 */
	private String arriveTimeStart;

	/**
	 *	Ԥ�ڵ�������ʱ��
	 */
	private String arriveTimeEnd;

	private List<String> sameTeamUsers;

	public List<String> getSameTeamUsers() {
		return sameTeamUsers;
	}

	public void setSameTeamUsers(List<String> sameTeamUsers) {
		this.sameTeamUsers = sameTeamUsers;
	}

	public String getShoppingNum() {
		return shoppingNum;
	}

	public void setShoppingNum(String shoppingNum) {
		this.shoppingNum = shoppingNum;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPrimitiveNum() {
		return primitiveNum;
	}

	public void setPrimitiveNum(String primitiveNum) {
		this.primitiveNum = primitiveNum;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getStorType() {
		return storType;
	}

	public void setStorType(String storType) {
		this.storType = storType;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsWholesale() {
		return isWholesale;
	}

	public void setIsWholesale(String isWholesale) {
		this.isWholesale = isWholesale;
	}

	public String getShoppingTimeStart() {
		return shoppingTimeStart;
	}

	public void setShoppingTimeStart(String shoppingTimeStart) {
		this.shoppingTimeStart = shoppingTimeStart;
	}

	public String getShoppingTimeEnd() {
		return shoppingTimeEnd;
	}

	public void setShoppingTimeEnd(String shoppingTimeEnd) {
		this.shoppingTimeEnd = shoppingTimeEnd;
	}

	public String getArriveTimeStart() {
		return arriveTimeStart;
	}

	public void setArriveTimeStart(String arriveTimeStart) {
		this.arriveTimeStart = arriveTimeStart;
	}

	public String getArriveTimeEnd() {
		return arriveTimeEnd;
	}

	public void setArriveTimeEnd(String arriveTimeEnd) {
		this.arriveTimeEnd = arriveTimeEnd;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}
