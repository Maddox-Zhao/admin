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
public enum EnumSeriesType {

	BAG("1","包包",0),
	WALLET("2","钱夹",0),
	BELT("3","腰带",1),
	APPAREL("4","服饰",1),
	SHOES("5","鞋履",1),
	ACCESSORIES("6","配饰",1),
	GLASSES("7","眼镜",1);
	
	
    private String code;

    private String name;

    private int size;
    
    EnumSeriesType(String code,String name,int size){
        this.code=code;
        this.name=name;
        this.size=size;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
    public int getSize(){
    	return this.size;
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
                if("4".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("4".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                if("5".equalsIgnoreCase(eOp1)){
                    ob1Index=5;
                }
                if("5".equalsIgnoreCase(eOp2)){
                    ob2Index=5;
                }
                if("6".equalsIgnoreCase(eOp1)){
                    ob1Index=6;
                }
                if("6".equalsIgnoreCase(eOp2)){
                    ob2Index=6;
                }
                if("7".equalsIgnoreCase(eOp1)){
                    ob1Index=7;
                }
                if("7".equalsIgnoreCase(eOp2)){
                    ob2Index=7;
                }
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(BAG.getKey(), BAG.getValue());
        enumDataMap.put(WALLET.getKey(), WALLET.getValue());
        enumDataMap.put(BELT.getKey(), BELT.getValue());
        enumDataMap.put(APPAREL.getKey(), APPAREL.getValue());
        enumDataMap.put(SHOES.getKey(),SHOES.getValue());
        enumDataMap.put(ACCESSORIES.getKey(), ACCESSORIES.getValue());
        enumDataMap.put(GLASSES.getKey(), GLASSES.getValue());
        
        return enumDataMap;
    }
    
    
}
