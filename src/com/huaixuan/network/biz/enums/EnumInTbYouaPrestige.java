package com.huaixuan.network.biz.enums;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public enum EnumInTbYouaPrestige {
    ZERO("1","请选择"),
    ONESTAR("2","一心"),
    TWOSTAR("3","二心"),
    THREESTAR("4","三心"),
    FOURSTAR("5","四心"),
    FIVESTAR("6","五心"),
    ONEDIAMOND("7","一钻"),
    TWODIAMOND("8","二钻"),
    THREEDIAMOND("9","三钻"),
    FOURDIAMOND("10","四钻"),
    FIVEDIAMOND("11","五钻"),
    ONECROWN("12","一冠"),
    TWOCROWN("13","二冠"),
    THREECROWN("14","三冠"),
    FOURCROWN("15","四冠"),
    FIVECROWN("16","五冠");

    private String code;

    private String name;

    EnumInTbYouaPrestige(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getKey() {
        return this.code;
    }
    public String getValue(){
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
                if("8".equalsIgnoreCase(eOp1)){
                    ob1Index=8;
                }
                if("8".equalsIgnoreCase(eOp2)){
                    ob2Index=8;
                }
                if("9".equalsIgnoreCase(eOp1)){
                    ob1Index=9;
                }
                if("9".equalsIgnoreCase(eOp2)){
                    ob2Index=9;
                }
                if("10".equalsIgnoreCase(eOp1)){
                    ob1Index=10;
                }
                if("10".equalsIgnoreCase(eOp2)){
                    ob2Index=10;
                }
                if("11".equalsIgnoreCase(eOp1)){
                    ob1Index=11;
                }
                if("11".equalsIgnoreCase(eOp2)){
                    ob2Index=11;
                }
                if("12".equalsIgnoreCase(eOp1)){
                    ob1Index=12;
                }
                if("12".equalsIgnoreCase(eOp2)){
                    ob2Index=12;
                }
                
                if("13".equalsIgnoreCase(eOp1)){
                    ob1Index=13;
                }
                if("13".equalsIgnoreCase(eOp2)){
                    ob2Index=13;
                }
                if("14".equalsIgnoreCase(eOp1)){
                    ob1Index=14;
                }
                if("14".equalsIgnoreCase(eOp2)){
                    ob2Index=14;
                }
                if("15".equalsIgnoreCase(eOp1)){
                    ob1Index=15;
                }
                if("15".equalsIgnoreCase(eOp2)){
                    ob2Index=15;
                }
                if("16".equalsIgnoreCase(eOp1)){
                    ob1Index=16;
                }
                if("16".equalsIgnoreCase(eOp2)){
                    ob2Index=16;
                }
                
                
                Integer ob1=new Integer(ob1Index);
                Integer ob2=new Integer(ob2Index);

                return ob1.compareTo(ob2);
            }
        });
        enumDataMap.put(ZERO.getKey(), ZERO.getValue());
        enumDataMap.put(ONESTAR.getKey(), ONESTAR.getValue());
        enumDataMap.put(TWOSTAR.getKey(), TWOSTAR.getValue());
        enumDataMap.put(THREESTAR.getKey(), THREESTAR.getValue());
        enumDataMap.put(FOURSTAR.getKey(), FOURSTAR.getValue());
        enumDataMap.put(FIVESTAR.getKey(), FIVESTAR.getValue());
       
        enumDataMap.put(ONEDIAMOND.getKey(), ONEDIAMOND.getValue());
        enumDataMap.put(TWODIAMOND.getKey(), TWODIAMOND.getValue());
        enumDataMap.put(THREEDIAMOND.getKey(), THREEDIAMOND.getValue());
        enumDataMap.put(FOURDIAMOND.getKey(), FOURDIAMOND.getValue());
        enumDataMap.put(FIVEDIAMOND.getKey(), FIVEDIAMOND.getValue());
        
        enumDataMap.put(ONECROWN.getKey(), ONECROWN.getValue());
        enumDataMap.put(TWOCROWN.getKey(), TWOCROWN.getValue());
        enumDataMap.put(THREECROWN.getKey(), THREECROWN.getValue());
        enumDataMap.put(FOURCROWN.getKey(), FOURCROWN.getValue());
        enumDataMap.put(FIVECROWN.getKey(), FIVECROWN.getValue());
        return enumDataMap;
    }
}
