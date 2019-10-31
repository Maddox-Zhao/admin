package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 物流范围关系付款类型(部分：没有周期结算)
 * @author chenyan 2009/08/06 modified by chenyan 2010/07/19
 *
 */
public enum EnumExpressDistPaymentPart {

	PAYMENT_FIRST("payment_first", "先款后货"),
    GOODS_FIRST("goods_first","货到付款"),
    ALL("all", "全部支持");

    private String code;
    private String name;

    EnumExpressDistPaymentPart(String code, String name) {
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
                if("payment_first".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("payment_first".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("goods_first".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("goods_first".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("all".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("all".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(PAYMENT_FIRST.getKey(), PAYMENT_FIRST.getValue());
        enumDataMap.put(GOODS_FIRST.getKey(), GOODS_FIRST.getValue());
        enumDataMap.put(ALL.getKey(), ALL.getValue());
        return enumDataMap;
    }
}
