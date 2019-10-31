/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuchao
 * @version $Id: EnumInDepository.java,v 0.1 2009-7-20 上午11:37:17 liuchao Exp $
 */
public enum EnumOutDepository {

    OUT_SALES("out_sales","销售订单"),
    OUT_DAMAGED("out_damaged","报残"),
    OUT_SHOPPING("out_shopping","采购退货"),
    OUT_STORAGE_REFUND("out_storage_refund","库存退货"),
    OUT_CHECK_DAMAGED("out_check_damaged","盘点耗损"),
    OUT_SALES_CHANGE("out_sales_change","销售换货"),
    OUT_MOVE_STORAGE("out_move_storage","移库出库"),
    OUT_BORROW_STORAGE("out_borrow_storage","外借出库"),
    OUT_BACK_STORAGE("out_back_storage","归还出库");


    private String code;

    private String name;

    EnumOutDepository(String code,String name){
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
        enumDataMap.put(OUT_SALES.getKey(), OUT_SALES.getValue());
        enumDataMap.put(OUT_DAMAGED.getKey(), OUT_DAMAGED.getValue());
        enumDataMap.put(OUT_SHOPPING.getKey(), OUT_SHOPPING.getValue());
        enumDataMap.put(OUT_STORAGE_REFUND.getKey(), OUT_STORAGE_REFUND.getValue());
        enumDataMap.put(OUT_CHECK_DAMAGED.getKey(), OUT_CHECK_DAMAGED.getValue());
        enumDataMap.put(OUT_SALES_CHANGE.getKey(), OUT_SALES_CHANGE.getValue());
        enumDataMap.put(OUT_MOVE_STORAGE.getKey(), OUT_MOVE_STORAGE.getValue());
        enumDataMap.put(OUT_BORROW_STORAGE.getKey(), OUT_BORROW_STORAGE.getValue());
        enumDataMap.put(OUT_BACK_STORAGE.getKey(), OUT_BACK_STORAGE.getValue());
        return enumDataMap;
    }
}
