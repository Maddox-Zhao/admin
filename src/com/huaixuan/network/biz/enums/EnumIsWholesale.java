package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 订单类型：普通，批发
 * @author chenyan
 *
 */
public enum EnumIsWholesale {
    IS_WHOLESALE_N("n", "普通"),
    IS_WHOLESALE_W("w","批发");

    private String code;
    private String name;

    EnumIsWholesale(String code, String name) {
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
                if("n".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("n".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("w".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("w".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(IS_WHOLESALE_N.getKey(), IS_WHOLESALE_N.getValue());
        enumDataMap.put(IS_WHOLESALE_W.getKey(), IS_WHOLESALE_W.getValue());
        return enumDataMap;
    }
}
