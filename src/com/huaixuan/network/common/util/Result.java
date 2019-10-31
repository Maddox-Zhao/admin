/**
 * @Title: Result.java
 * @Package com.huaixuan.network.biz.util
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:42:44
 * @version V1.0
 */
package com.huaixuan.network.common.util;

/**
 * @ClassName: Result
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 下午07:42:44
 *
 */
public class Result<T> {
	/*
	 * 返回结果 0 成功
	 * */
	private int result=1;
	/*
	 * 错误消息
	 * */
	private String message;
	/*
	 * 结果对象
	 * */
	private T o;


	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(int result,String message,T o){
		this.result = result;
		this.message = message;
		this.o = o;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getO() {
		return o;
	}
	public void setO(T o) {
		this.o = o;
	}

}