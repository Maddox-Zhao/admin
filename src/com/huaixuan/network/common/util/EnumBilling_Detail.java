package com.huaixuan.network.common.util;

public enum EnumBilling_Detail {
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","��������ȷ"),
    REQUIRED_DATE("REQUIRED_DATE","���ڱ�������"),
    ILLEGAL_DATE_FORMAT("ILLEGAL_DATE_FORMAT","���ڸ�ʽ����"),
    ILLEGAL_DATE_TOO_LONG("ILLEGAL_DATE_TOO_LONG","���ڼ��̫��(����һ��)"),
    ILLEGAL_TRANS_CODE("ILLEGAL_TRANS_CODE","��������������");

    private String code;
    private String name;
   
    EnumBilling_Detail(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
    public String getValue(String key){
        if(key.equalsIgnoreCase("ILLEGAL_ARGUMENT")){
        return ILLEGAL_ARGUMENT.getValue();
        }
        else if(key.equalsIgnoreCase("REQUIRED_DATE")){
            return REQUIRED_DATE.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_DATE_FORMAT")){
            return ILLEGAL_DATE_FORMAT.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_DATE_TOO_LONG")){
            return ILLEGAL_DATE_TOO_LONG.getValue(); 
        }
        else if(key.equalsIgnoreCase("REQUIRED_DATE")){
            return REQUIRED_DATE.getValue(); 
        }
        else{
            return ILLEGAL_TRANS_CODE.getValue(); 
        }
            
    }

     

}
