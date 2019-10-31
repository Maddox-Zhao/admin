/**
 * created since 2009-7-20
 */
package com.huaixuan.network.biz.enums.hy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author songfy
 */
public enum EnumTargetCustomer {

	MAN("1","男"),
	WOMAN("2","女"),
	CHILD("3","儿童");
	
	
    private String code;

    private String name;
    
    EnumTargetCustomer(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }

    @SuppressWarnings("unchecked")
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
                if("1".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("1".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("2".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("2".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("3".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("3".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }

                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(MAN.getKey(), MAN.getValue());
        enumDataMap.put(WOMAN.getKey(), WOMAN.getValue());
        enumDataMap.put(CHILD.getKey(), CHILD.getValue());
        
        return enumDataMap;
    }
    
    
}
