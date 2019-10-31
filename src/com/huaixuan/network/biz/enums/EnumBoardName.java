package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumBoardName {

    HOT_SALE("hot_sale","热销榜"),
    HIGH_POPULAR("high_popular","人气榜"),
    CUT_PRICE("cut_price","特价商品"),
    BROWSE_HISTORY("browse_history","历史浏览"),
    PROPERLY_LIKE("properly_like","猜你喜欢"),
    BUY_OTHERS("buy_others","买了还买");
    private String code;
    private String name;
    EnumBoardName(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
    
    public static Map<String , String> toMap(){
        Map<String , String> enumDataMap=new HashMap<String, String>();
        enumDataMap.put(HOT_SALE.getKey(), HOT_SALE.getValue());
        enumDataMap.put(HIGH_POPULAR.getKey(), HIGH_POPULAR.getValue());
        enumDataMap.put(CUT_PRICE.getKey(), CUT_PRICE.getValue());
        enumDataMap.put(BROWSE_HISTORY.getKey(), BROWSE_HISTORY.getValue());
        enumDataMap.put(PROPERLY_LIKE.getKey(), PROPERLY_LIKE.getValue());
        enumDataMap.put(BUY_OTHERS.getKey(), BUY_OTHERS.getValue());
        return enumDataMap;
    }
}
