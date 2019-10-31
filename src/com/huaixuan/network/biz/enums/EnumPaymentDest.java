/**
 *
 */
package com.huaixuan.network.biz.enums;


/**
 * 支付目的
 *
 * @author tao.wangt
 * @version $Id: EnumPaymentDest.java,v 0.1 2009-8-5 上午11:26:55 tao.wangt Exp $
 */
public enum EnumPaymentDest {
	PAYMENT_DEST_PAY("PAY", 1, "支付"), PAYMENT_DEST_DEPOSIT("DEPOSIT", 2, "充值"), PAYMENT_DEST_FOREGIFT(
			"FOREGIFT", 3, "保证金充值");
	private String name;
	private int value;
	private String message;

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * 根据值获取支付目的枚举
	 * <ul>
	 * <li>1.EnumPaymentDest.PAYMENT_DEST_PAY(支付)
	 * <li>2.EnumPaymentDest.PAYMENT_DEST_DEPOSIT(充值)
	 * <li>3.EnumPaymentDest.PAYMENT_DEST_FOREGIFT(保证金充值)
	 * <li>default:null(默认值)
	 *
	 * @param value
	 * @return
	 */
	public static EnumPaymentDest getEnumPaymentDestByValue(int value) {
		switch (value) {
		case 1:
			return EnumPaymentDest.PAYMENT_DEST_PAY;
		case 2:
			return EnumPaymentDest.PAYMENT_DEST_DEPOSIT;
		case 3:
			return EnumPaymentDest.PAYMENT_DEST_FOREGIFT;
		default:
			return null;
		}
	}

	/*
	 * public static void main(String[] args) { EnumPaymentDest enumPaymentDest
	 * = getEnumPaymentDestByValue(2); if (enumPaymentDest.getValue() == 2) {
	 * System.out.println("enum name is:" + enumPaymentDest.getName()); } }
	 */

	EnumPaymentDest(String name, int value, String message) {
		this.name = name;
		this.value = value;
		this.message = message;
	}
}
