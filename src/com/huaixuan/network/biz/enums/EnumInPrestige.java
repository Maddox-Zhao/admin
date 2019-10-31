package com.huaixuan.network.biz.enums;

import java.util.Map;
import java.util.TreeMap;

public enum EnumInPrestige
{
	ZERO("p", "普通VIP"), ONESTAR("y", "一级VIP"), TWOSTAR("e", "二级VIP"), THREESTAR(
			"s", "三级VIP"),FOUR("pk","普通客户");

	private String code;

	private String name;

	EnumInPrestige(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public String getKey()
	{
		return this.code;
	}

	public String getValue()
	{
		return this.name;
	}

	public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new TreeMap<String, String>();
        enumDataMap.put(FOUR.getKey(), FOUR.getValue());
        enumDataMap.put(ZERO.getKey(), ZERO.getValue());
        enumDataMap.put(ONESTAR.getKey(), ONESTAR.getValue());
        enumDataMap.put(TWOSTAR.getKey(), TWOSTAR.getValue());
        enumDataMap.put(THREESTAR.getKey(), THREESTAR.getValue());
        return enumDataMap;
    
}
}
