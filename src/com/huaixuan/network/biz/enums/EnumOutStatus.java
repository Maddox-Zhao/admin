package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 出库单状态
 * @author chenyan
 *
 */
public enum EnumOutStatus {
    OUT_NEW("out_new", "新建"),
    OUT_FINISHED("out_finished","完成");

    private String code;
    private String name;

    EnumOutStatus(String code, String name) {
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
                if("out_new".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("out_new".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("out_finished".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("out_finished".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(OUT_NEW.getKey(), OUT_NEW.getValue());
        enumDataMap.put(OUT_FINISHED.getKey(), OUT_FINISHED.getValue());
        return enumDataMap;
    }

}
