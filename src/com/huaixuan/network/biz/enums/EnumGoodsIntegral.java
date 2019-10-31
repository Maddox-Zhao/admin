/**
 * created since Mar 13, 2009
 */
package com.huaixuan.network.biz.enums;

/**
 * @author 活动积分枚举类
 * @version $Id: GoodsStatus.java,v 0.1 Mar 13, 2009 10:22:44 AM $
 */
public enum EnumGoodsIntegral {
	Integral_5("5","5"),// 5积分
	Integral_100("100","100"),// 100积分
	Integral_200("200","200"),// 200积分
	Integral_300("200","200"); // 300积分
    private String code;
    private String name;
    EnumGoodsIntegral(String code,String name){
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
