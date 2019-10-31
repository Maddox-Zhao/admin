package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 报残原因
 * @author
 * @version $Id: EnumDamagedReason.java,v 0.1 2009-7-24 下午02:05:12  Exp $
 */
public enum EnumDamagedReason  {
	//过期、配送损坏、运输造残、包装破损、商品破损、商品污染
    REASON_OVERDUE("reason_overdue", "过期"),
    REASON_DISPATCH_DMGE("reason_dispatch_dmge", "配送损坏"),
    REASON_CARRIAGE_DMGE("reason_carriage_dmge","运输造残"),
    REASON_PACK_DMGE("reason_pack_dmge","包装破损"),
    REASON_GOODS_DMGE("reason_goods_dmge","商品破损"),
    REASON_GOODS_POLLUTE("reason_goods_pollute","商品污染");
    

    private String code;
    private String name;

    EnumDamagedReason(String code, String name) {
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
                if("reason_overdue".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("reason_overdue".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("reason_dispatch_dmge".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("reason_dispatch_dmge".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("reason_carriage_dmge".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("reason_carriage_dmge".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("reason_pack_dmge".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("reason_pack_dmge".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("reason_goods_dmge".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("reason_goods_dmge".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }
                if("reason_goods_pollute".equalsIgnoreCase(eOp1)){
                    ob1Index=6;
                }
                if("reason_goods_pollute".equalsIgnoreCase(eOp2)){
                    ob2Index=6;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        
        enumDataMap.put(REASON_OVERDUE.getKey(), REASON_OVERDUE.getValue());
        enumDataMap.put(REASON_DISPATCH_DMGE.getKey(), REASON_DISPATCH_DMGE.getValue());
        enumDataMap.put(REASON_CARRIAGE_DMGE.getKey(), REASON_CARRIAGE_DMGE.getValue());
        enumDataMap.put(REASON_PACK_DMGE.getKey(), REASON_PACK_DMGE.getValue());
        enumDataMap.put(REASON_GOODS_DMGE.getKey(), REASON_GOODS_DMGE.getValue());
        enumDataMap.put(REASON_GOODS_POLLUTE.getKey(), REASON_GOODS_POLLUTE.getValue());
        return enumDataMap;
    }

}
