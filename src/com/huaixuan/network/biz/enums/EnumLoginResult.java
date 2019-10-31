package com.huaixuan.network.biz.enums;

/**
 * 登录状态
 * @author Administrator
 *
 */
public enum EnumLoginResult {
	
	SUCCESS("success"),USERNAME_BLANK("usernameBlank"),PASSWORD_BLANK("passwordBlank"),PASSWORD_WRONG("passwordWrong"),USER_FREEZING("userFreezing"),
	NO_USER("noUser"),NO_DAIXIAO("nodaixiao"),NO_ZHIYING("nozhiying");
	
	private EnumLoginResult(String code){
		this.code = code;
	}
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
