package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口名称
 *
 * @author jinxx
 */
public enum EnumTaobaoInterfaceName {
    TAOBAOPUBLISHGOODS("taobao_publishgoods","淘宝商品发布"),PAIPAISTOCKSYNC("taobao_stocksync","淘宝库存同步");

    private String code;

    private String name;

    EnumTaobaoInterfaceName(String code,String name){
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
        enumDataMap.put(TAOBAOPUBLISHGOODS.getKey(), TAOBAOPUBLISHGOODS.getValue());
        enumDataMap.put(PAIPAISTOCKSYNC.getKey(), PAIPAISTOCKSYNC.getValue());
        return enumDataMap;
    }
}
