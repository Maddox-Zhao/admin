/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums.hy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songfy
 */
public enum EnumStepLocation {

	SHANGHAI_FENKU("101","上海分库"),
	HONGKONG("102","香港总仓"),
	SHANGHAI_SHANGXI("201","上海陕西南路店"),
	SHANGHAI_ZIZHONG("202","上海自忠路店"),
	SHANGHAI_HUANGJINCHENGDAO("203","上海黄金城道店"),
	TAOBAO("301","淘宝店"),
	BUYER_1("401","采购买手1"),
	BUYER_2("402","采购买手2"),
	BUYER_3("403","采购买手3"),
	BUYER_4("404","采购买手4");
	
	
    private String code;

    private String name;

    EnumStepLocation(String code,String name){
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
        enumDataMap.put(SHANGHAI_FENKU.getKey(), SHANGHAI_FENKU.getValue());
        enumDataMap.put(HONGKONG.getKey(), HONGKONG.getValue());
        enumDataMap.put(SHANGHAI_SHANGXI.getKey(), SHANGHAI_SHANGXI.getValue());
        enumDataMap.put(SHANGHAI_ZIZHONG.getKey(), SHANGHAI_ZIZHONG.getValue());
        enumDataMap.put(SHANGHAI_HUANGJINCHENGDAO.getKey(), SHANGHAI_HUANGJINCHENGDAO.getValue());
        enumDataMap.put(TAOBAO.getKey(),TAOBAO.getValue());
        enumDataMap.put(BUYER_1.getKey(), BUYER_1.getValue());
        enumDataMap.put(BUYER_2.getKey(), BUYER_2.getValue());
        enumDataMap.put(BUYER_3.getKey(), BUYER_3.getValue());
        enumDataMap.put(BUYER_4.getKey(), BUYER_4.getValue());
        
        return enumDataMap;
    }
    
    
}
