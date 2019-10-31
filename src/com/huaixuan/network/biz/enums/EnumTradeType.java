/**
 *
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhang 2011-5-13
 */
public enum EnumTradeType {
	trade_type_0("前台下单", 0), trade_type_1("后台下单", 1), trade_type_2("拍拍订单", 2), trade_type_3(
			"淘宝同步订单", 3), trade_type_4("淘宝分销订单", 4), trade_type_5("邮易购订单", 5), trade_type_6(
			"乐酷天订单", 6), trade_type_7("中邮快购", 7), trade_type_8("团购", 8), trade_type_9(
			"校园代购", 9), trade_type_10("其他", 10);
	private Integer code;
	private String name;

	EnumTradeType(String name, Integer code) {
		this.code = code;
		this.name = name;
	}

	public Integer getValue() {
		return this.code;
	}

	public String getKey() {
		return this.name;
	}

	public static Map<String, Integer> toMap() {
		Map<String, Integer> enumDataMap = new HashMap<String, Integer>();
		enumDataMap.put(trade_type_0.getKey(), trade_type_0.getValue());
		enumDataMap.put(trade_type_1.getKey(), trade_type_1.getValue());
		enumDataMap.put(trade_type_2.getKey(), trade_type_2.getValue());
		enumDataMap.put(trade_type_3.getKey(), trade_type_3.getValue());
		enumDataMap.put(trade_type_4.getKey(), trade_type_4.getValue());
		enumDataMap.put(trade_type_5.getKey(), trade_type_5.getValue());
		enumDataMap.put(trade_type_6.getKey(), trade_type_6.getValue());
		enumDataMap.put(trade_type_7.getKey(), trade_type_7.getValue());
		enumDataMap.put(trade_type_8.getKey(), trade_type_8.getValue());
		enumDataMap.put(trade_type_9.getKey(), trade_type_9.getValue());
		enumDataMap.put(trade_type_10.getKey(), trade_type_10.getValue());
		return enumDataMap;
	}

	public static Map<String, EnumTradeType> codePerssion = new HashMap<String, EnumTradeType>();

	static {
		for (EnumTradeType enumTemp : EnumTradeType.values()) {
			codePerssion.put(enumTemp.getKey(), enumTemp);
		}
	}

	public static Boolean findPermission(String name) {

		if (null != codePerssion.get(name)) {
			return true;
		} else {
			return false;
		}
	};
}
