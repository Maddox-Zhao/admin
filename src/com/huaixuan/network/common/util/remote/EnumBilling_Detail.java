package com.huaixuan.network.common.util.remote;

public enum EnumBilling_Detail {
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","参数不正硄17"),
    REQUIRED_DATE("REQUIRED_DATE","日期必须输入"),
    ILLEGAL_DATE_FORMAT("ILLEGAL_DATE_FORMAT","日期格式错误"),
    ILLEGAL_DATE_TOO_LONG("ILLEGAL_DATE_TOO_LONG","日期间隔太大(超过丄17处17)"),
    ILLEGAL_TRANS_CODE("ILLEGAL_TRANS_CODE","错误的帐务类别码"),
    
    ILLEGAL_SIGN("ILLEGAL_SIGN","签名不正硄17"),
    TRADE_NOT_EXIST("TRADE_NOT_EXIST","交易不存圄17"),
    ILLEGAL_PARTNER("ILLEGAL_PARTNER","合作伙伴不存圄17"),
    ILLEGAL_PAYMENT_TYPE("ILLEGAL_PAYMENT_TYPE","无效支付类型"),
    ILLEGAL_SIGN_TYPE("ILLEGAL_SIGN_TYPE","签名类型不正硄17"),
    HASH_NO_PRIVILEGE("HASH_NO_PRIVILEGE","没有权限访问该服劄17"),
    ILLEGAL_LOGISTICS_FORMAT("ILLEGAL_LOGISTICS_FORMAT","无效物流格式"),
    ILLEGAL_CHARSET("ILLEGAL_CHARSET","字符集不合法"),
    SELF_TIMEOUT_NOT_SUPPORT("SELF_TIMEOUT_NOT_SUPPORT","不支持自定义超时");

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
