package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumAgentStatus {
	Is_agent("y","代销商品"),
	No_agent("n","非代销商品");
    private String code;
    private String name;
    EnumAgentStatus(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        enumDataMap.put(Is_agent.getKey(), Is_agent.getValue());
        enumDataMap.put(No_agent.getKey(), No_agent.getValue());

        return enumDataMap;
    }
}
