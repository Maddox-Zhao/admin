package com.huaixuan.network.biz.enums;

/**
 * 扣款状态
 * @author Administrator
 * @version $Id: EnumPaymentIsSucceed.java,v 0.1 Aug 13, 2009 4:30:38 PM Administrator Exp $
 */
public enum EnumPaymentIsSucceed {
    PAYMENT_IS_SUCCEED_INIT("I", "初始状态"),
    PAYMENT_DEST_DEPOSIT_YES("Y", "支付成功"), 
    PAYMENT_DEST_FOREGIFT_NO("F","支付失败");
    private String code;
    private int    value;
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    EnumPaymentIsSucceed(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public static EnumPaymentIsSucceed getByCode(String code) {
        if(code == null) {
            return null;
        }
        for (EnumPaymentIsSucceed enumPaymentIsSucceed : values()) {
            if (enumPaymentIsSucceed.getCode().equals(code)) {
                return enumPaymentIsSucceed;
            }
        }
        return null;
    }

}
