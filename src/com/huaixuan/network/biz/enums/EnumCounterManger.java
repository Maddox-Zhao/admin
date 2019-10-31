/**
 * created since 2009-8-7
 */
package com.huaixuan.network.biz.enums;

/**
 * @author zhangxiang
 * @version $Id: EnumCounterManger.java,v 0.1 2009-8-7 下午12:03:05 zhangxiang Exp $
 */
public enum EnumCounterManger {
    
    FAILDESCRIPTIONSTR("failDescriptionStr", "现在暂时只支持支付宝与代充代付文件格式!!!");

   
    
    private String code;

    private String name;

    EnumCounterManger(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return this.code;
    }

    public String getValue() {
        return this.name;
    }

}
