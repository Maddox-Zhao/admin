package com.huaixuan.network.common.util;

public enum EnumBilling_Paipai_Detail {
	No_Permission("508","�Բ��𣬸��û�û��Ȩ�ޣ�����������δ��¼��ԭ��"),
	Server_Exception("4096","���������ִ������Ժ����ԣ������������쳣����"),
	Protocol_Exception("4097","���������ִ������Ժ����ԣ���������̨Э�鷵�ش��󣩡�"),
	Creating_Cache("4098","���������ִ������Ժ����ԣ����������ڽ��軺�棩��"),
	Initialization_Exception("4099","���������ִ������Ժ����ԣ�������ʼ�����󣩡�"),
	Not_Support_Argument("8192","�Բ��𣬷�������֧�ָò�����"),
	Parameter_Exception("8193","�Բ��𣬲������ô���"),
	Permissions_No_Passed("12288","�Բ�������Ȩ���ʸ÷��񣨼�Ȩ��ͨ������"),
	No_Access_Interface("12289","�Բ�������Ȩ���ʸ÷���������Ȩ���ʸ÷���ӿڣ���"),
	Sign_Validation_Failure("12290","�Բ���signУ��ʧ�ܣ����ݱ��۸Ļ�sign���㷽������"),
	Access_frequency_is_too_fast("12291","�Բ������ķ���Ƶ��̫�죬���Ժ����ԡ�");

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
