package com.huaixuan.network.biz.enums;

/**
 * @author ZHANGXIANG
 * @version $Id: EnumZhiFuBaoMistakeType.java,v 0.1 2009-8-7 下午05:06:02 ZHANGXIANG Exp $
 */
public enum EnumCounterAllowUploadType {

    TERMINAL("txt", "txt代充代付文本文件格式"), ICBC("txt","txt工行文本文件格式"), ALIPAY("csv", "csv支付宝格式"),TENPAY("xls", "excel财付通格式"),CHINABANK("xls", "excel网银在线格式"), POST("txt", "txt中国邮政文本文件格式"), UPLOADCOMPAREFILE(
                                                                                                                 "uploadCompareFile",
                                                                                                                 "对账文件上传文件夹"), TENPAYFILEEXTENTIONERROR(
                                                                                                                         "counterAccountTenpayFileExtentionError",
                                                                                                                 "目前对账格式只支持EXCEL文件!!!"),CHINABANKFILEEXTENTIONERROR(
                                                                                                                         "counterAccountChinabankFileExtentionError",
                                                                                                                 "目前对账格式只支持EXCEL文件!!!"),POSTFILEEXTENTIONERROR(
                                                                                                                                                      "counterAccountPostFileExtentionError",
                                                                                                                                                      "目前对账格式只支持txt文件!!!"), ALIPAYFILEEXTENTIONERROR(
                                                                                                                                                                                                     "counterAccountAlipayFileExtentionError",
                                                                                                                                                                                                     "目前对账格式只支持csv文件!!!"), JSPOSTFILEEXTENTIONERROR(
                                                                                                                                                                                                                                                     "counterAccountJsPostFileExtentionError",
                                                                                                                                                                                                                                                     "目前对账格式没有后缀名!!!"), OTHERFILEEXTENTIONERROR(
                                                                                                                                                                                                                                                                                                "counterAccountOtherFileExtentionError",
                                                                                                                                                                                                                                                                                                "目前暂时不支持你所选择的对账类型!!!"), TERMINALFILEEXTENTIONERROR(
                                                                                                                                                                                                                                                                                                                                                   "counterAccountTerminalFileExtentionError",
                                                                                                                                                                                                                                                                                                                                                   "目前对账格式只支持txt文件!!!"),ICBCFILEEXTENTIONERROR(
                                                                                                                                                                                                                                                                                                                                                   "counterAccountIcbcFileExtentionError",
                                                                                                                                                                                                                                                                                                                                                   "目前对账格式只支持txt文件!!!");

    private String code;

    private String name;

    EnumCounterAllowUploadType(String code, String name) {
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
