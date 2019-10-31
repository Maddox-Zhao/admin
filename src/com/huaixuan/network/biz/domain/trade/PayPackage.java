package com.huaixuan.network.biz.domain.trade; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * �����Զ�����(bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class PayPackage extends BaseObject implements Serializable { 
 		 /* @property: */
	 private Long id;
	 /* @property:֧����ID */
	 private Long packageId;
	 /* @property:����ID */
	 private String tid;
	 /* @property:���׽�� */
	 private double amount;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public PayPackage(){} 
	 /* @model:���� */
	 public void setId(Long obj){
		 this.id = obj;
	 }

	 /* @model:��ȡ */
	 public Long getId(){
		 return this.id;
	 }
	 /* @model:����֧����ID */
	 public void setPackageId(Long obj){
		 this.packageId = obj;
	 }

	 /* @model:��ȡ֧����ID */
	 public Long getPackageId(){
		 return this.packageId;
	 }
	 /* @model:���ý���ID */
	 public void setTid(String obj){
		 this.tid = obj;
	 }

	 /* @model:��ȡ����ID */
	 public String getTid(){
		 return this.tid;
	 }
	 /* @model:���ý��׽�� */
	 public void setAmount(double obj){
		 this.amount = obj;
	 }

	 /* @model:��ȡ���׽�� */
	 public double getAmount(){
		 return this.amount;
	 }
	 /* @model:���� */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model:��ȡ */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model:���� */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model:��ȡ */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof PayPackage)) {
			 return false;
		 }
		 final PayPackage paypackage = (PayPackage) o;
		 return this.hashCode() == paypackage.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("packageId", this.packageId)
			 .append("tid", this.tid)
			 .append("amount", this.amount)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 