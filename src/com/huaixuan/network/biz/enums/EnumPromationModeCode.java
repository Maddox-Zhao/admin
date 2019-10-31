/**
 * created since Jun 16, 2009
 */
package com.huaixuan.network.biz.enums;

/**
 * @author taobao
 * @version $Id: EnumPromationModeCode.java,v 0.1 Jun 16, 2009 12:45:51 PM taobao Exp $
 */
public enum EnumPromationModeCode {
    SALE_GIVE("sale_give","买实物就赠送实物"),
    COMBINED_SALE("combined_sale","组合销售"),
    FULL_GIVE("full_give","满就送"),
    FULL_REDUCE("full_reduce","满就减"),
    SALE_EXEMPT_POSTAGE("sale_exempt_postage","买就包邮"),
    DISCOUNT("discount","打折促销");
    private String code;
    private String name;
    EnumPromationModeCode(String code,String name){
        this.code=code;
        this.name=name;
    }
    public String getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
}
