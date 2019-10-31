package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于代销会员申请提现的状态
 * @author chenyan 2009/10/13
 *
 */
public enum EnumCashStatus {

	INIT("init", "申请中"),
    SUCCESS("success","申请成功"),
	FAIL("fail","申请退回");
	
    private String code;
    private String name;

    EnumCashStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }
    
    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(INIT.getKey(), INIT.getValue());
        enumDataMap.put(SUCCESS.getKey(), SUCCESS.getValue());
        enumDataMap.put(FAIL.getKey(), FAIL.getValue());
        return enumDataMap;
    }
}
