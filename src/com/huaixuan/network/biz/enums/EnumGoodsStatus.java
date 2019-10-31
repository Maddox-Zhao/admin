/**
 * created since Mar 13, 2009
 */
package com.huaixuan.network.biz.enums;

/**
 * @author taobao
 * @version $Id: GoodsStatus.java,v 0.1 Mar 13, 2009 10:22:44 AM taobao Exp $
 */
public enum EnumGoodsStatus {
    ON_SALE("on_sale","销售中"),
    ON_DEPOT("on_depot","仓库中"),
    DELETE("delete","已删除"),
    CANEL_CUT_PRICE("canel_cut_price","2"),// 普通商品
    ACTIVITY_GOODS("activity_goods","3"),// 活动商品
    CUT_PRICE("cut_price","1"),// 特价商品
    IS_AGENT("is_agent","y"); // 特价商品
    private String code;
    private String name;
    EnumGoodsStatus(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }



}
