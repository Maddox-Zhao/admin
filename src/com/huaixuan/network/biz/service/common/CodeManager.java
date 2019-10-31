/**
 * @Title: CodeManager.java
 * @Package com.huaixuan.network.biz.service.common
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:40:46
 * @version V1.0
 */
package com.huaixuan.network.biz.service.common;


/**
 * @ClassName: CodeManager
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-1 下午02:40:46
 *
 */
public interface CodeManager {
    public static final int TRADE_CODE=1;
    public static final int ATTRIBUTE_CODE=2;
    public static final int REFUND_CODE=3;
    //采购单：code_type=4
    public static final int CAIGOU_CODE=4;
    //采购退货单：code_type=5
    public static final int CAITUI_CODE=5;
    //出库单：code_type=6
    public static final int CHUKU_CODE=6;
    //入库单：code_type=7
    public static final int RUKU_CODE=7;
    //报残单：code_type=8
    public static final int BAOCAN_CODE=8;
    //库存退货单：code_type=9
    public static final int KUTUI_CODE=9;
    //移库单号：code_type=10
    public static final int YIKU_CODE=10;
    //外借单号：code_type=11
    public static final int WAIJIE_CODE=11;
    //批次使用的自增长数
    public static final int PICI_CODE=12;
    //库存退货申请单:code_type=13
    public static final int KUAPP_CODE=13;

    public String buildCode(int type);
    public String buildCode(int type,int length);
    /**
     * 根据类型产生代码（例如：CG00005），
     * @param type
     * @param length 自增长位数长度
     * @param preStr
     * @return
     */
    public String buildCode(int type,int length,String preStr);

    /**
     * 根据类型和日期产生代码（例如:CG2009030200004），
     * @param type
     * @param length 自增长位数长度
     * @param typeStr
     * @return
     */
    public String buildCodeByDateAndType(int type,int length,String typeStr);
}
