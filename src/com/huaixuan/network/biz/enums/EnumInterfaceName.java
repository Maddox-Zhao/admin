package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口名称
 *
 * @author zhangwy
 */
public enum EnumInterfaceName {
    PAIPAIPUBLISHGOODS("paipai_publishgoods","拍拍商品发布"),PAIPAISTOCKSYNC("paipai_stocksync","拍拍库存同步"),
    TAOBAOFENXIAOSYNC("taobaofenxiao_stocksync","淘宝分销库存同步"),TAOBAOSTOCKSYNC("taobao_stocksync","淘宝库存同步"),
    TAOBAOPUBLISHGOODS("taobao_publishgoods","淘宝商品发布");

    private String code;

    private String name;

    EnumInterfaceName(String code,String name){
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
        enumDataMap.put(PAIPAIPUBLISHGOODS.getKey(), PAIPAIPUBLISHGOODS.getValue());
        enumDataMap.put(PAIPAISTOCKSYNC.getKey(), PAIPAISTOCKSYNC.getValue());
        enumDataMap.put(TAOBAOSTOCKSYNC.getKey(), TAOBAOSTOCKSYNC.getValue());
        return enumDataMap;
    }
}
