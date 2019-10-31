/**
 *
 */
package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shengyong
 *
 */
public enum EnumTicketRecordType {

	AGENT_TICKET_TRADE("trade_minus","点券付款"),
	AGENT_TICKET_REFUND("goods_add","退换货返点"),
	SALE_ADD("sale_add", "销售返点"),
	POINT_ADD("point_add", "销售积分"),
    POINT_MINUS("point_minus", "退货减积分"),
	CASH_MINUS("cash_minus","兑换现金");

	private String code;

	private String name;

	EnumTicketRecordType(String code, String name) {
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
                if("sale_add".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("sale_add".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("trade_minus".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("trade_minus".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("goods_add".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("goods_add".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("cash_minus".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("cash_minus".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(SALE_ADD.getKey(), SALE_ADD.getValue());
        enumDataMap.put(AGENT_TICKET_TRADE.getKey(), AGENT_TICKET_TRADE.getValue());
        enumDataMap.put(AGENT_TICKET_REFUND.getKey(), AGENT_TICKET_REFUND.getValue());
        enumDataMap.put(CASH_MINUS.getKey(), CASH_MINUS.getValue());
        return enumDataMap;
    }
}
