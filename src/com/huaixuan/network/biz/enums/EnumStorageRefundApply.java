package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public enum EnumStorageRefundApply  {
    INIT("init", "新建"),
    FAIL("fail","退回"),
    SUCCESS("success","完成"),
    CLOSE("close","关闭");

    private String code;
    private String name;

    EnumStorageRefundApply(String code, String name) {
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
                if("init".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("init".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("fail".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("fail".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("success".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("success".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("close".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("close".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(INIT.getKey(), INIT.getValue());
        enumDataMap.put(FAIL.getKey(), FAIL.getValue());
        enumDataMap.put(SUCCESS.getKey(), SUCCESS.getValue());
        enumDataMap.put(CLOSE.getKey(), CLOSE.getValue());
        return enumDataMap;
    }

}
