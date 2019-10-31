package com.huaixuan.network.biz.enums;

/**
 * @author tao.wangt
 * @version $Id: EnumCompareFailReason.java,v 0.1 2009-11-10 下午01:19:22 tao.wangt Exp $
 */
public enum EnumCompareFailReason {
    REASON_RECORD_NULL("recordNull", "对账记录为空"), REASON_FIELD_NULL("fieldNull", "对账记录包含空字段"), REASON_FIELD_LENGTH(
                                                                                                                 "fieldLength",
                                                                                                                 "对账记录包含字段数非法或者对账记录包含非法长度的字段"), REASON_DATE_FORMAT(
                                                                                                                                                                   "dateFormat",
                                                                                                                                                                   "日期格式非法"), REASON_STATUS_FAIL(
                                                                                                                                                                                                 "statusFail",
                                                                                                                                                                                                 "交易为失败状态");
    private String code;
    private String message;

    EnumCompareFailReason(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
