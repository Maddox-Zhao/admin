package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 采购退货原因
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumShoppingRefundReason  {
	//质量不合格、包装破损、货物损坏、颜色不对、发货数量过多、发货数量不足,
    REASON_QUALITY_QU("reason_quality_qu", "质量不合格"),
    REASON_CASING_MAR("reason_casing_mar","包装破损"),
    REASON_GOODS_MAR("reason_goods_mar","货物损坏"),
    REASON_COLOR_WRONG("reason_color_wrong","颜色不对"),
    REASON_AMOUNT_MORE("reason_amount_more","发货数量过多"),
    REASON_AMOUNT_LACK("reason_amount_lack","发货数量不足"),
    REASON_OTHER("reason_other","其他原因");
    

    private String code;
    private String name;

    EnumShoppingRefundReason(String code, String name) {
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
                if("reason_quality_qu".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("reason_quality_qu".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("reason_casing_mar".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("reason_casing_mar".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("reason_goods_mar".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("reason_goods_mar".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("reason_amount_more".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("reason_amount_more".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("reason_color_wrong".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("reason_color_wrong".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }
                if("reason_amount_lack".equalsIgnoreCase(eOp1)){
                    ob1Index=6;
                }
                if("reason_amount_lack".equalsIgnoreCase(eOp2)){
                    ob2Index=6;
                }
                if("reason_other".equalsIgnoreCase(eOp1)){
                    ob1Index=7;
                }
                if("reason_other".equalsIgnoreCase(eOp2)){
                    ob2Index=7;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(REASON_QUALITY_QU.getKey(), REASON_QUALITY_QU.getValue());
        enumDataMap.put(REASON_CASING_MAR.getKey(), REASON_CASING_MAR.getValue());
        enumDataMap.put(REASON_GOODS_MAR.getKey(), REASON_GOODS_MAR.getValue());
        enumDataMap.put(REASON_COLOR_WRONG.getKey(), REASON_COLOR_WRONG.getValue());
        enumDataMap.put(REASON_AMOUNT_MORE.getKey(), REASON_AMOUNT_MORE.getValue());
        enumDataMap.put(REASON_AMOUNT_LACK.getKey(), REASON_AMOUNT_LACK.getValue());
        enumDataMap.put(REASON_OTHER.getKey(), REASON_OTHER.getValue());
        return enumDataMap;
    }

}
