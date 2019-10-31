package com.huaixuan.network.biz.enums;



/**
 * @author ZHANGXIANG
 * @version $Id: EnumZhiFuBaoMistakeType.java,v 0.1 2009-8-7 下午05:06:02 ZHANGXIANG Exp $
 */
public enum EnumInputFileMistakeType {
    BankBILLNO("BankBillNo", "银行订单号："),
    TERMINALBIZNO("TerminalBizNo", "内部订单号："),
    FREEZEBALANCEFAIL("FreezeBalanceFail","发生保证金冻结不成功!"),
    ACCOUNTFAIL("AccountFail","发生账户充值不成功!");
   
    
    private String code;

    private String name;

    EnumInputFileMistakeType(String code, String name) {
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
