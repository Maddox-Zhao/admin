package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 入库单状态
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumInStatus  {
    IN_NEW("in_new", "新建"),
    IN_FINISHED("in_finished","完成");

    private String code;
    private String name;

    EnumInStatus(String code, String name) {
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
                if("in_new".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("in_new".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("in_finished".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("in_finished".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(IN_NEW.getKey(), IN_NEW.getValue());
        enumDataMap.put(IN_FINISHED.getKey(), IN_FINISHED.getValue());
        return enumDataMap;
    }

}
