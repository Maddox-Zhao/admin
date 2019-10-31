/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangwy
 */
public enum EnumInDepository {

    IN_SALES_REFUND("in_sales_refund","销售退货"),
    IN_SHOPPING("in_shopping","采购入库"),
    IN_SALES_CHANGE("in_sales_change","销售换货"),
	IN_CHECK_MORE("in_check_more","盘点生溢"),
    IN_ZERO_CHECK_MORE("in_zero_check_more","零库存盘盈"),
    IN_MOVE_STORAGE("in_move_storage","移库入库"),
    IN_BORROW_STORAGE("in_borrow_storage","外借入库"),
    IN_BACK_STORAGE("in_back_storage","归还入库");




    private String code;

    private String name;

    EnumInDepository(String code,String name){
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
        enumDataMap.put(IN_SALES_REFUND.getKey(), IN_SALES_REFUND.getValue());
        enumDataMap.put(IN_SHOPPING.getKey(), IN_SHOPPING.getValue());
        enumDataMap.put(IN_SALES_CHANGE.getKey(), IN_SALES_CHANGE.getValue());
        enumDataMap.put(IN_ZERO_CHECK_MORE.getKey(), IN_ZERO_CHECK_MORE.getValue());
        enumDataMap.put(IN_CHECK_MORE.getKey(), IN_CHECK_MORE.getValue());
        enumDataMap.put(IN_MOVE_STORAGE.getKey(), IN_MOVE_STORAGE.getValue());
        enumDataMap.put(IN_BORROW_STORAGE.getKey(), IN_BORROW_STORAGE.getValue());
        enumDataMap.put(IN_BACK_STORAGE.getKey(), IN_BACK_STORAGE.getValue());
        return enumDataMap;
    }
}
