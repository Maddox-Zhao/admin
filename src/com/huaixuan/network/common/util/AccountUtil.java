package com.huaixuan.network.common.util;

import java.util.Map;

import com.huaixuan.network.biz.enums.EnumInstitution;
import com.huaixuan.network.biz.enums.EnumPaymentDest;
import com.huaixuan.network.biz.enums.EnumPaymentFlagCompare;
import com.huaixuan.network.biz.enums.EnumPaymentIsSucceed;
import com.huaixuan.network.biz.enums.EnumUserType;
import com.hundsun.itrans.biz.domain.Enum.EnumAccount;
import com.hundsun.itrans.biz.domain.Enum.EnumSubAccount;
import com.hundsun.itrans.biz.domain.Enum.EnumSubTransCode;

/**
 * @author: zhangl
 * @since: 2009-08-05 ����06:34:24
 * @history:
 ************************************************
 * @file: AccountUtil.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ************************************************/
public class AccountUtil {
	/**
	 * �����ʻ����ʹ���ȡ���ʻ��������ƣ�����ʻ�����δ֪���������ʹ���
	 *
	 * @param code
	 *            �ʻ��������ִ���
	 * @return
	 */
	public static String getAccountTypeNameByCode(String code) {
		EnumAccount enumAccount = EnumAccount.getByKey(code);
		if (enumAccount != null) {
			return enumAccount.getValue();
		}
		return code;
	}

	/**
	 * �������ʻ����ʹ���ȡ�����ʻ��������ƣ�������ʻ�����δ֪���������ʹ���
	 *
	 * @param code
	 *            ���ʻ��������ִ���
	 * @return
	 */
	public static String getSubAccountTypeNameByCode(String code) {
		EnumSubAccount enumSubAccount = EnumSubAccount.getByKey(code);
		if (enumSubAccount != null) {
			return enumSubAccount.getValue();
		}
		return code;
	}

	/**
	 * ����ҵ�����ȡ�����е�ҵ�����ƣ����ҵ����δ֪������ҵ����
	 *
	 * @param code
	 * @return
	 */
	public static String getSubTransNameByCode(String code) {
		EnumSubTransCode enumSubTransCode = EnumSubTransCode.getByCode(code);
		if (enumSubTransCode != null) {
			return enumSubTransCode.getDescription();
		}
		return code;
	}

	/**
	 * �������д����ȡ���е����ƣ������ȡ�������������д���
	 *
	 * @param code
	 * @return
	 */
	public static String getInstitutionNameByCode(String code) {
		String institutionName = EnumInstitution.getDescriptionByName(code);
		if (institutionName != null) {
			return institutionName;
		}
		return code;
	}

	/**
	 * ȡ������ö��Map
	 *
	 * @return
	 */
	public static Map getInstitutionMap() {
		return EnumInstitution.toMap();
	}

	/**
	 * ȡ��֧��Ŀ��ö��
	 *
	 * @return
	 */
	public static EnumPaymentDest[] getPaymentDestEnum() {
		return EnumPaymentDest.values();
	}

	/**
	 * ȡ�ÿۿ�״̬ö��
	 *
	 * @return
	 */
	public static EnumPaymentIsSucceed[] getPaymentIsSucceedEnum() {
		return EnumPaymentIsSucceed.values();
	}

	/**
	 * ȡ������״̬ö��
	 *
	 * @return
	 */
	public static EnumPaymentFlagCompare[] getPaymentFlagCompareEnum() {
		return EnumPaymentFlagCompare.values();
	}

	/**
	 * ����֧��Ŀ��codeȡ��֧��Ŀ������
	 *
	 * @param code
	 * @return
	 */
	public static String getPaymentDestMessageByCode(int code) {
		EnumPaymentDest enumPaymentDest = EnumPaymentDest
				.getEnumPaymentDestByValue(code);
		if (enumPaymentDest != null) {
			return enumPaymentDest.getMessage();
		}
		return code + "";
	}

	/**
	 * ���ݿۿ�״̬��codeȡ�ÿۿ�״̬����
	 *
	 * @param code
	 * @return
	 */
	public static String getPaymentIsSucceedDescriptionByCode(String code) {
		EnumPaymentIsSucceed enumPaymentIsSucceed = EnumPaymentIsSucceed
				.getByCode(code);
		if (enumPaymentIsSucceed != null) {
			return enumPaymentIsSucceed.getDescription();
		}
		return code;
	}

	/**
	 * ���ݶ���״̬��codeȡ�ö���״̬����
	 *
	 * @param code
	 * @return
	 */
	public static String getPaymentFlagCompareDescriptionByCode(String code) {
		EnumPaymentFlagCompare enumPaymentFlagCompare = EnumPaymentFlagCompare
				.getByCode(code);
		if (enumPaymentFlagCompare != null) {
			return enumPaymentFlagCompare.getDescription();
		}
		return code;
	}

	/**
	 * ����userType ��codeȡ������
	 *
	 * @param code
	 * @return
	 */
	public static String getUserTypeNameByCode(String code) {
		Map<String, String> enumDataMap = EnumUserType.toMap();
		String name = null;
		if (enumDataMap != null) {
			name = enumDataMap.get(code);
		}
		if (name == null) {
			name = code;
		}
		return name;
	}
}