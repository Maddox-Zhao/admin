/**
 * created since 2009-8-3
 */
package com.huaixuan.network.biz.enums;

/**
 * @author shengyong
 * @version $Id: EnumStorageCheckStatus.java,v 0.1 2009-8-3 下午03:19:32 shengyong Exp $
 */
public enum EnumStorageCheckStatus {

    START("s", "开始盘点"), DETAIL("d", "生成盘点明细"), NOTDEAL("n", "需要生成明细"), FINDISH("f", "完成盘点");

    private String code;
    private String name;

    EnumStorageCheckStatus(String code, String name) {
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
