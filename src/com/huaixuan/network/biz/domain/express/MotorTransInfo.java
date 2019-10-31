package com.huaixuan.network.biz.domain.express;

import java.util.Date;

import com.huaixuan.network.biz.domain.BaseObject;

public class MotorTransInfo extends BaseObject {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date gmtExpressDate;				//��������
	private String tid;							//������
	private String expressCode;					//�˵���
	private String expressName;					//���˹�˾
	private String num;							//����
	private String receiver;					//�ջ���
	private String receiverAdd;					//�ջ��˵�ַ
	private String receiverTel;					//�ջ�����ϵ��ʽ
	private String expressTel;					//������˾�绰
	private String expressAdd;					//������˾��ַ
	private String fee;							//�˷�
	private String payType;						//���ʽ
	private String status;						//�Ƿ�ɼ�


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getGmtExpressDate() {
		return gmtExpressDate;
	}
	public void setGmtExpressDate(Date gmtExpressDate) {
		this.gmtExpressDate = gmtExpressDate;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverAdd() {
		return receiverAdd;
	}
	public void setReceiverAdd(String receiverAdd) {
		this.receiverAdd = receiverAdd;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public String getExpressTel() {
		return expressTel;
	}
	public void setExpressTel(String expressTel) {
		this.expressTel = expressTel;
	}
	public String getExpressAdd() {
		return expressAdd;
	}
	public void setExpressAdd(String expressAdd) {
		this.expressAdd = expressAdd;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}