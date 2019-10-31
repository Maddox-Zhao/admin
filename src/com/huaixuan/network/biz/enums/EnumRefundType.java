/**
 * created since 2009-8-4
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shengyong
 * @version $Id: EnumRefundType.java,v 0.1 2009-8-4 下午04:38:52 shengyong Exp $
 */
public enum EnumRefundType {
	REFUND_GOODS("g", "部分退货"), CHANGE_GOODS("c", "换货"), REFUND_ALL_GOODS("a",
			"全部退货"), REFUND_FEE("f", "退款");
	private String code;

	private String name;

	EnumRefundType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getKey() {
		return this.code;
	}

	public String getValue() {
		return this.name;
	}

	public static Map<String , String> toMap(){
        Map<String , String> enumDataMap=new HashMap<String, String>();
        enumDataMap.put(REFUND_GOODS.getKey(), REFUND_GOODS.getValue());
        enumDataMap.put(CHANGE_GOODS.getKey(), CHANGE_GOODS.getValue());
        enumDataMap.put(REFUND_ALL_GOODS.getKey(), REFUND_ALL_GOODS.getValue());
        enumDataMap.put(REFUND_FEE.getKey(), REFUND_FEE.getValue());
        return enumDataMap;
    }
}
