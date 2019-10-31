package com.huaixuan.network.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author: yanghb
 * @since: 2008-11-27  下午06:21:24
 * @history:
 ************************************************
 * @file: MoneyUtil.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ************************************************/
public class MoneyUtil {

    private static final int DEF_DIV_SCALE = 2;

    public MoneyUtil() {
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    public static long addNumber(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.add(b2).longValue();
    }

    public static long addNumber(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).longValue();
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1, double v2) {
        return div(v1, v2, 2);
    }

    public static double div(String v1, String v2) {
        return div(Double.parseDouble(v1), Double.parseDouble(v2), 2);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static double div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 4).doubleValue();
        }
    }

    public static double round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(v);
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 4).doubleValue();
        }
    }

    public static void main(String args[]) {
        System.out.println(mul(add(5D, 663.85000000000002D), 1.01D));
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney) {
        return getFormatMoney(strMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr) {
        return getFormatMoney(strMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr, Locale locale) {
        Double doubleMoney;
        if (strMoney == null || strMoney.trim().equals("")) {
            strMoney = "0";
        }
        try {
            doubleMoney = Double.valueOf(strMoney);
        } catch (Exception e) {
            return strMoney;
        }
        return getFormatMoney(doubleMoney, formatStr, locale);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney) {
        return getFormatMoney(intMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr) {
        return getFormatMoney(intMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr, Locale locale) {
        if (intMoney == null) {
            intMoney = Integer.parseInt("0");
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(intMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param doubleMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney) {
        return getFormatMoney(doubleMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param doubleMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        return getFormatMoney(doubleMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param doubleMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr, Locale locale) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(doubleMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param bigDecimalMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney) {
        return getFormatMoney(bigDecimalMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param bigDecimalMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr) {
        return getFormatMoney(bigDecimalMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param bigDecimalMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr, Locale locale) {
        if (bigDecimalMoney == null) {
            bigDecimalMoney = BigDecimal.valueOf(0.00);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(bigDecimalMoney);
    }

    /**
     * 价格基金转换，除100，取整数
     */
    public static int getMoneyPoint(double amount1, double amount2) {
        int point = 0;
        if (amount1 + amount2 > 0.0) {
            point = (int) ((amount1 + amount2) / 100);
        }
        return point;
    }

    public static int getMoneyPointGive(double amount1, double amount2, double amount3) {
        int point = 0;
        if ((amount1 + amount2 - amount3) > 0.0) {
            point = (int) ((amount1 + amount2 - amount3) / 100);
        }
        return point;
    }

    /**
     * 格式化数字字符串
     * @param numberStr
     * @param formatStr
     * @return
     */
    public static String getFormatNumber(double numberStr) {
        DecimalFormat df = new DecimalFormat("###,### ");
        return df.format(numberStr);
    }

}