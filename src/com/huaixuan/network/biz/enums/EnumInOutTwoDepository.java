/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwy
 */
public enum EnumInOutTwoDepository {

    IN_TYPE("in_sales_refund,in_shopping,in_sales_change,in_check_more,in_zero_check_more,in_move_storage,in_borrow_storage,in_back_storage","入库"),
    OUT_TYPE("out_sales,out_damaged,out_shopping,out_check_damaged,out_sales_change,out_storage_refund,out_move_storage,out_borrow_storage,out_back_storage","出库");



    private String code;

    private String name;

    EnumInOutTwoDepository(String code,String name){
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
        enumDataMap.put(IN_TYPE.getKey(), IN_TYPE.getValue());
        enumDataMap.put(OUT_TYPE.getKey(), OUT_TYPE.getValue());
        return enumDataMap;
    }
}
