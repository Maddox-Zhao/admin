package com.huaixuan.network.biz.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumRegionCode {
	
	JIANGSU("320000", "江苏"),
	ZHEJIANG("330000", "浙江"), 
	ANHUI("340000", "安徽"), 
	SHANDONG("370000","山东");

	private String code;

	private String name;
	
	EnumRegionCode(String code, String name) {
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
	
    public static List<String> toList() {
        List<String> list = new ArrayList<String>();
        for (EnumRegionCode enumRegion : EnumRegionCode.values()) {
            list.add(enumRegion.getCode());
        }
        return list;
    }

	private static Map<String,EnumRegionCode> regionMap = new HashMap<String, EnumRegionCode>();
    
    static {
        for (EnumRegionCode enumRegion : EnumRegionCode.values()) {
        	regionMap.put(enumRegion.getCode(), enumRegion);
        }
    }
    
    public static EnumRegionCode findRegion(String code) {
        return regionMap.get(code);
    };
	
}
