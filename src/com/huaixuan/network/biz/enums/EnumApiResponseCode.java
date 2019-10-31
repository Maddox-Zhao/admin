package com.huaixuan.network.biz.enums;




/**
 * @author Mr_Yang   2015-12-3 下午01:55:28
 * api返回状态
 **/

public enum EnumApiResponseCode
{
	 UN_KNOW(1,"未知错误"),
	 SIGN_ERROR(2,"签名错误"),
	 TOO_FAST(3,"调用频繁"),
	 JSON_ERROR(4,"request的json格式错误"),
	 TIME_OVER_FIVE_MIN(5,"请求时间戳和服务器时间戳超过5分钟"),
	 NO_USER(6,"没有此用户"),
	 ONLY_POST(7,"只允许POST请求"),
	 REQUEST_ERROR(8,"请求参数不全"),
	 TIME_ERROR(9,"请求的时间戳格式错误"),
	 OVER_PER_MIN(10,"超出每分钟调用次数30"),
	 PAGEINDX_MUST(11,"pageIndex参数必须填写正确"),
	 PAGESIZE_MUST(12,"pageSize参数必须填写正确"),
	 TYPE_MUST(13,"类型只能为0或者1"),
	 TIME_BUSY(14,"系统繁忙,请在夜间查询");
	 
	 
	 private int code;
	 private String msg;
	 private EnumApiResponseCode(int code, String msg) 
	 {
	        this.code = code;
	        this.msg = msg;
	 }
	 

	 public int getKey()
	 {
		 return this.code;
	 }
	 
	 public String getValue()
	 {
		 return this.msg;
	 }
	

}
 
