package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 公告分类
 * @author chenyan 2010/04/01
 *
 */
public enum EnumNoticeType {
    NEWS("news","本网动态"),
    //fangqing modified 2010/10/11
  /*  NEW_PRODUCET("newproduct","新品上架"),
    DOWN_PRODUCT("downproduct","下架产品"),
    ADD_PRODUCT("addproduct","补货上架");*/
    PRODUCT("pruduct","产品公告"),
    INDUSTRY("industry","行业新闻"),
    //added by chenhang 2010/10/25 增加公告类型 start
    ADVERTISESUPPORT("advertisesupport","广告营销支持"),
    SHOPMODIFYSUPPORT("shopmodifysupport","网店装修支持"),
    PRODUCTTRAINSUPPORT("producttrainsupport","产品培训支持"),
    //added by chenhang 2010/10/25 增加公告类型 end
    //added by chenyan 2011/01/20 start
    PRODUCTUP("productup","补货上架"),
    PRODUCTDOWN("productdown","产品下架");
    //added by chenyan 2011/01/20 end
    
    private String code;

    private String name;

    EnumNoticeType(String code,String name){
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
        enumDataMap.put(NEWS.getKey(), NEWS.getValue());
        enumDataMap.put(PRODUCT.getKey(),PRODUCT.getValue());
        enumDataMap.put(INDUSTRY.getKey(),INDUSTRY.getValue());
      //added by chenhang 2010/10/25 增加公告类型 start
        enumDataMap.put(ADVERTISESUPPORT.getKey(), ADVERTISESUPPORT.getValue());
        enumDataMap.put(SHOPMODIFYSUPPORT.getKey(),SHOPMODIFYSUPPORT.getValue());
        enumDataMap.put(PRODUCTTRAINSUPPORT.getKey(),PRODUCTTRAINSUPPORT.getValue());
        enumDataMap.put(PRODUCTUP.getKey(),PRODUCTUP.getValue());
        enumDataMap.put(PRODUCTDOWN.getKey(),PRODUCTDOWN.getValue());
      //added by chenhang 2010/10/25 增加公告类型 end
     /*   enumDataMap.put(NEW_PRODUCET.getKey(), NEW_PRODUCET.getValue());
        enumDataMap.put(DOWN_PRODUCT.getKey(), DOWN_PRODUCT.getValue());
        enumDataMap.put(ADD_PRODUCT.getKey(), ADD_PRODUCT.getValue());*/

        return enumDataMap;
    }
}
