package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 沟通记录状态
 * @author chenyan 2010/08/05
 *
 */
public enum EnumConnectRecordStatus {
    STATUS_NEW("new", "未完成"),
    STATUS_FINISHED("finished","已完成");

    private String code;
    private String name;

    EnumConnectRecordStatus(String code, String name) {
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
                if("new".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("new".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("finished".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("finished".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(STATUS_NEW.getKey(), STATUS_NEW.getValue());
        enumDataMap.put(STATUS_FINISHED.getKey(), STATUS_FINISHED.getValue());
        return enumDataMap;
    }
}
