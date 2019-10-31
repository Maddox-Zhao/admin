/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhangwy
 */
public enum EnumFinanceStatus {

    NO_SURE("n","未确认"),
    EA_SURE("v","暂估确认"),
    FACT_SURE("f","实际确认"),
    ALL_SURE("y","全部确认");



    private String code;

    private String name;

    EnumFinanceStatus(String code,String name){
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
                if("v".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("v".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("f".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("f".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("y".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("y".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(NO_SURE.getKey(), NO_SURE.getValue());
        enumDataMap.put(ALL_SURE.getKey(), ALL_SURE.getValue());
        enumDataMap.put(FACT_SURE.getKey(), FACT_SURE.getValue());
        enumDataMap.put(EA_SURE.getKey(), EA_SURE.getValue());  
        return enumDataMap;
    }
}
