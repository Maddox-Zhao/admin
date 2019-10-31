package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**一级仓库类型
 * @author Administrator
 *
 */
public enum EnumDepositoryFirstType {
	EnumDepositoryFirst_TYPE_N("n", "普通仓库"),
	EnumDepositoryFirst_TYPE_W("w","批发虚拟库");

    private String code;
    private String name;
    private int index;

    EnumDepositoryFirstType(String code, String name) {
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
                if("n".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("n".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("w".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("w".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(EnumDepositoryFirst_TYPE_N.getKey(), EnumDepositoryFirst_TYPE_N.getValue());
        enumDataMap.put(EnumDepositoryFirst_TYPE_W.getKey(), EnumDepositoryFirst_TYPE_W.getValue());
        return enumDataMap;
    }
}
