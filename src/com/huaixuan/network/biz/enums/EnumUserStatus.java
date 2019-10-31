package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台用户状态
 * 
 * @author zhengwei
 */
public enum EnumUserStatus {
	APPLYING(0, "申请中"), USING(1, "正常使用"), FREEZING(2, "冻结中");

	private static Map<Integer, String> map = new HashMap<Integer, String>();
	static {
		for (EnumUserStatus us : EnumUserStatus.values()) {
			map.put(us.getValue(), us.getDesc());
		}
	}

	private EnumUserStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private int value;
	private String desc;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static boolean isUsing(int status) {
		return USING.getValue() == status;
	}

	public static boolean isApplying(int status) {
		return APPLYING.getValue() == status;
	}

	public static boolean isFreezing(int status) {
		return FREEZING.getValue() == status;
	}

	public static Map<Integer, String> toMap() {
		return map;
	}
}
