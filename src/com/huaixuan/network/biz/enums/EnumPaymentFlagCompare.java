package com.huaixuan.network.biz.enums;

public enum EnumPaymentFlagCompare {
    PAYMENT_COMPARE_SUCCEED("S", "成功"), 
    PAYMENT_COMPARE_WAITING("F","等待处理"), 
    PAYMENT_COMPARE_NOTEAUAL("U","金额不等");

    private String code;
    private String description;

    EnumPaymentFlagCompare(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
    public static EnumPaymentFlagCompare getByCode(String code) {
        if(code == null) {
            return null;
        }
        for (EnumPaymentFlagCompare enumPaymentFlagCompare : values()) {
            if (enumPaymentFlagCompare.getCode().equals(code)) {
                return enumPaymentFlagCompare;
            }
        }
        return null;
    }

}
