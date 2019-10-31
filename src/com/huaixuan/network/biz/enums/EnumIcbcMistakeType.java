package com.huaixuan.network.biz.enums;



/**
 * @author ZHANGXIANG
 * @version $Id: EnumZhiFuBaoMistakeType.java,v 0.1 2009-8-7 下午05:06:02 ZHANGXIANG Exp $
 */
public enum EnumIcbcMistakeType {
    ONLINIE("onLine", "在第"), 
    LINE("line","行"),
    PARSEMISTAKE("parseMistake","格式解析错误,元素缺少,不匹配!!!"),
    PAYDATE("payDate", "订单生成时间为空,"),
    PAYDATEPARSEMISTAKE("payDateParseMistake","订单生成时间解析错误"),
    BANKBILLNO("bankBillNo", "银行订单号为空,"),
    BANKBILLNOMISTAKE("bankBillNoMistake", "银行订单号字符过长,"),
    BANKBILLNOPARSEMISTAKE("bankBillParseNoMistake", "银行订单号解析错误,"),
    INPAYAMOUNT("inPayAmount", "收入金额不能为空,"),
    PAYMOUNTPARSEMISTAKE("payAmountParseMistkae","发生金额解析错误!!!"),
    OUTPAYAMOUNT("outPayAmount", "支出金额不能为空,"),
    INOUTPAYAMOUNT("inoutPayAmount", "发生金额（收入金额，支出金额）不能同时为0,"),
    BANKSERIALNO("bankSerialNo", "银行流水号不能为空,"),
    BANKSERIALNOMISTAKE("bankSerialNoMistake", "银行流水号解析错误,"),
    COUNTERTYPE("counterType", "支付成功"),
    COUNTERTYPEMISTAKE("counterTypeMistke", "交易状态不对"),
    PAYNO("payNO", "订单号"),
    IMPORTFAIL("importFail", "导入失败错误原因："),
    RE_BANKBILLNO("rebankbillno", "银行订单号重复");
   
    
    private String code;

    private String name;

    EnumIcbcMistakeType(String code, String name) {
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
