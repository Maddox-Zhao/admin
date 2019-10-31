package com.huaixuan.network.biz.enums;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.network.melody.common.util.StringUtil;

/**
 * 所有渠道枚举 请遵守命名规则:全部大写
 *
 * @author daodao
 * @version $Id: EnumInstitution.java,v 0.1 2009-7-27 下午04:50:21 daodao Exp $
 */
public enum EnumInstitution {
	// 银行
	INST_EBANK_ICBC("ICBC", "中国工商银行"), INST_EBANK_CCB("CCB", "中国建设银行"), INST_EBANK_CMB(
			"CMB", "招商银行"), INST_EBANK_ABC("ABC", "中国农业银行"), INST_EBANK_CIB(
			"CIB", "兴业银行"), INST_EBANK_CEB("CEB", "光大银行"), INST_EBANK_SPDB(
			"SPDB", "上海浦东发展银行"), INST_EBANK_GDB("GDB", "广东发展银行"), INST_EBANK_SDB(
			"SDB", "深圳发展银行"), INST_EBANK_CITIC("CITIC", "中信银行"), INST_EBANK_BOCKT(
			"BOCKT", "中国银行"), INST_EBANK_BOCM("BOCM", "交通银行"), INST_EBANK_PSBC(
			"PSBC", "中国邮政储蓄银行"), INST_EBANK_HXBANK("HXBANK", "华夏银行"), INST_EBANK_CMSB(
			"CMSB", "中国民生银行"), INST_EBANK_EGBANK("EGBANK", "恒丰银行"), INST_EBANK_CZBANK(
			"CZBANK", "浙商银行"), INST_EBANK_CBHB("CBHB", "渤海银行"), INST_EBANK_JSBANK(
			"JSBANK", "江苏银行"), INST_EBANK_NJCB("NJCB", "南京银行"), INST_EBANK_HSBANK(
			"HSBANK", "徽商银行"), INST_EBANK_NBBANK("NBBANK", "宁波银行"), INST_EBANK_SHBANK(
			"SHBANK", "上海银行"), INST_EBANK_SPABANK("SPABANK", "深圳平安银行"), INST_EBANK_RCB(
			"RCB", "农村商业银行"), INST_EBANK_ACB("ACB", "农村合作银行"), INST_EBANK_HSBS(
			"HSBS", "汇丰银行"), INST_EBANK_CITI("CITI", "花旗银行"), INST_EBANK_BEA(
			"BEA", "东亚银行"),
	// 第三方支付
	INST_ALIPAY("ALIPAY", "支付宝"),
	// 邮政
	INST_POST("POST", "中国邮政"), INST_CHINABANK("CHINABANK", "网银在线"), INST_TENPAY(
			"TENPAY", "财付通"), INST_PAYEASE("PAYEASE", "首信易"), INST_UDPAY(
			"UDPAY", "网汇通"),
	// 邮政积分卡
	INST_JSPOINT("JSPOINT", "江苏邮政积分卡"),
	// 代充代付
	INST_TERMINAL("TERMINAL", "代充代付"),
	// 农村合作社
	INST_ACCU("ACCU", "农村信用社"),
	// 城市信用合作社
	INST_CCCU("CCCU", "城市信用合作社"), INST_EBANK_ADBC("ADBC", "中国农业发展银行");

	private String name;
	private String description;

	EnumInstitution(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	/**
	 * 根据银行名称获取银行描述
	 *
	 * @param name
	 * @return
	 */
	public static String getDescriptionByName(String name) {
		EnumInstitution institution = getByName(name);
		if (institution == null) {
			return null;
		}
		return institution.getDescription();
	}

	/**
	 * 根据银行名称获取枚举
	 *
	 * @param name
	 * @return
	 */
	public static EnumInstitution getByName(String name) {
		if (name == null) {
			return null;
		}
		for (EnumInstitution institution : values()) {
			if (StringUtil.equalsIgnoreCase(institution.getName(), name))
				return institution;
		}
		return null;
	}

	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (EnumInstitution institution : values()) {
			enumDataMap
					.put(institution.getName(), institution.getDescription());
		}
		return enumDataMap;
	}

}
