package com.huaixuan.network.biz.enums.hy;

import java.util.Map;
import java.util.TreeMap;

/**
 *2012-6-13 下午03:37:56
 *Mr_Yang 支付方式
 */
public enum EnumPayment
{
	PAYMENT_TYPE_YINLIAN(1,"银联"),
	PAYMENT_TYPE_XIANJIN(2,"现金"),
	PAYMENT_TYPE_VISA(3,"VISA"),
	PAYMENT_TYPE_MASTERCARD(4,"MASTER CARD"),
	PAYMENT_TYPE_ZHIPIAO(5,"支票"),
	PAYMENT_TYPE_HUIKUAN(6,"汇款"),
	PAYMENT_TYPE_ZHIFUBAO(7,"支付宝");

	private long key;
	private String value;
	
	EnumPayment(long key,String value)
	{
		this.key = key;
		this.value= value;
	}
	
	public long getKey()
	{
		return key;
	}

	public void setKey(long key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
	public static Map<Long, String> toMap()
	{
		Map<Long, String> map = new TreeMap<Long, String>();
		EnumPayment[] payment = EnumPayment.values();
		for(EnumPayment e : payment)
		{
			map.put(e.getKey(), e.getValue());
		}
		return map;
	}
	
}
