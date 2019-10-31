package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum EnumStockAge {
	FIFTEEN("fifteen","15天"),
	TWENTY("twenty","20天"),
    ONEMONTH("onemonth","30天"),
    TWOMONTH("twomonth","2个月"),
    THREEMONTH("threemonth","3个月"),
    HALFYEAR("halfyear","半年"),
	ALLDAY("allday","全部");


    private String code;

    private String name;

    EnumStockAge(String code,String name){
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
        Map<String, String> enumDataMap = new TreeMap<String, String>(new Comparator(){
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
                if("fifteen".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("fifteen".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("twenty".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("twenty".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("onemonth".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("onemonth".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("twomonth".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("twomonth".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("threemonth".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("threemonth".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }
                if("halfyear".equalsIgnoreCase(eOp1)){
                    ob1Index=6;
                }
                if("halfyear".equalsIgnoreCase(eOp2)){
                    ob2Index=6;
                }
                if("allday".equalsIgnoreCase(eOp1)){
                    ob1Index=7;
                }
                if("allday".equalsIgnoreCase(eOp2)){
                    ob2Index=7;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(FIFTEEN.getKey(), FIFTEEN.getValue());
        enumDataMap.put(TWENTY.getKey(), TWENTY.getValue());
        enumDataMap.put(ONEMONTH.getKey(), ONEMONTH.getValue());
        enumDataMap.put(TWOMONTH.getKey(), TWOMONTH.getValue());
        enumDataMap.put(THREEMONTH.getKey(), THREEMONTH.getValue());
        enumDataMap.put(HALFYEAR.getKey(), HALFYEAR.getValue());
        enumDataMap.put(ALLDAY.getKey(), ALLDAY.getValue());
        return enumDataMap;
    }
}
