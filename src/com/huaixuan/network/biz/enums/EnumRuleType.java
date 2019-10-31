/**
 * created since Jun 16, 2009
 */
package com.huaixuan.network.biz.enums;

/**
 * @author taobao
 * @version $Id: EnumRuleType.java,v 0.1 Jun 16, 2009 10:57:24 AM taobao Exp $
 */
public enum EnumRuleType {
    USERD(1L,"领用规则"),
    PROMATION(2L,"优惠规则");
    private Long code;
    private String name;
    EnumRuleType(Long code,String name){
        this.code=code;
        this.name=name;
    }
    public Long getKey() {
        return this.code;
    }
    public String getValue(){
       return this.name;
    }
}
