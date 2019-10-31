package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author lincf
 *
 * Nov 3, 2009
 */
public enum EnumStockOutStatus {
    Stock_Out_All("","全 部"),
    Stock_Out_INIT("init", "未通知"),
    Stock_Out_Notified("notified", "已通知"), 
    Stock_Out_No_notify("no_notify","不需要通知");
	
    private String code;
    private String name;

    EnumStockOutStatus(String code, String name) {
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
                if("init".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("init".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("notified".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("notified".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("no_notify".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("no_notify".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);
                return ob1.compareTo(ob2);
            }     
        });
        enumDataMap.put(Stock_Out_All.getKey(),Stock_Out_All.getValue());
        enumDataMap.put(Stock_Out_INIT.getKey(), Stock_Out_INIT.getValue());
        enumDataMap.put(Stock_Out_Notified.getKey(), Stock_Out_Notified.getValue());
        enumDataMap.put(Stock_Out_No_notify.getKey(), Stock_Out_No_notify.getValue());
        return enumDataMap;
        
    }

}
