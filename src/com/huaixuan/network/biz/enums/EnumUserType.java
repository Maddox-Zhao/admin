package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shengyong
 * 
 */
public enum EnumUserType {
	NORMAL_USER("p", "批发客户"), 
	AGENT_USER("d", "代销客户"),
	WHOLESALE_USER("w","批发兼代销");

	private String code;

	private String name;

	EnumUserType(String code, String name) {
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
                if("p".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("p".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("d".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("d".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                
                if("w".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("w".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });

        enumDataMap.put(NORMAL_USER.getKey(), NORMAL_USER.getValue());
        enumDataMap.put(AGENT_USER.getKey(), AGENT_USER.getValue());
        enumDataMap.put(WHOLESALE_USER.getKey(), WHOLESALE_USER.getValue());
        return enumDataMap;
    }

}
