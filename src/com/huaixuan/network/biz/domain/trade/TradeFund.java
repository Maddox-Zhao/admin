package com.huaixuan.network.biz.domain.trade; 
  
 import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.huaixuan.network.biz.domain.BaseObject;
  
 /**  
  * (bibleUtil auto code generation) 
  * @version 3.2.0  
  */  
 public class TradeFund extends BaseObject implements Serializable { 
 		 /* @property: */
	 private long id;
	 /* @property: */
	 private long userId;
	 /* @property: */
	 private String tid;
	 /* @property: */
	 private double amount;
	 /* @property: */
	 private int payType;
	 /* @property: */
	 private Date gmtCreate;
	 /* @property: */
	 private Date gmtModify;
	 /* Default constructor - creates a new instance with no values set. */
	 public TradeFund(){} 
	 /* @model: */
	 public void setId(long obj){
		 this.id = obj;
	 }

	 /* @model: */
	 public long getId(){
		 return this.id;
	 }
	 /* @model: */
	 public void setUserId(long obj){
		 this.userId = obj;
	 }

	 /* @model: */
	 public long getUserId(){
		 return this.userId;
	 }
	 /* @model: */
	 public void setTid(String obj){
		 this.tid = obj;
	 }

	 /* @model: */
	 public String getTid(){
		 return this.tid;
	 }
	 /* @model: */
	 public void setAmount(double obj){
		 this.amount = obj;
	 }

	 /* @model: */
	 public double getAmount(){
		 return this.amount;
	 }
	 /* @model: */
	 public void setPayType(int obj){
		 this.payType = obj;
	 }

	 /* @model: */
	 public int getPayType(){
		 return this.payType;
	 }
	 /* @model: */
	 public void setGmtCreate(Date obj){
		 this.gmtCreate = obj;
	 }

	 /* @model: */
	 public Date getGmtCreate(){
		 return this.gmtCreate;
	 }
	 /* @model: */
	 public void setGmtModify(Date obj){
		 this.gmtModify = obj;
	 }

	 /* @model: */
	 public Date getGmtModify(){
		 return this.gmtModify;
	 }
	/*{@inheritDoc}*/
	 public boolean equals(Object o) {
		 if (this == o) {
			 return true;
		 }
		 if (!(o instanceof TradeFund)) {
			 return false;
		 }
		 final TradeFund tradefund = (TradeFund) o;
		 return this.hashCode() == tradefund.hashCode();
	 }
	/*{@inheritDoc}*/
	 public int hashCode() {
		 return this.hashCode();
	 }
	/*{@inheritDoc}*/
	 public String toString() {
		 ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
			 .append("id", this.id)
			 .append("userId", this.userId)
			 .append("tid", this.tid)
			 .append("amount", this.amount)
			 .append("payType", this.payType)
			 .append("gmtCreate", this.gmtCreate)
			 .append("gmtModify", this.gmtModify);
		 return sb.toString();
	 }
 
 } 
 