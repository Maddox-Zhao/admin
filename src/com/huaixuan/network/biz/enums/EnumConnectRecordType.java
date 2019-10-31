package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**沟通记录类型
 * @author chenyan
 *
 */
public enum EnumConnectRecordType {
	TYPE_ORDER("order", "预约记录"),
    TYPE_CONNECT("connect","沟通记录");

    private String code;
    private String name;
    private int index;

    EnumConnectRecordType(String code, String name) {
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
                if("order".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("order".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("connect".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("connect".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(TYPE_ORDER.getKey(), TYPE_ORDER.getValue());
        enumDataMap.put(TYPE_CONNECT.getKey(), TYPE_CONNECT.getValue());
        return enumDataMap;
    }
}
