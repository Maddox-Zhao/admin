package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author lincf
 *
 * Nov 3, 2009
 */
public enum EnumSiteAddress {
    Site_Address_All("","全 部"),
    Site_Address_Tejiawang("tejiawang", "特价王"),
    Site_Address_51fanli("51fanli", "51返利网"), 
    Site_Address_Linktech("linktech","领克特");
	
    private String code;
    private String name;

    EnumSiteAddress(String code, String name) {
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
                if("".equalsIgnoreCase(eOp1)){
                    ob1Index=1;
                }
                if("".equalsIgnoreCase(eOp2)){
                    ob2Index=1;
                }
                if("tejiawang".equalsIgnoreCase(eOp1)){
                    ob1Index=2;
                }
                if("tejiawang".equalsIgnoreCase(eOp2)){
                    ob2Index=2;
                }
                if("51fanli".equalsIgnoreCase(eOp1)){
                    ob1Index=3;
                }
                if("51fanli".equalsIgnoreCase(eOp2)){
                    ob2Index=3;
                }
                if("linktech".equalsIgnoreCase(eOp1)){
                    ob1Index=4;
                }
                if("linktech".equalsIgnoreCase(eOp2)){
                    ob2Index=4;
                }
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);
                return ob1.compareTo(ob2);
            }     
        });
        enumDataMap.put(Site_Address_All.getKey(),Site_Address_All.getValue());
        enumDataMap.put(Site_Address_Tejiawang.getKey(), Site_Address_Tejiawang.getValue());
        enumDataMap.put(Site_Address_51fanli.getKey(), Site_Address_51fanli.getValue());
        enumDataMap.put(Site_Address_Linktech.getKey(), Site_Address_Linktech.getValue());
        return enumDataMap;
        
    }

}
