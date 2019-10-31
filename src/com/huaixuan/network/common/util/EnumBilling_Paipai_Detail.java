package com.huaixuan.network.common.util;

public enum EnumBilling_Paipai_Detail {
	No_Permission("508","对不起，该用户没有权限，可能是由于未登录等原因。"),
	Server_Exception("4096","服务器出现错误，请稍后再试（服务器产生异常）。"),
	Protocol_Exception("4097","服务器出现错误，请稍后再试（服务器后台协议返回错误）。"),
	Creating_Cache("4098","服务器出现错误，请稍后再试（服务器正在建设缓存）。"),
	Initialization_Exception("4099","服务器出现错误，请稍后再试（环境初始化错误）。"),
	Not_Support_Argument("8192","对不起，服务器不支持该参数。"),
	Parameter_Exception("8193","对不起，参数设置错误。"),
	Permissions_No_Passed("12288","对不起，您无权访问该服务（鉴权不通过）。"),
	No_Access_Interface("12289","对不起，您无权访问该服务（外网无权访问该服务接口）。"),
	Sign_Validation_Failure("12290","对不起，sign校验失败，数据被篡改或sign计算方法有误。"),
	Access_frequency_is_too_fast("12291","对不起，您的访问频率太快，请稍后再试。");

    private String code;
    private String name;
   
    EnumBilling_Paipai_Detail(String code,String name){
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
        if(key.equalsIgnoreCase("508")){
        	return No_Permission.getValue();
        }
        else if(key.equalsIgnoreCase("4096")){
            return Server_Exception.getValue(); 
        }
        else if(key.equalsIgnoreCase("4097")){
            return Protocol_Exception.getValue(); 
        }
        else if(key.equalsIgnoreCase("4098")){
            return Creating_Cache.getValue(); 
        }
        else if(key.equalsIgnoreCase("4099")){
            return Initialization_Exception.getValue(); 
        }
        else if(key.equalsIgnoreCase("8192")){
            return Not_Support_Argument.getValue(); 
        }
        else if(key.equalsIgnoreCase("8193")){
            return Parameter_Exception.getValue(); 
        }
        else if(key.equalsIgnoreCase("12288")){
            return Permissions_No_Passed.getValue(); 
        }
        else if(key.equalsIgnoreCase("12289")){
            return No_Access_Interface.getValue(); 
        }
        else if(key.equalsIgnoreCase("12290")){
            return Sign_Validation_Failure.getValue(); 
        }
        else{
            return Access_frequency_is_too_fast.getValue(); 
        }
            
    }

     

}
