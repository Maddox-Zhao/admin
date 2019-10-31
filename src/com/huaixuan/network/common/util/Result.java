/**
 * @Title: Result.java
 * @Package com.huaixuan.network.biz.util
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:42:44
 * @version V1.0
 */
package com.huaixuan.network.common.util;

/**
 * @ClassName: Result
 * @Description: TODO
 * @author liuwd weida-liu@foxmail.com
 * @date 2011-3-4 ����07:42:44
 *
 */
public class Result<T> {
	/*
	 * ���ؽ�� 0 �ɹ�
	 * */
	private int result=1;
	/*
	 * ������Ϣ
	 * */
	private String message;
	/*
	 * �������
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