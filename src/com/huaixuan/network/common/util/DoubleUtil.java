package com.huaixuan.network.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil {
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * 
	 * @param v2
	 *            加数
	 * 
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * 
	 * @param v2
	 *            减数
	 * 
	 * @return 两个参数的差
	 */

	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * 
	 * @param v2
	 *            乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2) {

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * 
	 * @param v2
	 *            除数
	 * 
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */

	public static double div(double v1, double v2, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException("The scale must be a positive integer or zero");

		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException("The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * **********************************************
	 * 
	 * @method：round
	 * @description：
	 * @param v
	 *            提供精确的小数位四舍五入处理
	 * @param scale
	 * @return
	 * @create by:2007-11-20-18:04:21 chenzy
	 * 
	 */
	public static Double round(Double v, int scale) {

		if (scale < 0) {

			throw new IllegalArgumentException("The scale must be a positive integer or zero");

		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return new Double(b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue());

	}

	/**
	 * 提供精确的小数位非四舍五入处理。
	 * <p>
	 * 直接获取小数位后位数，之后的都为0，不进行四舍五入.
	 * 
	 * @param v
	 *            需要非四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 */

	public static double floor(double v, int scale) {
		return stripTrailing(round(v, scale + 1), scale);
	}

	/************************************************
	 * @method：stripTrailing
	 * @param value
	 *            待截断的数字
	 * @param scale
	 *            有效位数
	 * @return
	 * @description：对value的scale位后进行截断
	 * 
	 * @create:2008-5-27-上午09:29:49 chennp
	 * 
	 */
	public static double stripTrailing(double value, int scale) {
		return stripTrail(new Double(value), scale).doubleValue();
	}

	public static Double stripTrailing(Double value, int scale) {
		return stripTrail(value, scale).doubleValue();
	}

	/************************************************
	 * @method：stripTrail
	 * @param value
	 *            待截断的数字
	 * @param scale
	 *            有效位数
	 * @return
	 * @description：对value的scale位后进行截断
	 * 
	 * @create:2008-5-27-上午09:29:49 chennp
	 * 
	 */
	public static BigDecimal stripTrail(Number value, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal valueOfMoveRightDecimal = getBigDecimal(value).movePointRight(scale);
		return getBigDecimal(valueOfMoveRightDecimal.toBigInteger()).movePointLeft(scale);
	}

	protected static BigDecimal getBigDecimal(Object object) {
		return new BigDecimal(String.valueOf(object));
	}

	public static void main(String args[]) {
		long start = System.currentTimeMillis();
		;
		System.out.println(DoubleUtil.floor(mul(178.00, mul(8.28012809, 360)), 1) + ", " + 13996.896);

		System.out.println("=============" + (System.currentTimeMillis() - start));

		System.out.println(DoubleUtil.floor(1036.0, 1) + ", " + 1036.0);
		System.out.println(DoubleUtil.floor(1036.2, 1) + ", " + 1036.2);
		System.out.println(DoubleUtil.floor(1036.6, 1) + ", " + 1036.6);
		System.out.println(DoubleUtil.floor(1036.9, 1) + ", " + 1036.9);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.10, 1) + ", " + 1036.10);
		System.out.println(DoubleUtil.floor(1036.12, 1) + ", " + 1036.12);
		System.out.println(DoubleUtil.floor(1036.16, 1) + ", " + 1036.16);
		System.out.println(DoubleUtil.floor(1036.19, 1) + ", " + 1036.19);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.290, 1) + ", " + 1036.290);
		System.out.println(DoubleUtil.floor(1036.292, 1) + ", " + 1036.292);
		System.out.println(DoubleUtil.floor(1036.296, 1) + ", " + 1036.296);
		System.out.println(DoubleUtil.floor(1036.299, 1) + ", " + 1036.299);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.2990, 1) + ", " + 1036.2990);
		System.out.println(DoubleUtil.floor(1036.2992, 1) + ", " + 1036.2992);
		System.out.println(DoubleUtil.floor(1036.2996, 1) + ", " + 1036.2996);
		System.out.println(DoubleUtil.floor(1036.2999, 1) + ", " + 1036.2999);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.19990, 2) + ", " + 1036.19990);
		System.out.println(DoubleUtil.floor(1036.19992, 1) + ", " + 1036.19992);
		System.out.println(DoubleUtil.floor(1036.19996, 1) + ", " + 1036.19996);
		System.out.println(DoubleUtil.floor(1036.19999, 1) + ", " + 1036.19999);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.180, 1) + ", " + 1036.180);
		System.out.println(DoubleUtil.floor(1036.182, 1) + ", " + 1036.182);
		System.out.println(DoubleUtil.floor(1036.186, 1) + ", " + 1036.186);
		System.out.println(DoubleUtil.floor(1036.189, 1) + ", " + 1036.189);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(1036.1890, 1) + ", " + 1036.1890);
		System.out.println(DoubleUtil.floor(1036.1892, 1) + ", " + 1036.1892);
		System.out.println(DoubleUtil.floor(1036.1896, 1) + ", " + 1036.1896);
		System.out.println(DoubleUtil.floor(1036.1899, 1) + ", " + 1036.1899);
		System.out.println("=============");

		System.out.println(DoubleUtil.floor(0.0010000, 3) + ", " + 1036.18990);
		System.out.println(DoubleUtil.floor(1036.18992, 1) + ", " + 1036.18992);
		System.out.println(DoubleUtil.floor(1036.18996, 1) + ", " + 1036.18996);
		System.out.println(DoubleUtil.floor(1036.18999, 1) + ", " + 1036.18999);
	}

	/**
	 * 截取为指定小数点后长度
	 * 
	 * @param d
	 *            double
	 * @param scale
	 *            int
	 * @return String
	 * @author shenyu
	 */
	public static String format(Double d, int scale) {
		if(d == null) {
			return null;
		}
		if (scale < 1) {
			throw new IllegalArgumentException("scale must greate than 0");
		}
		StringBuilder sb = new StringBuilder("0.");
		for (int i = 0; i < scale; i++) {
			sb.append('0');
		}
		return new DecimalFormat(sb.toString()).format(d);
	}
}
