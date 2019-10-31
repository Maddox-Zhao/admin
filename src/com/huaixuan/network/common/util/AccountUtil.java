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
 * @since: 2009-08-05 下午06:34:24
 * @history:
 ************************************************
 * @file: AccountUtil.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ************************************************/
public class AccountUtil {
	/**
	 * 根据帐户类型代码取得帐户类型名称，如果帐户类型未知，返回类型代码
	 *
	 * @param code
	 *            帐户类型数字代码
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
	 * 根据子帐户类型代码取得子帐户类型名称，如果子帐户类型未知，返回类型代码
	 *
	 * @param code
	 *            子帐户类型数字代码
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
	 * 根据业务码获取所进行的业务名称，如果业务码未知，返回业务码
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
	 * 根据银行代码获取银行的名称，如果获取不到，可以银行代码
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
	 * 取得银行枚举Map
	 *
	 * @return
	 */
	public static Map getInstitutionMap() {
		return EnumInstitution.toMap();
	}

	/**
	 * 取得支付目的枚举
	 *
	 * @return
	 */
	public static EnumPaymentDest[] getPaymentDestEnum() {
		return EnumPaymentDest.values();
	}

	/**
	 * 取得扣款状态枚举
	 *
	 * @return
	 */
	public static EnumPaymentIsSucceed[] getPaymentIsSucceedEnum() {
		return EnumPaymentIsSucceed.values();
	}

	/**
	 * 取得账务状态枚举
	 *
	 * @return
	 */
	public static EnumPaymentFlagCompare[] getPaymentFlagCompareEnum() {
		return EnumPaymentFlagCompare.values();
	}

	/**
	 * 根据支付目的code取得支付目的描述
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
	 * 根据扣款状态的code取得扣款状态描述
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
	 * 根据对账状态的code取得对账状态描述
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
	 * 根据userType 的code取得描述
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