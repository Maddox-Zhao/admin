package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 报残单状态
 * @author
 * @version $Id: EnumTradeStatus.java,v 0.1 2009-7-18 下午02:05:12  Exp $
 */
public enum EnumDamagedStatus  {
    DAM_NEW("dam_new", "新建"),
    DAM_WAIT_CHECK("dam_wait_check","等待审核"),
    DAM_CHECKED("dam_checked","审核通过"),
    DAM_NO_PASS_CHECKED("dam_no_pass_checked","审核未通过"),
    DAM_FINISHED("dam_finished","完成");

    private String code;
    private String name;
    private int index;

    EnumDamagedStatus(String code, String name) {
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
                if("dam_new".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("dam_new".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("dam_wait_check".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("dam_wait_check".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("dam_checked".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("dam_checked".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("dam_no_pass_checked".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("dam_no_pass_checked".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("dam_finished".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("dam_finished".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(DAM_NEW.getKey(), DAM_NEW.getValue());
        enumDataMap.put(DAM_WAIT_CHECK.getKey(), DAM_WAIT_CHECK.getValue());
        enumDataMap.put(DAM_CHECKED.getKey(), DAM_CHECKED.getValue());
        enumDataMap.put(DAM_NO_PASS_CHECKED.getKey(), DAM_NO_PASS_CHECKED.getValue());
        enumDataMap.put(DAM_FINISHED.getKey(), DAM_FINISHED.getValue());
        return enumDataMap;
    }

}
