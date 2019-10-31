package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 批发申请单状态
 * @author Administrator
 *
 */
public enum EnumWholesaleStatus {
	WHOLESALE_STATUS_NEW("new", "新建"),
	WHOLESALE_STATUS_HANDLING("handling","处理中"),
	WHOLESALE_STATUS_FINISH("finish", "完成");

    private String code;
    private String name;
    private int index;

    EnumWholesaleStatus(String code, String name) {
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
                if("handling".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("handling".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("finish".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("finish".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(WHOLESALE_STATUS_NEW.getKey(), WHOLESALE_STATUS_NEW.getValue());
        enumDataMap.put(WHOLESALE_STATUS_HANDLING.getKey(), WHOLESALE_STATUS_HANDLING.getValue());
        enumDataMap.put(WHOLESALE_STATUS_FINISH.getKey(), WHOLESALE_STATUS_FINISH.getValue());
        return enumDataMap;
    }
}
