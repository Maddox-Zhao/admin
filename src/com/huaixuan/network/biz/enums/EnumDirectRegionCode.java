package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 直辖市
 * @author Administrator
 *
 */
public enum EnumDirectRegionCode {
	
	BEIJING("110000", "北京"),
	SHANGHAI("310000", "上海"), 
	TIANJIN("120000", "天津"), 
	CHONGQING("500000","重庆");
	
	private String code;

	private String name;
	
	EnumDirectRegionCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private static Map<String,EnumDirectRegionCode> regionMap = new HashMap<String, EnumDirectRegionCode>();
    
    static {
        for (EnumDirectRegionCode enumRegion : EnumDirectRegionCode.values()) {
        	regionMap.put(enumRegion.getCode(), enumRegion);
        }
    }
    
    public static EnumDirectRegionCode findRegion(String code) {
        return regionMap.get(code);
    };
}
