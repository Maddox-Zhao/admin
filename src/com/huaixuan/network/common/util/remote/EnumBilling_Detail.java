package com.huaixuan.network.common.util.remote;

public enum EnumBilling_Detail {
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","���������q17"),
    REQUIRED_DATE("REQUIRED_DATE","���ڱ�������"),
    ILLEGAL_DATE_FORMAT("ILLEGAL_DATE_FORMAT","���ڸ�ʽ����"),
    ILLEGAL_DATE_TOO_LONG("ILLEGAL_DATE_TOO_LONG","���ڼ��̫��(�����A17��17)"),
    ILLEGAL_TRANS_CODE("ILLEGAL_TRANS_CODE","��������������"),
    
    ILLEGAL_SIGN("ILLEGAL_SIGN","ǩ�������q17"),
    TRADE_NOT_EXIST("TRADE_NOT_EXIST","���ײ�����17"),
    ILLEGAL_PARTNER("ILLEGAL_PARTNER","������鲻����17"),
    ILLEGAL_PAYMENT_TYPE("ILLEGAL_PAYMENT_TYPE","��Ч֧������"),
    ILLEGAL_SIGN_TYPE("ILLEGAL_SIGN_TYPE","ǩ�����Ͳ����q17"),
    HASH_NO_PRIVILEGE("HASH_NO_PRIVILEGE","û��Ȩ�޷��ʸ÷���17"),
    ILLEGAL_LOGISTICS_FORMAT("ILLEGAL_LOGISTICS_FORMAT","��Ч������ʽ"),
    ILLEGAL_CHARSET("ILLEGAL_CHARSET","�ַ������Ϸ�"),
    SELF_TIMEOUT_NOT_SUPPORT("SELF_TIMEOUT_NOT_SUPPORT","��֧���Զ��峬ʱ");

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
        else if(key.equalsIgnoreCase("ILLEGAL_SIGN")){
            return ILLEGAL_SIGN.getValue(); 
        }
        else if(key.equalsIgnoreCase("TRADE_NOT_EXIST")){
            return TRADE_NOT_EXIST.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_PARTNER")){
            return ILLEGAL_PARTNER.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_PAYMENT_TYPE")){
            return ILLEGAL_PAYMENT_TYPE.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_SIGN_TYPE")){
            return ILLEGAL_SIGN_TYPE.getValue(); 
        }
        else if(key.equalsIgnoreCase("HASH_NO_PRIVILEGE")){
            return HASH_NO_PRIVILEGE.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_LOGISTICS_FORMAT")){
            return ILLEGAL_LOGISTICS_FORMAT.getValue(); 
        }
        else if(key.equalsIgnoreCase("ILLEGAL_CHARSET")){
            return ILLEGAL_CHARSET.getValue(); 
        }
        else if(key.equalsIgnoreCase("SELF_TIMEOUT_NOT_SUPPORT")){
            return SELF_TIMEOUT_NOT_SUPPORT.getValue(); 
        }
        else{
            return ILLEGAL_TRANS_CODE.getValue(); 
        }
            
    }

     

}
