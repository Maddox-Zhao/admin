package com.huaixuan.network.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtil {
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������ĺ�
	 */

	public static double add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();

	}

	/**
	 * 
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������Ĳ�
	 */

	public static double sub(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();

	}

	/**
	 * 
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ���������Ļ�
	 */

	public static double mul(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();

	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
	 * 
	 * С�����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @return ������������
	 */

	public static double div(double v1, double v2) {

		return div(v1, v2, DEF_DIV_SCALE);

	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
	 * 
	 * �����ȣ��Ժ�������������롣
	 * 
	 * @param v1
	 *            ������
	 * 
	 * @param v2
	 *            ����
	 * 
	 * @param scale
	 *            ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * 
	 * @return ������������
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
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * 
	 * @param scale
	 *            С���������λ
	 * 
	 * @return ���������Ľ��
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
	 * @method��round
	 * @description��
	 * @param v
	 *            �ṩ��ȷ��С��λ�������봦��
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
	 * �ṩ��ȷ��С��λ���������봦��
	 * <p>
	 * ֱ�ӻ�ȡС��λ��λ����֮��Ķ�Ϊ0����������������.
	 * 
	 * @param v
	 *            ��Ҫ���������������
	 * @param scale
	 *            С���������λ
	 */

	public static double floor(double v, int scale) {
		return stripTrailing(round(v, scale + 1), scale);
	}

	/************************************************
	 * @method��stripTrailing
	 * @param value
	 *            ���ضϵ�����
	 * @param scale
	 *            ��Чλ��
	 * @return
	 * @description����value��scaleλ����нض�
	 * 
	 * @create:2008-5-27-����09:29:49 chennp
	 * 
	 */
	public static double stripTrailing(double value, int scale) {
		return stripTrail(new Double(value), scale).doubleValue();
	}

	public static Double stripTrailing(Double value, int scale) {
		return stripTrail(value, scale).doubleValue();
	}

	/************************************************
	 * @method��stripTrail
	 * @param value
	 *            ���ضϵ�����
	 * @param scale
	 *            ��Чλ��
	 * @return
	 * @description����value��scaleλ����нض�
	 * 
	 * @create:2008-5-27-����09:29:49 chennp
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
	 * ��ȡΪָ��С����󳤶�
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
