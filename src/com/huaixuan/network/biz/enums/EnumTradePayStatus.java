package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易付款状态
 * @author yingmu
 * @version $Id: EnumTradePayStatus.java,v 0.1 2009-3-23 下午05:09:19 Administrator Exp $
 */
public enum EnumTradePayStatus {
    NO_PAY("no_pay","未付款"), 
    PAID("paid","已付款");
    private String code;
    private String name;
    EnumTradePayStatus(String code,String name){
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
    	Map<String,String> enumDataMap = new HashMap<String,String>();
        enumDataMap.put(NO_PAY.getKey(), NO_PAY.getValue());
        enumDataMap.put(PAID.getKey(), PAID.getValue());
        return enumDataMap;
    }
}
