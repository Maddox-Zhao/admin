package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 采购订单状态
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumShoppingRefund  {
    REFUND_CAIGOU("refund_caigou", "采购退货"),
    REFUND_JUSHOU("refund_jushou","拒收退货"),
    REFUND_DINGDAN("refund_dingdan","单据退货"),
    REFUND_STORAGE("refund_storage","库存退货");

    private String code;
    private String name;
    private int index;

    EnumShoppingRefund(String code, String name) {
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
                if("refund_caigou".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("refund_caigou".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("refund_jushou".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("refund_jushou".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("refund_dingdan".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("refund_dingdan".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("refund_storage".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("refund_storage".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(REFUND_CAIGOU.getKey(), REFUND_CAIGOU.getValue());
        enumDataMap.put(REFUND_JUSHOU.getKey(), REFUND_JUSHOU.getValue());
        enumDataMap.put(REFUND_DINGDAN.getKey(), REFUND_DINGDAN.getValue());
        enumDataMap.put(REFUND_STORAGE.getKey(), REFUND_STORAGE.getValue());
        return enumDataMap;
    }

}
