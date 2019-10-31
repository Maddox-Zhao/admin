package com.huaixuan.network.biz.enums;

public enum EnumPaymentType {
    PAYMENT_TYPE_INVALID("INVALID", -1, "非法交易"),
    PAYMENT_TYPE_ENET("ENET", 1, "网银支付"),
    PAYMENT_TYPE_TMO("TMO", 2, "银行汇款"),
    PAYMENT_TYPE_COLL("COLL", 3, "代收货款");

    private String name;
    private int    value;
    private String message;

    EnumPaymentType(String name, int value, String message) {
        this.name = name;
        this.value = value;
        this.message = message;
    }

    public static EnumPaymentType getEnumPaymentTypeByValue(int value) {
        switch (value) {
            case 1:
                return EnumPaymentType.PAYMENT_TYPE_ENET;
            case 2:
                return EnumPaymentType.PAYMENT_TYPE_TMO;
            default:
                return EnumPaymentType.PAYMENT_TYPE_INVALID;
        }
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
