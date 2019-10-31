package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 返点审核状态
 * @author fanyj
 *
 */
public enum EnumReturnPointApplyType {
    NEW("new", "新建"),
    PASS("pass","审核通过"),
    NO_PASS("nopass","审核未通过");

    private String code;
    private String name;

    EnumReturnPointApplyType(String code, String name) {
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
                if("pass".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("pass".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("nopass".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("nopass".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }


                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(NEW.getKey(), NEW.getValue());
        enumDataMap.put(PASS.getKey(), PASS.getValue());
        enumDataMap.put(NO_PASS.getKey(), NO_PASS.getValue());
        return enumDataMap;
    }

}
