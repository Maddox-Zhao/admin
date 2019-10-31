/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwy
 */
public enum EnumInOutThreeDepository {
	
	
    OUT_SALES("out_sales","出库"),
    OUT_DAMAGED("out_damaged","出库"),
    OUT_SHOPPING("out_shopping","出库"),
    OUT_CHECK_DAMAGED("out_check_damaged","出库"),
    OUT_SALES_CHANGE("out_sales_change","出库"),
    OUT_STORAGE_REFUND("out_storage_refund","出库"),
    OUT_MOVE_STORAGE("out_move_storage","出库"),
    OUT_BORROW_STORAGE("out_borrow_storage","出库"),
    OUT_BACK_STORAGE("out_back_storage","出库"),
    IN_SALES_REFUND("in_sales_refund","入库"),
    IN_SHOPPING("in_shopping","入库"),
    IN_SALES_CHANGE("in_sales_change","入库"),
	IN_CHECK_MORE("in_check_more","入库"),
	IN_ZERO_CHECK_MORE("in_zero_check_more","入库"),
    IN_MOVE_STORAGE("in_move_storage","入库"),
    IN_BORROW_STORAGE("in_borrow_storage","入库"),
    IN_BACK_STORAGE("in_back_storage","入库");


    private String code;

    private String name;

    EnumInOutThreeDepository(String code,String name){
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
        enumDataMap.put(OUT_CHECK_DAMAGED.getKey(), OUT_CHECK_DAMAGED.getValue());
        enumDataMap.put(OUT_SALES_CHANGE.getKey(), OUT_SALES_CHANGE.getValue());
        enumDataMap.put(OUT_STORAGE_REFUND.getKey(), OUT_STORAGE_REFUND.getValue());
        enumDataMap.put(OUT_MOVE_STORAGE.getKey(), OUT_MOVE_STORAGE.getValue());
        enumDataMap.put(OUT_BORROW_STORAGE.getKey(), OUT_BORROW_STORAGE.getValue());
        enumDataMap.put(OUT_BACK_STORAGE.getKey(), OUT_BACK_STORAGE.getValue());
        enumDataMap.put(IN_SALES_REFUND.getKey(), IN_SALES_REFUND.getValue());
        enumDataMap.put(IN_SHOPPING.getKey(), IN_SHOPPING.getValue());
        enumDataMap.put(IN_SALES_CHANGE.getKey(), IN_SALES_CHANGE.getValue());
        enumDataMap.put(IN_CHECK_MORE.getKey(), IN_CHECK_MORE.getValue());
        enumDataMap.put(IN_ZERO_CHECK_MORE.getKey(), IN_ZERO_CHECK_MORE.getValue());
        enumDataMap.put(IN_MOVE_STORAGE.getKey(), IN_MOVE_STORAGE.getValue());
        enumDataMap.put(IN_BORROW_STORAGE.getKey(), IN_BORROW_STORAGE.getValue());
        enumDataMap.put(IN_BACK_STORAGE.getKey(), IN_BACK_STORAGE.getValue());
        return enumDataMap;
    }
}
