package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 根据订单来源不同，生成的tid有不同的标识
 *
 * @author songfy 2012/02/18
 *
 */
public enum EnumTradeFrom {

	TRADE_FROM_PAIPAI("2", "拍拍生成的订单号"), TRADE_FROM_TAOBAO("3", "淘宝生成的订单号"), 
	TRADE_FROM_FENXIAO("4", "分销生成的订单号"),TRADE_FROM_YYG("5", "邮易购生成的订单号"), TRADE_FROM_LKT(
			"6", "乐酷天生成的订单号"), TRADE_FROM_ZYKG("7", "中邮快购生成的订单号"), TRADE_FROM_TG(
			"8", "团购生成的订单号"), TRADE_FROM_XYDG("9", "校园代购生成的订单号"), TRADE_FROM_OTHERS(
			"10", "其他订单生成的订单号"),TRADE_FROM_XIANHUO("11", "现货订单"),TRADE_FROM_DLHUODONG("13", "大陆活动框订单"),TRADE_FROM_HKHUODONG("14", "香港活动框订单");

	private String code;
	private String name;
	private int index;

	EnumTradeFrom(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getKey() {
		return this.code;
	}

	public String getValue() {
		return this.name;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new TreeMap<String, String>(
				new Comparator() {
					public int compare(Object element1, Object element2) {
						String eOp1 =  element1.toString();
						String eOp2 =  element2.toString();
						if (eOp1 == null) {
							return -1;
						}
						if (eOp2 == null) {
							return 1;
						}

						int ob1Index = 0;
						int ob2Index = 0;
						if ("11".equalsIgnoreCase(eOp1)) {
							ob1Index = 1;
						}
						if ("11".equalsIgnoreCase(eOp2)) {
							ob2Index = 1;
						}
						if ("12".equalsIgnoreCase(eOp1)) {
							ob1Index = 2;
						}
						if ("12".equalsIgnoreCase(eOp2)) {
							ob2Index = 2;
						}
						if ("13".equalsIgnoreCase(eOp1)) {
							ob1Index = 3;
						}
						if ("13".equalsIgnoreCase(eOp2)) {
							ob2Index = 3;
						}
						Integer ob1 = new Integer(ob1Index);
						Integer ob2 = new Integer(ob2Index);

						return ob1.compareTo(ob2);
					}
				});
		enumDataMap.put(TRADE_FROM_XIANHUO.getKey(),
				TRADE_FROM_XIANHUO.getValue());
		enumDataMap.put(TRADE_FROM_DLHUODONG.getKey(),
				TRADE_FROM_DLHUODONG.getValue());
		enumDataMap.put(TRADE_FROM_HKHUODONG.getKey(),
				TRADE_FROM_HKHUODONG.getValue());
		return enumDataMap;
	}
}
