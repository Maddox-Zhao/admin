package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 交易状态
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-3-18 下午02:05:12  Exp $
 */
public enum EnumWholesaleListStatus  {
	WAIT_CONFIRM("wait_confirm","等待客服确认"),
    WAIT_BUYER_PAY("wait_buyer_pay", "等待买家付款"),
    WAIT_DISTRIBUTION("wait_distribution","等待配货"),
    WAIT_SELLER_SEND_GOODS("wait_seller_send_goods","等待商家发货"),
    WAIT_BUYER_CONFIRM_GOODS("wait_buyer_confirm_goods","等待买家确认收货"),
    TRADE_CLOSE("trade_close", "交易关闭"),
    TRADE_FINISH("trade_finish","交易完成");

    private String code;
    private String name;
    private int index;

    EnumWholesaleListStatus(String code, String name) {
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
                if("".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("wait_confirm".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("wait_confirm".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }                
                if("wait_distribution".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("wait_distribution".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("wait_buyer_pay".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("wait_buyer_pay".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("wait_seller_send_goods".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("wait_seller_send_goods".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }
                if("wait_buyer_confirm_goods".equalsIgnoreCase(eOp1)){
                    ob1Index=6;
                }
                if("wait_buyer_confirm_goods".equalsIgnoreCase(eOp2)){
                    ob2Index=6;
                }
                if("trade_finish".equalsIgnoreCase(eOp1)){
                    ob1Index=7;
                }
                if("trade_finish".equalsIgnoreCase(eOp2)){
                    ob2Index=7;
                }
                if("trade_close".equalsIgnoreCase(eOp1)){
                    ob1Index=8;
                }
                if("trade_close".equalsIgnoreCase(eOp2)){
                    ob2Index=8;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(WAIT_BUYER_PAY.getKey(), WAIT_BUYER_PAY.getValue());
        enumDataMap.put(WAIT_DISTRIBUTION.getKey(), WAIT_DISTRIBUTION.getValue());
        enumDataMap.put(WAIT_SELLER_SEND_GOODS.getKey(), WAIT_SELLER_SEND_GOODS.getValue());
        enumDataMap.put(WAIT_BUYER_CONFIRM_GOODS.getKey(), WAIT_BUYER_CONFIRM_GOODS.getValue());
        enumDataMap.put(TRADE_CLOSE.getKey(), TRADE_CLOSE.getValue());
        enumDataMap.put(TRADE_FINISH.getKey(), TRADE_FINISH.getValue());
        enumDataMap.put(WAIT_CONFIRM.getKey(), WAIT_CONFIRM.getValue());
        return enumDataMap;
    }

}
