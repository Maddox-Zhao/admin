package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 采购订单状态
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumStockStatus  {
    STOCK_NEW("stock_new", "新建"),
    STOCK_WAIT_CHECK("stock_wait_check","等待验收"),
    STOCK_CHECKED("stock_checked","已验收"),
    STOCK_FINISHED("stock_finished","采购完成"),
    STOCK_CLOSE("stock_close","采购关闭");

    private String code;
    private String name;
    private int index;

    EnumStockStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new TreeMap<String, String>(new Comparator() {
            public int compare(Object element1, Object element2) {
                String eOp1 = (String) element1;
                String eOp2 = (String) element2;
                if(eOp1==null){
                    return -1;
                }
                if(eOp2==null){
                    return 1;
                }

                int ob1Index=0;
                int ob2Index=0;
                if("stock_new".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("stock_new".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("stock_wait_check".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("stock_wait_check".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("stock_checked".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("stock_checked".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("stock_finished".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("stock_finished".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("stock_close".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("stock_close".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(STOCK_NEW.getKey(), STOCK_NEW.getValue());
        enumDataMap.put(STOCK_WAIT_CHECK.getKey(), STOCK_WAIT_CHECK.getValue());
        enumDataMap.put(STOCK_CHECKED.getKey(), STOCK_CHECKED.getValue());
        enumDataMap.put(STOCK_FINISHED.getKey(), STOCK_FINISHED.getValue());
        enumDataMap.put(STOCK_CLOSE.getKey(), STOCK_CLOSE.getValue());
        return enumDataMap;
    }

}
