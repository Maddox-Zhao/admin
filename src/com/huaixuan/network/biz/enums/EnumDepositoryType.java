package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 仓库类型
 * @author fanyj
 *
 */
public enum EnumDepositoryType {
    COMMON_DEP("common_dep", "普通仓库"),
    OUT_BORROW_DEP("out_borrow_dep","外借仓库"),
    DEFECT_DEP("defect_dep","次品仓库");

    private String code;
    private String name;

    EnumDepositoryType(String code, String name) {
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
                if("common_dep".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("common_dep".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("out_borrow_dep".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("out_borrow_dep".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("defect_dep".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("defect_dep".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }


                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(COMMON_DEP.getKey(), COMMON_DEP.getValue());
        enumDataMap.put(OUT_BORROW_DEP.getKey(), OUT_BORROW_DEP.getValue());
        enumDataMap.put(DEFECT_DEP.getKey(), DEFECT_DEP.getValue());
        return enumDataMap;
    }

}
